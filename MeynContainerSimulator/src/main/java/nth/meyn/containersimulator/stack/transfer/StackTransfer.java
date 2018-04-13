package nth.meyn.containersimulator.stack.transfer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import nth.meyn.containersimulator.layout.Layout;
import nth.meyn.containersimulator.routemanager.Route;
import nth.meyn.containersimulator.routemanager.RouteManager;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.statemachine.StateMachine;
import nth.meyn.containersimulator.unit.StackDestination;
import nth.meyn.containersimulator.unit.StackPosition;
import nth.meyn.containersimulator.unit.StackPositions;
import nth.meyn.containersimulator.unit.StackSource;
import nth.meyn.containersimulator.unit.cas.Cas;
import nth.meyn.containersimulator.unit.stack.finish.StackFinish;
import nth.meyn.containersimulator.unit.turntable.TurnTable;
import nth.meyn.containersimulator.unit.turntable.TurnTableStackDestination;
import nth.meyn.containersimulator.unit.turntable.TurnTableStackSource;

public class StackTransfer extends StateMachine {

	// TODO simultaneous StateFeedOut and StateFeedIn

	private final StackSource stackSource;
	private final StackDestination stackDestination;
	private Layout layout;

	public StackTransfer(Layout layout, StackSource stackSource,
			StackDestination stackDestination) {
		super(createName(stackSource, stackDestination), getStateClasses());
		this.layout = layout;
		this.stackSource = stackSource;
		this.stackDestination = stackDestination;
	}

	public StackTransfer(Layout layout, StackPositions stackPositions, String sourceName,
			String destinationName) {
		this(layout, stackPositions.getStackSource(sourceName),
				stackPositions.getStackDestination(destinationName));
	}

	private static String createName(StackSource stackSource, StackDestination stackDestination) {
		StringBuilder name = new StringBuilder();
		name.append(StackTransfer.class.getSimpleName());
		name.append(":");
		name.append(stackSource.getName());
		name.append("->");
		name.append(stackDestination.getName());
		return name.toString();
	}

	private static List<Class<? extends State>> getStateClasses() {
		List<Class<? extends State>> stateClasses = new ArrayList<>();
		stateClasses.add(StateWaitForTransfer.class);
		stateClasses.add(StateTransfer.class);
		return stateClasses;
	}

	public StackSource getStackSource() {
		return stackSource;
	}

	public StackDestination getStackDestination() {
		return stackDestination;
	}

	@Override
	public String toString() {
		return getName();
	}

	public boolean isAtDestination() {
		String stackDestinationName = stackSource.getStack().getDestination().getName();
		return stackDestinationName.equals(stackSource.getName());
	}

	public boolean onRoute() {
		RouteManager routeManager = layout.getRouteManager();
		String finalDestinationName = stackSource.getStack().getDestination().getName();
		Optional<Route> foundRoute = routeManager.findRoute(stackSource.getName(),
				finalDestinationName);
		if (foundRoute.isPresent()) {
			// is this stack transfer actually part of the route?
			return foundRoute.get().contains(this);
		} else {
			return false;
		}
	}

	/**
	 * Checks if the stack destination is a {@link Cas} and if this {@link Cas}
	 * to {@link StackFinish} route shares a {@link StackPosition} with the
	 * current route, because these positions need to be kept clear if there is
	 * a container on this route.
	 * 
	 * @return
	 */
	public boolean blocksRoute() {

		Stack stack = stackSource.getStack();
		StackPosition finalDestination = stack.getDestination();
		if (!(finalDestination instanceof Cas)) {
			return false;
		}
		RouteManager routeManager = layout.getRouteManager();

		String casName = finalDestination.getName();
		Optional<Route> sourceToCas = routeManager.findRoute(stackSource.getName(), casName);
		Optional<Route> casToFinish = routeManager.findRouteToFinish(casName);

		if (!sourceToCas.isPresent() || !casToFinish.isPresent()) {
			return false;
		}

		Set<StackPosition> commonStackPositions = findCommonStackPositions(sourceToCas.get(),
				casToFinish.get());
		
		
		
		
		if (!contains(commonStackPositions)) {
			return false;
		}
		
		for (StackPosition commonStackPosition : commonStackPositions) {
			if (commonStackPosition.getStack() != null) {
				return true; 
			}
		}

		return false;
	}

	private boolean contains(Set<StackPosition> commonStackPositions) {
		List<StackPosition> transferPositions = getStackPositions();
		for (StackPosition transferPosition : transferPositions) {
			if (commonStackPositions.contains(transferPosition)) {
				return true;
			}
		}
		return false;
	}

	private Set<StackPosition> findCommonStackPositions(Route route1, Route route2) {

		Set<StackPosition> commonStackPositions = new HashSet<>();

		LinkedHashSet<StackPosition> stackPositionsRoute1 = route1.getStackPositions();
		LinkedHashSet<StackPosition> stackPositionsRoute2 = route2.getStackPositions();

		if (stackSource instanceof TurnTableStackSource) {
			TurnTableStackSource turnTableStackSource = (TurnTableStackSource) stackSource;
			stackPositionsRoute1.remove(turnTableStackSource.getTurnTable());
		} else {
			stackPositionsRoute1.remove(stackSource);
		}
		
		for (StackPosition stackPosition : stackPositionsRoute2) {
			if (stackPositionsRoute1.contains(stackPosition)) {
				commonStackPositions.add(stackPosition);
			}
		}
		return commonStackPositions;
	}

	public List<StackPosition> getStackPositions() {
		List<StackPosition> stackPositions = new ArrayList<>();
		if (stackSource instanceof TurnTableStackSource) {
			TurnTableStackSource turnTableStackSource = (TurnTableStackSource) stackSource;
			stackPositions.add(turnTableStackSource.getTurnTable());
		} else {
			stackPositions.add(stackSource);
		}
		if (stackDestination instanceof TurnTableStackDestination) {
			TurnTableStackDestination turnTableStackDestination = (TurnTableStackDestination) stackDestination;
			stackPositions.add(turnTableStackDestination.getTurnTable());
		} else {
			stackPositions.add(stackDestination);
		}
		return stackPositions;
	}

	public boolean isOkToFeedOut() {
		return stackSource.isOkToFeedOut();
	}

	public boolean isOkToFeedIn() {
		return stackDestination.isOkToFeedIn();
	}

	public boolean isRouteOk() {

		boolean sourceHasStack = stackSource.getStack() != null;
		if (!sourceHasStack) {
			return false;
		}
		boolean atDestination = isAtDestination();
		if (atDestination) {
			return false;
		}
		boolean onRoute = onRoute();
		if (!onRoute) {
			return false;
		}
		boolean blocksRoute = blocksRoute();
		return !blocksRoute;
	}

}
