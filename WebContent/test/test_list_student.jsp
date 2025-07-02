<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">
  <c:param name="body">
    <div class="container mt-4">
      <h2>学生別成績一覧</h2>

      <!-- メッセージ -->
      <c:if test="${not empty message}">
        <div class="alert alert-warning">${message}</div>
      </c:if>

      <!-- 結果表示 -->
      <c:if test="${not empty testList}">
        <table class="table table-bordered">
          <thead>
            <tr>
              <th>氏名</th>
              <th>科目名</th>
              <th>科目コード</th>
              <th>回数</th>
              <th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="t" items="${testList}">
              <tr>
                <td>${t.studentName}</td>
                <td>${t.subjectName}</td>
                <td>${t.subjectCd}</td>
                <td>${t.no}</td>
                <td>${t.point}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

      <a href="/test/test_list" class="btn btn-secondary">戻る</a>
    </div>
  </c:param>
</c:import>
