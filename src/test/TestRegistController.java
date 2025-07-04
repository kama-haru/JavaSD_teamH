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

  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession();
    String schoolCd = (String) session.getAttribute("schoolCd");

    // DAO
    TestDao testDao = new TestDao();
    ClassNumDao classDao = new ClassNumDao();
    SubjectDao subjectDao = new SubjectDao();

    // セレクトボックス用データ設定
    request.setAttribute("entYearList", testDao.getEntYearList(schoolCd));
    request.setAttribute("classNumList", classDao.getClassNumList(schoolCd));
    request.setAttribute("subjectList", subjectDao.getAllSubjects(schoolCd));

    // パラメータ取得
    String entYear = request.getParameter("entYear");
    String classNum = request.getParameter("classNum");
    String subjectCd = request.getParameter("subjectCd");
    String noStr = request.getParameter("no");
    String searched = request.getParameter("searched");

    // 入力保持
    request.setAttribute("entYear", entYear);
    request.setAttribute("classNum", classNum);
    request.setAttribute("subjectCd", subjectCd);
    request.setAttribute("no", noStr);

    // 検索実行
    if ("true".equals(searched)) {
      if (entYear == null || entYear.isEmpty() ||
          classNum == null || classNum.isEmpty() ||
          subjectCd == null || subjectCd.isEmpty() ||
          noStr == null || noStr.isEmpty()) {
        // 入力不備
        request.setAttribute("error", "入学年度とクラスと科目と回数を選択してください");
      } else {
        try {
          int no = Integer.parseInt(noStr);
          List<Test> list = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, no, schoolCd);
          request.setAttribute("list", list);
        } catch (NumberFormatException e) {
          request.setAttribute("error", "回数の形式が正しくありません");
        }
      }
    }

    // JSPへフォワード
    request.getRequestDispatcher("/test/GRMR001.jsp").forward(request, response);
  }

  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // POSTは未使用
  }
}
