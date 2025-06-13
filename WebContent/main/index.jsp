<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("/accounts/login.jsp");
        return;
    }
%>

<c:import url="/base.jsp">
    <c:param name="title" value="ホーム" />
    <c:param name="body">
        <style>
            .main-content h2 { background-color: #f0f0f0; padding: 10px; margin-top: 0; }
            .menu-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; }
            .menu-item { padding: 20px; text-align: center; border-radius: 5px; }
            .menu-item h3 { margin-top: 0; }
            .menu-item ul { list-style: none; padding: 0; text-align: left; margin-top: 10px; }
            .student { background-color: #f8d7da; }
            .grades { background-color: #d4edda; }
            .subjects { background-color: #d1c4e9; }
        </style>

        <div class="main-content">
            <h2>① メニュー</h2>
            <div class="menu-grid">
                <div class="menu-item student">
                    <h3>② 学生管理</h3>
                </div>
                <div class="menu-item grades">
                    <h3>③ 成績管理</h3>
                    <ul>
                        <li>④ 成績登録</li>
                        <li>⑤ 成績参照</li>
                    </ul>
                </div>
                <div class="menu-item subjects">
                    <h3>⑥ 科目管理</h3>
                </div>
            </div>
        </div>
    </c:param>
</c:import>
