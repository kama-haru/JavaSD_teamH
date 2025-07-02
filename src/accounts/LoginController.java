package accounts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


	}
}