package nth.meyn.containersimulator.unit.turntable;

import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateFeedIn extends TurnTableState {

	private boolean feedInCompleted;

	public StateFeedIn(TurnTable turnTable) {
		super(turnTable);
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
			return StateTurnWaitFeedOut.class;
		} else {
			return null;
		}
	}

}
