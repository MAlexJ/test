package controller.test.user;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.User;
import org.json.JSONObject;

import static constants.Constant.URL_SIGN_UP;

/**
 * @author malex
 */
public class SignUpDoctorTest {

	// *** UTIL method *****//

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
				  "\"title\":\"Dr\"" +
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
