package ntut.csie.samt.account;

import ntut.csie.samt.*;
import junit.framework.TestCase;
import java.io.File;
import java.util.List;

public class ApacheAuthenticationEntityTest extends TestCase {

	private IAuthenticationEntity apacheAuthenticationEntity;
	final private File loadFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd");
	final private File emptyFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd_empty");
	private File copyFile = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		this.apacheAuthenticationEntity = null;
		//刪除測試用的複製檔案
		if( this.copyFile != null && this.copyFile.exists() ){
			this.copyFile.delete();
		}
	}

	/**
	 * 測試從帳號檔案中取得帳號ID列表是否正確
	 * 
	 * Case1 測試從正常檔案中取得帳號ID是否正確
	 * Case2 測試從空檔案中取得帳號ID是否正確
	 */
	public void testGetAccountList(){
		//Case1 測試從正常檔案中取得帳號ID是否正確
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//讀取檔案
		this.apacheAuthenticationEntity.load();
		//取得帳號id列表
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
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
		
		//Case2 測試從空檔案中取得帳號ID是否正確
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.emptyFile);
		//讀取檔案
		this.apacheAuthenticationEntity.load();
		//取得帳號id列表
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList.size() == 0 );
	}
	
	/**
	 * 測試修改帳號ID是否成功
	 * 
	 * Case1 修改存在的帳號
	 * Case2 修改不存在之帳號
	 * Case3 修改成存在之帳號
	 */
	public void testModifyAccountId(){
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//讀取檔案
		this.apacheAuthenticationEntity.load();
		
		//Case1 修改存在的帳號
		//修改帳號名稱
		this.apacheAuthenticationEntity.modifyAccountId("franklin", "franklin2");
		
		//取得帳號id列表
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//測試修改帳號ID是否成功
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		
		//Case2 修改不存在之帳號
		//修改不存在帳號名稱
		this.apacheAuthenticationEntity.modifyAccountId("franklin", "franklin3");
		
		//取得帳號id列表
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//測試修改帳號ID是否成功
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		assertTrue( accountList.indexOf("franklin3") == -1 );
		
		//Case3 修改成存在之帳號
		this.apacheAuthenticationEntity.modifyAccountId("bluewind", "nickhades");
		
		//取得帳號id列表
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//測試是否被修改掉了
		assertTrue( accountList.indexOf("bluewind") != -1 );
	}

	/**
	 * 測試修改密碼
	 */
	public void testModifyAccountPasswd(){
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//讀取檔案
		this.apacheAuthenticationEntity.load();
		
		//進行確認是否有讀出
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		assertTrue( accountList != null );
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		
		List<String> passwdList = this.apacheAuthenticationEntity.getAccountPasswdList();
		assertTrue( passwdList != null );
		assertTrue( "" + passwdList.size(), accountList.size() == passwdList.size() );
		
		int index = accountList.indexOf("franklin");
		String oldpasswd = passwdList.get(index);
		String newpasswd = "";
		
		//修改帳號名稱
		this.apacheAuthenticationEntity.modifyAccountPasswd("franklin", "test");
		
		//取得帳號id列表
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
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
		//取得密碼列表
		passwdList = this.apacheAuthenticationEntity.getAccountPasswdList();
		assertTrue( passwdList != null );
		assertTrue( "" + passwdList.size(), accountList.size() == passwdList.size() );
		//取得修改的帳號的新密碼
		index = accountList.indexOf("franklin");
		newpasswd = passwdList.get(index);
		//新密碼與舊密碼應不同
		assertFalse( oldpasswd.equals(newpasswd) );
	}
	
	/**
	 * 測試新增帳號
	 * 
	 * Case1 加入一個新帳號
	 * Case2 加入一個新帳號,沒有密碼
	 * Case3 加入重複的帳號
	 */
	public void testCreateAccount(){
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//讀取檔案
		this.apacheAuthenticationEntity.load();
		
		//Case1 加入一個新帳號
		this.apacheAuthenticationEntity.createAccount("user1", "password");
		//取得帳號id列表
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 10 );
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
		assertTrue( accountList.indexOf("user1") != -1 );
		
		//Case2 加入一個新帳號,沒有密碼
		this.apacheAuthenticationEntity.createAccount("user2", "");
		//取得帳號id列表
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 11 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("user1") != -1 );
		assertTrue( accountList.indexOf("user2") != -1 );
		
		//Case3 加入重複的帳號
		this.apacheAuthenticationEntity.createAccount("franklin", "password");
		//取得帳號id列表
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 11 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("user1") != -1 );
		assertTrue( accountList.indexOf("user2") != -1 );
	}
	
	/**
	 * 測試刪除帳號資訊
	 * 
	 * Case1 刪除第一個帳號Id
	 * Case2 刪除中間的帳號Id
	 * Case3 刪除最後一個的帳號Id
	 * Case4 刪除不存在的帳號Id
	 */
	public void testDeleteAccount(){
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//讀取檔案
		this.apacheAuthenticationEntity.load();
		//取得帳號id列表
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		
		//進行帳號刪除
		
		//Case1 刪除第一個
		this.apacheAuthenticationEntity.deleteAccount("franklin");
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有八筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 8 );
		//要刪除之帳號應不存在
		assertTrue( accountList.indexOf("franklin") == -1 );
		
		//Case2 刪除中間的
		this.apacheAuthenticationEntity.deleteAccount("cyndi");
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有七筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 7 );
		//要刪除之帳號應不存在
		assertTrue( accountList.indexOf("cyndi") == -1 );
		
		//Case3 刪除最後一個的
		this.apacheAuthenticationEntity.deleteAccount("lenwind");
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有六筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 6 );
		//要刪除之帳號應不存在
		assertTrue( accountList.indexOf("lenwind") == -1 );
		
		//Case4 刪除不存在的Id
		this.apacheAuthenticationEntity.deleteAccount("No Exist");
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//測試取得的帳號資訊是否正確
		assertTrue( accountList != null );
		//應有六筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 6 );
		//要刪除之帳號應不存在
		assertTrue( accountList.indexOf("No Exist") == -1 );
	}
	
	/**
	 * 測試將檔案資訊寫回檔案後是否正確
	 * 
	 * Case1 測試讀取寫入有帳號內容的檔案
	 */
	public void testSave1(){
		//複製一份要測試的檔案
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);
		
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.copyFile);
		//讀取檔案
		this.apacheAuthenticationEntity.load();
		
		//經過一些操作 修改 建立 刪除
		this.apacheAuthenticationEntity.modifyAccountId("franklin", "franklin2");
		this.apacheAuthenticationEntity.createAccount("addtest", "haha");
		this.apacheAuthenticationEntity.deleteAccount("frankcheng");
		
		//儲存檔案
		this.apacheAuthenticationEntity.save();
		
		//重新讀取做驗證
		this.apacheAuthenticationEntity = null;
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.copyFile);
		this.apacheAuthenticationEntity.load();
		//取得帳號id列表
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//應有九筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") == -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		assertTrue( accountList.indexOf("addtest") != -1 );
	}
	
	
	/**
	 * 測試將檔案資訊寫回檔案後是否正確
	 * 
	 * Case2 測試讀取寫入空白的檔案
	 */
	public void testSave2(){
		//複製一份要測試的檔案
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp");
		FileCopy.copy(this.emptyFile, this.copyFile);
		
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.copyFile);
		//讀取檔案
		this.apacheAuthenticationEntity.load();
		
		//建立帳號
		this.apacheAuthenticationEntity.createAccount("franklin1", "haha");
		this.apacheAuthenticationEntity.createAccount("franklin2", "haha");
		this.apacheAuthenticationEntity.createAccount("franklin3", "haha");
		this.apacheAuthenticationEntity.createAccount("franklin4", "haha");
		this.apacheAuthenticationEntity.createAccount("franklin5", "haha");
		
		//儲存檔案
		this.apacheAuthenticationEntity.save();
		
		//重新讀取做驗證
		this.apacheAuthenticationEntity = null;
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.copyFile);
		this.apacheAuthenticationEntity.load();
		//取得帳號id列表
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//應有五筆帳號資訊
		assertTrue( "" + accountList.size(), accountList.size() == 5 );
		//測試帳號ID是否正確
		assertTrue( accountList.indexOf("franklin1") != -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		assertTrue( accountList.indexOf("franklin3") != -1 );
		assertTrue( accountList.indexOf("franklin4") != -1 );
		assertTrue( accountList.indexOf("franklin5") != -1 );
	}
	

	
}
