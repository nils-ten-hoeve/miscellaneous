package nth.meyn.containersimulator.unit.cas;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitStartStun extends CasState {


	private boolean startStun;

	public StateWaitStartStun(Cas cas) {
		super(cas);
	}

	
	@Override
	public void onStart() {
		startStun=false;
	}

	public void onStartStun() {
		startStun=true;
		
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (startStun) {
			return StateStunStage1.class;
		}
		return null;
	}

	

}
