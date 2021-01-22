<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coupon_info.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	Coupon_InfoService coupon_infoSvc = new Coupon_InfoService();
	List<Coupon_InfoVO> list = coupon_infoSvc.getAll();
	pageContext.setAttribute("list", list);
%>


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
	background-color: lightgrey;
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
	width: 100%;
	background-color: lightblue;
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

td#product_photo {
	width: 100px;
	height: 100px;
}

img {
	max-width: 100px;
	max-height: 100px;
}
</style>

</head>
<body bgcolor='lightyellow'>
	<%@ include file="../header.jsp"%>
	<div class="container">
		<div class="row">
			<div class=col-12>
				<!-- <h4>此頁練習採用 EL 的寫法取值:</h4> -->
				<table id="table-1">
					<tr>
						<td>
							<h3>所有優惠券資料 - listAllCoupon_Info.jsp</h3>
							<h4>
								<a href="select_page.jsp">回首頁</a>
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
						<th>優惠券編號</th>
						<th>商品名稱</th>
						<th>商品品牌名稱</th>
						<th>兌換條件</th>
						<th>兌換期限</th>
						<th>商品圖片</th>
						<th>商品文字描述</th>
						<th>優惠類別</th>
						<th>可兌換地區代號</th>
						<th>可兌換數量</th>
						<th>已被兌換次數</th>
						<th>商品上架狀態</th>
						<th>商品刊登日期</th>
						<th>商品下架日期</th>
					</tr>
					<%@ include file="page1.file"%>
					<c:forEach var="coupon_infoVO" items="${list}"
						begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<td>${coupon_infoVO.cpon_code}</td>
							<td>${coupon_infoVO.product_name}</td>
							<td>${coupon_infoVO.product_brand}</td>
							<td>${coupon_infoVO.exc_condition}</td>
							<td>${coupon_infoVO.exe_deadline}</td>
							<td id="product_photo">
								<%
									String img = "data:image;base64,";
										String photoBASE64 = null;
										Coupon_InfoVO coupon_infoVO = (Coupon_InfoVO) pageContext.getAttribute("coupon_infoVO");
										Base64.Encoder encoder = Base64.getEncoder();
										if (coupon_infoVO.getProduct_photo() != null) {
											photoBASE64 = encoder.encodeToString(coupon_infoVO.getProduct_photo());
											img += photoBASE64;
										} else {
											img = "images/null.jpg";
										}
								%> <img src="<%=img%>"> <%-- 			${coupon_infoVO.product_photo} --%>
							</td>
							<td>${coupon_infoVO.product_context}</td>
							<td>${coupon_infoVO.cpon_type}</td>
							<td>${coupon_infoVO.cpon_area_code}</td>
							<td>${coupon_infoVO.exc_count}</td>
							<td>${coupon_infoVO.exced_count}</td>
							<td>${coupon_infoVO.shelf_sts}</td>
							<td>${coupon_infoVO.onshelf_date}</td>
							<%-- 			<td><fmt:formatDate value="${coupon_infoVO.onshelf_date}" pattern="EEE, MMM d, ''yy"/></td> --%>
							<td>${coupon_infoVO.cpon_offshelf_date}</td>
							<%-- 			<td><fmt:formatDate value="${coupon_infoVO.cpon_offshelf_date}" pattern="EEE, MMM d, ''yy"/></td> --%>
							<td>
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/back-end/coupon_info/coupon_info.do"
									style="margin-bottom: 0px;">
									<input type="submit" value="修改"> <input type="hidden"
										name="cpon_code" value="${coupon_infoVO.cpon_code}"> <input
										type="hidden" name="action" value="getOne_For_Update">
								</FORM>
							</td>
							<td>
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/back-end/coupon_info/coupon_info.do"
									style="margin-bottom: 0px;">
									<input type="submit" value="刪除"> <input type="hidden"
										name="cpon_code" value="${coupon_infoVO.cpon_code}"> <input
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