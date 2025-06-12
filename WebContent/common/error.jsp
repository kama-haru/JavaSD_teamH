<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>システムエラー（DB接続エラー等）- 得点管理システム</title>
    <style>
        body {
            font-family: sans-serif;
            background-color: #f0f8ff;
            margin: 0;
            padding: 0;
        }
        .header {
            background-color: #a8c8f0;
            padding: 20px;
            text-align: center;
        }
        .header h1 {
            color: #333;
            margin: 0;
            font-size: 24px;
        }
        .error-container {
            text-align: center;
            margin-top: 30px;
        }
        .error-message {
            color: #000000;
            font-size: 18px;
            margin: 20px 0;
        }
        .footer {
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            background-color: #e0e0e0;
            text-align: center;
            padding: 10px;
            color: #888;
            font-size: 12px;
        }
    </style>
</head>
<body>
    <!-- ヘッダー部分 -->
    <div class="header">
        <h1>得点管理システム</h1>
    </div>

    <!-- エラー表示部分 -->
    <div class="error-container">
        <%
            String errorType = (String) request.getAttribute("errorType");
            if (errorType == null) {
                errorType = (String) session.getAttribute("errorType");
            }
            if (errorType == null) {
                errorType = "SYSTEM";
            }

            String errorMessage = "";

            if ("LOGIN".equals(errorType)) {
                errorMessage = "ログインに失敗しました。IDまたはパスワードが正しくありません";
            } else if ("DB".equals(errorType)) {
                errorMessage = "データベース接続中にエラーが発生しました。";
            } else {
                errorMessage = "エラーが発生しました";
            }
        %>

        <div class="error-message">
            <%= errorMessage %>
        </div>
    </div>

    <!-- フッター -->
    <div class="footer">
        © 2025 TIC<br>
        大原学園
    </div>
</body>
</html>
