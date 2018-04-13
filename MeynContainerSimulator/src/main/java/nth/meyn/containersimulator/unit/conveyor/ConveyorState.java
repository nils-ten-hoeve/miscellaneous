package nth.meyn.containersimulator.unit.conveyor;

import nth.meyn.containersimulator.statemachine.State;

public abstract class ConveyorState extends State {

	public ConveyorState(Conveyor conveyor) {
		super(conveyor);
	}
}
