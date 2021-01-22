<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authroity Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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
</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>Auth: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Authroity: Home</p>
<h3>權限查詢:</h3>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<ul>
  <li><a href='listAllAuthroity.jsp'>List</a> all Authroities.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member_AuthroityServlet" >
        <b>輸入權限等級 (如1):</b>
        <input type="text" name="verify_level">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="authSvc" scope="page" class="com.member_authroity.model.Member_AuthroityService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member_AuthroityServlet" >
       <b>選擇權限等級:</b>
       <select size="1" name="verify_level">
          <c:forEach var="member_authroityVO" items="${authSvc.all}" > 
          <option value="${member_authroityVO.verify_level}">${member_authroityVO.verify_level}
         </c:forEach>    
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
    <%--  <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member_AuthroityServlet" >
       <b>選擇員工姓名:</b>
       <select size="1" name="verify_level">
         <c:forEach var="member_authroityVO" items="${authSvc.all}" > 
          <option value="${member_authroityVO.verify_level}">${member_authroityVO.verify_level}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li> --%>
</ul>


<h3>新增權限:</h3>

<ul>
  <li><a href='addAuthroity.jsp'>Add</a> a new Auth.</li>
</ul>

</body>
</html>