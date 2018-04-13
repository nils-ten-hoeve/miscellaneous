package nth.meyn.containersimulator.unit.shiftframe.position;

import java.time.Duration;

import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.timer.Timer;

public class StateShiftIn extends ShiftFrameState {

	private ShiftFrame shiftFrame;
	private boolean shiftFrameIsIn;

	public StateShiftIn(ShiftFrame shiftFrame) {
		super(shiftFrame);
		this.shiftFrame = shiftFrame;
	}

	@Override
	public void onStart() {
		shiftFrameIsIn=false;
		Duration duration = shiftFrame.getVirtualShiftInTime();
		new Timer(duration,this::onShiftFrameOut);
	}
	
	public void onShiftFrameOut(){
		shiftFrameIsIn=true;
	}
	
	@Override
	public Class<? extends State> getNextStateClass() {
		if (shiftFrameIsIn) {
			return StateWaitShiftOut.class;
		}
		return null;
	}

}
