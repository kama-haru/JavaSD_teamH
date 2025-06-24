package bean;

import java.io.Serializable;

public class Subject implements Serializable {
    private String schoolCd;
    private String cd;
    private String name;

    public Subject() {}

    public Subject(String schoolCd, String cd, String name) {
        this.schoolCd = schoolCd;
        this.cd = cd;
        this.name = name;
    }

    public String getSchoolCd() { return schoolCd; }
    public void setSchoolCd(String schoolCd) { this.schoolCd = schoolCd; }

    public String getCd() { return cd; }
    public void setCd(String cd) { this.cd = cd; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
