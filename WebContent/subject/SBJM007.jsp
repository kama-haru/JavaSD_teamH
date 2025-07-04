<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<c:import url="/base.jsp">
    <c:param name="body">

<body>
<h2 class="bg-light p-2 w-100">科目情報削除</h2>
  <div class="alert text-center rounded-0 m-0 py-1" role="alert" style="background-color: #5cbf80; color: black;">
    削除が完了しました
  </div>
  <div style="margin-top: 120px; ">
<p><a href="${pageContext.request.contextPath}/subject/list">科目一覧</a></p>
</div>
 </c:param>
</c:import>