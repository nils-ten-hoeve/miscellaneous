package nth.meyn.containersimulator.unit.turntable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import nth.meyn.containersimulator.PresentationOwner;
import nth.meyn.containersimulator.layout.Layout;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.timefactor.TimeFactor;
import nth.meyn.containersimulator.unit.StackPosition;
import nth.meyn.containersimulator.unit.Unit;

public class TurnTable extends Unit implements PresentationOwner, StackPosition {

	private Stack stack;
	private Text stateLabel;
	private TurnPosition currentTurnPosition;
	private final Node guiPresentation;
	private final List<TurnPosition> toTurnTablePositions;
	private final List<TurnPosition> fromTurnTablePositions;
	private final Duration virtualTransportTime;
	private final Duration virtualTurnTimeFullCycle;
	private TurnPosition goToTurnPosition;

	public TurnTable(Layout layout, String name, int xPos, int yPos, int rotation) {
		super(name, getStateClasses(), layout.getEventLog());
		TimeFactor timeFactor = layout.getTimeFactor();
		this.toTurnTablePositions = new ArrayList<>();
		this.fromTurnTablePositions = new ArrayList<>();
		this.guiPresentation = createGuiPresentation(xPos, yPos, rotation);
		this.virtualTransportTime = timeFactor.getVirtualDuration(Duration.ofSeconds(14));
		this.virtualTurnTimeFullCycle = timeFactor.getVirtualDuration(Duration.ofSeconds(4*7));//7 sec for 90 degrees
	}

	/**
	 * Adds a {@link TurnPosition} after the creation of the {@link TurnTable}
	 * and {@link StackTransfer} Important note: The order of adding is the
	 * order of priority!!!
	 */
	public void addToTurnTablePositions(StackTransfer stackTransfer) {
		TurnPosition turnPosition = new TurnPosition(stackTransfer, Direction.TO_TURN_TABLE);
		toTurnTablePositions.add(turnPosition);
	}

	/**
	 * Adds a {@link TurnPosition} after the creation of the {@link TurnTable}
	 * and {@link StackTransfer} Important note: The order of adding is the
	 * order of priority!!!
	 */
	public void addFromTurnTablePositions(StackTransfer stackTransfer) {
		TurnPosition turnPosition = new TurnPosition(stackTransfer, Direction.FROM_TURN_TABLE);
		fromTurnTablePositions.add(turnPosition);
	}
	
	private static List<Class<? extends State>> getStateClasses() {
		List<Class<? extends State>> stateClasses = new ArrayList<>();
		stateClasses.add(StateTurnWaitFeedIn.class);
		stateClasses.add(StateFeedIn.class);
		stateClasses.add(StateTurnWaitFeedOut.class);
		stateClasses.add(StateFeedOut.class);
		return stateClasses;
	}

	private Node createGuiPresentation(int xPos, int yPos, int rotation) {
		StackPane turnTable = new StackPane();
		
		Circle circle = new Circle(50);
		circle.setFill(Color.LIGHTGRAY);
		turnTable.getChildren().add(circle);

		Text nameLabel = new Text(getName());
		nameLabel.setTranslateY(-28);
		turnTable.getChildren().add(nameLabel);

		stateLabel = new Text();
		updateStateLabel(getCurrentState());
		stateLabel.setTranslateY(28);
		turnTable.getChildren().add(stateLabel);

		turnTable.setLayoutX(xPos);
		turnTable.setLayoutY(yPos);
		turnTable.setRotate(rotation - 90);

		return turnTable;
	}

	private void updateStateLabel(State stateToDisplay) {
		stateLabel.setText(stateToDisplay.getClass().getSimpleName().replace("State", ""));
	}

	@Override
	public void onStateChange(State currentState, State nextState) {
		super.onStateChange(currentState, nextState);
		updateStateLabel(nextState);
	}

	public TurnPosition getCurrentTurnPosition() {
		return currentTurnPosition;
	}

	@Override
	public Node getGuiPresentation() {
		return guiPresentation;
	}

	public List<TurnPosition> getToTurnTablePositions() {
		return toTurnTablePositions;
	}

	public List<TurnPosition> getFromTablePositions() {
		return fromTurnTablePositions;
	}

	public Stack getStack() {
		return stack;
	}

	public void setStack(Stack stack) {
		this.stack = stack;

	}

	public Duration getVirtualTransportTime() {
		return virtualTransportTime;
	}

	public Duration getVirtualTurnTimeFullCycle() {
		return virtualTurnTimeFullCycle;
	}

	
	
	public TurnTableStackSource createStackSource() {
		TurnTableStackSource stackSource = new TurnTableStackSource(this);
		return stackSource;
	}

	public TurnTableStackDestination createStackDestination() {
		TurnTableStackDestination stackDestintion = new TurnTableStackDestination(this);
		return stackDestintion;
	}

	public void onFeedInStarted() {
		StateTurnWaitFeedIn stateWaitFeedIn = getState(StateTurnWaitFeedIn.class);
		stateWaitFeedIn.onFeedInStarted();
	}

	public void onFeedInCompleted() {
		StateFeedIn stateFeedIn = getState(StateFeedIn.class);
		stateFeedIn.onFeedInCompleted();
	}

	public void onFeedOutStarted() {
		StateTurnWaitFeedOut stateWaitFeedOut = getState(StateTurnWaitFeedOut.class);
		stateWaitFeedOut.onFeedOutStarted();
	}

	public void onFeedOutCompleted() {
		StateFeedOut stateFeedOut = getState(StateFeedOut.class);
		stateFeedOut.onFeedOutCompleted();
	}

	public void setCurrentTurnPosition(TurnPosition newTurnPosition) {
		this.currentTurnPosition=newTurnPosition;
	}

	public TurnPosition getGoToTurnPosition() {
		return goToTurnPosition;
	}

	public void setGoToTurnPosition(TurnPosition goToTurnPosition) {
		this.goToTurnPosition = goToTurnPosition;
	}


	
}
