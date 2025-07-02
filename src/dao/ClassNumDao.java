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

	private Connection getConnection() throws Exception {
		InitialContext ic = new InitialContext();
		DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/JavaSD");
		return ds.getConnection();
	}

	// 全件取得（ClassNum型）
	public List<ClassNum> selectAll() throws Exception {
		List<ClassNum> list = new ArrayList<>();
		String sql = "SELECT SCHOOL_CD, CLASS_NUM FROM CLASS_NUM ORDER BY CLASS_NUM";

		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				ClassNum c = new ClassNum();
				c.setSchoolCd(rs.getString("SCHOOL_CD"));
				c.setClassNum(rs.getString("CLASS_NUM"));
				list.add(c);
			}
		}

		return list;
	}

	// クラス番号だけの一覧（文字列型）
	public List<String> getClassNumList() throws Exception {
		List<String> list = new ArrayList<>();
		String sql = "SELECT DISTINCT CLASS_NUM FROM CLASS_NUM ORDER BY CLASS_NUM";

		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				list.add(rs.getString("CLASS_NUM"));
			}
		}

		return list;
	}

	// 学校コードに該当するクラス一覧を取得（ClassNum型）
	public List<ClassNum> findAll(String schoolCd) throws Exception {
		List<ClassNum> list = new ArrayList<>();
		String sql = "SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ?";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

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

	// ✅ 学校コードに該当するクラス番号一覧（文字列型）
	public List<String> getClassNumList(String schoolCd) throws Exception {
		List<String> list = new ArrayList<>();
		String sql = "SELECT DISTINCT CLASS_NUM FROM CLASS_NUM WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, schoolCd);

			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					list.add(rs.getString("CLASS_NUM"));
				}
			}
		}

		return list;
	}
}