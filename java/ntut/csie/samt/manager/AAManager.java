package ntut.csie.samt.manager;

import java.util.List;
import java.io.File;

import org.tmatesoft.svn.core.SVNException;

import ntut.csie.samt.aaprofile.ImportHandler;
import ntut.csie.samt.svncontroll.SVNRepositoryController;

public class AAManager {

	private AuthorizationCenter authorizationCenter = null;
	private AuthenticationCenter authenticationCenter = null;
	private static AAManager instance = null;
	private SAMTProperties properties;
	private ImportHandler importHandler;
	private SVNRepositoryController svnController;
	private boolean writable = true;
	
	/**
	 * 建構元
	 */
	protected AAManager(){
		//讀取權限相關資訊
		this.authorizationCenter  = new AuthorizationCenter();
		//讀取組態設定檔
		this.properties = new SAMTProperties();
		//讀取認證相關資訊
		this.authenticationCenter = new AuthenticationCenter();
		this.authenticationCenter.setProtocol(this.properties.getServerProtocol());
		
		//讀取資訊描述檔
		this.authorizationCenter.load(this.properties.getAuthorizationPath());
		this.authenticationCenter.load(this.properties.getAuthenticationPath());
		
		//設定SVN連線資料
		this.svnController = new SVNRepositoryController();
		this.svnController.setAccount(this.properties.getManagerAccount());
		this.svnController.setPasswd(this.properties.getManagerPasswd());
		this.svnController.setSVNURL(this.properties.getURL());
		this.svnController.connection();
	}
	
    public static AAManager getInstance() {
        if (instance == null) {
            instance = new AAManager();
        }
        return instance;
    } 
    
    public static void Release() {
        instance = null;
    } 
	
    //權限檔案與認證檔案儲存相關
    
    /**
     * 將資訊儲存到AuthorizationFile
     */
    public void saveAuthorizationFile(){
    	if( writable ){
    		this.authorizationCenter.save();
    	}
    }
    
    /**
     * 將資訊儲存到AuthenticationFile
     */
    public void saveAuthenticationFile(){
    	if( writable ){
    		this.authenticationCenter.save();
    	}
    }
    
    /**
     * 儲存檔案會根據這個Flag決定是否要儲存,
     * 主要用於避免import直接匯入,
     * 必須等到所有步驟都完成才進行匯入
     * @param writable
     */
    public void setWritable(boolean writable){
    	this.writable = writable;
    }
    
    /**
     * 取得是否現在可以進行寫入檔案訊息
     * @return
     */
    public boolean isWritable(){
    	return this.writable;
    }
    
    //Normal Operation
    
