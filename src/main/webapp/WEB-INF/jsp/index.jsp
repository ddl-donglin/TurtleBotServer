<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    上传配件图片：<br/>
    <form action="/upload/imageFile" method="post"
    enctype="multipart/form-data">
    	image:<input type="file" name="image" value="" /><br/>
        filename:<input type="text" name="wantedFilename" value="" /><br/>
    	<input type="submit" value="submit" />
    </form>

    <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
        tag:<input type="text" name="tag"/><br/>
        name:<input type="text" name="name"/><br/>
        email:<input type="text" name="email"/><br/>
        password:<input type="text" name="password"/><br/>

        <input type="submit" value="submit" />
    </form>
    
    <hr>
  </body>
</html>
