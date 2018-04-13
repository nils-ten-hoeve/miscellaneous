package nth.meyn.containersimulator.unit.cas.starter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import nth.meyn.containersimulator.Updatable;
import nth.meyn.containersimulator.timefactor.TimeFactor;
import nth.meyn.containersimulator.unit.StackPositions;
import nth.meyn.containersimulator.unit.cas.Cas;

public class CasStarter implements Updatable {

	private final List<Cas> casUnits;
	private final Duration virtualStartInterval;
	private Duration compensationTime = Duration.ZERO;
	private LocalDateTime lastStartTime;
	private final Duration virtualMaxCompensationTime;
	private TimeFactor timeFactor;

	public CasStarter(StackPositions stackPositions, TimeFactor timeFactor, Duration startInterval,
			Duration maxCompensationTime) {
		this.timeFactor = timeFactor;
		this.virtualMaxCompensationTime = timeFactor.getVirtualDuration(maxCompensationTime).multipliedBy(-1);
		this.virtualStartInterval = timeFactor.getVirtualDuration(startInterval);
		this.casUnits = stackPositions.getCasUnits();
		// TODO get startInterval from tilter unit (s)
		// TODO specify the CAS units as a constructor parameter in case of
		// multiple CO2 supply systems
	}

	@Override
	public void update() {

		Optional<Cas> firstWaitingCasUnit = getFirstWaitingCasUnit();
		if (!firstWaitingCasUnit.isPresent()) {
			// We can not start. Skip the rest of this method.
			return;
		}

		if (!isTimeToStart()) {
			// We can not start. Skip the rest of this method.
			return;
		}

		if (!isStartWindowOk()) {
			// We can not start. Skip the rest of this method.
			return;
		}

		startStunCycle(firstWaitingCasUnit.get());
	//	System.out.println("=> Start CAS:" + firstWaitingCasUnit.get().getName());
	}

	private boolean isTimeToStart() {
		if (lastStartTime == null) {
			return true;
		}
		Duration timeSinceLastStart = Duration.between(lastStartTime, LocalDateTime.now());
		return timeSinceLastStart.plus(compensationTime).compareTo(virtualStartInterval) > 0;
	}

	private void startStunCycle(Cas casToStart) {
		casToStart.onStartStun();
		calculateCompensationTime();
		lastStartTime = LocalDateTime.now();
	}

	private void calculateCompensationTime() {
		if (lastStartTime == null) {
			compensationTime = Duration.ZERO;
		} else {
			Duration timeSinceLastStart = Duration.between(lastStartTime, LocalDateTime.now());
			compensationTime =   timeSinceLastStart.minus(virtualStartInterval);
			 
			// compensation time is always negative! It will always start too
			// late and never too early
			Duration maxCompensationTime = virtualStartInterval.multipliedBy(-1);
			if (compensationTime.compareTo(virtualMaxCompensationTime) > 0) {
				compensationTime = maxCompensationTime;
			}
		}
	}

	private boolean isStartWindowOk() {
		// TODO Auto-generated method stub
		return true;
	}

	private Optional<Cas> getFirstWaitingCasUnit() {
		Stream<Cas> casUnitsWaitingForStart = casUnits.stream().filter(c -> c.isWaitingForStart())
				.sorted((left, right) -> left.getStackLoadDateTime()
						.compareTo(right.getStackLoadDateTime()));
		Optional<Cas> firstWaitingCasUnit = casUnitsWaitingForStart.findFirst();
		return firstWaitingCasUnit;
	}

}
