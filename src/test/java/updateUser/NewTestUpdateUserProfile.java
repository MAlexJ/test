package updateUser;

import android.ProfileListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.test.user.GetUserByIdTest;
import controller.test.user.SignUpDoctorTest;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class NewTestUpdateUserProfile {

   @Test
   public void testDOCTOR() throws IOException {

      // #1 Create DOCTOR
      String emailDOCTOR = "doctor_d_p" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
      Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
      String passwordDOCTOR = "12345678";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
      int id = doctor.getId();
      String sessionToken = doctor.getSessionToken();

      // ********************  #1 update user profile *************************************

      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("sessionToken", sessionToken);

      List<ProfileListItem> actualList = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
         actualList.add(new ProfileListItem(i, "Title_" + i, "Description_" + i, "200" + i));
      }

      JSONArray medicationArray = new JSONArray();

      for (int i = 0; i < actualList.size(); i++) {
         JSONObject chronicJson = new JSONObject();

         chronicJson.put("title", actualList.get(i).getTitle());
         chronicJson.put("description", actualList.get(i).getDescription());
         chronicJson.put("year", actualList.get(i).getYear());
         medicationArray.put(chronicJson);
      }
      multipart.addFormField("userProfiles", medicationArray.toString());

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

      // ********************  #2 get patient by id *************************************

      JSONObject userById = GetUserByIdTest.getUserById(id);
      assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));

      // get chronicMedicationsList
      String userProfilesList = userById.getJSONObject("user").get("userProfiles").toString();

      // ********************  #3 Assert *************************************

      List<ProfileListItem> expectProfileList = new Gson().fromJson(userProfilesList, new TypeToken<ArrayList<ProfileListItem>>() {
      }.getType());

      for (int i = 0; i < expectProfileList.size(); i++) {
         assertEquals(expectProfileList.get(i).getTitle(), actualList.get(i).getTitle());
         assertEquals(expectProfileList.get(i).getDescription(), actualList.get(i).getDescription());
         assertEquals(expectProfileList.get(i).getYear(), actualList.get(i).getYear());
      }

   }

}
