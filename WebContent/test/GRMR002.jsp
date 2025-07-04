<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ログインチェック -->
<c:if test="${empty sessionScope.user}">
  <c:redirect url="/accounts/LOGI001.jsp" />
</c:if>

<!-- 成績一覧（科目）見出し -->
<h5 class="fw-bold mt-4">成績一覧（科目）</h5>

<!-- 科目名表示 -->
<c:if test="${not empty subjectName}">
  <p class="fw-bold mb-2">科目名：${subjectName}</p>
</c:if>

<!-- エラーメッセージ表示（科目情報が存在しませんでした など） -->
<c:if test="${not empty error}">
  <div class="text-danger small mb-3 ms-1 border-top pt-2">
    <i class="bi bi-exclamation-triangle"></i> ${error}
  </div>
</c:if>

<!-- 成績結果表示 -->
<c:if test="${not empty resultList}">
  <table class="table table-bordered table-sm" style="background-color: transparent;">
    <thead class="text-center align-middle" style="background-color: transparent;">
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
          <td class="text-center">${test.classNum}</td>
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
