<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.acts.model.*"%>
<%@ page import="java.util.*"%>
<% 
	//<a href ? Parameter> 用Getter方法可以取得
	String actid = request.getParameter("actid");
	
//存完，EL才取得到	
//pageContext.setAttribute("actid", actid);
	ActsService actSvc = new ActsService();
	
	ActsVO actVO = actSvc.getOneAct(actid);
	pageContext.setAttribute("actVO", actVO);
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<style>
#map {
	height: 900px;
	width: 1600px;
}
</style>
</head>

<body>


	<div id="map" class="embed-responsive embed-responsive-16by9"></div>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCmGlmJzrHR1EWo8c2tCA8FxvbJF2KKuyg&callback=initMap">
        </script> <!-- &key="金鑰"& -->
	<script>
        function initMap() {
        var actMap;
  		var geocoder = new google.maps.Geocoder();
  		var marker;
  		//var location;
  

    var mapOptions = {
    	zoom: 16
    }
    
    actMap = new google.maps.Map(document.getElementById('map'), mapOptions);

    
   
    geocoder.geocode( { 'address': "${actVO.loc}" }, function(results, status) {
        if (status == 'OK') {
          actMap.setCenter(results[0].geometry.location);
          //location = results[0].geometry.location;
          marker = new google.maps.Marker({
        	    map: actMap,
        	    position: results[0].geometry.location,
        	    title:"${actVO.title}"
        	});
          
          
          var contentString = '<h4>'+"活動主題: ${actVO.title}"+'</h4>'
          +'<p>' + '<font size="3">'+'地址: ${actVO.loc}'+ '</font>' +'</p><br/>'
          +'<img class="infoImg" src="<%=request.getContextPath()%>/front-end/acts/Acts_Search_Servlet?getPic=getPicture&actid=${actVO.actid}"'+
        		  ' width=100 height= 100><br/>'
          ;
          var infowindow = new google.maps.InfoWindow({content:contentString});

          marker.addListener("click", () => {
  		    infowindow.open(actMap, marker);
  		  });
         
        } else {
          alert('Geocode was not successful for the following reason: ' + status);
        }
      });
    

		   
        
        

//'<h2>'+"${actVO.title}"+'</h2>';
// +'<span>'+"${actVO.title}"+'</span><br/>';
// +'<span>'+'${actVO.loc}'+'</span><br/>';
<%-- +'<img class="infoImg" src="<%=request.getContextPath()%>/front-end/acts/Acts_Search_Servlet?getPic=getPicture&actid=${actVO.actid}"><br/>' --%>
      
    
   	 
   	
   	
}

    </script>
</body>

</html>