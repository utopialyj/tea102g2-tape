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
		src="<%=request.getContextPath()%>/front-end/images/tape4.png"
		style="margin-right: 8px; height: 40px;">
	</a>
	<div class="collapse navbar-collapse" id="navbarCollapse">
		<!-- Topbar Navbar -->
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link"
				href="<%=request.getContextPath()%>/front-end/Relationship/relationship.do?action=listfriend">
					<i class="fas fa-hand-holding-heart"></i> Match
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
			data-toggle="dropdown" style="padding: 2px;"> <img
				id="profilePic" class="img-profile rounded-circle"
				src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${member_infoVO.mem_id}&which_one=headshot"
				style="width: 45px; height: 45px; border: 1px solid white; object-fit: cover;">
				<span id="username" class="d-none d-lg-inline text-gray-600 small"
				style="font-size: 16px; vertical-align: middle; margin-left: 10px;">${member_infoVO.user_name}</span>
		</a> <!-- Dropdown - User Information -->
			<div class="dropdown-menu dropdown-menu-right shadow">
				<a class="dropdown-item"
					href="<%=request.getContextPath()%>/front-end/profile/introduction.jsp?Member=<%=session.getAttribute("mem_id")%>">
					<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Profile
				</a> <a class="dropdown-item"
					href="<%=request.getContextPath()%>/front-end/member_info/u_paswd.jsp?Member=<%=session.getAttribute("mem_id")%>">
					<i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i> Change
					Password
				</a> <a class="dropdown-item displayNone" id="administrator"
					href="<%=request.getContextPath()%>/back-end/index.jsp"> <i
					class="fas fa-wrench fa-sm fa-fw mr-2 text-gray-400"></i>
					Administrator
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
			data-toggle="dropdown" style="padding: 11px; padding-right: 0px;">
				<i class="fas fa-bell fa-fw" style="vertical-align: middle;"></i> <!-- Counter - Notification -->
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



<!-- Logout Modal -->
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
				<h5 class="modal-title" id="exampleModalLabel">
					Points: <span style="color: orange;">${member_infoVO.points}</span>
					pt
				</h5>
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










<!-- 聊天室  jsp 暫時寫在這裡-->

<!-- Chat Room -->
<div id="chatRoom" class="d-none d-md-block shadow">

	<div class="btn-group btn-group-sm">
		<button type="button" id="friendsChatBtn" class="btn btn-secondary">Friend</button>
		<button type="button" id="actsChatBtn" class="btn btn-secondary">Activity</button>
	</div>
	<div id="friendsChat" style="height: 91%; overflow: auto">

		<!-- 	chat status changing button -->
		<!-- 	<div class="btn-group btn-group-sm no-arrow" -->
		<!-- 		style="position: absolute; top: 0; right: 0;"> -->
		<!-- 		<button id="btnGroupDrop1" type="button" -->
		<!-- 			class="btn btn-secondary dropdown-toggle" data-toggle="dropdown"> -->
		<!-- 			<i class="fas fa-cog"></i> -->
		<!-- 		</button> -->
		<!-- 		<div class="dropdown-menu"> -->
		<!-- 			<a class="dropdown-item" onclick="changeChatStatus('online')">Online</a> -->
		<!-- 			<a class="dropdown-item" onclick="changeChatStatus('busy')">Busy</a> -->
		<!-- 			<a class="dropdown-item" onclick="changeChatStatus('offline')">Offline</a> -->
		<!-- 		</div> -->
		<!-- 	</div> -->


		<div>
			<!-- 	for chat status use -->
			<!-- 		<div id="selfStatus" style="position: relative; left: 80%;"> -->
			<!-- 			<span style="color: lightgreen">online</span> -->
			<!-- 		</div> -->



			<div id="chatFriendsList">
				<c:forEach var="friend" items="${friends}">
					<div id="${friend.mem_id}" class="friendLabel">
						<div class="friendStatus -offline">
							<div class="friendIcon">
								<img
									src="<%=request.getContextPath() %>/Member_InfoServlet?action=getPic&mem_id=${friend.mem_id}&which_one=headshot">
							</div>
						</div>
						<span>${friend.user_name}</span><span class="notify -off">
							•</span>
					</div>
				</c:forEach>
			</div>
			<div id="chatBox" class="shadow -off">
				<div id="chatTitle">
					<div id="titlePhoto">
						<img src="">
					</div>
					<span id="friendNameSpan" class="friendNameSpan"></span>
					<button type="button" id="closeChatBox" class="close">
						<span>&times;</span>
					</button>
				</div>
				<div id="messagesArea" class="message-area"></div>
				<div class="input-area">
					<label class="btn btn-secondary" style="margin: auto;"> <input
						id="sendImage" style="display: none;" type="file"> <i
						class="fas fa-paperclip"></i>
					</label> <span id="imageName" style="display: none"></span> <input
						type="text" id="inputMessage" class="text-field"
						placeholder="Message"
						onkeydown="if (event.keyCode == 13) sendMessage();" /> <input
						type="submit" id="sendMessage" class="btn btn-secondary"
						value="Send" onclick="sendMessage();" />
				</div>
			</div>
		</div>






	</div>
	<div id="actsChat" class="displayNone"
		style="height: 91%; overflow: auto">

		<!-- 				<iframe -->
		<%-- 					src="<%=request.getContextPath()%>/front-end/chat/chatActs.jsp" --%>
		<!-- 					style="height: 100%; width: 100%; border: 0;"> </iframe> -->
		<%-- 		<jsp:include page="<%=request.getContextPath()%>/front-end/chat/chatActs.jsp"/> --%>
		<div id="chatActsList">
			<c:forEach var="key" items="${actsKeySet}">
				<div id="${key}" class="actsLabel">
					<span>${actsList[key]}</span>
				</div>
			</c:forEach>
		</div>
		
		<div id="chatBoxActs" class="shadow -off">
			<div id="chatTitleActs">
				<span id="actsNameSpan" class="actsNameSpan"></span>
				<button type="button" id="closeChatBoxActs" class="close">
					<span>&times;</span>
				</button>
			</div>
			<div id="messagesAreaActs" class="message-area"></div>
			<div class="input-area">
				<label class="btn btn-secondary" style="margin: auto;"> <input
					id="sendImageActs" style="display: none;" type="file"> <i
					class="fas fa-paperclip"></i>
				</label> <span id="imageNameActs" style="display: none"></span> <input
					type="text" id="inputMessageActs" class="text-field"
					placeholder="Message"
					onkeydown="if (event.keyCode == 13) sendMessageActs();" /> <input
					type="submit" id="sendMessage" class="btn btn-secondary"
					value="Send" onclick="sendMessageActs();" />
			</div>
		</div>
		
		
		
		
