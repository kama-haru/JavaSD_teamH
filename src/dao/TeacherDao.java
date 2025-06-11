package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Teacher;

public class TeacherDao extends DAO {
    public Teacher getTeacher(String teacherId) throws Exception {
        Connection conn = getConnection();
        String query = "SELECT * FROM TEACHER WHERE ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, teacherId);
        ResultSet rs = pstmt.executeQuery();

        Teacher teacher = null;
        if (rs.next()) {
            teacher = new Teacher(
                rs.getString("ID"),
                rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getString("SCHOOL_CD")
            );
        }
        conn.close();
        return teacher;
    }
}