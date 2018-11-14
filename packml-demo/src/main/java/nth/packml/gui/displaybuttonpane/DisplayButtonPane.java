package nth.packml.gui.displaybuttonpane;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.system.System;

public class DisplayButtonPane extends Pane {

	public DisplayButtonPane(PackMLStateMachine stateMachine) {
		setTranslateX(150);
		setTranslateY(150);
		ChangeListener<System> listener = createSystemChangeListener();
		stateMachine.getSystemProperty().addListener(listener);
	}

	private ChangeListener<System> createSystemChangeListener() {
		return new ChangeListener<System>() {

			@Override
			public void changed(ObservableValue<? extends System> observable, System oldValue, System newSystem) {
				getChildren().clear();
				List<Control> displayButtons = newSystem.getDisplayButtons();
				int x = 0;
				for (Control displayButton : displayButtons) {
					getChildren().add(displayButton);
					displayButton.setLayoutX(x);
					x += displayButton.getPrefWidth() + 10;
				}

			}
		};
	}

}
