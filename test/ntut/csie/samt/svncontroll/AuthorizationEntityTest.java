package ntut.csie.samt.svncontroll;

import junit.framework.TestCase;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import ntut.csie.samt.svncontroll.AuthorizationEntity;

public class AuthorizationEntityTest extends TestCase {

	private AuthorizationEntity authorizationEntity = null;
	private File tmpFile;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		this.tmpFile = new File("WebContent/TestFile/tmp");
		
		if( !this.tmpFile.exists() ){
			this.tmpFile.createNewFile();
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		if( this.tmpFile.exists() ){
			this.tmpFile.delete();
		}
	}

	/**
	 * 測試讀取檔案後取得群組列表
	 * 
	 * Case1 讀取正常檔案
	 * Case2 讀取沒有群組標頭的檔案[groups]
	 * Case3 讀取無群組資料檔案
	 */
	public void testGetGroupList1(){
		//Case1 讀取正常檔案
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad.txt"));
		this.authorizationEntity.load();
		String []groupList = this.authorizationEntity.getGroupList();
		
		assertTrue( groupList.length == 4 );
		assertTrue( groupList[0].equals("admin") );
		assertTrue( groupList[1].equals("ST_team1") );
		assertTrue( groupList[2].equals("ST_team2") );
		assertTrue( groupList[3].equals("ST_team3") );
	}
	
	public void testGetGroupList2(){
		//Case2 讀取沒有群組標頭的檔案[groups]
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_noGroupHeader.txt"));
		this.authorizationEntity.load();
		String []groupList = this.authorizationEntity.getGroupList();
		groupList = this.authorizationEntity.getGroupList();
		assertTrue( groupList.length == 0 );
	}
	
	public void testGetGroupList3(){
		//Case3 讀取無資料檔案
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_noGroup.txt"));
		this.authorizationEntity.load();
		String []groupList = this.authorizationEntity.getGroupList();
		groupList = this.authorizationEntity.getGroupList();
		assertTrue( groupList.length == 0 );
	}
	
	/**
	 * 測試讀取檔案後取得群組成員列表
	 * 
	 * Case1 讀取正常檔案
	 */
	public void testGetGroupMemberList(){
		//Case1 讀取正常檔案
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad.txt"));
		this.authorizationEntity.load();
		String []groupList = this.authorizationEntity.getGroupList();
		//測試個數
		assertTrue( groupList.length == 4 );
		//測試群組名稱
		assertTrue( groupList[0].equals("admin") );
		assertTrue( groupList[1].equals("ST_team1") );
		assertTrue( groupList[2].equals("ST_team2") );
		assertTrue( groupList[3].equals("ST_team3") );
		//測試群組成員是否正確
		String []groupMemberList = authorizationEntity.getGroupMemberList(groupList[0]);
		
		assertTrue( groupMemberList.length == 2 );
		assertTrue( groupMemberList[0].equals("franklin") );
		assertTrue( groupMemberList[1].equals("@ST_team1") );
		
		groupMemberList = authorizationEntity.getGroupMemberList(groupList[1]);
		
		assertTrue( groupMemberList.length == 3 );
		assertTrue( groupMemberList[0].equals("franklin") );
		assertTrue( groupMemberList[1].equals("lenwind") );
		assertTrue( groupMemberList[2].equals("frankcheng") );
		
		groupMemberList = authorizationEntity.getGroupMemberList(groupList[2]);
		
		assertTrue( groupMemberList.length == 1 );
		assertTrue( groupMemberList[0].equals("thenewid") );
		
		groupMemberList = authorizationEntity.getGroupMemberList(groupList[3]);
		
		assertTrue( groupMemberList == null );
		
	}
	
	/**
	 * 測試讀取檔案後取得路徑列表
	 * 
	 * Case1 讀取正常檔案
	 */
	public void testGetRepositoryPath(){
		//Case 1 讀取正常檔案
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_permission.txt"));
		//讀取權限資料
		this.authorizationEntity.loadPermissions();
		//取得路徑列表
		String[] rpList = this.authorizationEntity.getRepositoryPath();
		assertTrue( rpList != null );
		//測試路徑數是否正確
		assertTrue( rpList.length == 14 );
		//測試取得之名稱 測試第一個,第八個,與最後一個
		assertTrue( rpList[0].equals("/") ); //第一個
		assertTrue( rpList[7].equals("/PSP/nickhades") ); //第八個
		assertTrue( rpList[13].equals("/OOAD/VSTS") ); //第十四個
	}
	
