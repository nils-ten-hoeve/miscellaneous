package nth.meyn.containersimulator.unit.cas;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitFeedOut extends CasState {

	private boolean feedOutStarted;

	public StateWaitFeedOut(Cas cas) {
		super(cas);
	}

	@Override
	public void onStart() {
		feedOutStarted = false;
	}

	public void onFeedOutStarted() {
		feedOutStarted = true;
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (feedOutStarted) {
			return StateFeedOut.class;
		}
		return null;
	}

}
