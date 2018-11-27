package nth.packml.mode;

public enum Mode {
	PRODUCTION(
			"This mode is used for routine production (a process step for making products from raw material).\nThe system executes relevant logic in response to commands from the operator or issued by another supervisory system or signals from sensors or other systems.",
			"2"),
	/*
	 * 
	 */
	MANUAL("This mode provides direct control of individual system actuators.\nThis feature is available depending upon the mechanical constraints of the mechanisms being exercised.\nThis feature would be typically used for:\n•	Correcting system actuators so that the system is in a state where the system can be started in production mode (e.g. repositioning a jammed container).\n•	The commissioning of individual actuators\n•	Testing actuators (e.g. for maintenance).\nOften the safety systems work the same as in production mode.",
			"1"),
	/*
	 * 
	 */
	CLEANING(
			"This mode is used by authorized cleaning personal to clean and sanitize the system at the end of production.\n•	The operators will need to get clear access to all system parts for cleaning and inspection\n•	In some cases, the system can run with a reduced speed\n•	In some cases, the system can automatically clean parts of the system.\nThe cleaning mode is often activated and deactivated with a cleaning key, so that the system cannot be switched on in other modes while the cleaning operators are cleaning the system.\nSpecial safety conditions apply in cleaning mode so that dangerous and exposed actuators are not activated during cleaning.\nOften the touch screen shows a cleaning icon and an alarm list only in this mode.\nNo other function should be available on the touchscreen so that the touchscreen can be cleaned safely.\nCleaning operators are normally not allowed operate other touchscreen functions.",
			"3"),
	/*
	 * 
	 */
	SERVICE("This mode allows authorized personnel, the ability to run an individual system independent of other systems in a production line.\nThis mode would typically be used for mechanical adjustments, system trials or testing operational improvements and is some cases can be used for fault finding.\nIt is expected that, because the system will perform its usual operations, it will need to undergo some or all its routine starting up procedures.\nAll or some operations in this mode may be on a 'hold-to-run' basis, so that the operator can stand closer to the system.\nThe system will often run with a reduced speed.\nThe safety systems often work somewhat different as in production mode, to enable specific maintenance work to be done.",
			"4");

	private final String description;
	private final String meynIconFontCharacter;

	private Mode(String description, String meynIconFontCharacter) {
		this.description = description;
		this.meynIconFontCharacter = meynIconFontCharacter;
	}

	public String getDisplayText() {
		StringBuilder displayText = new StringBuilder();
		displayText.append(name().substring(0, 1));
		displayText.append(name().substring(1).toLowerCase());
		return displayText.toString();
	}

	public String getDescription() {
		return description;
	}

	public String getMeynIconFontCharacter() {
		return meynIconFontCharacter;
	}

}
