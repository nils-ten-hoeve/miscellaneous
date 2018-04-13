package nth.meyn.containersimulator.unit.stack.finish;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitFeedIn extends StackFinishState {


	private boolean feedInStarted;

	public StateWaitFeedIn(StackFinish stackFinish) {
		super(stackFinish);
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
