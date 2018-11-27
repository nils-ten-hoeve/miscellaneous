package nth.packml.gui.displaybuttonpane;

import java.util.List;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.system.System;

public class DisplayButtonPane extends Pane {

	public DisplayButtonPane(PackMLStateMachine stateMachine) {
		setTranslateX(150);
		setTranslateY(150);
		stateMachine.getSystemProperty().addListener(e -> updateButtons(stateMachine));
	}

	public void updateButtons(PackMLStateMachine stateMachine) {
		getChildren().clear();
		System system = stateMachine.getSystemProperty().get();
		List<Control> displayButtons = system.getDisplayButtons();
		int x = 0;
		for (Control displayButton : displayButtons) {
			getChildren().add(displayButton);
			displayButton.setLayoutX(x);
			x += displayButton.getPrefWidth() + 10;
		}

	}

}
