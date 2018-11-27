package nth.packml.system;

import nth.packml.mode.Mode;
import nth.packml.state.State;

public class ModeStateDescription {

	private final Mode mode;
	private final State state;
	private final String stateDescription;

	public ModeStateDescription(Mode mode, State state, String stateDescription) {
		super();
		this.mode = mode;
		this.state = state;
		this.stateDescription = stateDescription;
	}

	public Mode getMode() {
		return mode;
	}

	public State getState() {
		return state;
	}

	public String getStateDescription() {
		return stateDescription;
	}

}
