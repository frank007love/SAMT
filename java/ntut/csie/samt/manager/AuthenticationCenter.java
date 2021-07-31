package ntut.csie.samt.manager;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import ntut.csie.samt.account.*;

public class AuthenticationCenter {

	private List<Account> accounts;
	private IAuthenticationEntity authenticationEntity;
	private File authenticationFile;
	private String protocol;
	
	/**
	 * 建構元
	 * 
	 */
	public AuthenticationCenter(){
		this.accounts = new ArrayList<Account>();
		this.authenticationFile = null;
	}
	
	public void setProtocol(String protocol){
		this.protocol = protocol;
	}
	
	/**
	 * 讀取帳號認證描述檔案
	 * @param name 檔案的位置
	 * @return
	 */
	public boolean load(String name){
		this.authenticationFile = new File(name);
		//檔案存在
		if( this.authenticationFile.exists() ){
			//svn使用http protocol則使用apache認證檔案格式 
			if( this.protocol.equals("http") ){
				this.authenticationEntity = new ApacheAuthenticationEntity(this.authenticationFile);
				
			//svn使用svn server protocol則使用svn認證檔案格式
			} else if( this.protocol.equals("svn") ){
				this.authenticationEntity = new SVNAuthenticationEntity(this.authenticationFile);
			}
			
			//從檔案中讀取所有的帳號資訊
			this.loadAccount();
			return true;
		}
		return false;
	}
	
	/**
	 * 將權限資訊存入檔案中
	 */
	public void save(){
		this.authenticationEntity.save();
	}
	
	/**
	 * 從檔案中讀取所有的帳號資訊
	 */
	private void loadAccount(){
		this.accounts.clear();
		//讀取檔案內容
		this.authenticationEntity.load();
		//取得檔案描述的帳號資訊
		List<String> accountList = this.authenticationEntity.getAccountList();

		//建立所有檔案中描述的帳號於ccounts
		for( int i = 0 ; accountList != null && i < accountList.size() ; i++ ){
			/*
			 * 將帳號資訊放到accounts
			 * 不使用createAccount是由於createAccount會增加Entity內容
			 * 在這裡目前不存放password資料,因為apache server使用md5加密
			 * 存放並沒有實際意義, 等到svn server有需要處理密碼再寫
			 */
			Account account = new Account();
			account.setName(accountList.get(i));
			account.setPassword("");
			this.accounts.add(account);
		}
	}
	
	/**
	 * 刪除帳號
	 * @param id 要刪除的帳號ID
	 * @return
	 */
	public boolean deleteAccount(String id){
		//找尋帳號是否存在, 存在則進行刪除
		Account account = this.findAccount(id);
		if( account != null ){
			//進行刪除
			this.accounts.remove(account);
			//修改Entity Data部分
			this.authenticationEntity.deleteAccount(id);
			return true;
		}
		return false;
	}
	
	/**
	 * 建立新的帳號
	 * @param id 帳號名稱
	 * @param passwd 密碼
	 * @return 成功則回傳true, 反之則false
	 */
	public boolean createAccount(String id, String passwd){
		passwd = passwd.trim();
		//找尋帳號是否存在, 存在則不加入, 若沒輸入密碼也是
		if( this.findAccount(id) != null || passwd == null || passwd.isEmpty() ){
			return false;
		}
		//建立帳號
		Account account = new Account();
		account.setName(id);
		account.setPassword(passwd);
		
		this.accounts.add(account);
		//修改Entity Data部分
		this.authenticationEntity.createAccount(id, passwd);
		return true;
	}
	
	/**
	 * Apache Server取出來的密碼會經過加密
	 */
	
	/**
	 * 修改帳號的名稱與密碼
	 * @param id
	 * @param newid
	 * @param newpasswd
	 * @return
	 */
	public boolean modifyAccount(String id,String newid, String newpasswd){
		//找尋要修改的帳號
		Account account =  this.findAccount(id);

		//若要修改之帳號不存在或是修改之新帳號名稱已存在(除了相同),則不進行修改
		if( account == null || ( this.findAccount(newid) != null && !id.equals(newid) ) ){
			return false;
		}
		//進行修改動作
		
		//有要修改帳號則id和newid會不同就進行修改
		if( !id.equals(newid) ){
			account.setName(newid);
			//修改檔案部份資料
			this.authenticationEntity.modifyAccountId(id, newid);
		}
		//非空則代表要修改密碼
		if( newpasswd != null && !newpasswd.isEmpty() ){
			account.setPassword(newpasswd);
			this.authenticationEntity.modifyAccountPasswd(id, newpasswd);
		}

		//回傳修改成功之訊息
		return true;
	}
	
	/**
	 * 依照id找尋帳號
	 * @param id 要找尋帳號的id
	 * @return 找尋到的帳號
	 */
	public Account findAccount(String id){
		Account account  = null;
		//找尋相對應的帳號
		for( int i = 0 ; i < this.accounts.size() ;i ++ ){
			//取得帳號
			account = this.accounts.get(i);
			//找到則break然後回傳
			if( account.getId().equals(id) ){
				break;
			}
			//沒找到account設為null
			account = null;
		}
		
		//回傳找到的帳號
		return account;
	}
	
	/**
	 * 取得帳號ID列表
	 * @return
	 */
	public List<String> getAccountIdList(){
		List<String> accountList = new ArrayList<String>();
		//從Accounts中取得帳號ID放到accountList
		for( int i = 0 ; i < this.accounts.size() ; i++ ){
			Account account = this.accounts.get(i);
			accountList.add(account.getId());
		}
		//若沒有帳號存在則設為null
		if( accountList.size() == 0 ){
			accountList = null;
		}
		//回傳帳號ID列表
		return accountList;
	}
	
}
