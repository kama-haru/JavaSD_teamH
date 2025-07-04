package subject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.CommonServlet;
@WebServlet("/subject/SubjectDeleteController")
public class SubjectDeleteController extends CommonServlet {

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cd = (String) request.getAttribute("cd");

        SubjectDao dao = new SubjectDao();
        boolean success = dao.delete(cd);

        if (success) {
            response.sendRedirect("SBJM007.jsp");
        } else {
            request.setAttribute("error", "削除に失敗しました。");
            request.getRequestDispatcher("SBJM006.jsp").forward(request, response);
        }
    }
    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GETメソッドは使わないので、405エラーを返すのがおすすめ
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GETメソッドは使用できません。");
    }

}
