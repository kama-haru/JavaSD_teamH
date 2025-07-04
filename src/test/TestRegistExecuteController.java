package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Test;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_regist_execute")
public class TestRegistExecuteController extends CommonServlet {

  @Override
  protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("UTF-8");

    try {
      String noStr = request.getParameter("no");
      if (noStr == null || noStr.isEmpty()) {
        request.setAttribute("errorMessage", "回数を選択してください。");
        forwardWithList(request, response, null);
        return;
      }

      int entYear = Integer.parseInt(request.getParameter("entYear"));
      String classNum = request.getParameter("classNum");
      String subjectCd = request.getParameter("subjectCd");
      int no = Integer.parseInt(noStr);

      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      TestDao dao = new TestDao();
      List<Test> list = dao.getAllStudentsForGradeEntry(entYear, classNum, schoolCd);

      String[] deleteIds = request.getParameterValues("delete");

      Map<String, String> errorMap = new HashMap<>();

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
          dao.deleteWithPoint(classNum, subjectCd, no, studentNo, schoolCd);
          continue;
        }

        String param = request.getParameter("point_" + studentNo);
        if (param == null || param.trim().isEmpty()) {
          errorMap.put(studentNo, "点数を入力してください。");
          continue;
        }

        if (param.matches("\\d+")) {
          int point = Integer.parseInt(param);
          if (point < 0 || point > 100) {
            errorMap.put(studentNo, "0～100の範囲で入力してください。");
            continue;
          }
          dao.saveOrUpdateWithSchoolCd(schoolCd, classNum, subjectCd, no, studentNo, point);
        } else {
          errorMap.put(studentNo, "点数が正しく入力されていません。");
        }
      }

      if (!errorMap.isEmpty()) {
        request.setAttribute("errorMessage", "入力に誤りがあります。");
        forwardWithList(request, response, errorMap);
        return;
      }

      request.getRequestDispatcher("/test/GRMU002.jsp").forward(request, response);

    } catch (NumberFormatException e) {
      request.setAttribute("errorMessage", "数値の形式が正しくありません。");
      forwardWithList(request, response, null);
    } catch (Exception e) {
      throw new IOException(e);
    }
  }

  private void forwardWithList(HttpServletRequest request, HttpServletResponse response, Map<String, String> errorMap) throws Exception {
    String entYear = request.getParameter("entYear");
    String classNum = request.getParameter("classNum");
    String subjectCd = request.getParameter("subjectCd");
    String noStr = request.getParameter("no");

    HttpSession session = request.getSession();
    String schoolCd = (String) session.getAttribute("schoolCd");

    TestDao dao = new TestDao();
    List<Test> list = dao.getAllStudentsForGradeEntry(Integer.parseInt(entYear), classNum, schoolCd);

    request.setAttribute("entYear", entYear);
    request.setAttribute("classNum", classNum);
    request.setAttribute("subjectCd", subjectCd);
    request.setAttribute("no", noStr);
    request.setAttribute("list", list);
    request.setAttribute("entYearList", dao.getEntYearList());
    request.setAttribute("classNumList", dao.getClassNumList());
    request.setAttribute("subjectList", dao.getSubjectList());

    if (errorMap != null) {
      request.setAttribute("errorMap", errorMap);
    }

    request.getRequestDispatcher("/test/GRMR001.jsp").forward(request, response);
  }

  @Override
  protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    post(req, resp);
  }
}