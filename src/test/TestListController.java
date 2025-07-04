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

  // 成績参照画面の GET 処理
  @Override
  protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      // セッションから学校コードを取得
      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      // DAO を準備
      TestDao testDao = new TestDao();
      ClassNumDao classDao = new ClassNumDao();
      SubjectDao subjectDao = new SubjectDao();

      // 入学年度・クラス・科目の選択肢を取得し、画面に渡す
      request.setAttribute("entYearList", testDao.getEntYearList(schoolCd));
      request.setAttribute("classNumList", classDao.getClassNumList(schoolCd));
      request.setAttribute("subjectList", subjectDao.getAllSubjects(schoolCd));

      // モード（subject または student）を取得
      String mode = request.getParameter("mode");
      request.setAttribute("mode", mode);

      // 科目情報による検索処理
      if ("subject".equals(mode)) {
        String entYear = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");

        // 入力チェック（どれか1つでも未入力ならエラー）
        if (entYear == null || entYear.isEmpty() ||
            classNum == null || classNum.isEmpty() ||
            subjectCd == null || subjectCd.isEmpty()) {
          request.setAttribute("errorSubject", "入学年度とクラスと科目を選択してください");
          request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);
          return;
        }

        // 成績データの取得
        List<Test> list = testDao.selectByEntYearClassSubject(entYear, classNum, subjectCd, schoolCd);

        // データがなければエラーメッセージを表示
        if (list.isEmpty()) {
          request.setAttribute("errorMessage", "成績情報が存在しませんでした");
        } else {
          request.setAttribute("resultList", list);
        }

        // 結果画面へ
        request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);

      } else if ("student".equals(mode)) {
        // 学生番号による検索処理
        String studentNo = request.getParameter("studentNo");

        // 入力チェック
        if (studentNo == null || studentNo.trim().isEmpty()) {
          request.setAttribute("errorStudent", "学生番号を入力してください");
          request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);
          return;
        }

        // 学生の成績を取得
        List<Test> list = testDao.selectStudentScores(studentNo, schoolCd);

        // 成績がなければエラーメッセージ、あれば表示用の属性を設定
        if (list.isEmpty()) {
          request.setAttribute("errorMessage", "成績情報が存在しませんでした");
        } else {
          request.setAttribute("studentResults", list);
          request.setAttribute("studentNo", studentNo);
          request.setAttribute("studentName", list.get(0).getStudentName()); // 最初のデータから氏名を取得
        }

        // 結果画面へ
        request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);

      } else {
        // mode が無い場合の初期表示（メッセージのみ）
        request.getRequestDispatcher("/test/GRMU001.jsp").forward(request, response);
      }

    } catch (Exception e) {
      // エラーが発生した場合はサーブレット例外としてスロー
      throw new ServletException(e);
    }
  }

  // POST メソッドは使わない
  @Override
  protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // POST unused
  }
}
