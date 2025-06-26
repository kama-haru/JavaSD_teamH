<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="/accounts/login.jsp"/>
</c:if>

<c:import url="/base.jsp">
    <c:param name="title" value="学生管理" />
    <c:param name="body">

	<div class="bg-light border p-2 ps-3 mb-4">
                <h2 class="mb-0">学生管理</h2>
            </div>
	<div class="text-end mb-3">
		<a href="${pageContext.request.contextPath}/student/create" class="text-primary text-decoration-underline">新規登録</a>
	</div>

	<!-- Search Panel -->
	<div class="border p-4 mb-4 bg-white">
		<form action="${pageContext.request.contextPath}/student/list" method="get" class="row g-3 align-items-end">
			<div class="col-md-3">
				<label for="f1" class="form-label">入学年度</label>
				<select name="entYear" id="f1" class="form-select">
					<option value="">--------</option>
					<c:forEach var="year" items="${entYearOptions}">
						<option value="${year}" ${entYearValue == year ? 'selected' : ''}>${year}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-3">
				<label for="f2" class="form-label">クラス</label>
				<select name="classNum" id="f2" class="form-select">
					<option value="">--------</option>
					<c:forEach var="classItem" items="${classNumOptions}">
						<option value="${classItem.classNum}" ${classNumValue == classItem.classNum ? 'selected' : ''}>${classItem.classNum}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-3 form-check d-flex align-items-center mt-4">
				<input class="form-check-input" type="checkbox" name="isAttend" id="f3" value="true" ${isAttendValue ? 'checked' : ''}>
				<label class="form-check-label mb-0" for="f3">在学中</label>
			</div>
			<div class="col-md-3">
				<button type="submit" class="btn btn-secondary">絞込み</button>
			</div>
		</form>
	</div>

	<!-- Result Info -->
	<div class="mb-2 text-muted small">
		検索結果：${fn:length(studentList)}件
	</div>

	<!-- Student Table -->
	<div class="table-responsive">
		<table class="table table-hover align-middle text-center">
			<thead class="table-light">
				<tr>
					<th class="text-start">入学年度</th>
					<th>学生番号</th>
					<th class="text-start">氏名</th>
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
								<td class="text-start">${student.entYear}</td>
								<td>${student.no}</td>
								<td class="text-start">${student.name}</td>
								<td>${student.classNum}</td>
								<td>
									<c:choose>
										<c:when test="${student.attend}">○</c:when>
										<c:otherwise>×</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/student/update?no=${student.no}" class="text-primary text-decoration-none">変更</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" class="text-center text-muted py-4">該当する学生情報はありません。</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>


</c:param>
</c:import>
