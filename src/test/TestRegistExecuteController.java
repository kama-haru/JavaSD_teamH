package test;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Test;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_regist_execute")
public class TestRegistExecuteController extends CommonServlet {
  @Override
  protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("UTF-8");

    int entYear = Integer.parseInt(request.getParameter("entYear"));
    String classNum = request.getParameter("classNum");
    String subjectCd = request.getParameter("subjectCd");
    int no = Integer.parseInt(request.getParameter("no"));

    TestDao dao = new TestDao();
    List<Test> list = dao.selectByEntYearClassSubject(entYear, classNum, subjectCd);

    String[] deleteIds = request.getParameterValues("delete");

    for (Test test : list) {
      String studentNo = test.getStudentNo().trim();

      boolean shouldDelete = false;
      if (deleteIds != null) {
        for (String del : deleteIds) {
          if (del.equals(studentNo)) {
            shouldDelete = true;
            break;
          }
        }
      }

      if (shouldDelete) {
        dao.delete(classNum, subjectCd, no, studentNo);
      } else {
        String param = request.getParameter("point_" + studentNo);
        if (param != null && param.matches("\\d+")) {
          int point = Integer.parseInt(param);
          dao.saveOrUpdate(classNum, subjectCd, no, studentNo, point);
        }
      }
    }

    request.getRequestDispatcher("/test/test_regist_done.jsp").forward(request, response);
  }

  @Override
  protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    post(req, resp);
  }
}