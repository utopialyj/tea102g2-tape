<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Topbar -->
<nav
	class="navbar navbar-expand-md navbar-dark fixed-top bg-info shadow topbar">
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarCollapse">
		<span class="navbar-toggler-icon"></span>
	</button>
	<a class="navbar-brand mr-auto"
		href="<%=request.getContextPath()%>/front-end/index.jsp"> <img
		src="<%=request.getContextPath()%>/front-end/images/tape2.svg"
		width="40" height="40">Tape
	</a>
	<div class="collapse navbar-collapse" id="navbarCollapse">
		<!-- Topbar Navbar -->
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/front-end/Relationship/relationship.do?action=listfriend"> <i
					class="fas fa-hand-holding-heart"></i> Match
			</a></li>
			<li class="nav-item"><a class="nav-link"
				href="<%=request.getContextPath()%>/front-end/acts/cardHomePage.jsp">
					<i class="fas fa-glass-cheers"></i> Activity
			</a></li>
			<li class="nav-item"><a class="nav-link" href="#"
				data-toggle="modal" data-target="#PointsModal"> <i
					class="fas fa-donate"></i> Points
			</a></li>

		</ul>
		<!-- Topbar Search -->
		<form class="d-lg-inline-block form-inline ml-auto mr-md-3 m-2 m-md-0">
			<div class="input-group">
				<input type="text" class="form-control bg-light border-0 small"
					placeholder="Search for...">
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
		<li class="nav-item dropdown no-arrow userAndNotify"><a
			class="nav-link dropdown-toggle" href="#" id="userDropdown"
			data-toggle="dropdown"> <img class="img-profile rounded-circle"
				src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=headshot"
				width="30" height="30"> <span
				class="d-none d-lg-inline text-gray-600 small">${member_infoVO.user_name}</span>
		</a> <!-- Dropdown - User Information -->
			<div class="dropdown-menu dropdown-menu-right shadow">
				<a class="dropdown-item"
					href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=session.getAttribute("mem_id")%>">
					<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Profile
				</a> <a class="dropdown-item" href="#"> <i
					class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i> Settings
				</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="#" data-toggle="modal"
					data-target="#logoutModal"> <i
					class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
					Logout
				</a>
			</div></li>

		<!-- Nav Item - Notification -->
		<li class="nav-item dropdown no-arrow userAndNotify"><a
			class="nav-link dropdown-toggle" href="#" id="notificationDropdown"
			data-toggle="dropdown"> <i class="fas fa-bell fa-fw"></i> <!-- Counter - Notification -->
				<span class="badge badge-danger -hide" id="badgeUnread">0</span>
		</a> <!-- Dropdown - Notification -->
			<div class="dropdown-menu dropdown-menu-right shadow"
				style="width: 280px; height: 320px; overflow: auto;">
				<div id="notificationArea">
					<ul id="allNTF"></ul>
				</div>
			</div></li>
	</ul>

</nav>
<!-- End of Topbar -->





<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
				<button class="close" type="button" data-dismiss="modal">
					<span>×</span>
				</button>
			</div>
			<div class="modal-body">Select "Logout" below if you are ready
				to end your current session.</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
				<a class="btn btn-primary"
					href="<%=request.getContextPath()%>/Member_InfoServlet?action=LogOut">Logout</a>
			</div>
		</div>
	</div>
</div>


<!-- Points Modal-->
<div class="modal fade" id="PointsModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Points</h5>
				<button class="close" type="button" data-dismiss="modal">
					<span>×</span>
				</button>
			</div>
			<div class="modal-body">Comming soon...</div>
			<div class="modal-footer">
			<button class="btn btn-primary" type="button" data-dismiss="modal">OK</button>
			</div>
		</div>
	</div>
</div>