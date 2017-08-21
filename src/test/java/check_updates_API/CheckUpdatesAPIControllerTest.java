package check_updates_API;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static constants.Constant.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckUpdatesAPIControllerTest {

	@Before
	public void before() {
		resetAll_API(false, false, false, false);
	}


	@Test
	public void testResetAll() {

		// #1 reset all
		resetAll_API(true, true, true, true);

		// #2 check reset all
		JSONObject jsonObject = check_API();

		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));

	}

	@Test
	public void setNewValueAndReset() {

		// 1 sett TRUE all values
		setStatus_API(true, true, true, true);

		// #2 check TRUE all
		JSONObject jsonObject = check_API();
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertTrue(api.getBoolean("languageAPI"));
		assertTrue(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));

		// #3 reset all
		resetAll_API(false, false, false, false);

		// #4 check FALSE all
		jsonObject = check_API();
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));

	}

	@Test
	public void testSetValue() {

		// #1 set languageAPI is TRUE
		setStatus_API(true, false, false, false);
		JSONObject jsonObject = check_API();
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertTrue(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));
		resetAll_API(false, false, false, false);


		// #2 set languageAPI is TRUE
		setStatus_API(false, true, false, false);
		jsonObject = check_API();
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));
		resetAll_API(false, false, false, false);


		// #3 set languageAPI is TRUE
		setStatus_API(false, false, true, false);
		jsonObject = check_API();
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertTrue(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertFalse(api.getBoolean("symptomsAPI"));
		resetAll_API(false, false, false, false);


		// #4 set languageAPI is TRUE
		setStatus_API(false, false, false, true);
		jsonObject = check_API();
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertFalse(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));
		resetAll_API(false, false, false, false);

		// #5 set languageAPI and specialityAPI are TRUE
		setStatus_API(false, true, false, true);
		jsonObject = check_API();
		api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));
		resetAll_API(false, false, false, false);

	}

	@Test
	public void resetAPI_test_1() {

		// #1 set languageAPI is TRUE
		setStatus_API(true, true, true, true);

		// #2 reset API
		resetAll_API(false, true, true, true);

		// check
		JSONObject jsonObject = check_API();
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("languageAPI"));
		assertTrue(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));

	}

	@Test
	public void resetAPI_test_2() {

		// #1 set languageAPI is TRUE
		setStatus_API(true, true, true, true);

		// #2 reset API
		resetAll_API(false, true, false, true);

		// check
		JSONObject jsonObject = check_API();
		JSONObject api = jsonObject.getJSONObject("statusAPI");
		assertFalse(api.getBoolean("languageAPI"));
		assertFalse(api.getBoolean("medicinesAPI"));
		assertTrue(api.getBoolean("specialityAPI"));
		assertTrue(api.getBoolean("symptomsAPI"));

	}


	private JSONObject setStatus_API(boolean languageAPI, boolean specialityAPI, boolean medicinesAPI, boolean symptomsAPI) {

		String request = "{\n" +
				  "\"languageAPI\": " + languageAPI + "," +
				  "\"specialityAPI\": " + specialityAPI + "," +
				  "\"medicinesAPI\": " + medicinesAPI + "," +
				  "\"symptomsAPI\": " + symptomsAPI + "" +
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
	private JSONObject check_API() {

		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.get(CHECK_UPDATES_API)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		return actualResponse.getBody().getObject();
	}


	/**
	 * Reset API
	 */
	private JSONObject resetAll_API(boolean languageAPI, boolean specialityAPI, boolean medicinesAPI, boolean symptomsAPI) {

		String request = "{\n" +
				  "\"languageAPI\": " + languageAPI + "," +
				  "\"specialityAPI\": " + specialityAPI + "," +
				  "\"medicinesAPI\": " + medicinesAPI + "," +
				  "\"symptomsAPI\": " + symptomsAPI + "" +
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

}
