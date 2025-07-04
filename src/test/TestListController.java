package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Test;
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
        String entYear = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");

        if (entYear == null || entYear.isEmpty() ||
            classNum == null || classNum.isEmpty() ||
            subjectCd == null || subjectCd.isEmpty()) {
          request.setAttribute("errorSubject", "入学年度とクラスと科目を選択してください");
          request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);
          return;
        }

        List<Test> list = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, schoolCd);
        if (list.isEmpty()) {
          request.setAttribute("errorMessage", "成績情報が存在しませんでした");
        } else {
          request.setAttribute("resultList", list);
        }

        request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);

      } else if ("student".equals(mode)) {
        String studentNo = request.getParameter("studentNo");

        if (studentNo == null || studentNo.trim().isEmpty()) {
          request.setAttribute("errorStudent", "学生番号を入力してください");
          request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);
          return;
        }

        List<Test> list = testDao.selectStudentScores(studentNo, schoolCd);
        if (list.isEmpty()) {
          request.setAttribute("errorMessage", "成績情報が存在しませんでした");
        } else {
          request.setAttribute("studentResults", list);
          request.setAttribute("studentNo", studentNo);
          request.setAttribute("studentName", list.get(0).getStudentName()); // optional
        }

        request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);

      } else {
        // 初期表示用メッセージ（modeなし）
        request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);
      }

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // POST unused
  }
}
