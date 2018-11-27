package nth.packml.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.scene.control.Control;
import nth.packml.gui.outputpane.Output;
import nth.packml.mode.Mode;
import nth.packml.state.State;

public abstract class System {
	abstract public String getName();

	abstract public ModeStateDescriptions getModeStateDescriptions();

	abstract public List<Control> getDisplayButtons();

	abstract public List<Control> getInputs();

	abstract public List<Output> getOutputs();

	public List<State> getDisabledStates(Mode mode) {
		List<ModeStateDescription> modeStateDescriptions = getModeStateDescriptions();
		List<State> allStates = Arrays.asList(State.values());
		List<State> statesForMode = modeStateDescriptions.stream()
				.filter(modeStateDescription -> modeStateDescription.getMode() == mode)
				.map(ModeStateDescription::getState).collect(Collectors.toList());
		List<State> disabledStates = new ArrayList<>(allStates);
		disabledStates.removeAll(statesForMode);
		return disabledStates;
	}

	public Set<Mode> getModes() {
		List<ModeStateDescription> modeStateDescriptions = getModeStateDescriptions();
		Set<Mode> modes = modeStateDescriptions.stream().map(ModeStateDescription::getMode).collect(Collectors.toSet());
		return modes;
	}

	@Override
	public String toString() {
		return getName();
	}

}
