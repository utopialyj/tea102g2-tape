<%@ page  contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TEA102G2 ActApl: Home</title>
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
<body  bgcolor='white'>

<table id="table-1">
   <tr><td><h3>TEA102G2 收藏活動: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for TEA102G2 收藏活動: Home</p>

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
  <li><a href='listAllActsApl.jsp'>List</a> 所有活動評價  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="ActColController" >
        <b>輸入APLUID (如APL0000001):</b>
        <input type="text" name="apluid">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="actAplSvc" scope="page" class="com.actapl.model.ActAplService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/actapl/ActAplController" >
       <b>選擇AplUID</b>
       <select size="1" name="apluid">
         <c:forEach var="actAplVO" items="${actAplSvc.all}" > 
          <option value="${actAplVO.apluid}">${actAplVO.apluid}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/actapl/ActAplController" >
       <b>選擇活動編號:</b>
       <select size="1" name="apluid">
         <c:forEach var="actAplVO" items="${actAplSvc.all}" > 
          <option value="${actAplVO.apluid}">${actAplVO.actid}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
  
  
  
    <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/actapl/ActAplController" >
       <b>報名狀態:</b>
       <select size="1" name="apluid">
         <c:forEach var="actAplVO" items="${actAplSvc.all}" > 
          <option value="${actAplVO.apluid}">${actAplVO.sts}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>新增報名</h3>

<ul>
  <li><a href='addActApl.jsp'>Add</a> 新增一筆報名.</li>
</ul>
</body>
</html>