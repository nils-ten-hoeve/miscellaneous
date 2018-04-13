package nth.meyn.containersimulator.unit.cas;

import java.time.Duration;

import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.timer.Timer;

public class StateStunStage4 extends CasState {


	private boolean stunStageCompleted;
	private final Cas cas;

	public StateStunStage4(Cas cas) {
		super(cas);
		this.cas=cas;
	}

	
	@Override
	public void onStart() {
		stunStageCompleted=false;
		Duration duration=cas.getVirtualStage4Time();
		new Timer(duration, this::onStunStageCompleted );
	}

	public void onStunStageCompleted() {
		stunStageCompleted=true;
		
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (stunStageCompleted) {
			return StateStunStage5.class;
		}
		return null;
	}

	

}
