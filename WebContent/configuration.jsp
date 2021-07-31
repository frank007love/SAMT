<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Configuration Management</title>
<style type="text/css">
@import url(css/tab.css);

@import url(css/style.css);
@import url(css/report.css);
</style>
<SCRIPT LANGUAGE="JavaScript" SRC="js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/SAMTConfig.js"></SCRIPT>
<script type="text/javascript">
<!--

function init(){
	loadSvnUrl();
	loadAuthorizationPath();
	loadAuthenticationPath();
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
	<li><a href="account.jsp"><span>帳號管理</span></a></li>
	<li><a href="aaprofile.jsp"><span>匯入與匯出</span></a></li>
	<li id="current"><a href="configuration.jsp"><span>組態設定</span></a></li>
</ul>
</div>

<div id="content"><br>
<br>
<div>
<table id="listGtb" border="1" class="ReportBorder" cellpadding="2"
	cellspacing="0" width="40%">
	<tr>
		<td colspan="3" class="ReportSuccessHead">
		<img style="cursor: pointer" src="img/server_network.png" title="組態設定" height="16" border="0"/>
		Configuration Setting</td>
	</tr>
	<tr>
		<th class="ReportHead" align="left" width="20%">Subversion URL</th>
		<th class="input.field" align="left" width="23%"><input
			id="svnurl" type="text" value="" onblur="validateSVNURL(this.value)"> <label id="urlCheckLabel"></label></th>
	</tr>
	<tr>
		<th class="ReportHead" align="left">AuthenticationFile Path</th>
		<th class="input.field" align="left"><input
			id="authenticationPath" type="text" value=""> <label></label>
		</th>
	</tr>
	<tr>
		<th class="ReportHead" align="left">AuthorizationFile Path</th>
		<th class="input.field" align="left"><input
			id="authorizationPath" type="text" value=""> <label></label>
		</th>
	</tr>

	<tr>
		<td colspan="2" align="right"><input type="button" value="Save" onclick="saveSAMTConfig()">
		</td>
	</tr>
</table>
</div>

<br>
</div>
</body>
</html>