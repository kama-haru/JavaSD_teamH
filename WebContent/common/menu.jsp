<%@ page contentType="text/html; charset=UTF-8" %>
<!-- ① サイドバー -->
<div class="sidebar">
    <!-- ② ナビゲーション -->
    <nav>
        <ul>
            <!-- ③ メニュー -->
            <a href="menu.jsp" name="menu">メニュー</a>
            <!-- 学生管理 -->
            <li>
                <a href="student_list.jsp" name="student">学生管理</a>
            </li>
            <!-- 成績管理 -->
            <li>成績管理</li>
            <ul>
                <li>
                    <a href="test_create.jsp" name="testCreate">成績登録</a>
                </li>
                <li>
                    <a href="test_.jsp" name="scoreRef">成績参照</a>
                </li>
            </ul>
            <!-- 科目管理 -->
            <li>
                <a href="subject_list.jsp" name="subjectList">科目管理</a>
            </li>
        </ul>
    </nav>
</div>
