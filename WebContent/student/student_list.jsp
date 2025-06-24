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
        background-color: #f4f4f4; margin: 0; padding: 20px; color: #333;
    }
    .container {
        max-width: 960px; margin: 0 auto; background-color: #fff;
        border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.05);
    }
    .page-title-bar {
        display: flex; justify-content: space-between; align-items: center;
        padding: 15px 25px; border-bottom: 1px solid #e9ecef;
    }
    .page-title-bar span { font-size: 20px; font-weight: bold; }
    .page-title-bar a { font-size: 14px; color: #007bff; text-decoration: none; }
    .page-title-bar a:hover { text-decoration: underline; }
    .panel { padding: 20px 25px; }
    .search-panel { background-color: #f8f9fa; border-bottom: 1px solid #e9ecef; }
    .search-form { display: flex; align-items: flex-end; gap: 20px; flex-wrap: wrap; }
    .form-group { display: flex; flex-direction: column; }
    .form-group label { font-size: 14px; margin-bottom: 6px; }
    .form-group select {
        width: 180px; padding: 8px 10px; border: 1px solid #ced4da;
        border-radius: 4px; font-size: 14px;
    }
    .checkbox-group { display: flex; align-items: center; padding-bottom: 9px; }
    .checkbox-group input[type="checkbox"] { margin-right: 6px; transform: scale(1.1); }
    .checkbox-group label { font-size: 14px; }
    .submit-button {
        padding: 8px 24px; font-size: 14px; color: #fff; background-color: #6c757d;
        border: 1px solid #6c757d; border-radius: 4px; cursor: pointer; transition: background-color 0.2s;
    }
    .submit-button:hover { background-color: #5a6268; }
    .list-info { padding: 15px 25px; font-size: 14px; color: #555; }
    .list-panel { padding: 0 25px 25px 25px; }
    .student-table { width: 100%; border-collapse: collapse; border: 1px solid #dee2e6; }
    .student-table th, .student-table td {
        border-bottom: 1px solid #dee2e6; padding: 12px; font-size: 14px; vertical-align: middle;
    }
    .student-table th { background-color: #f8f9fa; font-weight: bold; text-align: center; }
    .student-table td { text-align: center; }
    .student-table th:nth-child(1), .student-table td:nth-child(1),
    .student-table th:nth-child(3), .student-table td:nth-child(3) { text-align: left; }
    .student-table tbody tr:hover { background-color: #f1f1f1; }
    .student-table a { color: #007bff; text-decoration: none; }
    .student-table a:hover { text-decoration: underline; }
    .no-data-message { text-align: center; padding: 50px; color: #6c757d; }
</style>
</head>
<body>
<div class="container">
    <div class="page-title-bar">
        <span>学生管理</span>
        <a href="${pageContext.request.contextPath}/student/create">新規登録</a>
    </div>

    <div class="panel search-panel">
        <form action="list" method="get" class="search-form">
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
                    <%-- ★ 修正箇所: ClassNumオブジェクトのプロパティにアクセスするよう修正 --%>
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
                                    <a href="update?no=${student.no}">変更</a>
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