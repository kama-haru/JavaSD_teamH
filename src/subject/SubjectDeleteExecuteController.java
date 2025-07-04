package subject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;
@WebServlet("/subject/SubjectDeleteExecuteController")
public class SubjectDeleteExecuteController extends CommonServlet {

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cd = request.getParameter("cd");

        if (cd == null || cd.trim().isEmpty()) {
            request.setAttribute("error", "科目コードが指定されていません。");
            request.getRequestDispatcher("SBJM006.jsp").forward(request, response);
            return;
        }

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.findByCode(cd);

        if (subject == null) {
            request.setAttribute("error", "指定された科目は存在しません。");
            request.getRequestDispatcher("SBJM006.jsp").forward(request, response);
        } else {
            request.setAttribute("cd", cd);
            request.getRequestDispatcher("SubjectDeleteController").forward(request, response);
        }
    }

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GETメソッドは使用できません。");
    }
}
