<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
   <body>
   		<div align="center" magin-top="110px">
        <table width="274" height="50" border="1">
          <tr>
            <th height="27" scope="row">软件设计文档上传</th>
          </tr>
          <tr>
            <th height="66" scope="row">
			<form name="SightPostSearch" method="post" action="SearchSightPostServlet">
			<table width="100%" border="1">
              <tr>
                <th scope="col">
                  <input type="text" name="sightpost">
				</th>
                <th scope="col">
                  <input type="file" name="浏览"/>
				</th>
              </tr>
              <tr>
              	<th scope="col">
                	<p>查看提取结果</p>
                </th>
                <th scope="col">
                <button>查看</button>
                </th>
              </tr>
            </table>
           </form>  
        </div>
	</body>
  </body>
</html>
