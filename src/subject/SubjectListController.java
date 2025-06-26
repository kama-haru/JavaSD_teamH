package subject;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/subject/list"})
public class SubjectListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        String schoolCd = (String) session.getAttribute("schoolCd");

        SubjectDao dao = new SubjectDao();
        List<Subject> subjects = dao.getSubjectsBySchoolCd(schoolCd);

        req.setAttribute("subjects", subjects);
        req.getRequestDispatcher("/subject/subject_list.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        get(req, resp);
    }
}
