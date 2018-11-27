package nth.packml.state.transition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nth.packml.state.AbortSourceStates;
import nth.packml.state.State;
import nth.packml.state.StateRequest;
import nth.packml.state.StopSourceStates;

public class RouteFactory {

	public static List<Route> createDefaultRoutes() {
		List<Route> routes = new ArrayList<>();
		routes.add(createPowerUpRoute());
		routes.add(createAbortRoute());
		routes.add(createStopRoute());
		routes.add(createStartRoute());
		routes.add(createCompletedRoute());
		routes.add(createHoldRoute());
		routes.add(createSuspendRoute());
		return routes;
	}

	private static Route createPowerUpRoute() {
		Route route = new Route();
		route.add(new StateTransition(new ArrayList<>(Arrays.asList(State.values())), StateRequest.POWER_UP, State.ABORTED));
		return route;
	}

	public static Route createSuspendRoute() {
		Route route = new Route();
		route.add(new StateTransition(State.EXECUTE, StateRequest.SUSPEND, State.SUSPENDING));
		route.add(new StateTransition(State.SUSPENDING, StateRequest.SUSPENDING_COMPLETED, State.SUSPENDED));
		route.add(new StateTransition(State.SUSPENDED, StateRequest.UNSUSPEND, State.UNSUSPENDING));
		route.add(new StateTransition(State.UNSUSPENDING, StateRequest.UNSUSPENDING_COMPLETED, State.EXECUTE));
		return route;
	}

	public static Route createHoldRoute() {
		Route route = new Route();
		route.add(new StateTransition(State.EXECUTE, StateRequest.HOLD, State.HOLDING));
		route.add(new StateTransition(State.HOLDING, StateRequest.HOLDING_COMPLETED, State.HELD));
		route.add(new StateTransition(State.HELD, StateRequest.UNHOLD, State.UNHOLDING));
		route.add(new StateTransition(State.UNHOLDING, StateRequest.UNHOLDING_COMPLETED, State.EXECUTE));
		return route;
	}

	public static Route createCompletedRoute() {
		Route route = new Route();
		route.add(new StateTransition(State.COMPLETING, StateRequest.COMPLETING_COMPLETED, State.COMPLETE));
		route.add(new StateTransition(State.COMPLETE, StateRequest.RESET, State.RESETTING));
		return route;
	}

	public static Route createStartRoute() {
		Route route = new Route();
		route.add(new StateTransition(State.IDLE, StateRequest.START, State.STARTING));
		route.add(new StateTransition(State.STARTING, StateRequest.STARTING_COMPLETED, State.EXECUTE));
		route.add(new StateTransition(State.EXECUTE, StateRequest.COMPLETE, State.COMPLETING));
		return route;
	}

	public static Route createStopRoute() {
		Route route = new Route();
		route.add(new StateTransition(StopSourceStates.asList(), StateRequest.STOP, State.STOPPING));
		route.add(new StateTransition(State.STOPPING, StateRequest.STOPPING_COMPLETED, State.STOPPED));
		route.add(new StateTransition(State.STOPPED, StateRequest.RESET, State.RESETTING));
		route.add(new StateTransition(State.RESETTING, StateRequest.RESETTING_COMPLETED, State.IDLE));
		return route;
	}

	public static Route createAbortRoute() {
		Route route = new Route();
		route.add(new StateTransition(AbortSourceStates.asList(), StateRequest.ABORT, State.ABORTING));
		route.add(new StateTransition(State.ABORTING, StateRequest.ABORTING_COMPLETED, State.ABORTED));
		route.add(new StateTransition(State.ABORTED, StateRequest.CLEAR, State.CLEARING));
		route.add(new StateTransition(State.CLEARING, StateRequest.CLEARING_COMPLETED, State.STOPPED));
		return route;
	}

}
