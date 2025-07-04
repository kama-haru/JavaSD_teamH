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
  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      String entYear = request.getParameter("entYear");
      String classNum = request.getParameter("classNum");
      String subjectCd = request.getParameter("subjectCd");

      request.setAttribute("mode", "subject");

      // 入力チェック：いずれか1つでも空ならエラー
      if (entYear == null || classNum == null || subjectCd == null ||
          entYear.isEmpty() || classNum.isEmpty() || subjectCd.isEmpty()) {
        request.setAttribute("error", "入学年度とクラスと科目を選択してください。");
        request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);
        return;
      }

      // 成績検索
      TestDao testDao = new TestDao();
      List<Test> results = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, schoolCd);

      // 成績がすべて null または 0 の場合はエラー
      boolean hasScore = false;
      for (Test t : results) {
        Integer p1 = t.getPoint1();
        Integer p2 = t.getPoint2();
        if ((p1 != null && p1 > 0) || (p2 != null && p2 > 0)) {
          hasScore = true;
          break;
        }
      }

      if (!hasScore) {
        request.setAttribute("error", "科目情報が存在しませんでした。");
      }

      // 科目名取得
      SubjectDao subjectDao = new SubjectDao();
      Subject subject = subjectDao.select(subjectCd, schoolCd);

      // ✅ JSTL に空文字ではなく null を渡すことで表示制御を正しくする
      request.setAttribute("subjectName", subject != null ? subject.getName() : null);

      request.setAttribute("resultList", results);
      request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // POST は使用しない
  }
}