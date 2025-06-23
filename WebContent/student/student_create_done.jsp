<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録完了</title>
<style>
    body { font-family: "Meiryo", "メイリオ", sans-serif; background-color: #f7f7f7; margin: 20px; }
    .container { max-width: 800px; margin: 0 auto; background-color: #fff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
    .page-title { background-color: #f0f2f5; padding: 12px 20px; font-size: 16px; font-weight: bold; color: #333; margin: 0; border-bottom: 1px solid #e0e0e0; }
    .content-panel { padding: 30px 40px; }
    .success-message { background-color: #d4edda; color: #155724; padding: 15px 20px; border-radius: 4px; margin: 0 0 30px 0; font-size: 14px; }
    .navigation-links { display: flex; gap: 25px; align-items: center; }
    .navigation-links a { font-size: 14px; color: #007bff; text-decoration: none; }
    .navigation-links a:hover { text-decoration: underline; }
</style>
</head>
<body>
<div class="container">
    <h2 class="page-title">学生情報登録</h2>
    <div class="content-panel">
        <%-- ②完了メッセージ --%>
        <p class="success-message">登録が完了しました</p>

        <div class="navigation-links">
            <%-- ③戻るリンク (学生登録画面へ) --%>
            <a href="${pageContext.request.contextPath}/student/create">続けて登録する</a>

            <%-- ④学生一覧リンク --%>
            <a href="${pageContext.request.contextPath}/student/list">学生一覧</a>
        </div>
    </div>
</div>
</body>
</html>