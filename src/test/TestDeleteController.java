package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_delete")
public class TestDeleteController extends CommonServlet {

  // POSTメソッドで成績を削除する処理を行う
  @Override
  protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // 削除対象の学生番号を取得（チェックボックスで選ばれたもの）
    String[] deleteStudentNos = request.getParameterValues("delete");

    // 入学年度・クラス・科目コード・回数を取得
    String entYear = request.getParameter("entYear");
    String classNum = request.getParameter("classNum");
    String subjectCd = request.getParameter("subjectCd");
    int no = Integer.parseInt(request.getParameter("no"));
    int year = Integer.parseInt(entYear); // 入学年度をint型に変換

    // 学生が選ばれていれば削除処理を行う
    if (deleteStudentNos != null) {
      TestDao dao = new TestDao();
      for (String studentNo : deleteStudentNos) {
        dao.delete(year, classNum, subjectCd, no, studentNo);
      }
    }

    // 成績一覧画面に戻る（GETでリダイレクト）
    response.sendRedirect(request.getContextPath() + "/main/test_list?entYear=" + entYear +
                          "&classNum=" + classNum +
                          "&subjectCd=" + subjectCd +
                          "&no=" + no);
  }

  // GETメソッドは使わない（空のまま）
  @Override
  protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // 不使用
  }
}
