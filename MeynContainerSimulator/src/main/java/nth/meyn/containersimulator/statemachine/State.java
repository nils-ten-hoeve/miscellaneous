package nth.meyn.containersimulator.statemachine;

public abstract class State {


	/**
	 * Mandatory stateMachine parameter which can be linked to a field in the constructor of one of the {@link State} sub classes
	 * @param stateMachine
	 */
	public State(StateMachine stateMachine) {
	}
	
	/**
	 * This hook method is called when this state becomes active. You can override it to execute actions.
	 */
	public void onStart(){
		
	}
	
	/**
	 * This hook method is called when this state becomes passive because another state becomes active. You can override it to execute actions.
	 */
	
	public  void onExit() {
		
	}
	
	/**
	 * 
	 * @return the the class of the next state (null if state remains the same)
	 */
	
	public abstract Class<? extends State> getNextStateClass();
	


}
