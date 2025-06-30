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

      // 検索が実行された場合だけチェックする（entYear以外が空でもアクセスはあるため）
      if (request.getParameter("entYear") != null || request.getParameter("classNum") != null || request.getParameter("subjectCd") != null) {
        // 入力チェック
        if (entYear == null || entYear.isEmpty() ||
            classNum == null || classNum.isEmpty() ||
            subjectCd == null || subjectCd.isEmpty()) {

          request.setAttribute("errorMessage", "入学年度、クラスと科目を選択して下さい");
        } else if (noStr != null && !noStr.isEmpty()) {
          // 条件が揃っていれば検索実行
          int no = Integer.parseInt(noStr);
          List<Test> list = testDao.getTestList(entYear, classNum, subjectCd, no);

          request.setAttribute("list", list);
          request.setAttribute("entYear", entYear);
          request.setAttribute("classNum", classNum);
          request.setAttribute("subjectCd", subjectCd);
          request.setAttribute("no", no);
        }
      }

      // JSPへ遷移
      request.getRequestDispatcher("/test/test_regist.jsp").forward(request, response);

    } catch (Exception e) {
      throw new IOException(e);
    }
  }

  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // 成績登録のPOST処理はここに記述予定
  }
}
