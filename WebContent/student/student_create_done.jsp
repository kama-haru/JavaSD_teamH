<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/login.jsp" />
</c:if>

<c:import url="/base.jsp">
	<c:param name="title" value="登録完了" />
	<c:param name="body">
		<div class="bg-light border p-2 ps-3 mb-4">
			<h2>学生情報登録</h2>
		</div>

		<!-- ②完了メッセージ -->
		<div class="alert alert-success fs-6" role="alert">登録が完了しました</div>

		<!-- ③戻る／一覧リンク -->
		<div class="d-flex gap-4">
			<a href="${pageContext.request.contextPath}/student/create"
				class="text-decoration-none text-primary">続けて登録する</a> <a
				href="${pageContext.request.contextPath}/student/list"
				class="text-decoration-none text-primary">学生一覧</a>
		</div>

	</c:param>
</c:import>
