<%--
  Created by IntelliJ IDEA.
  User: yoon
  Date: 15. 4. 14.
  Time: 오전 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new Post</title>
</head>
<body>
<h1>Create new Post</h1>
<form method="post", action="/article">
  <input type="text" name="title" id="title" placeholder="제목" /> <br/>
  <textarea name="content" rows="4" cols="30" placeholder="내용"></textarea> <br/>
  <input type="submit"/>
</form>
</body>
</html>
