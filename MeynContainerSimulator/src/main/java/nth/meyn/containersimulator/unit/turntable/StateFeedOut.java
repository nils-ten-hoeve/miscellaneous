package nth.meyn.containersimulator.unit.turntable;

import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateFeedOut extends TurnTableState {

	private boolean feedOutCompleted;
	
	public StateFeedOut(TurnTable turnTable) {
		super(turnTable);
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
			return StateTurnWaitFeedIn.class;
		}
		return null;
	}

}
