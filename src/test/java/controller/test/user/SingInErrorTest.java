package controller.test.user;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

import java.util.Random;

/**
 * Test: User Authentication error code
 *
 * Description:
 * The Error User Authentication Code should not be 200
 * <p>
 * Abdul:
 * Error User Authentication code should be 401
 *
 * @author malex
 */
public class SingInErrorTest {

   @Test
   public void error_test() throws UnirestException {

      String email = "BLA_LBLA_LAAAAAA"+ new Random().nextInt(20000123) + "@gmail.com";
      Double latitude = new Random().nextDouble();
      Double longitude = new Random().nextDouble();
      String password = "12452556AA";

      String responseUp = SignUpPatientTest.sing_up_EMAIL(email, latitude.toString(), longitude.toString(), password);
      HttpResponse<JsonNode> response = SignUpPatientTest.getJsonNode_From_SignUpPatient_FACEBOOK(responseUp);


      System.out.println(response);
   }

}
