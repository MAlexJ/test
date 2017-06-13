package getNearestDoctors;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @author malex
 */
public class GetNearestDoctorsFilterTimeTest {

	private User patient;
	private Double latitudePATIENT;
	private Double longitudePATIENT;

	@Test
	public void testDate() {

		assertDate("09:00 AM");
		assertDate("09:00 PM");
		assertDate("12:00 PM");
		assertDate("12:00 AM");

		assertDate("12:01 AM");
		assertDate("12:59 AM");

		assertDate("05:00 PM");
	}

	@Test
	public void test() {

		// 1496937637705
		System.out.println("Start time: " + new SimpleDateFormat("hh:mm a").format(1496908837704L));
		System.out.println("End date:   " + new SimpleDateFormat("hh:mm a").format(1496937637705L));

		System.out.println("Start date: " + new SimpleDateFormat("hh:mm a").format(1496908837704L));
		System.out.println("End date:   " + new SimpleDateFormat("hh:mm a").format(1496925341722L));


		Calendar calendarEndTime = Calendar.getInstance();
		calendarEndTime.set(Calendar.HOUR_OF_DAY, 17);
		calendarEndTime.set(Calendar.MINUTE, 0);
		System.out.println("TIme:  " + calendarEndTime.getTimeInMillis() + "");

	}

	@Test
	public void timeTest() throws UnirestException {

		if (patient == null) {
			// #1 Create PATIENT
			String emailPATIENT = "patient_new_" + new Random().nextInt(243656300) + "@mail.com";
			latitudePATIENT = LATITUDE + new Random().nextInt(100);
			longitudePATIENT = LONGITUDE + new Random().nextInt(100);
			String passwordPATIENT = "123456789A";
			String loginModePATIENT = "EMAIL";
			patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
		}

		String query = "{ \n" +
				  "  \"email\" : \"" + patient.getEmail() + "\", \n" +
				  "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
				  "  \"speciality\":\"\",\n" +
				  "  \"language\":\"\",\n" +

				  "  \"fromTime\":\"" + createDateMillis("09:00 AM") + "\",\n" +
				  "  \"toTime\":\"" + createDateMillis("05:00 PM") + "\",\n" +

				  "  \"doctorName\":\"\",\n" +
				  "  \"locationAddress\":\"\",\n" +
				  "  \"longitude\":" + longitudePATIENT + ", \n" +
				  "  \"latitude\":" + latitudePATIENT + ", \n" +
				  "  \"listIndex\":1\n" +
				  "}";

		long startTime = System.currentTimeMillis();

		HttpResponse<JsonNode> response = Unirest.post(URL_GET_NEAREST_DOCTOR)
				  .header("content-type", "application/json")
				  .header("cache-control", "no-cache")
				  .body(query)
				  .asJson();

		long endTime = System.currentTimeMillis();

		System.out.println("Time response " + (endTime - startTime) + " milliseconds");

		Boolean status = (Boolean) response.getBody().getObject().get("status");
		Integer statusCode = (Integer) response.getBody().getObject().get("statusCode");

		// assert
		assertEquals(status, Boolean.TRUE);
		assertEquals(statusCode, new Integer(200));
	}

	@Test
	public void test2() {
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.set(Calendar.HOUR_OF_DAY, 17);

		System.out.println(endTimeCal.getTime());
	}

	@Test
	public void testAddMinutesToLocalTime() {

		// check interval: 15 minute
		String actualDate = "11:30 AM";

		long actualDateMillis = createDateMillis(actualDate);

		LocalTime actualLocalDate = convertLongToLong(actualDateMillis);

		LocalTime localTime = actualLocalDate.plusMinutes(15);
		System.out.println(localTime);

		// check interval: 30 minute
		LocalTime localTimeHalf = actualLocalDate.plusMinutes(30);
		System.out.println(localTimeHalf);

	}

	@Test
	public void getDayOfWeek() {
		Calendar c = Calendar.getInstance();
//        c.setTime(yourDate);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		System.out.println(dayOfWeek);


//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
//    LocalDate date = LocalDate.parse("23/2/2010", formatter); // LocalDate = 2010-02-23

		LocalDate date = LocalDate.now();
		DayOfWeek dow = date.getDayOfWeek();  // Extracts a `DayOfWeek` enum object.
		String output = dow.getDisplayName(TextStyle.SHORT, Locale.US); // String = Tue

		System.out.println(output);

	}

