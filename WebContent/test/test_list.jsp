<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>成績参照</title>
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
      padding: 30px 40px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    h2 {
      font-size: 20px;
      margin-bottom: 20px;
    }
    .section-title {
      font-weight: bold;
      margin-top: 20px;
      margin-bottom: 10px;
    }
    .form-row {
      display: flex;
      align-items: center;
      gap: 20px;
      margin-bottom: 10px;
    }
    label {
      white-space: nowrap;
    }
    select, input[type="text"] {
      padding: 6px;
      width: 150px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    button {
      padding: 6px 16px;
      background-color: #007BFF;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #0056b3;
    }
    .message {
      color: red;
      margin: 10px 0;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 15px;
    }
    th, td {
      border: 1px solid #ccc;
      padding: 8px;
      text-align: center;
    }
    th {
      background-color: #e0e0e0;
    }
    .note {
      font-size: 12px;
      color: #007BFF;
      margin-top: 10px;
    }
  </style>
</head>
<body>
  <div class="container">
    <h2>成績参照</h2>

    <!-- 科目情報による検索 -->
    <form action="test_list_subject" method="post">
      <div class="section-title">科目情報</div>
      <div class="form-row">
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

        <button type="submit">検索</button>
      </div>

      <c:if test="${not empty errorSubject}">
        <div class="message">${errorSubject}</div>
      </c:if>
    </form>

    <!-- 学生情報による検索 -->
    <form action="test_list_student" method="post">
      <div class="section-title">学生情報</div>
      <div class="form-row">
        <label>学生番号</label>
        <input type="text" name="studentNo" value="${studentNo}" />
        <button type="submit">検索</button>
      </div>

      <c:if test="${not empty errorStudent}">
        <div class="message">${errorStudent}</div>
      </c:if>
    </form>

    <!-- 案内メッセージ：初期状態のみ表示 -->
    <c:if test="${empty listSubject and empty listStudent and empty errorSubject and empty errorStudent and empty message}">
      <div class="note">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</div>
    </c:if>

    <!-- メッセージ -->
    <c:if test="${not empty message}">
      <div class="message">${message}</div>
    </c:if>

    <!-- ▼ 検索結果：科目別成績 -->
    <c:if test="${not empty listSubject}">
      <c:if test="${not empty subjectName}">
        <div>科目：${subjectName}</div>
      </c:if>
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
          <c:forEach var="t" items="${listSubject}">
            <tr>
              <td>${t.entYear}</td>
              <td>${t.classNum}</td>
              <td>${t.studentNo}</td>
              <td>${t.studentName}</td>
              <td>${t.point1}</td>
              <td>${t.point2}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:if>

    <!-- ▼ 検索結果：学生別成績 -->
    <c:if test="${not empty listStudent}">
      <div class="section-title">学生別成績結果</div>
      <c:if test="${not empty studentName}">
        <div>氏名：${studentName}（${studentNo}）</div>
      </c:if>
      <table>
        <thead>
          <tr>
            <th>科目名</th>
            <th>科目コード</th>
            <th>回数</th>
            <th>点数</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="t" items="${listStudent}">
            <tr>
              <td>${t.subjectName}</td>
              <td>${t.subjectCd}</td>
              <td>${t.no}</td>
              <td>${t.point}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:if>
  </div>
</body>
</html>
