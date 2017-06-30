package updateUser;

import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import static constants.Constant.LATITUDE;
import static constants.Constant.LONGITUDE;
import static constants.Constant.URL_UPDATE_USER_PROFILE;
import static controller.test.user.GetUserByIdTest.getUserById;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by malex on 30.06.17.
 */
public class UpdateUserProfileWithCountryCode {


	@Test
	public void test() throws IOException {
		// #1 Create PATIENT
		String emailPATIENT = "patient_p" + new Date().getTime() + "@gmail.com";
		Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
		Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
		String passwordPATIENT = "12345678";
		String loginModePATIENT = "EMAIL";

		User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
		String sessionToken = patient.getSessionToken();
		int id = patient.getId();

		String email = "new_email" + new Date().getTime() + "@gmail.com";
		String mobile = "0672798493";
		String countryCode = "+3";

		// #2 send request
		MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
		multipart.addFormField("email", email);
		multipart.addFormField("sessionToken", sessionToken);
		multipart.addFormField("mobile", mobile);
		// >> countryCode
		multipart.addFormField("countryCode", countryCode);

		multipart.addFilePart("file", new File("image.jpg"));
		String response = multipart.finish(); // response from server.

		JSONObject jsonObject = new JSONObject(response);
		String path = (String) jsonObject.get("path");
		Boolean status = (Boolean) jsonObject.get("status");
		String message = (String) jsonObject.get("message");
		Integer statusCode = (Integer) jsonObject.get("statusCode");

		assertEquals("The profile of User successfully updated", message);
		assertEquals(Boolean.TRUE, status);
		assertEquals(new Integer(200), statusCode);
		System.out.println(response);

		// #4 validate user from DB
		JSONObject userById = getUserById(id);
		assertEquals(email, (String) userById.getJSONObject("user").get("email"));
		assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));
		assertEquals(mobile, (String) userById.getJSONObject("user").get("mobile"));
		// >>> countryCode
		assertEquals(countryCode, (String) userById.getJSONObject("user").get("countryCode"));

		assertTrue(!path.isEmpty());
	}

}