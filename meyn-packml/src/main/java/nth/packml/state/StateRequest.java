package nth.packml.state;

import java.util.Optional;

public enum StateRequest {
POWER_UP,
	ABORT, ABORTING_COMPLETED, CLEAR, CLEARING_COMPLETED, 
STOP,STOPPING_COMPLETED, 
RESET,RESETTING_COMPLETED, START, STARTING_COMPLETED, EXECUTE, COMPLETE, COMPLETING_COMPLETED,
SUSPEND, SUSPENDING_COMPLETED, UNSUSPEND, UNSUSPENDING_COMPLETED, 
HOLD, HOLDING_COMPLETED, UNHOLD, UNHOLDING_COMPLETED; 
	
	public String getDisplayText() {
		StringBuilder displayText=new StringBuilder();
		displayText.append(name().substring(0,1));
		displayText.append(name().substring(1).toLowerCase());
		return displayText.toString();
	}

	public static Optional<StateRequest> find(String requestNameToFind) {
		for (StateRequest stateRequest : StateRequest.values()) {
			if (stateRequest.name().equals(requestNameToFind)) {
				return Optional.of(stateRequest);
			}
		}
		return Optional.empty();
	}
}

