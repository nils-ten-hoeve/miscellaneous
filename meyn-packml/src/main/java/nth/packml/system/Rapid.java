package nth.packml.system;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Control;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.outputpane.Output;

public class Rapid extends System {

	private final PackMLStateMachine stateMachine;

	public Rapid(PackMLStateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}

	@Override
	public String getName() {
		return "Rapid";
	}

	@Override
	public ModeStateDescriptions getModeStateDescriptions() {
		ModeStateDescriptions modeStateDescriptions = new ModeStateDescriptionsForMeyn();
		return modeStateDescriptions;
	}

	@Override
	public List<Control> getInputs() {
		return new ArrayList<>();
	}

	@Override
	public List<Output> getOutputs() {
		return new ArrayList<>();
	}

	@Override
	public List<Control> getDisplayButtons() {
		return new ArrayList<>();
	}

}
