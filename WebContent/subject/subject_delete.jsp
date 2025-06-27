<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<c:import url="/base.jsp">
	<c:param name="body">

		<h2 class="bg-light p-2 w-100">科目情報削除</h2>

		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>

		</c:if>


		<!-- 科目確認と削除ボタン -->
		<c:if test="${empty error}">

			<p>
				<strong>「${param.name}（${param.cd}）」を削除してもよろしいですか</strong>
			</p>

			<form
				action="${pageContext.request.contextPath}/subject/SubjectDeleteExecuteController"
				method="post">
				<input type="hidden" name="cd" value="${param.cd}"> <input
					type="submit" class="btn btn-danger" value="削除">
			</form>
		</c:if>
		<div style="margin-top: 100px;">
			<a href="${pageContext.request.contextPath}/subject/list">戻る</a>
		</div>


	</c:param>
</c:import>
