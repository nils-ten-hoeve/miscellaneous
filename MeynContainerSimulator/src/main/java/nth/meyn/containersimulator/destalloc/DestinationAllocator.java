package nth.meyn.containersimulator.destalloc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javafx.collections.transformation.SortedList;
import nth.meyn.containersimulator.Updatable;
import nth.meyn.containersimulator.routemanager.Route;
import nth.meyn.containersimulator.routemanager.RouteManager;
import nth.meyn.containersimulator.stack.Stack;
import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.unit.StackPosition;
import nth.meyn.containersimulator.unit.cas.Cas;
import nth.meyn.containersimulator.unit.cas.CasState;
import nth.meyn.containersimulator.unit.cas.StateExhaust;
import nth.meyn.containersimulator.unit.cas.StateWaitFeedIn;

public class DestinationAllocator implements Updatable {

	private final StackPosition stackAllocationPosition;
	private final Map<Cas, Route> casRoutesOrderdByLongest;

	public DestinationAllocator(RouteManager routeManager, StackPosition stackAllocationPosition) {
		this.stackAllocationPosition = stackAllocationPosition;
		
		casRoutesOrderdByLongest = createCasRoutesOrderdByLongest(routeManager, stackAllocationPosition);
		
	}



	private Map<Cas, Route> createCasRoutesOrderdByLongest(RouteManager routeManager,
			StackPosition stackAllocationPosition) {
		
		Map<Cas, Route> casRoutes =new HashMap<>();
		
		List<StackPosition> allCasUnits = routeManager.findStackPositionsOfType(Cas.class);
		for (StackPosition casUnit : allCasUnits) {
			Optional<Route> route = routeManager.findRoute(stackAllocationPosition.getName(),
					casUnit.getName());
			if (route.isPresent()) {
				casRoutes.put((Cas) casUnit,  route.get());
			}
		}
	
		LongestRouteComperator longestRouteComperator=new LongestRouteComperator(casRoutes);
		List<Cas> casUnitsOrderedByLongestRoute=new ArrayList<>(casRoutes.keySet());
		casUnitsOrderedByLongestRoute.sort(longestRouteComperator);
		
		Map<Cas, Route>  casRoutesOrderdByLongest = new LinkedHashMap<>();
		for (Cas casUnit: casUnitsOrderedByLongestRoute) {
			Route casRoute = casRoutes.get(casUnit);
			casRoutesOrderdByLongest.put(casUnit, casRoute);
		}
		
		return casRoutesOrderdByLongest;
	}

	
	
	
	
	class LongestRouteComperator implements Comparator<Cas> {
	    private final Map<Cas, Route> casRoutes;

	    public LongestRouteComperator(Map<Cas, Route> casRoutes) {
	        this.casRoutes = casRoutes;
	    }

		@Override
		public int compare(Cas cas1, Cas cas2) {
			Route route1 = casRoutes.get(cas1);
			Route route2 = casRoutes.get(cas2);
	        if (route1.size() >= route2.size()) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
		}
	}

	
	@Override
	public void update() {
		Optional<Cas> casDestination = findCasDestination();
		if (casDestination.isPresent()) {
			Stack stack = stackAllocationPosition.getStack();
			if (stack != null) {
				stack.setDestination(casDestination.get());
			}
		}
	}

	private Optional<Cas> findCasDestination() {
		
		Optional<Cas> casDestination = findCasDestination(StacksOnRoute.NONE,
				StateWaitFeedIn.class);
		if (casDestination.isPresent()) {
			return casDestination;
		}

		casDestination = findCasDestination(StacksOnRoute.NONE, StateExhaust.class);
		if (casDestination.isPresent()) {
			return casDestination;
		}

		casDestination = findCasDestination(StacksOnRoute.SOME, StateWaitFeedIn.class);
		if (casDestination.isPresent()) {
			return casDestination;
		}

		casDestination = findCasDestination(StacksOnRoute.SOME, StateExhaust.class);
		if (casDestination.isPresent()) {
			return casDestination;
		}

		return Optional.empty();

	}

	private Optional<Cas> findCasDestination(StacksOnRoute stacksOnRoute,
			Class<? extends CasState> casStateToFind) {
		
		for (Cas casUnit:casRoutesOrderdByLongest.keySet()) {
			Route casRoute = casRoutesOrderdByLongest.get(casUnit);
			
			if ((stacksOnRoute == StacksOnRoute.SOME
					&& !casRoute.hasStackOnRouteWithDestination(casUnit))
					|| (stacksOnRoute == StacksOnRoute.NONE && !casRoute.hasStackOnRoute())) {
				Class<? extends State> casState = casUnit.getCurrentState().getClass();
				if (casState == casStateToFind) {
					return Optional.of(casUnit);
				}
			}
		}
		return Optional.empty();
	}

}
