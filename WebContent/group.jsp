<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Group Management</title>

<style type="text/css">
@import url(css/tab.css);
@import url(css/style.css);
@import url(css/report.css);
</style>
<SCRIPT LANGUAGE="JavaScript" SRC="js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/Group.js"></SCRIPT>
<script type="text/javascript">
<!--

function init(){
	setTimeout('listGroup()',500);
}

//-->
</script>

</head>
<body onload="init();">
<img src="img/Subversion AMT.png">

<div id="tabsF">
<ul>
	<li id="current"><a href="group.jsp"><span>群組管理</span></a></li>
	<li><a href="authorization.jsp"><span>權限管理</span></a></li>
	<li><a href="account.jsp"><span>帳號管理</span></a></li>
	<li><a href="aaprofile.jsp"><span>匯入與匯出</span></a></li>
	<li><a href="configuration.jsp"><span>組態設定</span></a></li>
</ul>
</div>

<div id="content">
<br>
<br>

<table width="100%">
	<tr>
		<td valign="top" width="30%">
		<div style="position: relative; float: left">
		<table id="listGtb" border="1" class="ReportBorder" cellpadding="2"
			cellspacing="0" width="100%">
			<tr>
				<td colspan="3" class="ReportSuccessHead">
				<img style="cursor: pointer" onclick="addGroupTable()" src="img/add_16.gif" title="Add group" height="16" border="0"/>
				Group List</td>
			</tr>
			<tr>
				<th class="ReportHead" width="70%">群組名稱</th>
				<th class="ReportHead" width="30%">動作</th>
			</tr>
		</table>
		</div>

		</td>
		<td valign="top" width="20%">
		<div style="position: relative; float: left">
		<table id="listMtb" border="1" class="ReportBorder" cellpadding="2"
			cellspacing="0">
			<tr>
				<td colspan="3" class="ReportSuccessHead">
				<img style="cursor: pointer" onclick="addGroupMemberTable()" src="img/add_16.gif" title="Add Group Member" height="16" border="0"/>
				Member List
				<input type="hidden" id='memberGroupName' value=''>
				</td>
			</tr>
			<tr>
				<th class="ReportHead" width="20%">群組成員</th>
				<th class="ReportHead" width="10%">刪除</th>
			</tr>
		</table>
		</div>
		</td>
		<td id="switch" valign="top" width="20%">
		</td>
	</tr>
</table>

</div>

</body>
</html>