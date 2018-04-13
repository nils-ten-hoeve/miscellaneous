package nth.meyn.containersimulator.statemachine;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nth.meyn.containersimulator.Updatable;

public class StateMachine implements Updatable {

	private final List<State> states;
	private State currentState;
	private final String name;

	public StateMachine(String name, List<Class<? extends State>> stateClasses) {
		this.name = name;
		states = createStates(stateClasses);
		if (states.size() < 1) {
			throw new RuntimeException("This state machine must have at least on state!");
		}
		currentState = states.get(0);
	}

	private List<State> createStates(List<Class<? extends State>> stateClasses) {
		List<State> states = new ArrayList<>();
		for (Class<? extends State> stateClass : stateClasses) {
			Constructor<State> stateConstructor;
			try {
				stateConstructor = findStateConstructor(stateClass);
				State state = stateConstructor.newInstance(this);
				states.add(state);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return Collections.unmodifiableList(states);
	}

	@SuppressWarnings("unchecked")
	private Constructor<State> findStateConstructor(Class<? extends State> stateClass)
			throws NoSuchMethodException, SecurityException {
		Constructor<State> constructor = (Constructor<State>) stateClass.getConstructors()[0];
		return constructor;
	}

	public List<State> getStates() {
		return states;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void update() {
		Class<? extends State> nextStateClass = currentState.getNextStateClass();
		if (nextStateClass != null) {
			State nextState = getState(nextStateClass);
			onStateChange(currentState, nextState);
			currentState.onExit();
			currentState = nextState;
			currentState.onStart();
		}
	}

	/**
	 * Hook (e.g. for logging state changes). Note that state actions must be
	 * located on {@link State#onStart()} and {@link State#onExit()} methods
	 * 
	 * @param currentState2
	 * @param nextState
	 */
	public void onStateChange(State currentState, State nextState) {
		StringBuilder message = new StringBuilder();
		message.append(name);
		message.append(": ");
		message.append(this.getClass().getSimpleName());
		message.append(": ");
		message.append(currentState.getClass().getSimpleName());
		message.append(" -> ");
		message.append(nextState.getClass().getSimpleName());
	//	System.out.println(message.toString());
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public <T extends State> T getState(Class<T> stateType) {
		for (State state : states) {
			if (state.getClass() == stateType) {
				return (T) state;
			}
		}
		throw new RuntimeException(
				"Could not find a state of type: " + stateType.getCanonicalName());

	}
	
	@Override
	public String toString() {
		return name;
	}
}
