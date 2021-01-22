<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.relationship.model.*" %>
<%
	RelationshipVO relationshipVO = (RelationshipVO) request.getAttribute("relationshipVO");
%>
<html>
<head>
<meta charset="UTF-8">
<title>addRelationship 加入友誼列表</title>
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
	<td><h3>友誼列表 - addRelationship.jsp</h3></td>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Relationship/relationship.do" name="form1">
<table>
	

	<tr>
		<td>發出邀請會員:</td>
		<td><input type="TEXT" name="frdinv_memid" size="45"
			 value="<%= (relationshipVO==null)? "MEM0000001" : relationshipVO.getFrdinv_memid()%>" /></td>
	</tr>
	<tr>
		<td>受邀請會員:</td>
		<td><input type="TEXT" name="frdbeinv_memid" size="45"
			 value="<%= (relationshipVO==null)? "MEM0000002" : relationshipVO.getFrdbeinv_memid()%>" /></td>
	</tr>
	
	<jsp:useBean id="relationshipSvc" scope="page" class="com.relationship.model.RelationshipService" />
	
	<tr>
		<td>交友狀態:</td>
		<td><select size="1" name ="frdinv_sts">
		<c:forEach var="relationshipVO" items="${relationshipSvc.all}" > 
          <option value="${relationshipVO.frdrela_uid}">${relationshipVO.frdinv_sts}
         </c:forEach>   
		
		</select></td>
	</tr>
	
	
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>
</html>