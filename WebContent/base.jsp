<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>${param.title}</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
        crossorigin="anonymous">
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
        crossorigin="anonymous"></script>
</head>
<body class="container py-2 px-5">

    <header class="bg-primary-subtle border-bottom py-3 px-4 d-flex justify-content-between align-items-center mb-4">
        <h1 class="fs-2 m-0">得点管理システム</h1>
        <div class="user-info mt-4">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <span>${sessionScope.user.name} 様</span>
                    <a class="text-decoration-none text-primary" href="${pageContext.request.contextPath}/logout">ログアウト</a>
                </c:when>
            </c:choose>
        </div>
    </header>

    <div class="row ms-2">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <div class="col-md-2">
                    <c:import url="/menu.jsp" />
                </div>
                <div class="col-auto d-flex justify-content-center">
                    <div style="width: 1px; background-color: rgba(0,0,0,0.1); height: 100%;"></div>
                </div>
                <div class="col-md-9 me-auto ms-auto">
                    ${param.body}
                </div>
            </c:when>
            <c:otherwise>
                <div class="col-12">
                    ${param.body}
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <footer class="bg-secondary-subtle border-top text-center py-3 px-4 mt-4">
        <p class="mb-1">© 2023 TIC</p>
        <p class="mb-0">大原学園</p>
    </footer>

</body>
</html>
