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

      // パラメータの未入力チェック
      if (entYear == null || classNum == null || subjectCd == null ||
          entYear.isEmpty() || classNum.isEmpty() || subjectCd.isEmpty()) {
        request.setAttribute("errorMessage", "すべての項目を選択してください。");
      } else {
        TestDao testDao = new TestDao();
        List<Test> results = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, schoolCd);

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
          request.setAttribute("errorMessage", "科目情報が存在しませんでした。");
        }

        // 科目名取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.select(subjectCd, schoolCd);

        request.setAttribute("resultList", results); // <-- JSPと合わせるため修正
        request.setAttribute("subjectName", subject != null ? subject.getName() : ""); // 科目名
      }

      request.setAttribute("mode", "subject");
      request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // Not used
  }
}
