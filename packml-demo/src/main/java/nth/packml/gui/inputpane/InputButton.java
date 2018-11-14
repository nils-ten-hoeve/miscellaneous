package nth.packml.gui.inputpane;

import javafx.scene.control.Button;

public class InputButton extends Button {

	public InputButton(String text, Runnable action) {
		super(text);
		setOnAction(e -> action.run());
		setPrefWidth(190);
		setPrefHeight(40);
	}

}
