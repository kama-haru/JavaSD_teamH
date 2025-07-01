<!-- logout.jsp -LOGO001 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	session.invalidate();
%>

<c:import url="/base.jsp">
    <c:param name="title" value="ログアウト" />
    <c:param name="body">
		<!-- 内容 -->
        <div class="mx-auto" style="max-width: 800px;">
            <div class="bg-light p-3 mb-3">ログアウト</div>
            <!-- メッセージ -->
            <div class="alert alert-success text-center">ログアウトしました</div>
            <!-- ログイン戻るボタン -->
			<a href="${pageContext.request.contextPath}/accounts/login"
				class="btn btn-link">ログイン</a>
		</div>


    </c:param>
</c:import>
