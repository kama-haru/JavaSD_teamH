package test;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_regist")
public class TestRegistController extends CommonServlet {

  // 成績登録画面の表示（GET処理）
  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("UTF-8");

    // セッションから学校コードを取得
    HttpSession session = request.getSession();
    String schoolCd = (String) session.getAttribute("schoolCd");

    // DAO の用意
    TestDao testDao = new TestDao();
    ClassNumDao classDao = new ClassNumDao();
    SubjectDao subjectDao = new SubjectDao();

    // セレクトボックスの選択肢を取得してリクエストに設定
    request.setAttribute("entYearList", testDao.getEntYearList(schoolCd));
    request.setAttribute("classNumList", classDao.getClassNumList(schoolCd));
    request.setAttribute("subjectList", subjectDao.getAllSubjects(schoolCd));

    // 画面から送られてきたパラメータを取得
    String entYear = request.getParameter("entYear");
    String classNum = request.getParameter("classNum");
    String subjectCd = request.getParameter("subjectCd");
    String noStr = request.getParameter("no");           // 回数（文字列）
    String searched = request.getParameter("searched");  // 検索ボタンが押されたか判定

    // 入力値を再表示のためにセット（入力保持）
    request.setAttribute("entYear", entYear);
    request.setAttribute("classNum", classNum);
    request.setAttribute("subjectCd", subjectCd);
    request.setAttribute("no", noStr);

    // 検索処理（検索ボタンが押されていた場合）
    if ("true".equals(searched)) {
      // 入力チェック：どれかが空欄ならエラー
      if (entYear == null || entYear.isEmpty() ||
          classNum == null || classNum.isEmpty() ||
          subjectCd == null || subjectCd.isEmpty() ||
          noStr == null || noStr.isEmpty()) {
        request.setAttribute("error", "入学年度とクラスと科目と回数を選択してください");
      } else {
        try {
          // 回数を整数に変換
          int no = Integer.parseInt(noStr);

          // 成績リストを取得
          List<Test> list = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, no, schoolCd);

          // 画面に渡す
          request.setAttribute("list", list);
        } catch (NumberFormatException e) {
          // 回数が整数でない場合のエラー
          request.setAttribute("error", "回数の形式が正しくありません");
        }
      }
    }

    // 成績登録画面（JSP）にフォワード
    request.getRequestDispatcher("/test/GRMR001.jsp").forward(request, response);
  }

  // POSTメソッドは未使用
  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // POSTは未使用
  }
}
