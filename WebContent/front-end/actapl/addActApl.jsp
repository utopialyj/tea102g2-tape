<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Base64"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.actapl.model.*"%>
<%@ page import="com.member_info.model.*"%>
<%
	ActAplVO actAplVO = (ActAplVO) request.getAttribute("actAplVO");
	String actid = (String)session.getAttribute("actid");
	pageContext.setAttribute("actid", actid);
//String actid = request.getParameter("actid");

//若沒登入就直接進來這個頁面，會NullPointerException
//所以透過filter不只可以避免這個情況，還可以幫忙等級驗證
//Member_InfoVO memVO = (Member_InfoVO)session.getAttribute("memVO");

%>
	<!--  活動編號 , 活動主題 連動 -->
	<jsp:useBean id="actSvc" scope="page" class="com.acts.model.ActsService" />
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>活動報名 - addActApl.jsp</title>

	<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/front-end/css/navbar.css">
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/front-end/css/notification.css">

	<!-- BootStrap -->
	<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

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


<!-- 側邊攔的css 開始-->
<style>

aside {
 color: #fff;
 width: 100%;
 padding-left: 20px;
 height: 500px;
 background-image: linear-gradient(30deg, #ffffff, #ff8a8f);
    border-top-right-radius: 80px;
    -webkit-filter: drop-shadow(12px 12px 7px rgba(0, 0, 0, 0.7));
    filter: drop-shadow(-12px 5px 8px rgba(0, 0, 0, 0.3));
}

aside a {
 font-size: 16px;
 font-size: 16px;
 color:#565c5f;
 display: block;
 padding: 12px;
  padding-left: 15px; 
 text-decoration: none;
 -webkit-tap-highlight-color: transparent;
}

aside a:hover {
 color: #6c757d;
 background: #fff;
 outline: none;
 position: relative;
 background-color: #fff;
 border-top-left-radius: 20px;
 border-bottom-left-radius: 20px;
 text-decoration:none;
}

aside a i {
 margin-right: 5px;
}

aside a:hover::after {
 content: "";
 position: absolute;
 background-color: transparent;
 bottom: 100%;
 right: 0;
 height: 35px;
 width: 35px;
 border-bottom-right-radius: 18px;
 box-shadow: 0 20px 0 0 #fff;
}

aside a:hover::before {
 content: "";
 position: absolute;
 background-color: transparent;
 top: 38px;
 right: 0;
 height: 35px;
 width: 35px;
 border-top-right-radius: 18px;
 box-shadow: 0 -20px 0 0 #fff;
}

aside p {
 margin: 0;
 padding: 40px 0;
}

body {

 width: 100%;
 height: 100vh;
 margin: 0;
}
</style>
<!-- 側邊攔的css 尾 -->



</head>

<body>
	<!-- Navbar -->
	<%@ include file="/front-end/nav.jsp"%>
	<!-- --------------------------------------------------------------------------------------------------------------- -->






	<div class="container mt-3 ">
		<!-- Content here -->
		<div class="row">


			<div class="col-2 box1">
				<aside>
				  <p> </p>
				  <a href="<%=request.getContextPath()%>/front-end/acts/addActs.jsp">
				    <i class="far fa-calendar-plus" aria-hidden="true"></i>
				   發起活動
				  </a>
				  <a href="<%=request.getContextPath()%>/front-end/acts/cardHomePage.jsp">
				    <i class="fa fa-laptop" aria-hidden="true"></i>
				   活動預覽
				  </a>
				  <a href="<%=request.getContextPath()%>/front-end/acts/manage_own_acts.jsp">
				    <i class="fas fa-tasks" aria-hidden="true"></i>
				    活動管理
				  </a>
				  <a href="<%=request.getContextPath()%>/front-end/acts/manage_join_event.jsp">
				    <i class="fas fa-sign-in-alt" aria-hidden="true"></i>
				   活動報名
				  </a>
				  <a href="<%=request.getContextPath()%>/front-end/acts/manage_self_col.jsp">
				    <i class="fa fa-star-o" aria-hidden="true"></i>
				  活動收藏
				  </a>
				</aside>
			</div><!-- 左側 col-2 -->
			


			<!--    中央  col-8  -->
			<div class="col-8 box2">

					<a href="<%= request.getContextPath()%>/front-end/acts/cardHomePage.jsp">回首頁</a>

	<h3>新增報名資料:</h3>

	<font color="red" size="6"><span id="isRepeat"></span></font>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty aplMsgs}">
		<%-- <font style="color: red">請確認報名理由:</font>--%>
		<ul>
			<c:forEach var="message" items="${aplMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/actapl/ActAplController" name="form1">
		<table>
			<tr>
				<td>報名者:</td>
				<td><input type="TEXT" name="memid" size="45" value="${member_infoVO.user_name}"  disabled="disabled" /></td>
			</tr>
