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
public class SignUpPatientTest {


	// *** UTIL method *****//

	static HttpResponse<JsonNode> getJsonNode_From_SignUpPatient_FACEBOOK(String response) throws UnirestException {
		return Unirest.post(URL_SIGN_UP)
				  .header("content-type", "application/json")
				  .header("cache-control", "no-cache")
				  .body(response).asJson();
	}

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
