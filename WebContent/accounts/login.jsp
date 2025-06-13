<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.user}">
    <c:redirect url="/main/index.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <style>
        .login-container { width: 400px; margin: 50px auto; padding: 20px; background-color: #fff; border: 1px solid #ddd; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .login-container h2 { text-align: center; background-color: #f0f0f0; padding: 10px; margin-top: 0; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; }
        .form-group input { width: 95%; padding: 8px; }
        .btn { display: block; width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; cursor: pointer; font-size: 16px; }
        .error { color: red; text-align: center; margin-bottom: 15px; }
        .show-password { font-size: 0.8em; }
    </style>
</head>
<body>

    <c:import url="/menu.jsp"/>

    <div class="login-container">
        <h2> ログイン</h2>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="login" method="post">
            <div class="form-group">
                <label for="id">ID</label>
                <input type="text" id="id" name="id" required>
            </div>
            <div class="form-group">
                <label for="password">パスワード</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group show-password">
                <input type="checkbox" id="showPassword" onclick="togglePassword()">
                <label for="showPassword">パスワードを表示</label>
            </div>
            <button type="submit" class="btn">ログイン</button>
        </form>
    </div>

    <script>
        function togglePassword() {
            const passwordField = document.getElementById("password");
            passwordField.type = passwordField.type === "password" ? "text" : "password";
        }
    </script>
</body>
</html>
