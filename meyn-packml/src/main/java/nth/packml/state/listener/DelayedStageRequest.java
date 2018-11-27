package nth.packml.state.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.state.State;
import nth.packml.state.StateRequest;

public class DelayedStageRequest implements ChangeListener<State> {

	private final State state;
	private final StateRequest request;
	private final int delayInMilis;
	private final PackMLStateMachine stateMachine;

	public DelayedStageRequest(PackMLStateMachine stateMachine, State state, StateRequest request) {
		this(stateMachine, state, request, 3000);
	}

	public DelayedStageRequest(PackMLStateMachine stateMachine, State state, StateRequest request, int delayInMilis) {
		this.stateMachine = stateMachine;
		this.state = state;
		this.request = request;
		this.delayInMilis = delayInMilis;
	}

	@Override
	public String toString() {
		return String.format("%s, %s", state, request);
	}

	@Override
	public void changed(ObservableValue<? extends State> observable, State oldState, State newState) {
		if (newState == state) {
			Task<Void> sleeper = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					try {
						Thread.sleep(delayInMilis);
					} catch (InterruptedException e) {
					}
					return null;
				}
			};
			sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					stateMachine.fireRequest(request);
				}
			});
			new Thread(sleeper).start();

		}
	}
}