package nth.meyn.containersimulator.unit.cas;

import java.time.Duration;

import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.timer.Timer;

public class StateStunStage5 extends CasState {


	private boolean stunStageCompleted;
	private final Cas cas;

	public StateStunStage5(Cas cas) {
		super(cas);
		this.cas=cas;
	}

	
	@Override
	public void onStart() {
		stunStageCompleted=false;
		Duration duration=cas.getVirtualStage5Time();
		new Timer(duration, this::onStunStageCompleted );
	}


	public void onStunStageCompleted() {
		stunStageCompleted=true;
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (stunStageCompleted) {
			return StateExhaust.class;
		}
		return null;
	}

	

}
