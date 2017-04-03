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
public class SignUpPatientTest {

   @Test
   public void SignUpPatient_FACEBOOK() throws UnirestException {

      // create EMAIL
      String email = "patient_" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10);
      Double longitude = LONGITUDE + new Random().nextInt(20);

      // #1 create response
      String response = sing_up_FACEBOOK(email, latitude.toString(), longitude.toString());

      // #2 POST RESPONSE
      HttpResponse<JsonNode> actualResponse = getJsonNode_From_SignUpPatient_FACEBOOK(response);

      // #3 ACTUAL
      JsonNode actualResponseBody = actualResponse.getBody();

      // #4 EXPECT
      JSONObject jsonObject = actualResponse.getBody().getObject();
      // get ID AND SESSION TOKEN
      Integer id = (Integer) jsonObject.getJSONObject("user").get("userId");
      String sessionToken = (String) jsonObject.get("sessionToken");

      String expectResponse = "{\"user\":{\"userId\":" + id + ",\"consultationFee\":0,\"dateOfBirth\":488160000000,\"email\":\"" + email + "\",\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\"fullName\":\"Alex Test\",\"latitude\":\"" + latitude + "\",\"longitude\":\"" + longitude + "\",\"rating\":0,\"role\":\"PATIENT\",\"loginTypes\":[{\"loginMode\":\"FACEBOOK\"}],\"identificationCard\":\"Identification Card\",\"insuranceCompany\":\"Insurance Company\",\"occupation\":\"Occupation\",\"streetAddress\":\"Street Address\",\"religion\":\"Religion\"},\"status\":true,\"statusCode\":200,\"sessionToken\":\"" + sessionToken + "\",\"message\":\"Sign Up successful\"}";
      JsonNode expectResponseBody = new JsonNode(expectResponse);

