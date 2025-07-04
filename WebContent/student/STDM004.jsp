<%-- STDM004 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 未ログインの場合はログインページへリダイレクト --%>
<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/LOGI001.jsp" />
</c:if>

<%-- 共通レイアウト base.jsp を読み込む --%>
<c:import url="/base.jsp">
	<c:param name="title" value="学生管理" />
	<c:param name="body">

		<div class="bg-light border p-2 ps-3 mb-4">
			<h2 class="mb-0">学生情報変更</h2>
		</div>

		<%-- コントローラからエラーメッセージが渡された場合に表示 --%>
		<c:if test="${not empty error}">
			<div class="alert alert-danger text-center">${error}</div>
		</c:if>

		<%-- フォーム。更新処理は /student/update_execute にPOST送信される --%>
		<form
			action="${pageContext.request.contextPath}/student/update_execute"
			method="post">

			<!-- 入学年度（表示のみ、変更不可） -->
			<div class="mb-2 row">
				<label class="col-sm-3 col-form-label w-100">入学年度</label>
				<div class="col-sm-9 pt-2 ms-3">
					<text>${ent_year}</text>
				</div>
			</div>

			<!-- 学生番号（表示のみ、変更不可） -->
			<div class="mb-2 row">
				<label class="col-sm-3 col-form-label w-100">学生番号</label>
				<div class="col-sm-9 pt-2 ms-3">
					<span>${no}</span>
					<%-- 学生番号は変更しないが、どの学生を更新するかをサーバーに伝えるためにhiddenフィールドで送信する --%>
					<input type="hidden" name="no" value="${no}">
				</div>
			</div>

			<!-- 氏名（変更可能） -->
			<div class="mb-2 row">
				<label for="name" class="col-sm-3 col-form-label w-100">氏名</label>
				<div class="col-sm-12">
					<input type="text" class="form-control" id="name" name="name"
						value="${name}" required maxlength="30">
				</div>
			</div>

			<!-- クラス（変更可能） -->
			<div class="mb-2 row">
				<label for="class_num" class="col-sm-3 col-form-label w-100">クラス</label>
				<div class="col-sm-12">
					<select class="form-select" id="class_num" name="class_num">
						<%-- コントローラから渡されたclassListで選択肢を生成 --%>
						<c:forEach var="classItem" items="${classList}">
							<%-- 現在のクラスが選択された状態にする --%>
							<option value="${classItem.classNum}"
								<c:if test="${classItem.classNum == num}">selected</c:if>>
								${classItem.classNum}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<!-- 在学状況（変更可能） -->
			<div class="mb-3 row">
				<label class="col-sm-auto col-form-label" for="sj_attend">在学中</label>
				<div class="col-sm-9 d-flex align-items-center">
					<%-- sj_attendがtrueの場合にチェック状態にする --%>
					<input class="form-check-input" type="checkbox" id="sj_attend"
						name="is_attend" value="true"
						<c:if test="${sj_attend}">checked</c:if>>
				</div>
			</div>

			<!-- ボタン -->
			<div class="mt-4 d-flex gap-3">
				<button type="submit" class="btn btn-primary">変更</button>
				<a href="${pageContext.request.contextPath}/student/list"
					class="btn btn-outline-secondary">戻る</a>
			</div>
		</form>
	</c:param>
</c:import>