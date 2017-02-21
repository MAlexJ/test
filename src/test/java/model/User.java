package model;

/**
 * @author malex
 */
public class User {

   private String email;
   private Double latitude;
   private Double longitude;
   private String loginMode;
   private String password;
   private String sessionToken;

   public User() {
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Double getLatitude() {
      return latitude;
   }

   public void setLatitude(Double latitude) {
      this.latitude = latitude;
   }

   public Double getLongitude() {
      return longitude;
   }

   public void setLongitude(Double longitude) {
      this.longitude = longitude;
   }

   public String getLoginMode() {
      return loginMode;
   }

   public void setLoginMode(String loginMode) {
      this.loginMode = loginMode;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getSessionToken() {
      return sessionToken;
   }

   public void setSessionToken(String sessionToken) {
      this.sessionToken = sessionToken;
   }
}
