package student;

import java.io.IOException;
import java.io.PrintWriter; // PrintWriterをインポートします

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;

@WebServlet(urlPatterns = {"/student/create-execute"})
public class StudentCreateExecuteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        String schoolCd = "S01";
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        int entYear = Integer.parseInt(request.getParameter("entYear"));
        String classNum = request.getParameter("classNum");

        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(true);
        student.setSchoolCd(schoolCd);

        StudentDao dao = new StudentDao();

        try {
            // データベースに保存を試みる
            dao.save(student);

            // ★★★ 成功した場合 ★★★
            // ここまで来たら登録成功なので、完了画面を表示します。
            request.getRequestDispatcher("/student/student_create_done.jsp").forward(request, response);

        } catch (Exception e) {
            // ★★★ エラーが発生した場合 ★★★
            e.printStackTrace(); // まず、コンソールに詳細なエラーを出力

            // 表示するエラーメッセージを格納する変数
            String errorMessage;

            // ★★★ エラーの種類を判別 ★★★
            // getCause()で根本的な原因を取得し、そのメッセージを分析します。
            Throwable cause = e.getCause();
            String causeMessage = (cause != null) ? cause.toString() : e.toString();

            if (causeMessage.contains("Duplicate entry")) {
                // 原因が「主キーの重複」の場合
                errorMessage = "入力された学生番号「" + no + "」は既に使用されています。";
            } else if (causeMessage.contains("cannot be null")) {
                // 原因が「NULL制約違反」の場合 (例: 必須項目が空)
                errorMessage = "必須項目が入力されていません。学生番号や氏名などを確認してください。";
            } else if (causeMessage.contains("Communications link failure")) {
                // 原因が「データベース接続エラー」の場合
                errorMessage = "データベースに接続できませんでした。管理者に連絡してください。";
            } else {
                // その他の予期せぬエラーの場合
                errorMessage = "予期せぬエラーが発生しました。詳細はコンソールのログを確認してください。";
            }

            // ユーザー向けの簡単なエラー画面を直接表示します。
            out.println("<html>");
            out.println("<head><title>登録エラー</title></head>");
            out.println("<body>");
            out.println("<h2>学生情報の登録に失敗しました</h2>");
            out.println("<p style='color:red;'>エラー理由：" + errorMessage + "</p>");
            out.println("<p>入力内容を確認し、再度登録してください。</p>");
            out.println("<br>");
            out.println("<a href='../student/create'>登録画面に戻る</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}