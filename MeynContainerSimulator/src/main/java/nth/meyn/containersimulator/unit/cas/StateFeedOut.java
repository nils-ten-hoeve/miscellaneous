package nth.meyn.containersimulator.unit.cas;

import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateFeedOut extends CasState {

	private boolean feedOutCompleted;
	
	public StateFeedOut(Cas cas) {
		super(cas);
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
			return StateWaitFeedIn.class;
		}
		return null;
	}

}
