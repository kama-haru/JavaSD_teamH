package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_delete")
public class TestDeleteController extends CommonServlet {

  // CommonServlet に準拠した post() メソッドを実装（doPostではなく）
  @Override
  protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String[] deleteStudentNos = request.getParameterValues("delete");
    String entYear = request.getParameter("entYear");
    String classNum = request.getParameter("classNum");
    String subjectCd = request.getParameter("subjectCd");
    int no = Integer.parseInt(request.getParameter("no"));
    int year = Integer.parseInt(entYear);

    if (deleteStudentNos != null) {
      TestDao dao = new TestDao();
      for (String studentNo : deleteStudentNos) {
        dao.delete(year, classNum, subjectCd, no, studentNo);
      }
    }

    // 検索結果にリダイレクト（GETで戻す）
    response.sendRedirect(request.getContextPath() + "/main/test_list?entYear=" + entYear +
                          "&classNum=" + classNum +
                          "&subjectCd=" + subjectCd +
                          "&no=" + no);
  }

  @Override
  protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // 不使用
  }
}
