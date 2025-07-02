package student;

import java.io.IOException;
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

// URLパターン /student/update にマッピング
@WebServlet(urlPatterns = {"/student/update"})
public class StudentUpdateController extends HttpServlet {

    // GETリクエスト（画面表示）を処理
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String schoolCd = (String) session.getAttribute("schoolCd");

        // ※StudentUpdateExecuteControllerからフォワードされた場合の処理
        Student updatedStudent = (Student) session.getAttribute("updatedStudent");
        if (updatedStudent != null) {
            // セッションに更新用データがあれば、それを使って画面を再表示
            request.setAttribute("ent_year", null); // 入学年度は表示しない
            request.setAttribute("no", updatedStudent.getNo());
            request.setAttribute("name", updatedStudent.getName());
            request.setAttribute("num", updatedStudent.getClassNum());
            request.setAttribute("sj_attend", updatedStudent.isAttend());
            // 使用後はセッションから削除
            session.removeAttribute("updatedStudent");
        } else {
            // 通常の画面表示処理（一覧画面からリンクをクリックした時）
            String studentNo = request.getParameter("no");
            if (studentNo == null || studentNo.isEmpty()) {
                request.setAttribute("error", "学生番号が指定されていません。");
                request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
                return;
            }

            try {
                StudentDao studentDao = new StudentDao();
                Student student = studentDao.findByNo(studentNo);

                // 学生が存在しない、または他校の学生の場合はエラー
                if (student == null || !schoolCd.equals(student.getSchoolCd())) {
                    request.setAttribute("error", "指定された学生情報が見つからないか、権限がありません。");
                    request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
                    return;
                }

                // 取得した学生情報をリクエストスコープにセット
                request.setAttribute("ent_year", student.getEntYear());
                request.setAttribute("no", student.getNo());
                request.setAttribute("name", student.getName());
                request.setAttribute("num", student.getClassNum());
                request.setAttribute("sj_attend", student.isAttend());

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "データ取得中にエラーが発生しました。");
            }
        }

        // 画面のクラス選択肢を準備
        try {
            ClassNumDao classNumDao = new ClassNumDao();
            List<ClassNum> classList = classNumDao.findAll(schoolCd);
            request.setAttribute("classList", classList);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "クラス情報の取得に失敗しました。");
        }

        // student_update.jspにフォワード
        request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
    }

    // POSTリクエスト（更新実行）を処理
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String schoolCd = (String) session.getAttribute("schoolCd");

        // フォームからパラメータを取得
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");
        boolean isAttend = request.getParameter("is_attend") != null;

        try {
            StudentDao dao = new StudentDao();
            Student student = dao.findByNo(no); // DBから現在の学生情報を取得

            // 学生が存在しない、または他校の学生の場合はエラー
            if (student == null || !schoolCd.equals(student.getSchoolCd())) {
                request.setAttribute("error", "指定された学生情報が見つからないか、権限がありません。");
                request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
                return;
            }

            // フォームの値でStudentオブジェクトを更新
            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);

            // DAOで更新処理を実行
            boolean success = dao.update(student);
            if (success) {
                // 更新成功なら完了画面へフォワード
                request.getRequestDispatcher("/student/student_update_done.jsp").forward(request, response);
            } else {
                // 失敗ならエラーメッセージをセットして、doGetを呼び出し編集画面を再表示
                request.setAttribute("error", "情報の更新に失敗しました。");
                doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "予期せぬエラーが発生しました。");
            doGet(request, response); // エラー時も編集画面を再表示
        }
    }
}