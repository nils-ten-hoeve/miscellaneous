package nth.packml.mode;

public enum Mode {
	AUTOMATIC, MANUAL, MAINTENACE, CLEANING;

	public String getDisplayText() {
		StringBuilder displayText=new StringBuilder();
		displayText.append(name().substring(0,1));
		displayText.append(name().substring(1).toUpperCase());
		return displayText.toString();
	}
}
