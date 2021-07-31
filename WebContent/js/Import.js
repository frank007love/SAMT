function loadReport(type,form){

	if( running ){
		window.alert("正在進行匯入");
		return ;
	}

	var url = "report/" + type + "Report.xml";

	new Ajax.Request(url,
 	{
    	onSuccess: function(transport){

			if( type == 'group' ){
				generateGroupReport(transport,form);
			} else if ( type == 'groupMember' ){
				generateGroupMemberReport(transport,form);
			} else if ( type == 'permission' ){
				generatePermissionReport(transport,form);
			} else if ( type == 'account' ){
				generateAccountReport(transport,form);
			} else if ( type == 'svnfolder' ){
				generateFolderReport(transport,form);
			}
    	},
    	onFailure: function(){
    		;
      	}
  	});	
	
}

//產生匯入SVN資料夾的報表
function generateFolderReport(transport,form){
	var pathlist = transport.responseXML.documentElement.getElementsByTagName("path");
	
	var table = "<table border='1' class='ReportBorder' cellpadding='2' cellspacing='0' width='100%'>"+
	"<tr><td colspan='3' class='ReportSuccessHead'>" +
	"<img style='cursor: pointer' onclick='' src='img/report.png' title='Log' height='16' border='0'/>Log</td></tr>";
	
	//根據產生的xml檔案讀取資料顯示出來
	for( var i = 0 ; i < pathlist.length ; i++ ){
		var name = pathlist[i].getAttribute('name');
		var status = pathlist[i].getAttribute('status');
		getStatusString(status);
		if( form == 1 ){
			;
		} else {
			table += "<tr><td class='ReportBody'><b>" + name + getStatusString(status) +"</b></td></tr>";
		}
	}
	table += "</table>";
	//顯示
	$('report').innerHTML = table;	
}

//產生匯入權限的報表
function generatePermissionReport(transport,form){
	var pathlist = transport.responseXML.documentElement.getElementsByTagName("path");

	var table = "<table border='1' class='ReportBorder' cellpadding='2' cellspacing='0' width='100%'>"+
	"<tr><td colspan='3' class='ReportSuccessHead'>" +
	"<img style='cursor: pointer' onclick='' src='img/report.png' title='Log' height='16' border='0'/>Log</td></tr>";
	//圖型Report
	if( form == 1 )
		table += "<tr align='center'><td class='ReportHead'>加入的路徑名稱</td><td class='ReportHead'>匯入群組成員名稱</td><td class='ReportHead'>結果</td></tr>";
	
	for( var i = 0 ; i < pathlist.length ; i++ ){
		var pname = pathlist[i].getAttribute('name');
		var memberlist = pathlist[i].getElementsByTagName('pathmember');
		for( var j = 0 ; j < memberlist.length ; j++ ){
			var mname = memberlist[j].getAttribute('name');
			var mtype = memberlist[j].getAttribute('type');
			var status = memberlist[j].getAttribute('status');
			getStatusString(status);
			if( form == 1 ){
				//內容
				table += "<tr align='center'>";
				//針對第一個欄位使用row span使得重複資訊只要出現一次
				if( j == 0 ){
					table += "<td valign='top' class='ReportBody' rowspan='" + memberlist.length + "'><b>" + pname + "</td>";
				}
				//路徑成員內容
				table += "<td class='ReportBody'><b>" + 
				getMemberTypeImg(mtype) + mname + "</td><td class='ReportBody'>" + getStatusImg(status) + "</td</tr>";
			} else {
				
				table += "<tr><td class='ReportBody'><b>" + pname + "下的成員" + getMemberTypeImg(mtype) + "" + mname + "";
				table += getStatusString(status) + "</b></td></tr>";
			
			}
		}
	}
	
	table += "</table>";
	//顯示
	$('report').innerHTML = table;
}



//產生匯入帳號的報表
function generateAccountReport(transport,form){
	var grouplist = transport.responseXML.documentElement.getElementsByTagName("account");
	
	var table = "<table border='1' class='ReportBorder' cellpadding='2' cellspacing='0' width='100%'>"+
	"<tr><td colspan='2' class='ReportSuccessHead'>" +
	"<img style='cursor: pointer' onclick='' src='img/report.png' title='Log' height='16' border='0'/>Log</td></tr>";
	
	if( form == 1 ){
		table += "<tr align='center'><td class='ReportHead'>匯入帳號名稱</td><td class='ReportHead'>結果</td></tr>";
	}
	
	//根據產生的xml檔案讀取資料顯示出來
	for( var i = 0 ; i < grouplist.length ; i++ ){
		var name = grouplist[i].getAttribute('name');
		var status = grouplist[i].getAttribute('status');
		getStatusString(status);
		if( form == 1 ){
			//內容
			table += "<tr align='center'><td class='ReportBody'><b>" + name +"</td><td class='ReportBody'>" + 
			getStatusImg(status) + "</td></tr>";
		} else {
			table += "<tr><td class='ReportBody'><b>帳號" + name + getStatusString(status) +"</b></td></tr>";
		}
	}
	table += "</table>";
	//顯示
	$('report').innerHTML = table;
}

