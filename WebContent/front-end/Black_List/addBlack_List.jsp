<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.black_list.model.*" %>

<%
	Black_ListVO black_listVO = (Black_ListVO) request.getAttribute("black_listVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>addBlack_list 加入黑名單</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr>
	<td><h3>黑名單 - addBlack_List.jsp</h3></td>
	<td><h4><a href="select_page.jsp"><img src="images/pic.jpg" width="100" height="100" border="0">回首頁</a></h4></td>
	</tr>

</table>

<h3>新增資料</h3>

<c:if test="${not empty errorMsgs}">
<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Black_List/black_list.do" name="form1">
<table>
	

	<tr>
		<td>黑人的:</td>
		<td><input type="TEXT" name="blk_memid" size="45"
			 value="<%= (black_listVO==null)? "MEM0000001" : black_listVO.getBlk_memid()%>" /></td>
	</tr>
	<tr>
		<td>被黑:</td>
		<td><input type="TEXT" name="beblk_memid" size="45"
			 value="<%= (black_listVO==null)? "MEM0000002" : black_listVO.getBeblk_memid()%>" /></td>
	</tr>

	
	
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>































</body>
</html>