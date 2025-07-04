package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_list_subject")
public class TestListSubjectController extends CommonServlet {

  // 科目情報から成績を検索する GET メソッド
  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      // セッションから学校コードを取得
      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      // パラメータ取得（入学年度・クラス・科目コード）
      String entYear = request.getParameter("entYear");
      String classNum = request.getParameter("classNum");
      String subjectCd = request.getParameter("subjectCd");

      // 表示モードを "subject" に設定（画面でどの検索か判断するため）
      request.setAttribute("mode", "subject");

      // 入力チェック：どれかが空欄ならエラーを返して画面へ戻る
      if (entYear == null || classNum == null || subjectCd == null ||
          entYear.isEmpty() || classNum.isEmpty() || subjectCd.isEmpty()) {
        request.setAttribute("error", "入学年度とクラスと科目を選択してください。");
        request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);
        return;
      }

      // 成績データを取得
      TestDao testDao = new TestDao();
      List<Test> results = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, schoolCd);

      // 成績がすべて null または 0 の場合は「存在しない」として扱う
      boolean hasScore = false;
      for (Test t : results) {
        Integer p1 = t.getPoint1();  // 1回目の点数
        Integer p2 = t.getPoint2();  // 2回目の点数
        if ((p1 != null && p1 > 0) || (p2 != null && p2 > 0)) {
          hasScore = true;
          break;
        }
      }

      // 点数がなければエラーメッセージ
      if (!hasScore) {
        request.setAttribute("error", "科目情報が存在しませんでした。");
      }

      // 科目名を取得して画面に渡す
      SubjectDao subjectDao = new SubjectDao();
      Subject subject = subjectDao.select(subjectCd, schoolCd);

      // subject が null のときは JSTL で判断しやすいように null をセット（空文字にしない）
      request.setAttribute("subjectName", subject != null ? subject.getName() : null);

      // 成績リストを JSP に渡す
      request.setAttribute("resultList", results);

      // 結果を表示する画面へ転送
      request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);

    } catch (Exception e) {
      // 例外が出たときはサーブレット例外としてスロー
      throw new ServletException(e);
    }
  }

  // POST メソッドは使わない（空のまま）
  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // POST は使用しない
  }
}
