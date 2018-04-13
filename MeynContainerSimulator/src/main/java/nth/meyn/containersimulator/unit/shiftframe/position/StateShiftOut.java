package nth.meyn.containersimulator.unit.shiftframe.position;

import java.time.Duration;


import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.timefactor.TimeFactor;
import nth.meyn.containersimulator.timer.Timer;

public class StateShiftOut extends ShiftFrameState {

	private ShiftFrame shiftFrame;
	private boolean shiftFrameIsOut;

	public StateShiftOut(ShiftFrame shiftFrame) {
		super(shiftFrame);
		this.shiftFrame = shiftFrame;
	}

	@Override
	public void onStart() {
		shiftFrameIsOut=false;
		Duration duration = shiftFrame.getVirtualShiftOutTime();
		new Timer(duration,this::onShiftFrameOut);
	}
	
	public void onShiftFrameOut(){
		shiftFrameIsOut=true;
	}
	
	@Override
	public Class<? extends State> getNextStateClass() {
		if (shiftFrameIsOut) {
			return StateShiftIn.class;
		}
		return null;
	}

}
