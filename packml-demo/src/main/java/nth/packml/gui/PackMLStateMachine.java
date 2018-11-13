package nth.packml.gui;

import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import nth.packml.mode.Mode;
import nth.packml.mode.ModeRequest;
import nth.packml.state.State;
import nth.packml.state.StateRequest;
import nth.packml.state.transition.StateTransition;
import nth.packml.state.transition.StateTransitions;
import nth.packml.system.SystemFactory;

public class PackMLStateMachine {

	private StateTransitions stateTransitions;
	private final SimpleObjectProperty<State> stateProperty;
	private final SimpleObjectProperty<Mode> modeProperty;
	private final SimpleObjectProperty<nth.packml.system.System> systemProperty;

	public PackMLStateMachine(PackMLApp packMLApp) {
		stateProperty = new SimpleObjectProperty<>();

		systemProperty = new SimpleObjectProperty<>();
		systemProperty.set(SystemFactory.create(this).get(0));
		systemProperty.addListener(e -> {
			onSystemChange();
		});
		modeProperty = new SimpleObjectProperty<>();
		modeProperty.set(Mode.AUTOMATIC);
		systemProperty.addListener(e -> {
			onModeChange();
		});
		stateProperty.set(State.EXECUTE);

	}

	public void fireRequest(StateRequest stateRequest) {
		System.out.println(stateRequest);
		Optional<StateTransition> validTransition = stateTransitions.find(stateProperty.get(), stateRequest);
		if (validTransition.isPresent()) {
			System.out.println(String.format("State change: %s -> %s", stateProperty.get().getDisplayText(),
					validTransition.get().getDestination().getDisplayText()));
			State newState = validTransition.get().getDestination();
			stateProperty.set(newState);
		}
	}

	public SimpleObjectProperty<State> getStateProperty() {
		return stateProperty;
	}

	public void fireRequest(ModeRequest modeRequest) {
		Mode newMode = Mode.valueOf(modeRequest.name());
		// TODO check if mode exists in current system
		// TODO check if mode may change in current state
		modeProperty.set(newMode);
		// informModeChangeListeners(oldMode, newMode);
	}

	public void onSystemChange() {
		updateStateTransitions();
		fireRequest(StateRequest.POWER_UP);
	}

	public void onModeChange() {
		updateStateTransitions();
		fireRequest(StateRequest.POWER_UP);
	}

	private void updateStateTransitions() {
		stateTransitions = new StateTransitions(systemProperty.get(), modeProperty.get());
		// for (StateTransition stateTransition : stateTransitions) {
		// System.out.println(stateTransition);
		// }
	}

	public SimpleObjectProperty<nth.packml.system.System> getSystemProperty() {
		return systemProperty;
	}

	public SimpleObjectProperty<Mode> getModeProperty() {
		return modeProperty;
	}

}
