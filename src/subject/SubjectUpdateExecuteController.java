package subject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet("/subject/SubjectUpdateExecuteController")
public class SubjectUpdateExecuteController extends CommonServlet {

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.getSubject(cd);

        if (subject == null) {
            req.setAttribute("errorCd", "科目コードが存在しません。");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);
            return;
        }

        // フォワード先へセット
        req.setAttribute("cd", cd);
        req.setAttribute("name", name);
        req.getRequestDispatcher("/subject/SubjectUpdateController").forward(req, resp);
    }

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
