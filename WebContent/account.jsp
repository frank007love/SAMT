<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Authenticaiton Management</title>
<style type="text/css">
@import url(css/tab.css);
@import url(css/style.css);
@import url(css/report.css);
</style>
<SCRIPT LANGUAGE="JavaScript" SRC="js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/Account.js"></SCRIPT>
<script type="text/javascript">
<!--

function init(){
	setTimeout('listAccount()',1000);
}

//-->
</script>
</head>
<body onload="init()">
<img src="img/Subversion AMT.png">

<div id="tabsF">
<ul>
	<li><a href="group.jsp"><span>群組管理</span></a></li>
	<li><a href="authorization.jsp"><span>權限管理</span></a></li>
	<li id="current"><a href="account.jsp"><span>帳號管理</span></a></li>
	<li><a href="aaprofile.jsp"><span>匯入與匯出</span></a></li>
	<li><a href="configuration.jsp"><span>組態設定</span></a></li>
</ul>
</div>

<div id="content">
<br>
<br>

<table width="100%">
	<tr>
		<td valign="top" width="10%">
		<div style="position: relative; float: left">
		<table id="listAtb" border="1" class="ReportBorder" cellpadding="2" cellspacing="0"
			width="100%">
		<tr>
			<td colspan="2" class="ReportSuccessHead" width="30%">
			<img style="cursor: pointer" onclick="createAccountTable()" src="img/add_16.gif" title="新增帳號" height="16" border="0"/>
			Account List</td>
		</tr>
		<tr align="center">
			<td class="ReportHead" width="30%">帳號</td>
			<td class="ReportHead" width="10%">動作</td>
		</tr>

		</table>
		</div>
		</td>
		
		<td valign="top" width="20%">
		<div  id="switch">
		</div>
		</td>
		
		<td valign="top" width="10%">
		<div>
		</div>
		</td>
	</tr>
</table>

<br>
</div>
</body>
</html>