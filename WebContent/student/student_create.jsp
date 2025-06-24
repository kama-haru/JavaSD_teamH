<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生情報登録</title>
<style>
    body { font-family: "Meiryo", "メイリオ", sans-serif; background-color: #f7f7f7; margin: 20px; }
    .container { max-width: 800px; margin: 0 auto; background-color: #fff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
    .page-title { background-color: #f0f2f5; padding: 12px 20px; font-size: 16px; font-weight: bold; color: #333; margin: 0; border-bottom: 1px solid #e0e0e0; }
    .content-panel { padding: 30px 40px; }
    .form-group { margin-bottom: 20px; }
    .form-group label { display: block; font-size: 14px; margin-bottom: 8px; font-weight: bold; }
    .form-group input[type="text"], .form-group select { width: 100%; padding: 8px 12px; font-size: 14px; border: 1px solid #ccc; border-radius: 3px; box-sizing: border-box; }
    .form-actions { display: flex; gap: 25px; align-items: center; margin-top: 30px; }
    .submit-button { padding: 8px 24px; font-size: 14px; color: #fff; background-color: #007bff; border: none; border-radius: 3px; cursor: pointer; }
    .submit-button:hover { background-color: #0056b3; }
    a.back-link { font-size: 14px; color: #007bff; text-decoration: none; }
    a.back-link:hover { text-decoration: underline; }
</style>
</head>
<body>
<div class="container">
    <h2 class="page-title">学生情報登録</h2>
    <div class="content-panel">
        <form action="${pageContext.request.contextPath}/student/create-execute" method="post">
            <div class="form-group">
                <label for="entYear">入学年度</label>
                <select name="entYear" id="entYear">
                    <c:forEach var="year" items="${entYearList}">
                        <option value="${year}" ${year == currentYear ? 'selected' : ''}>${year}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="no">学生番号</label>
                <input type="text" id="no" name="no" placeholder="学生番号を入力してください" required>
            </div>
            <div class="form-group">
                <label for="name">氏名</label>
                <input type="text" id="name" name="name" placeholder="氏名を入力してください" required>
            </div>
            <div class="form-group">
                <label for="classNum">クラス</label>
                <select name="classNum" id="classNum">
                    <%-- ★ 修正箇所: ClassNumオブジェクトのプロパティにアクセスするよう修正 --%>
                    <c:forEach var="classItem" items="${classList}">
                        <option value="${classItem.classNum}">${classItem.classNum}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-actions">
                <button type="submit" class="submit-button">登録</button>
                <a href="${pageContext.request.contextPath}/student/list" class="back-link">戻る</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>