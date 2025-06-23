<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>学生情報変更</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; background-color: #f8f9fa; color: #333; padding: 20px; }
        .form-container { max-width: 600px; margin: 0 auto; background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); }
        h2 { font-size: 24px; border-bottom: 1px solid #e9ecef; padding-bottom: 15px; margin-bottom: 25px; }
        .form-row { display: flex; align-items: center; margin-bottom: 20px; }
        .form-row label { width: 120px; text-align: left; padding-right: 20px; font-weight: 500; color: #555; }
        .input-area { flex-grow: 1; }
        .readonly-text { font-size: 16px; }
        input[type="text"], select { width: 100%; padding: 8px 12px; border: 1px solid #ced4da; border-radius: 4px; font-size: 16px; box-sizing: border-box; }
        input[type="checkbox"] { width: 18px; height: 18px; vertical-align: middle; }
        .actions { padding-top: 20px; display: flex; align-items: center; }
        .btn-update { background-color: #1a73e8; color: white; border: none; padding: 10px 20px; border-radius: 4px; font-size: 16px; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn-update:hover { background-color: #1765cc; }
        .link-back { margin-left: 20px; color: #1a73e8; text-decoration: none; }
        .link-back:hover { text-decoration: underline; }
        .error-message { color: red; font-weight: bold; text-align: center; margin-bottom: 20px; }
    </style>
</head>
<body>

    <div class="form-container">
        <h2>学生情報変更</h2>

        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/student/update" method="post">

            <!-- 入学年度 (表示のみ) -->
            <div class="form-row">
                <label>入学年度</label>
                <div class="input-area">
                    <span class="readonly-text">${ent_year}</span>
                </div>
            </div>

            <!-- 学生番号 (表示のみ、更新キーとしてhiddenで送信) -->
            <div class="form-row">
                <label>学生番号</label>
                <div class="input-area">
                    <span class="readonly-text">${no}</span>
                    <input type="hidden" name="no" value="${no}">
                </div>
            </div>

            <!-- 氏名 -->
            <div class="form-row">
                <label for="name">氏名</label>
                <div class="input-area">
                    <input type="text" id="name" name="name" value="${name}" required maxlength="30">
                </div>
            </div>

            <!-- クラス -->
            <div class="form-row">
                <label for="class_num">クラス</label>
                <div class="input-area">
                    <select id="class_num" name="class_num">
                        <c:forEach var="classItem" items="${classList}">
                            <option value="${classItem.classNum}" <c:if test="${classItem.classNum == num}">selected</c:if>>
                                ${classItem.classNum}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- 在学中 -->
            <div class="form-row">
                <label for="sj_attend">在学中</label>
                <div class="input-area">
                    <input type="checkbox" id="sj_attend" name="is_attend" <c:if test="${sj_attend}">checked</c:if>>
                </div>
            </div>

            <!-- ボタンエリア -->
            <div class="actions">
                <button type="submit" class="btn-update">変更</button>
                <a href="${pageContext.request.contextPath}/student/list" class="link-back">戻る</a>
            </div>

        </form>
    </div>

</body>
</html>