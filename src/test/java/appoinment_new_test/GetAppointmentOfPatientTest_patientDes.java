package appoinment_new_test;

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

public class GetAppointmentOfPatientTest_patientDes {

	@Test
	public void test_EMPTY(){

		// #1 create users
		User doctor = createDoctor();
		User patient = createPatient();

		String symptom1 = "Headache";
		String symptom2 = "Pain";

		// #2 create a new appointment
		storeAppointment_EMPTY(patient, doctor,symptom1, symptom2);

		// #3 getAppointmentOfPatients
		JSONObject appointmentOfPatients = getAppointmentOfPatients(doctor);
		assertEquals(doctor.getEmail(), appointmentOfPatients.get("email"));

		JSONArray appointments = appointmentOfPatients.getJSONArray("appointments");
		JSONObject appointment = appointments.getJSONObject(0);

		assertEquals("EMPTY", appointment.getString("appointmentHistory"));
		assertEquals("PENDING", appointment.getString("appointmentStatus"));

		assertEquals(patient.getEmail(), appointment.getString("patientEmail"));

		assertEquals("", appointment.getString("patientDes"));
	}



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
