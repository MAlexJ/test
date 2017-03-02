package controller.test.user;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.URL_SIGN_UP;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class SignUpDoctorTest {

   private final static Double LATITUDE = 31.5550281;
   private final static Double LONGITUDE = 74.3112118;

   @Test
   public void SignUpDoctor_FACEBOOK() throws UnirestException {

      // create EMAIL
      String email = "doctor_" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10) / 100;
      Double longitude = LONGITUDE + new Random().nextInt(20) / 100;

      // #1 create response
      String response = sing_up_FACEBOOK(email, latitude.toString(), longitude.toString());

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

      String expectResponse = "{\"user\":{\"userId\":" + id + ",\"religion\":\"Religion\",\"longitude\":\"" + longitude.toString() + "\",\"consultationFee\":0,\"latitude\":\"" + latitude.toString() + "\",\"dateOfBirth\":1231797600000,\"deviceId\":\"307564180daba553\",\"deviceModel\":\"Meizu m2 note\",\"email\":\"" + email + "\",\"gender\":\"male\",\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\"fullName\":\"Alex Maximov\",\"locale\":\"ru_RU\",\"mobile\":\"77777777777\",\"osType\":\"android\",\"osVersion\":\"22\",\"rating\":0,\"role\":\"DOCTOR\",\"timeZoneId\":\"Europe/Kiev\",\"versionCode\":\"1\",\"languages\":[{\"language\":\"Arabic\"},{\"language\":\"English\"}],\"loginTypes\":[{\"loginMode\":\"FACEBOOK\"}],\"title\":\"Dr\"},\"status\":true,\"sessionToken\":\"" + sessionToken + "\",\"statusCode\":200,\"message\":\"Sign Up successful\"}";
      JsonNode expectResponseBody = new JsonNode(expectResponse);

      // #5 ASSERT
      assertEquals(expectResponseBody.toString(), actualResponseBody.toString());

   }


   @Test
   public void SignUpDoctor_GPUSS() throws UnirestException {

      // create EMAIL
      String email = "doctor_" + new Random().nextInt(234663400) + "@gmail.com";
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

      String expectResponse = "{\"user\":{\"userId\":" + id + ",\"religion\":\"Religion\",\"longitude\":\"" + longitude.toString() + "\",\"latitude\":\"" + latitude.toString() + "\",\"consultationFee\":0,\"dateOfBirth\":1231797600000,\"deviceId\":\"307564180daba553\",\"deviceModel\":\"Meizu m2 note\",\"email\":\"" + email + "\",\"gender\":\"male\",\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\"fullName\":\"Alex Maximov\",\"locale\":\"ru_RU\",\"mobile\":\"77777777777\",\"osType\":\"android\",\"osVersion\":\"22\",\"rating\":0,\"role\":\"DOCTOR\",\"timeZoneId\":\"Europe/Kiev\",\"versionCode\":\"1\",\"languages\":[{\"language\":\"Arabic\"},{\"language\":\"English\"}],\"loginTypes\":[{\"loginMode\":\"GPLUS\"}],\"title\":\"Dr\"},\"status\":true,\"statusCode\":200,\"sessionToken\":\"" + sessionToken + "\",\"message\":\"Sign Up successful\"}";
      JsonNode expectResponseBody = new JsonNode(expectResponse);

      // #5 ASSERT
      assertEquals(expectResponseBody.toString(), actualResponseBody.toString());

   }


   @Test
   public void SignUpDoctor_EMAIL() throws UnirestException {

      // create EMAIL
      String email = "doctor_" + new Random().nextInt(20000123) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10);
      Double longitude = LONGITUDE + new Random().nextInt(20);
      String response = sing_up_EMAIL(email, latitude.toString(), longitude.toString());


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

      String expectResponse = "{\"user\":{\"userId\":" + id + ",\"religion\":\"Religion\",\"longitude\":\"" + longitude.toString() + "\",\"latitude\":\"" + latitude.toString() + "\",\"consultationFee\":0,\"dateOfBirth\":1231797600000,\"deviceId\":\"307564180daba553\",\"deviceModel\":\"Meizu m2 note\",\"email\":\"" + email + "\",\"gender\":\"male\",\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\"fullName\":\"Alex Maximov\",\"locale\":\"ru_RU\",\"mobile\":\"77777777777\",\"osType\":\"android\",\"osVersion\":\"22\",\"rating\":0,\"role\":\"DOCTOR\",\"timeZoneId\":\"Europe/Kiev\",\"versionCode\":\"1\",\"languages\":[{\"language\":\"Arabic\"},{\"language\":\"English\"}],\"loginTypes\":[{\"loginMode\":\"EMAIL\"}],\"title\":\"Dr\"},\"status\":true,\"statusCode\":200,\"sessionToken\":\"" + sessionToken + "\",\"message\":\"Sign Up successful\"}";
      JsonNode expectResponseBody = new JsonNode(expectResponse);

      // #5 ASSERT
      assertEquals(expectResponseBody.toString(), actualResponseBody.toString());

   }


   @Test
   public void SignUp_EMAIL_FACEBOOK_GPLUS() throws UnirestException {

      // #1
      String email = "doctor_" + new Random().nextInt(200012340) + "@gmail.com";
      Double latitude = LATITUDE + new Random().nextInt(10);
      Double longitude = LONGITUDE + new Random().nextInt(20);
      String response = sing_up_EMAIL(email, latitude.toString(), longitude.toString());

      Unirest.post(URL_SIGN_UP)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body(response).asJson();

      // #2

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

      String expectResponse = "{\"user\":{\"userId\":" + id + ",\"religion\":\"Religion\",\"longitude\":\"" + longitude.toString() + "\",\"latitude\":\"" + latitude.toString() + "\",\"consultationFee\":0,\"dateOfBirth\":1231797600000,\"deviceId\":\"307564180daba553\",\"deviceModel\":\"Meizu m2 note\",\"email\":\"" + email + "\",\"gender\":\"male\",\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\"fullName\":\"Alex Maximov\",\"locale\":\"ru_RU\",\"mobile\":\"77777777777\",\"osType\":\"android\",\"osVersion\":\"22\",\"rating\":0,\"role\":\"DOCTOR\",\"timeZoneId\":\"Europe/Kiev\",\"versionCode\":\"1\",\"languages\":[{\"language\":\"Arabic\"},{\"language\":\"English\"}],\"loginTypes\":[{\"loginMode\":\"EMAIL\"},{\"loginMode\":\"FACEBOOK\"},{\"loginMode\":\"GPLUS\"}],\"title\":\"Dr\"},\"status\":true,\"statusCode\":200,\"sessionToken\":\"" + sessionToken + "\",\"message\":\"Sign Up successful\"}";
      JsonNode expectResponseBody = new JsonNode(expectResponse);

      // #5 ASSERT
      assertEquals(expectResponseBody.toString(), actualResponseBody.toString());
   }


   // *** UTIL method *****//

   private String sing_up_FACEBOOK(String email, String latitude, String longitude) {
      return "{\n" +
              "\"fullName\":\"Alex Maximov\",\n" +
              "\"religion\":\"Religion\",\n" +
              "\"loginMode\":\"FACEBOOK\",\n" +
              "\"email\":\" " + email + "\",\n" +
              "\"mobile\":\"77777777777\",\n" +
              "\"deviceModel\":\"Meizu m2 note\",\n" +
              "\"timeZoneId\":\"Europe\\/Kiev\",\n" +
              "\"locale\":\"ru_RU\",\n" +
              "\"deviceId\":\"307564180daba553\",\n" +
              "\"latitude\": \"" + latitude + "\",\n" +
              "\"longitude\": \"" + longitude + "\",\n" +
              "\"versionCode\":1,\n" +
              "\"osType\":\"android\",\n" +
              "\"osVersion\":22,\n" +
              "\"gender\":\"Male\",\n" +
              "\"role\":\"Doctor\",\n" +
              "\"dateOfBirth\":\"1231797600000\",\n" +
              "\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\n" +
              "\n" +
              "\"title\":\"Dr\",\n" +
              "\n" +
              "\"specialties\":[\n" +
              "\t{\"name\": \"Allergist\"},\n" +
              "\t{\"name\": \"Dentist\"}\n" +
              "\t],\n" +
              "\n" +
              "\"languages\": [\n" +
              "\t{\"language\": \"Arabic\"},\n" +
              "\t{\"language\": \"English\"}\n" +
              "\t]\n" +
              "\t\n" +
              "}\n";
   }

   private String sing_up_GPLUS(String email, String latitude, String longitude) {
      return "{\n" +
              "\"fullName\":\"Alex Maximov\",\n" +
              "\"loginMode\":\"GPLUS\",\n" +
              "\"religion\":\"Religion\",\n" +
              "\"email\":\" " + email + "\",\n" +
              "\"latitude\": \"" + latitude + "\",\n" +
              "\"longitude\": \"" + longitude + "\",\n" +
              "\"mobile\":\"77777777777\",\n" +
              "\"deviceModel\":\"Meizu m2 note\",\n" +
              "\"timeZoneId\":\"Europe\\/Kiev\",\n" +
              "\"locale\":\"ru_RU\",\n" +
              "\"deviceId\":\"307564180daba553\",\n" +
              "\"versionCode\":1,\n" +
              "\"osType\":\"android\",\n" +
              "\"osVersion\":22,\n" +
              "\"gender\":\"Male\",\n" +
              "\"role\":\"Doctor\",\n" +
              "\"dateOfBirth\":\"1231797600000\",\n" +
              "\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\n" +
              "\n" +
              "\"title\":\"Dr\",\n" +
              "\n" +
              "\"specialties\":[\n" +
              "\t{\"name\": \"Allergist\"},\n" +
              "\t{\"name\": \"Dentist\"}\n" +
              "\t],\n" +
              "\n" +
              "\"languages\": [\n" +
              "\t{\"language\": \"Arabic\"},\n" +
              "\t{\"language\": \"English\"}\n" +
              "\t]\n" +
              "}\n";
   }

   private String sing_up_EMAIL(String email, String latitude, String longitude) {
      return "{\n" +
              "\"fullName\":\"Alex Maximov\",\n" +
              "\"loginMode\":\"Email\",\n" +
              "\"religion\":\"Religion\",\n" +
              "\"email\":\"" + email + "\",\n" +
              "\"latitude\": \"" + latitude + "\",\n" +
              "\"longitude\": \"" + longitude + "\",\n" +
              "\"password\":\"1111\",\n" +
              "\"mobile\":\"77777777777\",\n" +
              "\"deviceModel\":\"Meizu m2 note\",\n" +
              "\"timeZoneId\":\"Europe\\/Kiev\",\n" +
              "\"locale\":\"ru_RU\",\n" +
              "\"deviceId\":\"307564180daba553\",\n" +
              "\"versionCode\":1,\n" +
              "\"osType\":\"android\",\n" +
              "\"osVersion\":22,\n" +
              "\"gender\":\"Male\",\n" +
              "\"role\":\"Doctor\",\n" +
              "\"dateOfBirth\":\"1231797600000\",\n" +
              "\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\n" +
              "\n" +
              "\"title\":\"Dr\",\n" +
              "\n" +
              "\"specialties\":[\n" +
              "\t{\"name\": \"Allergist\"},\n" +
              "\t{\"name\": \"Dentist\"}\n" +
              "\t],\n" +
              "\n" +
              "\"languages\": [\n" +
              "\t{\"language\": \"Arabic\"},\n" +
              "\t{\"language\": \"English\"}\n" +
              "\t]\n" +
              "}";
   }


   public static User singUn_To_App_Doctor(String email, String password, String loginMode, String latitude, String longitude) {
      User user = new User();

      String request = "{\n" +
              "\"fullName\":\"Alex Maximov\",\n" +
              "\"religion\":\"Religion\",\n" +
              "\"loginMode\":\"" + loginMode + "\",\n" +
              "\"email\":\"" + email + "\",\n" +
              "\"latitude\": \"" + latitude + "\",\n" +
              "\"longitude\": \"" + longitude + "\",\n" +
              "\"password\":\"" + password + "\",\n" +
              "\"mobile\":\"77777777777\",\n" +
              "\"deviceModel\":\"Meizu m2 note\",\n" +
              "\"timeZoneId\":\"Europe\\/Kiev\",\n" +
              "\"locale\":\"ru_RU\",\n" +
              "\"deviceId\":\"307564180daba553\",\n" +
              "\"versionCode\":1,\n" +
              "\"osType\":\"android\",\n" +
              "\"osVersion\":22,\n" +
              "\"gender\":\"Male\",\n" +
              "\"role\":\"Doctor\",\n" +
              "\"dateOfBirth\":\"1231797600000\",\n" +
              "\"imageUrl\":\"http://www.sande.cl/Images/Sitio/loginnew2.png\",\n" +
              "\n" +
              "\"title\":\"Dr\",\n" +
              "\n" +
              "\"specialties\":[\n" +
              "\t{\"name\": \"Allergist\"},\n" +
              "\t{\"name\": \"Dentist\"}\n" +
              "\t],\n" +
              "\n" +
              "\"languages\": [\n" +
              "\t{\"language\": \"Arabic\"},\n" +
              "\t{\"language\": \"English\"}\n" +
              "\t]\n" +
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
