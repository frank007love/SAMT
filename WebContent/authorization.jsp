<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Authorization Management</title>
<style type="text/css">
@import url(css/tab.css);
@import url(css/style.css);
@import url(css/report.css);
@import url(css/treeview/dhtmlxtree.css);
@import url(css/treeview/dhtmlxtabbar.css);
</style>

<SCRIPT LANGUAGE="JavaScript" SRC="js/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/Authorization.js"></SCRIPT>

<!-- 樹狀目錄相關js -->
<SCRIPT LANGUAGE="JavaScript" SRC="js/TreeView.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/treeview/dhtmlxcommon.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/treeview/dhtmlxtabbar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/treeview/dhtmlxtabbar_start.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/treeview/dhtmlxtree.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
<!-- 
//初始化樹狀目錄
function init(){
	loadTree();
}
-->
</SCRIPT>

</head>
<body onload="init()">
<img src="img/Subversion AMT.png">

<div id="tabsF">
<ul>
	<li><a href="group.jsp"><span>群組管理</span></a></li>
	<li id="current"><a href="authorization.jsp"><span>權限管理</span></a></li>
	<li><a href="account.jsp"><span>帳號管理</span></a></li>
	<li><a href="aaprofile.jsp"><span>匯入與匯出</span></a></li>
	<li><a href="configuration.jsp"><span>組態設定</span></a></li>
</ul>
</div>

<div id="content"><br>
<br>

<table width="100%">
	<tr>
		<td valign="top" width="30%">
		<div style="position: relative; float: left">
		<table border="1" class="ReportBorder" cellpadding="2" cellspacing="0"
			width="100%">
		<tr>
			<td class="ReportSuccessHead" width="40%">
			<img src='img/folder_view.png' title='Folder View' height='16' border='0'>
			Subversion Path List
			</td>
		</tr>
		<tr>
			<td class="ReportHead" width="40%">檔案路徑名稱</td>
		</tr>
		<tr>
			<td>
			<DIV ID="myMenuID"></DIV>
				<div id="svnFileMenu" style="width: 100%; height: 100%; "></div>
			</td>
			</tr>
			</table>
		</div>

		</td>
		<td valign="top" width="20%">
			<table id="listMtb" border="1" class="ReportBorder" cellpadding="2" cellspacing="0"
			width="100%">
				<tr>
					<td colspan="2" class="ReportSuccessHead" width="100%">
					<img style="cursor: pointer" src="img/add_16.gif" title="add permission" height="16" border="0" onclick="addPermissionTable(true)"/>
					Member List
					<input type="hidden" id="pathname" value=""/>
					</td>
				</tr>
				<tr align="center">
					<td class="ReportHead" width="70%">成員名稱</td>
					<td class="ReportHead" width="30%">動作</td>
				</tr>
			</table>
		</td>


		<td valign="top" width="20%">
			<div id="switch">

			</div>
		</td>
	</tr>
</table>
					
<br>
</div>
</body>
</html>