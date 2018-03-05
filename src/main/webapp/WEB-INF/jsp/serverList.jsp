<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangxu
  Date: 2018/1/5
  Time: 上午8:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务器列表</title>
</head>
<body>
<table border="1">
    <tr>
        <th>IP</th>
        <th>用户名</th>
        <th>密码</th>
        <th>描述</th>

    </tr>
<c:forEach items="${serverList}" var="server">

        <tr>
            <td>${server.ip}</td>
            <td>${server.userName}</td>
            <td>${server.password}</td>
            <td>${server.description}</td>
        </tr>


</c:forEach>
</table>
</body>
</html>
