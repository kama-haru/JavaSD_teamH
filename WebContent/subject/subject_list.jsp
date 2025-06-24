<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<c:import url="/base.jsp">
    <c:param name="body">
<h2 class="bg-light p-2 w-100">科目一覧</h2>

<div style="text-align: right; margin-bottom: 10px;">
    <a href="subject_create.jsp">新規登録</a>
</div>

<table class="table">
  <thead>
    <tr>
      <th>科目コード</th>
      <th>科目名</th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="subject" items="${subjects}">
      <tr>
        <td>${subject.cd}</td>
        <td>${subject.name}</td>
        <td><a href="subject_update.jsp?cd=${subject.cd}">変更</a></td>
        <td><a href="subject_delete.jsp?cd=${subject.cd}&name=${subject.name}">削除</a></td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<p><a href="${pageContext.request.contextPath}/main/index.jsp">TOPページへ</a></p>
 </c:param>
</c:import>