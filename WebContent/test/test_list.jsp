<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.user}">
  <c:redirect url="/accounts/login.jsp" />
</c:if>

<c:import url="/base.jsp">
  <c:param name="title" value="成績参照" />
  <c:param name="body">
    <div class="container my-5">
      <div class="bg-light border p-2 ps-3 mb-4">
        <h2 class="mb-0">成績参照</h2>
      </div>

      <div class="card p-4 mb-4">
        <form action="test_list" method="get" class="mb-4">
          <!-- ■科目情報 -->
          <div class="row g-3 align-items-center mb-2">
            <div class="col-auto">
              <div class="fw-bold">科目情報</div>
            </div>

            <div class="col-auto">
              <label class="form-label mb-0">入学年度</label>
              <select name="entYear" class="form-select form-select-sm" style="width: 110px;">
                <option value="">--</option>
                <c:forEach var="year" items="${entYearList}">
                  <option value="${year}" ${param.entYear == year ? 'selected' : ''}>${year}</option>
                </c:forEach>
              </select>
            </div>

            <div class="col-auto">
              <label class="form-label mb-0">クラス</label>
              <select name="classNum" class="form-select form-select-sm" style="width: 110px;">
                <option value="">--</option>
                <c:forEach var="cls" items="${classNumList}">
                  <option value="${cls}" ${param.classNum == cls ? 'selected' : ''}>${cls}</option>
                </c:forEach>
              </select>
            </div>

            <div class="col-auto">
              <label class="form-label mb-0">科目</label>
              <select name="subjectCd" class="form-select form-select-sm" style="width: 170px;">
                <option value="">--</option>
                <c:forEach var="sub" items="${subjectList}">
                  <option value="${sub.cd}" ${param.subjectCd == sub.cd ? 'selected' : ''}>${sub.name}</option>
                </c:forEach>
              </select>
            </div>

            <div class="col-auto">
              <button type="submit" name="mode" value="subject" class="btn btn-secondary btn-sm">検索</button>
            </div>
          </div>

          <!-- ■学生情報 -->
          <div class="row g-3 align-items-center">
            <div class="col-auto">
              <div class="fw-bold">学生情報</div>
            </div>

            <div class="col-auto">
              <label class="form-label mb-0">学生番号</label>
              <input type="text" name="studentNo" class="form-control form-control-sm" value="${param.studentNo}" style="width: 235px;" />
            </div>

            <div class="col-auto">
              <button type="submit" name="mode" value="student" class="btn btn-secondary btn-sm">検索</button>
            </div>
          </div>
        </form>

        <!-- エラーメッセージ -->
        <c:if test="${not empty errorMessage}">
          <div class="text-warning small mt-1">${errorMessage}</div>
        </c:if>
      </div>

      <!-- 結果表示 -->
      <c:choose>
        <c:when test="${mode == 'subject'}">
          <c:if test="${empty errorMessage}">
            <jsp:include page="test_list_subject.jsp" />
          </c:if>
        </c:when>
        <c:when test="${mode == 'student'}">
          <c:if test="${empty errorMessage}">
            <jsp:include page="test_list_student.jsp" />
          </c:if>
        </c:when>
      </c:choose>
    </div>
  </c:param>
</c:import>