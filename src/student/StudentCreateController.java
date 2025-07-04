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
import dao.ClassNumDao;

// URLパターン /student/create にマッピング
@WebServlet(urlPatterns = {"/student/create"})
public class StudentCreateController extends HttpServlet {

    // GETリクエストを処理するメソッド
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // セッションからログイン中のユーザーの学校コードを取得
        String schoolCd = (String) session.getAttribute("schoolCd");

        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classList = null;
        List<Integer> entYearList = new ArrayList<>();

        try {
            // DAOを使用して、学校コードに紐づくクラスの一覧を取得
            classList = classNumDao.findAll(schoolCd);

            // 現在の年を取得し、過去10年分の年度リストを作成
            int currentYear = LocalDate.now().getYear();
            for (int i = 0; i < 10; i++) {
                entYearList.add(currentYear - i);
            }

            // JSPで利用するために、取得したデータをリクエストスコープにセット
            request.setAttribute("classList", classList);
            request.setAttribute("entYearList", entYearList);
            request.setAttribute("currentYear", currentYear);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "画面の準備中にエラーが発生しました。");
        }

        // 処理を student_create.jsp にフォワード（転送）して画面を表示
        request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
    }

    // POSTリクエストが来た場合も、同じくGETメソッドの処理を呼び出す
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}