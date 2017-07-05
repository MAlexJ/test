package getNearestDoctors;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.test.user.SignUpPatientTest;
import model.User;
import org.junit.Test;

import java.util.Random;

import static constants.Constant.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author malex
 */
public class GetNearestDoctorByFilterLocation {


    // 1. create patient with latitude and longitude
    // 2. find doctor use API "getNearestDoctors" by location
    @Test
    public void test() throws UnirestException {

        // #1 Create PATIENT
        String emailPATIENT = "patient_p_g" + new Random().nextInt(243656300) + "@gmail.com";
        Double latitudePATIENT = LATITUDE + new Random().nextInt(100);
        Double longitudePATIENT = LONGITUDE + new Random().nextInt(100);
        String passwordPATIENT = "0987654321";
        String loginModePATIENT = "EMAIL";

        User patient = SignUpPatientTest.singUn_To_App_Patient(emailPATIENT, passwordPATIENT, loginModePATIENT, latitudePATIENT.toString(), longitudePATIENT.toString());

        String query = "{\n" +
                "\t\"email\":\"" + patient.getEmail() + "\",\n" +
                "\t\"listIndex\":1,\n" +
                "\t\"sessionToken\":\"" + patient.getSessionToken() + "\",\n" +
                "\t\"latitude\":\"32.7766642\",\n" +
                "\t\"longitude\":\"-96.79698789999999\",\n" +
                "\t\"locationAddress\":\"Dasmariñas, Calabarzon, Philippines,\"" +
                "}";

        HttpResponse<JsonNode> response = Unirest.post(URL_GET_NEAREST_DOCTOR)
                .header("content-type", "application/json")
                .header("cache-control", "no-cache")
                .body(query)
                .asJson();

        String message = (String) response.getBody().getObject().get("message");
        Boolean status = (Boolean) response.getBody().getObject().get("status");
        Integer statusCode = (Integer) response.getBody().getObject().get("statusCode");

        // assert
        assertEquals(message, "There are no doctors around");
        assertEquals(status, Boolean.TRUE);
        assertEquals(statusCode, new Integer(200));

    }

}