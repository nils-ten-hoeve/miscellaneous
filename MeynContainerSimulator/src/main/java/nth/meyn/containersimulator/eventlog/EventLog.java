package nth.meyn.containersimulator.eventlog;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.statemachine.StateMachine;
import nth.meyn.containersimulator.timefactor.TimeFactor;

public class EventLog extends ArrayList<Event> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3588253002254791276L;
	private final LocalDateTime startTime;
	private final TimeFactor timeFactor;

	public EventLog(TimeFactor timeFactor) {
		this.timeFactor = timeFactor;
		this.startTime = LocalDateTime.now(); 
	}

	public void add(StateMachine stateMachine, State newState) {
		String name=stateMachine.getName();
		String type=stateMachine.getClass().getSimpleName();
		String state=newState.getClass().getSimpleName();
		Duration durationSinceStart=timeFactor.getRealDuration(startTime);
		
		Event event=new Event(durationSinceStart, name, type, state);
		add(event);
	}
	
}
