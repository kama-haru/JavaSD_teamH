<%-- STDM002 --%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- JSTLのcoreタグライブラリを使用するための宣言 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- セッションに 'user' が存在しない（未ログイン）場合、ログインページにリダイレクトする --%>
<c:if test="${empty sessionScope.user}">
	<c:redirect url="/accounts/login.jsp" />
</c:if>

<%-- 共通レイアウトファイル base.jsp を読み込む --%>
<%-- titleとbodyの2つのパラメータを渡している --%>
<c:import url="/base.jsp">
	<c:param name="title" value="ホーム" />
	<c:param name="body">

		<%-- ページタイトル部分 --%>
		<div class="bg-light border p-2 ps-3 mb-4">
			<h2 class="mb-0">学生情報登録</h2>
		</div>

		<%-- フォーム。入力されたデータは /student/create_execute にPOST送信される --%>
		<form
			action="${pageContext.request.contextPath}/student/create_execute"
			method="post">

			<%-- 入学年度の選択肢（セレクトボックス） --%>
			<div class="mb-3">
				<label for="entYear" class="form-label">入学年度</label> <select
					name="entYear" id="entYear" class="form-select">
					<%-- デフォルトの未選択オプション --%>
					<option value="" <c:if test="${empty entYear}">selected</c:if>>--------</option>
					<%-- コントローラから渡されたentYearListをループして選択肢を生成 --%>
					<c:forEach var="year" items="${entYearList}">
						<%-- バリデーションエラーで戻ってきた際に、以前選択した値を保持するための処理 --%>
						<option value="${year}"
							<c:if test="${year == entYear}">selected</c:if>>${year}</option>
					</c:forEach>
				</select>
				<%-- entYearError（入学年度のエラー）がコントローラから渡された場合のみ、メッセージを表示 --%>
				<c:if test="${not empty entYearError}">
					<div class="text-warning small mt-1">${entYearError}</div>
				</c:if>
			</div>

			<%-- 学生番号の入力フィールド --%>
			<div class="mb-3">
				<label for="no" class="form-label">学生番号</label> <input type="text"
					id="no" name="no" class="form-control" placeholder="学生番号を入力してください"
					value="${no}" required> <%-- required属性でHTML5のクライアントサイドバリデーションも有効化 --%>
				<%-- noError（学生番号のエラー）がコントローラから渡された場合のみ、メッセージを表示 --%>
				<c:if test="${not empty noError}">
					<div class="text-warning small mt-1">${noError}</div>
				</c:if>
			</div>

			<%-- 氏名の入力フィールド --%>
			<div class="mb-3">
				<label for="name" class="form-label">氏名</label> <input type="text"
					id="name" name="name" class="form-control"
					placeholder="氏名を入力してください" value="${name}" required>
			</div>

			<%-- クラスの選択肢（セレクトボックス） --%>
			<div class="mb-4">
				<label for="classNum" class="form-label">クラス</label> <select
					name="classNum" id="classNum" class="form-select">
					<%-- コントローラから渡されたclassListをループして選択肢を生成 --%>
					<c:forEach var="classItem" items="${classList}">
						<option value="${classItem.classNum}"
							<%-- バリデーションエラーで戻ってきた際に、以前選択した値を保持 --%>
							<c:if test="${classItem.classNum == classNum}">selected</c:if>>${classItem.classNum}</option>
					</c:forEach>
				</select>
			</div>

			<%-- 登録ボタンと戻るリンク --%>
			<div class="d-flex gap-3 align-items-center">
				<button type="submit" class="btn btn-primary px-4">登録して終了</button>
			</div>
			<a href="${pageContext.request.contextPath}/student/list"
				class="text-decoration-none text-primary">戻る</a>
		</form>
	</c:param>
</c:import>