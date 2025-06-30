package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/list")
public class TestListController extends CommonServlet {

  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      // 各リスト取得
      TestDao testDao = new TestDao();
      ClassNumDao classDao = new ClassNumDao();
      SubjectDao subjectDao = new SubjectDao();

      List<Integer> entYearList = testDao.getEntYearList();
      List<String> classNumList = classDao.getClassNumList();
      List<Subject> subjectList = subjectDao.getAllSubjects();

      // JSPへ渡す
      request.setAttribute("entYearList", entYearList);
      request.setAttribute("classNumList", classNumList);
      request.setAttribute("subjectList", subjectList);

      request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  // POSTリクエストが来てもGETとして処理する（必要なければ削除可能）
  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    get(req, resp);
  }
}
