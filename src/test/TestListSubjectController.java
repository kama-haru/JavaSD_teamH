package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Test;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_list_student")
public class TestListSubjectController extends CommonServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      req.setCharacterEncoding("UTF-8");
      String studentNo = req.getParameter("studentNo");

      if (studentNo == null || studentNo.trim().isEmpty()) {
        req.setAttribute("errorStudent", "学生番号を入力してください。");
      } else {
        TestDao dao = new TestDao();
        List<Test> list = dao.selectStudentScores(studentNo);
        if (list.isEmpty()) {
          req.setAttribute("message", "学生が存在しませんでした");
        } else {
          req.setAttribute("listStudent", list);
          req.setAttribute("studentName", list.get(0).getStudentName());
          req.setAttribute("studentNo", studentNo);
        }
      }

      // 科目情報に関するリストはセットしない（ここが重要）
      // ただし、初期状態に戻すためには空リストを送るのが無難
      req.setAttribute("entYearList", new ArrayList<>());
      req.setAttribute("classNumList", new ArrayList<>());
      req.setAttribute("subjectList", new ArrayList<>());

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
