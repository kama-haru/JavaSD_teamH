package bean;

import java.io.Serializable;

/**
 * Represents a single test record. This bean contains all fields needed
 * across the application, including joined data from Student and Subject tables.
 */
public class Test implements Serializable {

  // Fields primarily from the TEST table
  private String studentNo;
  private String subjectCd;
  private String schoolCd;
  private String classNum;
  private int no;
  private int point;

  // Fields joined from other tables for display purposes
  private String studentName; // From STUDENT table
  private String subjectName; // From SUBJECT table
  private int entYear;        // From STUDENT table

  // Fields for the "科目別成績参照" (Subject-wise Grade Reference) screen
  private int point1;
  private int point2;

  // A general-purpose name field (as seen in your original bean)
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

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public int getPoint1() {
    return point1;
  }

  public void setPoint1(int point1) {
    this.point1 = point1;
  }

  public int getPoint2() {
    return point2;
  }

  public void setPoint2(int point2) {
    this.point2 = point2;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public int getEntYear() {
      return entYear;
  }

  public void setEntYear(int entYear) {
      this.entYear = entYear;
  }
}
