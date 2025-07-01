package accounts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/accounts/login" })
public class LoginController extends CommonServlet {
	// GETリクエストでログイン画面を表示
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// login.jsp にフォワード
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// セッションを取得
		HttpSession session = req.getSession();

		// リクエストパラメータからIDとパスワードを取得
		String id = req.getParameter("id");
		String password = req.getParameter("password");

		// 教員DAOのインスタンスを生成
		TeacherDao dao = new TeacherDao();

		try {
			// DAOを使ってログイン認証を実行
			Teacher teacher = dao.login(id, password);

			// ログイン成功時
			if (teacher != null) {
				session.setAttribute("user", teacher);
				session.setAttribute("schoolCd", teacher.getSchoolCd()); // ←
																			// 重要！

				// メインページへリダイレクト
				resp.sendRedirect(req.getContextPath() + "/main/index.jsp");
			} else {
				// ログイン失敗時、エラーメッセージを設定し、再びログイン画面へ
				req.setAttribute("error", "IDまたはパスワードが正しくありません");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}

		} catch (Exception e) {
			// 例外発生時（データベース接続エラーなど）、ログに出力
			e.printStackTrace();

			// セッションにサーバーエラーのフラグを設定
			session.setAttribute("serverError", true);

			// エラーページにリダイレクト
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}
}