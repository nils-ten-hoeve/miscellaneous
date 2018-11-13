package nth.packml.gui.outputpane;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.system.System;

public class OutputPane extends Pane {

	public OutputPane(PackMLStateMachine stateMachine) {
		setTranslateX(400);
		setTranslateY(200);
		ChangeListener<System> listener = createSystemChangeListener();
		stateMachine.getSystemProperty().addListener(listener);
	}

	private ChangeListener<System> createSystemChangeListener() {
		return new ChangeListener<System>() {

			@Override
			public void changed(ObservableValue<? extends System> observable, System oldValue, System newSystem) {
				getChildren().clear();
				getChildren().add(new Label("Outputs:"));
				List<Output> outputs = newSystem.getOutputs();
				int y = 30;
				for (Output output : outputs) {
					output.setLayoutY(y);
					y += 40;
					getChildren().add(output);
				}
			}
		};
	}

}
