package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_list")
public class TestListController extends CommonServlet {

  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      // ★ Add: schoolCd from session
      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      TestDao testDao = new TestDao();
      ClassNumDao classDao = new ClassNumDao();
      SubjectDao subjectDao = new SubjectDao();

      List<Integer> entYearList = testDao.getEntYearList();
      List<String> classNumList = classDao.getClassNumList();
      List<Subject> subjectList = subjectDao.getAllSubjects();

      request.setAttribute("entYearList", entYearList);
      request.setAttribute("classNumList", classNumList);
      request.setAttribute("subjectList", subjectList);

      request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    get(req, resp);
  }
}

