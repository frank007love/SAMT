package ntut.csie.samt.manager;

import junit.framework.TestCase;
import java.io.File;
import java.util.List;

import ntut.csie.samt.manager.AAManager;

public class AAManagerTest extends TestCase {

	private AAManager aaManager;
	
	protected void setUp() throws Exception {
		super.setUp();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		File file = new File("WebContent/TestFile/AuthFileTest/authz_empty");
		file.delete();
		file.createNewFile();
		
		file = new File("WebContent/TestFile/AAProfileTest/dav_svn.passwd_empty");
		file.delete();
		file.createNewFile();
	}

	/**
	 * Case1 測試匯入AAProfile檔案到空的群限設定與帳號設定檔, 資料是否正確建立
	 * Case2 重複匯入, 應取得回傳為1
	 */
	public void testImportProfile1(){
		//Case1
		
		//設定要測試的檔案路徑
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
		System.setProperty("ntut.csie.samt.PropertiesPath", "WebContent/TestFile/samt.properties_import");

		this.aaManager = AAManager.getInstance();
		
		//匯入AAProfie檔案
		this.aaManager.importProfile(new File("WebContent/TestFile/AAProfileTest/AAProfileTest.xml"));
		
		assertTrue( this.aaManager.importAccounts() == 2);
		
		List<String> accountList = this.aaManager.listAllAccount();
		assertTrue( ""+accountList.size(), accountList.size() == 8 );
		assertTrue( accountList.indexOf("oph") != -1 );
		assertTrue( accountList.indexOf("ztgao") != -1 );
		assertTrue( accountList.indexOf("tshsu") != -1 );
		assertTrue( accountList.indexOf("yc") != -1 );
		assertTrue( accountList.indexOf("ctchen") != -1 );
		assertTrue( accountList.indexOf("franklin") != -1 );	
		assertTrue( accountList.indexOf("lenwind") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );

		assertTrue( this.aaManager.importGroups() == 2);
		assertTrue( this.aaManager.importGroupMember() == 2); //帳號會先存在才加成員
		assertTrue( this.aaManager.importPermissions() == 2);

		
		//測試群組列表是否正確
		List<String> groupList =  this.aaManager.listAllGroup();
		assertTrue( groupList != null );
		assertTrue( groupList.size() == 4 );
		assertTrue( groupList.indexOf("admin") != -1 );
		assertTrue( groupList.indexOf("96") != -1 );
		assertTrue( groupList.indexOf("95") != -1 );
		assertTrue( groupList.indexOf("old") != -1 );
		
		//測試群組成員列表是否正確
		List<String> memberList = null;
		//測試admin的成員是否正確
		memberList = this.aaManager.listGroupMember("admin");
		assertTrue( memberList != null );
		assertTrue(""+memberList.size() ,memberList.size() == 3 );
		assertTrue( memberList.indexOf("@95") != -1 );
		assertTrue( memberList.indexOf("tshsu") != -1 );
		assertTrue( memberList.indexOf("ctchen") != -1 );
		//測試96的成員是否正確
		memberList = this.aaManager.listGroupMember("96");
		assertTrue( memberList != null );
		assertTrue( memberList.size() == 3 );
		assertTrue( memberList.indexOf("franklin") != -1 );
		assertTrue( memberList.indexOf("lenwind") != -1 );
		assertTrue( memberList.indexOf("frankcheng") != -1 );
		//測試95的成員是否正確
		memberList = this.aaManager.listGroupMember("95");
		assertTrue( memberList != null );
		assertTrue( memberList.size() == 2 );
		assertTrue( memberList.indexOf("oph") != -1 );
		assertTrue( memberList.indexOf("ztgao") != -1 );
		//測試old的成員是否正確
		memberList = this.aaManager.listGroupMember("old");
		assertTrue( memberList != null );
		assertTrue( memberList.size() == 3 );
		assertTrue( memberList.indexOf("yc") != -1 );
		assertTrue( memberList.indexOf("tshsu") != -1 );
		assertTrue( memberList.indexOf("ctchen") != -1 );
		
		//測試路徑成員是否正確, 包含權限
		List<String> pathMemberList = null;
		//測試路徑"/"的成員
		pathMemberList = this.aaManager.getPathMemberList("/");
		assertTrue( pathMemberList != null );
		assertTrue( pathMemberList.size() == 2 );
		assertTrue( pathMemberList.indexOf("@*") != -1 ); //* 加入非群組的錯誤
		assertTrue( pathMemberList.indexOf("@admin") != -1 );
		//測試路徑"/"成員權限
		assertTrue( this.aaManager.getPermission("/", "*", "group").equals("r") );
		assertTrue( this.aaManager.getPermission("/", "admin", "group").equals("rw") );
		
		//測試路徑"/JCIS/Java"的成員
		pathMemberList = this.aaManager.getPathMemberList("/JCIS/Java");
		assertTrue( pathMemberList != null );
		assertTrue( pathMemberList.size() == 3 );
		assertTrue( pathMemberList.indexOf("@95") != -1 );
		assertTrue( pathMemberList.indexOf("@96") != -1 );
		assertTrue( pathMemberList.indexOf("yc") != -1 );
		//測試路徑"/JCIS/Java"成員權限
		assertTrue( this.aaManager.getPermission("/JCIS/Java", "95", "group").equals("rw") );
		assertTrue( this.aaManager.getPermission("/JCIS/Java", "96", "group").equals("rw") );
		assertTrue( this.aaManager.getPermission("/JCIS/Java", "yc", "account").equals("r") );
		
		//測試路徑"/JCIS/Test"的成員
		pathMemberList = this.aaManager.getPathMemberList("/JCIS/Test");
		assertTrue( pathMemberList != null );
		assertTrue( pathMemberList.size() == 2 );
		assertTrue( pathMemberList.indexOf("@95") != -1 );
		assertTrue( pathMemberList.indexOf("@96") != -1 );
		//測試路徑"/JCIS/Test"成員權限
		assertTrue( this.aaManager.getPermission("/JCIS/Test", "95", "group").equals("rw") );
		assertTrue( this.aaManager.getPermission("/JCIS/Test", "96", "group").equals("rw") );
		
		//測試路徑"/TestingPath"的成員
		pathMemberList = this.aaManager.getPathMemberList("/TestingPath");
		assertTrue( pathMemberList != null );
		assertTrue( pathMemberList.size() == 2 );
		assertTrue( pathMemberList.indexOf("@95") != -1 );
		assertTrue( pathMemberList.indexOf("@96") != -1 );
		//測試路徑"/TestingPath"成員權限
		assertTrue( this.aaManager.getPermission("/TestingPath", "95", "group").equals("") );
		assertTrue( this.aaManager.getPermission("/TestingPath", "96", "group").equals("w") );
		
		//Case2 
		assertTrue( this.aaManager.importAccounts() == 1);
		assertTrue( this.aaManager.importGroups() == 1);
		assertTrue( this.aaManager.importGroupMember() == 1);
		assertTrue( this.aaManager.importPermissions() == 1);
	}
	
}
