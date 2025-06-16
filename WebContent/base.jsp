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
            <nav>
                <ul>
                    <li class="current"><a href="${pageContext.request.contextPath}/main/index.jsp">メニュー</a></li>
                    <li class="current"><a href="#">学生管理</a></li>
                <li>成績管理
                    <ul style="padding-left: 15px; margin-top: 5px;">
                        <li class="current"><a href="#">成績登録</a></li>
                        <li class="current"><a href="#">成績参照</a></li>
                    </ul>
                </li>
                	<li class="current"><a href="#">科目管理</a></li>
            	</ul>
            </nav>
        </div>
        <div class="main-content">
            ${ param.body }
        </div>
    </div>
</body>
</html>
