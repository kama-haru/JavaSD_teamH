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
import dao.ClassNumDao;

@WebServlet(urlPatterns = {"/student/create"})
public class StudentCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String schoolCd = "S01";
        ClassNumDao classNumDao = new ClassNumDao();
        // ★ 修正箇所: List<ClassNum> を使うように型を変更
        List<ClassNum> classList = null;
        List<Integer> entYearList = new ArrayList<>();

        try {
            // ★ 修正箇所: DBから取得したList<ClassNum>をそのまま使用する
            classList = classNumDao.findAll(schoolCd);

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