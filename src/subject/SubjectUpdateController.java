package subject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet("/subject/SubjectUpdateController")
public class SubjectUpdateController extends CommonServlet {

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String cd = (String) req.getAttribute("cd");
        String name = (String) req.getAttribute("name");

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);

        SubjectDao dao = new SubjectDao();
        dao.update(subject);

        req.getRequestDispatcher("/subject/subject_update_done.jsp").forward(req, resp);
    }

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
