package getNearestDoctors;

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
public class GetNearestDoctorsFilterTest {

   private User patient;
   private Double latitudePATIENT;
   private Double longitudePATIENT;

   @Before
   public void beforeTest() {
      if (patient == null) {
         // #1 Create PATIENT
         String emailPATIENT = "patient_p_g" + new Random().nextInt(243656300) + "@gmail.com";
         latitudePATIENT = LATITUDE + new Random().nextInt(100);
         longitudePATIENT = LONGITUDE + new Random().nextInt(100);
         String passwordPATIENT = "0987654321";
         String loginModePATIENT = "EMAIL";
         patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      }
   }

   //language
   @Test
   public void languageTest() throws UnirestException {
      String query = "{ \n" +
              "  \"email\" : \"" + patient.getEmail() + "\", \n" +
              "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "  \"speciality\":\"\",\n" +
              "  \"language\":\"Eng\",\n" +
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

   //speciality
   @Test
   public void specialityTest() throws UnirestException {
      String query = "{ \n" +
              "  \"email\" : \"" + patient.getEmail() + "\", \n" +
              "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "  \"speciality\":\"Neu\",\n" +
              "  \"language\":\"\",\n" +
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

   //locationAddress
   @Test
   public void locationAddressTest() throws UnirestException {
      String query = "{ \n" +
              "  \"email\" : \"" + patient.getEmail() + "\", \n" +
              "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "  \"speciality\":\"\",\n" +
              "  \"language\":\"\",\n" +
              "  \"doctorName\":\"\",\n" +
              "  \"locationAddress\":\"Kiev\",\n" +
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

   //doctorName
   @Test
   public void doctorNameTest() throws UnirestException {
      String query = "{ \n" +
              "  \"email\" : \"" + patient.getEmail() + "\", \n" +
              "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "  \"speciality\":\"\",\n" +
              "  \"language\":\"\",\n" +
              "  \"doctorName\":\"Alex\",\n" +
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

   // all
   @Test
   public void allTest() throws UnirestException {
      String query = "{ \n" +
              "  \"email\" : \"" + patient.getEmail() + "\", \n" +
              "  \"sessionToken\":\"" + patient.getSessionToken() + "\", \n" +
              "  \"speciality\":\"Cat\",\n" +
              "  \"language\":\"Eng\",\n" +
              "  \"doctorName\":\"Alex\",\n" +
              "  \"locationAddress\":\"Kiev\",\n" +
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
}
