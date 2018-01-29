<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<head>
    <meta charset="UTF-8">
    <title>Preview Post</title>
</head>

<body class="w3-padding-16">
    <div class="w3-margin w3-light-grey w3-padding-16">
        <div class="w3-margin">
            <h2><%= request.getParameter("title") %></h2>
        </div>
        <div class="w3-margin">
            <%= request.getParameter("body") %>
        </div>
    </div>
    <form action="post" name="form1" method="GET" id="form1">
        <input type="hidden" name="username" value=<%= request.getParameter("username") %> >
        <input type="hidden" name="postid" value=<%= request.getParameter("postid") %> >
        <input type="hidden" name="title" value=<%= request.getParameter("title") %> >
        <input type="hidden" name="body" value=<%= request.getParameter("body") %> >
        <div>
            <button class="w3-margin w3-btn w3-small w3-round-large w3-ripple w3-gray" type="submit" form="form1" name="action" value="open">Return</button>
        </div>
    </form>
</body>
</html>
