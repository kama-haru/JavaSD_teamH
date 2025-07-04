<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ログインしていない場合、ログイン画面に移動 -->
<c:if test="${empty sessionScope.user}">
  <c:redirect url="/accounts/LOGI001.jsp" />
</c:if>

<!-- ページのタイトル：成績一覧（科目） -->
<h5 class="fw-bold mt-4">成績一覧（科目）</h5>

<!-- 科目名があるとき、表示する -->
<c:if test="${not empty subjectName}">
  <p class="fw-bold mb-2">科目名：${subjectName}</p>
</c:if>

<!-- エラーがあるとき、赤色のメッセージを表示（例：「成績が存在しませんでした」など） -->
<c:if test="${not empty error}">
  <div class="text-danger small mb-3 ms-1 border-top pt-2">
    <i class="bi bi-exclamation-triangle"></i> ${error}
  </div>
</c:if>

<!-- 成績の一覧がある場合、テーブルで表示 -->
<c:if test="${not empty resultList}">
  <table class="table table-bordered table-sm" style="background-color: transparent;">
    <thead class="text-center align-middle" style="background-color: transparent;">
      <tr>
        <th>入学年度</th>    <!-- 学生が入学した年 -->
        <th>クラス</th>      <!-- 学生のクラス -->
        <th>学生番号</th>    <!-- 学生の番号 -->
        <th>氏名</th>        <!-- 学生の名前 -->
        <th>1回</th>         <!-- 1回目のテストの点数 -->
        <th>2回</th>         <!-- 2回目のテストの点数 -->
      </tr>
    </thead>
    <tbody>
      <!-- 各学生の成績を1行ずつ表示 -->
      <c:forEach var="test" items="${resultList}">
        <tr>
          <td class="text-center">${test.entYear}</td>
          <td class="text-center">${test.classNum}</td>
          <td class="text-center">${test.studentNo}</td>
          <td>${test.studentName}</td>

          <!-- 1回目の点数が0以下やnullのときは「-」を表示 -->
          <td class="text-center">
            <c:choose>
              <c:when test="${test.point1 != null && test.point1 > 0}">
                ${test.point1}
              </c:when>
              <c:otherwise>-</c:otherwise>
            </c:choose>
          </td>

          <!-- 2回目の点数が0以下やnullのときは「-」を表示 -->
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
