//列出帳號列表
function listAccount(){
	
	var table = $('listAtb');
	//移除原本存在的List
	removeTableContent(table,2);

	//呼叫Action取得帳號列表並且顯示
	new Ajax.Request('accountList.action',
 	{
    	onSuccess: function(transport){
 
      		var accountlist = transport.responseXML.documentElement.getElementsByTagName("account");

      		for( var i = 0; i< accountlist.length ; i++ ){
      			var name = accountlist[i].firstChild.nodeValue;
      			addAccountHandleMethod(name);
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

//新增一個名稱為name的帳號到Accout List Table
function addAccountHandleMethod(name){
	//取得Account List Table
	var table = $('listAtb');
	
	//tr
	var tr = new Element('tr',{id: 'tr'+name});
	tr.onmouseover = function(){
		this.style.background = "#eef8ff";
	}
	tr.onmouseout = function(){
		this.style.background = "#ffffff";
	}
	//th
	var th = new Element('th');
	var thText = document.createTextNode(name);
	th.appendChild(thText);
	
	//td
	var td = new Element('td',{align: 'center'});	
	//刪除帳號圖示
	var delete_img = new Element('img',{ src: 'img/delete.gif', height: '16',
	  border: '0' , title: '刪除帳號', style: 'cursor: pointer'});
	delete_img.onclick = function(){ deleteAccount(th);}
	//編輯帳號資訊圖示
	var edit_img = new Element('img',{ src: 'img/edit.gif', height: '16',
	  border: '0' , title: '編輯帳號資訊', style: 'cursor: pointer'});
	edit_img.onclick = function(){ editAccountTable(th);}	

	td.appendChild(edit_img);
	td.appendChild(delete_img);
	tr.appendChild(th);
	tr.appendChild(td);
	
	var tbody = table.childElements()[0];
	tbody.insert(tr);
}

//列出新增帳號的表格
function createAccountTable(){

	var div = $('switch');
	//若表格原本就存在則刪除表格
	if( $('CAT')){
		div.innerHTML = "";
		return;
	}
	
	div.innerHTML = "";
	//表格內容
	var table = "<table id='CAT' border='1' class='ReportBorder' cellpadding='2' cellspacing='0'	width='50%'>" +
	"<tr><td colspan='2' class='ReportSuccessHead' width='100%'>" +
	"<img style='cursor: pointer' onclick='' src='img/adduser.gif' title='新增帳號' height='16' border='0'/>" +
	"新增帳號</td></tr><tr><td class='ReportHead' width='15%' align='center'>帳號</td>" +
	"<td width='40%'><input size='15' maxlength='15' type='text' id='newAccount' onblur='validationAccount(this.value)'>" +
	"<label id='accoutCheck'></label></td></tr><tr>" +
	"<td class='ReportHead' align='center'>密碼</td>" +
	"<td><input size='15' maxlength='15' type='password' id='newPasswd'></td>" +
	"</tr><tr><td align='right' colspan='2'>" +
	"<input type='button' value='送出' onclick='createAccount()'>" +
	"</td></tr></table>";
	
	div.innerHTML = table;
}

var validateFlag = false;
//驗證帳號是否存在與有效
function validationAccount(id){
	
	var label = $('accoutCheck');

	if( id == "" || id == null ){
		label.innerHTML = "";
		validateFlag = false;
		return false;
	}	
	
	//呼叫Action驗證帳號是否存在與有效
	new Ajax.Request('accountIdCheck.action',
 	{
 		parameters: { account: id },
    	onSuccess: function(transport){
 			var result = transport.responseText;

      		if( result == "true" ){
      			label.innerHTML = "<img src='img/icon_ok.jpg' height='16' border='0'>";
      			validateFlag = true;
      			return true;   			
			} else {
				label.innerHTML = "<img src='img/icon_not.jpg' height='16' border='0'>";
				validateFlag = false;
				return false;
			}
      		
    	}
  	});	
}

//建立帳號, 利用Ajax呼叫Action
function createAccount(){

	var newAccount = $('newAccount').value;
	var newPasswd = $('newPasswd').value;

	//帳號或密碼為空則不新增
	if( newAccount == "" ){
		window.alert('帳號為空');
		return;
	}
	if( newPasswd == ""  ){
		window.alert('密碼為空');
		return;
	}
	
	//帳號為已存在或錯誤格式則顯示無法新增與原因
	if( !validateFlag ){
		window.alert('無法新增原因如下:\n1.帳號已存在\n2.帳號只允許英文與數字');
		return;
	}

	//呼叫Action新增帳號密碼
	new Ajax.Request('createAccount.action',
 	{
 		parameters: { account: newAccount, passwd: newPasswd },
    	onSuccess: function(transport){
 			var result = transport.responseText;
 			//新增成功則將其加入List Account Table且改新增之圖示
      		if( result == "true" ){
      			$('accoutCheck').innerHTML = '<img src="img/icon_not.jpg" height="16" border="0">';
      			addAccountHandleMethod(newAccount);
			} else {
				window.alert('建立帳號失敗，請重新再試。');
			}
    	}
  	});	

}

//刪除帳號
function deleteAccount(th){
	
	//在表格內顯示的id,由於傳入的經過更改無法改到,保險起見使用取得table值
	var accountID = th.innerHTML;
	new Ajax.Request('deleteAccount.action' ,
	{
		parameters: { account: accountID },
		onSuccess: function(transport){	
			var result = transport.responseText;
			//刪除成功則將表格那欄內容給刪除
			if( result == "true" ){
				var deleteTr = $("tr" + accountID);
				var patent = deleteTr.parentNode;
				patent.removeChild(deleteTr);
				validationAccount($('newAccount').value);
			} else {
				window.alert('刪除帳號失敗，請重新再試');;
			}
		}
	});	
}

//列出修改帳號與密碼的表格
function editAccountTable(th){

	var accountid = th.innerHTML;

	var div = $('switch');
	//若表格原本就存在,就刪除表格
	if( $('EAT') ){
		div.innerHTML = "";
		return ;
	}
	
	div.innerHTML = "";
	//表格內容
	var table = "<table id='EAT' border='1' class='ReportBorder' cellpadding='2' cellspacing='0' width='50%'>" +
	"<tr><td colspan='2' class='ReportSuccessHead' width='100%'>" + 
	"<img style='cursor: pointer' src='img/edit_account.gif' title='修改帳號與密碼' height='16' border='0'/>" +
	"修改帳號資訊</td></tr><tr>" +
	"<td align='center' class='ReportHead' width='15%'>舊帳號</td><td width='40%' class='listFont' id='oldId'>" + accountid + "</td>" +
	"</tr><tr><td align='center' class='ReportHead'>新帳號</td>" +
	"<td><input size='15' maxlength='15' type='text' id='newId' onblur='validationAccount(this.value)'>" +
	"<label id='accoutCheck'></label></td></tr><tr>" +
	"<td align='center' class='ReportHead'>新密碼</td><td><input size='15' maxlength='15' type='password' id='newPasswd'></td>" +
	"</tr><tr><td colspan='2' align='right'><input type='button' value='送出' onclick='editAccount()'/>" +
	"</td></tr></table>";
	
	div.innerHTML = table;
}

//進行帳號資訊修改
function editAccount(){
	
	var oldId = $('oldId').innerHTML;
	var newId = $('newId').value;
	var newPasswd = $('newPasswd').value;
	
	//代表帳號不修改
	if( newId == null || newId == "" ){
		newId = oldId;
	}
	
	//呼叫Action修改帳號密碼
	new Ajax.Request('editAccount.action',
 	{
 		parameters: { oldaccount: oldId, newaccount: newId, newpasswd: newPasswd },
    	onSuccess: function(transport){
 			var result = transport.responseText;
 			//修改帳號成功則做以下動作
      		if( result == "true" ){
				//取得原本帳號的tr與th並且進行修改
				var tr = $('tr'+oldId);
				var th = tr.getElementsByTagName('th')[0];
				tr.id = 'tr' + newId; //修改tr值
				th.innerHTML = newId; //修改th內的accountId
				
				//修改帳號資訊列表之名稱
				$('oldId').innerHTML = newId;
				//清空使用者之輸入
				validationAccount("");
				$('newId').value = "";
				$('newPasswd').value = "";
			} else {
				window.alert('修改失敗，請重新再試。');
			}
    	}
  	});	
}





