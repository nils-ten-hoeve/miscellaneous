package nth.packml.system;

import nth.packml.mode.Mode;
import nth.packml.state.State;

@SuppressWarnings("serial")
public class ModeStateDescriptionsForMeyn extends ModeStateDescriptions {

	@Override
	protected String createStateDescription(Mode mode, State state) {
		switch (state) {
		case ABORTING:
			return "This state is activated after the “abort” event.\nThe “abort” event is triggered when there is a critical system fault, e.g. after:\n•	An emergency stop switch is triggered\n•	A fence door or door hatch is opened\n•	Critical hardware is in fault (e.g. communication to remote IO or drives is lost or is in error)\nThe abort event can be triggered from any state.\nThe abort event is sometimes also used when the stopping state takes too long and needs to be aborted\nIn this state the actuators are often stopped with a safe stop to prevent further damage or hurting people.";
		case ABORTED:
			return "This state is activated after the “aborting completed” event during the “aborting” state.\nThe system is now stationary (no more movement). \nThis state is normally active after a power up. \nThe safety system might need to be reset before continuing to another state. ";
		case CLEARING:
			return "This state is activated after the “clearing” event during the “aborted” state.\nThe clearing event is only possible if all critical faults are resolved.\nThe clearing event can be triggered by pressing a reset button.\nThe safety system will be reset, actuators will be powered, and safe stops are disabled. ";
		case STOPPING:
			return "This state is activated after the “stop” event during the resetting, idle, starting, execute, completing, completed, holding, held, unholding, suspending, suspended and unsuspending states.\nThe “stop” event is triggered by pressing a stop button.\nThis state executes the logic which brings the system to a controlled stop, in a safe position where the system can easily be restarted. ";
		case STOPPED:
			return "This state is activated after the “stopping completed” event during the “stopping” state.\nThe system is now stationary (no more movement). ";
		case RESETTING:
			return "This state is activated after the “reset” event during the “stopped” or “completed” state. \nThe reset event is triggered by pressing a reset button.\nAll system Functional State diagrams and other statuses such as alarm can be reset during this state ";
		case IDLE:
			return "This state is activated after the “resetting completed” event during the “resetting” state. \nThe system is now stationary (no more movement) and ready to start ";
		case STARTING:
			return "This state is activated after the “start” event during the “idle” state. \nThe “start” event is triggered by pressing a start button.\nThis state typically sounds a start warning horn, after which modules are put in or out of position (e.g. depending on the selected recipe) and actuators are started (ramped up) if needed. ";
		case EXECUTE:
			return "This state is activated after one of the following conditions:\n•	the “starting completed” event during the “starting” state\n•	the “unholding completed” event during the “unholding” state\n•	the “unsuspending completed” event during the “unsuspending” state\n\nThe system will run normal operation (e.g. follow a Sequential Flow Chart) during this state. ";
		case COMPLETING:
			return "This state is activated after the “complete” event during the “execute” state. \nThe complete event is normally triggered when the system has completed a given task (e.g. produced a given number of products). \nThis state executes the logic which brings the system to a controlled stop, in a safe position where the system can easily be restarted.\nFew Meyn systems will use the completing and completed states, because most Meyn systems have a discrete continuous process. ";
		case COMPLETE:
			return "This state is activated after the “completing completed” event during the “completing” state. \nThe system has completed a given task (e.g. produced a given number of products) and is waiting to be restarted during this state. Few Meyn systems will use the completing and completed states, because most Meyn systems have a discrete continuous process. ";
		case HOLDING:
			return "This state is activated after the “hold” event during the “execution” state. \nThe hold event is often used when an operator needs to temporarily hold the system's operation whilst material blockages are cleared, or to stop throughput whilst a downstream problem is resolved.\nThe “stop” event can be triggered by pressing a stop button.\nThis state executes the logic which brings the system to a controlled stop, in a safe position where the system can easily be restarted. ";
		case HELD:
			return "This state is activated after the “holding completed” event during the “holding” state. \nThe system is now stationary (no more movement) and ready to start\n  ";
		case UNHOLDING:
			return "This state is activated after the “unhold” event during the “held” state.\nThe unhold event is triggered by an operator command e.g. when material blockages are cleared, and downstream throughput problems are resolved.\nThe unhold event can be triggered by pressing a start button.\nThis state typically sounds a start warning horn, after which modules are put in or out of position (e.g. depending on the selected recipe) and actuators are started (ramped up) if needed. ";
		case SUSPENDING:
			return "This state is activated after the “suspend” event during the “execution” state. \nThe suspend event is often triggered when a upstream system or a downstream system sends a suspend signal, e.g. for a rehanger when one of the lines has stopped.\nThis state executes the logic which brings the system to a controlled stop, in a safe position where the system can easily be restarted. ";
		case SUSPENDED:
			return "This state is activated after the “suspending completed” event during the “suspending” state. \nThe system is now stationary (no more movement) and ready to start ";
		case UNSUSPENDING:
			return "This state is activated after the “unsuspend” event during the “suspended” state.\nThe unsuspend event is often triggered when a upstream system or a downstream system sends a unsuspend signal, e.g. for a rehanger when one of the lines has started running.\nThis state typically sounds a start warning horn, after which modules are put in or out of position (e.g. depending on the selected recipe) and actuators are started (ramped up) if needed.\n ";
		default:
			return "";
		}
	}

}
