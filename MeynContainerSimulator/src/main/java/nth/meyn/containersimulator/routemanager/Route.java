package nth.meyn.containersimulator.routemanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.stack.transfer.StackTransfers;
import nth.meyn.containersimulator.unit.StackPosition;
import nth.meyn.containersimulator.unit.turntable.TurnTableStackSource;

public class Route extends ArrayList<StackTransfer> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8294836308912010642L;

	public List<StackTransfer> findNext(StackTransfers stackTransfers) {
		if (isEmpty()) {
			return new ArrayList<>();
		}
		Optional<StackTransfer> last = getLast();
		if (last.isPresent()) {
			String sourceNameToFind = last.get().getStackDestination().getName();
			return stackTransfers.findWithSourceName(sourceNameToFind);
		} else {
			return new ArrayList<>();
		}
	}

	public Optional<StackTransfer> getLast() {
		if (isEmpty()) {
			return  Optional.empty(); 
		} else {
			StackTransfer last = get(size() - 1);
			return Optional.of(last);
		}
	}

	public LinkedHashSet<StackPosition> getStackPositions() {
		LinkedHashSet<StackPosition> stackPositions=new LinkedHashSet<>();
		for (StackTransfer stackTransfer: this) {
			stackPositions.addAll(stackTransfer.getStackPositions());
		}
		return stackPositions;
	}

	public boolean hasAStackWithPosition(Class<? extends StackPosition> stackPositionTypeToFind) {
		Set<StackPosition> stackPositions=getStackPositions();
		for (StackPosition stackPosition : stackPositions) {
			if (stackPosition.getClass()== stackPositionTypeToFind) {
				return true;
			}
		}
		return false;
	}

	public boolean containsStackSource(String name) {
		for (StackTransfer stackTransfer : this) {
			if (name.equals(stackTransfer.getStackSource().getName())) {
				return true;
			}
		}
		return false;
	}

	public boolean containsStackDestination(String name) {
		for (StackTransfer stackTransfer : this) {
			if (name.equals(stackTransfer.getStackDestination().getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return True if there is a stack on the route (ignoring the first position of the route!!!)
	 */
	public boolean hasStackOnRoute() {
		for (StackTransfer stackTransfer : this) {
			if (stackTransfer.getStackDestination().getStack()!=null ) {
				return true;
			}
		}
		return false;
	}

	public boolean hasStackOnRouteWithDestination(StackPosition stackPosition) {
		for (StackTransfer stackTransfer : this) {
			Stack stack = stackTransfer.getStackDestination().getStack();
			if (stack!=null && stack.getDestination()==stackPosition) {
				return true;
			}
		}
		return false;
	}

}
