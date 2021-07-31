package ntut.csie.samt.manager;

import java.io.File;
import java.util.List;

import ntut.csie.samt.FileCopy;
import junit.framework.TestCase;

public class AuthenticationCenterTest extends TestCase {

	private AuthenticationCenter authenticationCenter;
	final private File loadFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd");
	final private File emptyFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd_empty");
	private File copyFile = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		this.authenticationCenter = new AuthenticationCenter();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		//刪除測試用的複製檔案
		if( this.copyFile != null && this.copyFile.exists() ){
			this.copyFile.delete();
		}
	}

	/**
	 * 讀取http protocol的認證方式檔案,
	 * 測試讀取檔案後, 取得的帳號ID列表是否正確
	 * 
	 * Case1 讀取正常的檔案
	 * Case2 讀取空的檔案
	 */
	public void testGetAccountIdList_http(){
		List<String> accountList = null;
		
		//Case1 讀取正常的檔案
		//複製一份要測試的檔案
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);

		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.copyFile.getPath()));
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 9 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		
		//Case2 讀取空的檔案
		this.authenticationCenter.setProtocol("http");
		this.authenticationCenter.load(this.emptyFile.getPath());
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList == null );
	}
	
	/**
	 * 測試新增帳號後列表是否正確,新增於已存在內容的檔案
	 * 
	 * Case1 新增不存在之帳號
	 * Case2 新增已存在之帳號
	 * Case3 新增無密碼之帳號
	 */
	public void testCreateAccount_http_existfile(){
		List<String> accountList = null;
		//複製一份要測試的檔案
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);
		//設置要使用的protocol與讀取檔案
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.copyFile.getPath()));
		
		//Case1 新增不存在之帳號
		assertTrue( this.authenticationCenter.createAccount("newAccount", "lisi87"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有十筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 10 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		assertTrue( accountList.indexOf("newAccount") != -1 );
		
		//Case2 新增已存在之帳號
		assertFalse( this.authenticationCenter.createAccount("newAccount", "lisi87"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有十筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 10 );
		
		//Case3 新增無密碼之帳號
		assertFalse( this.authenticationCenter.createAccount("newAccount2", ""));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有十筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 10 );
	}
	
	/**
	 * 測試新增帳號後列表是否正確,新增於空的檔案
	 * 
	 * Case1 新增不存在帳號
	 * Case2 新增已存在之帳號
	 * 新增無密碼之帳號
	 */
	public void testCreateAccount_http_emptyfile(){
		List<String> accountList = null;
		//設置要使用的protocol與讀取檔案
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.emptyFile.getPath()));
		
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList == null );
		
		//Case1 新增不存在之帳號
		assertTrue( this.authenticationCenter.createAccount("newAccount", "lisi87"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有一筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 1 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("newAccount") != -1 );
		
		//Case2 新增已存在之帳號
		assertFalse( this.authenticationCenter.createAccount("newAccount", "lisi87"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有一筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 1 );
		
		//Case3 新增無密碼之帳號
		assertFalse( this.authenticationCenter.createAccount("newAccount2", ""));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有一筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 1 );
	}
	
	/**
	 * 測試刪除帳號後列表是否正確,刪除於已存在內容的檔案
	 * 
	 * Case1 刪除存在之帳號-第一個
	 * Case2 刪除存在之帳號-中間的
	 * Case3 刪除存在之帳號-最後一個
	 * Case4 刪除不存在之帳號
	 */
	public void testDeleteAccount_http_existfile(){
		List<String> accountList = null;
		//複製一份要測試的檔案
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);
		//設置要使用的protocol與讀取檔案
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.copyFile.getPath()));
		
		//Case1 刪除存在之帳號-第一個
		assertTrue( this.authenticationCenter.deleteAccount("franklin"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有八筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 8 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		
		//Case2 刪除存在之帳號-中間的
		assertTrue( this.authenticationCenter.deleteAccount("cyndi"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有七筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 7 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") == -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		
		//Case3 刪除存在之帳號-最後一個
		assertTrue( this.authenticationCenter.deleteAccount("lenwind"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有六筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 6 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") == -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") == -1 );
		
		//Case4 刪除不存在之帳號
		assertFalse( this.authenticationCenter.deleteAccount("lenwind"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有六筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 6 );
	}
	
	/**
	 * 測試刪除帳號後列表是否正確,刪除於空的檔案
	 * 
	 * Case1 刪除帳號
	 */
	public void testDeleteAccount_http_emptyfile(){
		List<String> accountList = null;
		//設置要使用的protocol與讀取檔案
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.emptyFile.getPath()));
		
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList == null );
		
		//Case1 刪除帳號
		//帳號不存在
		assertFalse( this.authenticationCenter.deleteAccount("franklin"));
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList == null );
	}
	
	/**
	 * 測試修改帳號資訊
	 * 
	 * Case1 修改存在之帳號成不存在之帳號名稱,不修改密碼
	 * Case2 修改帳號與密碼, 帳號為不存在之名稱
	 * Case3 修改不存在之帳號
	 */
	public void testModifyAccount_http(){
		List<String> accountList = null;
		//複製一份要測試的檔案
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);
		//設置要使用的protocol與讀取檔案
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.copyFile.getPath()));
		
		//Case1 修改存在之帳號成不存在之帳號名稱,不修改密碼
		assertTrue ( this.authenticationCenter.modifyAccount("franklin", "franklin2", ""));
		
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有六筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 9 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		
		//Case2 修改帳號與密碼, 要修改的帳號為不存在之名稱
		assertTrue( this.authenticationCenter.modifyAccount("franklin2", "franklin", "hehey"));
		
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有六筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 9 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("franklin2") == -1 );
		
		//Case3 修改不存在之帳號
		assertFalse( this.authenticationCenter.modifyAccount("franklin2", "franklin", "hehey"));
		
		//取得帳號列表
		accountList = this.authenticationCenter.getAccountIdList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有六筆帳號資訊
		assertTrue( "" + accountList.size() ,  accountList.size() == 9 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("franklin2") == -1 );
	}
	
}
