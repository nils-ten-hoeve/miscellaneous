package nth.notifier.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DateTimeUtil {

	public static Date convertToTimeOnly(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, 1970);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date addMinutes(Date date, int minutesToAdd) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutesToAdd);
		return calendar.getTime();
	}

	public static Date subTime(Date time1, Date time2) {
		long milliseconds = time1.getTime() - time2.getTime();
		return new Date(milliseconds);
	}

	private final static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final static DateFormat TIME_FORMAT = new SimpleDateFormat("H:mm");
	private static SimpleDateFormat GMT_TIME_FORMAT;

	public static DateFormat gmtTimeFormat() {
		if (GMT_TIME_FORMAT == null) {
			GMT_TIME_FORMAT = new SimpleDateFormat("H:mm");
			Calendar calendar = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
			GMT_TIME_FORMAT.setCalendar(calendar);
		}
		return GMT_TIME_FORMAT;
	}
	
	public static DateFormat dateTimeFormat() {
		return DATE_TIME_FORMAT;
	}
	
	public static DateFormat dateFormat() {
		return DATE_FORMAT;
	}
	
	public static DateFormat timeFormat() {
		return TIME_FORMAT;
	}

}