	/**
	 * 測試讀取檔案後取得某路徑下的成員列表
	 * 
	 * Case1 讀取正常檔案且存在之路徑
	 * Case2 讀取正常檔案且不存在之路徑
	 */
	public void testGetFileMemberList(){
		//Case1 讀取正常檔案且存在之路徑
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_permission.txt"));
		//讀取權限資料
		this.authorizationEntity.loadPermissions();
		String [] members;
		//測試路徑 "/"
		members = authorizationEntity.getFileMemberList("/");
		assertTrue(members[0].equals("*"));
		assertTrue(members[1].equals("@admin"));
		//測試路徑 "/OOAD/VMMS"
		members = authorizationEntity.getFileMemberList("/OOAD/VMMS");
		assertTrue(members[0].equals("*"));
		assertTrue(members[1].equals("frankcheng"));
		assertTrue(members[2].equals("lenwind"));
		//測試路徑 "/OOAD/VSTS"
		members = authorizationEntity.getFileMemberList("/OOAD/VSTS");
		assertTrue(members[0].equals("*"));
		assertTrue(members[1].equals("bluewind"));
		assertTrue(members[2].equals("ccchang"));	
		
		//Case2 讀取正常檔案且不存在之路徑
		members = authorizationEntity.getFileMemberList("/OOAD/VSTS/NO");
		assertTrue(members.length == 0);
	}

	/**
	 * 測試取得Permission的權限資料 
	 * 
	 * Case1 讀取正常檔案且讀取存在之路徑與使用者
	 * Case2 讀取正常檔案且不存在之路徑與存在使用者
	 * Case3 讀取正常檔案且存在之路徑與不存在使用者
	 */
	public void testGetPermissionsData(){
		//Case1 讀取正常檔案且存在之路徑
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_permission.txt"));
		//讀取權限資料
		this.authorizationEntity.loadPermissions();
		//測試讀取出來的權限是否正確
		String permission = this.authorizationEntity.getPermissionsData("/","@admin");
		assertTrue(permission.equals("rw"));
		permission = this.authorizationEntity.getPermissionsData("/","*");
		assertTrue(permission.equals("r"));
		permission = this.authorizationEntity.getPermissionsData("/OOAD/SAMT","*");
		assertTrue(permission.equals(""));
		permission = this.authorizationEntity.getPermissionsData("/OOAD/SAMT","franklin");
		assertTrue(permission.equals("rw"));
		permission = this.authorizationEntity.getPermissionsData("/OOAD/VSTS","bluewind");
		assertTrue(permission.equals("rw"));
		
		//Case2 讀取正常檔案且不存在之路徑與存在使用者
		permission = this.authorizationEntity.getPermissionsData("/NO","@admin");
		assertTrue(!permission.equals("rw"));
		assertTrue(!permission.equals("r"));
		assertTrue(!permission.equals("w"));
		assertTrue(permission.equals(""));
		
		//Case3 讀取正常檔案且存在之路徑與不存在使用者
		permission = this.authorizationEntity.getPermissionsData("/OOAD/VMMS","@admin");
		assertTrue(!permission.equals("rw"));
		assertTrue(!permission.equals("r"));
		assertTrue(!permission.equals("w"));
		assertTrue(permission.equals(""));
	}
	
	/**
	 * 測試要存入的群組列表是否正確
	 */
	public void testSetGroupList(){
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/AuthFileTest/authz_empty"));
		//新的群組成員
		List<String> setGroupList = new ArrayList<String>();
		setGroupList.add("group1");
		setGroupList.add("group2");
		setGroupList.add("group3");
		setGroupList.add("group4");
		//設置群組成員
		this.authorizationEntity.setGroupList(setGroupList);
		//取得群組成員
		String [] getGroupList = this.authorizationEntity.getGroupList();
		assertTrue( getGroupList.length == 4 );
		//測試群組名稱是否正確
		assertTrue( getGroupList[0] , getGroupList[0].equals("group1") );
		assertTrue( getGroupList[1] , getGroupList[1].equals("group2") );
		assertTrue( getGroupList[2] , getGroupList[2].equals("group3") );
		assertTrue( getGroupList[3] , getGroupList[3].equals("group4") );
	}
	