      // #5 ASSERT
      assertEquals(expectResponseBody.toString(), actualResponseBody.toString());

   }

   static HttpResponse<JsonNode> getJsonNode_From_SignUpPatient_FACEBOOK(String response) throws UnirestException {
      return Unirest.post(URL_SIGN_UP)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body(response).asJson();
   }


   @Test
   public void SignUpPatient_GPUSS() throws UnirestException {

      // create EMAIL
      String email = "patient_" + new Random().nextInt(234663400) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10);
      Double longitude = LONGITUDE + new Random().nextInt(20);
      String response = sing_up_GPLUS(email, latitude.toString(), longitude.toString());

      // #2 POST RESPONSE
      HttpResponse<JsonNode> actualResponse = Unirest.post(URL_SIGN_UP)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body(response).asJson();

      // #3 ACTUAL
      JsonNode actualResponseBody = actualResponse.getBody();

      // #4 EXPECT
      JSONObject jsonObject = actualResponse.getBody().getObject();
      // get ID AND SESSION TOKEN
      Integer id = (Integer) jsonObject.getJSONObject("user").get("userId");
      String sessionToken = (String) jsonObject.get("sessionToken");

      String expectResponse = "{\"user\":{\"userId\":" + id + ",\"consultationFee\":0,\"dateOfBirth\":488160000000,\"email\":\"" + email + "\",\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\"fullName\":\"Alex Test\",\"latitude\":\"" + latitude + "\",\"longitude\":\"" + longitude + "\",\"rating\":0,\"role\":\"PATIENT\",\"loginTypes\":[{\"loginMode\":\"GPLUS\"}],\"identificationCard\":\"Identification Card\",\"insuranceCompany\":\"Insurance Company\",\"occupation\":\"Occupation\",\"streetAddress\":\"Street Address\",\"religion\":\"Religion\"},\"sessionToken\":\"" + sessionToken + "\",\"status\":true,\"statusCode\":200,\"message\":\"Sign Up successful\"}";
      JsonNode expectResponseBody = new JsonNode(expectResponse);

      // #5 ASSERT
      assertEquals(expectResponseBody.toString(), actualResponseBody.toString());

   }


   @Test
   public void SignUpPatient_EMAIL() throws UnirestException {

      // create EMAIL
      String email = "patient_" + new Random().nextInt(20000123) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10) / 100;
      Double longitude = LONGITUDE + new Random().nextInt(20) / 100;
      String response = sing_up_EMAIL(email, latitude.toString(), longitude.toString(), "123456789");


      // #2 POST RESPONSE
      HttpResponse<JsonNode> actualResponse = Unirest.post(URL_SIGN_UP)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body(response).asJson();

      // #3 ACTUAL
      JsonNode actualResponseBody = actualResponse.getBody();


      // #4 EXPECT
      JSONObject jsonObject = actualResponse.getBody().getObject();
      // get ID AND SESSION TOKEN
      Integer id = (Integer) jsonObject.getJSONObject("user").get("userId");
      String sessionToken = (String) jsonObject.get("sessionToken");

      String expectResponse = "{\"user\":{\"userId\":" + id + ",\"consultationFee\":0,\"dateOfBirth\":488160000000,\"email\":\"" + email + "\",\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\"fullName\":\"Alex Test\",\"latitude\":\"" + latitude + "\",\"longitude\":\"" + longitude + "\",\"rating\":0,\"role\":\"PATIENT\",\"loginTypes\":[{\"loginMode\":\"EMAIL\"}],\"identificationCard\":\"Identification Card\",\"insuranceCompany\":\"Insurance Company\",\"occupation\":\"Occupation\",\"streetAddress\":\"Street Address\",\"religion\":\"Religion\"},\"status\":true,\"statusCode\":200,\"sessionToken\":\"" + sessionToken + "\",\"message\":\"Sign Up successful\"}";
      JsonNode expectResponseBody = new JsonNode(expectResponse);

      // #5 ASSERT
      assertEquals(expectResponseBody.toString(), actualResponseBody.toString());

   }

   @Test
   public void SignUp_EMAIL_FACEBOOK_GPLUS() throws UnirestException {

      // #1
      String email = "patient_" + new Random().nextInt(200012340) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10);
      Double longitude = LONGITUDE + new Random().nextInt(20);
      String response = sing_up_EMAIL(email, latitude.toString(), longitude.toString(), "1234567");

      Unirest.post(URL_SIGN_UP)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body(response).asJson();

      response = sing_up_FACEBOOK(email, latitude.toString(), longitude.toString());

      Unirest.post(URL_SIGN_UP)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body(response).asJson();

      // #3
      response = sing_up_GPLUS(email, latitude.toString(), longitude.toString());

      HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.post(URL_SIGN_UP)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body(response).asJson();

      // #3 ACTUAL
      JsonNode actualResponseBody = jsonNodeHttpResponse.getBody();

      // #4 EXPECT
      JSONObject jsonObject = jsonNodeHttpResponse.getBody().getObject();
      // get ID AND SESSION TOKEN
      Integer id = (Integer) jsonObject.getJSONObject("user").get("userId");
      String sessionToken = (String) jsonObject.get("sessionToken");

      String expectResponse = "{\"user\":{\"userId\":" + id + ",\"consultationFee\":0,\"chronicMedications\":[],\"dateOfBirth\":488160000000,\"email\":\"" + email + "\",\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\"fullName\":\"Alex Test\",\"latitude\":\"" + latitude + "\",\"longitude\":\"" + longitude + "\",\"rating\":0,\"role\":\"PATIENT\",\"loginTypes\":[{\"loginMode\":\"EMAIL\"},{\"loginMode\":\"FACEBOOK\"},{\"loginMode\":\"GPLUS\"}],\"languages\":[],\"identificationCard\":\"Identification Card\",\"chronicMedicalConditions\":[],\"insuranceCompany\":\"Insurance Company\",\"occupation\":\"Occupation\",\"streetAddress\":\"Street Address\",\"religion\":\"Religion\"},\"sessionToken\":\"" + sessionToken + "\",\"status\":true,\"statusCode\":200,\"message\":\"Sign Up successful\"}";
      JsonNode expectResponseBody = new JsonNode(expectResponse);

      // #5 ASSERT
      assertEquals(expectResponseBody.toString(), actualResponseBody.toString());
   }


   // *** UTIL method *****//

   static String sing_up_FACEBOOK(String email, String latitude, String longitude) {
      return "{\n" +
              "\"fullName\":\"Alex Test\",\n" +
              "\"loginMode\":\"FACEBOOK\",\n" +
              "\"email\":\"" + email + "\",\n" +
              "\"role\":\"Patient\",\n" +
              "\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\n" +
              "\"dateOfBirth\":\"488160000000\",\n" +
              "\n" +
              "\"latitude\": \"" + latitude + "\",\n" +
              "\"longitude\": \"" + longitude + "\",\n" +
              "\n" +
              "\"languages\": [\n" +
              "\t{\"language\": \"Arabic\"},\n" +
              "\t{\"language\": \"English\"}\n" +
              "\t],\n" +
              "\n" +
              "\n" +
              "\"identificationCard\":\"Identification Card\",\n" +
              "\"insuranceCompany\":\"Insurance Company\",\n" +
              "\"occupation\":\"Occupation\",\n" +
              "\"streetAddress\":\"Street Address\",\n" +
              "\"religion\":\"Religion\"\n" +
              "}";
   }

   static String sing_up_GPLUS(String email, String latitude, String longitude) {
      return "{\n" +
              "\"fullName\":\"Alex Test\",\n" +
              "\"loginMode\":\"GPLUS\",\n" +
              "\"email\":\"" + email + "\",\n" +
              "\"role\":\"Patient\",\n" +
              "\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\n" +
              "\"dateOfBirth\":\"488160000000\",\n" +
              "\"latitude\": \"" + latitude + "\",\n" +
              "\"longitude\": \"" + longitude + "\",\n" +
              "\n" +
              "\"languages\": [\n" +
              "\t{\"language\": \"Arabic\"},\n" +
              "\t{\"language\": \"English\"}\n" +
              "\t],\n" +
              "\n" +
              "\"identificationCard\":\"Identification Card\",\n" +
              "\"insuranceCompany\":\"Insurance Company\",\n" +
              "\"occupation\":\"Occupation\",\n" +
              "\"streetAddress\":\"Street Address\",\n" +
              "\"religion\":\"Religion\"\n" +
              "}";
   }

   static String sing_up_EMAIL(String email, String latitude, String longitude, String password) {
      return "{\n" +
              "\"fullName\":\"Alex Test\",\n" +
              "\"loginMode\":\"EMAIL\",\n" +
              "\"email\":\"" + email + "\",\n" +
              "\"password\":\"" + password + "\",\n" +
              "\"role\":\"Patient\",\n" +
              "\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\n" +
              "\"dateOfBirth\":\"488160000000\",\n" +
              "\n" +
              "\"latitude\": \"" + latitude + "\",\n" +
              "\"longitude\": \"" + longitude + "\",\n" +
              "\n" +
              "\n" +
              "\"languages\": [\n" +
              "\t{\"language\": \"Arabic\"},\n" +
              "\t{\"language\": \"English\"}\n" +
              "\t],\n" +
              "\n" +
              "\n" +
              "\"identificationCard\":\"Identification Card\",\n" +
              "\"insuranceCompany\":\"Insurance Company\",\n" +
              "\"occupation\":\"Occupation\",\n" +
              "\"streetAddress\":\"Street Address\",\n" +
              "\"religion\":\"Religion\"\n" +
              "}";
   }

   public static User singUn_To_App_Patient(String email, String password, String loginMode, String latitude, String longitude) {
      User user = new User();

      String request = "{\n" +
              "\"fullName\":\"Alex Test\",\n" +
              "\"loginMode\":\"" + loginMode + "\",\n" +
              "\"email\":\"" + email + "\",\n" +
              "\"password\":\"" + password + "\",\n" +
              "\"role\":\"Patient\",\n" +
              "\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\n" +
              "\"dateOfBirth\":\"488160000000\",\n" +
              "\n" +
              "\"latitude\": \"" + latitude + "\",\n" +
              "\"longitude\": \"" + longitude + "\",\n" +
              "\n" +
              "\n" +
              "\"languages\": [\n" +
              "\t{\"language\": \"Arabic\"},\n" +
              "\t{\"language\": \"English\"}\n" +
              "\t],\n" +
              "\n" +
              "\n" +
              "\"identificationCard\":\"Identification Card\",\n" +
              "\"insuranceCompany\":\"Insurance Company\",\n" +
              "\"occupation\":\"Occupation\",\n" +
              "\"streetAddress\":\"Street Address\",\n" +
              "\"religion\":\"Religion\"\n" +
              "}";


      // #2 POST RESPONSE
      HttpResponse<JsonNode> actualResponse;
      try {
         actualResponse = Unirest.post(URL_SIGN_UP)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .body(request).asJson();
      } catch (UnirestException e) {
         throw new RuntimeException("error sing up doctor");
      }

      // get values
      JSONObject jsonObject = actualResponse.getBody().getObject();

      user.setId((Integer) jsonObject.getJSONObject("user").get("userId"));
      user.setEmail((String) jsonObject.getJSONObject("user").get("email"));
      user.setSessionToken((String) jsonObject.get("sessionToken"));

      return user;
   }


}
