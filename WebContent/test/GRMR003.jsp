<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ログインしていない場合、ログイン画面へ移動 -->
<c:if test="${empty sessionScope.user}">
  <c:redirect url="/accounts/LOGI001.jsp" />
</c:if>

<!-- タイトル：学生の成績一覧 -->
<h5 class="fw-bold mt-4">成績一覧（学生）</h5>

<!-- 学生の氏名と学生番号を表示 -->
<c:if test="${not empty studentName}">
  <p>氏名：${studentName}（${studentNo}）</p>
</c:if>

<!-- 成績が見つからなかったときのメッセージ -->
<c:if test="${empty studentResults}">
  <p class="text-muted">成績情報が見つかりませんでした。</p>
</c:if>

<!-- 成績があるときは、表で表示 -->
<c:if test="${not empty studentResults}">
  <table class="table table-bordered text-center" style="background-color: transparent;">
    <thead style="background-color: transparent;">
      <tr>
        <th>科目名</th>       <!-- 例：数学、日本語など -->
        <th>科目コード</th>   <!-- 例：SUB001 など -->
        <th>回数</th>         <!-- 1回目か2回目か -->
        <th>点数</th>         <!-- テストの点数 -->
      </tr>
    </thead>
    <tbody>
      <!-- 成績データを1件ずつ表示 -->
      <c:forEach var="t" items="${studentResults}">
        <tr>
          <td>${t.subjectName}</td>
          <td>${t.subjectCd}</td>
          <td>${t.no}</td>
          <td>
            <!-- 点数が0または未登録(null)のときは「-」を表示 -->
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
