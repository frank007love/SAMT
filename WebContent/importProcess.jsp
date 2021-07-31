<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title></title>
<style type="text/css">
@import url(css/tab.css);
@import url(css/style.css);
@import url(css/report.css);
</style>
</head>
<SCRIPT LANGUAGE="JavaScript" SRC="js/prototype.js"></SCRIPT>
<script type="text/javascript">

function loadingState(){
	return "<img src='img/Loading.gif' title='���椤'><b>Loading...</b>";
}

function okState(){
	return "<img src='img/check.png' height='16' border='0' title='����'><b>Complete</b>";
}

function errorState(){
	return "<img src='img/error.png' height='16' border='0' title='����'><b>Error</b>";
}

function warningState(){
	return "<img src='img/warning.png' height='16' border='0' title='����'><b>Warning</b>";
}

//�I�sAction�B�z�פJ�s��
function importGroup(){
	new Ajax.Request('importAAProfile.action',
 	{
 		parameters: { operation: '1' },
    	onSuccess: function(transport){
 			var result = transport.responseText;
 			
 			switch(result){
 			case '0':
 				$('group').innerHTML = errorState();
 				break;
 			case '1':
 				$('group').innerHTML = warningState();
 				break;
 			case '2':
 				$('group').innerHTML = okState();
 				break;
 			default:
 				$('group').innerHTML = errorState();
 				break;
 			}
 			parent.initReport();
 			importAccount();
    	}
  	});	
}

//�I�sAction�B�z�פJ�s�զ���
function importGroupMember(){
	new Ajax.Request('importAAProfile.action',
 	{
 		parameters: { operation: '2' },
    	onSuccess: function(transport){
 			var result = transport.responseText;

  			switch(result){
 			case '0':
 				$('groupMember').innerHTML = errorState();
 				break;
 			case '1':
 				$('groupMember').innerHTML = warningState();
 				break;
 			case '2':
 				$('groupMember').innerHTML = okState();
 				break;
 			default:
 				$('groupMember').innerHTML = errorState();
 				break;
 			}     		
 			importPermission();
    	}
  	});	
}

//�I�sAction�B�z�פJ�v��
function importPermission(){
	new Ajax.Request('importAAProfile.action',
 	{
 		parameters: { operation: '3' },
    	onSuccess: function(transport){
 			var result = transport.responseText;

  			switch(result){
 			case '0':
 				$('permission').innerHTML = errorState();
 				break;
 			case '1':
 				$('permission').innerHTML = warningState();
 				break;
 			case '2':
 				$('permission').innerHTML = okState();
 				break;
 			default:
 				$('permission').innerHTML = errorState();
 				break;
 			}     	  
 			importSVNFolder();			
    	}
  	});	
}

//�I�sAction�B�z�פJ�b��
function importAccount(){
	new Ajax.Request('importAAProfile.action',
 	{
 		parameters: { operation: '4' },
    	onSuccess: function(transport){
 			var result = transport.responseText;

  			switch(result){
 			case '0':
 				$('account').innerHTML = errorState();
 				break;
 			case '1':
 				$('account').innerHTML = warningState();
 				break;
 			case '2':
 				$('account').innerHTML = okState();
 				break;
 			default:
 				$('account').innerHTML = errorState();
 				break;
 			}         
 			importGroupMember();  			      		
    	}
  	});	
}

//�I�sAction�B�z�פJSVN Folder
function importSVNFolder(){
	new Ajax.Request('importAAProfile.action',
 	{
 		parameters: { operation: '5' },
    	onSuccess: function(transport){
 			var result = transport.responseText;
 			
  			switch(result){
 			case '0':
 				$('svnfolder').innerHTML = errorState();
 				break;
 			case '1':
 				$('svnfolder').innerHTML = warningState();
 				break;
 			case '2':
 				$('svnfolder').innerHTML = okState();
 				break;
 			default:
 				$('svnfolder').innerHTML = errorState();
 				break;
 			}          		
    	}
  	});	
}

//��l��process Table
function initPTable(){
	var trA = $('processTB').getElementsByTagName("tr");
	for( var i = 2 ; i < trA.length ; i++ ){
		trA[i].onmouseover = function(){ 
			this.style.background = "#eef8ff"; 
		};
		trA[i].onmouseout = function(){ 
			this.style.background = "#ffffff"; 
		};
	}
}

//�i��פJ�ʧ@
var process_waiting_time = 1000;
function process(){	
	//�פJ�s�է����פJ�����P�v��,�̫�פJSVN Folder
	setTimeout('importGroup()',process_waiting_time);
}

//������l�ƪ��ʧ@
function init(){
	//�Nparent������iframe resize
	parent.iframeResize();
	//��l��process table�����A
	initPTable();
	//�}�l�i��פJ�ʧ@
	$('group').innerHTML = loadingState();
	$('groupMember').innerHTML = loadingState();
	$('permission').innerHTML = loadingState();
	$('account').innerHTML = loadingState();
	$('svnfolder').innerHTML = loadingState();
	process();
}

</script>

<body onload='init()'>
<table id="processTB" border="1" class="ReportBorder" cellpadding="2" cellspacing="0"
	width="100%">
<tr>
	<td colspan="2" class="ReportSuccessHead" width="30%">
	<img style="cursor: pointer" onclick="" src="img/import.png" title="�פJ" height="16" border="0"/>
	�פJ�ɮ�</td>
</tr>
<tr align="center">
	<td class="ReportHead" width="30%">�ʧ@</td>
	<td class="ReportHead" width="10%">���A</td>
</tr>
<tr align="center" onclick="parent.loadReport('group')">
	<th class="ReportBody" width="30%">�פJ�s��</th>
	<td class="ReportBody" width="10%" id="group"></td>
</tr>
<tr align="center" onclick="parent.loadReport('groupMember')">
	<th class="ReportBody" width="30%">�פJ�s�զ���</th>
	<td class="ReportBody" width="10%" id="groupMember"></td>
</tr>
<tr align="center" onclick="parent.loadReport('permission')">
	<th class="ReportBody" width="30%">�פJ�v��</th>
	<td class="ReportBody" width="10%" id="permission"></td>
</tr>
<tr align="center" onclick="parent.loadReport('account')">
	<th class="ReportBody" width="30%">�פJ�b��</th>
	<td class="ReportBody" width="10%" id="account"></td>
</tr>
<tr align="center" onclick="parent.loadReport('svnfolder')">
	<th class="ReportBody" width="30%">�פJSVN Folder</th>
	<td class="ReportBody" width="10%" id="svnfolder"></td>
</tr>
</table>

</body>
</html>