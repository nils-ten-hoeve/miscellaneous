package nth.meyn.containersimulator.unit.conveyor;


import nth.meyn.containersimulator.statemachine.State;

public class StateFeedOutAndFeedIn extends ConveyorState {

	//TODO
	
//	private ContainerTransitionSimultaneously transition;
	private final Conveyor conveyor;

	public StateFeedOutAndFeedIn(Conveyor conveyor) {
		super(conveyor);
		this.conveyor = conveyor;
	}
	
	@Override
	public void onStart() {
//		Cas feedOutSource = conveyor;
//		StackDestination feedOutDestination = conveyor.getContainerDestination();
//		StackSource feedInSource = conveyor.getContainerSource();
//		Cas feedInDestination = conveyor;
//		transition=new ContainerTransitionSimultaneously(feedInSource, feedInDestination, feedOutSource, feedOutDestination);
	}

	@Override
	public Class<? extends State> getNextStateClass() {
//		if (transition.isFeedOutCompleted() && transition.isFeedInCompleted()) {
//			return StateTurnWaitFeedOut.class;
//		}
		return null;
	}

}
