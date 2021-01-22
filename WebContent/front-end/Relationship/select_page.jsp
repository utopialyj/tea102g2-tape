<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>Relationship: home</title>
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
   <tr><td><h3>Relationship: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Relationship: Home</p>

<h3>資料查詢:</h3>
	
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
  <li><a href='listAllRelationship.jsp'>List</a> all Relationship.  <br><br></li>
  

  <jsp:useBean id="relationshipSvc" scope="page" class="com.relationship.model.RelationshipService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Relationship/relationship.do" >
       <b>選擇黑名單編號:</b>
       <select size="1" name="frdrela_uid">
         <c:forEach var="relationshipVO" items="${relationshipSvc.all}" > 
          <option value="${relationshipVO.frdrela_uid}">${relationshipVO.frdrela_uid}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  

  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Relationship/relationship.do" >
       <b>選擇黑人編號:</b>
       <select size="1" name="frdrela_uid">
         <c:forEach var="relationshipVO" items="${relationshipSvc.all}" > 
          <option value="${relationshipVO.frdrela_uid}">${relationshipVO.frdinv_memid}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  

   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Relationship/relationship.do" >
       <b>選擇被黑編號:</b>
       <select size="1" name="frdrela_uid">
         <c:forEach var="relationshipVO" items="${relationshipSvc.all}" > 
          <option value="${relationshipVO.frdrela_uid}">${relationshipVO.frdbeinv_memid}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  
   <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Relationship/relationship.do" >
       <b>選擇被黑編號:</b>
       <select size="1" name="frdrela_uid">
         <c:forEach var="relationshipVO" items="${relationshipSvc.all}" > 
          <option value="${relationshipVO.frdrela_uid}">${relationshipVO.frdinv_sts}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
</ul>

	<input type="button" name="addf" value="">

<h3>交友狀態管理</h3>

<ul>
  <li><a href='addRelationship.jsp'>Add</a> a new Blcak_List.</li>
</ul>
</body>
</html>