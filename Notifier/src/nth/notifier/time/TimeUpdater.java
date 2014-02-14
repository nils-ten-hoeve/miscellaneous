package nth.notifier.time;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import nth.notifier.Notifier;

public class TimeUpdater {

	private static final String PROPERTY_FILENAME = "time.properties";
	private static final int START_DELAY = 0;
	private static final int PERIOD = 59000;// a bit less than a minute
	private static final String PROPERTY_START_DATE_TIME = "startDateTime";
	private final Notifier notifier;
	private Date startDateTime;
	private int workTimeInMinutes;
	private int preWarningInMinutes;
	private Date earlyStartTime;
	private Date lateStartTime;
	private Date almostTime;
	private Date pastTime;
	private Date wayPastTime;
	private Date startTime;
	private TimeDialog timeDialog;
	private javax.swing.Timer timer;

	public TimeUpdater(Notifier notifier, TimeDialog timeDialog) {
		this.notifier = notifier;
		this.timeDialog = timeDialog;
		// loadSettings();
		initFields();

		timer = new javax.swing.Timer(PERIOD, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		timer.setInitialDelay(START_DELAY);
		timer.start();
	}

	private void initFields() {

		startDateTime = getStartDateTimeFromFile();

		timeDialog.setStartTime(startDateTime);
		timeDialog.setTimeUpdater(this);
		try {
			earlyStartTime = DateTimeUtil.timeFormat().parse("7:00");
			lateStartTime = DateTimeUtil.timeFormat().parse("9:30");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		workTimeInMinutes = 7*60+50+30;//werktijd is 7:50 + 30 minuten pauze
		preWarningInMinutes = 10;
		reCalculate();
	}

	private Date getStartDateTimeFromFile() {
		Date startDateTime = null;

		// try to load the values from a file
		try {
			Properties properties = new Properties();
			File propertyFile = getPropertyFile();
			InputStream inputStream = new FileInputStream(propertyFile);
			properties.load(inputStream);
			String startDateTimeString = properties
					.getProperty(PROPERTY_START_DATE_TIME);
			if (startDateTimeString != null) {
				startDateTime = DateTimeUtil.dateTimeFormat().parse(
						startDateTimeString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// verify start time
		Date now = new Date(new Date().getTime() - 180000);// now - 3 minutes to
															// start computer

		if (startDateTime == null) {
			startDateTime = now;
			setStartDateTimeInFile(now);
		} else {
			String startDate = DateTimeUtil.dateFormat().format(startDateTime);
			String dateNow = DateTimeUtil.dateFormat().format(now);
			if (!startDate.equals(dateNow)) {
				// start time was of other date so use current time
				startDateTime = now;
				setStartDateTimeInFile(now);
			}
		}
		return startDateTime;
	}

	private void reCalculate() {
		startTime = DateTimeUtil.convertToTimeOnly(startDateTime);
		almostTime = DateTimeUtil.addMinutes(startTime, getWorkTimeInMinutes()
				- preWarningInMinutes);
		pastTime = DateTimeUtil.addMinutes(startTime, getWorkTimeInMinutes());
		wayPastTime = DateTimeUtil.addMinutes(startTime,
				getWorkTimeInMinutes() + 120);
	}

	public void update() {
		TimeStatus timeStatus = getTimeStatus();
		String timeToolTip = getToolTip(timeStatus);
		notifier.update(timeStatus, timeToolTip, getRemainingTime());
		if (timeDialog.isVisible()) {
			timeDialog.update(DateTimeUtil.timeFormat()
					.format(getCurrentTime()), DateTimeUtil.timeFormat()
					.format(pastTime), timeToolTip, getWorkTimeInMinutes(),
					getRemainingMinutes());
		}
	}

	private TimeStatus getTimeStatus() {
		if (startTime.before(earlyStartTime) || startTime.after(lateStartTime)) {
			return TimeStatus.INVALID_START_TIME;
		} else {
			Date currentTime = getCurrentTime();
			if (currentTime.after(startTime) && currentTime.before(almostTime)) {
				return TimeStatus.WORKING;
			} else if (currentTime.after(almostTime)
					&& currentTime.before(pastTime)) {
				return TimeStatus.ALMOST_TIME;
			} else if (currentTime.after(pastTime)
					&& currentTime.before(wayPastTime)) {
				return TimeStatus.PAST_TIME;
			} else { // currentTime.after(wayPastTime)
				return TimeStatus.WAY_PAST_TIME;
			}
		}
	}

	private Date getCurrentTime() {
		return DateTimeUtil.convertToTimeOnly(new Date());
	}

	private void setStartDateTimeInFile(Date startDateTime) {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.put(PROPERTY_START_DATE_TIME, DateTimeUtil
					.dateTimeFormat().format(startDateTime));
			File file = getPropertyFile();
			OutputStream outputStream = new FileOutputStream(file);
			properties.store(outputStream, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private File getPropertyFile() {
		// URL url = this.getClass().getResource("/");
		// String filePath = url.getFile() + PROPERTY_FILENAME;
		// File file = new File(filePath);
		try {
			URI uri = getClass().getProtectionDomain().getCodeSource()
					.getLocation().toURI();
			String filePath = uri.getPath();
			if (filePath.toLowerCase().endsWith(".jar")) {
				filePath = filePath.substring(0, filePath.lastIndexOf("/"));
			}
			filePath = filePath + "/" + PROPERTY_FILENAME;
			return new File(filePath);
		} catch (URISyntaxException e) {
			return null;
		}
	}

	private String getRemainingTime() {
		Date remainingTime = DateTimeUtil.subTime(pastTime, getCurrentTime());
		return DateTimeUtil.gmtTimeFormat().format(remainingTime);
	}

	private int getRemainingMinutes() {
		Date remainingTime = DateTimeUtil.subTime(pastTime, getCurrentTime());
		return (int) (remainingTime.getTime() / 60000);
	}

	private long getPastMinutes() {
		Date remainingTime = DateTimeUtil.subTime(getCurrentTime(), pastTime);
		return remainingTime.getTime() / 60000;
	}

	private String getPastTime() {
		Date remainingTime = DateTimeUtil.subTime(getCurrentTime(), pastTime);
		return DateTimeUtil.gmtTimeFormat().format(remainingTime);
	}

	private int getWorkTimeInMinutes() {
		return workTimeInMinutes;
	}

	private String getToolTip(TimeStatus timeStatus) {
		StringBuffer toolTip = new StringBuffer();
		switch (timeStatus) {
		case INVALID_START_TIME:
			toolTip.append("Invalid start time.  ");
			break;
		case WORKING:
			toolTip.append(getRemainingTime());
			toolTip.append(" remaining.  ");
			break;
		case ALMOST_TIME:
			toolTip.append(getRemainingMinutes());
			toolTip.append(" minutes to go.  ");
			break;
		case PAST_TIME:
			toolTip.append(getPastMinutes());
			toolTip.append(" minutes past time.  ");
			break;
		case WAY_PAST_TIME:
			toolTip.append(getPastTime());
			toolTip.append(" past time.  ");// TODO
			break;
		}
		return toolTip.toString();
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
		setStartDateTimeInFile(startDateTime);
		reCalculate();
		update();
	}

	public void stopTimer() {
		timer.stop();
	}

}
