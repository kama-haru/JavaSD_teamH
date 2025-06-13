<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f8f9fa;
        padding-top: 80px;
        padding-bottom: 60px;
    }
    .header {
        background-color: #e3f2fd;
        color: black;
        padding: 20px;
        font-size: 24px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        position: fixed;
        top: 0;
        width: 100%;
        box-sizing: border-box;
    }
    .header h1 {
        margin: 0;
        font-size: 24px;
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
        z-index: 1000;
    }
    .container {
        display: flex;
    }
    .sidebar {
        width: 200px;
        padding: 20px;
        background-color: #f0f0f0;
        /* Đảm bảo sidebar chiếm đủ chiều cao màn hình (trừ header và footer) */
        min-height: calc(100vh - 80px - 60px);
    }
    .sidebar h3 {
        margin-top: 0;
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
        color: #333;
    }
    .main-content {
        flex-grow: 1;
        padding: 20px;
    }
</style>

<div class="header">
    <div>
        <h1>得点管理システム</h1>
    </div>
    <div class="user-info">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <span>${sessionScope.user.name} 様</span>
                <a href="logout">ログアウト</a>
            </c:when>
        </c:choose>
    </div>
</div>

<div class="footer">
    <p>© 2023 TIC 大原学園</p>
</div>