<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Post</title>
</head>
<script type="text/javascript">
    function OnSubmit()
    {
      if(document.pressed == 'save' || document.pressed == 'delete')
      {
       document.form1.method ="POST";
      }
      else
      if(document.pressed == 'preview' || document.pressed == 'close')
      {
        document.form1.method ="GET";
      }
      return true;
    }
</script>

<body>
    <div><h1>Edit Post</h1></div>
    <form action="post" name="form1" id="form1" onsubmit="return OnSubmit();">
        <input type="hidden" name="username" value=<%= request.getParameter("username") %> >
        <input type="hidden" name="postid" value=<%= request.getParameter("postid") %> >
        <div>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="action" value="save">Save</button>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="action" value="list">Close</button>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="action" value="preview">Preview</button>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="action" value="delete">Delete</button>
        </div>
        <div>
            <label for="title">Title</label>

            <input type="text" name="title" id="title" value="<%= request.getAttribute("title") %>">
        </div>
        <div>
            <label for="body">Body</label>
            <textarea style="height: 20rem;" name="body" id="body"><%= request.getAttribute("body") %></textarea>
        </div>
    </form>
</body>
</html>
