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
    <link rel="stylesheet" href="/stylesheet/common.css"/>
    <link rel="stylesheet" href="/stylesheet/reset.css"/>
</head>
<body>
<div id="container">
    <div id="inner-container">
        <h1>글목록</h1>
        <c:if test="{not empty errorMessage}">
            <div class="errorMessage">
                ${errorMessage}
            </div>
        </c:if>
        <ol class="board">
            <c:forEach var="article" items="${articles}">
                <li>
                    <a href="/article/${article.id}">
                    <div class="row">
                        <p class="title">> ${article.title}</p>
                        <p class="time">${article.createdTime}</p>
                    </div>
                    </a>
                </li>
            </c:forEach>
        </ol>
        <ol class="pagination">
            <c:choose>
                <c:when test="${currentPage == 1}">
                    <li class="disabled"><a href="#">&lt;&lt;</a></li>
                    <li class="disabled"><a href="#">&lt;</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/article/list?page=1">&lt;&lt;</a></li>
                    <li><a href="/article/list?page=${currentPage-5}">&lt;</a></li>
                </c:otherwise>
            </c:choose>
            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                <c:url var="pageUrl" value="/article/list?page=${i}" />
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <li class="active"><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${currentPage >= totalEndPage-5}">
                    <li class="disabled"><a href="#">&gt;</a></li>
                    <li class="disabled"><a href="#">&gt;&gt;</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/article/list?page=${currentPage + 5}">&gt;</a></li>
                    <li><a href="/article/list?page=${totalEndPage}">&gt;&gt;</a></li>
                </c:otherwise>
            </c:choose>
        </ol>
    </div>
</div>
</body>
</html>
