package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Test;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_regist")
public class TestRegistController extends CommonServlet {
  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String entYear = request.getParameter("entYear");
      String classNum = request.getParameter("classNum");
      String subjectCd = request.getParameter("subjectCd");
      String noStr = request.getParameter("no");

      // ★ JSPに渡すため全てのパラメータをリクエストスコープに保存
      request.setAttribute("entYear", entYear);
      request.setAttribute("classNum", classNum);
      request.setAttribute("subjectCd", subjectCd);
      request.setAttribute("no", noStr);

      // リストデータ（セレクトボックス）
      TestDao testDao = new TestDao();
      request.setAttribute("entYearList", testDao.getEntYearList());
      request.setAttribute("classNumList", testDao.getClassNumList());
      request.setAttribute("subjectList", testDao.getSubjectList());

      // 検索実行条件チェック
      if (entYear != null || classNum != null || subjectCd != null || noStr != null) {
        if (entYear == null || entYear.isEmpty()
         || classNum == null || classNum.isEmpty()
         || subjectCd == null || subjectCd.isEmpty()
         || noStr == null || noStr.isEmpty()) {
          request.setAttribute("errorMessage", "入学年度、クラス、科目、回数をすべて選択してください。");
        } else {
          // ★ すべて揃っていれば検索処理
          List<Test> list = testDao.getStudentListForTestRegistration(entYear, classNum, subjectCd);
          request.setAttribute("list", list);
        }
      }

      request.getRequestDispatcher("/test/test_regist.jsp").forward(request, response);

    } catch (Exception e) {
      throw new IOException(e);
    }
  }

  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // 未使用
  }
}
