<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
      font-size: 20px;
      margin: 0 0 10px 0;
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
    .subject-label {
      font-weight: bold;
      margin-bottom: 10px;
    }
  </style>
</head>
<body>
  <div class="container">
    <h2>成績一覧（科目）</h2>

    <!-- 科目名 -->
    <c:if test="${not empty subjectName}">
      <p class="subject-label">科目：${subjectName}</p>
    </c:if>

    <!-- エラー -->
    <c:if test="${not empty error}">
      <p class="message">${error}</p>
    </c:if>

    <!-- メッセージ -->
    <c:if test="${not empty message}">
      <p class="message">${message}</p>
    </c:if>

    <!-- 成績表示 -->
    <c:if test="${not empty list}">
      <table>
        <thead>
          <tr>
            <th>入学年度</th>
            <th>クラス</th>
            <th>学籍番号</th>
            <th>氏名</th>
            <th>1回</th>
            <th>2回</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="i" begin="0" end="${fn:length(list) - 1}">
            <c:set var="current" value="${list[i]}" />
            <c:if test="${current.no == 1}">
              <tr>
                <td>${current.entYear}</td>
                <td>${current.classNum}</td>
                <td>${current.studentNo}</td>
                <td>${current.studentName}</td>

                <!-- 1回 -->
                <td>
                  <c:choose>
                    <c:when test="${current.point == null || current.point == 0}">-</c:when>
                    <c:otherwise>${current.point}</c:otherwise>
                  </c:choose>
                </td>

                <!-- 2回 -->
                <td>
                  <c:set var="point2" value="-" />
                  <c:forEach var="j" begin="${i+1}" end="${fn:length(list) - 1}">
                    <c:if test="${list[j].studentNo == current.studentNo && list[j].no == 2}">
                      <c:choose>
                        <c:when test="${list[j].point == null || list[j].point == 0}">
                          <c:set var="point2" value="-" />
                        </c:when>
                        <c:otherwise>
                          <c:set var="point2" value="${list[j].point}" />
                        </c:otherwise>
                      </c:choose>
                      <c:break />
                    </c:if>
                  </c:forEach>
                  ${point2}
                </td>
              </tr>
            </c:if>
          </c:forEach>
        </tbody>
      </table>
    </c:if>
  </div>
</body>
</html>
