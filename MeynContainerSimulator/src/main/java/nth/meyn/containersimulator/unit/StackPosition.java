package nth.meyn.containersimulator.unit;

import nth.meyn.containersimulator.PresentationOwner;
import nth.meyn.containersimulator.stack.Stack;

public interface StackPosition extends PresentationOwner {
	public Stack getStack();
	public void setStack(Stack stack);
	public String getName();
}
