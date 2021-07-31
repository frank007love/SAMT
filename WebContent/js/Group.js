function listGroup(){

	new Ajax.Request('groupList.action',
 	{
    	onSuccess: function(transport){
 
      		var grouplist = transport.responseXML.documentElement.getElementsByTagName("groupname");
		

      		for( var i = 0; i< grouplist.length ; i++ ){
      			var name = grouplist[i].firstChild.nodeValue;
      			var th = addGroupHandleMethod(name);
      			//member table no data, then list the first group's member
      			if( i == 0 && $('memberGroupName').value == "" ){
      				if( $('listMtb').getElementsByTagName("tr").length )
      					listGroupMember(th);
      			}
      		}   		
    	}
  	});

}

function deleteGroupMember(name,type,tr){
	var gname = $('memberGroupName').value;
	//gname = gname.substr(1,gname.length-3);
	
	new Ajax.Request('deletegroupMember.action' ,
	{
		parameters: { membername: name, membertype: type, groupname: gname },
		onSuccess: function(transport){
			var result = transport.responseText;
			
			if( result == "true" ){
				var p = tr.parentNode;
				p.removeChild(tr);
				addGroupMemberTable('delete');
			} else {
				window.alert("Error occur!!");
			}
		}
	});
}

//Group can't show
function listGroupMember(th){
	var group = th.innerHTML;
	$('switch').innerHTML = "";
	new Ajax.Request('groupMemberList.action',
 	{
 		parameters: { groupname: group },
    	onSuccess: function(transport){
      		var memberlist = transport.responseXML.documentElement.getElementsByTagName("member");
      		//移除原本存在的List
      		var table = $('listMtb');
			removeTableContent(table,2);
			
			$('memberGroupName').value = group;
			
      		for( var i = 0; i< memberlist.length ; i++ ){
      			var name = memberlist[i].firstChild.nodeValue;
      			var type = memberlist[i].getAttribute('type');
				//將資訊插入表格
      			var tbody = table.childElements()[0];
      			tbody.insert(memberListHandleMethod(name,type));				 
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

function memberListHandleMethod(name,type){
	var tr = new Element('tr');
	tr.onmouseover = function(){
		this.style.background = "#eef8ff";
	}
	tr.onmouseout = function(){
		this.style.background = "#ffffff";
	}
    var th = new Element('th');
    var thText = document.createTextNode(name);
  	var td = new Element('td',{align: 'center'});
    var img = new Element('img',{ src: 'img/delete.gif', title: 'delete', height: '16', border: '0'});

	//member image
	var mbimg;
	if( type == 'account' ){
		mbimg = new Element('img',{ src: 'img/account.bmp', title: 'account' });
	} else if( type == 'group' ){
		mbimg = new Element('img',{ src: 'img/group.bmp', title: 'group' });
	}			
	mbimg.setAttribute("height","16");
	mbimg.setAttribute("border","0");

	var a = new Element('a',{ href: '#'});
	a.onclick = function(){ deleteGroupMember(name,type,tr);}
	a.appendChild(img);		
	
	th.appendChild(mbimg);
	th.appendChild(thText);
    td.appendChild(a);
    tr.appendChild(th);
    tr.appendChild(td);
    return tr;
}

var addGroupFlag = false;
//After the validate, show the result
function validateHandleMethod(responseText){

	var result;
	if( responseText == "true" ){
		result = "<img src='img/icon_ok.jpg' height='16' border=0>";
		addGroupFlag = true;
	}
	else{
		result = "<img src='img/icon_not.jpg' height='16' border=0>";
		addGroupFlag = false;
	}
	$("checkResult").innerHTML = result;
}
//Check the group name is not exist
function checkGroupName(name){

	if( name == "" ){
		document.getElementById("checkResult").innerHTML = "";
		return;
	}

	new Ajax.Request('groupNameCheck.action',
 	{
 		method: 'post',
 		parameters: { groupname: name  },
    	onSuccess: function(transport){
      		validateHandleMethod(transport.responseText);
    	}
  	});
}

function addGroupHandleMethod(responseText){

	var table = $('listGtb');

	//tr
	var tr = document.createElement("tr");
	
	tr.setAttribute("id","tr"+ responseText);
	tr.onmouseover = function(){
		this.style.background = "#eef8ff";
	}
	tr.onmouseout = function(){
		this.style.background = "#ffffff";
	}

	//th
	var th = document.createElement("th");
	var thText = document.createTextNode(responseText);
	th.appendChild(thText);

	//td
	var td = new Element('td',{align: 'center'});
	//列出群組成員圖示
	var memberview_img = new Element('img',{ src: 'img/groupmember_show.png', height: '16', width: '16',
	  border: '0' , title: '列出群組成員', style: 'cursor: pointer'});
	memberview_img.onclick = function(){ listGroupMember(th);　}
	//刪除群組圖示
	var delete_img = new Element('img',{ src: 'img/delete.gif', height: '16',
	  border: '0' , title: '刪除群組', style: 'cursor: pointer'});
	delete_img.onclick = function(){ deleteGroup(th);}
	//編輯群組名稱圖示
	var edit_img = new Element('img',{ src: 'img/edit.gif', height: '16',
	  border: '0' , title: '編輯名稱', style: 'cursor: pointer'});
	edit_img.onclick = function(){ 
		editGroupTable(th);
	}
	
	td.appendChild(memberview_img);
	td.appendChild(edit_img);
	td.appendChild(delete_img);
	tr.appendChild(th);
	tr.appendChild(td);

    var tbody = table.childElements()[0];
    tbody.insert(tr);		
    
	return th;
}

function addGroup(){
	var newGroupName = document.getElementById('addgroupName').value;

	if( newGroupName == "" ){
		window.alert("Group name is empty");
	} else if( addGroupFlag == false ){
		window.alert("Group name is redundant");
	} else {
		//change the check img
		validateHandleMethod("false");
		//Send to action, add a group
		new Ajax.Request('addGroup.action',
 		{
 			parameters: { name: newGroupName },
    		onSuccess: function(transport){
      			addGroupHandleMethod(newGroupName);
    	 	}
  		});		
		
	}
}

function editGroupTable(th){
	var groupname = th.innerHTML;
	var div = $('switch');
	//若表格原本已存在則刪除
	if( $(groupname) != null ){
		div.innerHTML = "";
		return ; 
	}
	//Remove original table
	div.innerHTML = "";
	//Create table
	var table = "<table id='" + groupname + "' border='1' class='ReportBorder' cellpadding='2'  cellspacing='0' width='100%'>";
	table += "<tr><td colspan='3' class='ReportSuccessHead'><img src='img/groupsave.gif' title='修改群組名稱' height='16' border=0>修改群組名稱</td></tr>";
	table += "<tr><th width='30%' class='ReportHead'>群組名稱</th><td id='editname_td' class='listFont'>" + groupname + "</td></tr>";
	table += "<tr><th  class='ReportHead'>新名稱</th>";
	table += "<td><input id='editname' maxlength='15' size='15' class='input.field' type='text' value='' onblur='validateModifyGroupName()'>";
	table += "<label id='editCheckResult'></label></td>";
	table += "</tr><tr align='right'><td colspan='2'>" +
	 " <input type='button' value='送出' onclick='modifyGroupName()'>";
	table += "</td></tr></table>";
	
	div.innerHTML = table;
}

//show the add group table,contain a text let user input,and a submit button
function addGroupTable(){
	var div = $('switch');
	
	//若Table原本已存在, 則刪除並不顯示
	if( $('addGTB') != null ){
		div.innerHTML = "";
		return;
	}
	//Remove original table
	div.innerHTML = "";
	
	//Create table
	var table = "<table id='addGTB' border='1' class='ReportBorder' cellpadding='2'cellspacing='0' width='100%'>" +
	"<tr><td colspan='2' class='ReportSuccessHead'><img src='img/groupadd.gif' title='新增群組' height='16' border=0>新增群組</td></tr>" +
	"<tr><th class='ReportHead' width='30%'>群組名稱</th><th align='left'>" +
	"<input type='text' size='15' maxlength='15' id='addgroupName'><label id='checkResult'></label></th>" +
	"</tr><tr><td colspan='2' align='right'><input type='button' onclick='addGroup();' value='送出'></td>" +
	"</tr></table>";
			
	div.innerHTML = table;
	$('addgroupName').onblur = function(){
		checkGroupName(this.value,validateHandleMethod);
	}
	
}

function deleteGroup(th){
	//在表格內顯示的id,由於傳入的經過更改無法改到,保險起見使用取得table值
	var groupname = th.innerHTML;
	new Ajax.Request('deleteGroup.action' ,
	{
		parameters: { name: groupname },
		onSuccess: function(transport){	
			var deleteTr = $("tr"+groupname);
			//Catch add group input
			var groupName = $('addgroupName');
			//if no data, don't change check flag
			if( groupName != null && groupName.value != "" ){
				checkGroupName($('addgroupName').value);
			}
			var patent = deleteTr.parentNode;
			listGroupMember(groupname,true);
			patent.removeChild(deleteTr);
		}
	});	
}

//Validate is the name is redundant
function validateModifyGroupName(){

	var flag = false;

	if( $('editname').value == "" ){
		$('editCheckResult').innerHTML = "";
		return;
	}	
	
	new Ajax.Request('groupNameCheck.action',
 	{
 		method: 'post',
 		parameters: { groupname: $('editname').value  },
    	onSuccess: function(transport){
      		var result = transport.responseText;
      		if( result == "true" ){
				$('editCheckResult').innerHTML = '<img src="img/icon_ok.jpg" height="16" border="0">';
				flag =  true;
			}
			else{
				$('editCheckResult').innerHTML = '<img src="img/icon_not.jpg" height="16" border="0">';
				flag = false;
			}  		
      	
    	}
  	});
  	
}

/*
  Modify group name, call action to modify,
  and change the related table,
  Table: Grouplist,Memberlist,修改群組名稱的群組名稱
*/
function modifyGroupName(){
	
	var _oldname = $('editname_td').innerHTML;
	var _newname = $('editname').value;

	//the text is empty
	if( _newname == null || _newname == "" ){
		window.alert("Group name is empty");
		return;
	}
	//call ajax method to handle modifyname
	new Ajax.Request('modifyGroupName.action',
 	{
 		method: 'post',
 		parameters: { oldname: _oldname, newname: _newname },
    	onSuccess: function(transport){
    	
      		var result = transport.responseText;
      		//Modify Success
      		if( result == "true" ){
      			//Modify the validate image
				$('editCheckResult').innerHTML = '<img src="img/icon_not.jpg" height="16" border="0">';

				var table = $('listGtb');
				if( table.childNodes[0].length ){ // fire fox
					for( var i = table.getElementsByTagName("tr").length-1 ; i >= 2 ; i-- ){
						var tr = table.getElementsByTagName("tr")[i];
						if( tr.id == "tr"+_oldname ){
							tr.id = "tr" + _newname;
							tr.getElementsByTagName("th")[0].innerHTML = _newname;
						}
					}
				} else{ //ie
					for( var i = table.childNodes[0].childNodes.length-1 ; i >= 2 ; i-- ){
						var tr = table.childNodes[0].childNodes[i];
						if( tr.id == "tr"+_oldname ){
							tr.id = "tr" + _newname;
							tr.getElementsByTagName("th")[0].innerHTML = _newname;
						}
					}
				}
				
				//Refresh Member Table
				var th = new Element('th').update($('memberGroupName').value);
				listGroupMember(th);
				//若MemberTable群組與要修改的相同,則修改新名稱
				if( $('memberGroupName').value == oldname ){
					$('memberGroupName').value = _newname;
				}
				//Refresh ModifyTable groupname
				$('editname_td').innerHTML = _newname;
				
			}//Modify Failure
			else{
				window.alert("You can't modify");
			}  		
    	}
  	});
}

//列出所有使用者選擇之群組中非成員的群組與帳戶
function addGroupMemberTable(operation){
	//get pathname to find permission
	var gname = $('memberGroupName').value;
	var switchTab = $('switch');
	
	if( $('AGMT') != null && operation!='delete' ){
		switchTab.innerHTML = "";
		return;
	}

	if( gname.empty() || gname == null ){
		window.alert('尚未選擇群組!!');
		return;
	}
	
	/*
	  Ajax to action, get the group and account list,
	  and show table
	*/	
	new Ajax.Request('getAllMemberList.action',
 	{
 	 	parameters: { searchname: gname,op: 'group'},
 	 	onSuccess: function(transport){
 	 		var result = transport.responseXML;
 	 		var memberlist = transport.responseXML.documentElement.getElementsByTagName("member");
 	 		var table = "<table id='AGMT' border='1' class='ReportBorder' cellpadding='2'" + 	 		
			"cellspacing='0'width='100%' id='addpermissiontb'><tr>" +
			"<td colspan='2' class='ReportSuccessHead' ><img src='img/groupadd.gif' title='新增群組成員' height='16' border=0>" +
			"Add Member List<input type='hidden' id='addGM'></td></tr>" +
			"<tr align='center'><td width='30%' class='ReportHead'>選擇</td>"+
			"<td class='ReportHead'>成員名稱</td></tr>";
			
			for( var i = 0; i< memberlist.length ; i++ ){
      			var name = memberlist[i].firstChild.nodeValue;
      			var type = memberlist[i].getAttribute('type');
      			
      			 if( type == "group" ){
      			 	table += "<tr align='center' name='agroup' id='agroup'><td><input type='checkbox'></td>" +
      			 	"<td><img src='img/group.bmp' height='16' border='0' title='Group'><label class='listFont'>" + 
      			 	name + "</label></td></tr>";
      			 } else {
      			    table += "<tr align='center' name='aaccount' id='aaccount'><td><input type='checkbox'></td>" +
      			 	"<td><img src='img/account.bmp' height='16' border='0' title='Account'><label class='listFont'>" + 
      			 	name + "</label></td></tr>";
      			 }
      		}
      		
 	 		table += "<tr><td colspan='2' align='right'>"+
 	 		"<input type='button' value='新增' onclick='addGroupMember()'></td></tr></table>";
 	 		switchTab.innerHTML = table;
 	 		$('addGM').value = gname;
 	 	}
 	});	
}

function addGroupMember(){
	//get groupname
	var groupname = $('addGM').value;
	var grouplist = document.getElementsByName("agroup");
	var accountlist = document.getElementsByName("aaccount");
	//Add the user select, group part
	for( var i =0; i < grouplist.length ; i++ ){
		var membername = grouplist[i].getElementsByTagName('label')[0].innerHTML;
		var checkbox = grouplist[i].getElementsByTagName('input')[0];
		//Identify the user select, and call action to add
		if( checkbox.checked ){
			/*
			  Ajax to action,　新增群組成員
			*/
			new Ajax.Request('addGroupMember.action',
			{
				parameters: { memberName: membername, type: "group", groupName: groupname},
				onSuccess: function(transport){
					var result = transport.responseText;
					if( result == "true" ){
						var th = new Element('th').update(groupname);
						listGroupMember(th);
						$('memberGroupName').value = groupname;
						addGroupMemberTable();
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
			  Ajax to action,　新增群組成員
			*/
			new Ajax.Request('addGroupMember.action',
			{
				parameters: { memberName: membername, type: "account", groupName: groupname},
				onSuccess: function(transport){
					var result = transport.responseText;
					if( result == "true" ){
						var th = new Element('th').update(groupname);
						listGroupMember(th);
						$('memberGroupName').value = groupname;
						addGroupMemberTable();
					}else{
						window.alert("加入失敗");
					}
				}
			});
		}
	}		
}

