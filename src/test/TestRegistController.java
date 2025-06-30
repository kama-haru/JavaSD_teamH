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
      // 検索条件パラメータ取得
      String entYear = request.getParameter("entYear");
      String classNum = request.getParameter("classNum");
      String subjectCd = request.getParameter("subjectCd");
      String noStr = request.getParameter("no");

      // セレクトボックス用データ
      TestDao testDao = new TestDao();
      request.setAttribute("entYearList", testDao.getEntYearList());
      request.setAttribute("classNumList", testDao.getClassNumList());
      request.setAttribute("subjectList", testDao.getSubjectList());

      // 検索条件がそろっている場合のみ検索結果を取得
      if (entYear != null && classNum != null && subjectCd != null && noStr != null) {
        int year = Integer.parseInt(entYear);
        int no = Integer.parseInt(noStr);

        List<Test> list = testDao.getTestList(entYear, classNum, subjectCd, no);
        request.setAttribute("list", list);
        request.setAttribute("entYear", entYear);
        request.setAttribute("classNum", classNum);
        request.setAttribute("subjectCd", subjectCd);
        request.setAttribute("no", no);
      }

      request.getRequestDispatcher("/test/test_regist.jsp").forward(request, response);

    } catch (Exception e) {
      throw new IOException(e);
    }
  }

@Override
protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// TODO 自動生成されたメソッド・スタブ

}
}