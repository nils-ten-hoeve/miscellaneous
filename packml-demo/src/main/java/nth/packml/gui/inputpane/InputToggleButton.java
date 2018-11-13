package nth.packml.gui.inputpane;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class InputToggleButton extends ToggleButton {

	public InputToggleButton(String text) {
		super(text);
		setPrefWidth(160);
		setPrefHeight(40);
	}

	public InputToggleButton(String text, Runnable activateAction, Runnable deActivateAction) {
		this(text);
		setOnAction(createListener(activateAction, deActivateAction));
	}

	public InputToggleButton(String text, SimpleObjectProperty<Boolean> selectedProperty) {
		this(text);
		selectedProperty().bindBidirectional(selectedProperty);
	}

	private EventHandler<ActionEvent> createListener(Runnable activateAction, Runnable deActivateAction) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (isSelected()) {
					activateAction.run();
				} else {
					deActivateAction.run();
				}
			}
		};
	}

}
