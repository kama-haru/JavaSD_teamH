<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">
  <c:param name="body">
    <div class="container mt-4">
      <h2>科目別成績一覧</h2>

      <!-- メッセージ表示 -->
      <c:if test="${not empty message}">
        <div class="alert alert-warning">${message}</div>
      </c:if>

      <!-- 成績表示テーブル -->
      <c:if test="${not empty testList}">
        <table class="table table-bordered">
          <thead>
            <tr>
              <th>学生番号</th>
              <th>氏名</th>
              <th>1回目点数</th>
              <th>2回目点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="t" items="${testList}">
              <tr>
                <td>${t.studentNo}</td>
                <td>${t.studentName}</td>
                <td>
                  <c:choose>
                    <c:when test="${t.point1 != 0}">${t.point1}</c:when>
                    <c:otherwise>－</c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <c:choose>
                    <c:when test="${t.point2 != 0}">${t.point2}</c:when>
                    <c:otherwise>－</c:otherwise>
                  </c:choose>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

      <a href="/test/test_list" class="btn btn-secondary">戻る</a>
    </div>
  </c:param>
</c:import>
