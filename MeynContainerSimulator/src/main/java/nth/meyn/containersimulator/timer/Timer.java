package nth.meyn.containersimulator.timer;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {

	public Timer(Duration duration, Runnable runnable) {
		final ScheduledExecutorService executorService = Executors
				.newSingleThreadScheduledExecutor();
		executorService.schedule(runnable, duration.toMillis(), TimeUnit.MILLISECONDS);
	}

	

}
