<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.user}">
  <c:redirect url="/accounts/LOGI001.jsp" />
</c:if>

<h5 class="fw-bold mt-4">成績一覧（学生）</h5>

<c:if test="${not empty studentName}">
  <p>氏名：${studentName}（${studentNo}）</p>
</c:if>

<c:if test="${empty studentResults}">
  <p class="text-muted">成績情報が見つかりませんでした。</p>
</c:if>

<c:if test="${not empty studentResults}">
  <table class="table table-bordered text-center" style="background-color: transparent;">
    <thead style="background-color: transparent;">
      <tr>
        <th>科目名</th>
        <th>科目コード</th>
        <th>回数</th>
        <th>点数</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="t" items="${studentResults}">
        <tr>
          <td>${t.subjectName}</td>
          <td>${t.subjectCd}</td>
          <td>${t.no}</td>
          <td>
            <c:choose>
              <c:when test="${t.point == 0 || t.point == null}">-</c:when>
              <c:otherwise>${t.point}</c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</c:if>