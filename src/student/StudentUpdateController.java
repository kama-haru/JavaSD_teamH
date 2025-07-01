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

@WebServlet(urlPatterns = {"/student/update"})
public class StudentUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String schoolCd = (String) session.getAttribute("schoolCd");

        Student updatedStudent = (Student) session.getAttribute("updatedStudent");
        if (updatedStudent != null) {
            // エラーチェックを通過した更新用データがセッションにある場合はそれを使う
            request.setAttribute("ent_year", null); // 入学年度は変えられないのでnullや空に
            request.setAttribute("no", updatedStudent.getNo());
            request.setAttribute("name", updatedStudent.getName());
            request.setAttribute("num", updatedStudent.getClassNum());
            request.setAttribute("sj_attend", updatedStudent.isAttend());
            session.removeAttribute("updatedStudent");
        } else {
            // 普通の編集画面表示時
            String studentNo = request.getParameter("no");
            if (studentNo == null || studentNo.isEmpty()) {
                request.setAttribute("error", "学生番号が指定されていません。");
                request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
                return;
            }

            try {
                StudentDao studentDao = new StudentDao();
                Student student = studentDao.findByNo(studentNo);

                if (student == null || !schoolCd.equals(student.getSchoolCd())) {
                    request.setAttribute("error", "指定された学生情報が見つからないか、権限がありません。");
                    request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
                    return;
                }

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

        try {
            ClassNumDao classNumDao = new ClassNumDao();
            List<ClassNum> classList = classNumDao.findAll(schoolCd);
            request.setAttribute("classList", classList);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "クラス情報の取得に失敗しました。");
        }

        request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
    }

    // POSTは更新処理
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String schoolCd = (String) session.getAttribute("schoolCd");

        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");
        boolean isAttend = request.getParameter("is_attend") != null;

        try {
            StudentDao dao = new StudentDao();
            Student student = dao.findByNo(no);

            if (student == null || !schoolCd.equals(student.getSchoolCd())) {
                request.setAttribute("error", "指定された学生情報が見つからないか、権限がありません。");
                request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
                return;
            }

            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);

            boolean success = dao.update(student);
            if (success) {
                // 更新成功したら完了画面へ遷移
                request.getRequestDispatcher("/student/student_update_done.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "情報の更新に失敗しました。");
                doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "予期せぬエラーが発生しました。");
            doGet(request, response);
        }
}
}