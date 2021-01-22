<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_info.model.*"%>
<%@ page import= "java.util.Map"%>
<%
//有拿到信箱：
String m_email = (String)session.getAttribute("m_email");
   System.out.println("有傳遞到信箱？"+ !(m_email==null));
   
//造VO撈資料庫
Member_InfoService msvc = new Member_InfoService();
Member_InfoVO member_infoVO =msvc.findLogin(m_email);

pageContext.setAttribute("member_infoVO", member_infoVO);
   System.out.println("會員名字？"+ member_infoVO.getUser_name());


%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>密碼更改 u_paswd.jsp</title>

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

<%@ include file="/front-end/nav.jsp"%>

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
            <ul>
                
                <c:if test="${authority.authority==11}">
                	<li>
                    	<a href="<%=request.getContextPath() %>/front-end/compy_table/front_CompyMembershipPlatform_info_updateBusiness.jsp">還沒改</a>
                	</li>
                </c:if>
                <c:if test="${authority.authority==12}">
                	<li>
                    	<a href="<%=request.getContextPath() %>/front-end/compy_table/front_CompyMembershipPlatform_info_updateBusiness.jsp">還沒改</a>
                	</li>
                </c:if>
                <c:choose>
                	<c:when test="${authority.authority==2}">
                		<li>
                    		<a href="">企業用戶</a>
                		</li>
                		<li>
                    		<a href="">企業用戶</a>
                		</li>
                		<li>
                    		<a href="">企業用戶</a>
                		</li>
                		<li>
                    		<a href="">企業用戶</a>
                		</li>
                	</c:when>
                </c:choose>
            </ul>
        </div>
        <!-- SIDE DIV -->
         <!-- MAIN DIV -->
         <div class="info_div">
   
            
            <div>
                <h1>會員資訊-密碼更改</h1>
                <hr>
            </div>
            	<div class="pwd_original">
            		舊密碼<br><input type="password" id="pwd_original" name="pwd_original"><span id="pwd_original_span"></span>
            	</div>
            
            	<div class="pwd_update">
        			新密碼<br><input type="password" id="pwd_update" name="pwd_update">
    			</div>

    			<div class="pwd_update_comfirm">
        			新密碼二次確認<br><input type="password" id="pwd_update_comfirm" name="pwd_update_comfirm"><span id="pwd_update_span"></span>
    			</div>
    			<br>
    			<span id="all_span"></span>
    			<br>
    			<button id="submit_btn">確認送出更改</button>
    			</div>
    			</div>
    			</article>
        </div>
        	<%@ include file="/front-end/chatroom.jsp"%>
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
		
	<%@ include file="/front-end/js/notification.js" %>
		
	</script>
   <!--  <script src="js/f_update_paswd.js"></script> -->
   <script>
   $("#pwd_original").blur(function() {
		var pwd_original = $("#pwd_original").val();
		var obj = {
			"action" : "pwd_update",
			"pwd_original" : pwd_original
		}
		console.log("123");
		$.ajax({
			url : "<%=request.getContextPath()%>/Member_InfoServlet",
			type : "POST",
			data : obj,
			dataType:"JSON",

			success : function(result) {
				console.log(result.fina)
	            if(result.fina=="error"){
	            	$("#pwd_original_span").attr("style","color:red")
	                $("#pwd_original_span").text("密碼輸入錯誤")
	                console.log("1231231")
	            }else if(result.fina=="success"){
	            	$("#pwd_original_span").attr("style","color:#00B2B2")
	                $("#pwd_original_span").text("正確符合")
	                console.log("321")
	            }
			},
			error : function(err) {
			}
		})
	})
	$("#submit_btn").on("click", function () {
	            var pwd_update_comfirm = $("#pwd_update_comfirm").val();

	            var pwd_update = $("#pwd_update").val();

	            if (pwd_update_comfirm == pwd_update) {
	            	$("#pwd_update_span").attr("style","color:#00B2B2")
	                $("#pwd_update_span").text("相符");
	                if ($("#pwd_original_span").text() == "正確符合") {
	                	$("#all_span").attr("style","color:green")
	                    $("#all_span").text("密碼更改成功")

	                    let obj={
	                        "action":"pwd_update_comfirm",
	                        "pwd_update":pwd_update,
	                    }

	                    $.ajax({
	                		url : "<%=request.getContextPath()%>/Member_InfoServlet",
	                        type: "POST",
	                        data: obj,
	                        dataType: "JSON",

	                        success: function (result) {
	                        	if(result.fina="success"){
	                        		window.location.replace("<%=request.getContextPath()%>/front-end/login.jsp");
	                        		alert("密碼更改成功,請重新登入");
	                        	}
	                        },
	                        error: function (err) {
	                        }
	                    })

	                } else {
	                	$("#all_span").attr("style","color:red")
	                    $("#all_span").text("請確認舊密碼是否正確")
	                }
	            } else {
	            	$("#pwd_update_span").attr("style","color:red")
	                $("#pwd_update_span").text("二次密碼輸入不符");
	            }

	        })
   
   </script>

</body>
</html>