<!-- 		<h3 id="statusOutputActs" class="statusOutputActs"></h3> -->
<!-- 		<div id="messagesAreaActs" class="panel message-area"></div> -->
<!-- 		<div class="panel input-area"> -->
<!-- 			<input id="messageActs" class="text-field" type="text" -->
<!-- 				placeholder="Message" -->
<!-- 				onkeydown="if (event.keyCode == 13) sendMessageActs();" /> <input -->
<!-- 				type="submit" id="sendMessageActs" class="button" value="Send" -->
<!-- 				onclick="sendMessageActs();" /> -->
<!-- 		</div> -->
	</div>
</div>
<div id="chatIcon" class="d-block d-md-none"
	style="position: fixed; right: 3%; bottom: 3%; z-index: 2;">
	<i class="fab fa-facebook-messenger fa-3x" style="color: teal"></i>
</div>
<div id="guestBar" class="fixed-bottom displayNone shadow"
	style="width: 100%; height: 100px; background-color: #b2b2b238; backdrop-filter: blur(5px); text-align: center; padding: 27px">
	<div>
		<span
			style="vertical-align: middle; font-size: larger; color: dimgrey; text-shadow: -1px 0 whitesmoke, 0 1px whitesmoke, 1px 0 whitesmoke, 0 -1px whitesmoke;">想看更多？請
		</span> <a href="<%=request.getContextPath()%>/front-end/index.jsp">
			<button class="w-40 btn btn-success">Sign in</button>
		</a> <span
			style="vertical-align: middle; font-size: larger; color: dimgrey; text-shadow: -1px 0 whitesmoke, 0 1px whitesmoke, 1px 0 whitesmoke, 0 -1px whitesmoke;">
			或 </span> <a
			href="<%=request.getContextPath()%>/front-end/member_info/addMember.jsp">
			<button class="w-40 btn btn-outline-success">Register</button>
		</a>
	</div>
</div>
<script>
	document.getElementById("chatIcon").onclick = function() {
		document.getElementById("chatRoom").classList.toggle("d-none");
	}

	document.getElementById("friendsChatBtn").onclick = function() {
		document.getElementById("friendsChat").classList.remove("displayNone");
		document.getElementById("actsChat").classList.add("displayNone");
	}
	document.getElementById("actsChatBtn").onclick = function() {
		document.getElementById("friendsChat").classList.add("displayNone");
		document.getElementById("actsChat").classList.remove("displayNone");
	}

	console.log("${member_infoVO.mem_id}");
	if ("${member_infoVO.mem_id}" == "") {
		document.getElementById("guestBar").classList.remove("displayNone");
		document.getElementById("chatRoom").classList.remove("d-md-block");
		document.getElementById("profilePic").src = "<%=request.getContextPath()%>/front-end/images/guest.png";
		document.getElementById("username").innerText = "Guest";
	}

	if ("${member_infoVO.verify_lv}" == 9) {
		document.getElementById("administrator").classList
				.remove("displayNone");
	}
</script>