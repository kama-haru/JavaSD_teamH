package subject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet("/SubjectCreateExecuteController")
public class SubjectCreateExecuteController extends CommonServlet {

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        String schoolCd = (String) req.getSession().getAttribute("schoolCd");

        boolean hasError = false;

        // 空白チェック
        if (cd == null || cd.trim().isEmpty()) {
            req.setAttribute("errorCd", "科目コードは必須です。");
            hasError = true;
        } else if (cd.trim().length() != 3) {
            req.setAttribute("errorCd", "科目コードは3文字で入力してください。");
            hasError = true;
        }

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("errorName", "科目名は必須です。");
            hasError = true;
        }

        if (hasError) {
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("/subject/SBJM002.jsp").forward(req, resp);
            return;
        }

        // 重複チェック
        SubjectDao dao = new SubjectDao();
        if (dao.exists(schoolCd, cd.trim())) {
            req.setAttribute("errorCd", "科目コードが重複しています。");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("/subject/SBJM002.jsp").forward(req, resp);
            return;
        }

        // チェックOK → 登録処理へフォワード
        req.setAttribute("cd", cd.trim());
        req.setAttribute("name", name.trim());
        req.getRequestDispatcher("/SubjectCreateController").forward(req, resp);
    }

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
