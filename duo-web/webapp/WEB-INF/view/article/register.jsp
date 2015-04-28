<%--
  Created by IntelliJ IDEA.
  User: yoon
  Date: 15. 4. 14.
  Time: 오전 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>DUO - Article - Registeration</title>
    <link rel="stylesheet" href="/stylesheet/common.css"/>
    <link rel="stylesheet" href="/stylesheet/register.css"/>
    <link rel="stylesheet" href="/stylesheet/reset.css"/>
</head>
<body>
<div id="container">
    <div id="inner-container">
        <h1>새글쓰기</h1>
        <c:if test="${not empty errorMessage}">
            <div class="errorMessage">
                ${errorMessage}
            </div>
        </c:if>
        <form action="/article" method="post">
            <input type="title" name="text" placeholder="제목"/>
            <textarea rows="10" name="content" placeholder="내용"></textarea>
            <input type="submit" content="작성"/>
        </form>
        <a href="/article/list">글목록</a>
    </div>
</div>

</body>
</html>
