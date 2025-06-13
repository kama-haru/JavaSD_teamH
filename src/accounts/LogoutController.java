package accounts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.CommonServlet;
@WebServlet(urlPatterns = { "/logout" })
public class LogoutController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        req.getRequestDispatcher("/accounts/logout.jsp").forward(req, resp);
    }


	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {


	}

}
