package media;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.test.user.GetUserByIdTest;
import controller.test.user.SignUpDoctorTest;
import model.User;
import org.json.JSONObject;
import org.junit.Test;
import updateUser.MultipartUtility;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 *         Created by User on 03.03.2017.
 */
public class UpdateDoctorProfileTest {

   @Test
   public void testUpdateDoctor() throws IOException {

      // #1 Create DOCTOR
      String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
      Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
      String passwordDOCTOR = "12345678";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
      int id = doctor.getId();
      String sessionToken = doctor.getSessionToken();

      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("sessionToken", sessionToken);

      // chronicMedications
      List<University> universities = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
         universities.add(new University("Name_" + i, "Degree_" + i, "200" + i));
      }
      String chronicMedications = new Gson().toJson(universities);
      multipart.addFormField("universities", chronicMedications);

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


      // ********************  #3 get patient by id *************************************

      JSONObject userById = GetUserByIdTest.getUserById(id);
      assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));

      // get chronicMedicationsList
      String universitiesList = userById.getJSONObject("user").get("universities").toString();
      System.out.println("universities: " + universitiesList);
      Type listType = new TypeToken<ArrayList<University>>() {
      }.getType();
      List<University> universityList = new Gson().fromJson(universitiesList, listType);

      Collections.sort(universityList, (o1, o2) -> o1.getDegree().compareTo(o2.getDegree()));
      Collections.sort(universities, (o1, o2) -> o1.getDegree().compareTo(o2.getDegree()));

      for (int i = 0; i < universityList.size(); i++) {
         assertEquals(universityList.get(i).getYear(), universities.get(i).getYear());
         assertEquals(universityList.get(i).getNameUniversity(), universities.get(i).getNameUniversity());
         assertEquals(universityList.get(i).getDegree(), universities.get(i).getDegree());
      }

   }


   @Test
   public void testRE_UpdateDoctor() throws IOException {

      // #1 Create DOCTOR
      String emailDOCTOR = "doctor_d_d" + new Random().nextInt(243656300) + "@gmail.com";
      Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
      Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
      String passwordDOCTOR = "12345678";
      String loginModeDOCTOR = "EMAIL";

      User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
      int id = doctor.getId();
      String sessionToken = doctor.getSessionToken();

      // #2 send request
      MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart.addFormField("sessionToken", sessionToken);

      // chronicMedications
      List<University> universities = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
         universities.add(new University("Name_" + i, "Degree_" + i, "200" + i));
      }
      String chronicMedications = new Gson().toJson(universities);
      multipart.addFormField("universities", chronicMedications);

      multipart.addFilePart("file", new File("image.jpg"));
      String response = multipart.finish(); // response from server.

      JSONObject jsonObject = new JSONObject(response);
      Boolean status = (Boolean) jsonObject.get("status");
      String message = (String) jsonObject.get("message");
      Integer statusCode = (Integer) jsonObject.get("statusCode");

      assertEquals("The profile of User successfully updated", message);
      assertEquals(Boolean.TRUE, status);
      assertEquals(new Integer(200), statusCode);


      // ********************  #3 get patient by id *************************************

      JSONObject userById = GetUserByIdTest.getUserById(id);
      assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));

      // get chronicMedicationsList
      String universitiesList = userById.getJSONObject("user").get("universities").toString();
      System.out.println("universities: " + universitiesList);
      Type listType = new TypeToken<ArrayList<University>>() {
      }.getType();
      List<University> universityList = new Gson().fromJson(universitiesList, listType);

      //  ***************  #4 RE Update user profile ******************************

      // #2 send request
      MultipartUtility multipart_RE = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
      multipart_RE.addFormField("sessionToken", sessionToken);

      for (int i = 0; i < universityList.size(); i++) {
         universityList.get(i).setYear("333" + i);
         universityList.get(i).setNameUniversity("New University" + i);
         universityList.get(i).setDegree("NEW Degree" + i);
      }
      String chronicMedications_RE = new Gson().toJson(universityList);
      multipart_RE.addFormField("universities", chronicMedications_RE);

      String finish = multipart_RE.finish();// response from server.
      System.out.println("RE update " + finish);

      JSONObject userById_RE = GetUserByIdTest.getUserById(id);

      // get chronicMedicationsList
      String universitiesList_RE = userById_RE.getJSONObject("user").get("universities").toString();
      System.out.println("RE: universities: " + universitiesList_RE);
      Type listTyp_RE = new TypeToken<ArrayList<University>>() {
      }.getType();
      List<University> universityList_RE = new Gson().fromJson(universitiesList_RE, listTyp_RE);

      Collections.sort(universityList, (o1, o2) -> o1.getDegree().compareTo(o2.getDegree()));
      Collections.sort(universityList_RE, (o1, o2) -> o1.getDegree().compareTo(o2.getDegree()));

      for (int i = 0; i < universityList.size(); i++) {
         assertEquals(universityList.get(i).getYear(), universityList_RE.get(i).getYear());
         assertEquals(universityList.get(i).getNameUniversity(), universityList_RE.get(i).getNameUniversity());
         assertEquals(universityList.get(i).getDegree(), universityList_RE.get(i).getDegree());
      }
   }


   private class University implements Serializable {
      private int universityId;
      private String nameUniversity;
      private String degree;
      private String year;

      public University(String nameUniverisity, String degree, String year) {
         this.nameUniversity = nameUniverisity;
         this.degree = degree;
         this.year = year;
      }

      public int getUniversityId() {
         return universityId;
      }

      public void setUniversityId(int universityId) {
         this.universityId = universityId;
      }

      public String getNameUniversity() {
         return nameUniversity;
      }

      public void setNameUniversity(String nameUniversity) {
         this.nameUniversity = nameUniversity;
      }

      public String getDegree() {
         return degree;
      }

      public void setDegree(String degree) {
         this.degree = degree;
      }

      public String getYear() {
         return year;
      }

      public void setYear(String year) {
         this.year = year;
      }
   }
}
