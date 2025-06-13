package dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import bean.Teacher;

public class TeacherDao extends DAO {

    public Teacher findById(String id) throws Exception {
        Teacher teacher = null;
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM teacher WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                teacher = new Teacher();
                teacher.setId(rs.getString("id"));
                teacher.setPassword(rs.getString("password"));
                teacher.setName(rs.getString("name"));
                teacher.setSchoolCd(rs.getString("school_cd"));
            }
        }
        return teacher;
    }

    public boolean existsId(String id) throws Exception {
        boolean exists = false;
        try (Connection con = getConnection()) {
            String sql = "SELECT COUNT(*) FROM teacher WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
        }
        return exists;
    }

    public Teacher login(String id, String password) throws Exception {
        Teacher teacher = findById(id);
        if (teacher == null) {
            return null;
        }
        String inputHashed = Password(password);
        if (inputHashed.equals(Password(teacher.getPassword()))) {
            return teacher;
        } else {
            return null;
        }
    }

    private String Password(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hash);
    }
}
