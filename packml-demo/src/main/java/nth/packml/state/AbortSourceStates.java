package nth.packml.state;

import java.util.ArrayList;
import java.util.List;

public class AbortSourceStates {

	private static List<State> abortStates;

	static {
		abortStates =  new ArrayList<>();
		abortStates.add(State.CLEARING);
		abortStates.add(State.STOPPING);
		abortStates.add( State.STOPPED);
		abortStates.addAll(StopSourceStates.asList());
	}

	public static List<State> asList() {
		return abortStates;
	}

}
