package nth.meyn.containersimulator.unit;

import java.time.Duration;

import nth.meyn.containersimulator.PresentationOwner;

public interface StackSource extends StackPosition, PresentationOwner {
	public boolean isOkToFeedOut();
	public Duration getVirtualFeedOutTime();
	public void onFeedOutStarted();
	public void onFeedOutCompleted();
	
}
