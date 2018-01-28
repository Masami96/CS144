<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Preview Post</title>
</head>

<body>
    <div><h1>Preview Post</h1></div>
    <form action="post" name="form1" method="GET" id="form1">
        <input type="hidden" name="username" value=<%= request.getParameter("username") %> >
        <input type="hidden" name="postid" value=<%= request.getParameter("postid") %> >
        <input type="hidden" name="title" value=<%= request.getParameter("title") %> >
        <input type="hidden" name="body" value=<%= request.getParameter("body") %> >
        <div>
            <button type="submit" form="form1" name="action" value="open">Return</button>
        </div>
        <div>
            <h2><%= request.getParameter("title") %></h2>
        </div>
        <div>
            <%= request.getParameter("body") %>
        </div>
    </form>
</body>
</html>
