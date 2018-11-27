package nth.packml.state.transition;

import java.util.Arrays;
import java.util.List;

import nth.packml.state.State;
import nth.packml.state.StateRequest;

public class StateTransition {

	private final StateRequest request;
	private final State destination;
	private final List<State> sources;

	public StateTransition(List<State> sources, StateRequest request, State destination) {
		this.sources = sources;
		this.request = request;
		this.destination = destination;
	}

	public StateTransition(State source, StateRequest request, State destination) {
		this.sources = Arrays.asList(source);
		this.request = request;
		this.destination = destination;
	}

	public StateRequest getRequest() {
		return request;
	}

	public State getDestination() {
		return destination;
	}

	public List<State> getSources() {
		return sources;
	}

	public boolean isValid(State currentState, StateRequest currentRequest) {
		return sources.contains(currentState) && request == currentRequest;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s", sources, request, destination);
	}

}
