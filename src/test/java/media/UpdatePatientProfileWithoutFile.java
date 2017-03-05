package media;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.test.user.GetUserByIdTest;
import controller.test.user.SignUpPatientTest;
import model.ChronicMedicalCondition;
import model.ChronicMedication;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class UpdatePatientProfileWithoutFile {

    @Test
    public void testFullProfilePatientWithoutFile() throws IOException {

        // #1 Create PATIENT
        String emailPATIENT = "patient_p_m" + new Date().getTime() + "@gmail.com";
        Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
        Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
        String passwordPATIENT = "12345678";
        String loginModePATIENT = "EMAIL";

        User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
        String sessionToken = patient.getSessionToken();
        int id = patient.getId();

        String email = "new_email" + new Date().getTime() + "@gmail.com";
        String phone = "0987654321";
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

        // TODO >>>> without file

        // chronicMedications
        List<ChronicMedication> medications = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            medications.add(new ChronicMedication("Panadol_" + i, "Text_Description_" + i));
        }
        String chronicMedications = new Gson().toJson(medications);
        multipart.addFormField("chronicMedications", chronicMedications);

        // ChronicMedicalCondition
        List<ChronicMedicalCondition> conditions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            conditions.add(new ChronicMedicalCondition("Disease_" + i, "Hospital N" + i, "200" + i));
        }
        String chronicMedicalConditions = new Gson().toJson(conditions);
        multipart.addFormField("chronicMedicalConditions", chronicMedicalConditions);

        String response = multipart.finish(); // response from server.

        JSONObject jsonObject = new JSONObject(response);
        Boolean status = (Boolean) jsonObject.get("status");
        String message = (String) jsonObject.get("message");
        Integer statusCode = (Integer) jsonObject.get("statusCode");

        assertEquals("The profile of User successfully updated", message);
        assertEquals(Boolean.TRUE, status);
        assertEquals(new Integer(200), statusCode);
        System.out.println(response);


        // ********************  get patient by id *************************************
        JSONObject userByIdRE = GetUserByIdTest.getUserById(id);

        // assert chronicMedicalConditions
        String chronicMedicationsListRE = userByIdRE.getJSONObject("user").get("chronicMedicalConditions").toString();
        System.out.println("> " + chronicMedicationsListRE);
        Type listTypeRE = new TypeToken<ArrayList<ChronicMedicalCondition>>() {
        }.getType();
        List<ChronicMedicalCondition> medicationsListRE = new Gson().fromJson(chronicMedicationsListRE, listTypeRE);

        Collections.sort(conditions, (o1, o2) -> o1.getChronicDisease().compareTo(o2.getChronicDisease()));
        Collections.sort(medicationsListRE, (o1, o2) -> o1.getChronicDisease().compareTo(o2.getChronicDisease()));

        for (int i = 0; i < medicationsListRE.size(); i++) {
            assertEquals(medicationsListRE.get(i).getChronicDisease(), conditions.get(i).getChronicDisease());
            assertEquals(medicationsListRE.get(i).getYear(), conditions.get(i).getYear());
            assertEquals(medicationsListRE.get(i).getHospital(), conditions.get(i).getHospital());
        }

        // assert chronicMedications
        String medications_LIST = userByIdRE.getJSONObject("user").get("chronicMedications").toString();
        System.out.println("> " + medications_LIST);
        Type listType_RE = new TypeToken<ArrayList<ChronicMedication>>() {
        }.getType();
        List<ChronicMedication> medications_ListRE = new Gson().fromJson(medications_LIST, listType_RE);

        Collections.sort(medications, (o1, o2) -> o1.getDescription().compareTo(o2.getDescription()));
        Collections.sort(medications_ListRE, (o1, o2) -> o1.getDescription().compareTo(o2.getDescription()));

        for (int i = 0; i < medications.size(); i++) {
            assertEquals(medications.get(i).getDescription(), medications_ListRE.get(i).getDescription());
            assertEquals(medications.get(i).getNameMedication(), medications_ListRE.get(i).getNameMedication());
        }

    }

}
