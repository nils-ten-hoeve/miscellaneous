package nth.meyn.containersimulator.unit.stack.finish;

import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateFeedIn extends StackFinishState {

	private boolean feedInCompleted;
	
	public StateFeedIn(StackFinish stackFinish) {
		super(stackFinish);
	}

	/**
	 * Does almost nothing, action is performed by {@link StackTransfer}
	 */
	@Override
	public void onStart() {
		feedInCompleted=false;
	}
	
	public void onFeedInCompleted() {
		feedInCompleted=true;		
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (feedInCompleted) {
			return StateWaitFeedIn.class;
		}
		return null;
	}


}
