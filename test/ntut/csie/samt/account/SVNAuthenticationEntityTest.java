package ntut.csie.samt.account;

import java.io.File;

import junit.framework.TestCase;
import java.util.*;

public class SVNAuthenticationEntityTest extends TestCase {

	private IAuthenticationEntity svnAuthenticationEntity;
	final private File loadFile = new File("WebContent/TestFile/AuthFileTest/svn.passwd");
	final private File emptyFile = new File("WebContent/TestFile/AuthFileTest/svn.passwd_empty");
	final private File errorFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd");
	private File copyFile = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 測試從帳號檔案中取得帳號ID列表是否正確
	 * 
	 * Case1 測試從正常檔案中取得帳號ID是否正確
	 * Case2 測試從空檔案中取得帳號ID是否正確
	 * Case3 測試從不同檔案格式取得帳號ID是否正確
	 */
	public void testGetAccountList(){
		//Case1 測試從正常檔案中取得帳號ID是否正確
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList.size() == 9 );
		//確認帳號是否正確
		assert( accountList.contains("franklin") );
		assert( accountList.contains("bluewind") );
		assert( accountList.contains("ccchange") );
		assert( accountList.contains("frankcheng") );
		assert( accountList.contains("cyndi") );
		assert( accountList.contains("thenewid") );
		assert( accountList.contains("chihcheng") );
		assert( accountList.contains("nickhades") );
		assert( accountList.contains("lenwind") );
		
		//Case2 測試從空檔案中取得帳號ID是否正確
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.emptyFile);
		this.svnAuthenticationEntity.load();
		accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList == null || accountList.size() == 0 );
		
		//Case3 測試從不同檔案格式取得帳號ID是否正確
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.errorFile);
		this.svnAuthenticationEntity.load();
		accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList == null || accountList.size() == 0 );
	}
	
	/**
	 * 測試從帳號檔案中取得帳號密碼列表是否正確
	 * 
	 * Case1 測試從正常檔案中取得帳號密碼是否正確
	 * Case2 測試從空檔案中取得帳號密碼是否正確
	 * Case3 測試從不同檔案格式取得帳號密碼是否正確
	 */
	public void testGetAccountPasswdList(){
		//Case1 測試從正常檔案中取得帳號ID是否正確
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> passwdList = this.svnAuthenticationEntity.getAccountPasswdList();
		assert( passwdList.size() == 9 );
		//確認密碼是否正確
		for( int i = 0 ; i < passwdList.size() ; i++ ){
			assert( passwdList.get(i).equals("1234") );
		}
		
		//Case2 測試從空檔案中取得帳號ID是否正確
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.emptyFile);
		this.svnAuthenticationEntity.load();
		passwdList = this.svnAuthenticationEntity.getAccountList();
		assert( passwdList == null || passwdList.size() == 0 );
		
		//Case3 測試從不同檔案格式取得帳號ID是否正確
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.errorFile);
		this.svnAuthenticationEntity.load();
		passwdList = this.svnAuthenticationEntity.getAccountList();
		assert( passwdList == null || passwdList.size() == 0 );
	}	
	
	/**
	 * 測試修改帳號ID是否成功
	 * 
	 * Case1 修改存在的帳號
	 * Case2 修改不存在之帳號
	 * Case3 修改成存在之帳號
	 */
	public void testModifyAccountId(){
		//Case1 修改存在的帳號
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList.size() == 9 );		
		this.svnAuthenticationEntity.modifyAccountId("franklin", "frank007love");
		assert( accountList.size() == 9 );
		//測試修改是否成功
		assert( accountList.contains("frank007love") );
		assert( !accountList.contains("franklin") );
		
		//Case2 修改不存在之帳號
		this.svnAuthenticationEntity.modifyAccountId("franklin", "frank008love");
		assert( accountList.contains("frank007love") );
		assert( !accountList.contains("franklin") );
		assert( !accountList.contains("frank008love") );
		
		//Case3 修改成存在之帳號
		this.svnAuthenticationEntity.modifyAccountId("frank007love", "bluewind");
		assert( accountList.contains("frank007love") );
		assert( accountList.contains("bluewind") );
	}
	
	/**
	 * 測試新增帳號
	 * 
	 * Case1 加入一個新帳號
	 * Case2 加入一個新帳號,沒有密碼
	 * Case3 加入重複的帳號
	 */
	public void testCreateAccount(){
		//Case1 加入一個新帳號
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList.size() == 9 );
		this.svnAuthenticationEntity.createAccount("testAdd", "4567");
		assert( accountList.size() == 10 );
		assert( accountList.contains("testAdd") );
		
		//Case2 加入一個新帳號,沒有密碼
		this.svnAuthenticationEntity.createAccount("testAdd2", "");
		assert( accountList.size() == 10 );
		assert( !accountList.contains("testAdd2") );
		
		//Case3 加入重複的帳號
		this.svnAuthenticationEntity.createAccount("testAdd", "");
		assert( accountList.size() == 10 );
		assert( accountList.contains("testAdd") );
	}
	
	/**
	 * 測試刪除帳號資訊
	 * 
	 * Case1 刪除第一個帳號Id
	 * Case2 刪除中間的帳號Id
	 * Case3 刪除最後一個的帳號Id
	 * Case4 刪除不存在的帳號Id
	 * Case5 刪除全部Id
	 */
	public void testDeleteAccount(){
		//Case1 刪除第一個帳號Id
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList.size() == 9 );
		this.svnAuthenticationEntity.deleteAccount("franklin");
		assert( accountList.size() == 8 );
		assert( !accountList.contains("franklin") );
		
		//Case2 刪除中間的帳號Id
		this.svnAuthenticationEntity.deleteAccount("frankcheng");
		assert( accountList.size() == 7 );
		assert( !accountList.contains("frankcheng") );
		
		//Case3 刪除最後一個的帳號Id
		this.svnAuthenticationEntity.deleteAccount("lenwind");
		assert( accountList.size() == 6 );
		assert( !accountList.contains("lenwind") );
		
		//Case4 刪除不存在的帳號Id
		this.svnAuthenticationEntity.deleteAccount("lenwind");
		assert( accountList.size() == 6 );
		assert( !accountList.contains("lenwind") );
		
		//Case5 刪除全部Id
		this.svnAuthenticationEntity.deleteAccount("bluewind");
		this.svnAuthenticationEntity.deleteAccount("ccchange");
		this.svnAuthenticationEntity.deleteAccount("cyndi");
		this.svnAuthenticationEntity.deleteAccount("thenewid");
		this.svnAuthenticationEntity.deleteAccount("chihcheng");
		this.svnAuthenticationEntity.deleteAccount("nickhades");
		assert( accountList.size() == 0 );
	}
	
	/**
	 * 測試修改密碼
	 * 
	 * Case1 修改存在之帳號的密碼
	 * Case2 修改密碼但是密碼為空
	 * 
	 */
	public void testModifyAccountPasswd(){
		//Case1 修改存在之帳號的密碼
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		List<String> passwdList = this.svnAuthenticationEntity.getAccountPasswdList();
		
		this.svnAuthenticationEntity.modifyAccountPasswd("franklin", "5678");
		int index = accountList.indexOf("franklin");
		assert( !passwdList.get(index).equals("5678") );
		assert( passwdList.get(index).equals("5678") );
		
		//Case2 修改密碼但是密碼為空
		this.svnAuthenticationEntity.modifyAccountPasswd("franklin", "");
		assert( passwdList.get(index).equals("5678") );
	}
}
