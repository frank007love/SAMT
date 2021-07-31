//設定Root之節點,在進入頁面時自動去找尋第一層路徑
var node="/";
var tree;

//初始化Treeview
function loadTree(){
	//svnFileMenu為顯示div之id
	tree = new dhtmlXTreeObject("svnFileMenu","100%","100%",0);
	
	//設定圖片路徑
	tree.setImagePath("img/treeview/");
	
	//處理使用者點擊時呼叫的method
	tree.setOnClickHandler(
		function(id){
			openPathDocs(id);
	});

	//初始化root node
	tree.insertNewItem(0,"/","root",0,"folderClosed.gif",0,0,"");
	//自動讀取root的下一層目錄
	autoselectNode();
}
		
//自動選擇節點, 預設為root
function autoselectNode(){
	tree.selectItem(node,true);
	tree.openItem(node);
} 		
		
/*
使用者選擇打開資料夾,到Server端取得底下路徑
傳入的id即為所選擇之路徑
*/
function openPathDocs(id){
	//使用者點擊Loading則不做任何事情
	if( id.endsWith('tempNode')){
		return;
	}
	//取得節點數量
	var child = tree.hasChildren(id);
	//藉由'dir:' or 'file:'判斷為資料夾或檔案
	var type = id.substr(0,id.indexOf(':'));
	//列出成員
	listPathMember(id.substr(id.indexOf(':')+1));
	
	//有子節點存在則不呼叫Ajax取得底下節點
	if( child == 0 && type != "file" && !noChildNodeList.get(id) ){
		//設置Loading之圖案
		tree.insertNewItem(id,id+"tempNode","Loading...",0,"Loading.gif",0,0,"");
		//呼叫action取得底下目錄
		setTimeout('openPathDocsHandler(\"' + id + '\")',100);
	}
}

var noChildNodeList = new Hash(); 

//呼叫Action處理顯示節點
function openPathDocsHandler(id){

	var modifyId = id.substr(id.indexOf(':')+1);
	//利用Ajax到Server端取得底下路徑
	new Ajax.Request('getSubFile.action',
 	{
 		parameters: { parent: modifyId },
    	onSuccess: function(transport){
    		//刪除Loading圖案之節點
    		tree.deleteItem(id+"tempNode");
    			
    		//開始處理取得下層目錄描述之XML檔案
      		var pathlist = transport.responseXML.documentElement.getElementsByTagName("subpath");
      		
      		for( var i = 0; i< pathlist.length ; i++ ){
      			//取得檔案名稱與型態, 型態為 dir或是file
      			var name = pathlist[i].firstChild.nodeValue;
      			var type = pathlist[i].getAttribute('type');
      			//設置路徑, 為檔案名稱加上之前的路徑
      			var path = modifyId;
      			if( modifyId == "/" ){
      				path += name;
     			} else {
   					path += "/" + name;
   				}
      			//根據type顯示圖
      			var img = getIMG(name,type);
      			//新增一節點, p1: parent, p2: new node id, p3: context
      			tree.insertNewItem(id,type+":"+path,name,0,img,0,0,"");
      		}
      		//無下層目錄
      		if( pathlist.length == 0 ){
      			noChildNodeList.set(id,true); 
      		}
      	},
      	onFailure: function(){
      		window.alert("與SVN連線失敗,請重新再試!!");
      		//刪除Loading圖案之節點
    		tree.deleteItem(id+"tempNode");
      	}
  	});		
}

/*
type為dir or file, 若為dir則回傳資料夾圖片src,
若為file,則根據副檔名決定
*/
function getIMG(name,type){
	var img = "iconText.gif";
	if( type == "dir" ){
      	img = "folderClosed.gif";
    } else {
    	var extend = getExtend(name);
    	if( extend != "" && extend != null ){
			img = "../fileicons/" + extend + ".png";	
		}
    }
    
    return img;
}

//存在圖檔的副檔名, 若是知道javascipt確認檔案是否存在的方法,
//可直接取得副檔名然後接png去取得icon
//ex. name='test.java', extend='java' icon='java.png'
var extendArray = ['ai','aiff','c','chm','conf','cpp','css','csv','deb','divx','doc','docx','file',
'gif','gz','hjp','htm','html','iso','java','jpeg','jpg','js','mov','mp3','mpg','odc','odf','odg',
'odi','odp','ods','ogg','pdf','pgp','php','pl','png','ppt','pptx','ps','py','ram','rar','rb','rm',
'rpm','rtf','sql','swf','sxc','sxd','sxi','sxw','tar','tex','tgz','txt','vcf','wav','wma','wmv',
'xls','xml','xpi','xvid','zip' ];

//取得副檔名名稱
function getExtend(name){
	var lastindex = name.lastIndexOf('.');
	var extendName = name.substr( lastindex+1 ); 
	if( isExtendExist(extendName) ){
		return extendName;
	}
	return null;
}

//確認副檔名是否有存在的圖片
function isExtendExist(name){
	var index = extendArray.indexOf(name);
	if( index == -1 )
		return false;
	return true;
}


