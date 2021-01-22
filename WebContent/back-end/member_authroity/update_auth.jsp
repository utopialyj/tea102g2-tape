<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member_authroity.model.*"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  
<%
  Member_AuthroityVO member_authroityVO = (Member_AuthroityVO) request.getAttribute("member_authroityVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>權限資料修改</title>

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
	<tr><td>
		 <h3>權限資料修改 - update_auth.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/member_authroity/auth_select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>權限資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member_AuthroityServlet" name="form1">
<table>
	<tr>
		<td>權限等級:<font color=red><b>*</b></font></td>
		<td><%=member_authroityVO.getVerify_level()%></td>
		<input type="hidden" name="verify_level" value="${member_authroityVO.verify_level}"/>
	</tr>
	<tr>
		<td>聊天權限:</td>
		<td><input type="TEXT" name="chat_auth" size="45" value="<%=member_authroityVO.getChat_auth()%>" /></td>
	</tr>
	<tr>
		<td>動態權限:</td>
		<td><input type="TEXT" name="post_auth" size="45"	value="<%=member_authroityVO.getPost_auth()%>" /></td>
	</tr>
	<tr>
		<td>配對權限:</td>
		<td><input type="Text" name="meat_auth" size="45"	value="<%=member_authroityVO.getMeat_auth()%>" /></td>
	</tr>
	<tr>
		<td>點數權限:</td>
		<td><input type="TEXT" name="point_auth" size="45"	value="<%=member_authroityVO.getPoint_auth()%>" /></td>
	</tr>
	<tr>
		<td>參加揪團權限:</td>
		<td><input type="TEXT" name="join_event_auth" size="45" value="<%=member_authroityVO.getJoin_event_auth()%>" /></td>
	</tr>
	
	<tr>
		<td>開團權限:</td>
		<td><input type="TEXT" name="host_auth" size="45" value="<%=member_authroityVO.getHost_auth()%>" /></td>
	</tr>
	<tr>
		<td>登入權限:</td>
		<td><input type="TEXT" name="log_auth" size="45" value="<%=member_authroityVO.getLog_auth()%>" /></td>
	</tr>

	<%-- <jsp:useBean id="deptSvc" scope="page" class="com.dept.model.DeptService" />
	<tr>
		<td>部門:<font color=red><b>*</b></font></td>
		<td><select size="1" name="deptno">
			<c:forEach var="deptVO" items="${deptSvc.all}">
				<option value="${deptVO.deptno}" ${(empVO.deptno==deptVO.deptno)?'selected':'' } >${deptVO.dname}
			</c:forEach>
		</select></td>
	</tr> --%>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="verify_level" value="<%=member_authroityVO.getVerify_level()%>">
<input type="submit" value="送出修改"></FORM>
</body>
</html>