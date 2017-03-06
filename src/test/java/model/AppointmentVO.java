package model;

import java.io.Serializable;

/**
 * @author malex
 */
public class AppointmentVO implements Serializable {

   private int appointmentId;
   private String patientEmail;
   private String patientImageUrl;
   private String patientName;

   private long appointmentTime;
   private long appointmentDate;
   private String appointmentMode;
   private String patientDes;

   private String appointmentStatus;

   public AppointmentVO() {
   }

   public int getAppointmentId() {
      return appointmentId;
   }

   public void setAppointmentId(int appointmentId) {
      this.appointmentId = appointmentId;
   }

   public String getPatientEmail() {
      return patientEmail;
   }

   public void setPatientEmail(String patientEmail) {
      this.patientEmail = patientEmail;
   }

   public String getPatientImageUrl() {
      return patientImageUrl;
   }

   public void setPatientImageUrl(String patientImageUrl) {
      this.patientImageUrl = patientImageUrl;
   }

   public String getPatientName() {
      return patientName;
   }

   public void setPatientName(String patientName) {
      this.patientName = patientName;
   }

   public long getAppointmentTime() {
      return appointmentTime;
   }

   public void setAppointmentTime(long appointmentTime) {
      this.appointmentTime = appointmentTime;
   }

   public long getAppointmentDate() {
      return appointmentDate;
   }

   public void setAppointmentDate(long appointmentDate) {
      this.appointmentDate = appointmentDate;
   }

   public String getAppointmentMode() {
      return appointmentMode;
   }

   public void setAppointmentMode(String appointmentMode) {
      this.appointmentMode = appointmentMode;
   }

   public String getPatientDes() {
      return patientDes;
   }

   public void setPatientDes(String patientDes) {
      this.patientDes = patientDes;
   }

   public String getAppointmentStatus() {
      return appointmentStatus;
   }

   public void setAppointmentStatus(String appointmentStatus) {
      this.appointmentStatus = appointmentStatus;
   }
}
