
//List all pathmebmer that the path's user select
function listPathMember(path){
	//將最右邊的table給關掉
	$('switch').innerHTML = "";
	new Ajax.Request('pathMemberList.action',
 	{
 		parameters: { svnpath: path },
    	onSuccess: function(transport){
    		var memberlist = transport.responseXML.documentElement.getElementsByTagName("member");
    		var table = $('listMtb');
    		var pathlab = $('pathname');

    		pathlab.value = path;
			removeTableContent(table,2);
    		
      		for( var i = 0; i< memberlist.length ; i++ ){
      			var name = memberlist[i].firstChild.nodeValue;
      			var type = memberlist[i].getAttribute('type');
      			//將內容新增至Table
      			var tbody = table.childElements()[0];
      			tbody.insert(pathmemberListHandleMethod(name,type));
      		}
    	}
  	});
}

//刪除table內的tr tag,在第i個後
function removeTableContent(table,i){
	//取得tbody tag
	var tbody = table.childElements()[0];
	//Remove each tr tag
	while( tbody.childElements().length != i ){
		tbody.childElements()[i].remove();
	}
}

/*
  Create a tr,contain 'memberName','memberType' img,
  Action 'delete','edit permission'
*/
function pathmemberListHandleMethod(name,type){
	var tr = new Element('tr');
	//mouse event
	tr.onmouseover = function(){
		this.style.background = "#eef8ff";
	}
	tr.onmouseout = function(){
		this.style.background = "#ffffff";
	}
	
    var th = new Element('th');
    var thText = document.createTextNode(name);
 	var td = new Element('td',{align:'center'});
 	
 	//delete img
 	var dimg = new Element('img',{ src: 'img/delete.gif', title: 'Delte', height: '16', border: '0'});
	//edit img tag
	var eimg = new Element('img',{ src: 'img/edit.gif', title: 'Edit Permission', height: '16', border: '0'});
	//member img tag
	var mbimg = new Element('img',{src: 'img/'+type+'.bmp', title: type, height: '16', border: '0'});
	
	//delete href tag
	var da = new Element('a',{href: '#'});
	da.onclick = function(){  
		deletePermission(name,type,tr);
	}
	da.appendChild(dimg);	
	
	//edit href tag	
	var ea = new Element('a',{href: '#'}).update(eimg);
	ea.onclick = function(){ 
		editPermissionTable(name,type,this); 
	}
	ea.member = name + ":" + type;
	
	th.appendChild(mbimg);
	th.appendChild(thText);
	td.appendChild(ea);
    td.appendChild(da);
    tr.appendChild(th);
    tr.appendChild(td);
    return tr;
}


// Show the edit Permission Table
function editPermissionTable(memberName,memberType,ea){
	
	//取得目前在Switch的MemberName
	var memberCheck = $('membercheck');
	if( memberCheck != null ){
		memberCheck = memberCheck.value;
	}
	//判斷memberName是否與點擊的相同,相同代表點兩次
	if( memberCheck == ea.member ){
		$('switch').innerHTML = "";
		return ;
	}
	
	//get pathname to find permission
	var repositorypPath = $('pathname').value;	
	clickFlagA = false;
	
	/*
	  Ajax to action, get the permission data,  
	  and show the member's permission
	*/
	new Ajax.Request('getPermission.action',
 	{	
 		parameters: { path: repositorypPath, name: memberName, type: memberType},
		onSuccess: function(transport){
			var permission = transport.responseText;
			var swichDiv = $('switch');
			//Show permission table
			swichDiv.innerHTML = "<table border='1' class='ReportBorder' cellpadding='2'" +
			"cellspacing='0'width='100%'><tr>" +
			"<td colspan='3' class='ReportSuccessHead' ><img src='img/keys.png' title='修改權限' height='16' border=0>"+
			"Permission</td></tr>" +
			"<tr align='center'><td width='30%' class='ReportHead'>All</td>"+
			"<td class='ReportHead'>Read</td><td class='ReportHead'>Write</td></tr>" +
			"<tr align='center'><td><input type='checkbox' id='all'></td>" +
			"<td><input type='checkbox' id='read'></td>" +
			"<td><input type='checkbox' id='write'></td></tr></table><input id='membercheck' type='hidden' value='" + 
			memberName + ":" + memberType + "'>";
			
			selectMember = memberName + ":" + memberType;
			//Set permission checkbox
			if( permission == "rw" ){
				$('all').checked = true;
				$('read').checked = true;
				$('write').checked = true;
			} else if( permission == "r" ){
				$('read').checked = true;
			} else if( permission == "w" ){
				$('write').checked = true;
			}	
			//Set checkbox event
			$('all').onclick = function(){
				modifyPermission(memberName,memberType,"all");
			}
			$('read').onclick = function(){
				modifyPermission(memberName,memberType,"read");
			}
			$('write').onclick = function(){
				modifyPermission(memberName,memberType,"write");
			}
		}
	});
}

//Modify Path Permission
function modifyPermission(memberName,memberType,op){
	//get pathname to find permission
	var repositorypPath = $('pathname').value;
	var permissionType ="";
	/**
	  Control the check box,
	  if select 'all', then 'read'&'write' selected.
	  if both 'read'&'write' not be selected, then 'all' too. 
	*/
	if( op == "all" ){//Select all checkbox
		if( $('all').checked ){
			$('read').checked = true;
			$('write').checked = true;
			permissionType = "rw";
		} else{
			$('read').checked = false;
			$('write').checked = false;
			permissionType = "";		
		}
	} else {//Select read or write checkbox
		if( $('read').checked && $('write').checked ){
			$('all').checked = true;
			permissionType ="rw";
		} else if( $('read').checked ){
			$('all').checked = false;
			permissionType = "r";
		} else if( $('write').checked ){
			$('all').checked = false;
			permissionType = "w";
		}
		
		if( !$('read').checked || !$('write').checked ){
			$('all').checked = false;
		} 
	}
	/*
	  Ajax to action, modify the Permission data
	*/
	new Ajax.Request('modifyPermission.action',
 	{
 	 	parameters: { path: repositorypPath, name: memberName, type: memberType, permission: permissionType}
 	});
}

