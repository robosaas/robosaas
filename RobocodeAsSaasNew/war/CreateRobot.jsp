
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
 
<%
BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<style>
	body{
		background:url(http://artisanironworksllc.com/wp-content/uploads/2013/06/website-background-2.jpg) no-repeat;
	}
	
	.notBlack
	{
		background-color: white;
	}
	
	div.headingside{
	width:100%; 
	background:url(https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTuaG17um04MaxK44U-a1JSSsQq6qjN4HefRebC-uZcMwRABmkLSQ) no-repeat;
	background-size: 100%		
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
	.buttonname{
	display: inline-block;
    	text-decoration: none;
    	color: #fff;
	font-weight: bold;
    	background-color: #1C1C1C;
   	padding: 20px 70px;
    	font-size: 12px;
    	border: 1px solid #2d6898
	}
	div.rightside{
	background:url(http://www.crazyleafdesign.com/blog/wp-content/uploads/2012/10/iphone-5-leather.jpg?ce0830)no-repeat; 
	height:420px;width:400px;
	float:right
	}
	.editor-div {
   	height:410px;width:749px;
    float:left;
    position: center
   }
</style>
<title>ROBOCODE</title>
<script src="/ace/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="/ace/theme-xcode.js" type="text/javascript" charset="utf-8"></script>
<script src="/ace/mode-java.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
function getXmlHttpRequestObject() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP"); 
	} else {
		alert("Error due to old version of browser.Upgrade your browser");
	}
}
var requester = getXmlHttpRequestObject();
var editor;
function loadPage(){
	editor = ace.edit('robotCode');
    editor.setTheme("ace/theme/xcode");
    var javaMode = require("ace/mode/java").Mode;
	    editor.getSession().setMode(new javaMode());
	    editor.setShowPrintMargin(false);
}


function loadRobotCode(){
	
	var robotName = document.getElementById("robotName").value.trim();
	var packageName = document.getElementById("packageName").value.trim();
	var url ="CreateRobot?robotName="+robotName+"&packageName="+packageName;
	if (requester.readyState == 4 || requester.readyState == 0){
		requester.open("GET", url, false);
		requester.onreadystatechange = handleAlterContent; 
		requester.send(null);
		
	}
	
}

function saveRobot() {
	document.getElementById("finalCode").value = editor.getSession().getValue();
	document.getElementById('saveRobotForm').action = "<%= blobstoreService.createUploadUrl("/SaveRobot") %>";
	document.getElementById('saveRobotForm').method="post";
	document.getElementById('saveRobotForm').submit();
	
	}

function displayList()
{

	if (requester.readyState == 4) {
		var x=requester.responseText;
		document.getElementById("robList").innerHTML+="<p>" +x + "</p>";
			
	    
	}

}


function handleAlterContent() {
	if (requester.readyState == 4) {
        editor.getSession().setValue(requester.responseText);
	    editor.setReadOnly(false); 
	}
}
function updateRobot(){
	
	var robotName = document.getElementById("robotName").value.trim();
	var packageName = document.getElementById("packageName").value.trim();
	var url ="UpdateRobot?robotName="+robotName+"&packageName="+packageName;
	if (requester.readyState == 4 || requester.readyState == 0){
		requester.open("GET", url, false);
		requester.onreadystatechange = handleAlterContent; 
		requester.send(null);
		
	}
	
}
function deleteRobot(){
	
	var robotName = document.getElementById("robotName").value.trim();
	var packageName = document.getElementById("packageName").value.trim();
	var url ="DeleteRobot?robotName="+robotName+"&packageName="+packageName;
	if (requester.readyState == 4 || requester.readyState == 0){
		requester.open("GET", url, false);
		requester.onreadystatechange = handleAlterContent; 
		requester.send(null);
		
	}
	
}
function getRobot(){
	var url="GetRobot";
	if (requester.readyState == 4 || requester.readyState == 0){
		requester.open("GET", url, false);
		requester.onreadystatechange = displayList; 
		requester.send(null);
		
	}
	
}

</script>
</head>
<body onload="loadPage()">

<form name= "saveRobotForm" id="saveRobotForm"  method="get" enctype="multipart/form-data" onSubmit="function(){alert('Yaay!!');}"> 
<div style="width:100%">
  <div class="headingside">
	<center>
      <h1>ROBOCODE</h1>
	  <h3>Build the best,destory the rest</h3>
	</center>
  </div>
	    <textarea id="finalCode" name="finalCode" cols="5" rows="5" style="display:none;"></textarea>
		<label class="notBlack"> Robot Name</label><input type= "text" id ="robotName" name="robotName"  maxlength="10">
		<label class="notBlack"> Package Name</label><input type= "text" id ="packageName" name="packageName" maxlength="10">
		<input type = "button" name = "file" value="Create New Robot" onclick="loadRobotCode()">
		<input type="button" id="saveRobotButton" value="Save Robot" onclick="saveRobot()">
		<input type="button" id="updateRobotButton" value="Update Robot" onclick="updateRobot()">
		<input type="button" id="deleteRobotButton" value="Delete Robot" onclick="deleteRobot()">
	<div class="editor-div" id="robotCode">
	</div>
	<div class="rightside">
      <input type="button" id="refersh" value="SHOW" onclick="getRobot()" align="right">
      <a href="BattleView.jsp"><input type="button" id="refersh" value="PLAY BATTLE"></a>
      <div id="robList" class="notBlack"><b>ROBOTS:</b></div>
      
  </div>
 <div class="footer">
  <center>
      Copyright © 2014 utdrobocode.com
  </center>
  </div>
</div>
</form>