	/**
	 * Test the interval of time
	 * If the start time < the end time or equals to
	 */
	@Test
	public void testInterval() {

		// *****************************************************
		// ******** if the start date < the end date  **********
		// *****************************************************

		//    |---DB---|
		//         |
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("11:30 AM"), createDateMillis("11:30 AM")));

		//    |----DB-----|
		//      |- 15 -|
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("11:30 AM"), createDateMillis("11:45 AM")));
		assertTrue(setInterval(createDateMillis("11:00 AM"), 1496937637705L, createDateMillis("11:00 AM"), createDateMillis("11:15 AM")));

		//    |---DB---|
		//    |--------|
		assertTrue(setInterval(1496908837704L, 1496937637705L, 1496908837704L, 1496937637705L));

		//     |---DB---|
		//       |----|
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("11:30 AM"), createDateMillis("04:30 PM")));

		//     |---DB---|
		//   |------------|
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("10:30 AM"), createDateMillis("08:30 PM")));

		//     |---DB---|
		//  |-----|
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("09:00 AM"), createDateMillis("11:30 AM")));
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("04:00 AM"), createDateMillis("02:30 PM")));
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("04:00 AM"), createDateMillis("10:30 AM")));
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("03:00 AM"), createDateMillis("11:00 AM")));
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("03:00 AM"), createDateMillis("01:00 PM")));

		//     |---DB---|
		//          |------|
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("04:00 PM"), createDateMillis("09:00 PM")));
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 PM"), createDateMillis("09:00 PM")));
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:45 PM"), createDateMillis("09:00 PM")));

		//     |---DB---|
		//          |---|
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:45 PM"), createDateMillis("07:00 PM")));
		assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 PM"), createDateMillis("07:00 PM")));
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:46 PM"), createDateMillis("07:00 PM")));

		//     |---DB---|
		//     |---|
		assertTrue(setInterval(createDateMillis("11:00 AM"), 1496937637705L, createDateMillis("11:00 AM"), createDateMillis("11:15 AM")));
		assertTrue(setInterval(createDateMillis("11:00 AM"), 1496937637705L, createDateMillis("11:00 AM"), createDateMillis("11:30 AM")));
		assertTrue(setInterval(createDateMillis("11:00 AM"), 1496937637705L, createDateMillis("11:00 AM"), createDateMillis("11:55 AM")));
		assertTrue(setInterval(createDateMillis("11:00 AM"), 1496937637705L, createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));
		assertTrue(setInterval(createDateMillis("11:00 AM"), 1496937637705L, createDateMillis("11:00 AM"), createDateMillis("11:50 AM")));

		//     |---DB---|
		//     |-----------|

		// ************  FALSE  ****************//
		// *************************************//

		//     |--DB--|
		//                |----|
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("08:00 PM"), createDateMillis("10:30 PM")));
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("07:30 PM"), createDateMillis("11:30 PM")));
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("07:05 PM"), createDateMillis("11:00 PM")));


		//          |--DB--|
		//  |----|
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), createDateMillis("08:45 AM")));
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("04:05 AM"), createDateMillis("08:55 AM")));

		//           |---DB---|
		//   |-------|
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), createDateMillis("11:00 AM")));
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), 1496908837704L));

		//  |---DB---|
		//           |-------|
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), createDateMillis("11:00 AM")));
		assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), 1496908837704L));

		// *****************************************************
		// ******** if the start date > the end date  **********
		// *****************************************************

		// doctor's schedule 22:00 - 06:00
		assertTrue(setInterval(createDateMillis("08:00 PM"), createDateMillis("06:00 AM"), createDateMillis("11:00 PM"), createDateMillis("03:15 AM")));
		assertTrue(setInterval(createDateMillis("08:00 PM"), createDateMillis("06:00 AM"), createDateMillis("08:00 PM"), createDateMillis("03:15 AM")));
		assertTrue(setInterval(createDateMillis("08:00 PM"), createDateMillis("06:00 AM"), createDateMillis("08:00 PM"), createDateMillis("06:00 AM")));
		assertTrue(setInterval(createDateMillis("08:00 PM"), createDateMillis("06:00 AM"), createDateMillis("06:00 PM"), createDateMillis("07:00 AM")));


		// *****************************************************
		// **************** EXCEPTION TIME  ********************
		// *****************************************************

		// doctor's schedule 10:00 - 18:00
		assertTrue(findDoctorByFilter(createDateMillis("10:00 AM"), createDateMillis("06:00 PM"),
				  createDateMillis("09:00 AM"), createDateMillis("05:00 PM"), 15,
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));

		assertTrue(findDoctorByFilter(createDateMillis("10:00 AM"), createDateMillis("06:00 PM"),
				  createDateMillis("11:30 AM"), createDateMillis("01:30 PM"), 15,
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));

		assertTrue(findDoctorByFilter(createDateMillis("10:00 AM"), createDateMillis("06:00 PM"),
				  createDateMillis("11:00 AM"), createDateMillis("01:15 PM"), 15,
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));

		assertTrue(findDoctorByFilter(createDateMillis("10:00 AM"), createDateMillis("06:00 PM"),
				  createDateMillis("10:45 AM"), createDateMillis("01:15 PM"), 15,
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));

		assertFalse(findDoctorByFilter(createDateMillis("10:00 AM"), createDateMillis("06:00 PM"),
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM"), 15,
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));

		assertFalse(findDoctorByFilter(createDateMillis("10:00 AM"), createDateMillis("06:00 PM"),
				  createDateMillis("11:01 AM"), createDateMillis("01:00 PM"), 15,
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));

		assertFalse(findDoctorByFilter(createDateMillis("10:00 AM"), createDateMillis("06:00 PM"),
				  createDateMillis("11:01 AM"), createDateMillis("01:14 PM"), 15,
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));

		assertFalse(findDoctorByFilter(createDateMillis("10:00 AM"), createDateMillis("06:00 PM"),
				  createDateMillis("11:30 AM"), createDateMillis("01:10 PM"), 15,
				  createDateMillis("11:00 AM"), createDateMillis("01:00 PM")));

	}

	private boolean findDoctorByFilter(long startDateDB, long endDateDB, long startAndroid, long endAndroid, long timeConsultation, Long startExcwptionTime, Long endExceptionTime) {
		// doctor's schedule
		LocalTime localStartDateDB;
		LocalTime localEndDateDB;

		// time of search
		LocalTime localStartAndroid;
		LocalTime localEndAndroid;


		// check to equals
		if (startAndroid == endAndroid) {
			return false;
		}

		// check date
		if (startAndroid > endAndroid) {

			// NOT NORMAL CASE

			// start and end the time of doctor's schedule
			LocalTime docTimeStartDB = convertLongToLong(startDateDB);   // start from DB
			localStartDateDB = docTimeStartDB.plusHours(12);
			LocalTime docTimeEndDb = convertLongToLong(endDateDB);   // end from DB
			localEndDateDB = docTimeEndDb.plusHours(12);

			// start and ens the time of search
			localStartAndroid = convertLongToLongReversPlus(startAndroid);
			localEndAndroid = convertLongToLongReversPlus(endAndroid);

		} else {

			// NORMAL CASE
			localStartDateDB = convertLongToLong(startDateDB);
			localEndDateDB = convertLongToLong(endDateDB);

			localStartAndroid = convertLongToLong(startAndroid);
			localEndAndroid = convertLongToLong(endAndroid);

		}

		System.out.println("  >> startDate: " + startDateDB + " time: " + new SimpleDateFormat("HH:mm").format(startDateDB));
		System.out.println("  >> endDate: " + endDateDB + " time: " + new SimpleDateFormat("HH:mm").format(endDateDB));

		System.out.println("  << intreval_Start: " + startAndroid + " time: " + new SimpleDateFormat("HH:mm").format(startAndroid));
		System.out.println("  << intreval_End: " + endAndroid + " time: " + new SimpleDateFormat("HH:mm").format(endAndroid));

		System.out.println();

		// check interval: 15 or 30 minute from the star date
		LocalTime timeShift = localStartAndroid.plusMinutes(timeConsultation);
		if (localEndAndroid.compareTo(timeShift) == -1) {
			return false;
		}


		// check exception time
		if (startExcwptionTime != null && endExceptionTime != null) {


			LocalTime ltExTimeStart = convertLongToLong(startExcwptionTime);
			LocalTime ltExTimeEnd = convertLongToLong(endExceptionTime);

			System.out.println("SEARCH TIME START: " + localStartAndroid);
			System.out.println("SEARCH TIME END:   " + localEndAndroid);

			System.out.println("EX TIME START: " + ltExTimeStart);
			System.out.println("EX TIME END:   " + ltExTimeEnd);


			LocalTime startShiftExTime = ltExTimeStart.minusMinutes(timeConsultation);
			LocalTime endShiftExTime = ltExTimeEnd.plusMinutes(timeConsultation);
			System.out.println("SHIFT EX TIME START: " + startShiftExTime);
			System.out.println("SHIFT EX TIME END:   " + endShiftExTime);

			if ((startShiftExTime.compareTo(localStartAndroid) == - 1 && ltExTimeEnd.compareTo(localStartAndroid) == 1) && (endShiftExTime.compareTo(localEndAndroid) == 1)) {
				return false;
			}

			if (startShiftExTime.compareTo(localStartAndroid) == 0 && ltExTimeEnd.compareTo(endShiftExTime) == 1) {
				return false;
			}

			if (localStartAndroid.compareTo(ltExTimeStart) == 0 && localEndAndroid.compareTo(ltExTimeEnd) == 0) {
				return false;
			}
		}


		// CASE #1 equals
		if (localStartAndroid.compareTo(localStartDateDB) == 0 && localEndAndroid.compareTo(localEndDateDB) == 0) {
			return true;
		}

		// CASE #2 inside
		if ((localStartAndroid.compareTo(localStartDateDB) == 1 && localStartAndroid.compareTo(localEndDateDB) == -1) && (localEndAndroid.compareTo(localStartDateDB) == 1 && localEndAndroid.compareTo(localEndDateDB) == -1)) {
			return true;
		}

		// CASE #3 inside
		if ((localStartDateDB.compareTo(localStartAndroid) == 1 && localStartDateDB.compareTo(localEndAndroid) == -1) && (localEndDateDB.compareTo(localStartAndroid) == 1) && localEndDateDB.compareTo(localEndAndroid) == -1) {
			return true;
		}

		if (localStartAndroid.compareTo(localStartDateDB) == 1 && localStartAndroid.compareTo(localEndDateDB) == -1) {
			return true;
		}

		if (localStartAndroid.compareTo(localStartDateDB) == 0) {
			return true;
		}

		if (localEndAndroid.compareTo(localStartDateDB) == 1 && localEndAndroid.compareTo(localEndDateDB) == -1) {
			return true;
		}

		return false;
	}


	private boolean setInterval(long startDateDB, long endDateDB, long startAndroid, long endAndroid) {


		// doctor's schedule
		LocalTime localStartDateDB;
		LocalTime localEndDateDB;

		// time of search
		LocalTime localStartAndroid;
		LocalTime localEndAndroid;


		// check to equals
		if (startAndroid == endAndroid) {
			return false;
		}

		// check date
		if (startAndroid > endAndroid) {

			// NOT NORMAL CASE

			// start and end the time of doctor's schedule
			LocalTime docTimeStartDB = convertLongToLong(startDateDB);   // start from DB
			localStartDateDB = docTimeStartDB.plusHours(12);
			LocalTime docTimeEndDb = convertLongToLong(endDateDB);   // end from DB
			localEndDateDB = docTimeEndDb.plusHours(12);

			// start and ens the time of search
			localStartAndroid = convertLongToLongReversPlus(startAndroid);
			localEndAndroid = convertLongToLongReversPlus(endAndroid);


			// the search date: 22.00 - 01.00
			// revers date:     10.00 - 13.00
			System.out.println(" search date from android ");
			System.out.println("Local " + docTimeStartDB.toNanoOfDay() + " start time: " + docTimeStartDB);
			System.out.println("revers " + localStartDateDB.toNanoOfDay() + " time: " + localStartDateDB);
			System.out.println("Local " + docTimeEndDb.toNanoOfDay() + " end time: " + docTimeEndDb);
			System.out.println("revers " + localEndDateDB.toNanoOfDay() + " time: " + localEndDateDB);

		} else {

			// NORMAL CASE
			localStartDateDB = convertLongToLong(startDateDB);
			localEndDateDB = convertLongToLong(endDateDB);

			localStartAndroid = convertLongToLong(startAndroid);
			localEndAndroid = convertLongToLong(endAndroid);

		}

		// check exception time

		System.out.println("  >> startDate: " + startDateDB + " time: " + new SimpleDateFormat("HH:mm").format(startDateDB));
		System.out.println("  >> endDate: " + endDateDB + " time: " + new SimpleDateFormat("HH:mm").format(endDateDB));

		System.out.println("  << intreval_Start: " + startAndroid + " time: " + new SimpleDateFormat("HH:mm").format(startAndroid));
		System.out.println("  << intreval_End: " + endAndroid + " time: " + new SimpleDateFormat("HH:mm").format(endAndroid));

		System.out.println();

		// check interval: 15 or 30 minute from the star date
		LocalTime timeShift = localStartAndroid.plusMinutes(15);
		if (localEndAndroid.compareTo(timeShift) == -1) {
			return false;
		}

		// CASE #1 equals
		if (localStartAndroid.compareTo(localStartDateDB) == 0 && localEndAndroid.compareTo(localEndDateDB) == 0) {
			return true;
		}

		// CASE #2 inside
		if ((localStartAndroid.compareTo(localStartDateDB) == 1 && localStartAndroid.compareTo(localEndDateDB) == -1) && (localEndAndroid.compareTo(localStartDateDB) == 1 && localEndAndroid.compareTo(localEndDateDB) == -1)) {
			return true;
		}

		// CASE #3 inside
		if ((localStartDateDB.compareTo(localStartAndroid) == 1 && localStartDateDB.compareTo(localEndAndroid) == -1) && (localEndDateDB.compareTo(localStartAndroid) == 1) && localEndDateDB.compareTo(localEndAndroid) == -1) {
			return true;
		}

		if (localStartAndroid.compareTo(localStartDateDB) == 1 && localStartAndroid.compareTo(localEndDateDB) == -1) {
			return true;
		}

		if (localStartAndroid.compareTo(localStartDateDB) == 0) {
			return true;
		}

		if (localEndAndroid.compareTo(localStartDateDB) == 1 && localEndAndroid.compareTo(localEndDateDB) == -1) {
			return true;
		}

		return false;
	}


	/**
	 * Convert Date in millis to LocalTime
	 *
	 * @param date the date in millis
	 * @return the time in localtime format
	 */
	private LocalTime convertLongToLong(long date) {

		LocalDateTime result =
				  LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.UTC);

		// TODO set seconds and millis to -> '00'

		return result.toLocalTime();
	}


	/**
	 * Convert Date in millis to LocalTime plus 12 hours
	 *
	 * @param date the date in millis
	 * @return the time in localtime format
	 */
	private LocalTime convertLongToLongReversPlus(long date) {

		LocalDateTime result =
				  LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.UTC);

		// TODO set seconds and millis to -> '00'

		return result.toLocalTime().plusHours(12);

	}

	/**
	 * Convert Date in millis to LocalTime plus 12 hours
	 *
	 * @param date the date in millis
	 * @return the time in localtime format
	 */
	private LocalTime convertLongToLongReversinus(long date) {

		LocalDateTime result =
				  LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.UTC);

		// TODO set seconds and millis to -> '00'

		return result.toLocalTime().minusHours(12);

	}


	// check date
	private void assertDate(String date) {

		SimpleDateFormat pattern = new SimpleDateFormat("hh:mm a");

		long dateMillis = createDateMillis(date);

		String actualDate = pattern.format(dateMillis);

		System.out.println("Expect date: " + date + ", actual date: " + actualDate + "   millis: " + dateMillis);

		assertEquals(date, actualDate);
	}

	//*********************************** DATE FROM ANDROID  *********************************************

	/**
	 * Get time in millis
	 *
	 * @param date the time in format: 09:00 AM or PM
	 * @return the time in millis
	 */
	private long createDateMillis(String date) {

		Date dateIn;

		try {
			dateIn = new SimpleDateFormat("hh:mm a").parse(date);
		} catch (ParseException ex) {
			throw new UnsupportedOperationException("Error parse date " + ex.getMessage());
		}

		// **************************************

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateIn);
		return cal.getTimeInMillis();

		// **************************************

	}

}