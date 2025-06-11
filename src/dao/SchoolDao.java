package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;

public class SchoolDao extends DAO {
    public School getSchool(String schoolCd) throws Exception {
        Connection conn = getConnection();
        String query = "SELECT * FROM SCHOOL WHERE CD = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, schoolCd);
        ResultSet rs = pstmt.executeQuery();

        School school = null;
        if (rs.next()) {
            school = new School(
                rs.getString("CD"),
                rs.getString("NAME")
            );
        }
        conn.close();
        return school;
    }
}