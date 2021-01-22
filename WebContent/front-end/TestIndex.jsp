<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_info.model.*"%>
<%
	Member_InfoVO member_infoVO = (Member_InfoVO)session.getAttribute("member_infoVO");
	List<Member_InfoVO> friends = (List<Member_InfoVO>)session.getAttribute("friends");
%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <title></title>

  <!-- Bootstrap 的 CSS -->
  <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/navbar.css">
  <style>
 	.displayNone {
		display: none;
	}
  </style>
</head>

<body>
  <!-- Topbar -->
  <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-info shadow topbar">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse">
      <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand mr-auto" href="#">
      <img src="front-end/images/tape2.svg" width="40" height="40">
      <class class="h5">Tape</class>
    </a>
    <div class="collapse navbar-collapse" id="navbarCollapse">
      <!-- Topbar Navbar -->
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="#">
            <i class="fas fa-hand-holding-heart"></i>
            Match
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">
            <i class="fas fa-glass-cheers"></i>
            Activity
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link disabled" href="#">
            <i class="fas fa-donate"></i>
            Points
          </a>
        </li>
      </ul>
      <!-- Topbar Search -->
      <form class="d-lg-inline-block form-inline ml-auto mr-md-3 m-2 m-md-0">
        <div class="input-group">
          <input type="text" class="form-control bg-light border-0 small" placeholder="Search for...">
          <div class="input-group-append">
            <button class="btn btn-secondary" type="button">
              <i class="fas fa-search fa-sm"></i>
            </button>
          </div>
        </div>
      </form>
    </div>

    <ul class="ml-auto">
      <!-- Nav Item - User Information -->
      <li class="nav-item dropdown no-arrow userAndNotify">
        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown">
          <img class="img-profile rounded-circle" src="front-end/images/undraw_profile.svg" width="30" height="30">
          <span class="d-none d-lg-inline text-gray-600 small">Douglas McGee</span>
        </a>
        <!-- Dropdown - User Information -->
        <div class="dropdown-menu dropdown-menu-right shadow">
          <a class="dropdown-item" href="#">
            <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
            Profile
          </a>
          <a class="dropdown-item" href="#">
            <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
            Settings
          </a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
            <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
            Logout
          </a>
        </div>
      </li>

      <!-- Nav Item - Alerts -->
      <li class="nav-item dropdown no-arrow userAndNotify">
        <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown">
          <i class="fas fa-bell fa-fw"></i>
          <!-- Counter - Alerts -->
          <!-- <span class="badge badge-danger badge-counter">3+</span> -->
        </a>
        <!-- Dropdown - Alerts -->
        <div class="dropdown-menu dropdown-menu-right shadow" style="width: 400px; overflow: hidden;">
          <h6 class="dropdown-header">
            Alerts Center
          </h6>
          <a class="dropdown-item d-flex align-items-center" href="#">
            <div class="mr-3">
              <div class="bg-primary">
                <i class="fas fa-file-alt text-white"></i>
              </div>
            </div>
            <div>
              <div class="small text-gray-500">December 12, 2019</div>
              <span class="font-weight-bold">A new monthly report is ready to download!</span>
            </div>
          </a>
          <a class="dropdown-item d-flex align-items-center" href="#">
            <div class="mr-3">
              <div class="bg-success">
                <i class="fas fa-donate text-white"></i>
              </div>
            </div>
            <div>
              <div class="small text-gray-500">December 7, 2019</div>
              $290.29 has been deposited into your account!
            </div>
          </a>
          <a class="dropdown-item d-flex align-items-center" href="#">
            <div class="mr-3">
              <div class="bg-warning">
                <i class="fas fa-exclamation-triangle text-white"></i>
              </div>
            </div>
            <div>
              <div class="small text-gray-500">December 2, 2019</div>
              Spending Alert: We've noticed unusually high spending for your account.
            </div>
          </a>
        </div>
      </li>
    </ul>

  </nav>
  <!-- End of Topbar -->



  <div class="container">

    <div class="row">
    
      <div class="col-12 col-md-8">

      </div>
<!--       <div class="col-3 col-md-2"> -->
<!--         col-3 col-md-2 -->
<!--         col-3 col-md-2 -->
<!--         col-3 col-md-2 -->
<!--         col-3 col-md-2 -->
<!--         col-3 col-md-2 -->
<!--         col-3 col-md-2 -->
<!--       </div> -->
<!--       <div class="col-9 col-md-8"> -->
<%-- 		Hi ${member_infoVO.mem_id},	this page is for test.<br> --%>
<!-- 		<hr> -->
<!-- 		Friends:<br> -->
<%-- 		<c:forEach var="friend" items="${friends}"> --%>
<%-- 			${friend.mem_id}<br> --%>
<%-- 		</c:forEach> --%>

<!--       </div> -->
      <div id="chatRoom" class="d-none d-md-block col-md-2 shadow" style="position:fixed;right:15px;height:85%">

<div class="btn-group btn-group-sm">
  <button type="button" id="friendsChatBtn" class="btn btn-secondary">Friend</button>
  <button type="button" id="actsChatBtn" class="btn btn-secondary">Activity</button>
</div>
		<div id="friendsChat">
	        <jsp:include page="/front-end/chat/chat.jsp" flush="true"/>
		</div>
        <div id="actsChat" class="displayNone">
<%-- 	        <jsp:include page="/front-end/chat/chatActs.jsp" flush="true"/> --%>
        </div>
		
      </div>
    </div>
    
    <div id="chatIcon" class="d-block d-md-none"style="position: fixed; right: 3%; bottom: 3%;">
		<i class="fab fa-facebook-messenger fa-3x" style="color:blue"></i>
	</div>

  </div>


  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>


  <!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
  <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
  <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="https://kit.fontawesome.com/e66ce32cfd.js" crossorigin="anonymous"></script>
  <script>
  	document.getElementById("chatIcon").onclick=function(){
  		document.getElementById("chatRoom").classList.toggle("d-none");
  	}
  	
  	document.getElementById("friendsChatBtn").onclick=function(){
  		document.getElementById("friendsChat").classList.remove("displayNone");
  		document.getElementById("actsChat").classList.add("displayNone");
  	}
  	document.getElementById("actsChatBtn").onclick=function(){
  		document.getElementById("friendsChat").classList.add("displayNone");
  		document.getElementById("actsChat").classList.remove("displayNone");
  	}


  	
  	
  	// bug solution for jsp include with bootstrap dropdown
    $(document).ready(function () {
        $('.dropdown-toggle').dropdown();
    });
  </script>
</body>

</html>
