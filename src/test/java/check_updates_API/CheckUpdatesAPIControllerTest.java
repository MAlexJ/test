package check_updates_API;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * New TEST
 */
public class CheckUpdatesAPIControllerTest {


	@Test
	public void testResetAll() {

		User patient = createPatient();

		// #1 reset all
		resetAll_API(patient, true, true, true, true);

		// #2 check reset all
		JSONObject jsonObject = check_API(patient);

		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));

	}

	@Test
	public void setNewValueAndReset() {

		User patient = createPatient();

		// 1 sett TRUE all values
		setStatus_API(true, true, true, true);

		// #2 check TRUE all
		JSONObject jsonObject = check_API(patient);
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertTrue(api.getBoolean("languageAPI"));
		assertTrue(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));

		// #3 reset all
		resetAll_API(patient, false, false, false, false);

		// #4 check FALSE all
		jsonObject = check_API(patient);
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));

	}

	@Test
	public void testTowUsers() {

		User patient = createPatient();

		User patient2 = createPatient();

		// #1 set languageAPI is TRUE
		setStatus_API(true, false, false, false);
		JSONObject jsonObject = check_API(patient);
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertTrue(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));

		jsonObject = check_API(patient2);
		api = jsonObject.getJSONObject("statusAPI");
		assertTrue(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));

		resetAll_API(patient, false, false, false, false);

		jsonObject = check_API(patient);
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));

		jsonObject = check_API(patient2);
		api = jsonObject.getJSONObject("statusAPI");
		assertTrue(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));

	}

	@Test
	public void testSetValue() {

		User patient = createPatient();

		// #1 set languageAPI is TRUE
		setStatus_API(true, false, false, false);
		JSONObject jsonObject = check_API(patient);
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertTrue(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));
		resetAll_API(patient, false, false, false, false);


		// #2 set languageAPI is TRUE
		setStatus_API(false, true, false, false);
		jsonObject = check_API(patient);
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));
		resetAll_API(patient, false, false, false, false);


		// #3 set languageAPI is TRUE
		setStatus_API(false, false, true, false);
		jsonObject = check_API(patient);
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertTrue(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));
		resetAll_API(patient, false, false, false, false);


		// #4 set languageAPI is TRUE
		setStatus_API(false, false, false, true);
		jsonObject = check_API(patient);
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));
		resetAll_API(patient, false, false, false, false);

		// #5 set languageAPI and specialityAPI are TRUE
		setStatus_API(false, true, false, true);
		jsonObject = check_API(patient);
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));
		resetAll_API(patient, false, false, false, false);


	}

	@Test
	public void resetAPI_test_1() {

		User patient = createPatient();

		// #1 set languageAPI is TRUE
		setStatus_API(true, true, true, true);

		// #2 reset API
		resetAll_API(patient, false, true, true, true);

		// check
		JSONObject jsonObject = check_API(patient);
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("languageAPI"));
		assertTrue(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));

	}

	@Test
	public void resetAPI_test_2() {

		User patient = createPatient();

		// #1 set languageAPI is TRUE
		setStatus_API(true, true, true, true);

		// #2 reset API
		resetAll_API(patient, false, true, false, true);

		// check
		JSONObject jsonObject = check_API(patient);
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));

	}


	private JSONObject setStatus_API(boolean languageAPI, boolean specialityAPI, boolean medicinesAPI, boolean symptomsAPI) {

		String request = "{\n" +
				  "\t\"setAPI\":{\n" +
				  "\t\t\"languageAPI\": " + languageAPI + ",\n" +
				  "\t\t\"specialityAPI\": " + specialityAPI + ",\n" +
				  "\t\t\"medicinesAPI\": " + medicinesAPI + ",\n" +
				  "\t\t\"symptomsAPI\": " + symptomsAPI + "\n" +
				  "\t}\n" +
				  "}";

		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(SET_STATUS_API)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		return actualResponse.getBody().getObject();
	}


	/**
	 * Reset API
	 */
	private JSONObject check_API(User user) {

		String request = "{\n" +
				  "  \"email\":\"" + user.getEmail() + "\",\n" +
				  "  \"sessionToken\":\"" + user.getSessionToken() + "\"\n" +
				  "}";

		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(CHECK_UPDATES_API)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		return actualResponse.getBody().getObject();
	}


	/**
	 * Reset API
	 */
	private JSONObject resetAll_API(User user, boolean languageAPI, boolean specialityAPI, boolean medicinesAPI, boolean symptomsAPI) {

		String request = "{\n" +
				  "\t\"email\":\"" + user.getEmail() + "\",\n" +
				  "\t\"sessionToken\":\"" + user.getSessionToken() + "\",\n" +
				  "\t\"resetAPI\": {\n" +
				  "  \t\t\"languageAPI\": " + languageAPI + ",\n" +
				  "\t\t\"specialityAPI\": " + specialityAPI + ",\n" +
				  "\t\t\"medicinesAPI\": " + medicinesAPI + ",\n" +
				  "\t\t\"symptomsAPI\": " + symptomsAPI + "\n" +
				  "  }\n" +
				  "}";

		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(RESET_API_API)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		return actualResponse.getBody().getObject();
	}

	/**
	 * Create a new patient
	 */
	private User createPatient() {

		// #1 Create PATIENT
		String emailPATIENT = "pat_new_" + new Random().nextInt(243656300) + "@gmail.com";
		Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
		Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
		String passwordPATIENT = "12345678JHHHJ";
		String loginModePATIENT = "EMAIL";

		return SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
	}


}
