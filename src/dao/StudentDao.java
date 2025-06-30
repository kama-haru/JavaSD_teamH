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

    private Connection getConnection() throws Exception {
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/JavaSD");
        return ds.getConnection();
    }

    // 1. 学生を保存
    public boolean save(Student student) throws Exception {
        String sql = "INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getNo());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getEntYear());
            ps.setString(4, student.getClassNum());
            ps.setBoolean(5, student.isAttend());
            ps.setString(6, student.getSchoolCd());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // 2. 学生番号で検索
    public Student findByNo(String no) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE NO = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, no);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Student student = new Student();
                student.setNo(rs.getString("NO"));
                student.setName(rs.getString("NAME"));
                student.setEntYear(rs.getInt("ENT_YEAR"));
                student.setClassNum(rs.getString("CLASS_NUM"));
                student.setAttend(rs.getBoolean("IS_ATTEND"));
                student.setSchoolCd(rs.getString("SCHOOL_CD"));
                return student;
            }
            return null;
        }
    }

    // 3. 学生情報を更新
    public boolean update(Student student) throws Exception {
        String sql = "UPDATE STUDENT SET NAME = ?, CLASS_NUM = ?, IS_ATTEND = ? WHERE NO = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getClassNum());
            ps.setBoolean(3, student.isAttend());
            ps.setString(4, student.getNo());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // 4. 条件で学生リストをフィルター
    public List<Student> filter(int entYear, String classNum, boolean isAttend, String schoolCd) throws Exception {
        List<Student> list = new ArrayList<>();
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

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setNo(rs.getString("NO"));
                student.setName(rs.getString("NAME"));
                student.setEntYear(rs.getInt("ENT_YEAR"));
                student.setClassNum(rs.getString("CLASS_NUM"));
                student.setAttend(rs.getBoolean("IS_ATTEND"));
                student.setSchoolCd(rs.getString("SCHOOL_CD"));
                list.add(student);
            }
        }

        return list;
    }
}
