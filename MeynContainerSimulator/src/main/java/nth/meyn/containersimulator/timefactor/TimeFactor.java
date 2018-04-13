package nth.meyn.containersimulator.timefactor;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeFactor {

	private long timeFactor;

	/**
	 * 
	 * @param timeFactor
	 *            time that virtual time goes faster.<br>
	 *            E.g. timeFactor=2: virtual time moves 2x faster as real time.
	 *            <br>
	 *            E.g. timeFactor=0.5: virtual time moves 2x slower as real
	 *            time.<br>
	 */
	public TimeFactor(long timeFactor) {
		this.timeFactor = timeFactor;
	}

	public Duration getRealDuration(LocalDateTime startTime) {
		LocalDateTime now = LocalDateTime.now();
		Duration realDuration = Duration.between(startTime, now).multipliedBy(timeFactor);
		return realDuration;
	}

	public Duration getRealDuration(Duration virtualDuration) {
		Duration realDuration = virtualDuration.multipliedBy(timeFactor);
		return realDuration;
	}

	public Duration getVirtualDuration(Duration realDuration) {
		Duration virtualDuration = realDuration.dividedBy(timeFactor);
		return virtualDuration;
	}

	// public int getVirtualMillis(Duration realDuration) {
	// int virtualDurationInMillis = (int) (realDuration.toMillis()/timeFactor);
	// return virtualDurationInMillis;
	// }

}
