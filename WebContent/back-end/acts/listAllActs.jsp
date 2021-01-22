<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="java.util.*, java.util.Base64,java.text.DateFormat,java.text.SimpleDateFormat"%>
<%@ page import="com.acts.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	ActsService actsSvc = new ActsService();
	List<ActsVO> list = actsSvc.getAll();
	pageContext.setAttribute("list", list);
	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
%>
<%-- <%=request.getContextPath()%> --%>
<%-- <%= request.getServletPath() %> --%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Tape | Admin</title>
<!-- Custom fonts for this template-->
<link
	href="<%=request.getContextPath()%>/back-end/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="<%=request.getContextPath()%>/back-end/css/sb-admin-2.min.css"
	rel="stylesheet">


<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"> -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/vendor/bootstrap/icons-main/font/bootstrap-icons.css">
<style>
table#table-1 {
	background-color: turquoise;
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
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>
	<%@ include file="../header.jsp"%>
	<div class="container">
		<div class="row">
			<div class=col-12>
				<h4>此頁練習採用 EL 的寫法取值:</h4>
				<table id="table-1">
					<tr>
						<td>
							<h3>所有員工資料 - listAllEmp.jsp</h3>
							<h4>
								<a href="select_page.jsp"><img src="images/back1.gif"
									width="100" height="32" border="0">回首頁</a>
							</h4>
						</td>
					</tr>
				</table>

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>

				<table>
					<tr>
						<th>活動編號</th>
						<th>主辦者編號</th>
						<th>上下架狀態</th>
						<th>活動時間</th>
						<th>活動主題</th>
						<th>活動狀態</th>
						<th>活動類型</th>
						<th>活動內容</th>
						<th>活動圖片</th>
						<th>人數</th>
						<th>地區編號</th>
						<th>熱度</th>
						<th>地點</th>
						<th>店家資訊</th>
						<th>預算</th>
						<th>參與所需點數</th>
						<th>檢舉狀太</th>
					</tr>
					<%@ include file="page1.file"%>
					<c:forEach var="actsVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<%
								ActsVO actsVO = (ActsVO) pageContext.getAttribute("actsVO");
									String pic = null;
									if (actsVO.getPic() != null) {
										pic = actsVO.getBase64Image();
									}

									// 				String photoString = null;
									// 				try{
									// 					Base64.Encoder encoder = Base64.getEncoder();
									// 					if(actsVO.getPic() != null){
									//  					photoString = encoder.encodeToString(actsVO.getPic());
									// 					}
									// 				} catch (Exception e) {
									// 					e.printStackTrace();
									// 				}
							%>
							<td>${actsVO.actid}</td>
							<td>${actsVO.memid}</td>
							<td>${actsVO.shsts}</td>
							<td><%=sdf.format(actsVO.getTime())%></td>
							<td>${actsVO.title}</td>
							<td>${actsVO.sts}</td>
							<td>${actsVO.type}</td>
							<td>${actsVO.cont}</td>
							<td><img src="data:image;base64,<%=pic%>" width="100"
								height="50"></td>
							<td>${actsVO.peop}</td>
							<td>${actsVO.areacd}</td>
							<td>${actsVO.hot}</td>
							<td>${actsVO.loc}</td>
							<td>${actsVO.store}</td>
							<td>${actsVO.bgt}</td>
							<td>${actsVO.pts}</td>
							<td>${actsVO.rpsts}</td>
							<td>
								<!-- <%=request.getContextPath()%>/back_end/acts/ActsController -->
								<FORM METHOD="post" ACTION="ActsController"
									style="margin-bottom: 0px;">
									<input type="submit" value="修改"> <input type="hidden"
										name="actid" value="${actsVO.actid}"> <input
										type="hidden" name="action" value="getOne_For_Update">
								</FORM>
							</td>
							<td>
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/back_end/acts/ActsController"
									style="margin-bottom: 0px;">
									<input type="submit" value="刪除"> <input type="hidden"
										name="actid" value="${actsVO.actid}"> <input
										type="hidden" name="action" value="delete">
								</FORM>
							</td>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="page2.file"%>
			</div>
		</div>
	</div>


	<%@ include file="../footer.jsp"%>
</body>
</html>