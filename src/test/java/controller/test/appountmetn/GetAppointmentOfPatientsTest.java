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
import static junit.framework.TestCase.assertTrue;

public class GetAppointmentOfPatientsTest {

   @Test
   public void testGetAppointment() {

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
      AppointmentVO expectAppointment = StoreAppointmentTest.storeAppointmentHireDoctor(patient, doctor);

      // #4 get appointment

      long start = System.currentTimeMillis();

      JSONObject jsonObject = getAppointmentOfPatients(doctor);

      long end = System.currentTimeMillis();

      System.out.println("Time getAppointmentOfPatients : " + ((double) (end - start) / 1000));

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

      assertEquals(expectAppointment.getAppointmentDate(), actualAppointment.getAppointmentDate());
      assertEquals(expectAppointment.getPatientEmail(), actualAppointment.getPatientEmail());
      assertEquals(expectAppointment.getAppointmentMode(), actualAppointment.getAppointmentMode());

      assertTrue(!actualAppointment.getPatientName().isEmpty());
      assertTrue(!actualAppointment.getPatientEmail().isEmpty());

      System.out.println(actualAppointment.getPatientDes());

      assertTrue(!actualAppointment.getPatientDes().isEmpty());
   }


   /**
    * Get AppointmentOfPatients API
    */
   public static JSONObject getAppointmentOfPatients(User doctor) {

      // #1 CREATE REQUEST
      String request = "{ \n" +
              "\"doctorEmail\": \"" + doctor.getEmail() + "\", \n" +
              "\"sessionToken\": \"" + doctor.getSessionToken() + "\", \n" +
              "\"listIndex\": 1\n" +
              "} ";

      // #2 POST RESPONSE
      HttpResponse<JsonNode> actualResponse;
      try {
         actualResponse = Unirest.post(URL_GET_APPOINTMENT_OF_PATIENTS)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .body(request).asJson();
      } catch (UnirestException e) {
         throw new RuntimeException("error sing up doctor");
      }

      return actualResponse.getBody().getObject();
   }

}
