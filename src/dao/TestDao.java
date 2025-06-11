package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.TestListStudent;

public class TestDao extends DAO {
    public TestListStudent getTestResult(String studentNo, String subjectCd, int testNo) throws Exception {
        Connection conn = getConnection();
        String query = "SELECT * FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND NO = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, studentNo);
        pstmt.setString(2, subjectCd);
        pstmt.setInt(3, testNo);
        ResultSet rs = pstmt.executeQuery();

        TestListStudent testResult = null;
        if (rs.next()) {
            testResult = new TestListStudent(
                rs.getString("STUDENT_NO"),
                rs.getString("SUBJECT_CD"),
                rs.getString("SCHOOL_CD"),
                rs.getInt("NO"),
                rs.getInt("POINT"),
                rs.getString("CLASS_NUM")
            );
        }
        conn.close();
        return testResult;
    }
}