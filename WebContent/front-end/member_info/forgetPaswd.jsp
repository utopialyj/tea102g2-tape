<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>
<%@ page import= "java.util.Map"%>
<%
Map<String,String> map=(Map)session.getAttribute("user_name");	
/* 移除乾淨（需在最上面做這件事）
 */
 session.removeAttribute("m_email");
session.removeAttribute("member_infoVO");


%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>忘記密碼 forgetPaswd.jsp</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/notification.css">
<style>

article.paper {
	width: 100%;
	margin: 10px auto 0;
	border-radius: 5px;
	padding: 10px;
	background-color: white;
	box-shadow: 1px 1px 5px rgb(119, 120, 160);
}

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
<body>

<%-- <%@ include file="/front-end/nav.jsp"%> --%>

<div class="container mt-3">
		<!-- Content here -->
		<div class="row">
			<div class="col-2 box1">
			</div>
			<div class="col-8 box2">
<!-- SIDE DIV -->
<article class="paper">
	<div class="all_div">
        <div class="side_div_in">
            <div class="side_div_in_bigpic">
<%--                 <img id="bigpic_img" class="side_div_in_bigpic_img" src="<%=request.getContextPath() %>/compy_table/Compy_TableServlet_front?method=many_pics_display&mail=${authority.cp_contact_email}&which_one=bigpic" alt="大頭照"> --%>
            </div>
           
        </div>
        <!-- SIDE DIV -->
         <!-- MAIN DIV -->
         <div class="info_div">
            <div>
   
                <h1>忘記密碼-信箱驗證填寫</h1>
                <hr>
            </div>
            	<div class="email_input">
            		填寫信箱<br><input type="email" id="InputEmail2" name="m_email"><span id="InputEmail2_span"></span>
            	</div>
            
            	
    			<br>
    			<span id="all_span"></span>
    			<br>
    			<button id="submit_btn2" disabled="disabled">確認送出</button>
    			</div>
    			</div>
    			</article>
        </div>
<%--         	 <%@ include file="/front-end/chatroom.jsp"%>  --%>
        	</div>
    </div>
    <!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/e66ce32cfd.js"
		crossorigin="anonymous"></script>
	<script>
		
<%-- 	<%@ include file="/front-end/js/notification.js" %> --%>
		
	</script>
    <script>
    
  //AJAX用
    //信箱
    setTimeout(
    $("#InputEmail2").blur(function() {
    	console.log("1")
    	var InputEmail2 = $("#InputEmail2").val();
    	console.log("2")

    	var obj = {
    		"action" : "email_check2",
    		"InputEmail2" : InputEmail2
    	}
    	console.log("3")

    	console.log($("#InputEmail2").val());
    	$.ajax({
    		url : "/TEA102G2/Member_InfoServlet",
    		type : "POST",
    		data : obj,
    		dataType:"JSON",
    		cache:false,

    		success : function(result) {
    			console.log(result.fina)
                if(result.fina=="error"){
                	$("#InputEmail2_span").attr("style","color:red");
                    $("#InputEmail2_span").text("無此帳號");
                   console.log("1231231")
                }else if(result.fina=="empty"){
                	
                	$("#InputEmail2_span").attr("style","color:red");
                    $("#InputEmail2_span").text("信箱不得為空");
                     console.log("信箱不得為空")
                	
                }else if(result.fina=="success"){
                	$("#InputEmail2_span").attr("style","color:#00B2B2");
                    $("#InputEmail2_span").text("okay!");
                    $("#submit_btn2").removeAttr("disabled");
                    <%
                    System.out.println("=============================================");
                    String m_email = (String) session.getAttribute("m-email");
                    System.out.println("帳號?"+ m_email);
                    %>
                    
                }
    		},
    		error : function(err) {
    			console.log(error);
    		}
    	})//AJAX在此結束
    }),200);
    
    $("button#submit_btn2").on("click",function(){
			window.location.href = "<%= request.getContextPath()%>/front-end/member_info/u_paswd.jsp";
		})
    </script>

</body>
</html>