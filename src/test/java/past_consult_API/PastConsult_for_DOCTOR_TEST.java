package past_consult_API;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static constants.Constant.*;
import static org.junit.Assert.assertEquals;

public class PastConsult_for_DOCTOR_TEST {

	/**
	 * WITHOUT SYMPTOMS
	 */
	@Test
	public void test_EMPTY() {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 getPastConsultForDoctor
		JSONObject pastConsultForDoctor = getApp_getPastConsultForDoctor_EMPTY(doctor, patient);
		JSONArray appointments = pastConsultForDoctor.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("EMPTY", appointment.getString("appointmentHistory"));
		assertEquals("CLOSE", appointment.getString("appointmentStatus"));

		String format = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
		assertEquals(format + ": with no known chronic medical condition.\n" +
				  "\n" +
				  "He's not visited any hospital yet.", appointment.getString("patientDes"));

	}

	/**
	 * ONLY SYMPTOMS NAMES
	 */
	@Test
	public void test_EMPTY_ONLY_SYMPTOMS_NAMES() {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 getPastConsultForDoctor
		JSONObject pastConsultForDoctor = getApp_getPastConsultForDoctor_EMPTY_WITH_SYMPTOMS(doctor, patient);
		JSONArray appointments = pastConsultForDoctor.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("EMPTY", appointment.getString("appointmentHistory"));
		assertEquals("CLOSE", appointment.getString("appointmentStatus"));

		String format = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
		assertEquals(format + ": with no known chronic medical condition who presents with Headache 1 and Headache 2.\n" +
				  "\n" +
				  "He's not visited any hospital yet.", appointment.getString("patientDes"));
	}

	/**
	 * ONLY SYMPTOMS NAMES
	 */
	@Test
	public void test_PARTIAL_ONLY_SYMPTOM_VALUE() {

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 getPastConsultForDoctor
		JSONObject pastConsultForDoctor = getApp_getPastConsultForDoctor_PARTIAL_ONLY_SYMPTOM_VALUE(doctor, patient);
		JSONArray appointments = pastConsultForDoctor.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("PARTIAL", appointment.getString("appointmentHistory"));
		assertEquals("CLOSE", appointment.getString("appointmentStatus"));

		String format = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
		assertEquals(format + ": with no known chronic medical condition who presents with Headache 1 and Headache 2.\n" +
				  "\n" +
				  "The Headache 1 is onset_val_1, character_val_1\n" +
				  "The Headache 2 is site_2_character_val_1, onset_2_character_val_1, character_4_val_1\n" +
				  "\n" +
				  "He's not visited any hospital yet.", appointment.getString("patientDes"));
	}

	@Test
	public void test_PARTIAL_QUESTIONS(){
		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 getPastConsultForDoctor
		JSONObject pastConsultForDoctor = getApp_getPastConsultForDoctor_PARTIAL_QUESTIONS(doctor, patient);
		JSONArray appointments = pastConsultForDoctor.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("PARTIAL", appointment.getString("appointmentHistory"));
		assertEquals("CLOSE", appointment.getString("appointmentStatus"));

		String format = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
		assertEquals(format + ": with no known chronic medical condition who presents with Headache 1 of 1 days duration and Headache 2 of 3 days duration.\n" +
				  "\n" +
				  "He's not visited any hospital yet.", appointment.getString("patientDes"));
	}

	@Test
	public void test_COMPLETE() {
		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		// #2 getPastConsultForDoctor
		JSONObject pastConsultForDoctor = getApp_getPastConsultForDoctor_COMPLETE(doctor, patient);
		JSONArray appointments = pastConsultForDoctor.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("COMPLETE", appointment.getString("appointmentHistory"));
		assertEquals("CLOSE", appointment.getString("appointmentStatus"));

		String format = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
		assertEquals(format + ": with no known chronic medical condition who presents with headache 1 of 2 days duration and headache 2.\n" +
				  "\n" +
				  "The headache 1 is TEXT TXT TEXT......, sudden, throbbing\n" +
				  "The headache 2 is fullSided 2, sudden 2, throbbing 2\n" +
				  "\n" +
				  "He's not visited any hospital yet.", appointment.getString("patientDes"));
	}

