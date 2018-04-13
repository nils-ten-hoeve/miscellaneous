package nth.meyn.containersimulator.unit;

import java.util.List;

import nth.meyn.containersimulator.PresentationOwner;
import nth.meyn.containersimulator.eventlog.EventLog;
import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.statemachine.StateMachine;

public abstract class Unit extends StateMachine implements PresentationOwner {

	private final EventLog eventLog;

	public Unit(String name, List<Class<? extends State>> stateClasses, EventLog eventLog) {
		super(name, stateClasses);
		this.eventLog = eventLog;
	}
	
	@Override
	public void onStateChange(State currentState, State nextState) {
		super.onStateChange(currentState, nextState);
		eventLog.add(this, nextState);
		//TODO update state label
	}
	
}
