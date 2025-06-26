<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<c:if test="${empty sessionScope.user}">
    <c:redirect url="/accounts/login.jsp"/>
</c:if>

<c:import url="/base.jsp">
    <c:param name="body">
    <div class="container">
        <h2 class="bg-light p-2 w-100 fw-semibold">科目情報登録</h2><br>

        <div class="card-body">
            <form action="${pageContext.request.contextPath}/SubjectCreateExecuteController" method="post">

                <div class="mb-3">
                    <label for="cd" class="form-label">科目コード</label>
                    <input type="text" class="form-control" id="cd" name="cd"
                        value="${fn:escapeXml(cd)}"
                        placeholder="科目コードを入力してください" required>
                    <c:if test="${not empty errorCd}">
                        <div class="text-danger">${errorCd}</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label for="name" class="form-label">科目名</label>
                    <input type="text" class="form-control" id="name" name="name"
                        value="${fn:escapeXml(name)}"
                        placeholder="科目名を入力してください" required>
                    <c:if test="${not empty errorName}">
                        <div class="text-danger">${errorName}</div>
                    </c:if>
                </div>

                <div class="d-flex mb-2">
                    <button type="submit" class="btn btn-primary">登録</button>
                </div>
                <div class="d-flex">
                    <a href="${pageContext.request.contextPath}/subject/list">戻る</a>
                </div>

            </form>
        </div>
    </div>
 </c:param>
</c:import>
