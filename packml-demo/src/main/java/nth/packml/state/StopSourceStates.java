package nth.packml.state;

import java.util.ArrayList;
import java.util.List;

public class StopSourceStates {

	private static List<State> stopStates;

	static {
		stopStates = new ArrayList<>();
		stopStates.add(State.RESETTING);
		stopStates.add(State.IDLE);
		stopStates.add(State.STARTING);
		stopStates.add(State.EXECUTE);
		stopStates.add(State.COMPLETING);
		stopStates.add(State.COMPLETE);
		stopStates.add(State.HOLDING);
		stopStates.add(State.HELD);
		stopStates.add(State.UNHOLDING);
		stopStates.add(State.SUSPENDING);
		stopStates.add(State.SUSPENDED);
		stopStates.add(State.UNSUSPENDING);
	}

	public static List<State> asList() {
		return stopStates;
	}

}
