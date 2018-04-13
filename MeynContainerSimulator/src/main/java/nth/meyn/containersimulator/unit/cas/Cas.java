package nth.meyn.containersimulator.unit.cas;

import java.time.Duration;
import java.time.LocalDateTime;
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

public class Cas extends Unit implements PresentationOwner, StackSource, StackDestination {

	private Stack stack;
	private Text stateLabel;
	private final Node guiPresentation;
	private LocalDateTime stackLoadDateTime;
	private final Duration virtualFeedInTime;
	private final Duration virtualFeedOutTime;
	private final Duration virtualStage1Time;
	private final Duration virtualStage2Time;
	private final Duration virtualStage3Time;
	private final Duration virtualStage4Time;
	private final Duration virtualStage5Time;
	private final Duration virtualExhaustTime;
	private final Layout layout;

	public Cas(Layout layout, String name, int xPos, int yPos, int rotation) {
		super(name, getStateClasses(), layout.getEventLog());
		this.layout = layout;
		TimeFactor timeFactor = layout.getTimeFactor();
		this.guiPresentation = createGuiPresentation(xPos, yPos, rotation);
		this.virtualFeedInTime=timeFactor.getVirtualDuration(Duration.ofSeconds(18));
		this.virtualFeedOutTime=timeFactor.getVirtualDuration(Duration.ofSeconds(18));
		this.virtualStage1Time=timeFactor.getVirtualDuration(Duration.ofSeconds(60));
		this.virtualStage2Time=timeFactor.getVirtualDuration(Duration.ofSeconds(60));
		this.virtualStage3Time=timeFactor.getVirtualDuration(Duration.ofSeconds(60));
		this.virtualStage4Time=timeFactor.getVirtualDuration(Duration.ofSeconds(60));
		this.virtualStage5Time=timeFactor.getVirtualDuration(Duration.ofSeconds(120));
		this.virtualExhaustTime=timeFactor.getVirtualDuration(Duration.ofSeconds(30));
	}

	private static List<Class<? extends State>> getStateClasses() {
		List<Class<? extends State>> stateClasses = new ArrayList<>();
		stateClasses.add(StateWaitFeedIn.class);
		stateClasses.add(StateFeedIn.class);
		stateClasses.add(StateWaitStartStun.class);
		stateClasses.add(StateStunStage1.class);
		stateClasses.add(StateStunStage2.class);
		stateClasses.add(StateStunStage3.class);
		stateClasses.add(StateStunStage4.class);
		stateClasses.add(StateStunStage5.class);
		stateClasses.add(StateExhaust.class);
		stateClasses.add(StateWaitFeedOut.class);
		stateClasses.add(StateFeedOut.class);
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
		// including doors opening and closing
	}

	@Override
	public Duration getVirtualFeedOutTime() {
		return virtualFeedOutTime;
		// including doors opening and closing
	}

	@Override
	public Node getGuiPresentation() {
		return guiPresentation;
	}

	private Node createGuiPresentation(int xPos, int yPos, int rotation) {
		StackPane cas = new StackPane();
		Background background = new Background(
				new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY));
		cas.setBackground(background);
		cas.setPrefWidth(90);
		cas.setPrefHeight(80);

		Text nameLabel = new Text(getName());
		nameLabel.setTranslateY(-28);
		cas.getChildren().add(nameLabel);

		stateLabel = new Text();
		updateStateLabel(getCurrentState());
		stateLabel.setTranslateY(28);
		cas.getChildren().add(stateLabel);

		cas.setLayoutX(xPos);
		cas.setLayoutY(yPos);
		cas.setRotate(rotation-90);

		return cas;
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

	public void onStartStun() {
		StateWaitStartStun stateWaitStartStun = getState(StateWaitStartStun.class);
		stateWaitStartStun.onStartStun();
	}

	public boolean isWaitingForStart() {
		return getCurrentState().getClass()==StateWaitStartStun.class;
	}

	public LocalDateTime getStackLoadDateTime() {
		return stackLoadDateTime;
	}

	public void onStackLoaded() {
		this.stackLoadDateTime=LocalDateTime.now();		
	}

	public Duration getVirtualStage1Time() {
		return virtualStage1Time;
	}

	public Duration getVirtualStage2Time() {
		return virtualStage2Time;
	}

	public Duration getVirtualStage3Time() {
		return virtualStage3Time;
	}

	public Duration getVirtualStage4Time() {
		return virtualStage4Time;
	}

	public Duration getVirtualStage5Time() {
		return virtualStage5Time;
	}

	public Duration getVirtualExhaustTime() {
		return virtualExhaustTime;
	}

	public Layout getLayout() {
		return layout;
	}

	
}
