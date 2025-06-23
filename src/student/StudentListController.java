package student;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao(); // 元に戻します
        List<Student> studentList = null;
        List<String> classNumOptions = null;
        List<Integer> entYearOptions = new ArrayList<>();

        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        boolean isAttend = "true".equals(request.getParameter("isAttend"));

        if (entYearStr == null && classNum == null && request.getParameter("isAttend") == null) {
            isAttend = true;
        }

        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String schoolCd = "S01";

        try {
            studentList = studentDao.filter(entYear, classNum, isAttend, schoolCd);

            // ★★★ データベースから読み込む元のコードに戻します ★★★
            List<ClassNum> classNumObjects = classNumDao.findAll(schoolCd);

            if (classNumObjects != null) {
                classNumOptions = classNumObjects.stream()
                                                 .map(cn -> cn.getClassNum())
                                                 .collect(Collectors.toList());
            }

            int currentYear = LocalDate.now().getYear();
            for (int i = 0; i < 10; i++) {
                entYearOptions.add(currentYear - i);
            }

            request.setAttribute("studentList", studentList);
            request.setAttribute("entYearOptions", entYearOptions);
            request.setAttribute("classNumOptions", classNumOptions);
            request.setAttribute("entYearValue", entYearStr);
            request.setAttribute("classNumValue", classNum);
            request.setAttribute("isAttendValue", isAttend);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "データの取得中にエラーが発生しました。");
        }

        request.getRequestDispatcher("/student/student_list.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }
}