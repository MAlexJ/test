package controller.test.user;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.URL_SIGN_IN;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * @author malex
 */
public class SingInTest {

   private final static Double LATITUDE = 31.5550281;
   private final static Double LONGITUDE = 74.3112118;


   @Test
   public void SignIn_EMAIL() throws UnirestException {

      // #1 create user data
      String email = "patient_" + new Random().nextInt(20000123) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10);
      Double longitude = LONGITUDE + new Random().nextInt(20);
      String loginMode = "Email";
      String password = "12452556AA";

      // #2 SignUp new user
      String responseUp = SignUpPatientTest.sing_up_EMAIL(email, latitude.toString(), longitude.toString(), password);
      HttpResponse<JsonNode> jsonNode_from_signUpPatient_facebook = SignUpPatientTest.getJsonNode_From_SignUpPatient_FACEBOOK(responseUp);
      String sessionToken_FROM_SING_UP = (String) jsonNode_from_signUpPatient_facebook.getBody().getObject().get("sessionToken");

      // #3 SignIn
      HttpResponse<JsonNode> jsonNodeHttpResponse = postSingInToServer(email, password, loginMode);
      JSONObject jsonObject = jsonNodeHttpResponse.getBody().getObject();

      // #4 actual values from response
      String messageRes = (String) jsonObject.get("message");
      String sessionToken_FROM_SING_IN = (String) jsonObject.get("sessionToken");
      Boolean statusRes = (Boolean) jsonObject.get("status");
      String actualEmail = (String) jsonObject.getJSONObject("user").get("email");

      // #5 Check expect and actual values
      checkParameters(email, actualEmail, Boolean.TRUE, statusRes, "Sign in successful", messageRes, sessionToken_FROM_SING_UP, sessionToken_FROM_SING_IN);
   }

   @Test
   public void SignIn_GPLUS() throws UnirestException {

      // #1 create user data
      String email = "patient_" + new Random().nextInt(20000123) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10);
      Double longitude = LONGITUDE + new Random().nextInt(20);
      String loginMode = "GPLUS";

      // #2 SignUp new user
      String responseUp = SignUpPatientTest.sing_up_GPLUS(email, latitude.toString(), longitude.toString());
      HttpResponse<JsonNode> jsonNode_from_signUpPatient_facebook = SignUpPatientTest.getJsonNode_From_SignUpPatient_FACEBOOK(responseUp);
      String sessionToken_FROM_SING_UP = (String) jsonNode_from_signUpPatient_facebook.getBody().getObject().get("sessionToken");

      // #3 SignIn
      HttpResponse<JsonNode> jsonNodeHttpResponse = postSingInToServer(email, "", loginMode);
      JSONObject jsonObject = jsonNodeHttpResponse.getBody().getObject();

      // #4 actual values from response
      String messageRes = (String) jsonObject.get("message");
      String sessionToken_FROM_SING_IN = (String) jsonObject.get("sessionToken");
      Boolean statusRes = (Boolean) jsonObject.get("status");
      String actualEmail = (String) jsonObject.getJSONObject("user").get("email");

      // #5 Check expect and actual values
      checkParameters(email, actualEmail, Boolean.TRUE, statusRes, "Sign in successful", messageRes, sessionToken_FROM_SING_UP, sessionToken_FROM_SING_IN);
   }


   @Test
   public void SignIn_FACEBOOK() throws UnirestException {

      // #1 create user data
      String email = "patient_" + new Random().nextInt(20000123) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10);
      Double longitude = LONGITUDE + new Random().nextInt(20);
      String loginMode = "FACEBOOK";

      // #2 SignUp new user
      String responseUp = SignUpPatientTest.sing_up_FACEBOOK(email, latitude.toString(), longitude.toString());
      HttpResponse<JsonNode> jsonNode_from_signUpPatient_facebook = SignUpPatientTest.getJsonNode_From_SignUpPatient_FACEBOOK(responseUp);
      String sessionToken_FROM_SING_UP = (String) jsonNode_from_signUpPatient_facebook.getBody().getObject().get("sessionToken");

      // #3 SignIn
      HttpResponse<JsonNode> jsonNodeHttpResponse = postSingInToServer(email, "", loginMode);
      JSONObject jsonObject = jsonNodeHttpResponse.getBody().getObject();

      // #4 actual values from response
      String messageRes = (String) jsonObject.get("message");
      String sessionToken_FROM_SING_IN = (String) jsonObject.get("sessionToken");
      Boolean statusRes = (Boolean) jsonObject.get("status");
      String actualEmail = (String) jsonObject.getJSONObject("user").get("email");

      // #5 Check expect and actual values
      checkParameters(email, actualEmail, Boolean.TRUE, statusRes, "Sign in successful", messageRes, sessionToken_FROM_SING_UP, sessionToken_FROM_SING_IN);
   }


   // Check parameters
   private void checkParameters(String email, String actualEmail, Boolean status, Boolean actualStatus, String message, String actualMessage, String sessionTokenSingUp, String sessionTokenSingIn) {
      assertEquals(email, actualEmail);
      assertEquals(status, actualStatus);
      assertEquals(message, actualMessage);
      assertFalse(sessionTokenSingUp.equals(sessionTokenSingIn));
   }


   // Post the request with parameters to the server
   private HttpResponse<JsonNode> postSingInToServer(String email, String password, String loginMode) throws UnirestException {
      return Unirest.post(URL_SIGN_IN)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .header("postman-token", "629db750-ea65-0fed-62b1-6b23c825c21f")
              .body("{\r\n\"loginMode\":\"" + loginMode + "\",\r\n\"email\":\"" + email + "\",\r\n\"password\":\"" + password + "\"\r\n}\r\n")
              .asJson();
   }


   /**
    * Sing In user
    */
   public static User singIn_To_App(String email, String loginMode, String password) {

      HttpResponse<JsonNode> jsonNodeHttpResponse;
      try {
         jsonNodeHttpResponse = Unirest.post(URL_SIGN_IN)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .header("postman-token", "629db750-ea65-0fed-62b1-6b23c825c21f")
                 .body("{\r\n\"loginMode\":\"" + loginMode + "\",\r\n\"email\":\"" + email + "\",\r\n\"password\":\"" + password + "\"\r\n}\r\n")
                 .asJson();
      } catch (UnirestException e) {
         throw new RuntimeException("Error post sing in");
      }

      JSONObject jsonObject = jsonNodeHttpResponse.getBody().getObject();

      User user = new User();

      user.setEmail((String) jsonObject.getJSONObject("user").get("email"));
      user.setSessionToken((String) jsonObject.get("sessionToken"));

      return user;
   }
}
