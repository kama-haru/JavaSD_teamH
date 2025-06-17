<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous">
<style>
    body {
        font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #ffffff;
        padding-top: 80px ;
        padding-bottom: 60px;
    }

    .header {
        background-color:#eaf2ff;
        color: black;
        padding: 20px 30px;
        font-size: 24px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: fixed;
        top: 0;
        width: 100%;
        box-sizing: border-box;
        z-index: 1000;
        border-bottom: 1px solid #ccc;
    }
    .header h1 {
        margin: 0;
        font-size: 24px;
    }
    .header .user-info {
            font-size: 14px;
        }
    .header .user-info a {
        color: #007bff;
        margin-left: 15px;
        text-decoration: none;
        font-size: 16px;
    }
    .header .user-info a:hover {
        text-decoration: underline;
    }
    .footer {
        background-color: #e0e0e0;
        padding: 15px;
        text-align: center;
        position: fixed;
        width: 100%;
        bottom: 0;
        font-size: 0.9em;
		border-top: 1px solid #e0e0e0;
    }
    .footer p {
            margin: 2px 0;
        }
    .container {
        display: flex;
    }
    .sidebar {
        width: 200px;
        padding: 20px;
        min-height: calc(100vh - 80px - 60px);
    }
    .sidebar .current a {
            text-decoration: underline;
    }
    .sidebar ul {
        list-style-type: none;
        padding: 0;
    }
    .sidebar ul li {
        margin-bottom: 10px;
    }
    .sidebar a {
        text-decoration: none;
        color: #0066cc;
    }
    .main-content {
        flex-grow: 1;
        padding: 20px;
    }
</style>
</head>
<body>
<div class="header">
    <div>
        <h1>得点管理システム</h1>
    </div>
    <div class="user-info">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <span>${sessionScope.user.name} 様</span>
                <a href="${pageContext.request.contextPath}/logout">ログアウト</a>
            </c:when>
        </c:choose>
    </div>
</div>

<div class="footer">
    <p>© 2023 TIC</p>
    <p>大原学園</p>
</div>
</body>
</html>