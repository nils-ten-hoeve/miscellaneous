package nth.packml.state;

public enum StateType {
	STOPPING, STOPPED, STARTING, RUNNING;
	public String getDisplayText() {
		StringBuilder displayText = new StringBuilder();
		displayText.append(name().substring(0, 1));
		displayText.append(name().substring(1).toUpperCase());
		return displayText.toString();
	}
}
