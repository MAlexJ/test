package appoinment_API;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

import static constants.Constant.*;
import static org.junit.Assert.assertEquals;

/**
 * New TEST
 */
public class GetAppointmentOfPatientTest_patientDes {


	/**
	 * WITHOUT SYMPTOMS
	 */
	@Test
	public void test_EMPTY_WITHOUT_SYMPTOMS() {
		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		String symptom1 = "Headache";
		String symptom2 = "Pain";

		// #2 create a new appointment
		storeAppointment_EMPTY(patient, doctor, symptom1, symptom2);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("EMPTY", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));

		assertEquals("Alex Test is a 32 yrs old, with no known chronic medical condition.\n" +
				  "\n" +
				  "Hes not visited any hospital yet.", appointment.getString("patientDes"));

	}


	/**
	 * EMPTY WITH SYMPTOMS
	 */
	@Test
	public void test_EMPTY_WITH_SYMPTOMS() {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		String symptom1 = "Headache";
		String symptom2 = "Pain";

		// #2 create a new appointment
		storeAppointment_EMPTY_with_SYMPTOMS(patient, doctor, symptom1, symptom2);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("EMPTY", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));

		assertEquals("Alex Test is a 32 yrs old, with no known chronic medical condition who presents with Headache and Pain.\n" +
				  "\n" +
				  "Hes not visited any hospital yet.", appointment.getString("patientDes"));
	}


	/**
	 * WITHOUT SYMPTOMS
	 */
	@Test
	public void test_PARTIAL_ONLY_SYMPTOM_VALUE() {
		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		String symptom1 = "Headache";
		String symptom2 = "Pain";

		// #2 create a new appointment
		storeAppointment_PARTIAL_ONLY_SYMPTOM_VALUE(patient, doctor, symptom1, symptom2);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("PARTIAL", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));

		assertEquals("Alex Test is a 32 yrs old, with no known chronic medical condition who presents with Headache 1 and Headache 2.\n" +
				  "\n" +
				  "The Headache 1 is onset, character\n" +
				  "The Headache 2 is site 2, onset 2, character 4\n" +
				  "\n" +
				  "Hes not visited any hospital yet.", appointment.getString("patientDes"));

	}


	/**
	 * WITH QUESTIONS
	 */
	@Test
	public void test_PARTIAL_QUESTIONS() {
		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 create a new appointment
		storeAppointment_PARTIAL_WITH_QUESTIONS(patient, doctor);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("PARTIAL", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));

		assertEquals("Alex Test is a 32 yrs old, with no known chronic medical condition who presents with Headache 1 of 1 days duration and Headache 2 of 3 days duration.\n" +
				  "\n" +
				  "Hes not visited any hospital yet.", appointment.getString("patientDes"));
	}


	/**
	 * WITH ONE QUESTION
	 */
	@Test
	public void test_PARTIAL_ONE_QUESTION() {
		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 create a new appointment
		storeAppointment_PARTIAL_ONE_QUESTION(patient, doctor);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("PARTIAL", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));

		assertEquals("Alex Test is a 32 yrs old, with no known chronic medical condition who presents with Headache 1 of 1 days duration.\n" +
				  "\n" +
				  "Hes not visited any hospital yet.", appointment.getString("patientDes"));
	}


	/**
	 * PARTIAL WITH ONE QUESTION
	 */
	private JSONObject storeAppointment_PARTIAL_ONE_QUESTION(User patient, User doctor) {
		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"" + new Date().getTime() + "\",\n" +
				  "\"bookingMode\": \"videoCall\",\n" +
				  "\n" +
				  "\"doctorInfo\": {\n" +
				  "  \"hiredDoc\": \"" + doctor.getEmail() + "\",\n" +
				  "    \"doctorLatitude\": \"23.34000\",\n" +
				  "    \"doctorLongitude\": \"34.47666\",\n" +
				  "  \"doctorRate\": \"$100\"\n" +
				  "},\n" +
				  "\n" +
				  "\"symptoms\": [\n" +
				  "\t{ \n" +
				  "\t\t\"symptomName\": \"Headache 1\",\n" +
				  "\t\t\"questions\": [\n" +
				  "\t\t\t{\"symptomQuestionId\":\"1\",\"symptomQuestion\":\"When did it start?\",\"symptomAnswer\":\"1 days\"},\n" +
				  "\t\t    {\"symptomQuestionId\":\"2\",\"symptomQuestion\":\"How did it start?\",\"symptomAnswer\":\"bla bla bla\"}\n" +
				  "\t\t]\n" +
				  "\t}" +
				  "   ]" +
				  "}";

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

		// #3 result
		return actualResponse.getBody().getObject();
	}


	/**
	 * PARTIAL WITH QUESTIONS
	 */
	private JSONObject storeAppointment_PARTIAL_WITH_QUESTIONS(User patient, User doctor) {
		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"" + new Date().getTime() + "\",\n" +
				  "\"bookingMode\": \"videoCall\",\n" +
				  "\n" +
				  "\"doctorInfo\": {\n" +
				  "  \"hiredDoc\": \"" + doctor.getEmail() + "\",\n" +
				  "    \"doctorLatitude\": \"23.34000\",\n" +
				  "    \"doctorLongitude\": \"34.47666\",\n" +
				  "  \"doctorRate\": \"$100\"\n" +
				  "},\n" +
				  "\n" +
				  "\"symptoms\": [\n" +
				  "\t{ \n" +
				  "\t\t\"symptomName\": \"Headache 1\",\n" +
				  "\t\t\"questions\": [\n" +
				  "\t\t\t{\"symptomQuestionId\":\"1\",\"symptomQuestion\":\"When did it start?\",\"symptomAnswer\":\"1 days\"},\n" +
				  "\t\t    {\"symptomQuestionId\":\"2\",\"symptomQuestion\":\"How did it start?\",\"symptomAnswer\":\"bla bla bla\"}\n" +
				  "\t\t]\n" +
				  "\t}, \n" +
				  "\t{ \n" +
				  "\t\t\"symptomName\": \"Headache 2\",\n" +
				  "\t\t\"questions\": [\n" +
				  "\t\t\t{\"symptomQuestionId\":\"3\",\"symptomQuestion\":\"When did it start?\",\"symptomAnswer\":\"3 days\"},\n" +
				  "\t\t    {\"symptomQuestionId\":\"4\",\"symptomQuestion\":\"How did it start?\",\"symptomAnswer\":\"bla bla bla\"}\n" +
				  "\t\t]\n" +
				  "\t}\n" +
				  "   ]" +
				  "}";

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

		// #3 result
		return actualResponse.getBody().getObject();
	}


	/**
	 * EMPTY WITHOUT SYMPTOMS
	 */
	private JSONObject storeAppointment_PARTIAL_ONLY_SYMPTOM_VALUE(User patient, User doctor, String symptom1, String symptom2) {
		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"" + new Date().getTime() + "\",\n" +
				  "\"bookingMode\": \"videoCall\",\n" +
				  "\n" +
				  "\"doctorInfo\": {\n" +
				  "  \"hiredDoc\": \"" + doctor.getEmail() + "\",\n" +
				  "    \"doctorLatitude\": \"23.34000\",\n" +
				  "    \"doctorLongitude\": \"34.47666\",\n" +
				  "  \"doctorRate\": \"$100\"\n" +
				  "},\n" +
				  "\n" +
				  "\"symptoms\": [\n" +
				  "\t{ \n" +
				  "\t\t\"symptomName\": \"Headache 1\",\n" +
				  "\t\t\"symptomDescription\": [\n" +
				  "    \t\t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"onset\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "            \t\t{\n" +
				  "            \t\t\t\"symptomsValue\": \"onset_val_1\"\n" +
				  "            \t\t}\n" +
				  "        \t}, \n" +
				  "        \t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"character\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "            \t\t{\n" +
				  "            \t\t\t \"symptomsValue\": \"character_val_1\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t }\n" +
				  "      ]\n" +
				  "\t}, \n" +
				  "\t{\n" +
				  "\t\t\"symptomName\": \"Headache 2\",\n" +
				  "\t\t\"symptomDescription\": [\n" +
				  "    \t\t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"site 2\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "        \t\t\t{\n" +
				  "            \t\t\t\"symptomsValue\": \"site_2_character_val_1\"\n" +
				  "        \t\t\t}\n" +
				  "    \t\t}, \n" +
				  "    \t\t{\n" +
				  "    \t\t\t \"symptomDescriptionName\": \"onset 2\",\n" +
				  "    \t\t\t \"symptomDescriptionData\": \n" +
				  "        \t\t\t { \n" +
				  "        \t\t\t\t \"symptomsValue\": \"onset_2_character_val_1\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t}, \n" +
				  "    \t\t{\n" +
				  "    \t\t\t \"symptomDescriptionName\": \"character 4\",\n" +
				  "    \t\t\t  \"symptomDescriptionData\": \n" +
				  "    \t\t\t   {\n" +
				  "        \t\t\t  \"symptomsValue\": \"character_4_val_1\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t }\n" +
				  "        ]\n" +
				  "      }\n" +
				  "   ]" +
				  "}";

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

		// #3 result
		return actualResponse.getBody().getObject();

	}

	/**
	 * EMPTY WITHOUT SYMPTOMS
	 */
	private JSONObject storeAppointment_EMPTY(User patient, User doctor, String symptom1, String symptom2) {
		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"" + new Date().getTime() + "\",\n" +
				  "\"bookingMode\": \"videoCall\",\n" +
				  "\n" +
				  "\"doctorInfo\": {\n" +
				  "  \"hiredDoc\": \"" + doctor.getEmail() + "\",\n" +
				  "    \"doctorLatitude\": \"23.34000\",\n" +
				  "    \"doctorLongitude\": \"34.47666\",\n" +
				  "  \"doctorRate\": \"$100\"\n" +
				  "},\n" +
				  "\n" +
				  "\"symptoms\": []" +
				  "}";

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

		// #3 result
		return actualResponse.getBody().getObject();

	}


	/**
	 * EMPTY WITH SYMPTOMS
	 */
	private JSONObject storeAppointment_EMPTY_with_SYMPTOMS(User patient, User doctor, String symptom1, String symptom2) {

		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"" + new Date().getTime() + "\",\n" +
				  "\"bookingMode\": \"videoCall\",\n" +
				  "\n" +
				  "\"doctorInfo\": {\n" +
				  "  \"hiredDoc\": \"" + doctor.getEmail() + "\",\n" +
				  "    \"doctorLatitude\": \"23.34000\",\n" +
				  "    \"doctorLongitude\": \"34.47666\",\n" +
				  "  \"doctorRate\": \"$100\"\n" +
				  "},\n" +
				  "\n" +
				  "\"symptoms\": [\n" +
				  "\t{ \n" +
				  "\t\t\"symptomName\": \"" + symptom1 + "\"\n" +
				  "\t}, \n" +
				  "{\n" +
				  "\t\"symptomName\": \"" + symptom2 + "\"\n" +
				  "      }\n" +
				  "   ]\n" +
				  "}";

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

		// #3 result
		return actualResponse.getBody().getObject();

	}

	/**
	 * Call the API: getAppointmentOfPatients
	 */
	private JSONObject getAppointmentOfPatients(User doctor) {
		String request = "{ \n" +
				  "  \"doctorEmail\": \"" + doctor.getEmail() + "\", \n" +
				  "  \"sessionToken\": \"" + doctor.getSessionToken() + "\", \n" +
				  "  \"listIndex\": 1" +
				  "} ";

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(URL_GET_APPOINTMENT_OF_PATIENTS)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #3 result
		return actualResponse.getBody().getObject();

	}


	/**
	 * Create a new doctor
	 */
	private User createDoctor() {

		// #1 sign up user
		String emailDOCTOR = "doctor_a_" + new Random().nextInt(24000300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "24234KJJf";

		return SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, "EMAIL", latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
	}

	/**
	 * Create a new patient
	 */
	private User createPatient() {

		// #1 Create PATIENT
		String emailPATIENT = "patient_n_" + new Random().nextInt(243656300) + "@gmail.com";
		Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
		Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
		String passwordPATIENT = "12345678JHHHJ";
		String loginModePATIENT = "EMAIL";

		return SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
	}
}