//產生匯入群組成員的報表
function generateGroupMemberReport(transport,form){
	var grouplist = transport.responseXML.documentElement.getElementsByTagName("group");
	
	var table = "<table border='1' class='ReportBorder' cellpadding='2' cellspacing='0' width='100%'>"+
	"<tr><td colspan='3' class='ReportSuccessHead'>" +
	"<img style='cursor: pointer' onclick='' src='img/report.png' title='Log' height='16' border='0'/>Log</td></tr>";
	
	
	if( form == 1 ){
		table += "<tr align='center'><td class='ReportHead'>加入的群組名稱</td><td class='ReportHead'>匯入群組成員名稱</td><td class='ReportHead'>結果</td></tr>";
	}
	
	for( var i = 0 ; i < grouplist.length ; i++ ){
		var gname = grouplist[i].getAttribute('name');
		var memberlist = grouplist[i].getElementsByTagName('groupMember');
		for( var j = 0 ; j < memberlist.length ; j++ ){
			var mname = memberlist[j].getAttribute('name');
			var mtype = memberlist[j].getAttribute('type');
			var status = memberlist[j].getAttribute('status');
			getStatusString(status);
			if( form == 1 ){
				//內容
				table += "<tr align='center'>";
				//針對第一個欄位使用row span使得重複資訊只要出現一次
				if( j == 0 ){
					table += "<td valign='top' class='ReportBody' rowspan='" + memberlist.length + "'><b>" + gname + "</td>";
				}
				//群組成員內容
				table += "<td class='ReportBody'><b>" + 
				getMemberTypeImg(mtype) + mname + "</td><td class='ReportBody'>" + getStatusImg(status) + "</td</tr>";
			} else {
				table += "<tr><td class='ReportBody'><b>群組" + gname + "成員" + getMemberTypeImg(mtype) + mname;
				table += getStatusString(status) + "</b></tr></td>";
			}
		
		}
	}
	
	table += "</table>";
	//顯示
	$('report').innerHTML = table;
}

//產生匯入群組的報表
function generateGroupReport(transport,form){
	var grouplist = transport.responseXML.documentElement.getElementsByTagName("group");
	
	var table = "<table border='1' class='ReportBorder' cellpadding='2' cellspacing='0' width='100%'>"+
	"<tr><td colspan='2' class='ReportSuccessHead'>" +
	"<img style='cursor: pointer' onclick='' src='img/report.png' title='Log' height='16' border='0'/>Log</td></tr>";
	
	if( form == 1 ){
		table += "<tr align='center'><td class='ReportHead'>匯入群組名稱</td><td class='ReportHead'>結果</td></tr>";
	}
	
	//根據產生的xml檔案讀取資料顯示出來
	for( var i = 0 ; i < grouplist.length ; i++ ){
		var name = grouplist[i].getAttribute('name');
		var status = grouplist[i].getAttribute('status');
		getStatusString(status);
		//內容
		if( form == 1 ){
			table += "<tr align='center'><td class='ReportBody'><b>" + name +"</td><td class='ReportBody'><b>" + 
			getStatusImg(status) + "</td></tr>";
		} else {
			table += "<tr><td class='ReportBody'><b>群組" + name + getStatusString(status) +"</b></td></tr>";
		}
	}
	table += "</table>";
	//顯示
	$('report').innerHTML = table;
}

//根據status取得相對應的圖片
function getStatusImg(status){
	var img = "error";
	if( status == "success" ){
		img = "check";
	} else if( status == "warning" ){
		img = "warning";
	}
	return "<img src='img/" + img +".png' height='16' title='" + status + "' border='0'>";
}

//根據status取得匯入結果的字串
function getStatusString(status){
	var result = "發生錯誤無法匯入";
	if( status == "success" ){
		result = "匯入成功";
	} else if( status == "warning" ){
		result = "有重複名稱將略過";
	}	
	return result;
}

//根據type取得相對應的圖片
function getMemberTypeImg(type){
	return "<img src='img/" + type +".bmp' height='16' title='" + type + "' border='0'>";	
}

