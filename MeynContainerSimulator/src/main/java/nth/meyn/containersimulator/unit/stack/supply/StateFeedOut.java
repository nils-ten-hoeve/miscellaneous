package nth.meyn.containersimulator.unit.stack.supply;

import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateFeedOut extends StackSupplyState {

	private boolean feedOutCompleted;
	
	public StateFeedOut(StackSupply stackSupply) {
		super(stackSupply);
	}

	/**
	 * Does almost nothing, action is performed by {@link StackTransfer}
	 */
	@Override
	public void onStart() {
		feedOutCompleted=false;
	}
	
	public void onFeedOutCompleted() {
		feedOutCompleted=true;
	}
	
	@Override
	public Class<? extends State> getNextStateClass() {
		if (feedOutCompleted) {
			return StateWaitFeedOut.class;
		}
		return null;
	}


	

}
