package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.Subject;
import bean.Test;

public class TestDao {

	private Connection getConnection() throws Exception {
		InitialContext ic = new InitialContext();
		DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/JavaSD");
		return ds.getConnection();
	}

	public List<Test> getTestList(String entYear, String classNum, String subjectCd, int no) throws Exception {
		List<Test> list = new ArrayList<>();
		String sql = "SELECT t.STUDENT_NO, t.POINT, s.NAME AS STUDENT_NAME, s.ENT_YEAR, t.CLASS_NUM " + // ←
																										// 修正
				"FROM TEST t JOIN STUDENT s ON t.STUDENT_NO = s.NO "
				+ "WHERE s.ENT_YEAR = ? AND t.CLASS_NUM = ? AND t.SUBJECT_CD = ? AND t.NO = ? "
				+ "ORDER BY t.STUDENT_NO";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setInt(1, Integer.parseInt(entYear));
			st.setString(2, classNum);
			st.setString(3, subjectCd);
			st.setInt(4, no);

			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Test test = new Test();
					test.setStudentNo(rs.getString("STUDENT_NO"));
					test.setPoint(rs.getInt("POINT"));
					test.setStudentName(rs.getString("STUDENT_NAME"));
					test.setEntYear(rs.getInt("ENT_YEAR"));
					test.setClassNum(rs.getString("CLASS_NUM")); // ← 追加
					list.add(test);
				}
			}
		}

		return list;
	}

	public List<Test> selectStudentScores(String studentNo, String schoolCd) throws Exception {
	    List<Test> list = new ArrayList<>();
	    String sql = "SELECT t.*, s.NAME AS STUDENT_NAME, sub.NAME AS SUBJECT_NAME " +
	                 "FROM TEST t " +
	                 "JOIN STUDENT s ON t.STUDENT_NO = s.NO " +
	                 "JOIN SUBJECT sub ON t.SUBJECT_CD = sub.CD " +
	                 "WHERE t.STUDENT_NO = ? AND s.SCHOOL_CD = ? " +
	                 "ORDER BY t.NO";

	    try (Connection con = getConnection();
	         PreparedStatement st = con.prepareStatement(sql)) {

	        st.setString(1, studentNo);
	        st.setString(2, schoolCd);

	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                Test t = new Test();
	                t.setStudentNo(rs.getString("STUDENT_NO"));
	                t.setSubjectCd(rs.getString("SUBJECT_CD"));
	                t.setNo(rs.getInt("NO"));
	                t.setPoint(rs.getInt("POINT"));
	                t.setStudentName(rs.getString("STUDENT_NAME"));
	                t.setSubjectName(rs.getString("SUBJECT_NAME"));
	                list.add(t);
	            }
	        }
	    }
	    return list;
	}


	// Used by subject-based search
	public List<Test> selectBySubject(String subjectCd, int no) throws Exception {
		List<Test> list = new ArrayList<>();

		String sql = "SELECT t.STUDENT_NO, s.NAME AS STUDENT_NAME, t.POINT, s.CLASS_NUM, s.ENT_YEAR "
				+ "FROM TEST t JOIN STUDENT s ON t.STUDENT_NO = s.NO " + "WHERE t.SUBJECT_CD = ? AND t.NO = ? "
				+ "ORDER BY s.ENT_YEAR, s.CLASS_NUM, t.STUDENT_NO";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, subjectCd);
			st.setInt(2, no);

			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Test test = new Test();
					test.setStudentNo(rs.getString("STUDENT_NO"));
					test.setStudentName(rs.getString("STUDENT_NAME"));
					test.setPoint(rs.getInt("POINT"));
					test.setClassNum(rs.getString("CLASS_NUM"));
					test.setEntYear(rs.getInt("ENT_YEAR"));
					list.add(test);
				}
			}
		}

		return list;
	}

	public List<Test> selectByEntYearClassSubject(int entYear, String classNum, String subjectCd) throws Exception {
		List<Test> list = new ArrayList<>();
		String sql = "SELECT t.STUDENT_NO, s.NAME AS STUDENT_NAME, s.ENT_YEAR, t.CLASS_NUM, sub.NAME AS SUBJECT_NAME, "
				+ "MAX(CASE WHEN t.NO = 1 THEN t.POINT END) AS POINT1, "
				+ "MAX(CASE WHEN t.NO = 2 THEN t.POINT END) AS POINT2 " + "FROM TEST t "
				+ "JOIN STUDENT s ON t.STUDENT_NO = s.NO " + "JOIN SUBJECT sub ON t.SUBJECT_CD = sub.CD "
				+ "WHERE s.ENT_YEAR = ? AND t.CLASS_NUM = ? AND t.SUBJECT_CD = ? "
				+ "GROUP BY t.STUDENT_NO, s.NAME, s.ENT_YEAR, t.CLASS_NUM, sub.NAME " + "ORDER BY t.STUDENT_NO";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setInt(1, entYear);
			st.setString(2, classNum);
			st.setString(3, subjectCd);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Test t = new Test();
				t.setStudentNo(rs.getString("STUDENT_NO"));
				t.setStudentName(rs.getString("STUDENT_NAME"));
				t.setEntYear(rs.getInt("ENT_YEAR"));
				t.setClassNum(rs.getString("CLASS_NUM"));
				t.setSubjectName(rs.getString("SUBJECT_NAME")); // ✅ Set subject
																// name
				t.setPoint1(rs.getInt("POINT1"));
				t.setPoint2(rs.getInt("POINT2"));
				list.add(t);
			}
		}
		return list;
	}

	// 2. 成績登録用：指定回数の点数取得
	public List<Test> selectByEntYearClassSubjectAndNo(int entYear, String classNum, String subjectCd, int no)
			throws Exception {
		List<Test> list = new ArrayList<>();
		String sql = "SELECT t.student_no, s.name AS student_name, s.ent_year, t.class_num, t.subject_cd, t.point "
				+ "FROM test t JOIN student s ON t.student_no = s.no "
				+ "WHERE s.ent_year = ? AND t.class_num = ? AND t.subject_cd = ? AND t.no = ?";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, entYear);
			st.setString(2, classNum);
			st.setString(3, subjectCd);
			st.setInt(4, no);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Test test = new Test();
				test.setStudentNo(rs.getString("student_no"));
				test.setStudentName(rs.getString("student_name"));
				test.setEntYear(rs.getInt("ent_year"));
				test.setClassNum(rs.getString("class_num"));
				test.setSubjectCd(rs.getString("subject_cd"));
				test.setPoint(rs.getInt("point"));
				test.setNo(no);
				list.add(test);
			}
		}
		return list;
	}

	// 成績保存（存在すれば更新、なければINSERT）
	public void saveOrUpdate(Test test) throws Exception {
		try (Connection con = getConnection()) {
			String updateSql = "UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND NO = ?";
			PreparedStatement update = con.prepareStatement(updateSql);
			update.setInt(1, test.getPoint());
			update.setString(2, test.getStudentNo());
			update.setString(3, test.getSubjectCd());
			update.setInt(4, test.getNo());
			int count = update.executeUpdate();

			if (count == 0) {
				String insertSql = "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = con.prepareStatement(insertSql);
				insert.setString(1, test.getStudentNo());
				insert.setString(2, test.getSubjectCd());
				insert.setString(3, test.getSchoolCd());
				insert.setInt(4, test.getNo());
				insert.setInt(5, test.getPoint());
				insert.setString(6, test.getClassNum());
				insert.executeUpdate();
			}
		}
	}

	// 成績削除
	public void delete(int entYear, String classNum, String subjectCd, int no, String studentNo) throws Exception {
		String sql = "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND NO = ? AND CLASS_NUM = ? "
				+ "AND SCHOOL_CD = (SELECT SCHOOL_CD FROM STUDENT WHERE NO = ? AND ENT_YEAR = ? AND CLASS_NUM = ?)";

		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, studentNo);
			st.setString(2, subjectCd);
			st.setInt(3, no);
			st.setString(4, classNum);
			st.setString(5, studentNo);
			st.setInt(6, entYear);
			st.setString(7, classNum);

			st.executeUpdate();
		}
	}

	// 成績更新（個別）
	public void updatePoint(int year, String classNum, String subjectCd, int no, String studentNo, int point)
			throws Exception {
		String sql = "UPDATE TEST SET POINT = ? WHERE ENT_YEAR = ? AND CLASS_NUM = ? AND SUBJECT_CD = ? AND NO = ? AND STUDENT_NO = ?";

		try (Connection conn = this.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, point);
			stmt.setInt(2, year);
			stmt.setString(3, classNum);
			stmt.setString(4, subjectCd);
			stmt.setInt(5, no);
			stmt.setString(6, studentNo);

			stmt.executeUpdate();
		}
	}

	// 入学年度リストを取得
	public List<Integer> getEntYearList() throws Exception {
		List<Integer> list = new ArrayList<>();
		String sql = "SELECT DISTINCT ENT_YEAR FROM STUDENT ORDER BY ENT_YEAR DESC";

		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				list.add(rs.getInt("ENT_YEAR"));
			}
		}
		return list;
	}

	// クラス番号リストを取得
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

	// 科目リストを取得
	public List<Subject> getSubjectList() throws Exception {
		List<Subject> list = new ArrayList<>();
		String sql = "SELECT CD, NAME FROM SUBJECT ORDER BY CD";

		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				Subject s = new Subject();
				s.setCd(rs.getString("CD"));
				s.setName(rs.getString("NAME"));
				list.add(s);
			}
		}
		return list;
	}

	// 成績保存（UPDATE なければ INSERT）
	public void saveOrUpdate(String classNum, String subjectCd, int no, String studentNo, int point) throws Exception {
		try (Connection con = getConnection()) {
			String updateSql = "UPDATE TEST SET POINT = ? WHERE CLASS_NUM = ? AND SUBJECT_CD = ? AND NO = ? AND STUDENT_NO = ?";
			PreparedStatement update = con.prepareStatement(updateSql);
			update.setInt(1, point);
			update.setString(2, classNum);
			update.setString(3, subjectCd);
			update.setInt(4, no);
			update.setString(5, studentNo);
			int count = update.executeUpdate();

			if (count == 0) {
				String insertSql = "INSERT INTO TEST (CLASS_NUM, SUBJECT_CD, NO, STUDENT_NO, POINT) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement insert = con.prepareStatement(insertSql);
				insert.setString(1, classNum);
				insert.setString(2, subjectCd);
				insert.setInt(3, no);
				insert.setString(4, studentNo);
				insert.setInt(5, point);
				insert.executeUpdate();
			}
		}
	}

	// 成績削除（SCHOOL_CD や ENT_YEAR 条件は削除）
	public void delete(String classNum, String subjectCd, int no, String studentNo) throws Exception {
		String sql = "DELETE FROM TEST WHERE CLASS_NUM = ? AND SUBJECT_CD = ? AND NO = ? AND STUDENT_NO = ?";
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, classNum);
			st.setString(2, subjectCd);
			st.setInt(3, no);
			st.setString(4, studentNo);
			st.executeUpdate();
		}
	}

	public List<Test> getAllStudentsForGradeEntry(int entYear, String classNum, String schoolCd) throws Exception {
		List<Test> list = new ArrayList<>();
		String sql = "SELECT s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, s.ENT_YEAR, s.CLASS_NUM "
				+ "FROM STUDENT s WHERE s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND s.SCHOOL_CD = ? AND s.IS_ATTEND = TRUE "
				+ "ORDER BY s.NO";
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, entYear);
			st.setString(2, classNum);
			st.setString(3, schoolCd);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Test test = new Test();
				test.setStudentNo(rs.getString("STUDENT_NO"));
				test.setStudentName(rs.getString("STUDENT_NAME"));
				test.setEntYear(rs.getInt("ENT_YEAR"));
				test.setClassNum(rs.getString("CLASS_NUM"));
				list.add(test);
			}
		}
		return list;
	}

	public List<Test> getStudentListForTestRegistration(String entYear, String classNum, String subjectCd) throws Exception {
	    List<Test> list = new ArrayList<>();
	    String sql = "SELECT s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, s.ENT_YEAR, s.CLASS_NUM, t.POINT " +
	                 "FROM STUDENT s " +
	                 "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND t.SUBJECT_CD = ? " +
	                 "WHERE s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND s.IS_ATTEND = TRUE " +
	                 "ORDER BY s.NO";

	    try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
	        st.setString(1, subjectCd);
	        st.setInt(2, Integer.parseInt(entYear));
	        st.setString(3, classNum);

	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                Test test = new Test();
	                test.setStudentNo(rs.getString("STUDENT_NO"));
	                test.setStudentName(rs.getString("STUDENT_NAME"));
	                test.setEntYear(rs.getInt("ENT_YEAR"));
	                test.setClassNum(rs.getString("CLASS_NUM"));
	                test.setPoint(rs.getInt("POINT"));
	                list.add(test);
	            }
	        }
	    }

	    return list;
	}

	public List<Test> selectByEntYearClassSubject(String entYear, String classNum, String subjectCd, String schoolCd) throws Exception {
	    List<Test> list = new ArrayList<>();
	    String sql = "SELECT s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, s.ENT_YEAR, " +
	                 "COALESCE(t.CLASS_NUM, s.CLASS_NUM) AS CLASS_NUM, sub.NAME AS SUBJECT_NAME, " +
	                 "MAX(CASE WHEN t.NO = 1 THEN t.POINT END) AS POINT1, " +
	                 "MAX(CASE WHEN t.NO = 2 THEN t.POINT END) AS POINT2 " +
	                 "FROM STUDENT s " +
	                 "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND t.SUBJECT_CD = ? " +
	                 "LEFT JOIN SUBJECT sub ON t.SUBJECT_CD = sub.CD " +
	                 "WHERE s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND s.SCHOOL_CD = ? " +
	                 "GROUP BY s.NO, s.NAME, s.ENT_YEAR, s.CLASS_NUM, sub.NAME " +
	                 "ORDER BY s.NO";

	    try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
	        st.setString(1, subjectCd);
	        st.setString(2, entYear);
	        st.setString(3, classNum);
	        st.setString(4, schoolCd);

	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                Test t = new Test();
	                t.setStudentNo(rs.getString("STUDENT_NO"));
	                t.setStudentName(rs.getString("STUDENT_NAME"));
	                t.setEntYear(rs.getInt("ENT_YEAR"));
	                t.setClassNum(rs.getString("CLASS_NUM")); // now always available
	                t.setSubjectName(rs.getString("SUBJECT_NAME"));
	                t.setPoint1(rs.getInt("POINT1"));
	                t.setPoint2(rs.getInt("POINT2"));
	                list.add(t);
	            }
	        }
	    }
	    return list;
	}

	// 入学年度一覧を学校コードに応じて取得
	public List<Integer> getEntYearList(String schoolCd) throws Exception {
		List<Integer> list = new ArrayList<>();

		String sql = "SELECT DISTINCT ENT_YEAR FROM STUDENT WHERE SCHOOL_CD = ? ORDER BY ENT_YEAR DESC";

		try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, schoolCd);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("ENT_YEAR"));
			}
		}

		return list;
	}

	// 成績保存（点数あり、SCHOOL_CD付きバージョン）
	public void saveOrUpdateWithPoint(String schoolCd, String classNum, String subjectCd, int no, String studentNo,
			int point) throws Exception {
		try (Connection con = getConnection()) {
			String updateSql = "UPDATE TEST SET POINT = ? WHERE CLASS_NUM = ? AND SUBJECT_CD = ? AND NO = ? AND STUDENT_NO = ? AND SCHOOL_CD = ?";
			try (PreparedStatement update = con.prepareStatement(updateSql)) {
				update.setInt(1, point);
				update.setString(2, classNum);
				update.setString(3, subjectCd);
				update.setInt(4, no);
				update.setString(5, studentNo);
				update.setString(6, schoolCd);
				int count = update.executeUpdate();

				if (count == 0) {
					String insertSql = "INSERT INTO TEST (CLASS_NUM, SUBJECT_CD, NO, STUDENT_NO, POINT, SCHOOL_CD) VALUES (?, ?, ?, ?, ?, ?)";
					try (PreparedStatement insert = con.prepareStatement(insertSql)) {
						insert.setString(1, classNum);
						insert.setString(2, subjectCd);
						insert.setInt(3, no);
						insert.setString(4, studentNo);
						insert.setInt(5, point);
						insert.setString(6, schoolCd);
						insert.executeUpdate();
					}
				}
			}
		}
	}

	// 成績削除（SCHOOL_CD 条件あり）
	public void deleteWithPoint(String classNum, String subjectCd, int no, String studentNo, String schoolCd)
			throws Exception {
		String sql = "DELETE FROM TEST WHERE CLASS_NUM = ? AND SUBJECT_CD = ? AND NO = ? AND STUDENT_NO = ? AND SCHOOL_CD = ?";
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, classNum);
			st.setString(2, subjectCd);
			st.setInt(3, no);
			st.setString(4, studentNo);
			st.setString(5, schoolCd);
			st.executeUpdate();
		}
	}

	public void saveOrUpdateWithSchoolCd(String schoolCd, String classNum, String subjectCd, int no, String studentNo,
			int point) throws Exception {
		try (Connection con = getConnection()) {
			String updateSql = "UPDATE TEST SET POINT = ? WHERE CLASS_NUM = ? AND SUBJECT_CD = ? AND NO = ? AND STUDENT_NO = ?";
			PreparedStatement update = con.prepareStatement(updateSql);
			update.setInt(1, point);
			update.setString(2, classNum);
			update.setString(3, subjectCd);
			update.setInt(4, no);
			update.setString(5, studentNo);
			int count = update.executeUpdate();

			if (count == 0) {
				String insertSql = "INSERT INTO TEST (CLASS_NUM, SUBJECT_CD, NO, STUDENT_NO, POINT, SCHOOL_CD) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement insert = con.prepareStatement(insertSql);
				insert.setString(1, classNum);
				insert.setString(2, subjectCd);
				insert.setInt(3, no);
				insert.setString(4, studentNo);
				insert.setInt(5, point);
				insert.setString(6, schoolCd); // ✅ ここがないと失敗します
				insert.executeUpdate();
			}
		}
	}
	public List<Test> selectByEntYearClassSubject(String entYear, String classNum, String subjectCd, int no, String schoolCd) throws Exception {
	    List<Test> list = new ArrayList<>();
	    String sql = "SELECT s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, s.ENT_YEAR, s.CLASS_NUM, t.POINT " +
	                 "FROM STUDENT s " +
	                 "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND t.SUBJECT_CD = ? AND t.NO = ? " +
	                 "WHERE s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND s.SCHOOL_CD = ? " +
	                 "ORDER BY s.NO";

	    try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
	        st.setString(1, subjectCd);
	        st.setInt(2, no);
	        st.setString(3, entYear);
	        st.setString(4, classNum);
	        st.setString(5, schoolCd);

	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                Test t = new Test();
	                t.setStudentNo(rs.getString("STUDENT_NO"));
	                t.setStudentName(rs.getString("STUDENT_NAME"));
	                t.setEntYear(Integer.parseInt(entYear));
	                t.setClassNum(classNum);
	                t.setPoint(rs.getInt("POINT"));
	                list.add(t);
	            }
	        }
	    }
	    return list;
	}
}
