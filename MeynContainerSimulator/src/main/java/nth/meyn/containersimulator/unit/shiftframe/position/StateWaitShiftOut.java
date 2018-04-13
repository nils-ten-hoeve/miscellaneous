package nth.meyn.containersimulator.unit.shiftframe.position;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitShiftOut extends ShiftFrameState {

	private boolean shiftOut;

	public StateWaitShiftOut(ShiftFrame shiftFrame) {
		super(shiftFrame);
	}

	@Override
	public void onStart() {
		shiftOut=false;
	}
	
	/**
	 * This method is called from {@link ShiftFrame} 
	 */
	public void onShiftOut() {
		shiftOut=true;
	}
	
	@Override
	public Class<? extends State> getNextStateClass() {
		//when there are containers to move and all shift frame positions are free of the shift frame (cross over and (de)stackers)
		if(shiftOut) {
			return StateShiftOut.class;
		}
		return null;
	}

}
