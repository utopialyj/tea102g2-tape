<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>

<%
	Member_InfoVO member_InfoVO = (Member_InfoVO) request.getAttribute("member_infoVO");
%>
<%-- <%=user_tableVO == null%> --%>

<!-- 確認是否為空值 -->
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>員工登入 - logIn.jsp</title>

<!-- <style>
table#table-1 {
 background-color: orange;
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
 width: 500px;
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
</style> -->

</head>
<body bgcolor='white'>

	<form action="<%=request.getContextPath()%>/Member_InfoServlet" method="post">

		<table border=1>
			<tr>
				<td colspan=2>
					<p align=center>
						輸入<b>(測試登入)</b>:<br>
						<!-- 帳號:<b>tomcat</b><br>
							密碼:<b>tomcat</b><br> -->
				</td>
			</tr>

			<tr>
				<td>
					<p align=right>
						<b>account:</b>
				</td>
				<td>
					<p>
						<input type=text name="m_email"
							value="<%=(member_InfoVO == null) ? "" : member_InfoVO.getM_email()%>"
							size=15>
					<div class="in_div_errorMsgs" style="color: red">${errorMsgs.account}</div>

				</td>
			</tr>

			<tr>
				<td>
					<p align=right>
						<b>password:</b>
				</td>
				<td>
					<p>
						<input type=password name="m_paswd" size=15>
					<div class="in_div_errorMsgs" style="color: red">${errorMsgs.pwd}</div>
					<div class="in_div_errorMsgs" style="color: red">${errorMsgs.account_error}</div>
				</td>
			</tr>


			<tr>
				<td colspan=2 align=center><input type=submit value="  ok   ">
					<input type="hidden" name="action" value="loginhandler"></td>
			</tr>
		</table>
	</form>
</body>
</html>