<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	session.invalidate();
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>

	<c:import url="/base.jsp" />

	<div class="container mt-5">
		<div class="mx-auto" style="max-width: 800px;">
			<div class="bg-light p-3 mb-3">ログアウト</div>
			<div class="alert alert-success text-center">ログアウトしました</div>
			<a href="${pageContext.request.contextPath}/accounts/login"
				class="btn btn-link">ログイン</a>
		</div>
	</div>

</body>
</html>
