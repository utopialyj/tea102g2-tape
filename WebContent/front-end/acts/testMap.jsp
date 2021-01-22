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
	height: 500px;
	width: 500px;
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
//  const uluru = { lat: -25.363, lng: 131.044 };
//  const map = new google.maps.Map(document.getElementById("map"), {
//    zoom: 4,
//    center: uluru,
//  });

		var mapOptions = {
			    	zoom: 5
			    };
		  var map = new google.maps.Map(document.getElementById("map"), mapOptions);
		  
		  	var hello;
		  	var geocoder = new google.maps.Geocoder();
		    geocoder.geocode( { 'address': "台北車站" }, function(results, status) {
		        if (status == 'OK') {
		          map.setCenter(results[0].geometry.location);
		          console.log(results[0].geometry.location);
		          marker = new google.maps.Marker({
		  		    position: results[0].geometry.location,
		  		    map: map,
		  		    title: "我要地圖",
		  		  });
		          
		          const contentString =  '<div id="content">' +
				    '<div id="siteNotice">' +
				    "</div>" +
				    '<h1 id="firstHeading" class="firstHeading">Uluru</h1>' +
				    '<div id="bodyContent">' +
				    "<p><b>Uluru</b>, also referred to as <b>Ayers Rock</b>, is a large " +
				    "sandstone rock formation in the southern part of the " +
				    "Northern Territory, central Australia. It lies 335&#160;km (208&#160;mi) " +
				    "south west of the nearest large town, Alice Springs; 450&#160;km " +
				    "(280&#160;mi) by road. Kata Tjuta and Uluru are the two major " +
				    "features of the Uluru - Kata Tjuta National Park. Uluru is " +
				    "sacred to the Pitjantjatjara and Yankunytjatjara, the " +
				    "Aboriginal people of the area. It has many springs, waterholes, " +
				    "rock caves and ancient paintings. Uluru is listed as a World " +
				    "Heritage Site.</p>" +
				    '<p>Attribution: Uluru, <a href="https://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194">' +
				    "https://en.wikipedia.org/w/index.php?title=Uluru</a> " +
				    "(last visited June 22, 2009).</p>" +
				    "</div>" +
				    "</div>";
		          const infowindow = new google.maps.InfoWindow({
		  		    content: contentString,
		  		  });
		          marker.addListener("click", () => {
		  		    infowindow.open(map, marker);
		  		  });
		          
		         
		        } else {
		          alert('Geocode was not successful for the following reason: ' + status);
		        }
		      });
		
		  const contentString =
		    '<div id="content">' +
		    '<div id="siteNotice">' +
		    "</div>" +
		    '<h1 id="firstHeading" class="firstHeading">Uluru</h1>' +
		    '<div id="bodyContent">' +
		    "<p><b>Uluru</b>, also referred to as <b>Ayers Rock</b>, is a large " +
		    "sandstone rock formation in the southern part of the " +
		    "Northern Territory, central Australia. It lies 335&#160;km (208&#160;mi) " +
		    "south west of the nearest large town, Alice Springs; 450&#160;km " +
		    "(280&#160;mi) by road. Kata Tjuta and Uluru are the two major " +
		    "features of the Uluru - Kata Tjuta National Park. Uluru is " +
		    "sacred to the Pitjantjatjara and Yankunytjatjara, the " +
		    "Aboriginal people of the area. It has many springs, waterholes, " +
		    "rock caves and ancient paintings. Uluru is listed as a World " +
		    "Heritage Site.</p>" +
		    '<p>Attribution: Uluru, <a href="https://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194">' +
		    "https://en.wikipedia.org/w/index.php?title=Uluru</a> " +
		    "(last visited June 22, 2009).</p>" +
		    "</div>" +
		    "</div>";
		  const infowindow = new google.maps.InfoWindow({
		    content: contentString,
		  });
		  marker.addListener("click", () => {
		    infowindow.open(map, marker);
		  });
		}
	

    </script>
</body>

</html>