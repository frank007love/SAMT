<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>AAProfile Management</title>
<style type="text/css">
@import url(css/tab.css);
@import url(css/style.css);
@import url(css/report.css);
</style>
<SCRIPT LANGUAGE="JavaScript" SRC="js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/Import.js"></SCRIPT>
<script type="text/javascript">

var running = true;
//初始化報表資訊
function initReport(){
	running = false;
	loadReport('group');
}

//Resize 顯示上傳的iframe
function iframeResize(){
	$('iframeTd').width = "50%";
	$('reportTd').width = "50%";
}

</script>


</head>
<body>
<img src="img/Subversion AMT.png">

<div id="tabsF">
<ul>
	<li><a href="group.jsp"><span>群組管理</span></a></li>
	<li><a href="authorization.jsp"><span>權限管理</span></a></li>
	<li><a href="account.jsp"><span>帳號管理</span></a></li>
	<li id="current"><a href="aaprofile.jsp"><span>匯入與匯出</span></a></li>
	<li><a href="configuration.jsp"><span>組態設定</span></a></li>
</ul>
</div>

<div id="content">
<br><br>

<table border="0" width="100%">
<tr  valign="top">
	<td id="iframeTd" width="100%">
		<iframe id="uploadFrame" src="importForm.jsp" width="100%" height="250" frameborder="0" scrolling="auto"></iframe>
	</td>
	<td id="reportTd" width="0%">
		<div style="height: 10px"></div>
		<div id="report">
		</div>
	</td>
</tr>
</table>

<br>
</div>
</body>
</html>