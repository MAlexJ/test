package getNearestDoctors;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static controller.test.schedule.ScheduleCreateTest.storeSchedule;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * TODO: I need a test API which marks the doctor available in the application.!!!
 *
 * @author malex
 */
@Ignore
public class GetNearestDoctorsTest {

   // 1. create patient with latitude and longitude
   // 2. create doctor with latitude and longitude
   // 3. find doctor use API "getNearestDoctors"
   @Test
   public void test_find_doctor() throws UnirestException {

      // #1 Create PATIENT
      String emailPATIENT = "patient_p_g" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(100);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(100);
      String passwordPATIENT = "0987654321";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());

      // #2 Create DOCTOR
      String emailDOCTOR = "doctor_d_g" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = latitudePATIENT + 1;
      Double longitudeDOCTOR = longitudePATIENT + 1;
      String passwordDOCTOR = "7777777";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());


      String query = "{ \n" +
              "\t\"email\" : \"" + patient.getEmail() + "\", \n" +
              "\t\"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "\t\"longitude\":" + longitudePATIENT + ", \n" +
              "\t\"latitude\":" + latitudePATIENT + ", \n" +
              "\t\"listIndex\":1\n" +
              "}";

      HttpResponse<JsonNode> response = Unirest.post(URL_GET_NEAREST_DOCTOR)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body(query)
              .asJson();

      String message = (String) response.getBody().getObject().get("message");
      Boolean status = (Boolean) response.getBody().getObject().get("status");
      Integer statusCode = (Integer) response.getBody().getObject().get("statusCode");

      // assert
      assertEquals(message, "Doctors successful fetched");
      assertEquals(status, Boolean.TRUE);
      assertEquals(statusCode, new Integer(200));


      JSONArray jsonArray = (JSONArray) response.getBody().getObject().get("users");

      boolean isFound = false;

      for (int i = 0; i < jsonArray.length(); i++) {
         JSONObject user = (JSONObject) jsonArray.get(i);
         String email = (String) user.get("email");
         if (email.equalsIgnoreCase(doctor.getEmail())) {
            isFound = true;
         }
      }

      assertTrue(isFound);
   }

   // 1. create patient with latitude and longitude
   // 2. create doctor with latitude and longitude
   // 3. create the schedule for doctors
   // 4. find doctor use API "getNearestDoctors"
   @Test
   public void test_find_doctor_with_scheduler() throws UnirestException {

      String averTime = "45";

      // #1 Create PATIENT
      String emailPATIENT = "patient_p_g" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(100);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(100);
      String passwordPATIENT = "0987654321";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());

      // #2 Create DOCTOR
      String emailDOCTOR = "doctor_d_g" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = latitudePATIENT + 1;
      Double longitudeDOCTOR = longitudePATIENT + 1;
      String passwordDOCTOR = "7777777";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

      // create schedule for doctor
      storeSchedule(doctor, averTime);

      String query = "{ \n" +
              "\t\"email\" : \"" + patient.getEmail() + "\", \n" +
              "\t\"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "\t\"longitude\":" + longitudePATIENT + ", \n" +
              "\t\"latitude\":" + latitudePATIENT + ", \n" +
              "\t\"listIndex\":1\n" +
              "}";

      HttpResponse<JsonNode> response = Unirest.post(URL_GET_NEAREST_DOCTOR)
              .header("content-type", "application/json")
              .body(query)
              .asJson();

      String message = (String) response.getBody().getObject().get("message");
      Boolean status = (Boolean) response.getBody().getObject().get("status");
      Integer statusCode = (Integer) response.getBody().getObject().get("statusCode");

      // assert
      assertEquals(message, "Doctors successful fetched");
      assertEquals(status, Boolean.TRUE);
      assertEquals(statusCode, new Integer(200));


      JSONArray jsonArray = (JSONArray) response.getBody().getObject().get("users");

      boolean isFound = false;

      for (int i = 0; i < jsonArray.length(); i++) {
         JSONObject user = (JSONObject) jsonArray.get(i);
         String email = (String) user.get("email");
         if (email.equalsIgnoreCase(doctor.getEmail())) {
            isFound = true;

            // check schedule
            JSONArray schedulesExpect = user.getJSONArray("schedules");
            JSONObject shOb = (JSONObject) schedulesExpect.get(0);
            String avergeTimeConsultation = (String) shOb.get("avergeTimeConsultation");
            // assert time
            assertEquals(avergeTimeConsultation, averTime);

            JSONArray doctorScheduler = (JSONArray) shOb.opt("doctorScheduler");
            int length = doctorScheduler.length();

            // assert number of days
            assertEquals(length, 7);
         }
      }

      assertTrue(isFound);
   }

}