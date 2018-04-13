package nth.meyn.containersimulator.unit.shiftframe.position;

import java.time.Duration;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.unit.StackDestination;
import nth.meyn.containersimulator.unit.StackSource;

public class ShiftFramePosition implements StackDestination, StackSource {

	private Stack stack;
	private Node guiPresentation;
	private String name;

	
	public ShiftFramePosition(String name, double xPos, double yPos, int rotation) {
		this.name = name;
		guiPresentation=createGuiPresenation(name, xPos, yPos, rotation);
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
	public Node getGuiPresentation() {
		return guiPresentation;
	}

	private Node createGuiPresenation(String name, double xPos, double yPos, int rotation) {
		StackPane sfpos=new StackPane();
		Background background = new Background(
				new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY));
		sfpos.setBackground(background);
		sfpos.setPrefWidth(90);
		sfpos.setPrefHeight(60);

		Text nameLabel = new Text(name);
		nameLabel.setTranslateY(-28);
		sfpos.getChildren().add(nameLabel);

		sfpos.setLayoutX(xPos);
		sfpos.setLayoutY(yPos);
		sfpos.setRotate(rotation);
		
	    return sfpos;
	}

	@Override
	public boolean isOkToFeedOut() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Duration getVirtualFeedOutTime() {
		// TODO Auto-generated method stub
		return Duration.ZERO;
	}

	@Override
	public void onFeedOutStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFeedOutCompleted() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOkToFeedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Duration getVirtualFeedInTime() {
		// TODO Auto-generated method stub
		return Duration.ZERO;
	}

	@Override
	public void onFeedInStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFeedInCompleted() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return name;
	}

}
