package updateUser;

import controller.test.user.GetUserByIdTest;
import controller.test.user.SignUpDoctorTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static constants.Constant.URL_UPDATE_USER_PROFILE;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class UpdateUser_Name_PHONE_AND_Email {

   @Test
   public void test() throws IOException {

      // STEP 1: new value for user
      String newName = "TEST NAME FULL";
      String newPHONE = "0987654321";
      String newEmail = "new_email_" + new Random().nextInt(1000) + "@outlook.com";


      // STEP 2: create a new user
      String emailDOCTOR = "doctor_d_p" + new Random().nextInt(243656300) + "@mail.com";
      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, "12344656", "EMAIL", "12.122324", "12.12412421");


      // STEP 3: check user in DB
      JSONObject userById = GetUserByIdTest.getUserById(doctor.getId());
      assertEquals(doctor.getSessionToken(), (String) userById.getJSONObject("user").get("sessionToken"));
      assertEquals("77777777777", (String) userById.getJSONObject("user").get("mobile"));  // "mobile"
      assertEquals("Alex Maximov", (String) userById.getJSONObject("user").get("fullName"));   // "fullName"
      assertEquals(emailDOCTOR, (String) userById.getJSONObject("user").get("email")); // "email"


      // STEP 4: update: name, email and phone
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("sessionToken", doctor.getSessionToken());

      multipart.addFormField("email", newEmail);    // set email
      multipart.addFormField("fullName", newName);  // set fullName
      multipart.addFormField("mobile", newPHONE);   // set mobile

      String response = multipart.finish(); // response from server.
      JSONObject jsonObject = new JSONObject(response);
      assertEquals("The profile of User successfully updated", (String) jsonObject.get("message"));
      assertEquals(Boolean.TRUE, jsonObject.get("status"));
      assertEquals(200, jsonObject.get("statusCode"));


      // STEP 5: check user in DB
      JSONObject upUserById = GetUserByIdTest.getUserById(doctor.getId());
      assertEquals(newPHONE, ((String) upUserById.getJSONObject("user").get("mobile")).trim());    // "mobile"
      assertEquals(newName, (String) upUserById.getJSONObject("user").get("fullName"));   // "fullName"
      assertEquals(newEmail, (String) upUserById.getJSONObject("user").get("email"));     // "email"
   }
}