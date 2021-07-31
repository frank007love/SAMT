//儲存使用者之設定
function saveSAMTConfig(){
	//取得使用者之輸入
	var url = $('svnurl').value;
	var authorizationpath = $('authorizationPath').value;
	var authenticationpath = $('authenticationPath').value;

	//呼叫Action取得帳號列表並且顯示
	new Ajax.Request('saveSAMTConfig.action',
 	{
 		parameters: { authenticationPath: authenticationpath, authorizationPath: authorizationpath, svnURL: url},
    	onSuccess: function(transport){
 			var result = transport.responseText;
      		if( result == "true" ){
      			window.alert('修改完成,重新載入新配置');
      		} else {
      			window.alert('修改失敗');
      		}
    	}
  	});
}

//讀取SVN IRL
function loadSvnUrl(){
	new Ajax.Request('getSvnURL.action' ,
	{
		onSuccess: function(transport){		
			var url = transport.responseText;
			var svnurl_text = $('svnurl');
			svnurl_text.value = url;
		}		
	});
}

//讀取Authorization檔案路徑
function loadAuthorizationPath(){
	new Ajax.Request('getAuthorizationPath.action' ,
	{
		onSuccess: function(transport){		
			var path = transport.responseText;
			var authorization_text = $('authorizationPath');
			authorization_text.value = path;
		}		
	});
}

//讀取Authentication檔案路徑
function loadAuthenticationPath(){
	new Ajax.Request('getAuthenticationPath.action' ,
	{
		onSuccess: function(transport){		
			var path = transport.responseText;
			var authentication_text = $('authenticationPath');
			authentication_text.value = path;
		}		
	});
}

function validateSVNURL(url){
	/*var label = $('urlCheckLabel');
	new Ajax.Request('validateSVNURL.action' ,
	{
		parameters: {svnURL: url},
		onSuccess: function(transport){		
			var result = transport.responseText;
			window.alert(result);
		}		
	});*/
}

