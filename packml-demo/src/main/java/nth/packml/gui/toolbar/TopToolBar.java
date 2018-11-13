package nth.packml.gui.toolbar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.system.System;
import nth.packml.system.SystemFactory;

public class TopToolBar extends ToolBar {

	private final PackMLStateMachine stateMachine;

	public TopToolBar(PackMLStateMachine stateMachine) {
		super();
		this.stateMachine = stateMachine;
		getChildren().add(createSystemComboBox());
	}

	private ComboBox<System> createSystemComboBox() {
		ObservableList<System> systems = SystemFactory.create(stateMachine);
		ComboBox<System> comboBox = new ComboBox<>(systems);
		comboBox.valueProperty().addListener(createListener());
		comboBox.setValue(systems.get(0));
		return comboBox;
	}

	private ChangeListener<System> createListener() {
		return new ChangeListener<System>() {

			@Override
			public void changed(ObservableValue<? extends System> observable, System oldSystem, System newSystem) {
				stateMachine.getSystemProperty().set(newSystem);
			}
		};
	}
}
