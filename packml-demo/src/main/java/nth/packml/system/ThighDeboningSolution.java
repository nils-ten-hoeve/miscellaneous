package nth.packml.system;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.displaybuttonpane.DisplayButton;
import nth.packml.gui.displaybuttonpane.DisplayStateProperty;
import nth.packml.gui.inputpane.InputButton;
import nth.packml.gui.inputpane.InputToggleButton;
import nth.packml.gui.outputpane.Output;
import nth.packml.mode.Mode;
import nth.packml.state.State;
import nth.packml.state.StateRequest;

public class ThighDeboningSolution extends System {

	private final PackMLStateMachine stateMachine;
	private final SimpleObjectProperty<Boolean> emergencyStop;
	private final SimpleObjectProperty<Boolean> machineDoorOpen;
	private final SimpleObjectProperty<Boolean> carrierTransportTripped;
	private final SimpleObjectProperty<Boolean> abort;
	private final SimpleObjectProperty<Boolean> running;

	public ThighDeboningSolution(PackMLStateMachine stateMachine) {
		this.stateMachine = stateMachine;
		emergencyStop = new SimpleObjectProperty<Boolean>(false);
		machineDoorOpen = new SimpleObjectProperty<Boolean>(false);
		carrierTransportTripped = new SimpleObjectProperty<Boolean>(false);
		abort = createAbort();
		running = createRunning();
	}

	private SimpleObjectProperty<Boolean> createRunning() {
		SimpleObjectProperty<Boolean> running = new SimpleObjectProperty<Boolean>();
		running.bind(stateMachine.getStateProperty().isEqualTo(State.EXECUTE));
		return running;
	}

	private SimpleObjectProperty<Boolean> createAbort() {
		SimpleObjectProperty<Boolean> abort = createRunning();
		abort.bind(emergencyStop.isEqualTo(true).or(machineDoorOpen.isEqualTo(true))
				.or(carrierTransportTripped.isEqualTo(true)));
		abort.addListener(e -> {
			onAbort();
		});
		return abort;
	}

	private void onAbort() {
		stateMachine.fireRequest(StateRequest.ABORT);
	}

	@Override
	public String getName() {
		return "Theigh Deboning Solution";
	}

	@Override
	public ModeStateDescriptions getModeStateDescriptions() {
		ModeStateDescriptions modeStateDescriptions = new ModeStateDescriptions();
		modeStateDescriptions.add(Mode.AUTOMATIC, State.ABORTING,
				"The ABORTED state can be entered at any time in response to the Abort command  or on the occurrence of a machine fault.  The aborting logic will bring the machine to a rapid, controlled safe stop.  Operation of the Emergency Stop will cause the machine to be tripped by its safety system it will also provide a signal to initiate the ABORTING State.");
		modeStateDescriptions.add(Mode.AUTOMATIC, State.ABORTED,
				"This state maintains machine status information relevant to the Abort condition. The Stop command will force transition to the Stopped state.");
		modeStateDescriptions.add(Mode.AUTOMATIC, State.CLEARING,
				"The procedural element has received a command to clear faults that may have occurred when ABORTING, and are present in the ABORTED state before proceeding to a STOPPED state.");
		modeStateDescriptions.add(Mode.AUTOMATIC, State.STOPPING,
				"This state executes the logic which brings the machine to a controlled and safe stop.");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.STOPPED,
		// "The machine is powered and stationary. All communications with other
		// systems are functioning (If applicable).");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.RESETTING,
		// "This element is the result of a RESET command from the STOPPED
		// state. RESETTING will typically cause a machine to sound a horn and
		// place the machine in a state where components are energized awaiting
		// a START command.");
		modeStateDescriptions.add(Mode.AUTOMATIC, State.IDLE,
				"This is a State which indicates that RESETING is complete. This state maintains the machine conditions which were achieved during the RESET state.");
		modeStateDescriptions.add(Mode.AUTOMATIC, State.STARTING,
				"This state provides the steps needed to start the machine and is a result of a starting type command (local or remote). Following this command the machine will begin to “execute”.");
		modeStateDescriptions.add(Mode.AUTOMATIC, State.EXECUTE,
				"Once the machine is processing materials it is deemed to be Executing or in the EXECUTE state. Execute refers to the mode in which the machine is in. If the machine is in the “Clean Out” mode then “execute” refers to the action of cleaning the machine.");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.COMPLETING,
		// "This state is typically an automatically response from the EXECUTE
		// state. Normal operation has run to completion, ie. processing of
		// material at the infeed will stop.");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.COMPLETE,
		// "The machine has finished the COMPLETEING state and is now waiting
		// for a STOP command that will cause a transition to the STOPPED
		// state");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.HOLDING,
		// "When the machine is in the EXECUTE state the Hold command can be
		// used to start HOLDING logic which brings the machine to a controlled
		// stop or to a state which represents HELD for the particular machine
		// mode.");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.HELD,
		// "The HELD state would typically be used by the operator to hold the
		// temporarily hold the machine's operation whilst material blockages
		// are cleared, or to stop throughput whilst a downstream problem is
		// resolved.");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.UNHOLDING,
		// "The UNHOLDING state is typically a response to an operator command
		// to resume EXECUTE state. UNHOLDING prepares the machine to re-enter
		// the EXECUTE state.");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.SUSPENDING,
		// "This state is a result of a command change from the EXECUTE state.
		// This state is typically required prior to the SUSPENDED wait state,
		// and prepares the machine (ie stops glue cycles, stops carton feeds,
		// etc) prior to the SUSPEND state.");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.SUSPENDED,
		// "The machine may be running at the relevant setpoint speed, there is
		// no product being produced.This state can be reached as a result of a
		// machine status, and differs from HELD in that HELD is typically a
		// result of an operator request.");
		// modeStateDescriptions.add(Mode.AUTOMATIC, State.UNSUSPENDING,
		// "This state is a result of a request from SUSPENDED state to go back
		// to the EXECUTE state. This actions of this state may include: ramping
		// up speeds, turning on vaccums, the re-engagement of clutches. This
		// state is done prior to EXECUTE state, and prepares the machine for
		// the EXECUTE state.");
		return modeStateDescriptions;
	}

