package controller.test.schedule;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import model.User;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class ScheduleCreateTest {

	@Test
	public void testStoreSchedule() throws UnirestException {

		// #1 Create DOCTOR
		String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";
		String loginModeDOCTOR = "EMAIL";

		long start = System.currentTimeMillis();

		User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

		long end = System.currentTimeMillis();

		System.out.println("Time store appointment : " + ((double) (end - start) / 1000));

		// #2  Store Schedule
		JSONObject jsonObject = storeSchedule(doctor, "300");

		String message = (String) jsonObject.get("message");
		Integer statusCode = (Integer) jsonObject.get("statusCode");
		Boolean status = (Boolean) jsonObject.get("status");

		// #3  Assert
		assertEquals("Success", message);
		assertEquals(new Integer(200), statusCode);
		assertEquals(Boolean.TRUE, status);
	}


	@Test
	@Ignore
	public void test_RE_StoreSchedule() throws UnirestException {

		// #1 Create DOCTOR
		String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
		Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
		Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
		String passwordDOCTOR = "12345678";
		String loginModeDOCTOR = "EMAIL";

		User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

		// #2 POST: >> Store Schedule
		JSONObject jsonObject = reStoreSchedule(doctor, "777"); // >>>> send response to server
		String messageRe = (String) jsonObject.get("message");
		assertEquals("Success", messageRe);

		// #3  Assert
		String expectRequest = getReResponse(doctor, "777"); // get expect response
		JSONObject expectResponse = new JSONObject(expectRequest).getJSONObject("schedule");

		// #4 POST: >>> getSchedule
		JSONObject schedule = getSchedule(doctor);


		String message = (String) schedule.get("message");
		Integer statusCode = (Integer) schedule.get("statusCode");
		Boolean status = (Boolean) schedule.get("status");

		JSONObject doctorSchedule = schedule.getJSONObject("doctorSchedule");

		// #3  Assert
		assertEquals("Success", message);
		assertEquals(new Integer(200), statusCode);
		assertEquals(Boolean.TRUE, status);


		// JSON
		assertEquals(expectResponse.get("avergeTimeConsultation"), doctorSchedule.get("avergeTimeConsultation"));
		assertEquals(expectResponse.getJSONArray("doctorScheduler").toString(), doctorSchedule.getJSONArray("doctorScheduler").toString());

	}

	public static JSONObject getSchedule(User doctor) throws UnirestException {

		String response = "{\n" +
				  "\t\"doctorEmail\": \"" + doctor.getEmail() + "\", \n" +
				  "    \"sessionToken\":\"" + doctor.getSessionToken() + "\"\n" +
				  "}";

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse = Unirest.post(URL_GET_SCHEDULE)
				  .header("content-type", "application/json")
				  .header("cache-control", "no-cache")
				  .body(response).asJson();

		return actualResponse.getBody().getObject();
	}


	public static JSONObject storeSchedule(User doctor, String avergeTimeConsultation) throws UnirestException {

		String response = getResponse(doctor, avergeTimeConsultation);

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse = Unirest.post(URL_STORE_SCHEDULE)
				  .header("content-type", "application/json")
				  .header("cache-control", "no-cache")
				  .body(response).asJson();

		return actualResponse.getBody().getObject();
	}

	public static JSONObject reStoreSchedule(User doctor, String avergeTimeConsultation) throws UnirestException {

		String response = getReResponse(doctor, avergeTimeConsultation);

		// #2 POST RESPONSE
		HttpResponse<JsonNode> actualResponse = Unirest.post(URL_STORE_SCHEDULE)
				  .header("content-type", "application/json")
				  .header("cache-control", "no-cache")
				  .body(response).asJson();

		return actualResponse.getBody().getObject();
	}


	private static String getResponse(User doctor, String avergeTimeConsultation) {
		return "{\n" +
				  "\t\"doctorEmail\": \"" + doctor.getEmail() + "\", \n" +
				  "    \"sessionToken\":\"" + doctor.getSessionToken() + "\",\n" +
				  "\t\"schedule\": \n" +
				  "\t{\n" +
				  "\t\t\"avergeTimeConsultation\": \"" + avergeTimeConsultation + "\",\n" +
				  "\t\t\"doctorScheduler\": [\n" +
				  "\t\t{\n" +
				  "\t\t\t\"weekDayName\": \"SUN\",\n" +
				  "\t\t\t\"weekDay\": 1,\n" +
				  "\t\t\t\"startTime\": \"1488945634343\",\n" +
				  "\t\t\t\"endTime\": \"1488974434347\",\n" +
				  "\t\t\t\"exceptionTime\": \n" +
				  "\t\t\t[\n" +
				  "\t\t\t\t{\n" +
				  "\t\t\t\t\t\"startTime\": \"1488957344575\",\n" +
				  "\t\t\t\t\t\"endTime\": \"1488971747295\"\n" +
				  "\t\t\t\t}\n" +
				  "\t\t\t]\n" +
				  "\t\t}, \n" +
				  "\t\t{\n" +
				  "\t\t\t\"weekDayName\": \"MON\",\n" +
				  "\t\t\t\"weekDay\": 2,\n" +
				  "\t\t\t\"startTime\": \"1488945634343\",\n" +
				  "\t\t\t\"endTime\": \"1488974434347\",\n" +
				  "\t\t\t\"exceptionTime\": \n" +
				  "\t\t\t[\n" +
				  "\t\t\t\t{\n" +
				  "\t\t\t\t\t\"startTime\": \"1488957344575\",\n" +
				  "\t\t\t\t\t\"endTime\": \"1488971747295\"\n" +
				  "\t\t\t\t}\n" +
				  "\t\t\t]\n" +
				  "\t\t}, \n" +
				  "\t\t{\n" +
				  "\t\t\t\"weekDayName\": \"TUE\",\n" +
				  "\t\t\t\"weekDay\": 3,\n" +
				  "\t\t\t\"startTime\": \"1488945634343\",\n" +
				  "\t\t\t\"endTime\": \"1488974434347\",\n" +
				  "\t\t\t\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}, {\n" +
				  "\"weekDayName\": \"WED\",\n" +
				  "\"weekDay\": 4,\n" +
				  "\"startTime\": \"1488945634343\",\n" +
				  "\"endTime\": \"1488974434347\",\n" +
				  "\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}, {\n" +
				  "\"weekDayName\": \"THU\",\n" +
				  "\"weekDay\": 5,\n" +
				  "\"startTime\": \"1488945634343\",\n" +
				  "\"endTime\": \"1488974434347\",\n" +
				  "\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}, {\n" +
				  "\"weekDayName\": \"FRI\",\n" +
				  "\"weekDay\": 6,\n" +
				  "\"startTime\": \"1488945634343\",\n" +
				  "\"endTime\": \"1488974434347\",\n" +
				  "\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}, {\n" +
				  "\"weekDayName\": \"SAT\",\n" +
				  "\"weekDay\": 7,\n" +
				  "\"startTime\": \"1488945634343\",\n" +
				  "\"endTime\": \"1488974434347\",\n" +
				  "\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}]\n" +
				  "}\n" +
				  "}";
	}


	private static String getReResponse(User doctor, String avergeTimeConsultation) {
		return "{\n" +
				  "\t\"doctorEmail\": \"" + doctor.getEmail() + "\", \n" +
				  "    \"sessionToken\":\"" + doctor.getSessionToken() + "\",\n" +
				  "\t\"schedule\": \n" +
				  "\t{\n" +
				  "\t\t\"avergeTimeConsultation\": \"" + avergeTimeConsultation + "\",\n" +
				  "\t\t\"doctorScheduler\": [\n" +
				  "\t\t{\n" +
				  "\t\t\t\"weekDayName\": \"SUN\",\n" +
				  "\t\t\t\"weekDay\": 1,\n" +
				  "\t\t\t\"startTime\": \"1488945634343\",\n" +
				  "\t\t\t\"endTime\": \"1488974434347\",\n" +
				  "\t\t\t\"exceptionTime\": \n" +
				  "\t\t\t[\n" +
				  "\t\t\t\t{\n" +
				  "\t\t\t\t\t\"startTime\": \"1488957344575\",\n" +
				  "\t\t\t\t\t\"endTime\": \"1488971747295\"\n" +
				  "\t\t\t\t}\n" +
				  "\t\t\t]\n" +
				  "\t\t}, \n" +
				  "\t\t{\n" +
				  "\t\t\t\"weekDayName\": \"MON\",\n" +
				  "\t\t\t\"weekDay\": 2,\n" +
				  "\t\t\t\"startTime\": \"1488945634343\",\n" +
				  "\t\t\t\"endTime\": \"1488974434347\",\n" +
				  "\t\t\t\"exceptionTime\": \n" +
				  "\t\t\t[\n" +
				  "\t\t\t\t{\n" +
				  "\t\t\t\t\t\"startTime\": \"1488957344575\",\n" +
				  "\t\t\t\t\t\"endTime\": \"1488971747295\"\n" +
				  "\t\t\t\t}\n" +
				  "\t\t\t]\n" +
				  "\t\t}, \n" +
				  "\t\t{\n" +
				  "\t\t\t\"weekDayName\": \"TUE\",\n" +
				  "\t\t\t\"weekDay\": 3,\n" +
				  "\t\t\t\"startTime\": \"1488945634343\",\n" +
				  "\t\t\t\"endTime\": \"1488974434347\",\n" +
				  "\t\t\t\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}, {\n" +
				  "\"weekDayName\": \"WED\",\n" +
				  "\"weekDay\": 4,\n" +
				  "\"startTime\": \"1488945634343\",\n" +
				  "\"endTime\": \"1488974434347\",\n" +
				  "\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}, {\n" +
				  "\"weekDayName\": \"FRI\",\n" +
				  "\"weekDay\": 6,\n" +
				  "\"startTime\": \"1488945634343\",\n" +
				  "\"endTime\": \"1488974434347\",\n" +
				  "\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}, {\n" +
				  "\"weekDayName\": \"SAT\",\n" +
				  "\"weekDay\": 7,\n" +
				  "\"startTime\": \"1488945634343\",\n" +
				  "\"endTime\": \"1488974434347\",\n" +
				  "\"exceptionTime\": [{\n" +
				  "\"startTime\": \"1488957344575\",\n" +
				  "\"endTime\": \"1488971747295\"\n" +
				  "}]\n" +
				  "}]\n" +
				  "}\n" +
				  "}";
	}
}
