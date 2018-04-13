package nth.meyn.containersimulator.unit.turntable;

import java.util.List;

import javafx.animation.RotateTransition;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;

public class StateTurnWaitFeedIn extends TurnTableState {

	private boolean feedInStarted;
	private TurnTable turnTable;

	public StateTurnWaitFeedIn(TurnTable turnTable) {
		super(turnTable);
		this.turnTable = turnTable;
	}

	@Override
	public void onStart() {
		feedInStarted = false;
	}

	public void onFeedInStarted() {
		feedInStarted = true;
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (feedInStarted) {
			return StateFeedIn.class;
		}

		TurnPosition goToTurnPosition=turnTable.getGoToTurnPosition();
		TurnPosition newGoToPosition = getGoToTurnPosition();
		if (goToTurnPosition != newGoToPosition) {
			turnTable.setGoToTurnPosition(newGoToPosition);
			onRotateAnnimationStart(newGoToPosition);
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

		List<TurnPosition> toTurnTablePositions = turnTable.getToTurnTablePositions();

		// Source is ready to move
		for (TurnPosition turnPosition : toTurnTablePositions) {
			StackTransfer stackTransfer = turnPosition.getStackTransfer();
			if (stackTransfer.isOkToFeedOut() && stackTransfer.isRouteOk()) {
				return turnPosition;
			}
		}

		// Source is almost ready to move
		for (TurnPosition turnPosition : toTurnTablePositions) {
			StackTransfer stackTransfer = turnPosition.getStackTransfer();
			if (stackTransfer.isRouteOk()) {
				return turnPosition;
			}
		}

		// If nothing is ready to move: turn to priority 1
		if (toTurnTablePositions.isEmpty()) {
			throw new RuntimeException("No " + StackTransfer.class.getSimpleName()
					+ " created to transfer a container to " + turnTable.getName());
		} else {
			TurnPosition turnPostion = toTurnTablePositions.get(0);
			return turnPostion;
		}
	}

}
