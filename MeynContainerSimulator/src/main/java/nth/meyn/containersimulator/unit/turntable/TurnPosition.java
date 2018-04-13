package nth.meyn.containersimulator.unit.turntable;

import nth.meyn.containersimulator.PresentationOwner;
import nth.meyn.containersimulator.stack.transfer.StackTransfer;

public class TurnPosition {
	private final StackTransfer stackTransfer;
	private Direction direction;

	public TurnPosition(StackTransfer stackTransfer, Direction direction) {
		this.stackTransfer = stackTransfer;
		this.direction = direction;
	}

	public double getRotation() {
		if (direction==Direction.TO_TURN_TABLE) {
			return getRotationOf(stackTransfer.getStackSource());	
		} else {
			return getRotationOf(stackTransfer.getStackDestination());
		}
	}

	private double getRotationOf(PresentationOwner presentationOwner) {
		if (presentationOwner instanceof TurnTableStackSource || presentationOwner instanceof TurnTableStackDestination) {
			return 0;//TO DO get initial rotation from turn table
		} else {
			return presentationOwner.getGuiPresentation().getRotate();
		}
	}

	public StackTransfer getStackTransfer() {
		return stackTransfer;
	}

	@Override
	public String toString() {
		StringBuilder text=new StringBuilder();
		text.append(direction);
		text.append(" - ");
		text.append(stackTransfer);
		return text.toString();
	}

}
