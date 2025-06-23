package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.ClassNum;

public class ClassNumDao {

    public List<ClassNum> findAll(String schoolCd) throws Exception {
        List<ClassNum> list = new ArrayList<>();
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/JavaSD");

        try (
            Connection con = ds.getConnection();
            PreparedStatement st = con.prepareStatement("SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ?")
        ) {
            st.setString(1, schoolCd);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ClassNum c = new ClassNum();
                    c.setSchoolCd(rs.getString("SCHOOL_CD"));
                    c.setClassNum(rs.getString("CLASS_NUM"));
                    list.add(c);
                }
            }
        }
        return list;
    }
}