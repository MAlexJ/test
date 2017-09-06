package constants;

/**
 * @author malex
 */
public class Constant {

	public final static Double LATITUDE = 31.5550281;
	public final static Double LONGITUDE = 74.3112118;

	//   public final static String URL_BASE = "http://bookadoc.online.mocha6007.mochahost.com/BookADocWeb-1.0.0";
	public final static String URL_BASE = "http://localhost:8080";

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
	public final static String URL_STORE_APPOINTMENT = URL_BASE + "/appointment/hireDoctor/storeAppointment";
	public final static String URL_UPDATE_APPOINTMENT = URL_BASE + "/appointment/updateAppointment";

	// schedule
	public final static String URL_STORE_SCHEDULE = URL_BASE + "/schedule/storeSchedule";
	public final static String URL_GET_SCHEDULE = URL_BASE + "/schedule/getSchedule";


	// getNearest doctor
	public final static String URL_GET_NEAREST_DOCTOR = URL_BASE + "/user/getNearestDoctors";

	// FCM
	public final static String FCM_REGISTER_DEVICE = URL_BASE + "/fcm/registerDevice";

	// CHECK EMAIL API
	public final static String CHECK_EMAIL = URL_BASE + "/user/checkEmail";

	// MAGIC API
	public final static String CHECK_UPDATES_API = URL_BASE + "/api/checkUpdates";
	public final static String RESET_API_API = URL_BASE + "/api/resetAPI";
	public final static String SET_STATUS_API = URL_BASE + "/api/setStatusAPI";

	// pastconsult API
	public final static String URL_CHANGE_APPOINTMENT_STATUS = URL_BASE + "/pastconsult/setStatusAppointmentForTest";
	public final static String URL_GET_ALL_CONSULT_FOR_DOCTOR = URL_BASE + "/pastconsult/getAllConsultForDoctor";



}