<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Page Wrapper -->
<div id="wrapper">

	<!-- Sidebar -->
	<ul
		class="navbar-nav bg-gradient-secondary sidebar sidebar-dark accordion"
		id="accordionSidebar">

		<!-- Sidebar - Brand -->
		<a
			class="sidebar-brand d-flex align-items-center justify-content-center"
			href="<%=request.getContextPath()%>/back-end/index.jsp">
			<div class="sidebar-brand-icon">
				<img src="<%=request.getContextPath()%>/back-end/images/tape4.png"
					style="width: 80px">
			</div>
			<div class="sidebar-brand-text mx-3">Admin</div>
		</a>

		<!-- Divider -->
		<hr class="sidebar-divider my-0">

		<!-- Nav Item - Dashboard -->
		<li class="nav-item"><a class="nav-link"
			href="<%=request.getContextPath()%>/back-end/index.jsp"> <i
				class="bi bi-megaphone-fill"></i> <span>Announce</span></a></li>

		<!-- Divider -->
		<hr class="sidebar-divider">

		<!-- Heading -->
		<div class="sidebar-heading">Management</div>

		<!-- Nav Item - Pages Collapse Menu -->
		<li class="nav-item"><a class="nav-link"
			href="<%=request.getContextPath()%>/back-end/member_info/mem_select_page.jsp">
				<i class="bi bi-person-fill"></i> <span>Member Management</span>
		</a></li>

		<!-- Nav Item - Pages Collapse Menu -->
		<li class="nav-item"><a class="nav-link"
			href="<%=request.getContextPath()%>/back-end/acts/select_page.jsp">
				<i class="bi bi-people-fill"></i> <span>Activity Management</span>
		</a></li>

		<!-- Nav Item - Utilities Collapse Menu -->
		<li class="nav-item"><a class="nav-link"
			href="<%=request.getContextPath()%>/back-end/coupon_info/select_page.jsp">
				<i class="bi bi-cart-fill"></i> <span>Product Management</span>
		</a></li>

		<!-- Divider -->
		<hr class="sidebar-divider">



		<!-- Sidebar Toggler (Sidebar) -->
		<div class="text-center d-none d-md-inline">
			<button class="rounded-circle border-0" id="sidebarToggle"></button>
		</div>

	</ul>
	<!-- End of Sidebar -->

	<!-- Content Wrapper -->
	<div id="content-wrapper" class="d-flex flex-column">

		<!-- Main Content -->
		<div id="content">

			<!-- Topbar -->
			<nav
				class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

				<!-- Sidebar Toggle (Topbar) -->
				<button id="sidebarToggleTop"
					class="btn btn-link d-md-none rounded-circle mr-3">
					<i class="fa fa-bars"></i>
				</button>


				<!-- Topbar Navbar -->
				<ul class="navbar-nav ml-auto">


					<!-- Nav Item - Messages -->
					<li class="nav-item dropdown no-arrow mx-1"><a
						class="nav-link dropdown-toggle" href="#" id="messagesDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> <i class="fas fa-envelope fa-fw"></i> <!-- Counter - Messages -->
							<span class="badge badge-danger badge-counter"></span>
					</a> <!-- Dropdown - Messages -->
						<div
							class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
							aria-labelledby="messagesDropdown">
							<h6 class="dropdown-header" style="background-color: #696b79">Message
								Center</h6>
							<a class="dropdown-item d-flex align-items-center" href="#">
								<div class="dropdown-list-image mr-3">
									<img class="rounded-circle"
										src="https://source.unsplash.com/Mv9hjnEUHR4/60x60" alt="">
									<div class="status-indicator bg-success"></div>
								</div>
								<div>
									<div class="text-truncate">Am I a good boy? The reason I
										ask is because someone told me that people say this to all
										dogs, even if they aren't good...</div>
									<div class="small text-gray-500">Chicken the Dog · 2w</div>
								</div>
							</a> <a class="dropdown-item text-center small text-gray-500"
								href="#">Read More Messages</a>
						</div></li>


					<div class="topbar-divider d-none d-sm-block"></div>

					<!-- Nav Item - User Information -->
					<li class="nav-item dropdown no-arrow"><a
						class="nav-link dropdown-toggle" href="#" id="userDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> <span class="mr-2 text-gray-600 small">Administrator:
								${member_infoVO.user_name}</span> <img
							class="img-profile rounded-circle"
							src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=headshot">
					</a> <!-- Dropdown - User Information -->
						<div
							class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
							aria-labelledby="userDropdown">
							<a class="dropdown-item"
								href="<%=request.getContextPath()%>/front-end/index.jsp"> <i
								class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
								Go back
							</a>
						</div></li>

				</ul>

			</nav>
			<!-- End of Topbar -->