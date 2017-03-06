package controller.test.appountmetn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import controller.test.user.SignUpPatientTest;
import model.AppointmentVO;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class CancelAppointmentTest {

   @Test
   public void testCancelAppointment() {

      // #1 Create PATIENT
      String emailPATIENT = "patient_p" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());

      // #2 Create DOCTOR
      String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
      Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
      String passwordDOCTOR = "12345678";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

      // #3 Store appointment
      StoreAppointmentTest.storeAppointmentHireDoctor(patient, doctor);

      // #4 get appointment
      JSONObject jsonObject = GetAppointmentOfPatientsTest.getAppointmentOfPatients(doctor);

      // #5 Assert
      String actualEmail = jsonObject.get("email").toString();
      Boolean actualStatus = (Boolean) jsonObject.get("status");
      String actualMessage = (String) jsonObject.get("message");

      assertEquals(doctor.getEmail(), actualEmail);
      assertEquals(Boolean.TRUE, actualStatus);
      assertEquals("Doctor's appointments fetched successfully", actualMessage);

      String objectAppointment = jsonObject.getJSONArray("appointments").toString();
      List<AppointmentVO> expectAppointmentList = new Gson().fromJson(objectAppointment, new TypeToken<ArrayList<AppointmentVO>>() {
      }.getType());

      AppointmentVO actualAppointment = expectAppointmentList.get(0);
      int appointmentId = actualAppointment.getAppointmentId();

      // #2 POST RESPONSE
      HttpResponse<JsonNode> actualResponse;
      try {
         actualResponse = Unirest.post(URL_CANCEL_APPOINTMENT)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .body("{\n" +
                         "\"email\": \"" + doctor.getEmail() + "\",\n" +
                         "\"appointmentId\": " + appointmentId + ",\n" +
                         "\"sessionToken\": \"" + doctor.getSessionToken() + "\"\n" +
                         "}").asJson();
      } catch (UnirestException e) {
         throw new RuntimeException("error sing up doctor");
      }

      JSONObject object = actualResponse.getBody().getObject();
      assertEquals("Success", object.get("message"));

      // #5 get appointment is EMPTY
      JSONObject jsonObjectActual = GetAppointmentOfPatientsTest.getAppointmentOfPatients(doctor);
      assertEquals("[]", jsonObjectActual.get("appointments").toString());
   }

}
