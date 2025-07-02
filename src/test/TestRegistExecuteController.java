package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Test;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet("/test/test_regist_execute")
public class TestRegistExecuteController extends CommonServlet {
	@Override
	protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		try {
			String noStr = request.getParameter("no");
			if (noStr == null || noStr.isEmpty()) {
				request.setAttribute("errorMessage", "回数を選択してください。");
				forwardWithList(request, response);
				return;
			}

			int entYear = Integer.parseInt(request.getParameter("entYear"));
			String classNum = request.getParameter("classNum");
			String subjectCd = request.getParameter("subjectCd");
			int no = Integer.parseInt(noStr);

			HttpSession session = request.getSession();
			String schoolCd = (String) session.getAttribute("schoolCd");

			TestDao dao = new TestDao();
			List<Test> list = dao.getAllStudentsForGradeEntry(entYear, classNum, schoolCd);

			String[] deleteIds = request.getParameterValues("delete");

			for (Test test : list) {
				String studentNo = test.getStudentNo().trim();
				boolean shouldDelete = false;

				if (deleteIds != null) {
					for (String del : deleteIds) {
						if (del.equals(studentNo)) {
							shouldDelete = true;
							break;
						}
					}
				}

				if (shouldDelete) {
					// 修正：schoolCd を考慮した削除
					dao.deleteWithPoint(classNum, subjectCd, no, studentNo, schoolCd);
					continue;
				}

				String param = request.getParameter("point_" + studentNo);
				if (param == null || param.trim().isEmpty()) {
					request.setAttribute("errorMessage", "点数を入力してください。");
					forwardWithList(request, response);
					return;
				}

				if (param.matches("\\d+")) {
					int point = Integer.parseInt(param);
					if (point > 100) {
						request.setAttribute("errorMessage", "点数は100以下で入力してください。");
						forwardWithList(request, response);
						return;
					}
					// 修正：schoolCd を含む保存
					dao.saveOrUpdateWithSchoolCd(schoolCd, classNum, subjectCd, no, studentNo, point);
				} else {
					request.setAttribute("errorMessage", "点数が正しく入力されていない学生がいます。");
					forwardWithList(request, response);
					return;
				}
			}

			request.getRequestDispatcher("/test/test_regist_done.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "数値の形式が正しくありません。");
			forwardWithList(request, response);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private void forwardWithList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String entYear = request.getParameter("entYear");
		String classNum = request.getParameter("classNum");
		String subjectCd = request.getParameter("subjectCd");
		String noStr = request.getParameter("no");

		HttpSession session = request.getSession();
		String schoolCd = (String) session.getAttribute("schoolCd");

		TestDao dao = new TestDao();
		List<Test> list = dao.getAllStudentsForGradeEntry(Integer.parseInt(entYear), classNum, schoolCd);

		request.setAttribute("entYear", entYear);
		request.setAttribute("classNum", classNum);
		request.setAttribute("subjectCd", subjectCd);
		request.setAttribute("no", noStr);
		request.setAttribute("list", list);

		request.setAttribute("entYearList", dao.getEntYearList());
		request.setAttribute("classNumList", dao.getClassNumList());
		request.setAttribute("subjectList", dao.getSubjectList());

		request.getRequestDispatcher("/test/test_regist.jsp").forward(request, response);
	}

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		post(req, resp);
	}
}