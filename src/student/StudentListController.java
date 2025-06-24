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

import bean.ClassNum;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;

@WebServlet(urlPatterns = {"/student/list"})
public class StudentListController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Daoのインスタンス化
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao(); // ★ 修正箇所: ClassNumDaoをインスタンス化
        List<Student> studentList = null;
        List<Integer> entYearOptions = new ArrayList<>();
        List<ClassNum> classNumOptions = null; // ★ 修正箇所: 型をList<ClassNum>に変更

        // リクエストパラメータを取得
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        boolean isAttend = "true".equals(request.getParameter("isAttend"));

        // 初期表示時（パラメータが何もない場合）は「在学中」をチェック済みにする
        if (entYearStr == null && classNum == null && request.getParameter("isAttend") == null) {
            isAttend = true;
        }

        // 入学年度をint型に変換
        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 学校コードは固定値として使用
        String schoolCd = "S01";

        try {
            // データベースから学生リストをフィルタリングして取得
            studentList = studentDao.filter(entYear, classNum, isAttend, schoolCd);

            // ★ 修正箇所: クラスの選択肢をデータベースから取得
            classNumOptions = classNumDao.findAll(schoolCd);

            // 入学年度の選択肢を作成（現在から過去10年分）
            int currentYear = LocalDate.now().getYear();
            for (int i = 0; i < 10; i++) {
                entYearOptions.add(currentYear - i);
            }

            // JSPに渡す値をリクエストスコープにセット
            request.setAttribute("studentList", studentList);
            request.setAttribute("entYearOptions", entYearOptions);
            request.setAttribute("classNumOptions", classNumOptions); // DBから取得したリストをセット
            request.setAttribute("entYearValue", entYearStr);
            request.setAttribute("classNumValue", classNum);
            request.setAttribute("isAttendValue", isAttend);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "データの取得中にエラーが発生しました。");
        }

        // student_list.jspにフォワード
        request.getRequestDispatcher("/student/student_list.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }
}