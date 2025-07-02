package test;

import java.util.List;

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
  protected void post(HttpServletRequest req, HttpServletResponse res) throws Exception {
    req.setCharacterEncoding("UTF-8");
    String studentNo = req.getParameter("studentNo");

    // ★ Add (if needed): Get schoolCd for DAO that filters per school
    HttpSession session = req.getSession();
    String schoolCd = (String) session.getAttribute("schoolCd");

    if (studentNo == null || studentNo.trim().isEmpty()) {
      req.setAttribute("errorStudent", "学生番号を入力してください。");
    } else {
      TestDao dao = new TestDao();
      List<Test> list = dao.selectStudentScores(studentNo); // ← No schoolCd here, OK

      if (list.isEmpty()) {
        req.setAttribute("message", "該当する成績が見つかりませんでした。");
      } else {
        req.setAttribute("listStudent", list);
        req.setAttribute("studentName", list.get(0).getStudentName());
      }
    }

    req.setAttribute("studentNo", studentNo);
    req.getRequestDispatcher("/test/test_list.jsp").forward(req, res);
  }

  @Override
  protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // not used
  }
}

