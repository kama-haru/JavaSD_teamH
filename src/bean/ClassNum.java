package bean;

import java.io.Serializable;

public class ClassNum implements Serializable {
    private String schoolCd;
    private String classNum;

    public String getSchoolCd() { return schoolCd; }
    public void setSchoolCd(String schoolCd) { this.schoolCd = schoolCd; }

    public String getClassNum() { return classNum; }
    public void setClassNum(String classNum) { this.classNum = classNum; }
}