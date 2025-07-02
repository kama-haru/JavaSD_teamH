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

@WebServlet("/test/test_list_subject")
public class TestListSubjectController extends CommonServlet {

  @Override
  protected void post(HttpServletRequest req, HttpServletResponse res) throws Exception {
    req.setCharacterEncoding("UTF-8");

    String entYear = req.getParameter("entYear");
    String classNum = req.getParameter("classNum");
    String subjectCd = req.getParameter("subjectCd");

    // ★ Get schoolCd from session
    HttpSession session = req.getSession();
    String schoolCd = (String) session.getAttribute("schoolCd");

    TestDao testDao = new TestDao();

    if (entYear == null || entYear.isEmpty() ||
        classNum == null || classNum.isEmpty() ||
        subjectCd == null || subjectCd.isEmpty()) {

      req.setAttribute("errorSubject", "入学年度、クラス、科目をすべて選択してください。");

    } else {
      List<Test> list = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, schoolCd);

      if (list.isEmpty()) {
        req.setAttribute("message", "成績データが見つかりませんでした。");
      } else {
        req.setAttribute("listSubject", list);
        req.setAttribute("subjectName", list.get(0).getSubjectName());
      }
    }

    ClassNumDao classDao = new ClassNumDao();
    SubjectDao subjectDao = new SubjectDao();

    req.setAttribute("entYearList", testDao.getEntYearList());
    req.setAttribute("classNumList", classDao.getClassNumList());
    req.setAttribute("subjectList", subjectDao.getAllSubjects());

    req.setAttribute("entYear", entYear);
    req.setAttribute("classNum", classNum);
    req.setAttribute("subjectCd", subjectCd);

    req.getRequestDispatcher("/test/test_list.jsp").forward(req, res);
  }

  @Override
  protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // not used
  }
}

