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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author malex
 */
public class Follow_UnFollow_UserTest {


   @Test
   public void testFollowUser() {

      // #1 Create PATIENT
      String emailPATIENT = "patient_p" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());

      // check user
      checkUser(patient);

      // #2 Create DOCTOR
      String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
      Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
      String passwordDOCTOR = "12345678";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
      checkUser(doctor);

      // #3 followUser
      followUser(patient, doctor);
   }

   @Test
   public void testUnFollowUser() {

      // #1 Create PATIENT
      String emailPATIENT = "patient_p" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());

      // check user
      checkUser(patient);

      // #2 Create DOCTOR
      String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
      Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
      String passwordDOCTOR = "12345678";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
      checkUser(doctor);

      // #3 followUser
      followUser(patient, doctor);

      // #4 followUser
      unFollowUser(patient, doctor);
   }


   /**
    * Follow User
    */
   public static void followUser(User patient, User doctor) {

      String userEmail = patient.getEmail();
      String sessionToken = patient.getSessionToken();
      String doctorEmail = doctor.getEmail();

      // create request
      String request = "{\n" +
              "\"userEmail\": \"" + userEmail + "\",  \n" +
              "\"doctorEmail\": \"" + doctorEmail + "\",  \n" +
              "\"sessionToken\":\"" + sessionToken + "\"  \n" +
              "}";

      // post response
      HttpResponse<JsonNode> actualResponse;
      try {
         actualResponse = Unirest.post(URL_FOLLOW_USER)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .body(request).asJson();
      } catch (UnirestException e) {
         throw new RuntimeException("error sing up doctor");
      }

      // get response
      JSONObject jsonObject = actualResponse.getBody().getObject();
      checkResponseFollow(jsonObject);
   }

   /**
    * Follow User
    */
   public static void unFollowUser(User patient, User doctor) {

      String userEmail = patient.getEmail();
      String sessionToken = patient.getSessionToken();
      String doctorEmail = doctor.getEmail();

      // create request
      String request = "{\n" +
              "\"userEmail\": \"" + userEmail + "\",  \n" +
              "\"doctorEmail\": \"" + doctorEmail + "\",  \n" +
              "\"sessionToken\":\"" + sessionToken + "\"  \n" +
              "}";

      // post response
      HttpResponse<JsonNode> actualResponse;
      try {
         actualResponse = Unirest.post(URL_UN_FOLLOW_USER)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .body(request).asJson();
      } catch (UnirestException e) {
         throw new RuntimeException("error sing up doctor");
      }

      // get response
      JSONObject jsonObject = actualResponse.getBody().getObject();
      checkResponseUnFollow(jsonObject);
   }


   // check user
   private void checkUser(User patient) {
      assertTrue(!patient.getEmail().isEmpty());
      assertTrue(!patient.getSessionToken().isEmpty());
   }

   // check follow
   private static void checkResponseFollow(JSONObject jsonObject) {

      String message = (String) jsonObject.get("message");
      Boolean status = (Boolean) jsonObject.get("status");
      Integer statusCode = (Integer) jsonObject.get("statusCode");

      assertEquals("Doctor followed successfully", message);
      assertEquals(Boolean.TRUE, status);
      assertEquals(new Integer(200), statusCode);
   }

   // check unfollow
   private static void checkResponseUnFollow(JSONObject jsonObject) {
      String message = (String) jsonObject.get("message");
      Boolean status = (Boolean) jsonObject.get("status");
      Integer statusCode = (Integer) jsonObject.get("statusCode");

      assertEquals("Doctor unFollowed successfully", message);
      assertEquals(Boolean.TRUE, status);
      assertEquals(new Integer(200), statusCode);
   }


}
