package controller.test.language;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;

/**
 * @author malex
 */
public class LanguageControllerTest {

   @Test
   public void test() throws UnirestException {
      // #1 Create DOCTOR
      String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
      Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
      String passwordDOCTOR = "12345678";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
      String email = doctor.getEmail();
      String sessionToken = doctor.getSessionToken();

      HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.post(URL_GET_LANGUAGE)
              .header("content-type", "application/json")
              .header("cache-control", "no-cache")
              .body("{\r\n\t\"email\":\"" + email + "\",\r\n\t\"sessionToken\":\"" + sessionToken + "\"\r\n}")
              .asJson();


      JSONObject jsonObject = jsonNodeHttpResponse.getBody().getObject();

      String message = (String) jsonObject.get("message");


      System.out.println(jsonObject);

   }

}
