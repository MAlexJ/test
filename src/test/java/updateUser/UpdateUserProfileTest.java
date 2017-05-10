package updateUser;

import controller.test.user.SignUpDoctorTest;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import static constants.Constant.*;
import static controller.test.user.GetUserByIdTest.getUserById;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author malex
 */
public class UpdateUserProfileTest {

   @Test
   public void testErrorAuthSessionTokenIsEmpty() throws IOException {

      // #1 sessionToken is Empty or incorrect
      String sessionToken = "";

      // #2 post
      JSONObject jsonObject = send(sessionToken);
      Boolean status = (Boolean) jsonObject.get("status");
      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");

      // #3 assert
      assertEquals("Session token is empty.", message);
      assertEquals(Boolean.FALSE, status);
      assertEquals(new Integer(200), statusCode);
   }

   @Test
   public void testErrorAuthSessionTokenIncorrect() throws IOException {
      // #1 sessionToken is Empty or incorrect
      String sessionToken = "XxXxX";

      // #2 post
      try {
         JSONObject jsonObject = send(sessionToken);
         Boolean status = (Boolean) jsonObject.get("status");
         String message = (String) jsonObject.get("message");
         Integer statusCode = (Integer) jsonObject.get("statusCode");

         // #3 assert
         assertEquals("Error validating session token", message);
         assertEquals(Boolean.FALSE, status);
         assertEquals(new Integer(200), statusCode);
      } catch (IOException ex){
         assertEquals("Server returned non-OK status: 401", ex.getMessage());
      }
   }


