package nth.packml.gui.statepane;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.displaybuttonpane.Style;
import nth.packml.gui.outputpane.Indicator;
import nth.packml.mode.Mode;
import nth.packml.state.State;
import nth.packml.system.ModeStateDescription;
import nth.packml.system.System;

public class StateIndicator extends Indicator {
	private final PackMLStateMachine stateMachine;
	private final State state;

	public StateIndicator(PackMLStateMachine stateMachine, State state) {
		super();
		this.stateMachine = stateMachine;
		this.state = state;
		setTextFill(Style.TEXT_COLOR);
		setText(state.getDisplayText());
		setTooltip(new Tooltip("Right click for more information."));
		addListeners();
		init();
	}

	private void init() {
		updateActive();
		updateVisability();
	}

	private void addListeners() {
		stateMachine.getStateProperty().addListener(e -> updateActive());
		stateMachine.getSystemProperty().addListener(e -> updateVisability());
		stateMachine.getModeProperty().addListener(e -> updateVisability());
		addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onMouseClick(e));
	}

	public void onMouseClick(MouseEvent event) {
		if (event.getButton().name().equals("SECONDARY")) {
			showInfoDialog();
		}
	}

	private void showInfoDialog() {
		System system = stateMachine.getSystemProperty().get();
		Mode mode = stateMachine.getModeProperty().get();
		Optional<ModeStateDescription> description = system.getModeStateDescriptions().stream()
				.filter(d -> d.getMode() == mode && d.getState() == state).findFirst();
		if (description.isPresent()) {
			StringBuilder stateDescription = new StringBuilder();
			stateDescription.append(state.getDisplayText());
			stateDescription.append(" State:\n\n");
			stateDescription.append(description.get().getStateDescription());
			Alert alert = new Alert(AlertType.INFORMATION, stateDescription.toString());
			alert.showAndWait();
		}
	}

	public void updateVisability() {
		System system = stateMachine.getSystemProperty().get();
		Mode mode = stateMachine.getModeProperty().get();
		List<State> disabledStates = system.getDisabledStates(mode);
		setVisible(!disabledStates.contains(state));
	}

	public void updateActive() {
		State activeState = stateMachine.getStateProperty().get();
		if (activeState == state) {
			setBackground(BackgroundFactory.create(Style.ON_COLOR, Style.ROUNDING));
		} else {
			setBackground(BackgroundFactory.create(Style.OFF_COLOR, Style.ROUNDING));
		}
	}

}
