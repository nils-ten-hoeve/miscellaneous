package nth.packml.gui.inputpane;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import nth.packml.gui.displaybuttonpane.Style;
import nth.packml.gui.statepane.BackgroundFactory;

public class InputToggleButton extends ToggleButton {

	private final Color onColor;

	public InputToggleButton(String text, Color onColor) {
		super(text);
		this.onColor = onColor;
		selectedProperty().addListener(e -> changeBackground());
		changeBackground();
		setPrefWidth(190);
		setPrefHeight(40);
	}

	public InputToggleButton(String text) {
		this(text, Style.ON_COLOR);
		selectedProperty().addListener(e -> changeBackground());
		changeBackground();
		setPrefWidth(190);
		setPrefHeight(40);
	}

	public InputToggleButton(String text, Runnable activateAction, Runnable deActivateAction) {
		this(text);
		selectedProperty().addListener(e -> executeAction(activateAction, deActivateAction));
	}

	public InputToggleButton(String text, SimpleObjectProperty<Boolean> selectedProperty, Color onColor) {
		this(text, onColor);
		selectedProperty().bindBidirectional(selectedProperty);
	}

	public void executeAction(Runnable activateAction, Runnable deActivateAction) {
		if (isSelected()) {
			activateAction.run();
		} else {
			deActivateAction.run();
		}
	}

	public void changeBackground() {
		if (isSelected()) {
			setBackground(BackgroundFactory.create(onColor, Style.ROUNDING));
		} else {
			setBackground(BackgroundFactory.create(Style.OFF_COLOR, Style.ROUNDING));
		}
	}

}
