package nth.meyn.containersimulator.unit.turntable;

import java.util.List;

import javafx.animation.RotateTransition;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateTurnWaitFeedOut extends TurnTableState {

	private boolean feedOutStarted;
	private TurnTable turnTable;

	public StateTurnWaitFeedOut(TurnTable turnTable) {
		super(turnTable);
		this.turnTable = turnTable;
	}

	@Override
	public void onStart() {
		feedOutStarted = false;
	}

	public void onFeedOutStarted() {
		feedOutStarted = true;
	}

	@Override
	public Class<? extends State> getNextStateClass() {

		if (feedOutStarted) {
			return StateFeedOut.class;
		}
		
		TurnPosition goToTurnPosition=turnTable.getGoToTurnPosition();
		TurnPosition newGoToTurnPosition = getGoToTurnPosition();
		if (goToTurnPosition !=newGoToTurnPosition) {
			System.out.println(":::"+turnTable.getName()+":"+newGoToTurnPosition);

			turnTable.setGoToTurnPosition(newGoToTurnPosition);
			onRotateAnnimationStart(newGoToTurnPosition);
		}
		
		return null;
	}

	private void onRotateAnnimationStart(TurnPosition newGoToPosition) {
		double currentRotation = turnTable.getGuiPresentation().getRotate();
		double toBeRotation = newGoToPosition.getRotation();
		double rotationPercentage = (Math.abs(currentRotation - toBeRotation) % 360) / 360;
		int durationInMillis = (int) (turnTable.getVirtualTurnTimeFullCycle().toMillis() * rotationPercentage);
		
		startRotateTurnTableAnimation(newGoToPosition, toBeRotation, durationInMillis);

		startRotateStackAnimation(newGoToPosition, toBeRotation, durationInMillis);
	}

	private void startRotateStackAnimation(TurnPosition newGoToPosition, double toBeRotation,
			int durationInMillis) {
		Stack stack = turnTable.getStack();
		if (stack != null) {
			RotateTransition rotateStackTransition = new RotateTransition();
			rotateStackTransition.setNode(stack.getGuiPresentation());
			rotateStackTransition.setToAngle(toBeRotation);
			rotateStackTransition.setDuration(javafx.util.Duration.millis(durationInMillis));
			rotateStackTransition.setOnFinished(event -> {
				turnTable.setCurrentTurnPosition(newGoToPosition);
			});
			rotateStackTransition.play();
		}
	}

	private void startRotateTurnTableAnimation(TurnPosition newGoToPosition, double toBeRotation,
			int durationInMillis) {
		RotateTransition rotateTurnTableTransition = new RotateTransition();
		rotateTurnTableTransition.setNode(turnTable.getGuiPresentation());
		rotateTurnTableTransition.setToAngle(toBeRotation);
		rotateTurnTableTransition.setDuration(javafx.util.Duration.millis(durationInMillis));
		rotateTurnTableTransition.setOnFinished(event -> {
			turnTable.setCurrentTurnPosition(newGoToPosition);
		});
		rotateTurnTableTransition.play();
	}
	private TurnPosition getGoToTurnPosition() {
		
		List<TurnPosition> fromTurnTablePositions= turnTable.getFromTablePositions();

		//Destination is ready to move
		for (TurnPosition turnPosition : fromTurnTablePositions) {
			StackTransfer stackTransfer=turnPosition.getStackTransfer();
			if ( stackTransfer.isOkToFeedIn() && stackTransfer.isRouteOk()) {
				return turnPosition;
			}
		}

//		//Destination is almost ready to move
//		for (TurnPosition turnPosition : fromTurnTablePositions) {
//			StackTransfer stackTransfer=turnPosition.getStackTransfer();
//			if (  stackTransfer.isRouteOk()) {
//				return turnPosition;
//			}
//		}

return turnTable.getGoToTurnPosition();
//		
//		if (fromTurnTablePositions.isEmpty()) {
//			throw new RuntimeException("No "+StackTransfer.class.getSimpleName()+" created to transfer a container from "+turnTable.getName());
//		} else {
//			TurnPosition turnPostion = fromTurnTablePositions.get(0);
//			return turnPostion;
//		}
	}

	

}
