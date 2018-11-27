package nth.packml.state;

public enum State {

	STOPPED(StateType.STOPPED),
	STARTING(StateType.STARTING),
	IDLE(StateType.STOPPED), 
	SUSPENDING(StateType.STOPPING),
	SUSPENDED(StateType.STOPPED),
	UNSUSPENDING(StateType.STARTING),
	EXECUTE(StateType.RUNNING),
	STOPPING(StateType.STOPPING),
	ABORTING(StateType.STOPPING),
	ABORTED(StateType.STOPPED),
	HOLDING(StateType.STOPPING),
	HELD(StateType.STOPPED),
	UNHOLDING(StateType.STARTING),
	COMPLETING(StateType.STOPPING),
	COMPLETE(StateType.STOPPED),
	RESETTING(StateType.STOPPING),
	CLEARING(StateType.STOPPING);
	
	private final StateType stateType;

	private State(StateType stateType) {
		this.stateType = stateType;
	}
	
	public StateType getGroup() {
		return stateType;
	}
	
	public String getDisplayText() {
		StringBuilder displayText=new StringBuilder();
		displayText.append(name().substring(0,1));
		displayText.append(name().substring(1).toLowerCase());
		return displayText.toString();
	}
}
