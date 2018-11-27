package nth.packml.state.listener;

import nth.packml.gui.PackMLStateMachine;
import nth.packml.state.transition.StateTransition;

public interface StateChangeListerner {
	public void onStateChange(PackMLStateMachine stateMachine, StateTransition stateTransition);
}
