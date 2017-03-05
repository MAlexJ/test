package model;

import java.io.Serializable;

/**
 * @author malex
 */
public class ChronicMedicalCondition implements Serializable {

    private int chronicMedicalConditionId;
    private String chronicDisease;
    private String hospital;
    private String year;

    public ChronicMedicalCondition(String chronicDisease, String hospital, String year) {
        this.chronicDisease = chronicDisease;
        this.hospital = hospital;
        this.year = year;
    }

    public int getChronicMedicalConditionId() {
        return chronicMedicalConditionId;
    }

    public void setChronicMedicalConditionId(int chronicMedicalConditionId) {
        this.chronicMedicalConditionId = chronicMedicalConditionId;
    }

    public String getChronicDisease() {
        return chronicDisease;
    }

    public void setChronicDisease(String chronicDisease) {
        this.chronicDisease = chronicDisease;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