	private JSONObject getApp_getPastConsultForDoctor_COMPLETE(User doctor, User patient) {
		// #2 Create appointment
		try {
			Unirest.post(URL_STORE_APPOINTMENT)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
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
							    "   ]" +
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #3 get appointment
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		JSONArray apps = appointmentOfPatients.getJSONArray("appointments");
		JSONObject app = apps.getJSONObject(0);
		assertEquals("COMPLETE", app.getString("appointmentHistory"));
		assertEquals("PENDING", app.getString("appointmentStatus"));

		// #4 change appointment status to CLOSE
		int appointmentId = app.getInt("appointmentId");
		try {
			Unirest.post(URL_CHANGE_APPOINTMENT_STATUS)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
								 "\"email\": \"" + patient.getEmail() + "\",\n" +
								 "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
								 "\"appointmentId\": \"" + appointmentId + "\",\n" +
								 "\"status\": \"CLOSE\"" +
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #5 get
		return getPastConsultForDoctor(doctor);
	}

	private JSONObject getApp_getPastConsultForDoctor_PARTIAL_QUESTIONS(User doctor, User patient) {
		// #2 Create appointment
		try {
			Unirest.post(URL_STORE_APPOINTMENT)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
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
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #3 get appointment
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		JSONArray apps = appointmentOfPatients.getJSONArray("appointments");
		JSONObject app = apps.getJSONObject(0);
		assertEquals("PARTIAL", app.getString("appointmentHistory"));
		assertEquals("PENDING", app.getString("appointmentStatus"));

		// #4 change appointment status to CLOSE
		int appointmentId = app.getInt("appointmentId");
		try {
			Unirest.post(URL_CHANGE_APPOINTMENT_STATUS)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
								 "\"email\": \"" + patient.getEmail() + "\",\n" +
								 "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
								 "\"appointmentId\": \"" + appointmentId + "\",\n" +
								 "\"status\": \"CLOSE\"" +
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #5 get
		return getPastConsultForDoctor(doctor);
	}


	private JSONObject getApp_getPastConsultForDoctor_PARTIAL_ONLY_SYMPTOM_VALUE(User doctor, User patient) {
		// #2 Create appointment
		try {
			Unirest.post(URL_STORE_APPOINTMENT)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
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
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #3 get appointment
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		JSONArray apps = appointmentOfPatients.getJSONArray("appointments");
		JSONObject app = apps.getJSONObject(0);
		assertEquals("PARTIAL", app.getString("appointmentHistory"));
		assertEquals("PENDING", app.getString("appointmentStatus"));

		// #4 change appointment status to CLOSE
		int appointmentId = app.getInt("appointmentId");
		try {
			Unirest.post(URL_CHANGE_APPOINTMENT_STATUS)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
								 "\"email\": \"" + patient.getEmail() + "\",\n" +
								 "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
								 "\"appointmentId\": \"" + appointmentId + "\",\n" +
								 "\"status\": \"CLOSE\"" +
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #5 get
		return getPastConsultForDoctor(doctor);
	}


	private JSONObject getApp_getPastConsultForDoctor_EMPTY_WITH_SYMPTOMS(User doctor, User patient) {
		// #2 Create appointment
		try {
			Unirest.post(URL_STORE_APPOINTMENT)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
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
								 "\t\t\"symptomName\": \"Headache 1\"\n" +
								 "\t}, \n" +
								 "{\n" +
								 "\t\"symptomName\": \"Headache 2\"\n" +
								 "      }\n" +
								 "   ]\n" +
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #3 get appointment
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		JSONArray apps = appointmentOfPatients.getJSONArray("appointments");
		JSONObject app = apps.getJSONObject(0);
		assertEquals("EMPTY", app.getString("appointmentHistory"));
		assertEquals("PENDING", app.getString("appointmentStatus"));

		// #4 change appointment status to CLOSE
		int appointmentId = app.getInt("appointmentId");
		try {
			Unirest.post(URL_CHANGE_APPOINTMENT_STATUS)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
								 "\"email\": \"" + patient.getEmail() + "\",\n" +
								 "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
								 "\"appointmentId\": \"" + appointmentId + "\",\n" +
								 "\"status\": \"CLOSE\"" +
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #5 get
		return getPastConsultForDoctor(doctor);
	}


	private JSONObject getApp_getPastConsultForDoctor_EMPTY(User doctor, User patient) {
		// #2 Create appointment
		try {
			Unirest.post(URL_STORE_APPOINTMENT)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
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
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #3 get appointment
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		JSONArray apps = appointmentOfPatients.getJSONArray("appointments");
		JSONObject app = apps.getJSONObject(0);
		assertEquals("EMPTY", app.getString("appointmentHistory"));
		assertEquals("PENDING", app.getString("appointmentStatus"));

		// #4 change appointment status to CLOSE
		int appointmentId = app.getInt("appointmentId");
		try {
			Unirest.post(URL_CHANGE_APPOINTMENT_STATUS)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body("{\n" +
								 "\"email\": \"" + patient.getEmail() + "\",\n" +
								 "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
								 "\"appointmentId\": \"" + appointmentId + "\",\n" +
								 "\"status\": \"CLOSE\"" +
								 "}").asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API");
		}

		// #5 get
		return getPastConsultForDoctor(doctor);
	}


	private JSONObject getPastConsultForDoctor(User doctor) {
		String request = "{ \n" +
				  "  \"email\": \"" + doctor.getEmail() + "\", \n" +
				  "  \"sessionToken\": \"" + doctor.getSessionToken() + "\", \n" +
				  "  \"listIndex\": 1" +
				  "} ";

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse;
		try {
			actualResponse = Unirest.post(URL_GET_ALL_CONSULT_FOR_DOCTOR)
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .body(request).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException("Error checkEmail API !");
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
			throw new RuntimeException("Error checkEmail API !");
		}

		// #3 result
		return actualResponse.getBody().getObject();

	}

	/**
	 * Create a new doctor
	 */
	private User createDoctor() {

		// #1 sign up user
		String emailDOCTOR = "doc_d_" + new Random().nextInt(24000300) + "@g.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(102);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(201);
		String passwordDOCTOR = "24234KJJf";

		return SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, "EMAIL", latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
	}

	/**
	 * Create a new patient
	 */
	private User createPatient() {

		// #1 Create PATIENT
		String emailPATIENT = "pat_p_" + new Random().nextInt(243656300) + "@gmail.com";
		Double latitudePATIENT = LATITUDE + new Random().nextInt(101);
		Double longitudePATIENT = LONGITUDE + new Random().nextInt(203);
		String passwordPATIENT = "23425235L";
		String loginModePATIENT = "EMAIL";

		return SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
	}
}
