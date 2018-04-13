package nth.meyn.containersimulator.unit.conveyor;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitFeedOut extends ConveyorState {


	private boolean feedOutStarted;

	public StateWaitFeedOut(Conveyor conveyor) {
		super(conveyor);
	}

@Override
public void onStart() {
feedOutStarted=false;
}
	
	public void onFeedOutStarted() {
		feedOutStarted=true;
	}

	
	@Override
	public Class<? extends State> getNextStateClass() {
		if (feedOutStarted) {
				//TODO	return StateFeedOutAndFeedIn.class;
				return StateFeedOut.class;
		}
		return null;
	}

}
