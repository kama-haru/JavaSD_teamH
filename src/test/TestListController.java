package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_list")
public class TestListController extends CommonServlet {
  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      TestDao testDao = new TestDao();
      ClassNumDao classDao = new ClassNumDao();
      SubjectDao subjectDao = new SubjectDao();

      request.setAttribute("entYearList", testDao.getEntYearList(schoolCd));
      request.setAttribute("classNumList", classDao.getClassNumList(schoolCd));
      request.setAttribute("subjectList", subjectDao.getAllSubjects(schoolCd));

      String mode = request.getParameter("mode");
      request.setAttribute("mode", mode);

      if ("subject".equals(mode)) {
        request.getRequestDispatcher("/test/test_list_subject").forward(request, response);
      } else if ("student".equals(mode)) {
        request.getRequestDispatcher("/test/test_list_student").forward(request, response);
      } else {
        request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);
      }

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

@Override
protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// TODO 自動生成されたメソッド・スタブ

}
}
