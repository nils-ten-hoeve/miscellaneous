package nth.meyn.containersimulator.unit.stack.supply;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitFeedOut extends StackSupplyState {


	private boolean feedOutStarted;

	public StateWaitFeedOut(StackSupply stackSupply) {
		super(stackSupply);
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
			return StateFeedOut.class;
		}
		return null;
	}

	

}
