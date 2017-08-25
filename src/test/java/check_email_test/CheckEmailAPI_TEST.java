package check_email_test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * New TEST
 */
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
	public void testFirstRegisters() {

		// Test #1 for loginType: EMAIL
		testFirstLogin("EMAIL");

		// Test #2 for loginType: GPLUS
		testFirstLogin("GPLUS");

		// Test #3 for loginType: FACEBOOK
		testFirstLogin("FACEBOOK");
	}

	/**
	 * If the user already has an account then -> SignIn
	 * Response for 'EMAIL' loginType:
	 * "status": true,
	 * "statusCode": 200,
	 * "message": "You already have the EMAIL account."
	 */
	@Test
	public void testLoginTypeEmail() {

		// #1 sign up user
		String emailDOCTOR = "ddest" + new Random().nextInt(24000300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";
		String loginModeDOCTOR = "EMAIL";

		User user = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

		// #2 check email
		JSONObject response = checkEmailAPI(user.getEmail(), "EMAIL");

		// #3 asserts
		try {
			assertNull(response.getJSONObject("user"));
		} catch (JSONException ex) {
			// ignore
		}
		assertEquals("You already have the " + loginModeDOCTOR + " account.", response.getString("message"));
		assertEquals(true, response.getBoolean("status"));
		assertEquals(200, response.getInt("statusCode"));
	}

	/**
	 * If the user already has an account -> SignIn
	 * Response for 'FACEBOOK or GPLUS' loginType:
	 * "status": true,
	 * "statusCode": 200,
	 * "message": "You already have the FACEBOOK or GPLUS account."
	 */
	@Test
	public void testLoginTypeGPLUSandFacebook() {

		// #1 FACEBOOK
		testFirstLoginGplusAndFacebook("FACEBOOK");

		// #2 GPLUS
		testFirstLoginGplusAndFacebook("GPLUS");

	}

	/**
	 * The user already has an account with one social network
	 * and he tries to register an account with another social network.
	 */
	@Test
	public void testIncorrectLoginTypeSocialNetwork() {

		// #1 the user has login type: FACEBOOK and he wants to register GPLUS
		testSeveralSocialNetworks("FACEBOOK", "GPLUS");


		// #2 the user has login type: GPLUS and he wants to register FACEBOOK
		testSeveralSocialNetworks("GPLUS", "FACEBOOK");

	}

	@Test
	public void testIncorrectLoginTypeMoreThanTwo() {

		// #1 the user has login type: FACEBOOK + EMAIL and he wants to register GPLUS
		testSeveralSocialNetworksWithEmail("EMAIL", "FACEBOOK", "GPLUS");

		// #2 the user has login type: GPLUS + EMAIL and he wants to register FACEBOOK
		testSeveralSocialNetworksWithEmail("EMAIL", "GPLUS", "FACEBOOK");

	}


	private void testSeveralSocialNetworksWithEmail(String firstLType, String secondLType, String loginTypeForReReg) {
		// #1 sign up user
		String emailDOCTOR = "ddest_tt" + new Random().nextInt(24000300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";

		SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, firstLType, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
		SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, secondLType, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

		// #2 check email
		JSONObject response = checkEmailAPI(emailDOCTOR, loginTypeForReReg);

		// #3 asserts
		assertEquals("To register an account, you can only use one of the social networks Facebook or G+. You already have the " + secondLType + " account.", response.getString("message"));
		assertEquals(false, response.getBoolean("status"));
		assertEquals(501, response.getInt("statusCode"));

		try {
			assertNull(response.getJSONObject("user"));
		} catch (JSONException ex) {
			// ignore
		}

	}


	private void testSeveralSocialNetworks(String loginTypeForReg, String loginTypeForReReg) {

		// #1 sign up user
		String emailDOCTOR = "ddest_tt" + new Random().nextInt(24000300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";

		User user = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginTypeForReg, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

		// #2 check email
		JSONObject response = checkEmailAPI(user.getEmail(), loginTypeForReReg);

		// #3 asserts
		assertEquals("To register an account, you can only use one of the social networks Facebook or G+. You already have the " + loginTypeForReg + " account.", response.getString("message"));
		assertEquals(false, response.getBoolean("status"));
		assertEquals(501, response.getInt("statusCode"));

		try {
			assertNull(response.getJSONObject("user"));
		} catch (JSONException ex) {
			// ignore
		}

	}

	/**
	 * If the user already has an "EMAIL" account and he wants to register one more FACEBOOK or GPLUS
	 */
	@Test
	public void testDifferentLoginType() {

		// #1 EMAIL + FACEBOOK
		testSecondLoginWithOneOfSocialNetwork("EMAIL", "FACEBOOK");

		// #2 EMAIL + GPLUS
		testSecondLoginWithOneOfSocialNetwork("EMAIL", "GPLUS");

		// #3 FACEBOOK + EMAIL
		testSecondLoginWithOneOfSocialNetwork("FACEBOOK", "EMAIL");

		// #4 GPLUS + EMAIL
		testSecondLoginWithOneOfSocialNetwork("GPLUS", "EMAIL");

	}


	private void testSecondLoginWithOneOfSocialNetwork(String loginForRe, String typeSocialNetwork) {

		// #1 sign up user
		String emailDOCTOR = "ddest_tt" + new Random().nextInt(24000300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";

		User user = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginForRe, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

		// #2 check email
		JSONObject response = checkEmailAPI(user.getEmail(), typeSocialNetwork);

		// #3 asserts
		assertEquals("Email not found in database for login type: " + typeSocialNetwork, response.getString("message"));
		assertEquals(false, response.getBoolean("status"));
		assertEquals(200, response.getInt("statusCode"));
	}


	private void testFirstLoginGplusAndFacebook(String loginModeDOCTOR) {

		// #1 sign up user for FACEBOOK
		String emailDOCTOR = "ddest" + new Random().nextInt(24000300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";
		User user = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

		// #2 check email
		JSONObject response = checkEmailAPI(user.getEmail(), loginModeDOCTOR);

		// 1.3 check response
		JSONObject expectUser = response.getJSONObject("user");
		assertEquals(expectUser.getString("email"), user.getEmail());
		assertEquals(expectUser.getString("sessionToken"), user.getSessionToken());
		assertEquals("You already have the " + loginModeDOCTOR + " account.", response.getString("message"));
		assertEquals(true, response.getBoolean("status"));
		assertEquals(200, response.getInt("statusCode"));
	}


	private void testFirstLogin(String loginType) {

		// 1.1 create random email
		String email = "y_email_" + new Random().nextInt(243656300) + "@y.com";

		// 1.2 send request
		JSONObject response = checkEmailAPI(email, loginType);

		// 1.3 check response
		assertEquals("Email not found in database.", response.getString("message"));
		assertEquals(false, response.getBoolean("status"));
		assertEquals(200, response.getInt("statusCode"));

		try {
			assertNull(response.getJSONObject("user"));
		} catch (JSONException ex) {
			// ignore
		}

	}


	private JSONObject checkEmailAPI(String email, String loginType) {

		JSONObject ob = new JSONObject();
		ob.put("email", email);
		ob.put("loginType", loginType);

		System.out.println("Request param: \n" + "URL: " + CHECK_EMAIL + "\n" + "json: " + ob);

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(CHECK_EMAIL)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(ob).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		System.out.println("Response param: \n" + "json: " + actualResponse.getBody().getObject() + "\n\n");

		// #3 result
		return actualResponse.getBody().getObject();

	}
}