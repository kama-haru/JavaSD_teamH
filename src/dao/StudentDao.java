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

    // JNDIを使用してデータベース接続を取得
    private Connection getConnection() throws Exception {
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/JavaSD");
        return ds.getConnection();
    }

    // 1. 学生情報をデータベースに保存（登録）する
    public boolean save(Student student) throws Exception {
        String sql = "INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Studentオブジェクトから値を取得し、SQLのプレースホルダにセット
            ps.setString(1, student.getNo());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getEntYear());
            ps.setString(4, student.getClassNum());
            ps.setBoolean(5, student.isAttend());
            ps.setString(6, student.getSchoolCd());

            // SQLを実行し、影響のあった行数を取得
            int rows = ps.executeUpdate();
            // 1行以上更新されていれば成功（trueを返す）
            return rows > 0;
        }
    }

    // 2. 学生番号(no)で学生情報を1件検索する
    public Student findByNo(String no, String schoolCd) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE NO = ? AND SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, no);
            ps.setString(2, schoolCd);
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


    // 3. 学生情報を更新する
    public boolean update(Student student) throws Exception {
        // 更新対象は氏名、クラス、在学状況。学生番号(NO)をキーに更新
        String sql = "UPDATE STUDENT SET NAME = ?, CLASS_NUM = ?, IS_ATTEND = ? WHERE NO = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getClassNum());
            ps.setBoolean(3, student.isAttend());
            ps.setString(4, student.getNo()); // WHERE句の条件

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // 4. 指定された条件で学生リストを絞り込み検索する
    public List<Student> filter(int entYear, String classNum, boolean isAttend, String schoolCd) throws Exception {
        List<Student> list = new ArrayList<>();
        // 基本のSQL文。学校コードでの絞り込みは必須
        StringBuilder sql = new StringBuilder("SELECT * FROM STUDENT WHERE SCHOOL_CD = ?");
        // パラメータを格納するリスト
        List<Object> params = new ArrayList<>();
        params.add(schoolCd);

        // 入学年度が指定されている場合、条件とパラメータを追加
        if (entYear > 0) {
            sql.append(" AND ENT_YEAR = ?");
            params.add(entYear);
        }
        // クラス番号が指定されている場合、条件とパラメータを追加
        if (classNum != null && !classNum.isEmpty()) {
            sql.append(" AND CLASS_NUM = ?");
            params.add(classNum);
        }
        // 在学中フラグがtrueの場合、条件とパラメータを追加
        if (isAttend) {
            sql.append(" AND IS_ATTEND = ?");
            params.add(true);
        }

        // 最後にソート順を指定
        sql.append(" ORDER BY ENT_YEAR, NO");

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            // パラメータリストから順番に値をセット
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            // 結果をループしてStudentオブジェクトのリストを作成
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
 // SCHOOL_CD と NO の組み合わせで学生を検索
    public Student findBySchoolCdAndNo(String schoolCd, String no) throws Exception {
        String sql = "SELECT * FROM STUDENT WHERE SCHOOL_CD = ? AND NO = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, schoolCd);
            ps.setString(2, no);
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

}