<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Preview Post</title>
</head>
<script type="text/javascript">
    function OnSubmit()
    {
      if(document.pressed == 'Return')
      {
       document.form1.action ="post";
      }
    }
</script>

<body>
    <div><h1>Preview Post</h1></div>
    <form name="form1" method="GET" id="form1" onsubmit="return OnSubmit();">
        <div>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="buttonType" value="Return">Return</button>
        </div>
        <div>
            <label for="title">Title</label>
            <input type="text" name="title" id="title">
        </div>
        <div>
            <label for="body">Body</label>
            <textarea style="height: 20rem;" name="body" id="body"></textarea>
        </div>
    </form>
</body>
</html>
