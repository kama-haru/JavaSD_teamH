package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Test;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_list_student")
public class TestListStudentController extends CommonServlet {

  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      String studentNo = request.getParameter("studentNo");

      if (studentNo == null || studentNo.trim().isEmpty()) {
        request.setAttribute("errorMessage", "学生番号を入力してください。");
      } else {
        TestDao dao = new TestDao();
        List<Test> list = dao.selectStudentScores(studentNo, schoolCd);
        request.setAttribute("studentResults", list);

        if (!list.isEmpty()) {
          request.setAttribute("studentName", list.get(0).getStudentName());
          request.setAttribute("studentNo", studentNo);

          boolean hasPoint = false;
          for (Test t : list) {
            if (t.getPoint() != null && t.getPoint() > 0) {
              hasPoint = true;
              break;
            }
          }

          if (!hasPoint) {
            request.setAttribute("errorMessage", "成績情報が存在しませんでした。");
          }

        } else {
          request.setAttribute("errorMessage", "学生が存在しませんでした。");
        }
      }

      request.setAttribute("mode", "student");
      request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

@Override
protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// TODO 自動生成されたメソッド・スタブ
	
}
}
