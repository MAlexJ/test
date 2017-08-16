package check_email_test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.CHECK_EMAIL;

public class CheckEmailAPI_TEST {

	/**
	 * If the user registers for the first time ( loginType: Email/Facebook or GPlus )
	 * <p>
	 * Response :
	 * "status": false,
	 * "statusCode": 200,
	 * "message": "Email not found in database."
	 */
	@Test
	public void test() {

		/* >>>> login type: EMAIL <<<< */

		// #1 create random email
		String email = "y_email_" + new Random().nextInt(243656300) + "@y.com";
		// #2 send request
		checkEmailAPI(email, "EMAIL");

	}

	private JSONObject checkEmailAPI(String email, String loginType) {

		// #1 create request
		String request = "";

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(CHECK_EMAIL)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		System.out.println("Request param: \n" + "URL: " + CHECK_EMAIL + "\n" + "json: " + request + "\n");

		// #3 result
		return actualResponse.getBody().getObject();

	}

}