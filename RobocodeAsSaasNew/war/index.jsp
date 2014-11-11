<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
</style>
	<script>
function domainSelection(theSelected) {
	 var x = theSelected.selectedIndex;
	 window.location.href ="header.jsp";
	 if(document.getElementById('DOMAIN').options[document.getElementById('DOMAIN').selectedIndex].text == "SELECT")
    alert("Please select the domain on right top corner");
	 else
		 window.location.href ="header.jsp";
}
</script>
<title>ROBOCODE</title>
</head>
<body>
<div style="width:100%">

  <div class="headingside">
  	<center>
      <h1>ROBOCODE</h1>
	  <h3>Build the best,destory the rest</h3>
	  <marquee>Login to start the battel</marquee>
	</center>
  </div>
  <div class="mid">  
	<select name="DOMAIN" align="right">
        <option name="DOMAIN" selected="select" value="Select">SELECT</option>
        <option name="DOMAIN" value="domain1">DOMAIN 1</option>
        <option name="DOMAIN" value="domain2">DOMAIN 2</option>
        <option name="DOMAIN" value="domain3">DOMAIN 3</option>
        <option name="DOMAIN" value="domain4">DOMAIN 4</option>
    </select>
	</br></br></br></br></br></br></br></br></br></br></br></br></br></br></br>
	<h1 class="heading"> WELCOME TO THE GAME</h1>
	<center>
	<input type="button" value="LOGIN" class="buttonname" onClick="domainSelection(this)"></input>
	
	</center>
	</div>
  <div class="footer">
  <center>
      Copyright © 2014 utdrobocode.com
  </center>
  </div>
</div>
</body>
</html>