package student;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;

@WebServlet(urlPatterns = { "/student/list" })
public class StudentListController extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// /student/list に対応するサーブレット
		HttpSession session = request.getSession();
		String schoolCd = (String) session.getAttribute("schoolCd");

		// DAOクラスのインスタンスを生成
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();
		// 学生リスト、入学年度オプション、クラスオプションの初期化
		List<Student> studentList = null;
		List<Integer> entYearOptions = new ArrayList<>();
		List<ClassNum> classNumOptions = null;

		// リクエストパラメータの取得（絞り込み条件）
		String entYearStr = request.getParameter("entYear");
		String classNum = request.getParameter("classNum");
		boolean isAttend = "true".equals(request.getParameter("isAttend"));
		// 「絞込み」ボタンが押されたかを判定するためのフラグ
		boolean isFilterRequest = request.getParameter("filter") != null;

		// 入学年度が未選択の場合、エラーメッセージを設定（ただし「絞込み」ボタンが押された場合のみ）
		if (isFilterRequest && (entYearStr == null || entYearStr.isEmpty())) {
			request.setAttribute("error_entYear", "入学年度を選択してください。");
		}

		// 入学年度（文字列）を数値に変換
		int entYear = 0;
		if (entYearStr != null && !entYearStr.isEmpty()) {
			try {
				entYear = Integer.parseInt(entYearStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();// 数値変換エラー
			}
		}

		try {
			// 絞り込み条件に基づいて学生リストを取得
			studentList = studentDao.filter(entYear, classNum, isAttend, schoolCd);

			// クラス番号の選択肢リストをDBから取得
			classNumOptions = classNumDao.findAll(schoolCd);
			// 過去10年間の入学年度オプションを生成
			int currentYear = LocalDate.now().getYear();
			for (int i = 0; i < 10; i++) {
				entYearOptions.add(currentYear - i);
			}
			// JSPで使用するため、各データをリクエストスコープにセット
			request.setAttribute("studentList", studentList);
			request.setAttribute("entYearOptions", entYearOptions);
			request.setAttribute("classNumOptions", classNumOptions);

			// フォームに入力した値を保持するためにセット
			request.setAttribute("entYearValue", entYearStr);
			request.setAttribute("classNumValue", classNum);
			request.setAttribute("isAttendValue", isAttend);
		} catch (Exception e) {
			e.printStackTrace();
			// データ取得エラーが発生した場合のエラーメッセージ
			request.setAttribute("error", "データの取得中にエラーが発生しました。");
		}

		// JSPページへフォワード（画面表示）
		request.getRequestDispatcher("/student/STDM001.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}