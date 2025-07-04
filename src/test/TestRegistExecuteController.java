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

  // 成績登録（登録処理）
  @Override
  protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("UTF-8");

    try {
      // 回数を取得（nullまたは空の場合はエラー）
      String noStr = request.getParameter("no");
      if (noStr == null || noStr.isEmpty()) {
        request.setAttribute("errorMessage", "回数を選択してください。");
        forwardWithList(request, response, null);  // エラー時の再表示
        return;
      }

      // パラメータ取得（入学年度・クラス・科目・回数）
      int entYear = Integer.parseInt(request.getParameter("entYear"));
      String classNum = request.getParameter("classNum");
      String subjectCd = request.getParameter("subjectCd");
      int no = Integer.parseInt(noStr);

      // 学校コードをセッションから取得
      HttpSession session = request.getSession();
      String schoolCd = (String) session.getAttribute("schoolCd");

      TestDao dao = new TestDao();

      // 対象の学生リストを取得（成績登録用）
      List<Test> list = dao.getAllStudentsForGradeEntry(entYear, classNum, schoolCd);

      // 削除対象の学生番号を取得（チェックされたもの）
      String[] deleteIds = request.getParameterValues("delete");

      // 学生ごとのエラーメッセージを入れるマップ
      Map<String, String> errorMap = new HashMap<>();

      // 各学生について処理
      for (Test test : list) {
        String studentNo = test.getStudentNo().trim();
        boolean shouldDelete = false;

        // 削除対象かチェック
        if (deleteIds != null) {
          for (String del : deleteIds) {
            if (del.equals(studentNo)) {
              shouldDelete = true;
              break;
            }
          }
        }

        // 削除対象なら削除処理を行い次へ
        if (shouldDelete) {
          dao.deleteWithPoint(classNum, subjectCd, no, studentNo, schoolCd);
          continue;
        }

        // 点数入力の取得
        String param = request.getParameter("point_" + studentNo);

        // 未入力の場合
        if (param == null || param.trim().isEmpty()) {
          errorMap.put(studentNo, "点数を入力してください。");
          continue;
        }

        // 数字チェック
        if (param.matches("\\d+")) {
          int point = Integer.parseInt(param);

          // 範囲チェック
          if (point < 0 || point > 100) {
            errorMap.put(studentNo, "0～100の範囲で入力してください。");
            continue;
          }

          // データを登録または更新
          dao.saveOrUpdateWithSchoolCd(schoolCd, classNum, subjectCd, no, studentNo, point);

        } else {
          // 数字でない場合
          errorMap.put(studentNo, "点数が正しく入力されていません。");
        }
      }

      // エラーがある場合は元の画面に戻して表示
      if (!errorMap.isEmpty()) {
        request.setAttribute("errorMessage", "入力に誤りがあります。");
        forwardWithList(request, response, errorMap);
        return;
      }

      // エラーがなければ完了画面へ
      request.getRequestDispatcher("/test/GRMU002.jsp").forward(request, response);

    } catch (NumberFormatException e) {
      // 回数などの数値変換でエラーが出た場合
      request.setAttribute("errorMessage", "数値の形式が正しくありません。");
      forwardWithList(request, response, null);
    } catch (Exception e) {
      // その他の例外
      throw new IOException(e);
    }
  }

  // 入力エラーなどがあったときに元の画面に戻るメソッド
  private void forwardWithList(HttpServletRequest request, HttpServletResponse response, Map<String, String> errorMap) throws Exception {
    String entYear = request.getParameter("entYear");
    String classNum = request.getParameter("classNum");
    String subjectCd = request.getParameter("subjectCd");
    String noStr = request.getParameter("no");

    HttpSession session = request.getSession();
    String schoolCd = (String) session.getAttribute("schoolCd");

    TestDao dao = new TestDao();

    // 学生リストを再取得
    List<Test> list = dao.getAllStudentsForGradeEntry(Integer.parseInt(entYear), classNum, schoolCd);

    // 入力値と選択肢を再設定
    request.setAttribute("entYear", entYear);
    request.setAttribute("classNum", classNum);
    request.setAttribute("subjectCd", subjectCd);
    request.setAttribute("no", noStr);
    request.setAttribute("list", list);
    request.setAttribute("entYearList", dao.getEntYearList());
    request.setAttribute("classNumList", dao.getClassNumList());
    request.setAttribute("subjectList", dao.getSubjectList());

    // 学生ごとのエラーがある場合は渡す
    if (errorMap != null) {
      request.setAttribute("errorMap", errorMap);
    }

    // 再表示
    request.getRequestDispatcher("/test/GRMR001.jsp").forward(request, response);
  }

  // GETリクエストでもPOSTと同じ処理をする
  @Override
  protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    post(req, resp);
  }
}
