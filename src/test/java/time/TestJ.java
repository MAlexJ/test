package time;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by malex on 27.06.17.
 */
public class TestJ {

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


		Instant instant = Instant.ofEpochMilli(1499264104780L);
		instant.atZone(ZoneId.of(requestFromClient));
		System.out.println(instant);


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


//		String str = "1970-01-01 09:00";
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//		LocalDateTime localtDateAndTime = LocalDateTime.parse(str, formatter);

		ZonedDateTime dateAndTimeInSydney = ZonedDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.of(9, 0), ZoneId.of("Europe/Kiev"));
		System.out.println("Current date and time in XXX : " + dateAndTimeInSydney.toLocalTime());

		System.out.println(dateAndTimeInSydney.toInstant().toEpochMilli());

		ZonedDateTime utcDate = dateAndTimeInSydney.withZoneSameInstant(ZoneId.of("Europe/Kiev"));
		System.out.println("Current date and time in Europe/Kiev : " + utcDate.toLocalTime());


		// ANDROID
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(21600000);
//		cal.setTimeZone(TimeZone.getTimeZone("CDT"));

		System.out.println("TTT " + cal.getTime());
//
//		String dateMillis = createDateMillis("09:00", "Europe/Kiev");
//
//		LocalTime localTime = LocalTime.of(9, 0);

////		System.out.println("ldt "+ ldt);
//
//
//		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("Europe/Kiev"));
//
//
//		System.out.println("localDateTime "+localDateTime);


//		Instant instant = Instant.EPOCH;
//
//
//
//		ZoneId zoneId = ZoneId.of( "Europe/Kiev");
//		ZonedDateTime zdt = ZonedDateTime.ofInstant( instant , zoneId );
//
//		System.out.println(zdt.toOffsetDateTime());
//		System.out.println(zdt.toEpochSecond());

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
