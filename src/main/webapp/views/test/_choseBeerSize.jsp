<%--
  Created by IntelliJ IDEA.
  User: thanh
  Date: 12/12/19
  Time: 11:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> TEST BEER</title>
</head>
<body>
<form method="post" action="/test/beer">
    Select beer, sizes:
    <input type="checkbox" name="sizes" value="12oz">12 oz
    <input type="checkbox" name="sizes" value="22oz">22 oz
    <input type="checkbox" name="sizes" value="32oz">32 oz
    <input type="submit" value="SUBMIT ME">
</form>

</body>
</html>
