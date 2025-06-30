<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:import url="/base.jsp">
    <c:param name="body">
<h2 class="bg-light p-2 w-100">成績管理</h2>

  <div class="alert text-center rounded-0 m-0 py-1" role="alert" style="background-color: #5cbf80; color: black;">
    登録が完了しました
  </div>
  <div style="margin-top: 120px; ">
<p><a href="${pageContext.request.contextPath}/test/test_regist">戻る</a></p>
<p><a href="${pageContext.request.contextPath}/test/list">成績参照</a></p>
</div>

 </c:param>
</c:import>