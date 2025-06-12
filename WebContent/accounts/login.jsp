<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン - 得点管理システム</title>
    <style>
        body {
            font-family: sans-serif;
            background-color: #f9f9f9;
            text-align: center;
        }
        .login-container {
            background-color: #ffffff;
            border-radius: 10px;
            padding: 30px;
            margin: 80px auto;
            width: 300px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .error-message {
            color: red;
            font-size: 14px;
            margin-bottom: 15px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="checkbox"] {
            margin-right: 5px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 8px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .footer {
            margin-top: 50px;
            color: #aaa;
            font-size: 0.8em;
        }
    </style>
    <script>
        function togglePassword() {
            var pwField = document.getElementById("password");
            pwField.type = (pwField.type === "password") ? "text" : "password";
        }
    </script>
</head>
<body>

    <!-- ①画面タイトル -->
    <h2>ログイン</h2>

    <div class="login-container">
        <%-- エラーメッセージの表示 --%>
        <%
            String errorType = (String) request.getAttribute("errorType");
            if ("LOGIN".equals(errorType)) {
        %>
            <div class="error-message">
                ログインに失敗しました。IDまたはパスワードが正しくありません。
            </div>
        <% } %>

        <form action="LoginServlet" method="post">
            <!-- ②ログインID -->
            <input type="text" name="id" id="id" maxlength="20" placeholder="半角でご入力ください" required pattern="^[a-zA-Z0-9]+$">

            <!-- ③パスワード -->
            <input type="password" name="password" id="password" maxlength="20" placeholder="20文字以内の半角英数字でご入力ください" required pattern="^[a-zA-Z0-9]+$">

            <!-- ④⑤パスワード表示/非表示 -->
            <input type="checkbox" id="chk_d_ps" onclick="togglePassword()">
            <label for="chk_d_ps">パスワードを表示</label>

            <!-- ⑥ログインボタン -->
            <br><br>
            <input type="submit" name="login" value="ログイン">
        </form>
    </div>

    <div class="footer">
        © 2025 TIC<br>
        大原学園
    </div>

</body>
</html>
