package nth.meyn.containersimulator.unit.turntable;

import nth.meyn.containersimulator.statemachine.State;

public abstract class TurnTableState extends State {

	public TurnTableState(TurnTable turnTable) {
		super(turnTable);
	}
}
