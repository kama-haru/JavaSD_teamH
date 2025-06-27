package test;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_list_subject")
public class TestListSubjectController extends CommonServlet {
  @Override
  protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      request.setCharacterEncoding("UTF-8");

      String entYearStr = request.getParameter("entYear");
      String classNum = request.getParameter("classNum");
      String subjectCd = request.getParameter("subjectCd");

      if (entYearStr == null || entYearStr.isEmpty() ||
          classNum == null || classNum.isEmpty() ||
          subjectCd == null || subjectCd.isEmpty()) {
        request.setAttribute("error", "入学年度とクラスと科目を選択してください。");
      } else {
        int entYear = Integer.parseInt(entYearStr);
        TestDao testDao = new TestDao();
        List<Test> listSubject = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd);
        request.setAttribute("listSubject", listSubject);

        if (listSubject == null || listSubject.isEmpty()) {
          request.setAttribute("message", "学生情報が存在しませんでした。");
        }

        SubjectDao subjectDao = new SubjectDao();
        for (Subject s : subjectDao.getAllSubjects()) {
          if (s.getCd().equals(subjectCd)) {
            request.setAttribute("subjectName", s.getName());
            break;
          }
        }
      }

      TestDao testDao = new TestDao();
      request.setAttribute("entYearList", testDao.getEntYearList());
      request.setAttribute("classNumList", new ClassNumDao().getClassNumList());
      request.setAttribute("subjectList", new SubjectDao().getAllSubjects());

      request.setAttribute("entYear", entYearStr);
      request.setAttribute("classNum", classNum);
      request.setAttribute("subjectCd", subjectCd);

      request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

@Override
protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// TODO 自動生成されたメソッド・スタブ

}
}