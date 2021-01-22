<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>lessthan2.jsp</title>
</head>
<body>

	<br>
	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr bgcolor='orange' align='center' valign='middle' height='20'>
			 <td>   
				     <h3><font color=red>${member_info.user_name} </font>等級不夠請去驗證</h3>
			 </td>
			 <td>
			 	<a href="#">前往驗證頁面</a> 
			 	<br>
			 	<br>
			 	<a href="<%=request.getContextPath()%>/front-end/acts/cardHomePage.jsp"">前往揪團首頁</a>
			 </td>
		</tr>
	</table>
	<b> <br>
	<br>                .....
	</b>
	
</body>
</html>