package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Test;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_list_student")
public class TestListStudentController extends CommonServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      String studentNo = req.getParameter("studentNo");

      if (studentNo == null || studentNo.trim().isEmpty()) {
        req.setAttribute("errorStudent", "学生番号を入力してください。");
        req.getRequestDispatcher("/test/test_list.jsp").forward(req, res);
        return;
      }

      TestDao dao = new TestDao();
      List<Test> list = dao.selectStudentScores(studentNo);

      if (list.isEmpty()) {
        req.setAttribute("message", "該当する成績が見つかりませんでした。");
      } else {
        req.setAttribute("listStudent", list);
        req.setAttribute("studentName", list.get(0).getStudentName());  // ✅ Add student's name
      }

      req.setAttribute("studentNo", studentNo);  // retain input in the form
      req.getRequestDispatcher("/test/test_list.jsp").forward(req, res);

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

@Override
protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	// TODO 自動生成されたメソッド・スタブ

}
}
