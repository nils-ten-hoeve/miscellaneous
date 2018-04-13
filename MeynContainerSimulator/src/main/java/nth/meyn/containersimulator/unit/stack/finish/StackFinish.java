package nth.meyn.containersimulator.unit.stack.finish;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import nth.meyn.containersimulator.PresentationOwner;
import nth.meyn.containersimulator.layout.Layout;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.unit.StackDestination;
import nth.meyn.containersimulator.unit.Unit;

public class StackFinish extends Unit implements StackDestination, PresentationOwner {

	private Stack stack;
	private Text stateLabel;
	private Node guiPresentation;

	public StackFinish(Layout layout, String name, int xPos, int yPos, int rotation) {
		super(name, getStateClasses(), layout.getEventLog());
		guiPresentation = createGuiPresentation(xPos, yPos, rotation);
	}

	private static List<Class<? extends State>> getStateClasses() {
		List<Class<? extends State>> stateClasses = new ArrayList<>();
		stateClasses.add(StateWaitFeedIn.class);
		stateClasses.add(StateFeedIn.class);
		return stateClasses;
	}

	@Override
	public Stack getStack() {
		return stack;
	}

	@Override
	public void setStack(Stack newContainerStack) {
		stack = newContainerStack;
	}

	private Node createGuiPresentation(double xPos, double yPos, int rotation) {
		StackPane stackLoader = new StackPane();
		Background background = new Background(
				new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY));
		stackLoader.setBackground(background);
		stackLoader.setPrefWidth(90);
		stackLoader.setPrefHeight(70);

		Text nameLabel = new Text(getName());
		nameLabel.setTranslateY(-28);
		stackLoader.getChildren().add(nameLabel);

		stateLabel = new Text();
		updateStateLabel(getCurrentState());
		stateLabel.setTranslateY(28);
		stackLoader.getChildren().add(stateLabel);

		stackLoader.setLayoutX(xPos);
		stackLoader.setLayoutY(yPos);
		stackLoader.setRotate(rotation - 90);

		return stackLoader;
	}

	private void updateStateLabel(State stateToDisplay) {
		stateLabel.setText(stateToDisplay.getClass().getSimpleName().replace("State", ""));
	}

	@Override
	public Node getGuiPresentation() {
		return guiPresentation;
	}

	@Override
	public boolean isOkToFeedIn() {
		return getCurrentState().getClass() == StateWaitFeedIn.class;
	}

	@Override
	public Duration getVirtualFeedInTime() {
		return Duration.ZERO;
	}

	@Override
	public void onFeedInStarted() {
		StateWaitFeedIn stateWaitFeedIn = getState(StateWaitFeedIn.class);
		stateWaitFeedIn.onFeedInStarted();

	}

	@Override
	public void onFeedInCompleted() {
		StateFeedIn stateFeedIn = getState(StateFeedIn.class);
		stateFeedIn.onFeedInCompleted();
	}


}
