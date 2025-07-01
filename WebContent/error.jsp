<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- base.jsp を使って、共通レイアウトを読み込む -->
<c:import url="/base.jsp">

	<!-- タイトルに「エラー」と表示 -->
	<c:param name="title" value="エラー" />
	<!-- ページ本文（body） -->
	<c:param name="body">
		<!-- エラーメッセージ表示 -->
		<p>エラーが発生しました</p>


	</c:param>
</c:import>