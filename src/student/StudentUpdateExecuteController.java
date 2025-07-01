package student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import dao.StudentDao;

@WebServlet(urlPatterns = {"/student/update_execute"})
public class StudentUpdateExecuteController extends HttpServlet {

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

        boolean hasError = false;
        String nameError = null;

        // 入力チェック（例：名前は必須）
        if (name == null || name.trim().isEmpty()) {
            nameError = "氏名を入力してください。";
            hasError = true;
        }

        if (hasError) {
            // エラー時は入力値とエラーメッセージをセットして、編集画面に戻す
            request.setAttribute("nameError", nameError);
            request.setAttribute("no", no);
            request.setAttribute("name", name);
            request.setAttribute("num", classNum);
            request.setAttribute("sj_attend", isAttend);

            request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
            return;
        }

        try {
            StudentDao dao = new StudentDao();
            Student student = dao.findByNo(no);

            if (student == null || !schoolCd.equals(student.getSchoolCd())) {
                request.setAttribute("error", "指定された学生情報が見つからないか、権限がありません。");
                request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
                return;
            }

            HttpSession sess = request.getSession();
            Student updatedStudent = new Student();
            updatedStudent.setNo(no);
            updatedStudent.setName(name);
            updatedStudent.setClassNum(classNum);
            updatedStudent.setAttend(isAttend);
            updatedStudent.setSchoolCd(schoolCd);

            sess.setAttribute("updatedStudent", updatedStudent);


			request.getRequestDispatcher("/student/update").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "処理中にエラーが発生しました。");
            request.getRequestDispatcher("../error.jsp").forward(request, response);
        }
    }
}
