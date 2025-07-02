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

// URLパターン /student/list にマッピング
@WebServlet(urlPatterns = {"/student/list"})
public class StudentListController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        String schoolCd = (String) session.getAttribute("schoolCd"); // ログイン中のユーザーの学校コードを取得

        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        List<Student> studentList = null;
        List<Integer> entYearOptions = new ArrayList<>();
        List<ClassNum> classNumOptions = null;

        // 絞り込みフォームからのパラメータを取得
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        // チェックボックスはチェックされていると"true"が送られる
        boolean isAttend = "true".equals(request.getParameter("isAttend"));

        // 初回アクセス時（パラメータが何もない場合）は、「在学中」をデフォルトでONにする
        if (entYearStr == null && classNum == null && request.getParameter("isAttend") == null) {
            isAttend = true;
        }

        // 入学年度を数値に変換
        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        try {
            // DAOのfilterメソッドを呼び出し、条件に合う学生リストを取得（学校コードも渡す）
            studentList = studentDao.filter(entYear, classNum, isAttend, schoolCd);

            // 絞り込み用のクラス選択肢を取得（学校コードでフィルタリング）
            classNumOptions = classNumDao.findAll(schoolCd);

            // 絞り込み用の入学年度選択肢（過去10年分）を作成
            int currentYear = LocalDate.now().getYear();
            for (int i = 0; i < 10; i++) {
                entYearOptions.add(currentYear - i);
            }

            // JSPで表示・利用するデータをリクエストスコープにセット
            request.setAttribute("studentList", studentList);
            request.setAttribute("entYearOptions", entYearOptions);
            request.setAttribute("classNumOptions", classNumOptions);
            // 検索後にフォームの値を保持するための値をセット
            request.setAttribute("entYearValue", entYearStr);
            request.setAttribute("classNumValue", classNum);
            request.setAttribute("isAttendValue", isAttend);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "データの取得中にエラーが発生しました。");
        }

        // student_list.jsp にフォワードして画面表示
        request.getRequestDispatcher("/student/student_list.jsp").forward(request, response);
    }

    // POSTリクエストが来てもGETと同じ処理を実行
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }
}