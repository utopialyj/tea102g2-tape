<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="chatRoom" class="d-none d-md-block col-md-2 shadow"
	style="position: fixed; right: 15px; height: 87%; width: auto; background-color: aliceblue;">

	<div class="btn-group btn-group-sm">
		<button type="button" id="friendsChatBtn" class="btn btn-secondary">Friend</button>
		<button type="button" id="actsChatBtn" class="btn btn-secondary">Activity</button>
	</div>
	<div id="friendsChat" style="height: 91%;">
<%-- 		<jsp:include page="chat/chat.jsp"/> --%>
		<iframe src="<%=request.getContextPath()%>/front-end/chat/chat.jsp"
			style="height: 100%; width: 100%; border: 0;"> </iframe>
	</div>
	<div id="actsChat" class="displayNone">
<!-- 		<iframe -->
<%-- 			src="<%=request.getContextPath()%>/front-end/chat/chatActs.jsp" --%>
<!-- 			style="height: 100%; width: 100%; border: 0;"> </iframe> -->
	</div>
</div>
<div id="chatIcon" class="d-block d-md-none"
	style="position: fixed; right: 3%; bottom: 3%;">
	<i class="fab fa-facebook-messenger fa-3x" style="color: lightskyblue"></i>
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
</script>