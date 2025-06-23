<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>学生情報変更完了</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h2.page-title {
            font-size: 24px;
            border-bottom: 1px solid #e9ecef;
            padding-bottom: 15px;
            margin-top: 0;
            margin-bottom: 25px;
        }
        .completion-message {
            background-color: #d1e7dd;
            color: #0f5132;
            border: 1px solid #badbcc;
            padding: 15px 20px;
            border-radius: 4px;
            margin-bottom: 25px;
            text-align: center;
        }
        .back-link-container {
            text-align: center;
        }
        .back-link {
            color: #1a73e8;
            text-decoration: none;
            font-size: 16px;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">

        <h2 class="page-title">学生情報変更</h2>

        <p class="completion-message">
            変更が完了しました
        </p>

        <div class="back-link-container">
            <a href="${pageContext.request.contextPath}/student/list" class="back-link">学生一覧へ戻る</a>
        </div>

    </div>

</body>
</html>