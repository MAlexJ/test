package model;

import java.io.Serializable;

/**
 * @author malex
 */
public class University implements Serializable {

    private int universityId;
    private String nameUniversity;
    private String degree;
    private String year;

    public University(String nameUniverisity, String degree, String year) {
        this.nameUniversity = nameUniverisity;
        this.degree = degree;
        this.year = year;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public String getNameUniversity() {
        return nameUniversity;
    }

    public void setNameUniversity(String nameUniversity) {
        this.nameUniversity = nameUniversity;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}