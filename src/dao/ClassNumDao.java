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

    // JNDIを使用してデータベース接続を取得するプライベートメソッド
    private Connection getConnection() throws Exception {
        InitialContext ic = new InitialContext();
        // web.xmlやcontext.xmlで設定されたリソース名 "jdbc/JavaSD" を参照
        DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/JavaSD");
        return ds.getConnection();
    }

    // CLASS_NUMテーブルから全てのクラス情報を取得する
    public List<ClassNum> selectAll() throws Exception {
        List<ClassNum> list = new ArrayList<>();
        String sql = "SELECT SCHOOL_CD, CLASS_NUM FROM CLASS_NUM ORDER BY CLASS_NUM";

        // try-with-resources構文で、ConnectionとPreparedStatementが自動的にクローズされる
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            // 結果セットをループして、1行ずつClassNumオブジェクトに変換
            while (rs.next()) {
                ClassNum c = new ClassNum();
                c.setSchoolCd(rs.getString("SCHOOL_CD"));
                c.setClassNum(rs.getString("CLASS_NUM"));
                list.add(c); // リストに追加
            }
        }
        return list;
    }

    // クラス番号(CLASS_NUM)のみを文字列のリストとして取得する
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

    // 指定された学校コード(schoolCd)に紐づくクラス情報の一覧を取得する
    public List<ClassNum> findAll(String schoolCd) throws Exception {
        List<ClassNum> list = new ArrayList<>();
        String sql = "SELECT * FROM CLASS_NUM WHERE SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // SQLのプレースホルダ '?' に値をセット
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