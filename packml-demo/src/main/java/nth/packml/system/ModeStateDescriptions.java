package nth.packml.system;

import java.util.ArrayList;

import nth.packml.mode.Mode;
import nth.packml.state.State;

@SuppressWarnings("serial")
public class ModeStateDescriptions extends ArrayList<ModeStateDescription> {

	public void add(Mode mode, State state, String stateDescription) {
		ModeStateDescription modeStateDescription=new ModeStateDescription(mode, state, stateDescription);
		add(modeStateDescription);
	}
}
