<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>成績一覧（科目）</title>
  <style>
    body {
      font-family: sans-serif;
      background-color: #f2f2f2;
      margin: 0;
      padding: 0;
    }
    .container {
      max-width: 960px;
      margin: 30px auto;
      background-color: #fff;
      padding: 25px 30px;
      border-radius: 8px;
      box-shadow: 0 0 8px rgba(0,0,0,0.1);
    }
    h2 {
      margin-top: 0;
      margin-bottom: 20px;
      font-size: 20px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 15px;
    }
    th, td {
      border: 1px solid #ccc;
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #e0e0e0;
    }
    .message {
      color: red;
      margin-top: 10px;
    }
  </style>
</head>
<body>
  <div class="container">
    <h2>成績一覧（科目）</h2>

    <!-- エラーメッセージ表示 -->
    <c:if test="${not empty errorSubject}">
      <p class="message">${errorSubject}</p>
    </c:if>

    <!-- 成績結果表示 -->
    <c:if test="${not empty listSubject}">
      <table>
        <thead>
          <tr>
            <th>入学年度</th>
            <th>学科</th>
            <th>クラス</th>
            <th>学籍番号</th>
            <th>氏名</th>
            <th>1回目</th>
            <th>2回目</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="t" items="${listSubject}">
            <tr>
              <td>${t.entYear}</td>
              <td>${t.departmentName}</td>
              <td>${t.classNum}</td>
              <td>${t.studentNo}</td>
              <td>${t.studentName}</td>
              <td>
                <c:if test="${t.no == 1}">
                  ${t.point}
                </c:if>
              </td>
              <td>
                <c:if test="${t.no == 2}">
                  ${t.point}
                </c:if>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:if>

    <!-- 該当なしメッセージ -->
    <c:if test="${not empty messageSubject}">
      <p class="message">${messageSubject}</p>
    </c:if>

  </div>
</body>
</html>
