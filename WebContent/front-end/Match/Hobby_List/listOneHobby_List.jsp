<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.hobby_list.model.*"%>
<%
	Hobby_ListVO hobby_listVO = (Hobby_ListVO) request.getAttribute("hobby_listVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<html>
<head>
<meta charset="UTF-8">
<title>興趣資料 - listOneHobby_List.jsp</title>
<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>
</head>
<body bgcolor='white'>
<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>興趣資料 - ListOneHobby_List.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/pic.jpg" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>興趣編號</th>
		<th>興趣</th>
	
	</tr>
	<tr>
		<td><%=hobby_listVO.getHob_code()%></td>
		<td><%=hobby_listVO.getHob()%></td>
	
	</tr>
</table>

</body>
</html>