<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Battle Runner</title>
<head>
<style type="text/css">

.tftable {font-size:12px;color:#333333;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
.tftable th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
.tftable tr {background-color:#d4e3e5;}
.tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}
.tftable tr:hover {background-color:#ffffff;}


    #resultPane {
    position: absolute;
    margin-left: 3%;
    margin-right: 3%;
    margin-top: 3%;
    margin-bottom: 3%;
    width: 55%;
    height: 90%;
    left: 470px;
    top: 90px;
   }
    #AvailableRobotsDiv
	{
 		position: absolute; 
 		top: 110px;
 		left: 10px; 
	}
	#AddRemoveDiv
	{
 		position: absolute; 
 		top: 150px; 
 		left: 180px; 
	}
	#SelectedRobotsDiv
	
	{
 		position: absolute; 
 		top: 110px; 
 		left: 280px; 
	}
	
</style>	

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
function getRobots(){
	
	
	requester.onreadystatechange = function()
	{
		if(requester.readyState == 4 && requester.status == 200)
			document.getElementById("AvailableRobotsDiv").innerHTML = requester.responseText;
	}
	requester.open("GET", "PlayerGetRobots", true);
	requester.send();
	
}

function runBattle()
{
    var robos = document.getElementById("selecetdRobots");
	   for(var i=0; i<robos.options.length; i++){
		   robos.options[i].selected= true;
	   }
	document.getElementById("resultPane").innerHTML = "<h2>Running Battle</h2>";
	if (requester.readyState == 4 || requester.readyState == 0) {
		var gameRounds = document.getElementById("gameRounds").value;
		var robots = [];
		var robotsList;
   		var selectedRobots = document.getElementById("selecetdRobots");
   		for (var i = 0; i < selectedRobots.options.length; i++) {
            if (selectedRobots.options[i].selected == true) {
            	robots.push(selectedRobots.options[i].value)
            }
        }
   		robotsList = robots.join(",");
   		alert(robotsList);
   		var body = "selectedRobots=" + encodeURIComponent(robotsList);
   		requester.open("POST", "RoboPlayer", true);
   		requester.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
   		requester.setRequestHeader("Content-Length", body.length);
   		requester.setRequestHeader("Connection", "close");
   		requester.send(body+"&gameRounds="+gameRounds);
   		requester.onreadystatechange = displayResult; 
   		requester.send(null);
		
	}

	
}

function displayResult(){
	document.getElementById("resultPane").innerHTML = "<h2>done</h2>";
	if(requester.readyState == 4 && requester.status == 200)
		document.getElementById("resultPane").innerHTML = requester.responseText;
}


function addRemoveRobot(SS1,SS2)
{
    var SelID='';
    var SelText='';
    // Move rows from SS1 to SS2 from bottom to top
    for (i=SS1.options.length - 1; i>=0; i--)
    {
        if (SS1.options[i].selected == true)
        {
            SelID=SS1.options[i].value;
            SelText=SS1.options[i].text;
            var newRow = new Option(SelText,SelID);
            SS2.options[SS2.length]=newRow;
            SS1.options[i]=null;
        }
    }
    
  //  SelectSort(SS2);
}


function SelectSort(SelList)
{
    var ID='';
    var Text='';
    for (x=0; x < SelList.length - 1; x++)
    {
        for (y=x + 1; y < SelList.length; y++)
        {
            if (SelList[x].text > SelList[y].text)
            {
                // Swap rows
                ID=SelList[x].value;
                Text=SelList[x].text;
                SelList[x].value=SelList[y].value;
                SelList[x].text=SelList[y].text;
                SelList[y].value=ID;
                SelList[y].text=Text;
            }
        }
    }
}

</script>

</head>
<body>

<form name="Battle" action="/uploadBattle" method="post">
  <div>Number of Rounds:<input type="text" value="5" id= "gameRounds" name="gameRounds"/></div>
  <input type = "button" name = "file" value="Get Robots" onclick="getRobots()"> <br>
		<div id="AvailableRobotsDiv">
		<select name='availableRobots' id='availableRobots' size='10' style='width: 150px;' multiple="multiple"></select>
	    </div>
	<div id="AddRemoveDiv">
		<table>
			<tr><td><input type="button" value="Add" onclick="addRemoveRobot(document.Battle.availableRobots, document.Battle.selecetdRobots)" /></td></tr>
			<tr><td><input type="button" value="Remove" onclick="addRemoveRobot(document.Battle.selecetdRobots, document.Battle.availableRobots)" /></td></tr>
			<tr><td id="t1"><input type="button" id="RunBattle" onclick="runBattle()" value="Run Battle" /> </td></tr>
		</table>
	</div>
	<div id="SelectedRobotsDiv" >
		<select name="selectedRobots" id="selecetdRobots" size="10" style="width: 150px;" multiple="multiple"></select>				
	</div>
	
</form>
<div id="resultPane">
</div>


</body>
</html>