package nth.meyn.containersimulator.unit.conveyor;

import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateFeedIn extends ConveyorState {

	private boolean feedInCompleted;

	public StateFeedIn(Conveyor conveyor) {
		super(conveyor);
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
			return StateWaitFeedOut.class;
		} else {
			return null;
		}
	}

}
