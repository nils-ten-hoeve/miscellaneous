package nth.packml.system;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Control;
import nth.packml.gui.PackMLStateMachine;
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
		ModeStateDescriptions modeStateDescriptions = new ModeStateDescriptions();
		modeStateDescriptions.add(Mode.PRODUCTION, State.ABORTING,
				"The ABORTED state can be entered at any time in response to the Abort command  or on the occurrence of a machine fault.  The aborting logic will bring the machine to a rapid, controlled safe stop.  Operation of the Emergency Stop will cause the machine to be tripped by its safety system it will also provide a signal to initiate the ABORTING State.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.ABORTED,
				"This state maintains machine status information relevant to the Abort condition. The Stop command will force transition to the Stopped state.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.CLEARING,
				"The procedural element has received a command to clear faults that may have occurred when ABORTING, and are present in the ABORTED state before proceeding to a STOPPED state.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.STOPPING,
				"This state executes the logic which brings the machine to a controlled and safe stop.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.STOPPED,
				"The machine is powered and stationary.  All communications with other systems are functioning (If applicable).");
		modeStateDescriptions.add(Mode.PRODUCTION, State.RESETTING,
				"This element is the result of a RESET command from the STOPPED state. RESETTING will typically cause a machine to sound a horn and place the machine in a state where components are energized awaiting a START command.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.IDLE,
				"This is a State which indicates that RESETING is complete. This state maintains the machine conditions which were achieved during the RESET state.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.STARTING,
				"This state provides the steps needed to start the machine and is a result of a starting type command (local or remote). Following this command the machine will begin to “execute”.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.EXECUTE,
				"Once the machine is processing materials it is deemed to be Executing or in the EXECUTE state. Execute refers to the mode in which the machine is in. If the machine is in the “Clean Out” mode then “execute” refers to the action of cleaning the machine.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.COMPLETING,
				"This state is typically an automatically response from the EXECUTE state. Normal operation has run to completion, ie. processing of material at the infeed will stop.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.COMPLETE,
				"The machine has finished the COMPLETEING state and is now waiting for a STOP command that will cause a transition to the STOPPED state");
		modeStateDescriptions.add(Mode.PRODUCTION, State.HOLDING,
				"When the machine is in the EXECUTE state the Hold command can be used to start HOLDING logic which brings the machine to a controlled stop or to a state which represents HELD for the particular machine mode.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.HELD,
				"The HELD state would typically be used by the operator to hold the temporarily hold the machine's operation whilst material blockages are cleared, or to stop throughput whilst a downstream problem is resolved.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.UNHOLDING,
				"The UNHOLDING state is typically a response to an operator command to resume EXECUTE state.  UNHOLDING prepares the machine to re-enter the EXECUTE state.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.SUSPENDING,
				"This state is a result of a command change from the EXECUTE state.  This state is typically required prior to the SUSPENDED wait state, and prepares the machine (ie stops glue cycles, stops carton feeds, etc) prior to the SUSPEND state.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.SUSPENDED,
				"The machine may be running at the relevant setpoint speed, there is no product being produced.This state can be reached as a result of a machine status, and differs from HELD in that HELD is typically a result of an operator request.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.UNSUSPENDING,
				"This state is a result of a request from SUSPENDED state to go back to the EXECUTE state.  This actions of this state may include: ramping up speeds, turning on vaccums, the re-engagement of clutches.  This state is done prior to EXECUTE state, and prepares the machine for the EXECUTE state.");
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
		inputs.add(new InputButton("Comple", () -> {
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
		}));
		inputs.add(new InputButton("Reset", () -> {
			stateMachine.fireRequest(StateRequest.RESET);
		}));
		return inputs;
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
