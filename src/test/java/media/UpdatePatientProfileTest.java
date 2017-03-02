package media;

import com.google.gson.Gson;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class UpdatePatientProfileTest {


   @Test
   public void testFullProfileWithChronicMedication() throws IOException {
      // #1 Create PATIENT
      String emailPATIENT = "patient_p_m" + new Date().getTime() + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String sessionToken = patient.getSessionToken();

      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("sessionToken", sessionToken);

      // chronicMedications
      List<ChronicMedication> medications = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
         medications.add(new ChronicMedication("Panadol_" + i, "Text_Description_" + i));
      }
      String chronicMedications = new Gson().toJson(medications);
      multipart.addFormField("chronicMedications", chronicMedications);

      multipart.addFilePart("file", new File("image.jpg"));
      String response = multipart.finish(); // response from server.

      JSONObject jsonObject = new JSONObject(response);
      Boolean status = (Boolean) jsonObject.get("status");
      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");

      assertEquals("The profile of User successfully updated", message);
      assertEquals(Boolean.TRUE, status);
      assertEquals(new Integer(200), statusCode);
      System.out.println(response);
   }


   private class ChronicMedication implements Serializable {

      private int chronicMedicationId;
      private String nameMedication;
      private String description;

      ChronicMedication(String nameMedication, String description) {
         this.nameMedication = nameMedication;
         this.description = description;
      }

      public int getChronicMedicationId() {
         return chronicMedicationId;
      }

      public void setChronicMedicationId(int chronicMedicationId) {
         this.chronicMedicationId = chronicMedicationId;
      }

      public String getNameMedication() {
         return nameMedication;
      }

      public void setNameMedication(String nameMedication) {
         this.nameMedication = nameMedication;
      }

      public String getDescription() {
         return description;
      }

      public void setDescription(String description) {
         this.description = description;
      }
   }


   @Test
   public void testFullProfileWithChronicMedicalCondition() throws IOException {
      // #1 Create PATIENT
      String emailPATIENT = "patient_p_m" + new Date().getTime() + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String sessionToken = patient.getSessionToken();

      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("sessionToken", sessionToken);

      // ChronicMedicalCondition
      List<ChronicMedicalCondition> conditions = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
         conditions.add(new ChronicMedicalCondition("Disease_" + i, "Hospital N" + i, "200" + i));
      }
      String chronicMedicalConditions = new Gson().toJson(conditions);
      multipart.addFormField("chronicMedicalConditions", chronicMedicalConditions);

      multipart.addFilePart("file", new File("image.jpg"));
      String response = multipart.finish(); // response from server.

      JSONObject jsonObject = new JSONObject(response);
      Boolean status = (Boolean) jsonObject.get("status");
      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");

      assertEquals("The profile of User successfully updated", message);
      assertEquals(Boolean.TRUE, status);
      assertEquals(new Integer(200), statusCode);
      System.out.println(response);

   }

   private class ChronicMedicalCondition {

      private int chronicMedicalConditionId;
      private String chronicDisease;
      private String hospital;
      private String year;

      public ChronicMedicalCondition(String chronicDisease, String hospital, String year) {
         this.chronicDisease = chronicDisease;
         this.hospital = hospital;
         this.year = year;
      }

      public int getChronicMedicalConditionId() {
         return chronicMedicalConditionId;
      }

      public void setChronicMedicalConditionId(int chronicMedicalConditionId) {
         this.chronicMedicalConditionId = chronicMedicalConditionId;
      }

      public String getChronicDisease() {
         return chronicDisease;
      }

      public void setChronicDisease(String chronicDisease) {
         this.chronicDisease = chronicDisease;
      }

      public String getHospital() {
         return hospital;
      }

      public void setHospital(String hospital) {
         this.hospital = hospital;
      }

      public String getYear() {
         return year;
      }

      public void setYear(String year) {
         this.year = year;
      }
   }


}
