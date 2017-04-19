package testTimeTopic;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Ignore;
import org.junit.Test;

import static constants.Constant.URL_SEARCH_DOCTOR;

/**
 * Created by User on 18.04.2017.
 */
@Ignore
public class TimeTest {

   @Test
   public void testLiterature() throws UnirestException {
      for (int i = 0; i < 20; i++) {
         String query = "{  \n" +
                 " \"email\": \"doc@gmail.com\",  \n" +
                 " \"sessionToken\":\"4854b06a-8bd2-496b-a4fb-300ee059b3dd-1492188830884\",\n" +
                 " \"listIndex\":1\n" +
                 "} ";

         long startTime = System.currentTimeMillis();

         HttpResponse<JsonNode> response = Unirest.post(URL_SEARCH_DOCTOR)
                 .header("content-type", "application/json")
                 .header("cache-control", "no-cache")
                 .body(query)
                 .asJson();

         long endTime = System.currentTimeMillis();

         System.out.println("Time response " + (endTime - startTime) + " milliseconds, code: " + response.getBody().getObject().get("statusCode"));

      }
   }
}
