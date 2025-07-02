<!-- index.jsp - MMNU001 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/login.jsp" />
</c:if>

<c:import url="/base.jsp">
	<c:param name="title" value="ホーム" />
	<c:param name="body">
		<!-- bodyの内容 -->
		<div class="bg-light border p-2 ps-3 mb-4">
			<h2 class="mb-0">メニュー</h2>
		</div>


		<div class="row g-4 justify-content-center me-3 ms-2">

			<!-- 学生管理 -->
			<div class="col-md-4">
				<div
					class="d-flex justify-content-center align-items-center p-4 rounded-3 shadow-sm h-100"
					style="background-color: #e6cccc;">
					<a href="${pageContext.request.contextPath}/student/list"
						class="fs-5 text-decoration-underline">学生管理</a>
				</div>
			</div>

			<!-- 成績管理 -->
			<div class="col-md-4">
				<div
					class="d-flex justify-content-center align-items-center flex-column text-center p-4 rounded-3 shadow-sm"
					style="background-color: #d9ead3; height: 150px;">
					<h5 class="fw-normal mb-1">成績管理</h5>
					<a href="#" class="fs-5 text-decoration-underline d-block mb-1">成績登録</a>
					<a href="#" class="fs-5 text-decoration-underline d-block">成績参照</a>
				</div>
			</div>

			<!-- 科目管理 -->
			<div class="col-md-4">
				<div
					class="d-flex justify-content-center align-items-center p-4 rounded-3 shadow-sm h-100"
					style="background-color: #d9d2e9;">
					<a href="${pageContext.request.contextPath}/subject/list"
						class="fs-5 text-decoration-underline">科目管理</a>
				</div>
			</div>

		</div>

	</c:param>
</c:import>
