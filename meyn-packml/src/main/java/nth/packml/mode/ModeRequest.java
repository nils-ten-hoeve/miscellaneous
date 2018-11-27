package nth.packml.mode;

public enum ModeRequest {
	PRODUCTION, MANUAL, CLEANING, SERVICE;

	public String getDisplayText() {
		StringBuilder displayText = new StringBuilder();
		displayText.append(name().substring(0, 1));
		displayText.append(name().substring(1).toLowerCase());
		return displayText.toString();
	}
}
