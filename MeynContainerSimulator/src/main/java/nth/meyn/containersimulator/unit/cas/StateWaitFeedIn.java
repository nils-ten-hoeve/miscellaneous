package nth.meyn.containersimulator.unit.cas;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitFeedIn extends CasState {


	private boolean feedInStarted;

	public StateWaitFeedIn(Cas cas) {
		super(cas);
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
