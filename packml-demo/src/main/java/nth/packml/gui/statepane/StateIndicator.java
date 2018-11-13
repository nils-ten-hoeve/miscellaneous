package nth.packml.gui.statepane;

import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.outputpane.Indicator;
import nth.packml.mode.Mode;
import nth.packml.state.State;
import nth.packml.system.System;

public class StateIndicator extends Indicator {

	private final SimpleObjectProperty<Mode> modeProperty;

	public StateIndicator(PackMLStateMachine stateMachine, State state, int gridColumn, int gridRow) {
		super();
		modeProperty = stateMachine.getModeProperty();
		setText(state.getDisplayText());
		setActive(false);
		setLayoutX(gridColumn * 100);
		setLayoutY(gridRow * 50);
		addListeners(stateMachine, state);
	}

	private void addListeners(PackMLStateMachine stateMachine, State state) {
		ChangeListener<State> stateChangeListener = createStateListener(state);
		stateMachine.getStateProperty().addListener(stateChangeListener);

		ChangeListener<System> systemChangeListerner = createSystemListener(state);
		stateMachine.getSystemProperty().addListener(systemChangeListerner);
	}

	private ChangeListener<System> createSystemListener(State state) {
		return new ChangeListener<System>() {
			@Override
			public void changed(ObservableValue<? extends System> observable, System oldValue, System newSystem) {
				List<State> disabledStates = newSystem.getDisabledStates(modeProperty.get());
				setVisible(!disabledStates.contains(state));
			}
		};
	}

	private ChangeListener<State> createStateListener(State state) {
		return new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newState) {
				setActive(newState == state);
			}

		};
	}

	private void setActive(boolean active) {
		if (active) {
			setBackground(BackgroundFactory.create(Color.BLUE, 5));
			setTextFill(Color.WHITE);
		} else {
			setBackground(BackgroundFactory.create(Color.LIGHTGRAY, 5));
			setTextFill(Color.BLACK);
		}
	}

}
