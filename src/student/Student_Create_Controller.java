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
import dao.ClassNumDao;

@WebServlet(urlPatterns = {"/student/create"})
public class Student_Create_Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String schoolCd = "S01";
        ClassNumDao classNumDao = new ClassNumDao(); // 元に戻します
        List<String> classList = null;
        List<Integer> entYearList = new ArrayList<>();

        try {
            // ★★★ データベースから読み込む元のコードに戻します ★★★
            List<ClassNum> classNumObjects = classNumDao.findAll(schoolCd);
            if (classNumObjects != null) {
                classList = classNumObjects.stream()
                                           .map(cn -> cn.getClassNum())
                                           .collect(Collectors.toList());
            }

            int currentYear = LocalDate.now().getYear();
            for (int i = 0; i < 10; i++) {
                entYearList.add(currentYear - i);
            }

            request.setAttribute("classList", classList);
            request.setAttribute("entYearList", entYearList);
            request.setAttribute("currentYear", currentYear);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "画面の準備中にエラーが発生しました。");
        }

        request.getRequestDispatcher("/student/student_create.jsp").forward(request, response);
    }
}