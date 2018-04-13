package nth.meyn.containersimulator.eventlog;

import java.time.Duration;

public class Event {
	private final String name;
	private final String type;
	private final String state;
	private final Duration durationSinceStart;
	
	public Event(Duration durationSinceStart, String name, String type, String state) {
		this.durationSinceStart = durationSinceStart;
		this.name = name;
		this.type = type;
		this.state = state;
	}

	
	public Duration getDurationSinceStart() {
		return durationSinceStart;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getState() {
		return state;
	}
	
	@Override
	public String toString() {
		StringBuilder message=new StringBuilder();
		message.append(durationSinceStart);
		message.append(": ");
		message.append(name);
		message.append(" - ");
		message.append(type);
		message.append(": ");
		message.append(state);
		return message.toString();
	}


}
