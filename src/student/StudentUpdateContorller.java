package student;

import java.io.IOException;
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

// クラス名はファイル名 "StudentUpdateContorller.java" に合わせています
@WebServlet(urlPatterns = {"/student/update"})
public class StudentUpdateContorller extends HttpServlet {

    /**
     * GETリクエスト（変更画面の初期表示）
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String studentNo = request.getParameter("no");
        String schoolCd = "S01";

        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();

        try {
            Student student = studentDao.findByNo(studentNo);
            List<ClassNum> classList = classNumDao.findAll(schoolCd);

            if (student != null) {
                request.setAttribute("ent_year", student.getEntYear());
                request.setAttribute("no", student.getNo());
                request.setAttribute("name", student.getName());
                request.setAttribute("num", student.getClassNum());
                request.setAttribute("sj_attend", student.isAttend());
                request.setAttribute("classList", classList);
            } else {
                 request.setAttribute("error", "指定された学生情報が見つかりません。");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "データの取得中にエラーが発生しました。");
        }

        request.getRequestDispatcher("/student/student_update.jsp").forward(request, response);
    }

    /**
     * POSTリクエスト（変更内容の更新処理）
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");
        boolean isAttend = request.getParameter("is_attend") != null;

        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);
        student.setAttend(isAttend);

        StudentDao studentDao = new StudentDao();

        try {
            studentDao.update(student);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "情報の更新に失敗しました。");
            doGet(request, response);
            return;
        }

        // ★★★ 修正箇所 ★★★
        // 処理完了後、完了画面表示用のURLにリダイレクトする
        response.sendRedirect(request.getContextPath() + "/student/update-execute");
    }
}