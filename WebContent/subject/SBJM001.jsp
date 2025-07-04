<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/LOGI001.jsp" />
</c:if>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<c:import url="/base.jsp">
	<c:param name="body">

		<div class="bg-light border p-2 ps-3 mb-4">
			<h2 class="mb-0">科目一覧</h2>
		</div>

		<div class="text-end mb-3 mx-3">
			<a href="${pageContext.request.contextPath}/subject/SBJM002.jsp"
				class="text-primary text-decoration-underline">新規登録</a>
		</div>

		<table class="table">
			<thead>
				<tr>
					<th>科目コード</th>
					<th>科目名</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="subject" items="${subjects}">
					<tr>
						<td>${subject.cd}</td>
						<td>${subject.name}</td>
						<td><a
							href="${pageContext.request.contextPath}/subject/SBJM004.jsp?cd=${subject.cd}">変更</a></td>
						<td><a
							href="${pageContext.request.contextPath}/subject/SBJM006.jsp?cd=${subject.cd}&name=${subject.name}">削除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<p>
			<a href="${pageContext.request.contextPath}/main/MMNU001.jsp">TOPページへ</a>
		</p>
	</c:param>
</c:import>