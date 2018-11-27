package nth.packml.system;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.displaybuttonpane.Style;
import nth.packml.gui.inputpane.InputButton;
import nth.packml.gui.inputpane.InputToggleButton;
import nth.packml.gui.outputpane.Output;
import nth.packml.mode.Mode;
import nth.packml.state.State;
import nth.packml.state.StateRequest;

public class PackMLSystem extends System {

	private final PackMLStateMachine stateMachine;

	public PackMLSystem(PackMLStateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}

	@Override
	public String getName() {
		return "Pack-ML System";
	}

	@Override
	public ModeStateDescriptions getModeStateDescriptions() {
		ModeStateDescriptions modeStateDescriptions = new ModeStateDescriptionsForPackML();
		modeStateDescriptions.add(Mode.PRODUCTION, State.ABORTING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.ABORTED);
		modeStateDescriptions.add(Mode.PRODUCTION, State.CLEARING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.STOPPING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.STOPPED);
		modeStateDescriptions.add(Mode.PRODUCTION, State.RESETTING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.IDLE);
		modeStateDescriptions.add(Mode.PRODUCTION, State.STARTING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.EXECUTE);
		modeStateDescriptions.add(Mode.PRODUCTION, State.COMPLETING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.COMPLETE);
		modeStateDescriptions.add(Mode.PRODUCTION, State.HOLDING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.HELD);
		modeStateDescriptions.add(Mode.PRODUCTION, State.UNHOLDING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.SUSPENDING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.SUSPENDED);
		modeStateDescriptions.add(Mode.PRODUCTION, State.UNSUSPENDING);
		return modeStateDescriptions;
	}

	@Override
	public List<Control> getInputs() {
		List<Control> inputs = new ArrayList<>();
		inputs.add(new InputButton("Start", () -> {
			stateMachine.fireRequest(StateRequest.START);
		}));
		inputs.add(new InputButton("Stop", () -> {
			stateMachine.fireRequest(StateRequest.STOP);
		}));
		inputs.add(new InputButton("Complete", () -> {
			stateMachine.fireRequest(StateRequest.COMPLETE);
		}));
		inputs.add(new InputToggleButton("Hold", () -> {
			stateMachine.fireRequest(StateRequest.HOLD);
		}, () -> {
			stateMachine.fireRequest(StateRequest.UNHOLD);
		}));
		inputs.add(new InputToggleButton("Suspend", () -> {
			stateMachine.fireRequest(StateRequest.SUSPEND);
		}, () -> {
			stateMachine.fireRequest(StateRequest.UNSUSPEND);
		}));
		inputs.add(new InputButton("Abort", () -> {
			stateMachine.fireRequest(StateRequest.ABORT);
		}));
		inputs.add(new InputButton("Clear", () -> {
			stateMachine.fireRequest(StateRequest.CLEAR);
		}, createStateProperty(State.ABORTED), Style.HIGHLIGHT_COLOR));
		inputs.add(new InputButton("Reset", () -> {
			stateMachine.fireRequest(StateRequest.RESET);
		}, createResetProperty(), Style.HIGHLIGHT_COLOR));
		return inputs;
	}

	private SimpleObjectProperty<Boolean> createResetProperty() {
		SimpleObjectProperty<Boolean> resetProperty = new SimpleObjectProperty<Boolean>();
		resetProperty.bind(stateMachine.getStateProperty().isEqualTo(State.STOPPED)
				.or(stateMachine.getStateProperty().isEqualTo(State.COMPLETE)));
		return resetProperty;
	}

	private SimpleObjectProperty<Boolean> createStateProperty(State state) {
		SimpleObjectProperty<Boolean> stateProperty = new SimpleObjectProperty<Boolean>();
		stateProperty.bind(stateMachine.getStateProperty().isEqualTo(state));
		return stateProperty;
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
