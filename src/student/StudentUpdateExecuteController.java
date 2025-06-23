package student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

// リダイレクト先のURLと合わせる
@WebServlet(urlPatterns={"/student/update-execute"})
public class StudentUpdateExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 完了画面のJSPにフォワードする
		req.getRequestDispatcher("/student/student_update_done.jsp").forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// POSTで直接アクセスされた場合もGETと同じ処理を行う
		get(req, resp);
	}

}