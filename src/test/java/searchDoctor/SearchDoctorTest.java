package searchDoctor;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class SearchDoctorTest {

   private User patient;

   @Before
   public void beforeTest() {
      if (patient == null) {
         // #1 Create PATIENT
         String emailPATIENT = "patient_p_g" + new Random().nextInt(243656300) + "@gmail.com";
         Double latitudePATIENT = LATITUDE + new Random().nextInt(100);
         Double longitudePATIENT = LONGITUDE + new Random().nextInt(100);
         String passwordPATIENT = "0987654321";
         String loginModePATIENT = "EMAIL";
         patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      }
   }

   // speciality
   @Test
   public void testSpeciality() throws UnirestException {
      String query = "{\n" +
              "  \"patientEmail\" : \"" + patient.getEmail() + "\", \n" +
              "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "  \"listIndex\":1,\n" +
              "  \"speciality\":\"All\",\n" +
              "  \"doctorName\":\"\"\n" +
              "}\n";

      long startTime = System.currentTimeMillis();

      HttpResponse<JsonNode> response = Unirest.post(URL_SEARCH_DOCTOR)
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

   // doctorName
   @Test
   public void testDoctorName() throws UnirestException {
      String query = "{\n" +
              "  \"patientEmail\" : \"" + patient.getEmail() + "\", \n" +
              "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "  \"listIndex\":1,\n" +
              "  \"speciality\":\"\",\n" +
              "  \"doctorName\":\"Arab\"\n" +
              "}\n";

      long startTime = System.currentTimeMillis();

      HttpResponse<JsonNode> response = Unirest.post(URL_SEARCH_DOCTOR)
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

   // all
   @Test
   public void testAll() throws UnirestException {
      String query = "{\n" +
              "  \"patientEmail\" : \"" + patient.getEmail() + "\", \n" +
              "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "  \"listIndex\":1,\n" +
              "  \"speciality\":\"All\",\n" +
              "  \"doctorName\":\"Arab\"\n" +
              "}\n";

      long startTime = System.currentTimeMillis();

      HttpResponse<JsonNode> response = Unirest.post(URL_SEARCH_DOCTOR)
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

}
