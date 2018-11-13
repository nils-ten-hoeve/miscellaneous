package nth.packml.system;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Control;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.displaybuttonpane.DisplayButton;
import nth.packml.gui.outputpane.Output;

public class DrawerSystem extends System {

	private final PackMLStateMachine stateMachine;

	public DrawerSystem(PackMLStateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}

	@Override
	public String getName() {
		return "Drawer System";
	}

	@Override
	public ModeStateDescriptions getModeStateDescriptions() {
		// TODO Auto-generated method stub
		return null;
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
	public List<DisplayButton> getDisplayButtons() {
		return new ArrayList<>();
	}
}
