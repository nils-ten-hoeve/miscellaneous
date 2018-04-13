package nth.meyn.containersimulator.unit.stack.supply;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
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
import nth.meyn.containersimulator.unit.StackPosition;
import nth.meyn.containersimulator.unit.StackSource;
import nth.meyn.containersimulator.unit.Unit;

public class StackSupply extends Unit implements StackSource , PresentationOwner{

	private int sequanceNumber;
	private Stack stack;
	private Text stateLabel;
	private final Node guiPresentation;
	private final Layout layout;
	private final StackPosition destination;


	public StackSupply(Layout layout, String name, int xPos, int yPos, int rotation, StackPosition destination) {
		super(name, getStateClasses(), layout.getEventLog());
		this.layout = layout;
		this.destination = destination;
		guiPresentation=createGuiPresentation(xPos,yPos, rotation);
		createNewContainerStack();
	}

	private static List<Class<? extends State>> getStateClasses() {
		List<Class<? extends State>> stateClasses = new ArrayList<>();
		stateClasses.add(nth.meyn.containersimulator.unit.stack.supply.StateWaitFeedOut.class);
		stateClasses.add(nth.meyn.containersimulator.unit.stack.supply.StateFeedOut.class);
		return stateClasses;
	}

	@Override
	public Stack getStack() {
		return stack;
	}

	@Override
	public void setStack(Stack newContainerStack) {
		if (newContainerStack==null) {
			//container stack is moved to next unit: so we create a new one
			createNewContainerStack();
		} else {
			throw new RuntimeException("A Stackloader provides containers but can not recieve them!!!");
		}
	}

	private void createNewContainerStack() {
		sequanceNumber++;
//		Bounds bounds = guiPresentation.localToScene(guiPresentation.getBoundsInLocal());//getBoundsInParent???

		Bounds stackSupplyBounds = guiPresentation.localToScene(guiPresentation.getBoundsInLocal());

		
//		Bounds boundsOnFloor = guiPresentation.getBoundsInParent();
		double xPos=stackSupplyBounds.getMinX();
		double yPos = stackSupplyBounds.getMinY();
		stack=new Stack(sequanceNumber, 2, destination, xPos, yPos);
		layout.addPresentation(stack.getGuiPresentation());
		stack.getGuiPresentation().toFront();
		stack.getGuiPresentation().setRotate(guiPresentation.getRotate());
	}

	@Override
	public boolean isOkToFeedOut() {
		return stack!=null;
	}

	@Override
	public Duration getVirtualFeedOutTime() {
		return Duration.ZERO;
	}

	private Node createGuiPresentation(double xPos, double yPos, int rotation) {
		StackPane stackLoader=new StackPane();
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
		stackLoader.setRotate(rotation-90);
		
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
	public void onFeedOutStarted() {
		StateWaitFeedOut stateWaitFeedOut=getState(StateWaitFeedOut.class);
		stateWaitFeedOut.onFeedOutStarted();
	}

	@Override
	public void onFeedOutCompleted() {
		StateFeedOut stateFeedOut=getState(StateFeedOut.class);
		stateFeedOut.onFeedOutCompleted();
	}
	
}
