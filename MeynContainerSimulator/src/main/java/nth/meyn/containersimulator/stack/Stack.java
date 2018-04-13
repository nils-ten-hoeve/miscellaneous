package nth.meyn.containersimulator.stack;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import nth.meyn.containersimulator.PresentationOwner;
import nth.meyn.containersimulator.unit.StackPosition;

public class Stack implements PresentationOwner {
	private final int sequanceNumber;
	private int numberOfContainers;
	private StackPosition destination;
	private final Node guiComponent;
	private Text destinationLabel;

	public Stack(int sequanceNumber, int numberOfContainers, StackPosition destination, double xPos, double yPos) {
		super();
		this.sequanceNumber = sequanceNumber;
		this.numberOfContainers = numberOfContainers;
		this.destination = destination;
		this.guiComponent = createGuiComponents(xPos, yPos);
	}

	public int getNumberOfContainers() {
		return numberOfContainers;
	}

	public void setNumberOfContainers(int numberOfContainers) {
		this.numberOfContainers = numberOfContainers;
	}

	public int getSequanceNumber() {
		return sequanceNumber;
	}


	
	public StackPosition getDestination() {
		return destination;
	}

	public void setDestination(StackPosition destination) {
		this.destination = destination;
		updateDestinationLabel(destination);
	}

	private void updateDestinationLabel(StackPosition destination) {
		StringBuilder text = new StringBuilder();
		text.append("Dest: ");
		if (destination == null) {
			text.append("None");
		} else {
			text.append(destination.getName());
		}
		destinationLabel.setText(text.toString());
	}

	@Override
	public Node getGuiPresentation() {
		return guiComponent;
	}

	private Node createGuiComponents(double xPos, double yPos) {
		// Group container = new Group();
		//
		// Rectangle rect = new Rectangle();
		// rect.setHeight(40);
		// rect.setWidth(80);
		// rect.setFill(Color.DARKGRAY);
		//
		// Text nameLabel = new Text(5, 15, "Container: " + sequanceNumber);
		// destinationLabel = new Text(5, 30, "Dest: None");
		//
		// container.getChildren().add(rect);
		// container.getChildren().add(nameLabel);
		// container.getChildren().add(destinationLabel);
		//
		// container.setTranslateX(xPos);
		// container.setTranslateY(yPos);
		//
		// return container;

		StackPane container = new StackPane();
		Background background = new Background(
				new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY));
		container.setBackground(background);
		container.setPrefWidth(80);
		container.setPrefHeight(40);

		Text nameLabel = new Text( "Stack: " + sequanceNumber);
		nameLabel.setTranslateY(-10);
		container.getChildren().add(nameLabel);

		destinationLabel = new Text();
		destinationLabel.setTranslateY(10);
		container.getChildren().add(destinationLabel);
		updateDestinationLabel(destination);

//		container.setLayoutX(xPos);
//		container.setLayoutY(yPos);

		container.relocate(xPos, yPos);
		return container;
	}

}
