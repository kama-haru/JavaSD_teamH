<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${ param.title }</title>
</head>
<body>
    <c:import url="/menu.jsp"/>
    <div class="container">
        <div class="sidebar">
            <h3>メニュー</h3>
            <ul>
                <li><a href="#">学生管理</a></li>
                <li>成績管理
                    <ul style="padding-left: 15px; margin-top: 5px;">
                        <li><a href="#">成績登録</a></li>
                        <li><a href="#">成績参照</a></li>
                    </ul>
                </li>
                <li><a href="#">科目管理</a></li>
            </ul>
        </div>
        <div class="main-content">
            ${ param.body }
        </div>
    </div>
</body>
</html>
