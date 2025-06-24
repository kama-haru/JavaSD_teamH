<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生管理</title>
<style>
    body {
        font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 20px;
        color: #212529;
    }

    .container {
        max-width: 1000px;
        margin: 0 auto;
        background-color: #fff;
        border: 1px solid #dee2e6;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
    }

    .page-title-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 25px 30px;
        border-bottom: 1px solid #dee2e6;
        background-color: #f0f2f5;
    }
    .page-title-bar span {
        font-size: 24px;
        font-weight: bold;
    }
    .page-title-bar a {
        font-size: 14px;
        padding: 8px 16px;
        background-color: #007bff;
        color: #fff;
        border-radius: 4px;
        text-decoration: none;
    }
    .page-title-bar a:hover {
        background-color: #0056b3;
    }

    .search-panel {
        padding: 20px 30px;
        border-bottom: 1px solid #dee2e6;
    }
    .search-form {
        display: flex;
        align-items: center;
        gap: 20px;
        flex-wrap: wrap;
    }
    .form-group {
        display: flex;
        flex-direction: column;
    }
    .form-group label {
        font-size: 12px;
        font-weight: 500;
        margin-bottom: 5px;
    }
    .form-group select {
        width: 180px;
        padding: 6px 10px;
        font-size: 14px;
        border: 1px solid #ced4da;
        border-radius: 4px;
        background-color: #fff;
    }

    .checkbox-group {
        display: flex;
        align-items: center;
    }
    .checkbox-group input[type="checkbox"] {
        transform: scale(1.2);
        margin-right: 6px;
    }
    .checkbox-group label {
        font-size: 14px;
    }

    .submit-button {
        padding: 8px 20px;
        background-color: #343a40;
        color: #fff;
        border-radius: 4px;
        border: none;
        font-size: 14px;
        cursor: pointer;
    }
    .submit-button:hover {
        background-color: #23272b;
    }

    .list-info {
        padding: 10px 30px;
        font-size: 13px;
        color: #555;
    }

    .list-panel {
        padding: 0 30px 30px 30px;
    }
    .student-table {
        width: 100%;
        border-collapse: collapse;
    }
    .student-table th,
    .student-table td {
        padding: 12px 10px;
        font-size: 14px;
        border-bottom: 1px solid #e0e0e0;
        text-align: center;
    }
    .student-table th {
        background-color: #f8f9fa;
        font-weight: 600;
        color: #333;
    }
    .student-table th:nth-child(1),
    .student-table td:nth-child(1),
    .student-table th:nth-child(3),
    .student-table td:nth-child(3) {
        text-align: left;
    }
    .student-table td:nth-child(6) {
        width: 60px;
    }
    .student-table tbody tr:hover {
        background-color: #f1f3f5;
    }
    .student-table a {
        color: #007bff;
        font-weight: 500;
        text-decoration: none;
    }
    .student-table a:hover {
        text-decoration: underline;
    }
    .no-data-message {
        padding: 40px;
        text-align: center;
        color: #999;
    }
</style>
</head>
<body>
<div class="container">
    <div class="page-title-bar">
        <span>学生管理</span>
        <a href="${pageContext.request.contextPath}/student/create">新規登録</a>
    </div>

    <div class="panel search-panel">
        <form action="${pageContext.request.contextPath}/student/list" method="get" class="search-form">
            <div class="form-group">
                <label for="f1">入学年度</label>
                <select name="entYear" id="f1">
                    <option value="">--------</option>
                    <c:forEach var="year" items="${entYearOptions}">
                        <option value="${year}" ${entYearValue == year ? 'selected' : ''}>${year}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="f2">クラス</label>
                <select name="classNum" id="f2">
                    <option value="">--------</option>
                    <c:forEach var="classItem" items="${classNumOptions}">
                        <option value="${classItem.classNum}" ${classNumValue == classItem.classNum ? 'selected' : ''}>${classItem.classNum}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="checkbox-group">
                <input type="checkbox" name="isAttend" id="f3" value="true" ${isAttendValue ? 'checked' : ''}>
                <label for="f3">在学中</label>
            </div>
            <div>
                <button type="submit" class="submit-button">絞込み</button>
            </div>
        </form>
    </div>

    <div class="list-info">
        検索結果：${fn:length(studentList)}件
    </div>

    <div class="panel list-panel">
        <table class="student-table">
            <thead>
                <tr>
                    <th>入学年度</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>クラス</th>
                    <th>在学中</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty studentList}">
                        <c:forEach var="student" items="${studentList}">
                            <tr>
                                <td>${student.entYear}</td>
                                <td>${student.no}</td>
                                <td>${student.name}</td>
                                <td>${student.classNum}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${student.attend}">○</c:when>
                                        <c:otherwise>×</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/student/update?no=${student.no}">変更</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="no-data-message">該当する学生情報はありません。</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
