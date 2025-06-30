<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>成績登録</title>
  <style>
    body {
      font-family: sans-serif;
      background: #f4f6f8;
      margin: 0;
      padding: 0;
    }
    .container {
      max-width: 1000px;
      margin: 40px auto;
      background: white;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    }
    h2 {
      margin-bottom: 25px;
    }
    form {
      margin-bottom: 30px;
    }
    .row {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
      align-items: center;
      margin-bottom: 20px;
    }
    label {
      min-width: 80px;
      font-weight: bold;
    }
    select, input[type="number"] {
      padding: 6px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    button {
      padding: 8px 20px;
      background-color: #3498db;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #2980b9;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 10px;
      text-align: center;
    }
    th {
      background: #ecf0f1;
    }
    .action-buttons {
      display: flex;
      justify-content: flex-start;
      margin-top: 20px;
    }
  </style>
</head>
<body>
  <div class="container">
    <h2>成績登録</h2>

    <form action="test_regist" method="get">
      <div class="row">
        <label>入学年度</label>
        <select name="entYear">
          <option value="">------</option>
          <c:forEach var="y" items="${entYearList}">
            <option value="${y}" <c:if test="${y == entYear}">selected</c:if>>${y}</option>
          </c:forEach>
        </select>

        <label>クラス</label>
        <select name="classNum">
          <option value="">------</option>
          <c:forEach var="c" items="${classNumList}">
            <option value="${c}" <c:if test="${c == classNum}">selected</c:if>>${c}</option>
          </c:forEach>
        </select>

        <label>科目</label>
        <select name="subjectCd">
          <option value="">------</option>
          <c:forEach var="s" items="${subjectList}">
            <option value="${s.cd}" <c:if test="${s.cd == subjectCd}">selected</c:if>>${s.name}</option>
          </c:forEach>
        </select>

        <label>回数</label>
        <select name="no">
          <option value="">------</option>
          <c:forEach begin="1" end="2" var="i">
            <option value="${i}" <c:if test="${i == no}">selected</c:if>>${i}</option>
          </c:forEach>
        </select>

        <button type="submit">検索</button>
      </div>
    </form>

    <c:if test="${not empty list}">
      <p>科目名：
        <c:forEach var="s" items="${subjectList}">
          <c:if test="${s.cd == subjectCd}">${s.name}</c:if>
        </c:forEach>
        （${no} 回目）
      </p>

      <form action="test_regist_execute" method="post">
        <input type="hidden" name="entYear" value="${entYear}" />
        <input type="hidden" name="classNum" value="${classNum}" />
        <input type="hidden" name="subjectCd" value="${subjectCd}" />
        <input type="hidden" name="no" value="${no}" />

        <table>
          <tr>
            <th>入学年度</th>
            <th>クラス</th>
            <th>学生番号</th>
            <th>氏名</th>
            <th>点数</th>
            <th>削除</th>
          </tr>
          <c:forEach var="t" items="${list}">
            <tr>
              <td>${t.entYear}</td>
              <td>${t.classNum}</td>
              <td>${t.studentNo}</td>
              <td>${t.studentName}</td>
              <td>
                <input type="number" name="point_${t.studentNo}" value="${t.point}" min="0" max="100" required />
              </td>
              <td><input type="checkbox" name="delete" value="${t.studentNo}" /></td>
            </tr>
          </c:forEach>
        </table>

        <div class="action-buttons">
          <button type="submit">登録して終了</button>
        </div>
      </form>
    </c:if>
  </div>
</body>
</html>
