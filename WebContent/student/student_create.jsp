<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/login.jsp" />
</c:if>

<c:import url="/base.jsp">
	<c:param name="title" value="ホーム" />
	<c:param name="body">
		<div class="bg-light border p-2 ps-3 mb-4">
			<h2>学生情報登録</h2>
		</div>

		<form
			action="${pageContext.request.contextPath}/student/create-execute"
			method="post">
			<div class="mb-3">
				<label for="entYear" class="form-label">入学年度</label> <select
					name="entYear" id="entYear" class="form-select">
					<option value="" <c:if test="${empty entYear}">selected</c:if>>--------</option>
					<c:forEach var="year" items="${entYearList}">
						<option value="${year}"
							<c:if test="${year == entYear}">selected</c:if>>${year}</option>
					</c:forEach>
				</select>
				<c:if test="${not empty entYearError}">
					<div class="text-warning small mt-1">${noError}</div>
				</c:if>
			</div>

			<div class="mb-3">
				<label for="no" class="form-label">学生番号</label> <input type="text"
					id="no" name="no" class="form-control" placeholder="学生番号を入力してください"
					value="${no}" required>
				<c:if test="${not empty noError}">
					<div class="text-warning small mt-1">${noError}</div>
				</c:if>
			</div>

			<div class="mb-3">
				<label for="name" class="form-label">氏名</label> <input type="text"
					id="name" name="name" class="form-control"
					placeholder="氏名を入力してください" value="${name}" required>
			</div>

			<div class="mb-4">
				<label for="classNum" class="form-label">クラス</label> <select
					name="classNum" id="classNum" class="form-select">
					<c:forEach var="classItem" items="${classList}">
						<option value="${classItem.classNum}"
							<c:if test="${classItem.classNum == classNum}">selected</c:if>>${classItem.classNum}</option>
					</c:forEach>
				</select>
			</div>

			<div class="d-flex gap-3 align-items-center">
				<button type="submit" class="btn btn-primary px-4">登録して終了</button>
			</div>
			<a href="${pageContext.request.contextPath}/student/list"
				class="text-decoration-none text-primary">戻る</a>
		</form>
	</c:param>
</c:import>
