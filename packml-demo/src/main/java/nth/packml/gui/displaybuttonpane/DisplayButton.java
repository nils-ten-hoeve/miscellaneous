package nth.packml.gui.displaybuttonpane;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import nth.packml.gui.statepane.BackgroundFactory;

public class DisplayButton extends Button {

	private static final int ROUNDING = 5;
	private static final Color OFF_COLOR = Color.BLACK;

	public DisplayButton(String text, DisplayStateProperty displayButtonStateProperty, Color onColor) {
		super(text);
		setPrefWidth(40);
		setPrefHeight(40);
		displayButtonStateProperty.addListener(e -> {
			onStateChange(displayButtonStateProperty, onColor);
		});
		onStateChange(displayButtonStateProperty, onColor);
	}

	private void onStateChange(DisplayStateProperty displayButtonStateProperty, Color onColor) {
		switch (displayButtonStateProperty.get()) {
		case ON:
			setTextFill(Color.WHITE);
			setBackground(BackgroundFactory.create(onColor, ROUNDING));
			break;
		case BLINKING:
			break;
		default:
			setTextFill(Color.WHITE);
			setBackground(BackgroundFactory.create(OFF_COLOR, ROUNDING));
			break;
		}

	}

}
