package media;


import controller.test.user.SignUpPatientTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author malex
 */
public class UploadImage {

//   @Test
   public void testUploadImage() throws IOException {

      // #1 Create PATIENT
      String emailPATIENT = "patient_p" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
      Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
      String passwordPATIENT = "12345678";
      String loginModePATIENT = "EMAIL";

      User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
      String email = patient.getEmail();
      String sessionToken = patient.getSessionToken();


      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_MEDIA_UPLOAD, "UTF-8");
      multipart.addFormField("email", email);
      multipart.addFormField("sessionToken", sessionToken);

      multipart.addFilePart("file", new File("image.jpg"));
      String response = multipart.finish(); // response from server.

      JSONObject jsonObject = new JSONObject(response);
      String path = (String) jsonObject.get("path");
      Boolean status = (Boolean) jsonObject.get("status");
      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");


      // #3 assert
      assertEquals(new Integer(200), statusCode);
      assertEquals("Uploaded successfully", message);
      assertEquals(Boolean.TRUE, status);
      assertTrue(!path.isEmpty());

      System.out.println(response);
   }
}
