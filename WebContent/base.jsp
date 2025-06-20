<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<body class="pt-5 pb-4">

	<header class="bg-primary-subtle border-bottom position-fixed top-0 start-0 end-0 py-3 px-4 d-flex justify-content-between align-items-center z-3">
        <h1 class="fs-5 m-0">得点管理システム</h1>
		<div class="user-info">
			<c:choose>
				<c:when test="${not empty sessionScope.user}">
					<span>${sessionScope.user.name} 様</span>
					<a class="text-decoration-none text-primary"
						href="${pageContext.request.contextPath}/logout">ログアウト</a>
				</c:when>
			</c:choose>
		</div>
	</header>

	<div class="container-fluid">
        <div class="row min-vh-100">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <aside class="col-md-3 border-end p-3">
                <c:import url="/menu.jsp" />
            </aside>
                    <main class="col-md-9 p-4">
                <c:out value="${param.body}" escapeXml="false" />
            </main>
                </c:when>
            </c:choose>
        </div>
    </div>

	<footer class="bg-secondary-subtle border-top text-center py-3 px-4 position-fixed bottom-0 start-0 end-0">
        <p class="mb-1">© 2023 TIC</p>
        <p class="mb-0">大原学園</p>
    </footer>
</body>
</html>
