package nth.meyn.containersimulator.unit.turntable;

import java.time.Duration;

import javafx.scene.Node;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.unit.StackSource;

public class TurnTableStackSource implements StackSource {

	private final TurnTable turnTable;

	 public TurnTableStackSource(TurnTable turnTable) {
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
	public boolean isOkToFeedOut() {
		boolean waitFeedOutState = turnTable.getCurrentState().getClass()==StateTurnWaitFeedOut.class;
		if (!waitFeedOutState) {
			return false;
		}
		TurnPosition currentTurnPosition = turnTable.getCurrentTurnPosition();
		if (currentTurnPosition==null) {
			return false;
		}
		boolean thisTurnPosition=currentTurnPosition.getStackTransfer().getStackSource()==this;
		return thisTurnPosition;
	}

	@Override
	public Duration getVirtualFeedOutTime() {
		return turnTable.getVirtualTransportTime();
	}

	@Override
	public void onFeedOutStarted() {
		turnTable.onFeedOutStarted();
	}

	@Override
	public void onFeedOutCompleted() {
		turnTable.onFeedOutCompleted();
	}

	public TurnTable getTurnTable() {
		return turnTable;
	}


}
