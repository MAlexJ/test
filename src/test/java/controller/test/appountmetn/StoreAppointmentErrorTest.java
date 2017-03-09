package controller.test.appountmetn;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpDoctorTest;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class StoreAppointmentErrorTest {

   @Test
   public void error_patient_email() {
      // #1 Create PATIENT
      String emailPATIENT = "xxx@xxx.xxx";
      User patient = new User();
      patient.setEmail(emailPATIENT);

      // #2 Create DOCTOR
      String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
      Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
      String passwordDOCTOR = "12345678";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());

      // test
      JSONObject jsonObject = storeAppointment(patient, doctor);

      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");
      Boolean status = (Boolean) jsonObject.get("status");

      assertEquals("Error User Authentication", message);
      assertEquals(new Integer(200), statusCode);
      assertEquals(Boolean.FALSE, status);
   }

   @Test
   public void error_doctor_email() {

      // #1 Create PATIENT
      String emailPATIENT = "patient_p" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());


      // #2 Create DOCTOR
      String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";

      User doctor = new User();
      doctor.setEmail(emailDOCTOR);

      // test
      JSONObject jsonObject = storeAppointment(patient, doctor);

      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");
      Boolean status = (Boolean) jsonObject.get("status");

      assertEquals("User with email: "+emailDOCTOR+" not found", message);
      assertEquals(new Integer(200), statusCode);
      assertEquals(Boolean.FALSE, status);

   }

   private JSONObject storeAppointment(User patient, User doctor) {

      String request = "{\n" +
              "\"email\": \"" + patient.getEmail() + "\",\n" +
              "\"sessionToken\": \"" + patient.getSessionToken() + "\",\n" +
              "\n" +
              "\"bookingDate\": \"1453095300000\",\n" +
              "\"bookingTime\": \"1453095300000\",\n" +
              "\"bookingMode\": \"videoCall\",\n" +
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

      return actualResponse.getBody().getObject();
   }

}
