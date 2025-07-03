package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;

public class SubjectDao extends DAO {

	// 1件取得
	public Subject getSubject(String subjectCd) throws Exception {
		Connection conn = getConnection();
		String query = "SELECT * FROM SUBJECT WHERE CD = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, subjectCd);
		ResultSet rs = pstmt.executeQuery();

		Subject subject = null;
		if (rs.next()) {
			subject = new Subject(rs.getString("SCHOOL_CD"), rs.getString("CD"), rs.getString("NAME"));
		}
		rs.close();
		pstmt.close();
		conn.close();

		return subject;
	}

	// 全件取得（すべての学校用）
	public List<Subject> getAllSubjects() throws Exception {
		List<Subject> list = new ArrayList<>();
		Connection conn = getConnection();
		String sql = "SELECT * FROM SUBJECT ORDER BY CD";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Subject subject = new Subject();
			subject.setSchoolCd(rs.getString("SCHOOL_CD"));
			subject.setCd(rs.getString("CD"));
			subject.setName(rs.getString("NAME"));
			list.add(subject);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// ✅ 追加：学校コードで取得（JSP用対応）
	public List<Subject> getAllSubjects(String schoolCd) throws Exception {
		List<Subject> list = new ArrayList<>();
		Connection conn = getConnection();
		String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, schoolCd);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Subject subject = new Subject();
			subject.setSchoolCd(rs.getString("SCHOOL_CD"));
			subject.setCd(rs.getString("CD"));
			subject.setName(rs.getString("NAME"));
			list.add(subject);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// 挿入
	public void insert(Subject subject) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO SUBJECT (SCHOOL_CD, CD, NAME) VALUES (?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, subject.getSchoolCd());
		pstmt.setString(2, subject.getCd());
		pstmt.setString(3, subject.getName());
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}

	// 学校コードで取得
	public List<Subject> getSubjectsBySchoolCd(String schoolCd) throws Exception {
		List<Subject> list = new ArrayList<>();
		Connection conn = getConnection();
		String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, schoolCd);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Subject subject = new Subject();
			subject.setSchoolCd(rs.getString("SCHOOL_CD"));
			subject.setCd(rs.getString("CD"));
			subject.setName(rs.getString("NAME"));
			list.add(subject);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// 重複チェック
	public boolean exists(String schoolCd, String cd) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT COUNT(*) FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, schoolCd);
		pstmt.setString(2, cd);
		ResultSet rs = pstmt.executeQuery();

		boolean exists = false;
		if (rs.next()) {
			exists = rs.getInt(1) > 0;
		}
		rs.close();
		pstmt.close();
		conn.close();

		return exists;
	}

	// 更新
	public void update(Subject subject) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE SUBJECT SET NAME = ? WHERE CD = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, subject.getName());
		pstmt.setString(2, subject.getCd());
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}

	// 指定の学校コードと科目コードで1件取得
	public Subject getSubjectBySchoolCdAndCd(String schoolCd, String cd) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, schoolCd);
		pstmt.setString(2, cd);
		ResultSet rs = pstmt.executeQuery();

		Subject subject = null;
		if (rs.next()) {
			subject = new Subject();
			subject.setSchoolCd(rs.getString("SCHOOL_CD"));
			subject.setCd(rs.getString("CD"));
			subject.setName(rs.getString("NAME"));
		}
		rs.close();
		pstmt.close();
		conn.close();

		return subject;
	}

	// 削除
	public void deleteBySchoolCdAndCd(String schoolCd, String cd) throws Exception {
		Connection conn = getConnection();
		String sql = "DELETE FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, schoolCd);
		pstmt.setString(2, cd);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}

	// 科目コードで取得（例外処理あり）
	public Subject findByCode(String cd) {
		Subject subject = null;
		try (Connection con = getConnection()) {
			String sql = "SELECT * FROM SUBJECT WHERE CD = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, cd);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				subject = new Subject();
				subject.setCd(rs.getString("CD"));
				subject.setName(rs.getString("NAME"));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
	}

	// 削除（例外処理あり）
	public boolean delete(String cd) {
		try (Connection con = getConnection()) {
			String deleteTestSql = "DELETE FROM TEST WHERE SUBJECT_CD = ?";
			try (PreparedStatement st1 = con.prepareStatement(deleteTestSql)) {
				st1.setString(1, cd);
				st1.executeUpdate();
			}

			String deleteSubjectSql = "DELETE FROM SUBJECT WHERE CD = ?";
			try (PreparedStatement st2 = con.prepareStatement(deleteSubjectSql)) {
				st2.setString(1, cd);
				int rows = st2.executeUpdate();
				return rows > 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 全件取得（例外処理あり）
	public List<Subject> findAll() {
		List<Subject> list = new ArrayList<>();
		try (Connection con = getConnection()) {
			String sql = "SELECT * FROM SUBJECT";
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Subject s = new Subject();
				s.setCd(rs.getString("CD"));
				s.setName(rs.getString("NAME"));
				list.add(s);
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// ✅ 科目コードと学校コードから1件取得（科目名表示用）
	public Subject select(String subjectCd, String schoolCd) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, subjectCd);
		pstmt.setString(2, schoolCd);
		ResultSet rs = pstmt.executeQuery();

		Subject subject = null;
		if (rs.next()) {
			subject = new Subject();
			subject.setSchoolCd(rs.getString("SCHOOL_CD"));
			subject.setCd(rs.getString("CD"));
			subject.setName(rs.getString("NAME"));
		}

		rs.close();
		pstmt.close();
		conn.close();

		return subject;
	}
}