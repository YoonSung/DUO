<%--
  Created by IntelliJ IDEA.
  User: yoon
  Date: 15. 4. 21.
  Time: 오후 1:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>DUO - Article - DetailView </title>
  <link rel="stylesheet" href="/stylesheet/detail.css"/>
  <link rel="stylesheet" href="/stylesheet/common.css"/>
  <link rel="stylesheet" href="/stylesheet/reset.css"/>
</head>
<body>
<div id="container">
  <div id="inner-container">
    <h1>글상세</h1>
    <c:if test="{not empty errorMessage}">
      <div class="errorMessage">
          ${errorMessage}
      </div>
    </c:if>

    <div class="article">
      <div class="info">
        <p>
          <span>글쓴이 :</span>
          ${article.author.name}
        </p>

        <p>
          <span>날짜 :</span>
          ${article.createdTime}
        </p>
        <p></p>
      </div>
      <div class="spliter"/>
      <div class="title">
        <span >제목 : </span>
        ${article.title}
      </div>

      <div class="spliter"/>
      <div class="content">
        ${article.content}
      </div>

      <div class="comment">
      </div>
    </div>
  </div>
</div>
</body>
</html>

