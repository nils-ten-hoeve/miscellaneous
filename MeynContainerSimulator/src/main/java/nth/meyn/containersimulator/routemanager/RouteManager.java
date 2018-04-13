package nth.meyn.containersimulator.routemanager;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.stack.transfer.StackTransfers;
import nth.meyn.containersimulator.unit.StackPosition;
import nth.meyn.containersimulator.unit.stack.finish.StackFinish;

public class RouteManager  {

	private final StackTransfers stackTransfers;

	public RouteManager(StackTransfers stackTransfers) {
		this.stackTransfers = stackTransfers;
	}

	public Optional<Route> findRoute(String sourceName, String finalDestinationName) {
		List<StackTransfer> foundStackTransfers = stackTransfers.findWithSourceName(sourceName);
		for (StackTransfer stackTransfer : foundStackTransfers) {
			Route potentialRoute = new Route();
			potentialRoute.add(stackTransfer);
			
			if (stackTransfer.getStackDestination().getName().equals(finalDestinationName)) {
				return Optional.of( potentialRoute);
			}

			Optional<Route> route = createAndFind(potentialRoute, finalDestinationName);
			if (route.isPresent()) {
				return route;
			}
		}
		return Optional.empty();

	}

	private Optional<Route> createAndFind(Route potentialRoute, String finalDestinationName) {
		List<StackTransfer> nextStackTransfers = potentialRoute.findNext(stackTransfers);
		if (nextStackTransfers.isEmpty()) {
			return Optional.empty(); // dead end
		}

		for (StackTransfer nextStackTransfer : nextStackTransfers) {
			if (!potentialRoute.contains(nextStackTransfer)
					&& !potentialRoute
					.containsStackSource(nextStackTransfer.getStackDestination().getName())
					&& !potentialRoute
							.containsStackDestination(nextStackTransfer.getStackDestination().getName())) {
				Route newPotentialRoute = new Route();
				newPotentialRoute.addAll(potentialRoute);
				newPotentialRoute.add(nextStackTransfer);

				if (nextStackTransfer.getStackDestination().getName()
						.equals(finalDestinationName)) {
					return Optional.of(newPotentialRoute);
				} else {
					Optional<Route> recursiveResult = createAndFind(newPotentialRoute, finalDestinationName);
					if (recursiveResult.isPresent()) {
						return recursiveResult;
					}
				}
			}
		}
		return Optional.empty();// not found or circular route
	}

	/**
	 * @return stack positions that are being used in stack transfers
	 */
	public Set<StackPosition> findStackPositions() {
		Set<StackPosition> stackPositions = new HashSet<>();
		for (StackTransfer stackTransfer : stackTransfers) {
			stackPositions.add(stackTransfer.getStackSource());
			stackPositions.add(stackTransfer.getStackDestination());
		}
		return stackPositions;
	}

	public List<StackPosition> findStackPositionsOfType(
			Class<? extends StackPosition> stackPositionTypeToFind) {
		Set<StackPosition> stackPositions = findStackPositions();
		List<StackPosition> result = stackPositions.stream()
				.filter(sp -> sp.getClass() == stackPositionTypeToFind)
				.collect(Collectors.toList());
		return result;
	}

	public Optional<Route> findRouteToFinish(String sourceName) {
		List<StackPosition> stackFinishPositions = findStackPositionsOfType(StackFinish.class);

		for (StackPosition stackFinishPosition : stackFinishPositions) {
			Optional<Route> route = findRoute(sourceName, stackFinishPosition.getName());
			if (route.isPresent()) {
				return route;
			}
		}
		return Optional.empty();
	}

}
