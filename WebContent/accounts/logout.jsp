<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty sessionScope.user}">
	<c:redirect url="/main/index.jsp" />
</c:if>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
<style>
.logout-container {
	width: 80%;
	max-width: 960px;
	margin: 30px auto;
	background-color: #ffffff;
	padding: 20px 40px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	flex-grow: 1
}

.logout-title-box {
	background-color: #f8f9fa;
	border: 1px solid #dee2e6;
	padding: 15px;
	margin-bottom: 20px;
}

.logout-title-box h2 {
	margin: 0;
	font-size: 18px;
	font-weight: normal;
}

.success-message {
	background-color: #d4edda;
	border: 1px solid #c3e6cb;
	color: #155724; /
	padding: 15px;
	border-radius: 4px;
	margin-bottom: 30px;
	text-align: center;
}

.login-link a {
	color: #007bff;
	text-decoration: none;
	font-size: 16px;
}

.login-link a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>

	<c:import url="/menu.jsp" />

	<div class="logout-container">

		<div class="logout-title-box">
			<h2>ログアウト</h2>
		</div>

		<div class="success-message">ログアウトしました</div>

		<div class="login-link">
			<a href="${pageContext.request.contextPath}/accounts/login">ログイン</a>
		</div>
	</div>


</body>
</html>
