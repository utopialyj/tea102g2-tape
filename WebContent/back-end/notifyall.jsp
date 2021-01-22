<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<input type="text" id="mem_id" placeholder="官方公告">
	<button id="send">send</button>
	<script>
		document.getElementById("send").onclick=function(){
			fetch("<%=request.getContextPath()%>/TestNotify?mem_id=" + document.getElementById("mem_id").value);
			console.log("Notified.");
		}
	</script>
</body>
</html>