   @Test
   public void testFullProfileWithoutRole() throws IOException {
      // #1 Create PATIENT
      String emailPATIENT = "patient_p" + new Date().getTime() + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String sessionToken = patient.getSessionToken();
      int id = patient.getId();

      String email = "new_email" + new Date().getTime() + "@gmail.com";
      String mobile = "0987654321";
      String gender = "Male";
      String profession = "New Profassion Test";
      String latitude = "111.11111";
      String longitude = "222.22222";
      String fullName = "New Full NAme Test";
      String identificationCard = "New 2342343243253 Test";
      String religion = "XxX Test";
      String dateOfBirth = "1231797600000";
      String streetAddress = "New streetAddress Test";
      String occupation = "New occupation Test";
      String insuranceCompany = "Insurance Company Test";
      String title = "New DR Test";

      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("email", email);
      multipart.addFormField("sessionToken", sessionToken);
      multipart.addFormField("mobile", mobile);
      multipart.addFormField("gender", gender);
      multipart.addFormField("profession", profession);
      multipart.addFormField("latitude", latitude);
      multipart.addFormField("longitude", longitude);
      multipart.addFormField("fullName", fullName);
      multipart.addFormField("identificationCard", identificationCard);
      multipart.addFormField("religion", religion);
      multipart.addFormField("dateOfBirth", dateOfBirth);
      multipart.addFormField("streetAddress", streetAddress);
      multipart.addFormField("occupation", occupation);
      multipart.addFormField("insuranceCompany", insuranceCompany);
      multipart.addFormField("title", title);

      multipart.addFilePart("file", new File("image.jpg"));
      String response = multipart.finish(); // response from server.

      JSONObject jsonObject = new JSONObject(response);
      String path = (String) jsonObject.get("path");
      Boolean status = (Boolean) jsonObject.get("status");
      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");

      assertEquals("The profile of User successfully updated", message);
      assertEquals(Boolean.TRUE, status);
      assertEquals(new Integer(200), statusCode);
      System.out.println(response);

      // #4 validate user from DB
      JSONObject userById = getUserById(id);
      assertEquals(email, (String) userById.getJSONObject("user").get("email"));
      assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));
      assertEquals(gender.toLowerCase(), (String) userById.getJSONObject("user").get("gender"));
      assertEquals(profession, (String) userById.getJSONObject("user").get("profession"));
      assertEquals(mobile, (String) userById.getJSONObject("user").get("mobile"));
      assertEquals(fullName, (String) userById.getJSONObject("user").get("fullName"));
      assertEquals(religion, (String) userById.getJSONObject("user").get("religion"));
      assertEquals(Long.parseLong(dateOfBirth), userById.getJSONObject("user").get("dateOfBirth"));
      assertEquals(streetAddress, (String) userById.getJSONObject("user").get("streetAddress"));
      assertEquals(occupation, (String) userById.getJSONObject("user").get("occupation"));
      assertEquals(insuranceCompany, (String) userById.getJSONObject("user").get("insuranceCompany"));
      assertEquals(title, ((String) userById.getJSONObject("user").get("title")).trim());
      assertEquals(latitude, (String) userById.getJSONObject("user").get("latitude"));
      assertEquals(longitude, (String) userById.getJSONObject("user").get("longitude"));

      assertTrue(!path.isEmpty());
   }

   @Test
   public void testFullProfileWithoutRoleEmptyValue() throws IOException {

      // #1 Create PATIENT
      String emailPATIENT = "patient_p" + new Date().getTime() + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String sessionToken = patient.getSessionToken();
      int id = patient.getId();

      String email = "";
      String phone = "";
      String gender = "";
      String profession = "";
      String latitude = "";
      String longitude = "";
      String fullName = "";
      String identificationCard = "";
      String religion = "";
      String dateOfBirth = "";
      String streetAddress = "";
      String occupation = "";
      String insuranceCompany = "";
      String title = "";


      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("email", email);
      multipart.addFormField("sessionToken", sessionToken);
      multipart.addFormField("phone", phone);
      multipart.addFormField("gender", gender);
      multipart.addFormField("profession", profession);
      multipart.addFormField("latitude", latitude);
      multipart.addFormField("longitude", longitude);
      multipart.addFormField("fullName", fullName);
      multipart.addFormField("identificationCard", identificationCard);
      multipart.addFormField("religion", religion);
      multipart.addFormField("dateOfBirth", dateOfBirth);
      multipart.addFormField("streetAddress", streetAddress);
      multipart.addFormField("occupation", occupation);
      multipart.addFormField("insuranceCompany", insuranceCompany);
      multipart.addFormField("title", title);

      // #3 response from server.
      String response = multipart.finish();

      JSONObject jsonObject = new JSONObject(response);
      Boolean status = (Boolean) jsonObject.get("status");
      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");

      assertEquals("The profile of User successfully updated", message);
      assertEquals(Boolean.TRUE, status);
      assertEquals(new Integer(200), statusCode);
      System.out.println(response);

      // #4 validate user from DB
      JSONObject userById = getUserById(id);
      String emailActual = (String) userById.getJSONObject("user").get("email");
      String sessionTokenActual = (String) userById.getJSONObject("user").get("sessionToken");

      assertTrue(!emailActual.isEmpty());
      assertEquals(sessionToken, sessionTokenActual);

   }

   @Test
   public void testFullProfileWithLanguage() throws IOException {
      // #1 Create PATIENT
      String emailPATIENT = "patient_p_" + new Date().getTime() + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String sessionToken = patient.getSessionToken();
      int id = patient.getId();

      String email = "new_email" + new Date().getTime() + "@gmail.com";

      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("email", email);
      multipart.addFormField("sessionToken", sessionToken);

      // languages
      String[] languages = {"English", "French"};
//      JSONArray jsonArray = new JSONArray(languages);
//      multipart.addFormField("languages", jsonArray.toString());

      JSONArray languagesJSONArray = new JSONArray();

      // 1
      JSONObject langJSON = new JSONObject();
      langJSON.put("language", "English");
      languagesJSONArray.put(langJSON);

      // 2
      JSONObject langJSON_2 = new JSONObject();
      langJSON_2.put("language", "French");
      languagesJSONArray.put(langJSON_2);

      multipart.addFormField("languages", languagesJSONArray.toString());


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

      // #4 validate user from DB
      JSONObject userById = getUserById(id);
      assertEquals(email, (String) userById.getJSONObject("user").get("email"));
      assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));

      // languages
      String languagesResponse = userById.getJSONObject("user").get("languages").toString();
      for (String iter : languages) {
         assertTrue(languagesResponse.contains(iter));
      }

   }

   @Test
   public void testFullProfileWithSpecialties() throws IOException {
      // #1 Create PATIENT
      String emailPATIENT = "patient_p_" + new Date().getTime() + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpDoctorTest.singUn_To_App_Doctor(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String sessionToken = patient.getSessionToken();
      int id = patient.getId();

      String email = "new_email" + new Date().getTime() + "@gmail.com";

      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("email", email);
      multipart.addFormField("sessionToken", sessionToken);

      // specialties
      String[] specialties = {"Allergist", "Anaesthesiologist", "Biomedical scientist"};

      JSONArray specJSONArray = new JSONArray();
      // 1
      JSONObject specJSON_1 = new JSONObject();
      specJSON_1.put("specialty", "Allergist");
      specJSONArray.put(specJSON_1);
      // 2
      JSONObject specJSON_2 = new JSONObject();
      specJSON_2.put("specialty", "Anaesthesiologist");
      specJSONArray.put(specJSON_2);
      // 3
      JSONObject specJSON_3 = new JSONObject();
      specJSON_3.put("specialty", "Biomedical scientist");
      specJSONArray.put(specJSON_3);
      // set
      multipart.addFormField("specialties", specJSONArray.toString());
      System.out.println("EXPECTED specialties: " + specJSONArray.toString());

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

      // #4 validate user from DB
      JSONObject userById = getUserById(id);
      assertEquals(email, (String) userById.getJSONObject("user").get("email"));
      assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));

      // check specialties
      String specialtiesResponse = userById.getJSONObject("user").get("specialties").toString();
      System.out.println("ACTUAL specialties: " + specialtiesResponse);
      for (String iter : specialties) {
         assertTrue(specialtiesResponse.contains(iter));
      }


   }

   //*****************************   UTIL  ************************************/

   /**
    * Send post
    */
   private JSONObject send(String sessionToken) throws IOException {

      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("sessionToken", sessionToken);

      multipart.addFilePart("file", new File("image.jpg"));
      String response = multipart.finish(); // response from server.

      return new JSONObject(response);
   }

//   @Test
//   public void ttt() {
//      JSONArray languagesJSONArray = new JSONArray();
//
//      // 1
//      JSONObject langJSON = new JSONObject();
//      langJSON.put("language", "English");
//      languagesJSONArray.put(langJSON);
//
//      // 2
//      JSONObject langJSON_2 = new JSONObject();
//      langJSON_2.put("language", "Deutsch");
//      languagesJSONArray.put(langJSON_2);
//
//
//      System.out.println("languages " + languagesJSONArray.toString());
//   }


}
