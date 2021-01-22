<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>Hobby_List: Home</title>
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
   <tr><td><h3>Black_List: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Black_List: Home</p>

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
  <li><a href='listAllHobby_List.jsp'>List</a> all Black_List.  <br><br></li>
  

  <jsp:useBean id="hobby_listSvc" scope="page" class="com.hobby_list.model.Hobby_ListService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Match/Hobby_List/hobby_list.do" >
       <b>選擇興趣編號:</b>
       <select size="1" name="hob_code">
         <c:forEach var="hobby_listVO" items="${hobby_listSvc.all}" > 
          <option value="${hobby_listVO.hob_code}">${hobby_listVO.hob_code}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Match/Hobby_List/hobby_list.do">
  	<b>選擇多個興趣</b>

  	<c:forEach var="hobby_listVO" items="${hobby_listSvc.all}" > 
          <option value="${hobby_listVO.hob_code}">${hobby_listVO.hob}
<!--           <input type="checkbox" name="action" value="getOne_For_Display">     -->
            <label><input type="checkbox" name="hobby" value="${hobby_listVO.hob_code}">${hobby_listVO.hob}</label>           
    </c:forEach>

    
  	<input type="hidden" name="action" value="getOne_For_Display">
    <input type="submit" value="送出">
  	</FORM>
  </li>

  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/Match/Hobby_List/hobby_list.do" >
       <b>選擇興趣:</b>
       <select size="1" name="hob_code">
         <c:forEach var="hobby_listVO" items="${hobby_listSvc.all}" > 
          <option value="${hobby_listVO.hob_code}">${hobby_listVO.hob}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>

</ul>


<h3>興趣管理</h3>

<ul>
  <li><a href='addHobby_List.jsp'>Add</a> a new Hobby_List.</li>
</ul>

</body>
</html>