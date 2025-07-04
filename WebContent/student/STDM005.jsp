<%-- STDM005 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<%-- 共通レイアウト base.jsp を読み込む --%>
<c:import url="/base.jsp">
    <c:param name="body">
        <h2 class="bg-light p-2 w-100">学生情報更新</h2>

        <div class="container">
            <%-- 更新完了メッセージ --%>
            <div class="alert text-center rounded-0 m-0 py-1" role="alert" style="background-color: #5cbf80; color: black;">
                更新が完了しました
            </div>

            <div style="margin-top: 120px; ">
                <%-- 学生一覧画面へのリンク --%>
                <a href="${pageContext.request.contextPath}/student/list">学生一覧</a>
            </div>
        </div>
    </c:param>
</c:import>