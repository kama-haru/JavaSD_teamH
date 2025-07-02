<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSTLのcoreタグライブラリを使用するための宣言 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Bootstrap 5.3.0 のCSSをCDNから読み込み、デザインを適用 --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<%-- c:importタグを使い、共通のレイアウトファイルであるbase.jspを読み込む --%>
<%-- base.jspの「body」という名前のパラメータに、このファイル内のコンテンツを埋め込む --%>
<c:import url="/base.jsp">
    <c:param name="body">
        <%-- ページのメインタイトル --%>
        <h2 class="bg-light p-2 w-100">学生情報登録</h2>

        <div class="container">
            <%-- 登録完了メッセージを表示するアラートボックス --%>
            <div class="alert text-center rounded-0 m-0 py-1" role="alert" style="background-color: #5cbf80; color: black;">
                登録が完了しました
            </div>

            <%-- 画面下部にリンクを配置するためのdiv --%>
            <div style="margin-top: 120px; ">
                <%-- request.getContextPath() を使ってコンテキストパスからの相対パスでリンクを生成 --%>
                <a href="<%= request.getContextPath() %>/main/index.jsp" style="margin-right: 80px;">戻る</a>
                <%-- EL式を使って学生一覧画面へのリンクを生成 --%>
                <a href="${pageContext.request.contextPath}/student/list">学生一覧</a>
            </div>
        </div>
    </c:param>
</c:import>