package accounts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.CommonServlet;

@WebServlet(urlPatterns = { "/logout" })
public class LogoutController extends CommonServlet {

	// GETメソッドによりログアウト処理を実行
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 既存のセッションを取得（存在しない場合はnull）
		HttpSession session = req.getSession(false);

		// セッションが存在していれば、無効化（ログアウト処理）
		if (session != null) {
			session.invalidate(); // セッションを完全に破棄
		}

		// ログアウト後の画面（logout.jsp）にフォワード
		req.getRequestDispatcher("/accounts/logout.jsp").forward(req, resp);
	}

	// POSTメソッドは使用しないため、空実装
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	}

}
