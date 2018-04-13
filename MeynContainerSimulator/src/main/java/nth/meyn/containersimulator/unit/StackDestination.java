package nth.meyn.containersimulator.unit;

import java.time.Duration;

import nth.meyn.containersimulator.PresentationOwner;

public interface StackDestination extends StackPosition, PresentationOwner {
	public boolean isOkToFeedIn();
	public Duration getVirtualFeedInTime();
	public void onFeedInStarted();
	public void onFeedInCompleted();

}
