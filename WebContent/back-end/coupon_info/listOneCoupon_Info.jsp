<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coupon_info.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	Coupon_InfoVO coupon_infoVO = (Coupon_InfoVO) request.getAttribute("coupon_infoVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
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
				<!-- <h4>此頁暫練習採用 Script 的寫法取值:</h4> -->
				<table id="table-1">
					<tr>
						<td>
							<h3>優惠券資料 - listOneCoupon_Info.jsp</h3>
							<h4>
								<a href="select_page.jsp">回首頁</a>
							</h4>
						</td>
					</tr>
				</table>

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
					<tr>
						<td><%=coupon_infoVO.getCpon_code()%></td>
						<td><%=coupon_infoVO.getProduct_name()%></td>
						<td><%=coupon_infoVO.getProduct_brand()%></td>
						<td><%=coupon_infoVO.getExc_condition()%></td>
						<td><%=coupon_infoVO.getExe_deadline()%></td>
						<td id="product_photo">
							<%
								String img = "data:image;base64,";
								String photoBASE64 = null;
								Base64.Encoder encoder = Base64.getEncoder();
								if (coupon_infoVO.getProduct_photo() != null) {
									photoBASE64 = encoder.encodeToString(coupon_infoVO.getProduct_photo());
									img += photoBASE64;
								} else {
									img = "images/null.jpg";
								}
							%> <img src="<%=img%>"> <%-- 			<%=coupon_infoVO.getProduct_photo()%> --%>
						</td>
						<td><%=coupon_infoVO.getProduct_context()%></td>
						<td><%=coupon_infoVO.getCpon_type()%></td>
						<td><%=coupon_infoVO.getCpon_area_code()%></td>
						<td><%=coupon_infoVO.getExc_count()%></td>
						<td><%=coupon_infoVO.getExced_count()%></td>
						<td><%=coupon_infoVO.getShelf_sts()%></td>
						<td><%=coupon_infoVO.getOnshelf_date()%></td>
						<td><%=coupon_infoVO.getCpon_offshelf_date()%></td>
					</tr>
				</table>
			</div>
		</div>
	</div>


	<%@ include file="../footer.jsp"%>
</body>
</html>