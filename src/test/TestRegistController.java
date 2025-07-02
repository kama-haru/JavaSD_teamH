package test;

import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_regist")
public class TestRegistController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String schoolCd = (String) session.getAttribute("schoolCd");

		// DAOインスタンス
		TestDao testDao = new TestDao();
		ClassNumDao classDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

		// プルダウン用データ設定
		request.setAttribute("entYearList", testDao.getEntYearList(schoolCd));
		request.setAttribute("classNumList", classDao.getClassNumList(schoolCd));
		request.setAttribute("subjectList", subjectDao.getAllSubjects(schoolCd));

		// パラメータ取得
		String entYear = request.getParameter("entYear");
		String classNum = request.getParameter("classNum");
		String subjectCd = request.getParameter("subjectCd");
		String noStr = request.getParameter("no");

		// 検索条件保持
		request.setAttribute("entYear", entYear);
		request.setAttribute("classNum", classNum);
		request.setAttribute("subjectCd", subjectCd);
		request.setAttribute("no", noStr);

		// 検索条件がすべて揃っている場合のみ検索処理を行う
		if (entYear != null && classNum != null && subjectCd != null && noStr != null && !entYear.isEmpty()
				&& !classNum.isEmpty() && !subjectCd.isEmpty() && !noStr.isEmpty()) {

			int no = Integer.parseInt(noStr);
			int year = Integer.parseInt(entYear);

			List<Test> list = testDao.selectByEntYearClassSubject(year, classNum, subjectCd);
			request.setAttribute("list", list);

		} else if (entYear != null || classNum != null || subjectCd != null || noStr != null) {
			// 何かが入力されているが不完全な場合
			request.setAttribute("error", Arrays.asList("すべての検索条件を選択してください。"));
		}

		request.getRequestDispatcher("/test/test_regist.jsp").forward(request, response);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// POST処理（未実装）
	}
}