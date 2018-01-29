<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<head>
    <meta charset="UTF-8">
    <title>Edit Post</title>
</head>
<script type="text/javascript">
    function OnSubmit()
    {
        if(document.pressed == 'save' || document.pressed == 'delete') {
            document.form1.method ="POST";
        } 
        else if(document.pressed == 'preview' || document.pressed == 'close') {
            document.form1.method ="GET";
        }
        return true;
    }
</script>

<body>
    <form class="w3-container w3-card-4 w3-light-grey" action="post" name="form1" id="form1" onsubmit="return OnSubmit();">
        <h1>Edit Post</h1>
        <input type="hidden" name="username" value=<%= request.getParameter("username") %> >
        <input type="hidden" name="postid" value=<%= request.getParameter("postid") %> >
        <div>
            <button class="w3-btn w3-small w3-round-large w3-ripple w3-white" type="submit" form="form1" onclick="document.pressed=this.value" name="action" value="save">Save</button>
            <button class="w3-btn w3-small w3-round-large w3-ripple w3-white" type="submit" form="form1" onclick="document.pressed=this.value" name="action" value="list">Close</button>
            <button class="w3-btn w3-small w3-round-large w3-ripple w3-white" type="submit" form="form1" onclick="document.pressed=this.value" name="action" value="preview">Preview</button>
            <button class="w3-btn w3-small w3-round-large w3-ripple w3-white" type="submit" form="form1" onclick="document.pressed=this.value" name="action" value="delete">Delete</button>
        </div>
        <div>
            <label for="title">Title</label>
            <input class="w3-input w3-border" type="text" name="title" id="title" value="<%= request.getAttribute("title") == null ? "" : request.getAttribute("title") %>">
        </div>
        <div>
            <label for="body">Body</label>
            <textarea class="w3-input w3-border" style="height: 20rem;" name="body" id="body"><%= request.getAttribute("body") == null ? "" : request.getAttribute("body") %></textarea>
        </div>
    </form>
</body>
</html>
