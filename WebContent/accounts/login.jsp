<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.user}">
    <c:redirect url="/main/index.jsp"/>
</c:if>

<c:import url="/base.jsp">
    <c:param name="title" value="ログイン" />
    <c:param name="body">

        <div class="d-flex justify-content-center">
            <div class="card shadow-sm w-100" style="max-width: 500px;">
                <div class="card-header text-center bg-light py-2">
                    <h2 class="fs-5 m-0">ログイン</h2>
                </div>
                <div class="card-body p-3">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger text-center">${error}</div>
                    </c:if>

                    <form action="login" method="post">
                        <!-- ID -->
                        <div class="form-floating mb-2">
                            <input type="text" class="form-control" id="id" name="id" placeholder="ID" required>
                            <label for="id">ID</label>
                        </div>

                        <!-- パスワード -->
                        <div class="form-floating mb-2">
                            <input type="password" class="form-control" id="password" name="password" placeholder="パスワード" required>
                            <label for="password">パスワード</label>
                        </div>

                        <div class="mb-3 text-center">
                            <div class="form-check d-inline-block">
                                <input class="form-check-input" type="checkbox" id="showPassword" onclick="togglePassword()">
                                <label class="form-check-label small" for="showPassword">
                                    パスワードを表示
                                </label>
                            </div>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary px-4 py-1 mx-auto w-50">ログイン</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <script>
            function togglePassword() {
                const pw = document.getElementById("password");
                pw.type = pw.type === "password" ? "text" : "password";
            }
        </script>

    </c:param>
</c:import>
