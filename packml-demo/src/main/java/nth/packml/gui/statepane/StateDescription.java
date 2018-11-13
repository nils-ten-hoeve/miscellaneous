package nth.packml.gui.statepane;

import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.mode.Mode;
import nth.packml.state.State;
import nth.packml.system.ModeStateDescription;
import nth.packml.system.System;

public class StateDescription extends Label  {


	private final SimpleObjectProperty<System> systemProperty;
	private final SimpleObjectProperty<Mode> modeProperty;
	private final SimpleObjectProperty<State> stateProperty;

	public StateDescription(PackMLStateMachine stateMachine) {
		setPrefWidth(500);
		setLayoutY(350);
		setWrapText(true);
		systemProperty=stateMachine.getSystemProperty();
		modeProperty=stateMachine.getModeProperty();
		stateProperty=stateMachine.getStateProperty();
		stateProperty.addListener(e-> {onStateChange();});
	}

	public void onStateChange() {
		Optional<ModeStateDescription> foundModeStateDescription = systemProperty.get().getModeStateDescriptions().stream()
				.filter(modeStateDescription -> modeStateDescription.getMode() == modeProperty.get()
						&& modeStateDescription.getState() == stateProperty.get())
				.findFirst();
		if (foundModeStateDescription.isPresent()) {
			ModeStateDescription modeStateDescription = foundModeStateDescription.get();
			StringBuilder description=new StringBuilder();
			description.append(modeStateDescription.getState().getDisplayText());
			description.append(":\n");
			description.append(modeStateDescription.getStateDescription());
			setText(description.toString());
		}
	}



}