	/**
	 * 新增一個名稱為name的群組
	 * 
	 * @param name 新群組名稱
	 * @return 成功則回傳true, 反之false
	 */
	public boolean addGroup(String name){
		boolean result = this.authorizationCenter.addGroup(name);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * 刪除一個名稱為name的群組
	 * 
	 * @param name 刪除群組名稱
	 * @return 成功則回傳true, 反之false
	 */
	public boolean deleteGroup(String name){
		//刪除最後一個群組會死掉
		boolean result = this.authorizationCenter.deleteGroup(name);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * 修改群組名稱
	 * 
	 * @param oldname 原本的名稱
	 * @param newName 新的名稱
	 * @return 成功則回傳true, 反之false
	 */
	public boolean modifyGroupName(String oldname,String newname){
		boolean result = authorizationCenter.modifyGroupName(oldname, newname);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * 取得群組列表
	 * 
	 * @return 群組列表
	 */
	public List<String> listAllGroup(){
		return this.authorizationCenter.getStringGroupList();
	}
	
	/**
	 * 新增一個群組成員
	 * 
	 * @param groupName 要加入的群組
	 * @param mebmerName 群組成員名稱
	 * @param type 群組成員型態, "group" or "user"
	 * @return 加入成功則回傳true, 否則是false
	 */
	public boolean addGroupMember(String groupName, String memberName, String type){
		boolean result = false;
		//確認使用者是否存在, 不存在則不加入
		if( type.equals("account") ){
			//account不存在則不加入
			if( this.authenticationCenter.findAccount(memberName) == null){
				return result;
			}
			result =  this.authorizationCenter.addGroupMember(groupName, memberName, type);
			this.saveAuthorizationFile();
			return result;
		}
		
		//新增群組成員, type為一個群組
		result =  this.authorizationCenter.addGroupMember(groupName, memberName, type);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * 刪除群組成員
	 * 
	 * @param groupName 群組名稱
	 * @param memberName 成員名稱
	 * @param type 群組成員型態, "group" or "user"
	 * @return 加入成功則回傳true, 否則是false
	 */
	public boolean deleteGroupMember(String groupName, String memberName, String type){		
		boolean result = this.authorizationCenter.deleteGroupMember(groupName, memberName, type);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * 列出某群組的成員
	 * 
	 * @param groupName 要找尋的群組名稱
	 * @return 此群組之群組成員
	 */
	public List<String> listGroupMember(String groupName){
		return this.authorizationCenter.getStringMemberList(groupName);
	}
	
	/**
	 * 找尋群組
	 * 
	 * @param name 要找尋的群組名稱
	 * @return 有找到則回傳群組, 否則就null
	 */
	public boolean findGroup(String name){
		return this.authorizationCenter.findGroup(name) != null;
	}
	
	/**
	 * 新增群權限,應判斷使用者是否存在
	 * 
	 * @param path 要新增全線的路徑
	 * @param name 新增成員名稱
	 * @param type 成員型態
	 * @param permission 權限
	 * @return 成功則true,失敗則false
	 */
	public boolean addPermission(String path, String membername,String membertype, String permission){
		boolean result = false;
		//若type為account,則交給AuthenticationCenter判斷使用者是否存在,
		//若type為group,則丟下去給AuthorizationCenter決定是否要加入
		if( membertype.equals("account") ){
			//account不存在則不加入
			if( this.authenticationCenter.findAccount(membername) == null){
				return result;
			}
			result =  this.authorizationCenter.addPermission(path, membername, membertype, permission);
		} else if( membertype.equals("group") ){
			result = this.authorizationCenter.addPermission(path, membername, membertype, permission);
		}
		
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * 修改權限
	 * 
	 * @param path 要修改權限的路徑
	 * @param membername 要修改的成員
	 * @param membertype 成員型態
	 * @param permission 修改的權限
	 * @return 成功則true,失敗則false
	 */
	public boolean modifyPermission(String path, String membername, String membertype, String permission){
		boolean result = this.authorizationCenter.modifyPermission(path, membername, membertype, permission);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * 刪除權限資料
	 * 
	 * @param path 要刪除權限的路徑
	 * @param membername 要刪除的成員
	 * @param membertype 成員型態
	 * @return 成功則true,失敗則false
	 */
	public boolean deletePermission(String path, String membername, String membertype){
		boolean result = this.authorizationCenter.deletePermission(path, membername, membertype);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * 根據路徑取得其下的成員列表
	 * 
	 * @param path 要取得列表的路徑
	 * @return 成員列表
	 */
	public List<String> getPathMemberList(String path){
		return this.authorizationCenter.getPathMemberList(path);
	}
	
	/**
	 * 根據路徑取得其下的成員權限
	 * 
	 * @param path 路徑名稱
	 * @param name 成員名稱
	 * @param type 成員型態
	 * @return 成員權限
	 */
	public String getPermission(String path,String name,String type){
		return this.authorizationCenter.getPermission(path, name, type);
	}
	
	//帳號認證相關函式
	
	/**
	 * 列出所有帳號的ID
	 */
	public List<String> listAllAccount(){
		return this.authenticationCenter.getAccountIdList();
	}
	
	/**
	 * 判斷帳號是否已被建立
	 * @param id 要判斷的帳號id
	 * @return
	 */
	public boolean isAccountExist(String id){
		//要找尋的帳號id不存在
		if( this.authenticationCenter.findAccount(id) == null )
			return false;
		//要找尋的帳號id存在
		return true;
	}
	
	/**
	 * 建立新的帳號
	 * @param id
	 * @param passwd
	 * @return
	 */
	public boolean createAccount(String id,String passwd){
		boolean result = this.authenticationCenter.createAccount(id, passwd);
		this.saveAuthenticationFile();
		return result;
	}
	
	/**
	 * 刪除帳號
	 * @param id
	 * @return
	 */
	public boolean deleteAccount(String id){
		boolean result = this.authenticationCenter.deleteAccount(id);
		this.saveAuthenticationFile();
		return result;
	}
	
	/**
	 * 修改帳號的ID與密碼
	 * @param oldId 舊帳號ID
	 * @param newId 新帳號ID
	 * @param newPasswd 新密碼
	 * @return
	 */
	public boolean modifyAccount(String oldId,String newId,String newPasswd){
		boolean result = this.authenticationCenter.modifyAccount(oldId, newId, newPasswd);
		//去修改權限部分的帳號名稱將舊的改成新的名稱
		this.updateAuthorizationAccountName(oldId, newId);
		//寫入檔案
		this.saveAuthorizationFile();
		this.saveAuthenticationFile();
		return result;
	}
	
	/**
	 * 去更新群組成員內的Account名稱,
	 * 將oldId改成newId
	 * @param oldId 原本名稱
	 * @param newId 新名稱
	 */
	private void updateAuthorizationAccountName(String oldId,String newId){
		List<String> groupList = this.authorizationCenter.getStringGroupList();
		//進行修改動作
		for( int i = 0 ; groupList!= null && i < groupList.size() ; i++ ){
			String groupName = groupList.get(i);
			String type = "account";
			//先刪除原本的再新增新的即完成修改動作
			if( this.authorizationCenter.deleteGroupMember(groupName, oldId, type))
				this.authorizationCenter.addGroupMember(groupName, newId, type);
		}
	}
	
	//取得組態相關設定相關函式
	
	public String getAuthenticationPath(){
		return this.properties.getAuthenticationPath();
	}
	
	public String getAuthorizationPath(){
		return this.properties.getAuthorizationPath();
	}
	
	public String getURL(){
		return this.properties.getURL();
	}	
	
	//設定組態相關設定相關函式
	
	public void setAuthenticationPath(String path){
		this.properties.setAuthenticationPath(path);
	}
	
	public void setAuthorizationPath(String path){
		this.properties.setauthorizationPath(path);
	}
	
	public void setURL(String url){
		this.properties.setSVNUrl(url);
	}
	
	/**
	 * 儲存設定的Properties內容
	 */
	public void saveProperties(){
		this.properties.saveProperties();
	}
	
	//SVN相關函式
	
	/**
	 * 取得某路徑下所有的檔案或資料夾
	 * 
	 * @param parentPath 要取得的路徑
	 * @param action "DIR" 取得資料夾 , "FILE" 取得檔案
	 */
	public List<String> getRepositorySubFile(String parentPath,String action)  throws SVNException{
		return this.svnController.getRepositorySubFile(parentPath, action);
	}
	
	/**
	 * 根據路徑, 建立SVN相對應的資料夾
	 * @param pathlist 要建立的資料夾列表
	 */
	public void createDir(List<String> pathlist) throws SVNException{
		this.svnController.createDir(pathlist);
	}
	
	/**
	 * 判斷path是否存在SVN內
	 * @param path
	 * @return
	 */
	public boolean isPathExist(String path){
		return this.svnController.isPathExist(path);
	}
	
	//匯入檔案 , 匯出檔案
	
	/**
	 * 匯入權限設定檔, 將相關權限、帳號與要建立的資料夾
	 * 設定完成
	 * 分開import動作是為了做一個讓使用者知道現在正在
	 * 做import的什麼動作
	 * @param file 要匯入的檔案
	 */
	public void importProfile(File file){
		if( this.importHandler == null ){
			this.importHandler = new ImportHandler(this);
			this.importHandler.importProfile(file);
		}
	}
	
	/**
	 * 將從AAProfile取得的建立SVN資料列表建立到SVN上
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importSVNFolder(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importSVNFolder();
	}
	
	/**
	 * 將從AAProfile取得的帳號記錄到帳號設定檔
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importAccounts(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importAccounts();
	}
	
	/**
	 * 將從AAProfile取得的群組列表資訊記錄到權限設定
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importGroups(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importGroups();
	}
	
	/**
	 * 將從AAProfile取得的群組成員列表資訊記錄到權限設定
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importGroupMember(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importGroupMember();
	}
	
	/**
	 * 將從AAProfile取得的權限資訊記錄到權限設定
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importPermissions(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importPermissions();
	}
	
}
