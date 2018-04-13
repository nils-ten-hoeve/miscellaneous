package nth.meyn.containersimulator.stack.transfer;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.unit.StackDestination;
import nth.meyn.containersimulator.unit.StackSource;

public class StateTransfer extends StackTransferState {

	private boolean isCompleted;
	private final StackTransfer stackTransfer;

	public StateTransfer(StackTransfer stackTransfer) {
		super(stackTransfer);
		this.stackTransfer = stackTransfer;
	}

	@Override
	public void onStart() {
		isCompleted = false;
		
		StackSource source = stackTransfer.getStackSource();
		StackDestination destination = stackTransfer.getStackDestination();
		source.onFeedOutStarted();
		destination.onFeedInStarted();
		startAnnimation();
	}

	@Override
	public void onExit() {
		transferStackBetweenUnits();
		
		StackSource source = stackTransfer.getStackSource();
		StackDestination destination = stackTransfer.getStackDestination();		
		source.onFeedOutCompleted();
		destination.onFeedInCompleted();
	}

	private void startAnnimation() {
		StackSource source = stackTransfer.getStackSource();
		StackDestination destination = stackTransfer.getStackDestination();

		long durationInMilliSeconds = Math.max(source.getVirtualFeedOutTime().toMillis(),
				destination.getVirtualFeedInTime().toMillis());

		Stack stack = source.getStack();
		Node stackPresentaion = stack.getGuiPresentation();

		TranslateTransition transition = new TranslateTransition(
				Duration.millis(durationInMilliSeconds), stackPresentaion);

		Node sourcePresentation = source.getGuiPresentation();
		Node destinationPresentation = destination.getGuiPresentation();

		Bounds sourceBounds = sourcePresentation
				.localToScene(sourcePresentation.getBoundsInLocal());
		Bounds destinationBounds = destinationPresentation
				.localToScene(destinationPresentation.getBoundsInLocal());

		transition.setFromX(sourceBounds.getMinX()-10);
		transition.setFromY(sourceBounds.getMinY()-100);
		transition.setToX(destinationBounds.getMinX()-10);
		transition.setToY(destinationBounds.getMinY()-100);

		transition.onFinishedProperty().set(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				isCompleted = true;
			}
		});
		;

		transition.play();
	}

	private void transferStackBetweenUnits() {
		StackSource source = stackTransfer.getStackSource();
		StackDestination destination = stackTransfer.getStackDestination();
		
		Stack stack = source.getStack();
		source.setStack(null);
		destination.setStack(stack);
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (isCompleted) {
			return StateWaitForTransfer.class;
		}
		return null;
	}

}
