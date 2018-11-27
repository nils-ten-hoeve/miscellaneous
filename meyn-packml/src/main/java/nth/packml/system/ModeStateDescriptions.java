package nth.packml.system;

import java.util.ArrayList;

import nth.packml.mode.Mode;
import nth.packml.state.State;

@SuppressWarnings("serial")
public abstract class ModeStateDescriptions extends ArrayList<ModeStateDescription> {

	public void add(Mode mode, State state) {
		String stateDescription = createStateDescription(mode, state);
		ModeStateDescription modeStateDescription = new ModeStateDescription(mode, state, stateDescription);
		add(modeStateDescription);
	}

	protected abstract String createStateDescription(Mode mode, State state);
}
