<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>

<!-- 相片檢視器 CSS -->
<link rel="stylesheet" href="<%= request.getContextPath()%>/front_end/css/viewer/viewer.min.css">
<!-- 相片檢視器 CSS -->


</head>
<body>
	<ul id="viewer">
    <li><img src="<%= request.getContextPath()%>/front_end/acts/img/tibet-1.jpg" alt="圖片1"></li>
    <li><img src="<%= request.getContextPath()%>/front_end/acts/img/tibet-2.jpg" alt="圖片2"></li>
    <li><img src="<%= request.getContextPath()%>/front_end/acts/img/tibet-3.jpg" alt="圖片3"></li>
    <li><img src="<%= request.getContextPath()%>/front_end/acts/img/tibet-4.jpg" alt="圖片4"></li>
    <li><img src="<%= request.getContextPath()%>/front_end/acts/img/tibet-5.jpg" alt="圖片5"></li>
    <li><img src="<%= request.getContextPath()%>/front_end/acts/img/tibet-6.jpg" alt="圖片6"></li>
</ul>


	<script src="<%= request.getContextPath()%>/front_end/js/jquery.min.js"></script>
	<script src="<%= request.getContextPath()%>/front_end/js/viewer.min.js"></script>
	<script src="<%= request.getContextPath()%>/front_end/js/viewer-jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>
</body>
	<script>
		$('#viewer').viewer();
	</script>
</html>