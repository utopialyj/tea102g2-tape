<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Deposit_List: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: lightgrey;
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
<body bgcolor='lightyellow'>

<table id="table-1">
   <tr><td><h3>Deposit_List: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Deposit_List: Home</p>

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
  <li><a href='listAllDeposit_List.jsp'>List</a> all Deposit_List.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="deposit_list.do" >
        <b>輸入儲值訂單編號 (如1):</b>
        <input type="text" name="deposit_code">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="deposit_listSvc" scope="page" class="com.deposit_list.model.Deposit_ListService" />
   
  <li>
     <FORM METHOD="post" ACTION="deposit_list.do" >
       <b>選擇儲值訂單編號:</b>
       <select size="1" name="deposit_code">
         <c:forEach var="deposit_listVO" items="${deposit_listSvc.all}" > 
          <option value="${deposit_listVO.deposit_code}">${deposit_listVO.deposit_code}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>

</ul>


<h3>儲值訂單管理</h3>

<ul>
  <li><a href='addDeposit_List.jsp'>Add</a> a new Deposit_List.</li>
</ul>

</body>
</html>