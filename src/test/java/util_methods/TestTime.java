package util_methods;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by malex on 27.06.17.
 */
public class TestTime {

	/**
	 * 1499264104780 -> Time in client: 17.15
	 */
	@Test
	public void testTime() {

		//
		// Request: set the date to DB
		// params: date and ZoneId
		//
		String requestFromClient = "Europe/Kiev";


		Instant instant = Instant.ofEpochMilli(18000000);
		instant.atZone(ZoneId.of(requestFromClient));
		System.out.println(instant);
		System.out.println(instant.toEpochMilli());


		OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);

		System.out.println("offsetDateTime: " + offsetDateTime);


		//
		// Request: getDateFromDB -> Client
		// params: date and ZoneId
		//
		requestFromClient = "Europe/Kiev";

		ZonedDateTime getDateFromDB = offsetDateTime.atZoneSameInstant(ZoneId.of(requestFromClient));

		System.out.println("getDateFromDB: " + getDateFromDB);

	}


	@Test
	public void test() {

		// ANDROID
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1499335228235L);

		System.out.println("Time: " + cal.getTime());

	}


	private String createDateMillis(String date, String timeZone) {

		Date dateIn;

		try {
			dateIn = new SimpleDateFormat("HH:mm").parse(date);
		} catch (ParseException ex) {
			throw new UnsupportedOperationException("Error parse date " + ex.getMessage());
		}

		// **************************************

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateIn);
		cal.setTimeZone(TimeZone.getTimeZone(timeZone));

		Long time = cal.getTimeInMillis();
		return time.toString();

		// **************************************

	}

}
