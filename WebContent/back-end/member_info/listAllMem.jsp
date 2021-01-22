<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%
	Member_InfoService memberSvc = new Member_InfoService();
	//因為要使用EmpService()，所以要先宣告該類別物件
	List<Member_InfoVO> list = memberSvc.getAll();
	//用上面的物件呼叫getAll()取得所有值
	pageContext.setAttribute("list", list);
	//pageContext是該頁面的內容，這裏用上面取得的資料設定頁面的屬性
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

img {
	width: 100px
}
</style>
</head>
<body bgcolor='white'>
	<%@ include file="../header.jsp"%>
	<div class="container">
		<div class="row">
			<div class=col-12>
				<table id="table-1">
					<tr>
						<td>
							<h3>所有員工資料 - listAllMem.jsp</h3>
							<h4>
								<a
									href="<%=request.getContextPath()%>/back-end/member_info/mem_select_page.jsp">回首頁</a>
							</h4>
						</td>
					</tr>
				</table>

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<!-- if條件式，參考：p.252 -->

					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
				<table>
					<tr>
						<th>會員編號</th>
						<th>信箱</th>
						<th>密碼</th>
						<th>電話號碼</th>
						<th>身分證字號</th>
						<th>身分證照片正面</th>
						<th>身分證照片反面</th>
						<th>會員權限等級</th>
						<th>會員名稱</th>
						<th>性別</th>
						<th>生日</th>
						<th>星座</th>
						<th>血型</th>
						<th>特長</th>
						<th>大頭照</th>
						<th>學校</th>
						<th>公司</th>
						<th>自我介紹</th>
						<th>地區代碼</th>
						<th>點數</th>
						<th>配對地區</th>
						<th>年齡低標</th>
						<th>年齡高標</th>
						<th>修改</th>
						<th>刪除</th>
					</tr>
					<%@ include file="page1.file"%>
					<c:forEach var="member_infoVO" items="${list}"
						begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<!-- 		pageIndex跟rowsperpage在page.file1裡面 -->
						<tr>
							<td>${member_infoVO.mem_id}</td>
							<td>${member_infoVO.m_email}</td>
							<td>${member_infoVO.m_paswd}</td>
							<td>${member_infoVO.m_phone}</td>
							<td>${member_infoVO.identity_number}</td>
							<td class="td1"><img
								src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=idphoto_f"></td>
							<%-- 			<td>${member_infoVO.idphoto_b}</td>
 --%>
							<td class="td1"><img
								src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=idphoto_b"></td>

							<td>${member_infoVO.verify_lv}</td>
							<td>${member_infoVO.user_name}</td>
							<td>${member_infoVO.gender}</td>
							<td>${member_infoVO.m_birthday}</td>
							<td>${member_infoVO.horoscope}</td>
							<td>${member_infoVO.blood_type}</td>
							<td>${member_infoVO.specialty}</td>
							<%-- 			<td>${member_infoVO.headshot}</td>
 --%>
							<td class="td1"><img
								src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=headshot"></td>
							<td>${member_infoVO.school}</td>
							<td>${member_infoVO.company}</td>
							<td>${member_infoVO.intro}</td>
							<td>${member_infoVO.area_code}</td>
							<td>${member_infoVO.points}</td>
							<td>${member_infoVO.meat_area}</td>
							<td>${member_infoVO.meat_minage}</td>
							<td>${member_infoVO.meat_maxage}</td>


							<td>
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/Member_InfoServlet"
									style="margin-bottom: 0px;">
									<input type="submit" value="修改"> <input type="hidden"
										name="mem_id" value="${member_infoVO.mem_id}"> <input
										type="hidden" name="action" value="getOne_For_Update">
								</FORM>
							</td>
							<td>
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/Member_InfoServlet"
									style="margin-bottom: 0px;">
									<input type="submit" value="刪除"> <input type="hidden"
										name="mem_id" value="${member_infoVO.mem_id}"> <input
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