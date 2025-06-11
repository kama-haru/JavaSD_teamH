package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Student;

public class StudentDao extends DAO {
    public Student getStudent(String studentNo) throws Exception {
        Connection conn = getConnection();
        String query = "SELECT * FROM STUDENT WHERE NO = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, studentNo);
        ResultSet rs = pstmt.executeQuery();

        Student student = null;
        if (rs.next()) {
            student = new Student(
                rs.getString("NO"),
                rs.getString("NAME"),
                rs.getInt("ENT_YEAR"),
                rs.getString("CLASS_NUM"),
                rs.getBoolean("IS_ATTEND"),
                rs.getString("SCHOOL_CD")
            );
        }
        conn.close();
        return student;
    }
}