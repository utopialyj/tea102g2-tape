<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Base64"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.actcol.model.*"%>
<%@ page import="com.member_info.model.*"%>

<%
	ActColVO actColVO = (ActColVO) request.getAttribute("actColVO");

Member_InfoVO member_infoVO = (Member_InfoVO)session.getAttribute("member_infoVO");

String actid = (String)session.getAttribute("actid");
pageContext.setAttribute("actid", actid);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>活動清單新增 - addActApl.jsp</title>

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

div.hidden_for_users {
	/* position: fixed;
    opacity: 0;
    pointer-events: none; */
	-moz-appearance: none;
	-webkit-appearance: none;
	appearance: none;
}
</style>

<style>
 img{ 
   	max-width: 100%; 
   	max-height: 100%; 
   }
table { 
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	white-space: nowrap;
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
			<td>
				<h3>活動收藏新增 - addActCol.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath() %>/front-end/acts/cardHomePage.jsp"><img src="<%= request.getContextPath()%>/front-end/acts/images/kali.jpg"
						alt="找不到圖片" width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>
	<font color="red" size="6"><span id="isRepeat"></span></font>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/actcol/ActColController" name="form1">
		<table>
			<tr>
				<td>會員編號:</td>
				<td><input name="memid" type="text"
					value="${member_infoVO.mem_id}" /></td>
			</tr>

			<tr>
				<td>活動編號:</td>
				<td><input type="TEXT" name="actid" size="45"
					value="${actid}" /></td>
			</tr>



		</table>

		<input type="hidden" name="action" value="insert"> 
		<input type="submit" value="送出新增">
	</FORM>
	
	<script src="<%=request.getContextPath() %>/front_end/js/jquery/jquery-3.5.1.min.js"></script>
	<script >
	$(function (){
		let aplPair = {
		"action":"repeat_check",
    	"actid" : "${actid}"
	}

	$.ajax({
		url : "<%= request.getContextPath()%>/front-end/actapl/ActColController",
		type : "GET",
		data : aplPair,
		dataType:"JSON",
		success : function(result) {
			if(result.nope=="repeat_col"){
				$("#isRepeat").text("重複收藏");
				$("form[name='form1']").remove();
			}else if(result.yes=="ok_apl"){
				$("#isRepeat").text("可以收藏");
			}
		},
		error : function(err) {
		}
	})

});
	
	</script>
</body>
</html>