package student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import dao.StudentDao;

// URLパターン /student/create_execute にマッピング
@WebServlet(urlPatterns = { "/student/create_execute" })
public class StudentCreateExecuteController extends HttpServlet {

    // POSTリクエストのみを処理
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストの文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String schoolCd = (String) session.getAttribute("schoolCd");

		// フォームから送信されたパラメータを取得
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String classNum = request.getParameter("classNum");

		int entYear = 0;
		String entYearParam = request.getParameter("entYear");
		// 入学年度が選択されていれば、数値に変換
		if (entYearParam != null && !entYearParam.isEmpty()) {
			try {
				entYear = Integer.parseInt(entYearParam);
			} catch (NumberFormatException e) {
				// 数値変換に失敗しても処理を続行（後でエラーとして扱う）
			}
		}

		StudentDao dao = new StudentDao();
		boolean hasError = false;
		String noError = null;
		String entYearError = null;

		// バリデーション：入学年度が選択されているかチェック
		if (entYear == 0) {
			entYearError = "入学年度を選択してください。";
			hasError = true;
		}

		try {
			Student existing = dao.findBySchoolCdAndNo(schoolCd, no);
			if (existing != null) {
				noError = "学生番号が重複しています。";
				hasError = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "データベースエラーが発生しました。");
			hasError = true;
		}

		// エラーが1つでもあった場合
		if (hasError) {
			// エラーメッセージと入力値をリクエストスコープにセット
			request.setAttribute("noError", noError);
			request.setAttribute("entYearError", entYearError);
			request.setAttribute("no", no);
			request.setAttribute("name", name);
			request.setAttribute("entYear", entYear);
			request.setAttribute("classNum", classNum);

			// 再度、登録画面(/student/create)にフォワードして、エラーメッセージと共に再表示
			request.getRequestDispatcher("/student/create").forward(request, response);
			return; // ここで処理を終了
		}

		// エラーがなかった場合、登録処理を実行
		try {
			Student student = new Student();
			student.setNo(no);
			student.setName(name);
			student.setEntYear(entYear);
			student.setClassNum(classNum);
			student.setAttend(true); // 新規登録時は「在学中」として登録
			student.setSchoolCd(schoolCd);

			dao.save(student);

			// 登録完了画面にフォワード
			request.getRequestDispatcher("/student/STDM003.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "予期せぬエラーが発生しました。詳細はログを確認してください。");
			// エラー発生時も入力値を保持して登録画面に戻す
			request.setAttribute("no", no);
			request.setAttribute("name", name);
			request.setAttribute("entYear", entYear);
			request.setAttribute("classNum", classNum);
			request.getRequestDispatcher("/student/create").forward(request, response);
		}
	}
}