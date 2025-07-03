<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ログインチェック -->
<c:if test="${empty sessionScope.user}">
  <c:redirect url="/accounts/login.jsp" />
</c:if>

<!-- 科目名 -->
<c:if test="${not empty subjectName}">
  <p class="fw-bold">科目名：${subjectName}</p>
</c:if>

<!-- エラーメッセージ -->
<c:if test="${not empty errorMessage}">
  <div class="alert alert-warning">${errorMessage}</div>
</c:if>

<!-- 成績一覧（科目） -->
<c:if test="${not empty resultList}">
  <table class="table table-bordered table-sm">
    <thead class="table-secondary text-center align-middle">
      <tr>
        <th>入学年度</th>
        <th>クラス</th>
        <th>学生番号</th>
        <th>氏名</th>
        <th>1回</th>
        <th>2回</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="test" items="${resultList}">
        <tr>
          <td class="text-center">${test.entYear}</td>
          <td class="text-center">${test.classNum}</td> <!-- ✅ クラス名表示 -->
          <td class="text-center">${test.studentNo}</td>
          <td>${test.studentName}</td>
          <td class="text-center">
            <c:choose>
              <c:when test="${test.point1 != null && test.point1 > 0}">
                ${test.point1}
              </c:when>
              <c:otherwise>-</c:otherwise>
            </c:choose>
          </td>
          <td class="text-center">
            <c:choose>
              <c:when test="${test.point2 != null && test.point2 > 0}">
                ${test.point2}
              </c:when>
              <c:otherwise>-</c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</c:if>
