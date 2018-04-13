package nth.meyn.containersimulator.unit.cas;

import java.time.Duration;
import java.util.Optional;

import nth.meyn.containersimulator.routemanager.Route;
import nth.meyn.containersimulator.routemanager.RouteManager;
import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.statemachine.State;
import nth.meyn.containersimulator.timer.Timer;
import nth.meyn.containersimulator.unit.stack.finish.StackFinish;

public class StateExhaust extends CasState {


	private boolean exhaustStageCompleted;
	private final Cas cas;

	public StateExhaust(Cas cas) {
		super(cas);
		this.cas=cas;
	}

	
	@Override
	public void onStart() {
		exhaustStageCompleted=false;
		
		setStackDestinationToFinish();
		
		Duration duration=cas.getVirtualExhaustTime();
		new Timer(duration, this::onExhaustStageCompleted );
	}


	private void setStackDestinationToFinish() {
		Optional<StackFinish> stackFinish = getStackFinish();
		if (stackFinish.isPresent()) {
			cas.getStack().setDestination(stackFinish.get());		
		}
	}


	private Optional<StackFinish> getStackFinish() {
		RouteManager routeManager = cas.getLayout().getRouteManager();
		Optional<Route> routeToFinish = routeManager.findRouteToFinish(cas.getName());
		if (!routeToFinish.isPresent()) {
			Optional<Route> routeToFinishForDebug = routeManager.findRouteToFinish(cas.getName());
			throw new RuntimeException("There is no route from "+cas.getName()+" to a "+StackFinish.class.getSimpleName());
		}
		Optional<StackTransfer> lastTransfer = routeToFinish.get().getLast();
		if (lastTransfer.isPresent()) {
			StackFinish stackFinish=(StackFinish) lastTransfer.get().getStackDestination();
			return Optional.of(stackFinish);
		} else {
			return Optional.empty();			
		}
	}

	public void onExhaustStageCompleted() {
		exhaustStageCompleted=true;
		
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		if (exhaustStageCompleted) {
			return StateWaitFeedOut.class;
		}
		return null;
	}

	

}
