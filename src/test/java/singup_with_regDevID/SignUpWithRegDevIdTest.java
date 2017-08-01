package singup_with_regDevID;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SignUpWithRegDevIdTest {


	@Test
	public void testForDoctor() {

		// #1 create a new doctor
		String emailDOCTOR = "doctor_dtest" + new Random().nextInt(243656300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";
		String loginModeDOCTOR = "EMAIL";

		User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

		// #2 get parameter from response
		String email = doctor.getEmail();
		int patientId = doctor.getId();
		String sessionToken = doctor.getSessionToken();

		assertEquals(emailDOCTOR, email); // <<<< ASSERT
		System.out.println("ID: " + patientId + ", EMAIL: " + email + ", TOKEN: " + sessionToken);


		// #3 save ID DEV KEY
		String devKey = "TEST_ID 124121523121515235532";

		JSONObject jsonObject = regDevId(doctor, devKey);
		String message = jsonObject.getString("message");
		boolean status = jsonObject.getBoolean("status");

		// #4 assert response
		assertEquals("Device registered successfully", message);
		assertTrue(status);
		System.out.println("RESPONSE: \n" + jsonObject + "\n");

	}

	@Test
	public void testForPatient() {

		// #1 Create PATIENT
		String emailPATIENT = "patient_test_" + new Random().nextInt(243656300) + "@g.com";
		Double latitudePATIENT = LATITUDE + new Random().nextInt(100);
		Double longitudePATIENT = LONGITUDE + new Random().nextInt(100);
		String passwordPATIENT = "0987654321";
		String loginModePATIENT = "EMAIL";

		User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());


		// #2 get parameter from response
		String email = patient.getEmail();
		int patientId = patient.getId();
		String sessionToken = patient.getSessionToken();

		assertEquals(emailPATIENT, email); // <<<< ASSERT
		System.out.println("ID: " + patientId + ", EMAIL: " + email + ", TOKEN: " + sessionToken);


		// #3 save ID DEV KEY
		String devKey = "TEST_ID 124121523121515235532";

		JSONObject jsonObject = regDevId(patient, devKey);
		String message = jsonObject.getString("message");
		boolean status = jsonObject.getBoolean("status");

		assertEquals("Device registered successfully", message);
		assertTrue(status);
		System.out.println("RESPONSE: \n" + jsonObject + "\n");

	}


	// reg a new Dev ID
	private JSONObject regDevId(User user, String devId) {

		// #1 create request
		String request = "{ \n" +
				  "\"email\": \"" + user.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + user.getSessionToken() + "\",\n" +
				  "\"deviceKey\":\"" + devId + "\" \n" +
				  "}";

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(FCM_REGISTER_DEVICE)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("error sing up doctor");
		}

		System.out.println("Request param: \n" + "URL: " + FCM_REGISTER_DEVICE + "\n" + "json: " + request + "\n");

		// #3 result
		return actualResponse.getBody().getObject();
	}

}