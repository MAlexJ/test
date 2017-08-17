package testAppointment.sroteAppointment;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

public class StoreAppointmentTest {


	@Test
	public void testStoreAppointmentWithoutSymptoms_PENDING() {

		// Test #1
		storeNewAppointment(true);

		// Test #2
		storeNewAppointment(false);

	}

	@Test
	public void testStoreAppointmentWithSymptoms_COMPLETE() {

		// Test #1 full Symptoms
		storeAppointmentWithSymptoms_COMPLETE(1);

		// Test #2 only Symptoms without description
		storeAppointmentWithSymptoms_COMPLETE(2); // >>> "questions": [],
		storeAppointmentWithSymptoms_COMPLETE(3); // >> null

		// Test #3 only Symptoms without questions
		storeAppointmentWithSymptoms_COMPLETE(4); // >>> "symptomDescription": [],
		storeAppointmentWithSymptoms_COMPLETE(5); // >> null

	}

	@Test
	public void testStoreAppointmentWithSymptoms_PARTIAL() {

		// Test #1 only Symptoms value
		storeAppointmentWithSymptoms_COMPLETE(6);

	}


	/**
	 * Store a new appointment without symptoms
	 *
	 * @param showSymptoms show empty list symptoms or null
	 */
	private void storeNewAppointment(boolean showSymptoms) {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 create a new schedule for a doctor

		// #3 store a new Appointment with empty list of symptoms
		JSONObject jsonObject = storeAppointment(patient, doctor, showSymptoms);

		// #4 assert
		assertEquals("Doctor booked successfully!", jsonObject.getString("message"));
		assertEquals(true, jsonObject.getBoolean("status"));
		assertEquals(200, jsonObject.getInt("statusCode"));

	}

	private void storeAppointmentWithSymptoms_COMPLETE(int num) {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 create a new schedule for a doctor

		// #3 store a new Appointment with empty list of symptoms
		// #3 store a new Appointment with empty list of symptoms
		JSONObject jsonObject = storeAppointment_COMPLETE(patient, doctor, num);

		// #4 assert
		assertEquals("Doctor booked successfully!", jsonObject.getString("message"));
		assertEquals(true, jsonObject.getBoolean("status"));
		assertEquals(200, jsonObject.getInt("statusCode"));
	}

