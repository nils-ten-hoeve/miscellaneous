package nth.packml.gui;

import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import nth.packml.mode.Mode;
import nth.packml.mode.ModeRequest;
import nth.packml.state.State;
import nth.packml.state.StateRequest;
import nth.packml.state.StateType;
import nth.packml.state.transition.StateTransition;
import nth.packml.state.transition.StateTransitions;
import nth.packml.system.SystemFactory;

public class PackMLStateMachine {

	private StateTransitions stateTransitions;
	private final SimpleObjectProperty<State> stateProperty;
	private final SimpleObjectProperty<Mode> modeProperty;
	private final SimpleObjectProperty<nth.packml.system.System> systemProperty;
	private final SimpleObjectProperty<Mode> waitingMode;

	public PackMLStateMachine(PackMLApp packMLApp) {
		stateProperty = new SimpleObjectProperty<>();

		systemProperty = new SimpleObjectProperty<>();
		systemProperty.set(SystemFactory.create(this).get(0));
		systemProperty.addListener(e -> {
			onSystemChange();
		});
		modeProperty = new SimpleObjectProperty<>();
		modeProperty.set(Mode.PRODUCTION);
		waitingMode = new SimpleObjectProperty<>();

		systemProperty.addListener(e -> {
			onModeChange();
		});
		stateProperty.set(State.EXECUTE);
		stateProperty.addListener(e -> changeModeIfPossible());

	}

	private void changeModeIfPossible() {
		if (systemProperty.get().getModes().contains(waitingMode.get())) {
			if (stateProperty.get().getGroup() == StateType.STOPPED) {
				System.out.println(String.format("Mode change: %s -> %s", modeProperty.get().getDisplayText(),
						waitingMode.get().getDisplayText()));
				modeProperty.set(waitingMode.get());
				waitingMode.set(null);
			} else {
				fireRequest(StateRequest.STOP);
			}
		}
	}

	public void fireRequest(StateRequest stateRequest) {
		System.out.println(String.format("State request: %s", stateRequest.getDisplayText()));
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
		System.out.println(String.format("Mode request: %s", modeRequest.getDisplayText()));
		Mode newMode = Mode.valueOf(modeRequest.name());
		if (systemProperty.get().getModes().contains(newMode)) {
			waitingMode.set(newMode);
			changeModeIfPossible();
		}
	}

	public void onSystemChange() {
		System.out.println(String.format("System change: %s", systemProperty.get().getClass().getSimpleName()));
		updateStateTransitions();
		waitingMode.set(Mode.PRODUCTION);
		fireRequest(StateRequest.POWER_UP);
	}

	public void onModeChange() {
		updateStateTransitions();
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

	public SimpleObjectProperty<Mode> getWaitingModeProperty() {
		return waitingMode;
	}

}
