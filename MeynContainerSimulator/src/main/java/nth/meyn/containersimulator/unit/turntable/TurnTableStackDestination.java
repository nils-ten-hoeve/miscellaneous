package nth.meyn.containersimulator.unit.turntable;

import java.time.Duration;

import javafx.scene.Node;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.unit.StackDestination;

public class TurnTableStackDestination implements StackDestination {

	private final TurnTable turnTable;

	 public TurnTableStackDestination(TurnTable turnTable) {
		this.turnTable = turnTable;
	}
	 
	@Override
	public Stack getStack() {
		return turnTable.getStack();
	}

	@Override
	public void setStack(Stack stack) {
		turnTable.setStack(stack);
	}

	@Override
	public String getName() {
		return turnTable.getName();
	}

	@Override
	public Node getGuiPresentation() {
		return turnTable.getGuiPresentation();
	}



	@Override
	public boolean isOkToFeedIn() {
		boolean waitFeedInState = turnTable.getCurrentState().getClass()==StateTurnWaitFeedIn.class;
		if (!waitFeedInState) {
			return false;
		}
		TurnPosition currentTurnPosition=turnTable.getCurrentTurnPosition();
		if (currentTurnPosition==null) {
			return false;
		}
		boolean thisTurnPosition=currentTurnPosition.getStackTransfer().getStackDestination()==this;
		return thisTurnPosition;
	}

	@Override
	public Duration getVirtualFeedInTime() {
		return turnTable.getVirtualTransportTime();
	}

	@Override
	public void onFeedInStarted() {
		turnTable.onFeedInStarted();
	}

	@Override
	public void onFeedInCompleted() {
		turnTable.onFeedInCompleted();		
	}

	public TurnTable getTurnTable() {
		return turnTable;
	}


}
