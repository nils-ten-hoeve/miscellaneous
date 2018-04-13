package nth.meyn.containersimulator.unit.shiftframe.position;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import nth.meyn.containersimulator.PresentationOwner;
import nth.meyn.containersimulator.layout.Layout;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.timefactor.TimeFactor;
import nth.meyn.containersimulator.unit.StackDestination;
import nth.meyn.containersimulator.unit.StackPosition;
import nth.meyn.containersimulator.unit.Unit;

public class ShiftFrame extends Unit implements PresentationOwner, StackDestination {

	private Stack stack;
	private Text stateLabel;
	private final Node guiPresentation;
	private List<StackPosition> shiftFramePositions;
	private final Duration virtualFeedInTime;
	private final Duration virtualShiftOutTime;
	private final Duration virtualShiftInTime;

	public ShiftFrame(Layout layout, String name, int xPos, int yPos, int rotation, List<StackPosition> shiftFramePositions)  {
		super(name, getStateClasses(), layout.getEventLog());
		this.shiftFramePositions = shiftFramePositions;
		this.guiPresentation=createGuiPresentation(xPos,yPos, rotation, shiftFramePositions);
		TimeFactor timeFactor = layout.getTimeFactor();
		this.virtualFeedInTime=timeFactor.getVirtualDuration(Duration.ofSeconds(1));
		this.virtualShiftInTime=timeFactor.getVirtualDuration(Duration.ofSeconds(6));
		this.virtualShiftOutTime=timeFactor.getVirtualDuration(Duration.ofSeconds(6));
	}


	private static List<Class<? extends State>> getStateClasses() {
		List<Class<? extends State>> stateClasses = new ArrayList<>();
		stateClasses.add(StateWaitShiftOut.class);
		stateClasses.add(StateShiftOut.class );
		stateClasses.add(StateShiftIn.class);
		return stateClasses;
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
	public Node getGuiPresentation() {
		return guiPresentation;
	}


	private Node createGuiPresentation(int xPos, int yPos, int rotation, List<StackPosition> shiftFramePositions) {
		StackPane shiftFrame=new StackPane();
		shiftFrame.setStyle("-fx-border-color:black;");
		shiftFrame.setPrefWidth(90*shiftFramePositions.size()+10+10);
		shiftFrame.setPrefHeight(70);

		Text nameLabel = new Text(getName());
		nameLabel.setTranslateY(-28);
		shiftFrame.getChildren().add(nameLabel);

		stateLabel = new Text();
		updateStateLabel(getCurrentState());
		stateLabel.setTranslateY(28);
		shiftFrame.getChildren().add(stateLabel);

		shiftFrame.setLayoutX(xPos);
		shiftFrame.setLayoutY(yPos);
		shiftFrame.setRotate(rotation-90);
		
	    return shiftFrame;
	}


	private void updateStateLabel(State stateToDisplay) {
		stateLabel.setText(stateToDisplay.getClass().getSimpleName());
	}


	@Override
	public void onStateChange(State currentState, State nextState) {
		super.onStateChange(currentState, nextState);
		updateStateLabel(nextState);
	}


	@Override
	public void onFeedInStarted() {
//		StateTurnWaitFeedIn stateWaitFeedIn= getState(StateTurnWaitFeedIn.class);
//		stateWaitFeedIn.onFeedInStarted();
	}


	@Override
	public void onFeedInCompleted() {
//		StateFeedIn stateFeedIn= getState(StateFeedIn.class);
//		stateFeedIn.onFeedInCompleted();
			}


	@Override
	public boolean isOkToFeedIn() {
		// TODO Auto-generated method stub
		return false;
	}


	public Duration getVirtualShiftInTime() {
		return virtualShiftInTime;
	}


	public Duration getVirtualShiftOutTime() {
		return virtualShiftOutTime;
	}


	

}
