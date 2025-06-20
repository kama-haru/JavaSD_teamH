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


            <!-- ① Thanh tiêu đề -->
            <div class="bg-light border p-2 ps-3 mb-4">
                <span>メニュー</span>
            </div>

            <!-- ② Hàng chứa 3 mục chức năng -->
            <div class="row g-4">

                <!-- 学生管理 -->
                <div class="col-md-4">
                    <div class="p-4 rounded-3 shadow-sm h-100" style="background-color: #e6cccc;">
                        <a href="${pageContext.request.contextPath}/student/student_list.jsp" class="fs-5 text-decoration-underline">学生管理</a>
                    </div>
                </div>

                <!-- 成績管理 -->
                <div class="col-md-4">
                    <div class="p-4 rounded-3 shadow-sm h-100" style="background-color: #d9ead3;">
                        <h5 class="fw-normal mb-3">成績管理</h5>
                        <a href="#" class="fs-5 text-decoration-underline d-block mb-2">成績登録</a>
                        <a href="#" class="fs-5 text-decoration-underline d-block">成績参照</a>
                    </div>
                </div>

                <!-- 科目管理 -->
                <div class="col-md-4">
                    <div class="p-4 rounded-3 shadow-sm h-100" style="background-color: #d9d2e9;">
                        <a href="#" class="fs-5 text-decoration-underline">科目管理</a>
                    </div>
                </div>

            </div>
        </div>
    </c:param>
</c:import>