//Delete a permissio data
function deletePermission(memberName,memberType,tr){
	//get pathname to find permission
	var repositorypPath = $('pathname').value;
	/*
	  Ajax to action, delete the Permission data,
	  and remove the tr
	*/	
	new Ajax.Request('deletePermission.action',
 	{
 	 	parameters: { path: repositorypPath, name: memberName, type: memberType},
 	 	onSuccess: function(transport){
 	 		var result = transport.responseText;
 	 		//delete success,remove the tr
 	 		if( result == "true" ){
 	 			var patent = tr.parentNode;
 	 			//permission table exist, refresh
 	 			if ( $('addpermissiontb') != null)
 	 				addPermissionTable();
				patent.removeChild(tr);
 	 		} else {
 	 			window.alert("Member doesn't exist.");
 	 		}
 	 	}
 	});
}

var clickFlagA = false;
//列出使用者選擇的路徑中不在成員內的所有群組與帳戶
function addPermissionTable(click){
	//視窗存在若使用者點擊視窗則不出現
	if( click == true  ){
		clickFlagA = !clickFlagA;
		if( clickFlagA == false ){
			$('switch').innerHTML = "";
			return;
		}
	}

	//get pathname to find permission
	var repositorypPath = $('pathname').value;
	var switchDiv = $('switch');
	/*
	  Ajax to action, get the group and account list,
	  and show table
	*/	
	new Ajax.Request('getAllMemberList.action',
 	{
 	 	parameters: { searchname: repositorypPath, op: 'path'},
 	 	onSuccess: function(transport){
 	 		var result = transport.responseXML;
 	 		var memberlist = transport.responseXML.documentElement.getElementsByTagName("member");
 	 		var table = "<table border='1' class='ReportBorder' cellpadding='2'" +
			"cellspacing='0'width='100%' id='addpermissiontb'><tr>" +
			"<td colspan='2' class='ReportSuccessHead' ><img src='img/groupadd.gif' title='新增路徑成員' height='16' border=0>" +
			"Add Member List<input type='hidden' id='addPM'></td></tr>" +
			"<tr align='center'><td width='30%' class='ReportHead'>選擇</td>"+
			"<td class='ReportHead'>成員名稱</td></tr>";
			
			for( var i = 0; i< memberlist.length ; i++ ){
      			var name = memberlist[i].firstChild.nodeValue;
      			var type = memberlist[i].getAttribute('type');
      			
      			 if( type == "group" ){
      			 	table += "<tr align='center' name='apgroup' id='apgroup'><td><input type='checkbox'></td>" +
      			 	"<td><img src='img/group.bmp' height='16' border='0' title='Group'><label class='listFont'>" + 
      			 	name + "</label></td></tr>";
      			 } else {
      			    table += "<tr align='center' name='apaccount' id='apaccount'><td><input type='checkbox'></td>" +
      			 	"<td><img src='img/account.bmp' height='16' border='0' title='Account'><label class='listFont'>" + 
      			 	name + "</label></td></tr>";
      			 }
      		}
 	 		
 	 		table += "<tr><td colspan='2' align='right'>"+
 	 		"<input type='button' value='新增' onclick='addPermission()'></td></tr></table>";
 	 		switchDiv.innerHTML = table;
 	 		$('addPM').value = repositorypPath;
 	 	}
 	});	
}

//Call action to add permission data
function addPermission(){
	//get pathname to find permission
	var repositorypPath = $('addPM').value;
	var grouplist = document.getElementsByName("apgroup");
	var accountlist = document.getElementsByName("apaccount");
	//Add the user select, group part
	for( var i =0; i < grouplist.length ; i++ ){
		var membername = grouplist[i].getElementsByTagName('label')[0].innerHTML;
		var checkbox = grouplist[i].getElementsByTagName('input')[0];
		//Identify the user select, and call action to add
		if( checkbox.checked ){
			/*
			  Ajax to action, add a permission data
			*/
			new Ajax.Request('addPermission.action',
			{
				parameters: { name: membername, type: 'group', path: repositorypPath},
				onSuccess: function(transport){
					var result = transport.responseText;
					if( result == "true" ){
						listPathMember(repositorypPath);
						addPermissionTable();
					}else{
						window.alert("加入失敗");
					}
				}
			});
		}
	}
	//Add the user select, account part
	for( var i =0; i < accountlist.length ; i++ ){
		var membername = accountlist[i].getElementsByTagName('label')[0].innerHTML;
		var checkbox = accountlist[i].getElementsByTagName('input')[0];
		//Identify the user select, and call action to add
		if( checkbox.checked ){
			/*
			  Ajax to action, add a permission data
			*/
			new Ajax.Request('addPermission.action',
			{
				parameters: { name: membername, type: 'account', path: repositorypPath},
				onSuccess: function(transport){
					var result = transport.responseText;
					if( result == "true" ){
						listPathMember(repositorypPath);
						addPermissionTable();
					}else{
						window.alert("加入失敗");
					}
				}
			});
		}
	}
}
