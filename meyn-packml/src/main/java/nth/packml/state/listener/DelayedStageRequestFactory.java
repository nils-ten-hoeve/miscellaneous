package nth.packml.state.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.state.State;
import nth.packml.state.StateRequest;

public class DelayedStageRequestFactory {

	public static List<ChangeListener<State>> create(PackMLStateMachine stateMachine) {
		List<ChangeListener<State>> listeners=new ArrayList<>();
		for (State state : State.values()) {
			String requestName=state.name()+"_COMPLETED";
			Optional<StateRequest> request=StateRequest.find(requestName);
			if (request.isPresent()) {
				 DelayedStageRequest listener = new DelayedStageRequest(stateMachine, state, request.get());
				listeners.add(listener);
			}
		}
		return listeners;
	}

}
