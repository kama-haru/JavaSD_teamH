<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <nav>
        <ul class="list-unstyled ms-1">

            <!-- メニュー -->
            <li class="mb-2">
                <a href="${pageContext.request.contextPath}/main"
                   class="text-decoration-none d-block">メニュー</a>
            </li>

            <!-- 学生管理 -->
            <li class="mb-2">
                <a href="${pageContext.request.contextPath}/student/list"
                   class="text-decoration-none d-block">学生管理</a>
            </li>

            <!-- 成績管理 -->
            <li class="mb-1">
                成績管理
                <ul class="list-unstyled mt-2 ms-3">
                    <li class="mb-1">
                        <a href="#" class="text-decoration-none d-block">成績登録</a>
                    </li>
                    <li>
                        <a href="#" class="text-decoration-none d-block">成績参照</a>
                    </li>
                </ul>
            </li>

            <!-- 科目管理 -->
            <li class="mb-2">
                <a href="${pageContext.request.contextPath}/subject/list" class="text-decoration-none d-block">科目管理</a>
            </li>

        </ul>
    </nav>
</div>