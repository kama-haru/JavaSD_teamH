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

@WebServlet(urlPatterns = {"/student/list"})
public class StudentListController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        String schoolCd = (String) session.getAttribute("schoolCd");

        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        List<Student> studentList = null;
        List<Integer> entYearOptions = new ArrayList<>();
        List<ClassNum> classNumOptions = null;

        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        boolean isAttend = "true".equals(request.getParameter("isAttend"));

        // ★★★ BẮT ĐẦU THÊM MÃ ★★★
        // Kiểm tra xem nút "Lọc" có được nhấn không
        boolean isFilterRequest = request.getParameter("filter") != null;

        // Nếu nút được nhấn và năm nhập học không được chọn, đặt thông báo lỗi.
        // Logic xử lý chính vẫn sẽ tiếp tục như bình thường.
        if (isFilterRequest && (entYearStr == null || entYearStr.isEmpty())) {
            request.setAttribute("error_entYear", "入学年度を選択してください。");
        }
        // ★★★ KẾT THÚC THÊM MÃ ★★★

        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        try {
            // Lời gọi đến DAO không thay đổi. Nếu entYear là 0, nó sẽ hoạt động
            // giống như không lọc theo năm, đây chính là hành vi ban đầu.
            studentList = studentDao.filter(entYear, classNum, isAttend, schoolCd);

            classNumOptions = classNumDao.findAll(schoolCd);

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