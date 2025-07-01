<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/login.jsp" />
</c:if>

<c:import url="/base.jsp">
	<c:param name="title" value="成績登録" />
	<c:param name="body">

		<div class="bg-light border p-2 ps-3 mb-4">
			<h2 class="mb-0">成績登録</h2>
		</div>

		<!-- 検索フォーム -->
		<form action="test_regist" method="get" class="mb-4">
			<div class="row g-3 align-items-center mb-3 border p-2 px-2 mx-auto mt-2">

				<div class="col-md-2">
					<label class="form-label">入学年度</label> <select name="entYear"
						class="form-select">
						<option value="">------</option>
						<c:forEach var="y" items="${entYearList}">
							<option value="${y}" <c:if test="${y == entYear}">selected</c:if>>${y}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-2">
					<label class="form-label">クラス</label> <select name="classNum"
						class="form-select">
						<option value="">------</option>
						<c:forEach var="c" items="${classNumList}">
							<option value="${c}"
								<c:if test="${c == classNum}">selected</c:if>>${c}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-4">
					<label class="form-label">科目</label> <select name="subjectCd"
						class="form-select">
						<option value="">------</option>
						<c:forEach var="s" items="${subjectList}">
							<option value="${s.cd}"
								<c:if test="${s.cd == subjectCd}">selected</c:if>>${s.name}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-2">
					<label class="form-label">回数</label> <select name="no"
						class="form-select">
						<option value="">------</option>
						<c:forEach begin="1" end="2" var="i">
							<option value="${i}" <c:if test="${i == no}">selected</c:if>>${i}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-auto mt-2">
					<button type="submit" class="btn btn-primary">検索</button>
				</div>

			</div>
		</form>

		<!-- 成績一覧 -->
		<c:if test="${not empty list}">
			<p class="fw-bold">
				科目名：
				<c:forEach var="s" items="${subjectList}">
					<c:if test="${s.cd == subjectCd}">${s.name}</c:if>
				</c:forEach>
				（${no} 回目）
			</p>

			<form action="test_regist_execute" method="post">
				<input type="hidden" name="entYear" value="${entYear}" /> <input
					type="hidden" name="classNum" value="${classNum}" /> <input
					type="hidden" name="subjectCd" value="${subjectCd}" /> <input
					type="hidden" name="no" value="${no}" />

				<div class="table-responsive">
					<table class="table table-bordered table-hover align-middle">
						<thead class="table-light">
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
									<td><input type="number" class="form-control text-center"
										name="point_${t.studentNo}" value="${t.point}" min="0"
										max="100" required /></td>
									<td>
										<div class="form-check text-center">
											<input type="checkbox" class="form-check-input" name="delete"
												value="${t.studentNo}" />
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="mt-4">
					<button type="submit" class="btn btn-success">登録して終了</button>
				</div>
			</form>
		</c:if>

	</c:param>
</c:import>