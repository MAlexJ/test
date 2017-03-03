package controller.test.user;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class GetUserByIdTest {

   @Test
   public void test_PATIENT() {

      // #1 Create PATIENT
      String emailPATIENT = "patient_p" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String sessionToken = patient.getSessionToken();
      String email = patient.getEmail();

      int id = patient.getId();
      System.out.println("Patient id: " + id);

      JSONObject jsonObject = getUserById(id);

      String emailActual = (String) jsonObject.getJSONObject("user").get("email");
      String sessionTokenActual = (String) jsonObject.getJSONObject("user").get("sessionToken");

      assertEquals(email, emailActual);
      assertEquals(sessionToken, sessionTokenActual);
   }

   @Test
   public void test_DOCTOR() {
      // #1 Create PATIENT
      String emailPATIENT = "doctor_p" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpDoctorTest.singUn_To_App_Doctor(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String sessionToken = patient.getSessionToken();
      String email = patient.getEmail();

      int id = patient.getId();
      System.out.println("Patient id: " + id);

      JSONObject jsonObject = getUserById(id);

      String emailActual = (String) jsonObject.getJSONObject("user").get("email");
      String sessionTokenActual = (String) jsonObject.getJSONObject("user").get("sessionToken");

      assertEquals(email, emailActual);
      assertEquals(sessionToken, sessionTokenActual);
   }


   /**
    * Get User by ID
    */
   public static JSONObject getUserById(int id) {
      String request = "{\n" +
              "\"userId\":\"" + id + "\"\n" +
              "}";

      HttpResponse<JsonNode> actualResponse;
      try {
         actualResponse = Unirest.post(URL_GET_USER_BY_ID)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .body(request).asJson();
      } catch (UnirestException e) {
         throw new RuntimeException("error sing up doctor");
      }

      // get values
      return actualResponse.getBody().getObject();
   }


}
