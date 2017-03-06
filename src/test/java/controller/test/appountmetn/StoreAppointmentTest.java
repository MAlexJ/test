package controller.test.appountmetn;

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

import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class StoreAppointmentTest {

   @Test
   public void storeAppointmentTest() {

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

      // test
      storeAppointmentHireDoctor(patient, doctor);
   }

   public static AppointmentVO storeAppointmentHireDoctor(User patient, User doctor) {

      AppointmentVO appointmentVO = new AppointmentVO();
      appointmentVO.setPatientEmail(patient.getEmail());
      appointmentVO.setAppointmentMode("videoCall");
      appointmentVO.setAppointmentDate(1453095300000L);
      appointmentVO.setAppointmentTime(1477000000000L);

      String request = "{\n" +
              "\"email\": \"" + patient.getEmail() + "\",\n" +
              "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
              "\n" +
              "\"bookingDate\": \"" + appointmentVO.getAppointmentDate() + "\",\n" +
              "\"bookingTime\": \"" + appointmentVO.getAppointmentTime() + "\",\n" +
              "\"bookingMode\": \"" + appointmentVO.getAppointmentMode() + "\",\n" +
              "\n" +
              "\"doctorInfo\": {\n" +
              "\t\"hiredDoc\": \"" + doctor.getEmail() + "\",\n" +
              "    \"doctorLatitude\": \"23.34\",\n" +
              "    \"doctorLongitude\": \"34.4\",\n" +
              "\t\"doctorRate\": \"$324\"\n" +
              "},\n" +
              "\n" +
              "\"symptoms\": [{\n" +
              "\t\"symptomName\": \"headache 1\",\n" +
              "\t\"symptomDescription\": \n" +
              "\t\t\t[\n" +
              "\t\t\t\t{\n" +
              "\t\t\t\t\t\"symptomDescriptionName\": \"site\",\n" +
              "\t\t\t\t\t\"symptomDescriptionData\": \n" +
              "\t\t\t\t\t\t{\n" +
              "\t\t\t\t\t\t\t\"symptomsValue\": \"fullSided\"\n" +
              "\t\t\t\t\t\t}\n" +
              "\t\t\t\t}, \n" +
              "\t\t\t\t{\n" +
              "\t\t\t\t\t\"symptomDescriptionName\": \"onset\",\n" +
              "\t\t\t\t\t\"symptomDescriptionData\": \n" +
              "\t\t\t\t\t\t{\n" +
              "\t\t\t\t\t\t\t\"symptomsValue\": \"sudden\"\n" +
              "\t\t\t\t\t\t}\n" +
              "\t\t\t\t}, \n" +
              "\t\t\t\t{\n" +
              "\t\t\t\t\t\"symptomDescriptionName\": \"character\",\n" +
              "\t\t\t\t\t\"symptomDescriptionData\": \n" +
              "\t\t\t\t\t\t{\n" +
              "\t\t\t\t\t\t\t\"symptomsValue\": \"throbbing\"\n" +
              "\t\t\t\t\t\t}\n" +
              "\t\t\t\t}\n" +
              "\t\t\t]\n" +
              "\t}, \n" +
              "\n" +
              "\t{\n" +
              "\t\"symptomName\": \"headache 2\",\n" +
              "\t\"symptomDescription\": \n" +
              "\t\t[\n" +
              "\t\t\t{\n" +
              "\t\t\t\t\"symptomDescriptionName\": \"site 2\",\n" +
              "\t\t\t\t\"symptomDescriptionData\": \n" +
              "\t\t\t\t\t{\n" +
              "\t\t\t\t\t\t\"symptomsValue\": \"fullSided 2\"\n" +
              "\t\t\t\t\t}\n" +
              "\t\t\t}, \n" +
              "\t\t\t{\n" +
              "\t\t\t\t\"symptomDescriptionName\": \"onset 2\",\n" +
              "\t\t\t\t\"symptomDescriptionData\": \n" +
              "\t\t\t\t\t{ \n" +
              "\t\t\t\t\t\t\"symptomsValue\": \"sudden 2\"\n" +
              "\t\t\t\t\t}\n" +
              "\t\t\t}, \n" +
              "\t\t\t{\n" +
              "\t\t\t\t\"symptomDescriptionName\": \"character 2\",\n" +
              "\t\t\t\t\"symptomDescriptionData\": \n" +
              "\t\t\t\t\t{\n" +
              "\t\t\t\t\t\t\"symptomsValue\": \"throbbing 2\"\n" +
              "\t\t\t\t\t}\n" +
              "\t\t\t}\n" +
              "\t\t]\n" +
              "\t}\n" +
              "\t\n" +
              "]\n" +
              "}";


      // #2 POST RESPONSE
      HttpResponse<JsonNode> actualResponse;
      try {
         actualResponse = Unirest.post(URL_HIRE_STORE_APPOINTMENT)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .body(request).asJson();
      } catch (UnirestException e) {
         throw new RuntimeException("error sing up doctor");
      }

      // get values
      JSONObject jsonObject = actualResponse.getBody().getObject();

      // check response
      assertRespose(jsonObject);

      return appointmentVO;

   }

   //Assert
   private static void assertRespose(JSONObject jsonObject) {

      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");
      Boolean status = (Boolean) jsonObject.get("status");

      assertEquals("Doctor booked successfully!", message);
      assertEquals(new Integer(200), statusCode);
      assertEquals(Boolean.TRUE, status);
   }


}
