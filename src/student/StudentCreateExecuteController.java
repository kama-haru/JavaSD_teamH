package student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;

@WebServlet(urlPatterns = { "/student/create-execute" })
public class StudentCreateExecuteController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String schoolCd = "S01";

		String no = request.getParameter("no");
		String name = request.getParameter("name");

		int entYear = 0;
		String entYearParam = request.getParameter("entYear");
		if (entYearParam != null && !entYearParam.isEmpty()) {
			try {
				entYear = Integer.parseInt(entYearParam);
			} catch (NumberFormatException e) {

			}
		}

		String classNum = request.getParameter("classNum");

		StudentDao dao = new StudentDao();

		boolean hasError = false;
		String noError = null;
		String entYearError = null;

		if (entYear == 0) {
			entYearError = "入学年度を選択してください。";
			hasError = true;
		}

		try {
			Student existing = dao.findByNo(no);
			if (existing != null) {
				noError = "学生番号が重複しています";
				hasError = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "データベースエラーが発生しました。");
			hasError = true;
		}

		if (hasError) {

			request.setAttribute("noError", noError);
			request.setAttribute("entYearError", entYearError);

			request.setAttribute("no", no);
			request.setAttribute("name", name);
			request.setAttribute("entYear", entYear);
			request.setAttribute("classNum", classNum);

			request.getRequestDispatcher("/student/create").forward(request, response);
			return;
		}

		try {
			Student student = new Student();
			student.setNo(no);
			student.setName(name);
			student.setEntYear(entYear);
			student.setClassNum(classNum);
			student.setAttend(true);
			student.setSchoolCd(schoolCd);

			dao.save(student);

			request.getRequestDispatcher("/student/student_create_done.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("errorMessage", "予期せぬエラーが発生しました。詳細はログを確認してください。");

			request.setAttribute("no", no);
			request.setAttribute("name", name);
			request.setAttribute("entYear", entYear);
			request.setAttribute("classNum", classNum);

			request.getRequestDispatcher("/student/create").forward(request, response);
		}
	}
}
