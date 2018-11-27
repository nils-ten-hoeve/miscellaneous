package nth.packml.system;

import nth.packml.mode.Mode;
import nth.packml.state.State;

@SuppressWarnings("serial")
public class ModeStateDescriptionsForPackML extends ModeStateDescriptions {

	@Override
	protected String createStateDescription(Mode mode, State state) {
		switch (state) {
		case ABORTING:
			return "The ABORTED state can be entered at any time in response to the Abort command  or on the occurrence of a machine fault.  The aborting logic will bring the machine to a rapid, controlled safe stop.  Operation of the Emergency Stop will cause the machine to be tripped by its safety system it will also provide a signal to initiate the ABORTING State.";
		case ABORTED:
			return "This state maintains machine status information relevant to the Abort condition. The Stop command will force transition to the Stopped state.";
		case CLEARING:
			return "The procedural element has received a command to clear faults that may have occurred when ABORTING, and are present in the ABORTED state before proceeding to a STOPPED state.";
		case STOPPING:
			return "This state executes the logic which brings the machine to a controlled and safe stop.";
		case STOPPED:
			return "The machine is powered and stationary.  All communications with other systems are functioning (If applicable).";
		case RESETTING:
			return "This element is the result of a RESET command from the STOPPED state. RESETTING will typically cause a machine to sound a horn and place the machine in a state where components are energized awaiting a START command.";
		case IDLE:
			return "This is a State which indicates that RESETING is complete. This state maintains the machine conditions which were achieved during the RESET state.";
		case STARTING:
			return "This state provides the steps needed to start the machine and is a result of a starting type command (local or remote). Following this command the machine will begin to “execute”.";
		case EXECUTE:
			return "Once the machine is processing materials it is deemed to be Executing or in the EXECUTE state. Execute refers to the mode in which the machine is in. If the machine is in the “Clean Out” mode then “execute” refers to the action of cleaning the machine.";
		case COMPLETING:
			return "This state is typically an automatically response from the EXECUTE state. Normal operation has run to completion, ie. processing of material at the infeed will stop.";
		case COMPLETE:
			return "The machine has finished the COMPLETEING state and is now waiting for a STOP command that will cause a transition to the STOPPED state";
		case HOLDING:
			return "When the machine is in the EXECUTE state the Hold command can be used to start HOLDING logic which brings the machine to a controlled stop or to a state which represents HELD for the particular machine mode.";
		case HELD:
			return "The HELD state would typically be used by the operator to hold the temporarily hold the machine's operation whilst material blockages are cleared, or to stop throughput whilst a downstream problem is resolved.";
		case UNHOLDING:
			return "The UNHOLDING state is typically a response to an operator command to resume EXECUTE state.  UNHOLDING prepares the machine to re-enter the EXECUTE state.";
		case SUSPENDING:
			return "This state is a result of a command change from the EXECUTE state.  This state is typically required prior to the SUSPENDED wait state, and prepares the machine (ie stops glue cycles, stops carton feeds, etc) prior to the SUSPEND state.";
		case SUSPENDED:
			return "The machine may be running at the relevant setpoint speed, there is no product being produced.This state can be reached as a result of a machine status, and differs from HELD in that HELD is typically a result of an operator request.";
		case UNSUSPENDING:
			return "This state is a result of a request from SUSPENDED state to go back to the EXECUTE state.  This actions of this state may include: ramping up speeds, turning on vaccums, the re-engagement of clutches.  This state is done prior to EXECUTE state, and prepares the machine for the EXECUTE state.";
		default:
			return "";
		}
	}

}
