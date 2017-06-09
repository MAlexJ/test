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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
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
    public void testInterval() {

        //    |---DB---|
        //    |--------|
        assertTrue(setInterval(1496908837704L, 1496937637705L, 1496908837704L, 1496937637705L));

        //     |---DB---|
        //       |----|
        assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("11:30 AM"), createDateMillis("16:30 AM")));

        //     |---DB---|
        //   |------------|
        assertTrue(setInterval(1496908837704L, 1496937637705L, createDateMillis("10:30 AM"), createDateMillis("19:30 AM")));

        //     |---DB---|
        //  |-----|


        //     |---DB---|
        //          |------|


        //     |--DB--|
        //                |----|
        assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("19:30 AM"), createDateMillis("20:30 AM")));


        //          |--DB--|
        //  |----|
        assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), createDateMillis("08:45 AM")));

        //           |---DB---|
        //   |-------|
        assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), createDateMillis("11:00 AM")));
        assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), 1496908837704L));

        //  |---DB---|
        //           |-------|
        assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), createDateMillis("11:00 AM")));
        assertFalse(setInterval(1496908837704L, 1496937637705L, createDateMillis("06:30 AM"), 1496908837704L));

    }


    private boolean setInterval(long startDateDB, long endDateDB, long startAndroid, long endAndroid) {

        System.out.println("  >> startDate: " + startDateDB + " time: " + new SimpleDateFormat("HH:mm").format(startDateDB));
        System.out.println("  >> endDate: " + endDateDB + " time: " + new SimpleDateFormat("HH:mm").format(endDateDB));

        System.out.println("  >> intreval_Start: " + startAndroid + " time: " + new SimpleDateFormat("HH:mm").format(startAndroid));
        System.out.println("  >> intreval_End: " + endAndroid + " time: " + new SimpleDateFormat("HH:mm").format(endAndroid));

        System.out.println();

        LocalTime localStartDateDB = convertLongToLong(startDateDB);
        LocalTime localEndDateDB = convertLongToLong(endDateDB);

        LocalTime localStartAndroid = convertLongToLong(startAndroid);
        LocalTime localEndAndroid = convertLongToLong(endAndroid);


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

        // Case #4

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

        return result.toLocalTime();
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