	/**
	 * 測試設置群組成員
	 * 
	 * Case1 無群組成員之群組
	 * Case2 群組成員包含群組
	 * Case3 不存在的群組成員
	 */
	public void testSetGroupMember(){
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/AuthFileTest/authz_save"));
		//新的群組成員
		List<String> setGroupList = new ArrayList<String>();
		setGroupList.add("group1");
		setGroupList.add("group2");
		setGroupList.add("group3");
		//設置群組成員
		this.authorizationEntity.setGroupList(setGroupList);
		//取得群組成員
		String [] getGroupList = this.authorizationEntity.getGroupList();
		assertTrue( getGroupList.length == 3 );
		//測試群組名稱是否正確
		assertTrue( getGroupList[0] , getGroupList[0].equals("group1") );
		assertTrue( getGroupList[1] , getGroupList[1].equals("group2") );
		assertTrue( getGroupList[2] , getGroupList[2].equals("group3") );
		
		List<String> setMemberList = null;
		
		//Case1 無群組成員之群組
		//group1 沒有群組成員
		String [] getMemberList = this.authorizationEntity.getGroupMemberList("group1");
		assertTrue( getMemberList == null );
		
		//Case2 群組成員包含群組
		//group2 群組成員包含著group3
		setMemberList = new ArrayList<String>();
		setMemberList.add("@group3");
		setMemberList.add("user1");
		setMemberList.add("user2");
		this.authorizationEntity.setGroupMember("group2", setMemberList);
		
		getMemberList = this.authorizationEntity.getGroupMemberList("group2");
		assertTrue( getMemberList != null );
		assertTrue( getMemberList.length == 3 );
		//測試群組成員名稱是否正確
		assertTrue( getMemberList[0] , getMemberList[0].equals("@group3") );
		assertTrue( getMemberList[1] , getMemberList[1].equals("user1") );
		assertTrue( getMemberList[2] , getMemberList[2].equals("user2") );
		
		//Case3 不存在的群組成員
		this.authorizationEntity.setGroupMember("group4", setMemberList);
		getMemberList = this.authorizationEntity.getGroupMemberList("group4");
		assertTrue( getMemberList == null );
	}
	
	/**
	 * 測試設置權限資訊
	 * 
	 * Case1 設置群組(r)與*(r)
	 * Case2 設置一個使用者(r)與群組(r)
	 * Case3 設置一個使用者()與群組(rw)
	 */
	public void testSetPermissionData(){
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/AuthFileTest/authz_save"));
		List<String> pathMember = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		
		//Case1
		pathMember.add("@*");
		permissions.add("r");
		pathMember.add("@group1");
		permissions.add("r");
		
		this.authorizationEntity.setPermissionData("/", pathMember, permissions);
		
		pathMember.clear();
		permissions.clear();
		
		//Case2
		this.authorizationEntity.setPermissionData("/JCIS/Java", pathMember, permissions);
		
		//Case3
		pathMember.add("user1");
		permissions.add("");
		pathMember.add("@group1");
		permissions.add("rw");
		this.authorizationEntity.setPermissionData("/JCIS/Java/Test", pathMember, permissions);
		
	}
	
	/**
	 * 測試儲存檔案
	 * 
	 * Case1 測試完全沒設置資料儲存
	 */
	public void testSave1(){
		this.authorizationEntity = new AuthorizationEntity(this.tmpFile);
		//設置空資料
		this.authorizationEntity.setGroupList(null);
		this.authorizationEntity.setGroupMember(null, null);
		this.authorizationEntity.setPermissionData(null, null, null);
		this.authorizationEntity.save();
		
		//測試取得結果內容
		this.authorizationEntity = null;
		this.authorizationEntity = new AuthorizationEntity(this.tmpFile);
		//群組列表應為空
		String []groupList = this.authorizationEntity.getGroupList();
		if( groupList == null ){
			assertTrue( true );
		} else {
			assertTrue(""+ groupList.length, groupList.length == 0 );
		}
		//路徑列表應為空
		String []pathList = this.authorizationEntity.getRepositoryPath();
		if( pathList == null ){
			assertTrue( true );
		} else {
			assertTrue(""+ pathList.length, pathList.length == 0 );
		}
	}
	
}