	private JSONObject storeAppointment_COMPLETE(User patient, User doctor, int num) {

		String symptom;

		switch (num) {

			case 1:
				symptom = "\t{ \"symptomName\": \"headache 1\",\n" +
						  "\t\"questions\": [\n" +
						  "\t\t\t{\"symptomQuestion\":\"When did it start?\",\"symptomAnswer\":\"2 days\"},\n" +
						  "\t\t    {\"symptomQuestion\":\"How did it start?\",\"symptomAnswer\":\"bla bla bla\"}\n" +
						  "\t\t],\n" +
						  "\t\"symptomDescription\": \n" +
						  "    \t[\n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"site\",\n" +
						  "          \n" +
						  "          \"symptomDescriptionOther\":\"TEXT TXT TEXT......\"\n" +
						  "        }, \n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"onset\",\n" +
						  "          \"symptomDescriptionData\": \n" +
						  "            {\n" +
						  "              \"symptomsValue\": \"sudden\"\n" +
						  "            }\n" +
						  "        }, \n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"character\",\n" +
						  "          \"symptomDescriptionData\": \n" +
						  "            {\n" +
						  "              \"symptomsValue\": \"throbbing\"\n" +
						  "            }\n" +
						  "        }\n" +
						  "      ]\n" +
						  "\t}, \n" +
						  "\t{\n" +
						  "\t\t\"symptomName\": \"headache 2\",\n" +
						  "\t\t\"symptomDescription\": \n" +
						  "    \t[\n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"site 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          {\n" +
						  "            \"symptomsValue\": \"fullSided 2\"\n" +
						  "          }\n" +
						  "      }, \n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"onset 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          { \n" +
						  "            \"symptomsValue\": \"sudden 2\"\n" +
						  "          }\n" +
						  "      }, \n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"character 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          {\n" +
						  "            \"symptomsValue\": \"throbbing 2\"\n" +
						  "          }\n" +
						  "      }\n" +
						  "     ]\n" +
						  "\t}";
				break;
			case 2:
				symptom = "\t{ \n" +
						  "\t\"symptomName\": \"headache 1\",\n" +
						  "\t\"questions\": [],\n" +
						  "\t\"symptomDescription\":[\n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"site\",\n" +
						  "          \n" +
						  "          \"symptomDescriptionOther\":\"TEXT TXT TEXT......\"\n" +
						  "        }, \n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"onset\",\n" +
						  "          \"symptomDescriptionData\": \n" +
						  "            {\n" +
						  "              \"symptomsValue\": \"sudden\"\n" +
						  "            }\n" +
						  "        }, \n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"character\",\n" +
						  "          \"symptomDescriptionData\": \n" +
						  "            {\n" +
						  "              \"symptomsValue\": \"throbbing\"\n" +
						  "            }\n" +
						  "        }\n" +
						  "      ]\n" +
						  "\t}, \n" +
						  "\t{\n" +
						  "\t\"symptomName\": \"headache 2\",\n" +
						  "\t\"questions\": [],\n" +
						  "\t\"symptomDescription\":[\n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"site 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          {\n" +
						  "            \"symptomsValue\": \"fullSided 2\"\n" +
						  "          }\n" +
						  "      }, \n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"onset 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          { \n" +
						  "            \"symptomsValue\": \"sudden 2\"\n" +
						  "          }\n" +
						  "      }, \n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"character 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          {\n" +
						  "            \"symptomsValue\": \"throbbing 2\"\n" +
						  "          }\n" +
						  "      }\n" +
						  "     ]\n" +
						  "\t}";
				break;
			case 3:
				symptom = "\t{ \n" +
						  "\t\"symptomName\": \"headache 1\",\n" +
						  "\t\"symptomDescription\":[\n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"site\",\n" +
						  "          \n" +
						  "          \"symptomDescriptionOther\":\"TEXT TXT TEXT......\"\n" +
						  "        }, \n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"onset\",\n" +
						  "          \"symptomDescriptionData\": \n" +
						  "            {\n" +
						  "              \"symptomsValue\": \"sudden\"\n" +
						  "            }\n" +
						  "        }, \n" +
						  "        {\n" +
						  "          \"symptomDescriptionName\": \"character\",\n" +
						  "          \"symptomDescriptionData\": \n" +
						  "            {\n" +
						  "              \"symptomsValue\": \"throbbing\"\n" +
						  "            }\n" +
						  "        }\n" +
						  "      ]\n" +
						  "\t}, \n" +
						  "\t{\n" +
						  "\t\"symptomName\": \"headache 2\",\n" +
						  "\t\"symptomDescription\":[\n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"site 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          {\n" +
						  "            \"symptomsValue\": \"fullSided 2\"\n" +
						  "          }\n" +
						  "      }, \n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"onset 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          { \n" +
						  "            \"symptomsValue\": \"sudden 2\"\n" +
						  "          }\n" +
						  "      }, \n" +
						  "      {\n" +
						  "        \"symptomDescriptionName\": \"character 2\",\n" +
						  "        \"symptomDescriptionData\": \n" +
						  "          {\n" +
						  "            \"symptomsValue\": \"throbbing 2\"\n" +
						  "          }\n" +
						  "      }\n" +
						  "     ]\n" +
						  "\t}";
				break;

			case 4:
				symptom = "\t{ \n" +
						  "\t\"symptomName\": \"headache 1\",\n" +
						  "\t\"symptomDescription\":[]\n" +
						  "\t}, \n" +
						  "\t{\n" +
						  "\t\"symptomName\": \"headache 2\",\n" +
						  "\t\"symptomDescription\":[]\n" +
						  "\t}";
				break;

			case 5:
				symptom = "\t{ \n" +
						  "\t\"symptomName\": \"headache 1\",\n" +
						  "\t\"questions\":[]\n" +
						  "\t}, \n" +
						  "\t{\n" +
						  "\t\"symptomName\": \"headache 2\",\n" +
						  "\t\"questions\":[]\n" +
						  "\t}";
				break;

			case 6:
				symptom = "\t{ \n" +
						  "\t\"symptomName\": \"headache 1\"\n" +
						  "\t}, \n" +
						  "\t{\n" +
						  "\t\"symptomName\": \"headache 2\"\n" +
						  "\t}";
				break;

			default:
				symptom = "";

		}

		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\"," +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\"," +
				  "\n" +
				  "\"bookingTime\": \"" + new Date().getTime() + "\"," +
				  "\"bookingMode\": \"videoCall\"," +
				  "\n" +
				  "\"doctorInfo\": {" +
				  "  \"hiredDoc\": \"" + doctor.getEmail() + "\"," +
				  "    \"doctorLatitude\": \"23.34000\"," +
				  "    \"doctorLongitude\": \"34.47666\"," +
				  "  \"doctorRate\": \"$100\"" +
				  "},\"symptoms\": [" + symptom + "]" +
				  "}";


		System.out.println("Request param: \n" + "URL: " + URL_STORE_APPOINTMENT + "\n" + "json: " + request);

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(URL_STORE_APPOINTMENT)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		System.out.println("Response param: \n" + "json: " + actualResponse.getBody().getObject() + "\n\n");

		// #3 result
		return actualResponse.getBody().getObject();
	}


	private JSONObject storeAppointment(User patient, User doctor, boolean showSymptoms) {

		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\"," +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\"," +
				  "\n" +
				  "\"bookingTime\": \"" + new Date().getTime() + "\"," +
				  "\"bookingMode\": \"videoCall\"," +
				  "\n" +
				  "\"doctorInfo\": {" +
				  "  \"hiredDoc\": \"" + doctor.getEmail() + "\"," +
				  "    \"doctorLatitude\": \"23.34000\"," +
				  "    \"doctorLongitude\": \"34.47666\"," +
				  "  \"doctorRate\": \"$100\"" +
				  "}\n" + (showSymptoms ? ",\"symptoms\": []" : "") +
				  "}";


		System.out.println("Request param: \n" + "URL: " + URL_STORE_APPOINTMENT + "\n" + "json: " + request);

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(URL_STORE_APPOINTMENT)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		System.out.println("Response param: \n" + "json: " + actualResponse.getBody().getObject() + "\n\n");

		// #3 result
		return actualResponse.getBody().getObject();

	}


	/**
	 * Create a new doctor
	 */
	private User createDoctor() {

		// #1 sign up user
		String emailDOCTOR = "ddest_tt" + new Random().nextInt(24000300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";

		return SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, "EMAIL", latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
	}

	/**
	 * Create a new patient
	 */
	private User createPatient() {

		// #1 Create PATIENT
		String emailPATIENT = "patient_p" + new Random().nextInt(243656300) + "@gmail.com";
		Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
		Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
		String passwordPATIENT = "12345678";
		String loginModePATIENT = "EMAIL";

		return SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
	}
}