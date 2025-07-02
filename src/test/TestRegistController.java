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

    // DAOインスタンス
    TestDao testDao = new TestDao();
    ClassNumDao classDao = new ClassNumDao();
    SubjectDao subjectDao = new SubjectDao();

    // プルダウンデータ設定
    request.setAttribute("entYearList", testDao.getEntYearList(schoolCd));
    request.setAttribute("classNumList", classDao.getClassNumList(schoolCd));
    request.setAttribute("subjectList", subjectDao.getAllSubjects(schoolCd));

    // パラメータ取得
    String entYear = request.getParameter("entYear");
    String classNum = request.getParameter("classNum");
    String subjectCd = request.getParameter("subjectCd");
    String noStr = request.getParameter("no");

    // 検索条件保持
    request.setAttribute("entYear", entYear);
    request.setAttribute("classNum", classNum);
    request.setAttribute("subjectCd", subjectCd);
    request.setAttribute("no", noStr);

    // 検索実行（全学生を取得）
    if (entYear != null && classNum != null && subjectCd != null && noStr != null &&
        !entYear.isEmpty() && !classNum.isEmpty() && !subjectCd.isEmpty() && !noStr.isEmpty()) {

      int no = Integer.parseInt(noStr);
      List<Test> list = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, no, schoolCd);
      request.setAttribute("list", list);
    }

    // JSPへフォワード
    request.getRequestDispatcher("/test/test_regist.jsp").forward(request, response);
  }

@Override
protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// TODO 自動生成されたメソッド・スタブ
	
}
}
