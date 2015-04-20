<%--
  Created by IntelliJ IDEA.
  User: yoon
  Date: 15. 4. 14.
  Time: 오전 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>DUO - Article</title>
    <link rel="stylesheet" href="/stylesheet/list.css"/>
    <link rel="stylesheet" href="/stylesheet/reset.css"/>
</head>
<body>
<h1>Board List</h1>

<div id="container">
    <div id="inner-container">
        <h1>글목록</h1>
        <ol class="board">
            <c:forEach var="article" items="${articles}">
                <li>${article.title}</li>
            </c:forEach>
        </ol>
    </div>
</div>
</body>
</html>