<%--<input type="TEXT" name="actid" size="45"
					value="<%=request.getParameter("actid")%>" /> --%>
			<tr>
					<td>活動主題:</td>
					<td><input type="TEXT" name="actid" size="45"
						value="${actSvc.getOneAct(actid).title}"  disabled="disabled"/></td>
			</tr>

			<tr>
				<td>報名理由:</td>
				<td><textarea rows="10" cols="30" name="rson"><%=(actAplVO == null) ? "" : actAplVO.getRson()%></textarea></td>
			</tr>


		</table>
		<input type="hidden" name="actid" value="${actid}" />
		<input type="hidden" name="action" value="insert"> 
		<input id="quick_btn" type="button" value="快填">
		<input type="submit" id="repeat_btn" value="送出新增">
	</FORM>
			
			</div>
			<!-- 中央 col-8 -->


			<!-- 右側 col-2 -->
			<%@ include file="/front-end/chatroom.jsp"%>
			<!-- 右側 col-2-->
		</div>
		<!-- row_end -->
	</div>
	<!-- container mt-3 _end -->



	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>
	
	<script>
  
 <%@ include file="/front-end/js/notification.js" %>

 </script>
    <script>
    
//     let aplPair = {
//     		"action":"repeat_check",
<%--     		"actid" : "<%=request.getParameter("actid")%>" --%>
//     	}
    
// 	$("#repeat_check").click(function(){
// 		$.ajax({
// 			url : "/TEA102G2/front_end/actapl/ActAplController",
// 			type : "GET",
// 			data : aplPair,
// 			dataType:"JSON",

// 			success : function(result) {
// 				if(result.nope=="repeat_apl"){
// 					$("#isRepeat").text("重複報名");
// 				}else if(result.yes=="ok_apl"){
// 					$("#isRepeat").text("可以報名");
// 				}
// 			},
// 			error : function(err) {
// 			}
// 		})

// 	});
    
    


//     	$(document).click(function (){
     		//alert("Hello ");
    		
//     		let aplPair = {
// 	    		"action":"repeat_check",
<%--     	    		"actid" : "<%=request.getParameter("actid")%>" --%>
// 	    	}
    		
//     		$.ajax({
//     			url : "/TEA102G2/front_end/actapl/ActAplController",
//     			type : "GET",
//     			data : aplPair,
//     			dataType:"JSON",
//     			success : function(result) {
//     				if(result.nope=="repeat_apl"){
//     					$("#isRepeat").text("重複報名");
//     				}else if(result.yes=="ok_apl"){
//     					$("#isRepeat").text("可以報名");
//     				}
//     			},
//     			error : function(err) {
//     			}
//     		})
    		
//     	});
    		

    		

   	
//     	$(document).ready(function (){
//     		//alert("Hello ");
    		
//     		let aplPair = {
// 	    		"action":"repeat_check",
<%--     	    		"actid" : "<%=request.getParameter("actid")%>" --%>
// 	    	}
    		
//     		$.ajax({
//     			url : "/TEA102G2/front_end/actapl/ActAplController",
//     			type : "GET",
//     			data : aplPair,
//     			dataType:"JSON",
//     			success : function(result) {
//     				if(result.nope=="repeat_apl"){
//     					$("#isRepeat").text("重複報名");
//     				}else if(result.yes=="ok_apl"){
//     					$("#isRepeat").text("可以報名");
//     				}
//     			},
//     			error : function(err) {
//     			}
//     		})
    		
//     	});


	$(function (){//DOM解析完畢
			let aplPair = {
			"action":"repeat_check",
	    	"actid" : "${actid}"
			}
	
		$.ajax({
			url : "<%= request.getContextPath()%>/front-end/actapl/ActAplController",
			type : "GET",
			data : aplPair,
			dataType:"JSON",
			success : function(result) {
				if(result.nope=="repeat_apl"){
					$("#isRepeat").text("重複報名");
					$("textarea[name='rson']").attr("disabled","disabled");//報名理由反灰不給填寫
					$("input[id='repeat_btn']").attr("disabled","disabled");//按鍵反灰不給按
					$("input#quick_btn").remove();
				}else if(result.yes=="ok_apl"){
					$("#isRepeat").text("可以報名");
				}
			},
			error : function(err) {
			}
		})//aJax結束
		
		$("input#quick_btn").on("click",function(){
			//alert("hello");
			$("textarea[name='rson']").val("我最愛咖哩了");
			
		})//神奇小按鈕
		
	
	});

    	
    
    </script>




</body>


</html>