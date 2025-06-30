<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/login.jsp" />
</c:if>

<c:import url="/base.jsp">
	<c:param name="title" value="学生管理" />
	<c:param name="body">

		<div class="bg-light border p-2 ps-3 mb-4">
			<h2 class="mb-0">成績参照</h2>
		</div>

		<!-- 科目情報による検索 -->
		<div class="card p-4 mb-4">
			<!-- Form 1: 科目情報 -->
			<form action="test_list_subject" method="post" class="mb-4">
				<div class="row g-3 align-items-center">

					<div class="col-auto">
						<div class="fw-bold">科目情報</div>
					</div>

					<!-- 入学年度 -->
					<div class="col-auto">
						<label class="form-label mb-0">入学年度</label> <select name="entYear"
							class="form-select form-select-sm" style="width: 110px;">
							<option value="">------</option>
							<c:forEach var="y" items="${entYearList}">
								<option value="${y}"
									<c:if test="${y == entYear}">selected</c:if>>${y}</option>
							</c:forEach>
						</select>
					</div>

					<!-- クラス -->
					<div class="col-auto">
						<label class="form-label mb-0">クラス</label> <select name="classNum"
							class="form-select form-select-sm" style="width: 110px;">
							<option value="">------</option>
							<c:forEach var="c" items="${classNumList}">
								<option value="${c}"
									<c:if test="${c == classNum}">selected</c:if>>${c}</option>
							</c:forEach>
						</select>
					</div>

					<!-- 科目 -->
					<div class="col-auto">
						<label class="form-label mb-0">科目</label> <select name="subjectCd"
							class="form-select form-select-sm" style="width: 170px;">
							<option value="">------</option>
							<c:forEach var="s" items="${subjectList}">
								<option value="${s.cd}"
									<c:if test="${s.cd == subjectCd}">selected</c:if>>${s.name}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-auto">
						<button type="submit" class="btn btn-primary btn-sm">検索</button>
					</div>
				</div>

				<c:if test="${not empty errorSubject}">
					<div class="text-danger mt-2">${errorSubject}</div>
				</c:if>
			</form>


			<hr class="my-1" />

			<!-- Form 2: 学生情報 -->
			<form action="test_list_student" method="post">
				<div class="row g-3 align-items-center">

					<div class="col-auto">
						<div class="fw-bold">学生情報</div>
					</div>

					<!-- 学生番号 -->
					<div class="col-auto">
						<label class="form-label mb-0">学生番号</label> <input type="text"
							name="studentNo" value="${studentNo}"
							class="form-control form-control-sm" style="width: 235px;" />
					</div>

					<div class="col-auto">
						<button type="submit" class="btn btn-primary btn-sm">検索</button>
					</div>
				</div>

				<c:if test="${not empty errorStudent}">
					<div class="text-danger mt-2">${errorStudent}</div>
				</c:if>
			</form>
		</div>
		<!-- 案内メッセージ：初期状態のみ表示 -->
		<c:if
			test="${empty listSubject and empty listStudent and empty errorSubject and empty errorStudent and empty message}">
			<div class="text-primary small mt-3">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</div>
		</c:if>

		<!-- メッセージ -->
		<c:if test="${not empty message}">
			<div class="text-danger mt-3">${message}</div>
		</c:if>

		<!-- 検索結果：科目別成績 -->
		<c:if test="${not empty listSubject}">
			<c:if test="${not empty subjectName}">
				<div class="mb-2">科目：${subjectName}</div>
			</c:if>

			<div class="table-responsive">
				<table
					class="table table-bordered table-hover text-center align-middle">
					<thead class="table-secondary">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学籍番号</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="t" items="${listSubject}">
							<tr>
								<td>${t.entYear}</td>
								<td>${t.classNum}</td>
								<td>${t.studentNo}</td>
								<td>${t.studentName}</td>
								<td>${t.point1}</td>
								<td>${t.point2}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

		<!-- 検索結果：学生別成績 -->
		<c:if test="${not empty listStudent}">
			<div class="fw-bold mb-2 mt-4">学生別成績結果</div>
			<c:if test="${not empty studentName}">
				<div class="mb-2">氏名：${studentName}（${studentNo}）</div>
			</c:if>

			<div class="table-responsive">
				<table
					class="table table-bordered table-hover text-center align-middle">
					<thead class="table-secondary">
						<tr>
							<th>科目名</th>
							<th>科目コード</th>
							<th>回数</th>
							<th>点数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="t" items="${listStudent}">
							<tr>
								<td>${t.subjectName}</td>
								<td>${t.subjectCd}</td>
								<td>${t.no}</td>
								<td>${t.point}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>


	</c:param>
</c:import>
