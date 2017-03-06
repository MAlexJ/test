package android;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 06.03.2017.
 */
public class TestAndroidRequest {

   @Test
   public void test() {

      List<ProfileListItem> list = new ArrayList<>();

      for (int i = 0; i < 3; i++) {
         list.add(new ProfileListItem(i, "Title_" + i, "Description_" + i, "200" + i));
      }


      JSONArray medicationArray = new JSONArray();

      for (int i = 0; i < list.size(); i++) {
         JSONObject chronicJson = new JSONObject();

         chronicJson.put("title", list.get(i).getTitle());
         chronicJson.put("description", list.get(i).getDescription());
         chronicJson.put("year", list.get(i).getYear());

         medicationArray.put(chronicJson);

      }

      System.out.println(medicationArray.toString());

   }
}
