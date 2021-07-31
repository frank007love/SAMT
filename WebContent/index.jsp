<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Subersion Authorization Management Tool</title>
<style type="text/css">
@import url(css/menuTab.css);
</style>

<script type="text/javascript">
<!--
var xmlHttp;
function createXHR(){
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}else if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	if (!xmlHttp) {
		alert('您使用的瀏覽器不支援 XMLHTTP 物件');
		return false;
	}
}

function sendRequest(url){
	createXHR();
	url+='?ts='+new Date().getTime();
	xmlHttp.open('GET',url,true);
	xmlHttp.onreadystatechange=catchResult;
	xmlHttp.send(null);
}

function catchResult(){
	if (xmlHttp.readyState==4){
		if (xmlHttp.status == 200) {
			document.getElementById('tabContent').innerHTML=xmlHttp.responseText;
    		}else{
			var msg='<strong>'+xmlHttp.status+':</strong><br/>'+xmlHttp.statusText;
			document.getElementById('tabContent').innerHTML=msg;
		}
	}

}

function loadTab(obj,url){
	//建立載入畫面
	var tab=document.getElementById('tabContent');
	tab.innerHTML='<img src="img/Loading.gif" width="16" height="16" align="absmiddle" /> Loading...';

	//將 Tab 標籤樣式設成 Blur 型態
	var tabsF=document.getElementById('tabsF').getElementsByTagName('li');
	for (var i=0;i<tabsF.length;i++){
		tabsF[i].setAttribute('id',null);
	}

	//變更分頁標題樣式
	obj.parentNode.setAttribute('id','current');

	//啟動AJAX
	setTimeout('sendRequest(\''+url+'\')',500);
}

function checkGroupName(name){
	

	alert(name);

}

//-->
</script>

</head>
<body>

<div id="tabsF">
<ul>
	<li id="current"><a href="javascript://"
		onclick="loadTab(this,'groupList.action');"><span>群組管理</span></a></li>
	<li><a href="javascript://" onclick="loadTab(this,'group.jsp');"><span>群組成員管理</span></a></li>
	<li><a href="javascript://" onclick="loadTab(this,'#');"><span>權限管理</span></a></li>
	<li><a href="javascript://" onclick="loadTab(this,'#');"><span>帳號管理</span></a></li>
	<li><a href="javascript://" onclick="loadTab(this,'#');"><span>匯入與匯出</span></a></li>
</ul>
</div>
<div id="tabsC">
<div id="tabContent"></div>
</div>

</body>
</html>