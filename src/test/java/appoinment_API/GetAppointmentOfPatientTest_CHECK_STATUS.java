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
import static org.junit.Assert.assertNotNull;

/**
 * NEW TEST
 * <p>
 * "empty" - mean user did not provide any symptoms detail.
 * <p>
 * "Partial" - mean user only select the symptoms but
 * does not provide the symptoms detail like
 * question answers and descriptions.
 * <p>
 * "Complete" -  mean it gives all the details with symptoms
 */
public class GetAppointmentOfPatientTest_CHECK_STATUS {

	@Test
	public void testGetAppointmentOfPatients_EMPTY_1() {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 create a new appointment
		storeAppointment(patient, doctor);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("EMPTY", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));
		assertNotNull(appointment.getString("patientDes"));

	}

	@Test
	public void testGetAppointmentOfPatients_EMPTY_2() {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 create a new appointment
		storeAppointment_EMPTY_2(patient, doctor, "Pain", "Headache");

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		System.out.println(appointment);

		assertEquals("EMPTY", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));
		assertNotNull(appointment.getString("patientDes"));

	}

	@Test
	public void testGetAppointmentOfPatients_COMPLETE() {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 store appointment
		storeAppointment_COMPLETE(patient, doctor);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		System.out.println(appointment);

		assertEquals("COMPLETE", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));
		assertNotNull(appointment.getString("patientDes"));
	}

	// only descriptions with symptomDescriptionName
	@Test
	public void testGetAppointmentOfPatients_PARTIAL_1() {
		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 store appointment
		storeAppointment_PARTIAL_1(patient, doctor);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		System.out.println(appointment);

		assertEquals("PARTIAL", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));
		assertNotNull(appointment.getString("patientDes"));

	}

	// only questions
	@Test
	public void testGetAppointmentOfPatients_PARTIAL_2() {
		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 store appointment
		storeAppointment_PARTIAL_2(patient, doctor);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		System.out.println(appointment);

		assertEquals("PARTIAL", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));
		assertNotNull(appointment.getString("patientDes"));

	}

	// The parameter: symptomQuestionId should be in the question.
	@Test
	public void storeAppointment_ERROR_when_not_send_symptomQuestionId_with_questions() {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 store appointment
		JSONObject jsonObject = storeAppointment_COMPLETE_ERROR(patient, doctor);

		assertEquals("The parameter: symptomQuestionId should be in the question.", jsonObject.get("message"));
	}


	private JSONObject storeAppointment_PARTIAL_2(User patient, User doctor) {

		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"1453095300000\",\n" +
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
				  "\t\t\"symptomName\": \"headache 1\",\n" +
				  "\t\t\"questions\": [\n" +
				  "\t\t\t{\"symptomQuestionId\":\"1\",\"symptomQuestion\":\"When did it start?\",\"symptomAnswer\":\"2 days\"},\n" +
				  "\t\t    {\"symptomQuestionId\":\"1\",\"symptomQuestion\":\"How did it start?\",\"symptomAnswer\":\"bla bla bla\"}\n" +
				  "\t\t]\n" +
				  "\t}\n" +
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

	private JSONObject storeAppointment_PARTIAL_1(User patient, User doctor) {

		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"1453095300000\",\n" +
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
				  "\t\t\"symptomName\": \"headache 1\",\n" +
				  "\t\t\"symptomDescription\": [\n" +
				  "    \t\t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"site\"\n" +
				  "        \t}, \n" +
				  "        \t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"onset\",\n" +
				  "        \t\t\"symptomDescriptionData\": {}\n" +
				  "        \t}\n" +
				  "      ]\n" +
				  "\t}\n" +
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


	private JSONObject getAppointmentOfPatients(User doctor) {
		String request = "{ \n" +
				  "  \"doctorEmail\": \"" + doctor.getEmail() + "\", \n" +
				  "  \"sessionToken\": \"" + doctor.getSessionToken() + "\", \n" +
				  "  \"listIndex\": 1\n" +
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
		String emailDOCTOR = "doctor_abl_" + new Random().nextInt(24000300) + "@g.com";
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
		String emailPATIENT = "patient_new_" + new Random().nextInt(243656300) + "@gmail.com";
		Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
		Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
		String passwordPATIENT = "12345678JHHHJ";
		String loginModePATIENT = "EMAIL";

		return SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
	}


	private JSONObject storeAppointment(User patient, User doctor) {

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
				  "}" +
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

	private JSONObject storeAppointment_COMPLETE(User patient, User doctor) {
		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"1453095300000\",\n" +
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
				  "\t\t\"symptomName\": \"headache 1\",\n" +
				  "\t\t\"questions\": [\n" +
				  "\t\t\t{\"symptomQuestionId\":\"1\",\"symptomQuestion\":\"When did it start?\",\"symptomAnswer\":\"2 days\"},\n" +
				  "\t\t    {\"symptomQuestionId\":\"2\",\"symptomQuestion\":\"How did it start?\",\"symptomAnswer\":\"bla bla bla\"}\n" +
				  "\t\t],\n" +
				  "\t\t\"symptomDescription\": [\n" +
				  "    \t\t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"site\",\n" +
				  "    \t\t\t\"symptomDescriptionOther\":\"TEXT TXT TEXT......\"\n" +
				  "        \t}, \n" +
				  "        \t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"onset\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "            \t\t{\n" +
				  "            \t\t\t\"symptomsValue\": \"sudden\"\n" +
				  "            \t\t}\n" +
				  "        \t}, \n" +
				  "        \t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"character\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "            \t\t{\n" +
				  "            \t\t\t \"symptomsValue\": \"throbbing\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t }\n" +
				  "      ]\n" +
				  "\t}, \n" +
				  "\t{\n" +
				  "\t\t\"symptomName\": \"headache 2\",\n" +
				  "\t\t\"symptomDescription\": [\n" +
				  "    \t\t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"site 2\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "        \t\t\t{\n" +
				  "            \t\t\t\"symptomsValue\": \"fullSided 2\"\n" +
				  "        \t\t\t}\n" +
				  "    \t\t}, \n" +
				  "    \t\t{\n" +
				  "    \t\t\t \"symptomDescriptionName\": \"onset 2\",\n" +
				  "    \t\t\t \"symptomDescriptionData\": \n" +
				  "        \t\t\t { \n" +
				  "        \t\t\t\t \"symptomsValue\": \"sudden 2\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t}, \n" +
				  "    \t\t{\n" +
				  "    \t\t\t \"symptomDescriptionName\": \"character 2\",\n" +
				  "    \t\t\t  \"symptomDescriptionData\": \n" +
				  "    \t\t\t   {\n" +
				  "        \t\t\t  \"symptomsValue\": \"throbbing 2\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t }\n" +
				  "        ]\n" +
				  "      }\n" +
				  "   ]\n" +
				  "}\n";

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


	private JSONObject storeAppointment_COMPLETE_ERROR(User patient, User doctor) {

		String request = "{\n" +
				  "\"email\": \"" + patient.getEmail() + "\",\n" +
				  "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
				  "\n" +
				  "\"bookingTime\": \"1453095300000\",\n" +
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
				  "\t\t\"symptomName\": \"headache 1\",\n" +
				  "\t\t\"questions\": [\n" +
				  "\t\t\t{\"symptomQuestion\":\"When did it start?\",\"symptomAnswer\":\"2 days\"},\n" +
				  "\t\t    {\"symptomQuestion\":\"How did it start?\",\"symptomAnswer\":\"bla bla bla\"}\n" +
				  "\t\t],\n" +
				  "\t\t\"symptomDescription\": [\n" +
				  "    \t\t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"site\",\n" +
				  "    \t\t\t\"symptomDescriptionOther\":\"TEXT TXT TEXT......\"\n" +
				  "        \t}, \n" +
				  "        \t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"onset\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "            \t\t{\n" +
				  "            \t\t\t\"symptomsValue\": \"sudden\"\n" +
				  "            \t\t}\n" +
				  "        \t}, \n" +
				  "        \t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"character\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "            \t\t{\n" +
				  "            \t\t\t \"symptomsValue\": \"throbbing\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t }\n" +
				  "      ]\n" +
				  "\t}, \n" +
				  "\t{\n" +
				  "\t\t\"symptomName\": \"headache 2\",\n" +
				  "\t\t\"symptomDescription\": [\n" +
				  "    \t\t{\n" +
				  "        \t\t\"symptomDescriptionName\": \"site 2\",\n" +
				  "        \t\t\"symptomDescriptionData\": \n" +
				  "        \t\t\t{\n" +
				  "            \t\t\t\"symptomsValue\": \"fullSided 2\"\n" +
				  "        \t\t\t}\n" +
				  "    \t\t}, \n" +
				  "    \t\t{\n" +
				  "    \t\t\t \"symptomDescriptionName\": \"onset 2\",\n" +
				  "    \t\t\t \"symptomDescriptionData\": \n" +
				  "        \t\t\t { \n" +
				  "        \t\t\t\t \"symptomsValue\": \"sudden 2\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t}, \n" +
				  "    \t\t{\n" +
				  "    \t\t\t \"symptomDescriptionName\": \"character 2\",\n" +
				  "    \t\t\t  \"symptomDescriptionData\": \n" +
				  "    \t\t\t   {\n" +
				  "        \t\t\t  \"symptomsValue\": \"throbbing 2\"\n" +
				  "        \t\t\t }\n" +
				  "    \t\t }\n" +
				  "        ]\n" +
				  "      }\n" +
				  "   ]\n" +
				  "}\n";

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


	private JSONObject storeAppointment_EMPTY_2(User patient, User doctor, String symptom1, String symptom2) {

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
				  "\t{\n" +
				  "\t\t\"symptomName\": \"" + symptom2 + "\"\n" +
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

}
