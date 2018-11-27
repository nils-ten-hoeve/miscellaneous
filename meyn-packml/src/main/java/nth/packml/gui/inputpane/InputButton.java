package nth.packml.gui.inputpane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import nth.packml.gui.displaybuttonpane.Style;
import nth.packml.gui.statepane.BackgroundFactory;

public class InputButton extends Button {

	private BooleanProperty onProperty;
	private Color onColor;

	public InputButton(String text, Runnable action) {
		super(text);
		setOnAction(e -> action.run());
		setBackground(BackgroundFactory.create(Style.OFF_COLOR, Style.ROUNDING));
		setPrefWidth(190);
		setPrefHeight(40);
	}

	public InputButton(String text, Runnable action, SimpleObjectProperty<Boolean> valueProperty, Color onColor) {
		this(text, action);
		this.onColor = onColor;
		onProperty = createOnProperty(valueProperty);
		onProperty.addListener(e -> onPropertyChange());
		onPropertyChange();
	}

	private BooleanProperty createOnProperty(SimpleObjectProperty<Boolean> valueProperty) {
		return BooleanProperty.booleanProperty(valueProperty);
	}

	public void onPropertyChange() {
		if (onProperty.get()) {
			setBackground(BackgroundFactory.create(onColor, Style.ROUNDING));
		} else {
			setBackground(BackgroundFactory.create(Style.OFF_COLOR, Style.ROUNDING));
		}
	}

}
