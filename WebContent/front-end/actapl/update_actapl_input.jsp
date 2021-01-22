<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.actapl.model.*"%>

<%
	ActAplVO actAplVO = (ActAplVO) request.getAttribute("actAplVO"); //ActAplServlet.java (Concroller) 存入req的actRnkVO物件 (包括幫忙取出的actRnkVO, 也包括輸入資料錯誤時的actRnkVO物件)
	pageContext.setAttribute("actAplVO", actAplVO);
%>

--這回取得的AplUid是 ${actAplVO.apluid}--

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>報名清單修改 - update_actapl_input.jsp</title>

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
			<td>
				<h3>活動評價修改 - update_actapl_input.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front_end/actapl/ActAplController" name="form1">
		<table>
			<tr>
				<td>APlUID:<font color=red><b>*</b></font></td>
				<td><%=actAplVO.getApluid()%></td>
			</tr>

			<tr>
				<td>會員編號:</td>
				<td><input type="text" name="memid" size="45"
					value="<%=actAplVO.getMemid()%>" /></td>
			</tr>

			<tr>
				<td>活動編號:</td>
				<td><input type="text" name="actid" size="45"
					value="<%=actAplVO.getActid()%>" /></td>
			</tr>


			
			<tr>
				<td>報名理由:</td>
				<td><textarea rows="10" cols="30" name="rson"><%=(actAplVO == null) &&  (actAplVO.getRson() == null)? actAplVO.getRson():"No Reason" %></textarea></td>
			</tr>
			
			<tr>
				<td>報名狀態:</td>
				<td><select name="sts">
						<option value="請選擇">報名狀態</option>
						<option value="同意"
							<%=(actAplVO != null) && "同意".equals(actAplVO.getSts()) ? "selected" : ""%>>同意</option>
						<option value="不同意"
							<%=(actAplVO != null) && "不同意".equals(actAplVO.getSts()) ? "selected" : ""%>>不同意</option>
						<option value="下架"
							<%=(actAplVO != null) && "下架".equals(actAplVO.getSts()) ? "selected" : ""%>>下架</option>
				</select></td>
			</tr>


		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="apluid" value="<%=actAplVO.getApluid()%>">
		<input type="submit" value="送出修改">
	</FORM>


</body>






</html>