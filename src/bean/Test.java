package bean;

import java.io.Serializable;

public class Test implements Serializable {

  private String studentNo;
  private String subjectCd;
  private String schoolCd;
  private String classNum;
  private Integer no;
  private Integer point;

  private String studentName;
  private String subjectName;
  private Integer entYear;

  private Integer point1;
  private Integer point2;

  private String name;

  // --- Getters and Setters ---

  public String getStudentNo() {
    return studentNo;
  }

  public void setStudentNo(String studentNo) {
    this.studentNo = studentNo;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSubjectCd() {
    return subjectCd;
  }

  public void setSubjectCd(String subjectCd) {
    this.subjectCd = subjectCd;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public String getSchoolCd() {
    return schoolCd;
  }

  public void setSchoolCd(String schoolCd) {
    this.schoolCd = schoolCd;
  }

  public String getClassNum() {
    return classNum;
  }

  public void setClassNum(String classNum) {
    this.classNum = classNum;
  }

  public Integer getPoint() {
    return point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  public Integer getPoint1() {
    return point1;
  }

  public void setPoint1(Integer point1) {
    this.point1 = point1;
  }

  public Integer getPoint2() {
    return point2;
  }

  public void setPoint2(Integer point2) {
    this.point2 = point2;
  }

  public Integer getNo() {
    return no;
  }

  public void setNo(Integer no) {
    this.no = no;
  }

  public Integer getEntYear() {
    return entYear;
  }

  public void setEntYear(Integer entYear) {
    this.entYear = entYear;
  }
}
