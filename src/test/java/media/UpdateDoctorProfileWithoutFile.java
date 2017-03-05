package media;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.test.user.GetUserByIdTest;
import controller.test.user.SignUpDoctorTest;
import model.University;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static constants.Constant.LATITUDE;
import static constants.Constant.LONGITUDE;
import static constants.Constant.URL_UPDATE_USER_PROFILE;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by malex on 05.03.17.
 *
 * @author malex
 */
public class UpdateDoctorProfileWithoutFile {

    @Test
    public void testFullProfileDoctorWithoutFile() throws IOException {
        // #1 Create DOCTOR
        String emailDOCTOR = "doctor_d" + new Random().nextInt(243656300) + "@gmail.com";
        Double latitudeDOCTOR = LATITUDE + new Random().nextInt(10);
        Double longitudeDOCTOR = LONGITUDE + new Random().nextInt(20);
        String passwordDOCTOR = "12345678";
        String loginModeDOCTOR = "EMAIL";

        User doctor = SignUpDoctorTest.singUn_To_App_Doctor(emailDOCTOR, passwordDOCTOR, loginModeDOCTOR, latitudeDOCTOR.toString(), longitudeDOCTOR.toString());
        int id = doctor.getId();
        String sessionToken = doctor.getSessionToken();

        String email = "new_email" + new Date().getTime() + "@gmail.com";
        String phone = "0888888888";
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

        // chronicMedications
        List<University> universities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            universities.add(new University("Name_" + i, "Degree_" + i, "200" + i));
        }
        String chronicMedications = new Gson().toJson(universities);
        multipart.addFormField("universities", chronicMedications);

        // TODO >>>> without file

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

}
