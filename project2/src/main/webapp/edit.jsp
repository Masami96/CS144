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
      if(document.pressed == 'Save' || document.pressed == 'Close' || document.pressed == 'Delete')
      {
       document.form1.action ="list";
      }
      else
      if(document.pressed == 'Preview')
      {
        document.form1.action ="preview";
      }
      return true;
    }
</script>

<body>
    <div><h1>Edit Post</h1></div>
    <form name="form1" method="GET" id="form1" onsubmit="return OnSubmit();">
        <div>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="buttonType" value="Save">Save</button>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="buttonType" value="Close">Close</button>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="buttonType" value="Preview">Preview</button>
            <button type="submit" form="form1" onclick="document.pressed=this.value" name="buttonType" value="Delete">Delete</button>
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