	@Override
	public List<Control> getInputs() {
		List<Control> inputs = new ArrayList<>();
		inputs.add(new InputButton("Start button anywhere", () -> {
			onStartButton();
		}));
		inputs.add(new InputButton("Stop button anywhere", () -> {
			opStopButton();
		}));
		inputs.add(new InputButton("Reset Button on display panel", () -> {
			onResetButton();
		}));
		inputs.add(new InputButton("Key in cleaning mode", () -> {
			onKeyInCleaningMode();
		}));
		inputs.add(new InputToggleButton("Emergency Stop button", emergencyStop));
		inputs.add(new InputToggleButton("Machine door opened", machineDoorOpen));
		inputs.add(new InputToggleButton("Carrier Transport Drive Tripped", carrierTransportTripped));
		return inputs;
	}
	//
	// private void onMachineDoorClosed() {
	// machineDoorOpen.set(false);
	// }
	//
	// private void onEmergencyButtonReleased() {
	// emergencyStop.set(false);
	// }
	//
	// private void onEmergencyButtonPressed() {
	// emergencyStop.set(true);
	// }

	private void onKeyInCleaningMode() {

	}

	// private void onCarrierTransportDriveTripped() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// private void onMachineDoorOpened() {
	// machineDoorOpen.set(true);
	// }

	private void onResetButton() {
		if (!abort.get()) {
			stateMachine.fireRequest(StateRequest.CLEAR);
		}
	}

	private void opStopButton() {
		stateMachine.fireRequest(StateRequest.STOP);
	}

	private void onStartButton() {
		stateMachine.fireRequest(StateRequest.START);
	}

	@Override
	public List<Output> getOutputs() {
		ArrayList<Output> outputs = new ArrayList<>();
		outputs.add(new Output("Emergency stop", Color.RED, emergencyStop));
		outputs.add(new Output("Machine door open", Color.RED, machineDoorOpen));
		outputs.add(new Output("Carrier transport tripped", Color.RED, carrierTransportTripped));
		outputs.add(new Output("Carrier transport drive", Color.GREEN, running));
		outputs.add(new Output("De-skinner", Color.GREEN, running));
		outputs.add(new Output("Pump", Color.GREEN, running));
		return outputs;
	}

	@Override
	public List<DisplayButton> getDisplayButtons() {
		ArrayList<DisplayButton> displayButtons = new ArrayList<>();
		SimpleObjectProperty<State> stateProperty = stateMachine.getStateProperty();
		DisplayStateProperty onDisplayProperty = new DisplayStateProperty(stateProperty.isEqualTo(State.EXECUTE),
				stateProperty.isEqualTo(State.STARTING));
		displayButtons.add(new DisplayButton("1", onDisplayProperty, Color.FORESTGREEN));

		DisplayStateProperty offDisplayProperty = new DisplayStateProperty(
				stateProperty.isEqualTo(State.STOPPED).or(stateProperty.isEqualTo(State.ABORTED)),
				stateProperty.isEqualTo(State.STOPPING).or(stateProperty.isEqualTo(State.ABORTING)));
		displayButtons.add(new DisplayButton("0", offDisplayProperty, Color.GRAY));

		return displayButtons;
	}

}
