package model;

import java.io.Serializable;

/**
 * @author malex
 */
public class ChronicMedication implements Serializable {

    private int chronicMedicationId;
    private String nameMedication;
    private String description;

    public ChronicMedication(String nameMedication, String description) {
        this.nameMedication = nameMedication;
        this.description = description;
    }

    public int getChronicMedicationId() {
        return chronicMedicationId;
    }

    public void setChronicMedicationId(int chronicMedicationId) {
        this.chronicMedicationId = chronicMedicationId;
    }

    public String getNameMedication() {
        return nameMedication;
    }

    public void setNameMedication(String nameMedication) {
        this.nameMedication = nameMedication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
