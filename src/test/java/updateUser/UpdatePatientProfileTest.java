package updateUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.test.user.GetUserByIdTest;
import controller.test.user.SignUpPatientTest;
import model.ChronicMedicalCondition;
import model.ChronicMedication;
import model.User;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

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


        // TODO >>> check chronicMedications
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

        // TODO >>> check chronicMedicalConditions
    }

    @Test
    public void reUpdatePatientProfileChronicMedications() throws IOException {

        // ************  #1 Create PATIENT ****************************
        String emailPATIENT = "patient_p_m" + new Date().getTime() + "@gmail.com";
        Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
        Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
        String passwordPATIENT = "12345678";
        String loginModePATIENT = "EMAIL";

        User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
        String sessionToken = patient.getSessionToken();
        String email = patient.getEmail();

        //  ***************  #2 Update user profile ******************************
        MultipartUtility multipart = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
        multipart.addFormField("sessionToken", sessionToken);

        // >>> chronicMedications
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

        // ********************  #3 get patient by id *************************************
        int id = patient.getId();

        JSONObject userById = GetUserByIdTest.getUserById(id);
        assertEquals(email, (String) userById.getJSONObject("user").get("email"));
        assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));

        // get chronicMedicationsList
        String chronicMedicationsList = userById.getJSONObject("user").get("chronicMedications").toString();
        System.out.println("> " + chronicMedicationsList);
        Type listType = new TypeToken<ArrayList<ChronicMedication>>() {
        }.getType();
        List<ChronicMedication> medicationsList = new Gson().fromJson(chronicMedicationsList, listType);

        //  ***************  #4 REUpdate user profile ******************************

        MultipartUtility multipartRE = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
        multipartRE.addFormField("sessionToken", sessionToken);

        // set new values to ChronicMedicalCondition
        for (int i = 0; i < medicationsList.size(); i++) {
            medicationsList.get(i).setDescription("New Description " + i);
            medicationsList.get(i).setNameMedication("New Name " + i);
        }
        String chronicMedicalConditionsRE = new Gson().toJson(medicationsList);
        multipartRE.addFormField("chronicMedications", chronicMedicalConditionsRE);

        String finish = multipartRE.finish();// response from server.
        System.out.println(finish);


        // ********************  #5 get patient by id *************************************
        JSONObject userByIdRE = GetUserByIdTest.getUserById(id);


        // assert
        String chronicMedicationsListRE = userByIdRE.getJSONObject("user").get("chronicMedications").toString();
        System.out.println("> " + chronicMedicationsListRE);
        Type listTypeRE = new TypeToken<ArrayList<ChronicMedication>>() {
        }.getType();
        List<ChronicMedication> medicationsListRE = new Gson().fromJson(chronicMedicationsListRE, listTypeRE);

        Collections.sort(medicationsList, (o1, o2) -> o1.getDescription().compareTo(o2.getDescription()));
        Collections.sort(medicationsListRE, (o1, o2) -> o1.getDescription().compareTo(o2.getDescription()));

        for (int i = 0; i < medicationsList.size(); i++) {
            assertEquals(medicationsList.get(i).getDescription(), medicationsListRE.get(i).getDescription());
            assertEquals(medicationsList.get(i).getNameMedication(), medicationsListRE.get(i).getNameMedication());
            assertEquals(medicationsList.get(i).getChronicMedicationId(), medicationsListRE.get(i).getChronicMedicationId());
        }
    }


    @Test
    public void reUpdatePatientProfileChronicMedicalCondition() throws IOException {
        // ************  #1 Create PATIENT ****************************
        String emailPATIENT = "patient_p_m" + new Date().getTime() + "@gmail.com";
        Double latitudePATIENT = LATITUDE + new Random().nextInt(10);
        Double longitudePATIENT = LONGITUDE + new Random().nextInt(20);
        String passwordPATIENT = "12345678";
        String loginModePATIENT = "EMAIL";

        User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());
        String sessionToken = patient.getSessionToken();
        String email = patient.getEmail();

        //  ***************  #2 Update user profile ******************************
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

        // ********************  #3 get patient by id *************************************
        int id = patient.getId();

        JSONObject userById = GetUserByIdTest.getUserById(id);
        assertEquals(email, (String) userById.getJSONObject("user").get("email"));
        assertEquals(sessionToken, (String) userById.getJSONObject("user").get("sessionToken"));

        // get chronicMedicationsList
        String chronicMedicationsList = userById.getJSONObject("user").get("chronicMedicalConditions").toString();
        System.out.println("> " + chronicMedicationsList);
        Type listType = new TypeToken<ArrayList<ChronicMedicalCondition>>() {
        }.getType();
        List<ChronicMedicalCondition> chronicMedicalConditionList = new Gson().fromJson(chronicMedicationsList, listType);

        //  ***************  #4 REUpdate user profile ******************************

        MultipartUtility multipartRE = new MultipartUtility(URL_UPDATE_USER_PROFILE, "UTF-8");
        multipartRE.addFormField("sessionToken", sessionToken);

        // set new values to ChronicMedicalCondition
        for (int i = 0; i < chronicMedicalConditionList.size(); i++) {
            chronicMedicalConditionList.get(i).setChronicDisease("New ChronicDisease " + i);
            chronicMedicalConditionList.get(i).setHospital("New Hospital " + i);
            chronicMedicalConditionList.get(i).setYear("New Year " + i);
        }
        String chronicMedicalConditionsRE = new Gson().toJson(chronicMedicalConditionList);
        multipartRE.addFormField("chronicMedicalConditions", chronicMedicalConditionsRE);

        String finish = multipartRE.finish();// response from server.
        System.out.println(finish);


        // ********************  #5 get patient by id *************************************
        JSONObject userByIdRE = GetUserByIdTest.getUserById(id);


        // assert
        String chronicMedicationsListRE = userByIdRE.getJSONObject("user").get("chronicMedicalConditions").toString();
        System.out.println("> " + chronicMedicationsListRE);
        Type listTypeRE = new TypeToken<ArrayList<ChronicMedicalCondition>>() {
        }.getType();
        List<ChronicMedicalCondition> medicationsListRE = new Gson().fromJson(chronicMedicationsListRE, listTypeRE);

        Collections.sort(chronicMedicalConditionList, (o1, o2) -> o1.getChronicDisease().compareTo(o2.getChronicDisease()));
        Collections.sort(medicationsListRE, (o1, o2) -> o1.getChronicDisease().compareTo(o2.getChronicDisease()));

        for (int i = 0; i < medicationsListRE.size(); i++) {
            assertEquals(medicationsListRE.get(i).getChronicDisease(), chronicMedicalConditionList.get(i).getChronicDisease());
            assertEquals(medicationsListRE.get(i).getChronicMedicalConditionId(), chronicMedicalConditionList.get(i).getChronicMedicalConditionId());
            assertEquals(medicationsListRE.get(i).getYear(), chronicMedicalConditionList.get(i).getYear());
            assertEquals(medicationsListRE.get(i).getHospital(), chronicMedicalConditionList.get(i).getHospital());
        }
    }

}
