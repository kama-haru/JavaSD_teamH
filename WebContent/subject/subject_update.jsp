<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.SubjectDao" %>
<%@ page import="bean.Subject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String cd = request.getParameter("cd");
    SubjectDao dao = new SubjectDao();
    Subject subject = dao.getSubject(cd);

    if (subject == null) {
        request.setAttribute("errorCd", "科目コードが存在しません。");
    }

    request.setAttribute("cd", cd);
    request.setAttribute("name", subject != null ? subject.getName() : "");
%>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<c:import url="/base.jsp">
  <c:param name="body">
    <div class="container">
      <h2 class="bg-light p-2 fw-semibold">科目情報変更</h2>

      <form action="${pageContext.request.contextPath}/subject/SubjectUpdateExecuteController" method="post">

        <div class="mb-3">
          <label class="form-label">科目コード</label>
          <span class="form-control-plaintext">${cd}</span>
          <input type="hidden" name="cd" value="${cd}">
          <c:if test="${not empty errorCd}">
            <div class="text-danger">${errorCd}</div>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="name" class="form-label">科目名</label>
          <input type="text" class="form-control" id="name" name="name" value="${fn:escapeXml(name)}" required maxlength="30">
        </div>

        <div class="mb-2">
          <button type="submit" class="btn btn-primary">変更</button>
        </div>

        <div>
          <a href="${pageContext.request.contextPath}/subject/list">戻る</a>
        </div>
      </form>
    </div>
  </c:param>
</c:import>
