package nth.packml.gui.inputpane;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.system.System;

public class InputPane extends Pane {

	public InputPane(PackMLStateMachine stateMachine) {
		setTranslateX(150);
		setTranslateY(220);
		ChangeListener<System> listener = createSystemChangeListener();
		stateMachine.getSystemProperty().addListener(listener);
	}

	private ChangeListener<System> createSystemChangeListener() {
		return new ChangeListener<System>() {

			@Override
			public void changed(ObservableValue<? extends System> observable, System oldValue, System newSystem) {
				getChildren().clear();
				getChildren().add(new Label("Inputs:"));
				List<Control> inputs = newSystem.getInputs();
				int y = 30;
				for (Control input : inputs) {
					input.setLayoutY(y);
					y += 47;
					getChildren().add(input);
				}
			}
		};
	}

}
