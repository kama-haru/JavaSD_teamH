package subject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet("/SubjectCreateController")
public class SubjectCreateController extends CommonServlet {

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String cd = (String) request.getAttribute("cd");
        String name = (String) request.getAttribute("name");
        String schoolCd = (String) request.getSession().getAttribute("schoolCd");

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchoolCd(schoolCd);

        SubjectDao dao = new SubjectDao();
        dao.insert(subject);

        request.getRequestDispatcher("/subject/subject_create_done.jsp").forward(request, response);
    }

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
