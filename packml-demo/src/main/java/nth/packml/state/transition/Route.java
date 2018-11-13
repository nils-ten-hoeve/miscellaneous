package nth.packml.state.transition;

import java.util.ArrayList;
import java.util.List;

import nth.packml.mode.Mode;
import nth.packml.state.State;

@SuppressWarnings("serial")
public class Route extends ArrayList<StateTransition> {

	public Route(Route route) {
		super(route);
	}

	public Route() {
		super();
	}

	/**
	 * @param mode
	 * @param disabledStatesForMode
	 * @return a copy of the default route where disabled states are removed (or
	 *         bypassed)
	 */
	public Route getFor(Mode mode, List<State> disabledStatesForMode) {
		Route route = new Route(this);

		boolean allTransitionsHaveDisabledStates = true;
		for (StateTransition stateTransition : route) {
			List<State> sources = stateTransition.getSources();
			if (sources.size() == 1) {
				if (!disabledStatesForMode.contains(sources.get(0))) {
					allTransitionsHaveDisabledStates = false;
				}
				if (!disabledStatesForMode.contains(stateTransition.getDestination())) {
					allTransitionsHaveDisabledStates = false;
				}
			} else if (sources.size() > 1) {
				allTransitionsHaveDisabledStates = false;
			}
		}
		if (allTransitionsHaveDisabledStates) {
			return createEmptyRoute();
		}

		for (StateTransition stateTransition : route) {
			List<State> sources = stateTransition.getSources();
			if (sources.size() > 1) {
				sources.removeAll(disabledStatesForMode);
			}
		}

		// for (State disabledState : disabledStatesForMode) {
		// Optional<StateTransition> sourceTransition =
		// route.findTransitionWithSource(disabledState);
		// Optional<StateTransition> destinationTransition =
		// route.findTransitionWithDestination(disabledState);
		// if (sourceTransition.isPresent() &&
		// destinationTransition.isPresent()) {
		// route.remove(sourceTransition.get());
		// route.remove(destinationTransition.get());
		// StateTransition bypassTransition = new
		// StateTransition(destinationTransition.get().getSources(),
		// destinationTransition.get().getRequest(),
		// sourceTransition.get().getDestination());
		// route.add(bypassTransition);
		// }
		// }

		return route;
	}

	private Route createEmptyRoute() {
		return new Route();
	}

}
