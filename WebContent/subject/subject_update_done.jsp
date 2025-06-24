<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:import url="/base.jsp">
    <c:param name="body">
<h2 class="bg-light p-2 w-100">科目情報更新</h2>

  <div class="alert text-center rounded-0 m-0 py-1" role="alert" style="background-color: #5cbf80; color: black;">
    更新が完了しました
  </div>
  <div style="margin-top: 120px; ">
<p><a href="${pageContext.request.contextPath}/subject/subject_list">科目一覧</a></p>
</div>

 </c:param>
</c:import>