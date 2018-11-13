package nth.packml.state.transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nth.packml.mode.Mode;
import nth.packml.state.State;
import nth.packml.state.StateRequest;

@SuppressWarnings("serial")
public class StateTransitions extends ArrayList<StateTransition> {

	private final List<Route> defaultRoutes;

	public StateTransitions(nth.packml.system.System system, Mode mode) {
		defaultRoutes = RouteFactory.createDefaultRoutes();
		List<State> disabledStates = system.getDisabledStates(mode);
		addAll(createTransitionsForMode(mode, disabledStates));
		bypassDisabledStates(disabledStates);
	}

	private void bypassDisabledStates(List<State> disabledStates) {
		List<StateTransition> toRemove = new ArrayList<>();
		for (State disabledState : disabledStates) {
			List<StateTransition> withDisabledSources = findTransitionsWithSource(disabledState);
			List<StateTransition> withDisablesDestinations = findTransitionsWithDestination(disabledState);

			if (withDisabledSources.size() == 1 && withDisablesDestinations.size() == 1) {
				toRemove.add(withDisabledSources.get(0));
				toRemove.add(withDisablesDestinations.get(0));
				StateTransition bypassTransition = new StateTransition(withDisablesDestinations.get(0).getSources(),
						withDisablesDestinations.get(0).getRequest(), withDisabledSources.get(0).getDestination());
				add(bypassTransition);
			}

			if (withDisabledSources.size() == 1 && withDisablesDestinations.size() >= 1) {
				for (StateTransition withDisablesDestination : withDisablesDestinations) {
					toRemove.add(withDisabledSources.get(0));
					toRemove.add(withDisablesDestination);
					StateTransition bypassTransition = new StateTransition(withDisablesDestination.getSources(),
							withDisablesDestination.getRequest(), withDisabledSources.get(0).getDestination());
					add(bypassTransition);
				}
			}
			removeAll(toRemove);
		}

	}

	private List<StateTransition> findTransitionsWithDestination(State disabledState) {
		return stream().filter(transition -> transition.getDestination() == disabledState).collect(Collectors.toList());
	}

	private List<StateTransition> findTransitionsWithSource(State disabledState) {
		return stream().filter(transition -> transition.getSources().contains(disabledState))
				.collect(Collectors.toList());
	}

	private List<StateTransition> createTransitionsForMode(Mode mode, List<State> disabledStatesForMode) {
		List<StateTransition> transitions = new ArrayList<>();
		for (Route route : defaultRoutes) {
			transitions.addAll(route.getFor(mode, disabledStatesForMode));
		}
		return transitions;
	}

	public Optional<StateTransition> find(State currentState, StateRequest stateRequest) {
		return stream().filter(transition -> transition.isValid(currentState, stateRequest)).findFirst();
	}
}
