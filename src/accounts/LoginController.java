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

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();

        String id = req.getParameter("id");
        String password = req.getParameter("password");


        TeacherDao dao = new TeacherDao();

        try {
            // Attempt login process
            Teacher teacher = dao.login(id, password);

            if (teacher != null) {
                session.setAttribute("user", teacher);
                resp.sendRedirect(req.getContextPath() + "/main/index.jsp");
            } else {
                req.setAttribute("error", "IDまたはパスワードが正しくありません");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            // Log the error for server-related issues (like DB connection errors)
            e.printStackTrace();

            // Set a server error flag in the session to show the error page
            session.setAttribute("serverError", true);

            // Redirect to the error page if the server connection fails
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}