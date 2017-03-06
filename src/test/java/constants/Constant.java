package constants;

/**
 * @author malex
 */
public class Constant {

   public final static Double LATITUDE = 31.5550281;
   public final static Double LONGITUDE = 74.3112118;

      public final static String URL_BASE = "http://bookadoc.online.mocha6007.mochahost.com/BookADocWeb-1.0.0";
//   public final static String URL_BASE = "http://localhost:8080";

   public final static String URL_SIGN_UP = URL_BASE + "/user/SignUp";
   public final static String URL_SIGN_IN = URL_BASE + "/user/SignIn";
   public final static String URL_FOLLOW_USER = URL_BASE + "/user/followUser";
   public final static String URL_UN_FOLLOW_USER = URL_BASE + "/user/unFollowUser";


   public final static String URL_HIRE_STORE_APPOINTMENT = URL_BASE + "/appointment/hireDoctor/storeAppointment";

   public final static String URL_UPDATE_USER_PROFILE = URL_BASE + "/user/updateUser";

   public final static String URL_GET_USER_BY_ID = URL_BASE + "/user/getUserById";

   public final static String URL_GET_LANGUAGE = URL_BASE + "/language/getAll";


   // Appointment
   public final static String URL_GET_APPOINTMENT_OF_PATIENTS = URL_BASE + "/appointment/getAppointmentOfPatients";
   public final static String URL_CANCEL_APPOINTMENT = URL_BASE + "/appointment/cancelAppointment";
   public final static String URL_APPROVE_APPOINTMENT = URL_BASE + "/appointment/approveAppointment";


}
