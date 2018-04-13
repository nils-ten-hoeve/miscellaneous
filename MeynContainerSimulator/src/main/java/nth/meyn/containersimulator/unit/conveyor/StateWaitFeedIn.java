package nth.meyn.containersimulator.unit.conveyor;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitFeedIn extends ConveyorState {


	private boolean feedInStarted;

	public StateWaitFeedIn(Conveyor conveyor) {
		super(conveyor);
	}

	
	
	@Override
	public void onStart() {
		feedInStarted=false;
	}

	public void onFeedInStarted() {
		feedInStarted=true;
		
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (feedInStarted) {
			return StateFeedIn.class;
		}
		return null;
	}

	

}
