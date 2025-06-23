package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.Student;

public class StudentDao {

    public List<Student> filter(int entYear, String classNum, boolean isAttend, String schoolCd) throws Exception {
        List<Student> list = new ArrayList<>();
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/JavaSD");

        StringBuilder sql = new StringBuilder("SELECT * FROM STUDENT WHERE SCHOOL_CD = ?");
        List<Object> params = new ArrayList<>();
        params.add(schoolCd);

        if (entYear > 0) {
            sql.append(" AND ENT_YEAR = ?");
            params.add(entYear);
        }
        if (classNum != null && !classNum.isEmpty()) {
            sql.append(" AND CLASS_NUM = ?");
            params.add(classNum);
        }
        if (isAttend) {
            sql.append(" AND IS_ATTEND = ?");
            params.add(true);
        }
        sql.append(" ORDER BY ENT_YEAR, NO");

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getString("NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setAttend(rs.getBoolean("IS_ATTEND"));
                    s.setSchoolCd(rs.getString("SCHOOL_CD"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    public boolean save(Student student) throws Exception {
        int line = 0;
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/JavaSD");

        String sql = "INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, student.getNo());
            st.setString(2, student.getName());
            st.setInt(3, student.getEntYear());
            st.setString(4, student.getClassNum());
            st.setBoolean(5, student.isAttend());
            st.setString(6, student.getSchoolCd());
            line = st.executeUpdate();
        }
        return line > 0;
    }

    /**
     * 学生番号を条件に学生情報を1件取得します。
     * @param no 学生番号
     * @return 該当する学生情報。見つからない場合はnull。
     * @throws Exception
     */
    public Student findByNo(String no) throws Exception {
        Student student = null;
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/JavaSD");

        String sql = "SELECT * FROM STUDENT WHERE NO = ?";

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, no);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    student = new Student();
                    student.setNo(rs.getString("NO"));
                    student.setName(rs.getString("NAME"));
                    student.setEntYear(rs.getInt("ENT_YEAR"));
                    student.setClassNum(rs.getString("CLASS_NUM"));
                    student.setAttend(rs.getBoolean("IS_ATTEND"));
                    student.setSchoolCd(rs.getString("SCHOOL_CD"));
                }
            }
        }
        return student;
    }

    /**
     * 学生情報を更新します。
     * @param student 更新情報が格納されたStudentオブジェクト
     * @return 更新が成功した場合はtrue、失敗した場合はfalse
     * @throws Exception
     */
    public boolean update(Student student) throws Exception {
        int line = 0;
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/JavaSD");

        // 氏名、クラス、在学状況を更新します。入学年度と学生番号は変更しない前提です。
        String sql = "UPDATE STUDENT SET NAME = ?, CLASS_NUM = ?, IS_ATTEND = ? WHERE NO = ?";

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, student.getName());
            st.setString(2, student.getClassNum());
            st.setBoolean(3, student.isAttend());
            st.setString(4, student.getNo()); // WHERE句の条件

            line = st.executeUpdate();
        }
        return line > 0;
    }
}