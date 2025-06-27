<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>成績登録完了</title>
  <style>
body {
  font-family: sans-serif;
  background: #f4f6f8;
  margin: 0;
  padding: 0;
}
.banner {
  background-color: #27ae60;
  color: white;
  padding: 24px 0;
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  letter-spacing: 1px;
}
.container {
  max-width: 600px;
  margin: 100px auto;
  background: white;
  padding: 50px 30px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}
.message {
  font-size: 20px;
  color: #2c3e50;
  margin-bottom: 20px;
}
  </style>
</head>
<body>
  <div class="banner">登録完了しました</div>

  <div class="container">
    <div class="link-group">
      <a href="test_regist">戻る</a>
      <a href="test">成績参照</a>
    </div>
  </div>
</body>
</html>