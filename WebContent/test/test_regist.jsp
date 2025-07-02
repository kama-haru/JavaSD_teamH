<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/login.jsp" />
</c:if>

<c:import url="/base.jsp">
	<c:param name="title" value="成績管理" />
	<c:param name="body">
		<div class="container my-5">
			<h2 class="mb-4 fw-bold">成績管理</h2>

			<!-- 検索フォーム -->
			<form method="get" action="test_regist" class="mb-4">
				<div class="row g-2 align-items-end">
					<div class="col-md-2">
						<label class="form-label">入学年度</label> <select name="entYear"
							class="form-select form-select-sm">
							<option value="">------</option>
							<c:forEach var="y" items="${entYearList}">
								<option value="${y}"
									<c:if test="${y == entYear}">selected</c:if>>${y}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2">
						<label class="form-label">クラス</label> <select name="classNum"
							class="form-select form-select-sm">
							<option value="">------</option>
							<c:forEach var="c" items="${classNumList}">
								<option value="${c}"
									<c:if test="${c == classNum}">selected</c:if>>${c}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3">
						<label class="form-label">科目</label> <select name="subjectCd"
							class="form-select form-select-sm">
							<option value="">------</option>
							<c:forEach var="s" items="${subjectList}">
								<option value="${s.cd}"
									<c:if test="${s.cd == subjectCd}">selected</c:if>>${s.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2">
						<label class="form-label">回数</label> <select name="no"
							class="form-select form-select-sm">
							<option value="">------</option>
							<c:forEach begin="1" end="2" var="i">
								<option value="${i}" <c:if test="${i == no}">selected</c:if>>${i}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-auto">
						<button type="submit" class="btn btn-primary btn-sm px-4">検索</button>
					</div>
				</div>
			</form>

			<!-- メッセージ表示 -->
			<c:if test="${not empty error}">
				<div class="text-warning small mt-1">
					<c:forEach var="e" items="${error}">
						<div>${e}</div>
					</c:forEach>
				</div>
			</c:if>

			<c:if
				test="${empty list && not empty entYear && not empty classNum && not empty subjectCd && not empty no}">
				<div class="ps-1 small text-body-secondary fw-normal">
					成績が存在しませんでした。</div>
			</c:if>

			<!-- 成績あり -->
			<c:if test="${not empty list}">
				<p class="fw-bold mt-3">
					科目：
					<c:forEach var="s" items="${subjectList}">
						<c:if test="${s.cd == subjectCd}">${s.name}</c:if>
					</c:forEach>
					（${no} 回）
				</p>

				<form action="test_regist_execute" method="post">
					<input type="hidden" name="entYear" value="${entYear}" /> <input
						type="hidden" name="classNum" value="${classNum}" /> <input
						type="hidden" name="subjectCd" value="${subjectCd}" /> <input
						type="hidden" name="no" value="${no}" />

					<table class="table table-borderless text-center align-middle mt-3">
						<thead class="bg-transparent">
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>点数</th>
								<th>削除</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="t" items="${list}">
								<tr>
									<td>${t.entYear}</td>
									<td>${t.classNum}</td>
									<td>${t.studentNo}</td>
									<td>${t.studentName}</td>
									<td><input type="number"
										class="form-control form-control-sm text-center"
										name="point_${t.studentNo}"
										value="${t.point == 0 ? '' : t.point}" min="0" max="100" /></td>
									<td><input type="checkbox" class="form-check-input"
										name="delete" value="${t.studentNo}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<div class="text-start mt-3">
						<button type="submit" class="btn btn-success btn-sm px-5">登録して終了</button>
					</div>
				</form>
			</c:if>
		</div>
	</c:param>
</c:import>