<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<style>
	div.mid{
		position:fixed;
		top: 100px;
   		left: 470px;
		 background: url(https://pbs.twimg.com/profile_images/516976384820793344/mAUYyN2Z_400x400.png) no-repeat top center;
		 opacity: 1;
	}
	div.headingside{
	width:100%; 
	position:fixed;
	top:0px;
	left:0px;
	background:url(https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTuaG17um04MaxK44U-a1JSSsQq6qjN4HefRebC-uZcMwRABmkLSQ) no-repeat;
	background-size: 100%;	
	height:100px
	}
	div.footer{
	width:100%; 
	height:50px; 
	float:left; 
	bottom: 0px; 
	position:fixed; 
	left: 0px; 	
	background:url(https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTuaG17um04MaxK44U-a1JSSsQq6qjN4HefRebC-uZcMwRABmkLSQ) no-repeat;
	background-size: 100%;
	clear:both;
	}
	h1.heading{
  	 color: #D0F8FF;
   		text-shadow: 0 0 5px #342D7E, 0 0 10px #342D7E,
           0 0 20px #342D7E, 0 0 30px #342D7E,
           0 0 40px #342D7E;
		letter-spacing: 1px;
		text-align:center;
		width:100%;
    	position: relative;

	}
	div.frontpage{
		background:  #342D7E;
		height : 610px;
		width :100%
	}
	.buttonname{
	display: inline-block;
    	text-decoration: none;
    	color: #fff;
	font-weight: bold;
    	background-color: #1C1C1C;
   	padding: 20px 70px;
    	font-size: 24px;
    	border: 1px solid #2d6898;
	}
	.button{
	display: inline-block;
    	text-decoration: none;
    	color: #fff;
	font-weight: bold;
    	background-color: #1C1C1C;
    	font-size: 12px;
    	border: 1px solid #2d6898;
	}
	</style>
<script src="/ace/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="/ace/theme-xcode.js" type="text/javascript" charset="utf-8"></script>
<script src="/ace/mode-java.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
function domainname(){
	
	var robotName = document.getElementById("robotName").value.trim();
	var packageName = document.getElementById("packageName").value.trim();
	var url ="DeleteRobot?robotName="+robotName+"&packageName="+packageName;
	if (requester.readyState == 4 || requester.readyState == 0){
		requester.open("GET", url, false);
		requester.onreadystatechange = handleAlterContent; 
		requester.send(null);
		
	}
	
}
</script>
</head>

<body>
 <div class="headingside">
  	<center>
      <h1>ROBOCODE</h1>
	  <h3>Build the best,destory the rest</h3>
	</center>
<%
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();
if (user != null) {
    pageContext.setAttribute("user", user);
   
%>

<p align="right">Hello, ${fn:escapeXml(user.nickname)}! (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
    </br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br>
    <center>
     <a href="CreateRobot.jsp"><input type="button" value="PLAY" class="buttonname" onclick="domainname()"></input></a></center>
<%
} else {
%>
<p align="right">Hello!
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
    to begin the battle</p>
<%
    }
%>
</div>

<image src=https://pbs.twimg.com/profile_images/516976384820793344/mAUYyN2Z_400x400.png">

 <div class="footer">
  <center>
      Copyright © 2014 utdrobocode.com
  </center>
  </div>
</body>
</html>