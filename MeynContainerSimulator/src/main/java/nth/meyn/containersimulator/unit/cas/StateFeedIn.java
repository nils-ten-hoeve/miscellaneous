package nth.meyn.containersimulator.unit.cas;

import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateFeedIn extends CasState {

	private boolean feedInCompleted;
	private Cas cas;

	public StateFeedIn(Cas cas) {
		super(cas);
		this.cas = cas;
	}

	/**
	 * Does almost nothing, action is performed by {@link StackTransfer}
	 */
	@Override
	public void onStart() {
		feedInCompleted=false;
	}

	@Override
	public void onExit() {
		cas.onStackLoaded();
	}
	
	public void onFeedInCompleted() {
		feedInCompleted=true;
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (feedInCompleted) {
			return StateWaitStartStun.class;
		} else {
			return null;
		}
	}

}
