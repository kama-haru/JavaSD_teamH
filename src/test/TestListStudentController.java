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

  // 学生番号による成績参照（GET処理）
  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      // セッションから学校コードを取得
      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      // パラメータから学生番号を取得
      String studentNo = request.getParameter("studentNo");

      // 学生番号が空の場合はエラーメッセージを設定
      if (studentNo == null || studentNo.trim().isEmpty()) {
        request.setAttribute("errorMessage", "学生番号を入力してください。");
      } else {
        // 成績データを取得
        TestDao dao = new TestDao();
        List<Test> list = dao.selectStudentScores(studentNo, schoolCd);

        // JSP に渡すために成績リストを設定
        request.setAttribute("studentResults", list);

        if (!list.isEmpty()) {
          // 氏名・学生番号をリクエストに保存
          request.setAttribute("studentName", list.get(0).getStudentName());
          request.setAttribute("studentNo", studentNo);

          // 点数がひとつでも存在するかチェック
          boolean hasPoint = false;
          for (Test t : list) {
            if (t.getPoint() != null && t.getPoint() > 0) {
              hasPoint = true;
              break;
            }
          }

          // 点数が1つもない場合は成績なしと表示
          if (!hasPoint) {
            request.setAttribute("errorMessage", "成績情報が存在しませんでした。");
          }

        } else {
          // 学生番号に一致する学生がいなかった場合のエラーメッセージ
          request.setAttribute("errorMessage", "学生が存在しませんでした。");
        }
      }

      // 表示モードを "student" に設定して JSP を表示
      request.setAttribute("mode", "student");
      request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);

    } catch (Exception e) {
      // 例外が発生した場合はサーブレット例外として投げる
      throw new ServletException(e);
    }
  }

  // POSTメソッドは使わない
  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // 自動生成されたメソッド（未使用）
  }
}
