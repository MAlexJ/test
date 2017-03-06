package android;

import java.io.Serializable;

/**
 * Created by User on 06.03.2017.
 */
public class ProfileListItem implements Serializable {

   private int id = 0;
   private String title;
   private String description;
   private String year;

   public ProfileListItem(int id, String title, String description, String year) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.year = year;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getYear() {
      return year;
   }

   public void setYear(String year) {
      this.year = year;
   }
}