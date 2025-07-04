<%-- STDM001 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- JSTLのcoreとfunctionsライブラリを使用するための宣言 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%-- 未ログインの場合はログインページへリダイレクト --%>
<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/login.jsp" />
</c:if>

<%-- 共通レイアウト base.jsp を読み込む --%>
<c:import url="/base.jsp">
	<c:param name="title" value="学生管理" />
	<c:param name="body">

		<div class="bg-light border p-2 ps-3 mb-4">
			<h2 class="mb-0">学生管理</h2>
		</div>

		<%-- 新規登録画面へのリンク --%>
		<div class="text-end mb-3 mx-3">
			<a href="${pageContext.request.contextPath}/student/create"
				class="text-primary text-decoration-underline">新規登録</a>
		</div>

		<!-- 絞り込み検索パネル -->
		<div class="border p-4 mb-4 bg-white mx-2">
			<%-- 検索フォーム。GETメソッドで自分自身のURL(/student/list)に送信する --%>
			<form action="${pageContext.request.contextPath}/student/list"
				method="get" class="row g-3 align-items-end">

				<%-- 入学年度の絞り込み --%>
				<div class="col-md-3">
					<label for="f1" class="form-label">入学年度</label> <select
						name="entYear" id="f1" class="form-select">
						<option value="">--------</option>
						<%-- コントローラから渡されたentYearOptionsで選択肢を生成 --%>
						<c:forEach var="year" items="${entYearOptions}">
							<%-- 検索実行後に選択した値を保持するための三項演算子 --%>
							<option value="${year}" ${entYearValue == year ? 'selected' : ''}>${year}</option>
						</c:forEach>
					</select>




				</div>

				<%-- クラスの絞り込み --%>
				<div class="col-md-3">
					<label for="f2" class="form-label">クラス</label> <select
						name="classNum" id="f2" class="form-select">
						<option value="">--------</option>
						<%-- コントローラから渡されたclassNumOptionsで選択肢を生成 --%>
						<c:forEach var="classItem" items="${classNumOptions}">
							<option value="${classItem.classNum}"
								${classNumValue == classItem.classNum ? 'selected' : ''}>${classItem.classNum}</option>
						</c:forEach>
					</select>
				</div>

				<%-- 在学状況の絞り込み（チェックボックス） --%>
				<div
					class="col-md-3 d-flex justify-content-center align-items-center ">
					<div class="form-check mb-3">
						<input class="form-check-input" type="checkbox" name="isAttend"
							id="f3" value="true" ${isAttendValue ? 'checked' : ''}> <label
							class="form-check-label mb-0" for="f3">在学中</label>
					</div>
				</div>

				<%-- 絞り込み実行ボタン --%>
				<div class="col-md-3 mb-1">

					<button type="submit" name="filter" class="btn btn-secondary">絞込み</button>
				</div>
				<%-- 入学年度が選択されていない場合のエラーメッセージを表示 --%>
				<c:if test="${not empty error_entYear}">
					<div class="text-warning small mt-1">${error_entYear}</div>
				</c:if>
			</form>
		</div>

		<!-- 検索結果件数の表示 -->
		<%-- fn:length()関数を使ってリストの要素数を取得 --%>
		<div class="mb-2 text-muted small mx-2">
			検索結果：${fn:length(studentList)}件</div>

		<!-- 学生情報テーブル -->
		<div class="table-responsive mx-2">
			<table class="table table-hover align-middle text-center">

				<thead>
					<tr>
						<th class="text-start">入学年度</th>
						<th>学生番号</th>
						<th class="text-start">氏名</th>
						<th>クラス</th>
						<th>在学中</th>
						<th></th>
						<%-- 変更リンク用の空ヘッダー --%>
					</tr>
				</thead>

				<tbody>
					<%-- c:chooseを使って、studentListが空でないかチェック --%>
					<c:choose>
						<%-- studentListにデータが存在する場合 --%>
						<c:when test="${not empty studentList}">
							<%-- c:forEachでstudentListをループして、1件ずつテーブルの行として表示 --%>
							<c:forEach var="student" items="${studentList}">
								<tr>
									<td class="text-start">${student.entYear}</td>
									<td>${student.no}</td>
									<td class="text-start">${student.name}</td>
									<td>${student.classNum}</td>
									<td>
										<%-- c:chooseで在学状況(isAttend)がtrueかfalseかによって表示を切り替え --%> <c:choose>
											<c:when test="${student.attend}">○</c:when>
											<c:otherwise>×</c:otherwise>
										</c:choose>
									</td>
									<%-- 変更画面へのリンク。学生番号(no)をクエリパラメータとして渡す --%>
									<td><a
										href="${pageContext.request.contextPath}/student/update?no=${student.no}"
										class="text-primary text-decoration-none">変更</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<%-- studentListが空の場合 --%>
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