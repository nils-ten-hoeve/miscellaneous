package nth.meyn.containersimulator.unit.conveyor;

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
import nth.meyn.containersimulator.timefactor.TimeFactor;
import nth.meyn.containersimulator.unit.StackDestination;
import nth.meyn.containersimulator.unit.StackSource;
import nth.meyn.containersimulator.unit.Unit;

public class Conveyor extends Unit implements PresentationOwner, StackSource, StackDestination {

	private Stack stack;
	private Text stateLabel;
	private final Node guiPresentation;
	private final Duration virtualFeedInTime;
	private final Duration virtualFeedOutTime;

	public Conveyor(Layout layout, String name, int xPos, int yPos, int rotation) {
		super(name, getStateClasses(), layout.getEventLog());
		TimeFactor timeFactor = layout.getTimeFactor();
		this.guiPresentation = createGuiPresentation(xPos, yPos, rotation);
		this.virtualFeedInTime = timeFactor.getVirtualDuration(Duration.ofSeconds(14));
		this.virtualFeedOutTime = timeFactor.getVirtualDuration(Duration.ofSeconds(14));
	}

	private static List<Class<? extends State>> getStateClasses() {
		List<Class<? extends State>> stateClasses = new ArrayList<>();
		stateClasses.add(StateWaitFeedIn.class);
		stateClasses.add(StateFeedIn.class);
		stateClasses.add(StateWaitFeedOut.class);
		stateClasses.add(StateFeedOut.class);
		stateClasses.add(StateFeedOutAndFeedIn.class);
		return stateClasses;
	}

	@Override
	public boolean isOkToFeedOut() {
		return stack != null && getCurrentState().getClass() == StateWaitFeedOut.class;
	}

	@Override
	public boolean isOkToFeedIn() {
		return stack == null && getCurrentState().getClass() == StateWaitFeedIn.class;
	}

	@Override
	public Stack getStack() {
		return stack;
	}

	@Override
	public void setStack(Stack stack) {
		this.stack = stack;
	}

	@Override
	public Duration getVirtualFeedInTime() {
		return virtualFeedInTime;
	}

	@Override
	public Duration getVirtualFeedOutTime() {
		return virtualFeedOutTime;
	}

	@Override
	public Node getGuiPresentation() {
		return guiPresentation;
	}

	private Node createGuiPresentation(int xPos, int yPos, int rotation) {
		StackPane conveyor = new StackPane();
		Background background = new Background(
				new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY));
		conveyor.setBackground(background);
		conveyor.setPrefWidth(90);
		conveyor.setPrefHeight(70);

		Text nameLabel = new Text(getName());
		nameLabel.setTranslateY(-28);
		conveyor.getChildren().add(nameLabel);

		stateLabel = new Text();
		updateStateLabel(getCurrentState());
		stateLabel.setTranslateY(28);
		conveyor.getChildren().add(stateLabel);

		conveyor.setLayoutX(xPos);
		conveyor.setLayoutY(yPos);
		conveyor.setRotate(rotation - 90);

		return conveyor;
	}

	private void updateStateLabel(State stateToDisplay) {
		stateLabel.setText(stateToDisplay.getClass().getSimpleName().replace("State", ""));
	}

	@Override
	public void onStateChange(State currentState, State nextState) {
		super.onStateChange(currentState, nextState);
		updateStateLabel(nextState);
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

	@Override
	public void onFeedOutStarted() {
		StateWaitFeedOut stateWaitFeedOut = getState(StateWaitFeedOut.class);
		stateWaitFeedOut.onFeedOutStarted();
	}

	@Override
	public void onFeedOutCompleted() {
		StateFeedOut stateFeedOut = getState(StateFeedOut.class);
		stateFeedOut.onFeedOutCompleted();
	}

}
