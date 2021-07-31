package ntut.csie.samt.manager;

import junit.framework.TestCase;
import java.util.List;
import java.io.File;

import ntut.csie.samt.group.GroupMemberType;
import ntut.csie.samt.group.IGroup;
import ntut.csie.samt.*;

public class AuthorizationCenterTest extends TestCase {

	private AuthorizationCenter authorizationCenter;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		authorizationCenter = new AuthorizationCenter();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		File target = new File("WebContent/TestFile/AuthFileTest/tmp2");
		
		//刪除測試暫存檔
		if( target.exists() ){ 
			target.delete();
		}
	}

	/**
	 * 測試新增群組, 測試取得字串群組列表
	 * 
	 * Case1 加入不存在之群組
	 */
	public void testAddGroup1(){
		//Case 1 加入不存在之群組
		//測試是否有加入成功
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		List<String> groupList = authorizationCenter.getStringGroupList();
		
		//看取得之列表數目是否正確
		assertTrue( groupList.size() == 3 );
		
		//看所加入之群組名稱是否正確
		assertTrue( groupList.contains("group1") );
		assertTrue( groupList.contains("group2") );
		assertTrue( groupList.contains("group3") );
	}
	
	/**
	 * 測試新增群組, 測試取得字串群組列表
	 * 
	 * Case2 加入已存在之群組
	 */
	public void testAddGroup2(){
		//Case 2 加入已存在之群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		//重新加入group1
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		List<String> groupList = authorizationCenter.getStringGroupList();
		
		//看所加入之群組名稱是否正確
		assertTrue( groupList.contains("group1") );
		assertTrue( groupList.contains("group2") );
		assertTrue( groupList.contains("group3") );
	}
	
	/**
	 * 測試新增群組, 測試取得字串群組列表
	 * 
	 * Case3 加入名稱為""之群組
	 */
	public void testAddGroup3(){
		//Case 3 加入名稱為""之群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		//加入group ""
		authorizationCenter.addGroup("");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		List<String> groupList = authorizationCenter.getStringGroupList();
		//看所加入之群組名稱是否正確
		assertTrue( groupList.contains("group1") );
		assertTrue( groupList.contains("group2") );
		assertTrue( groupList.contains("group3") );
		assertTrue( !groupList.contains("") );
	}
	
	/**
	 * 測試修改群組名稱, 包含群組列表, 在其它群組成員的名稱,
	 * 在讀取權限所設定之名稱
	 * 
	 * Case1 修改存在群組成不存在的群組名稱
	 */
	public void testModifyGroupName1(){
		//加入群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//加入群組成員
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//將group1和group3加入group2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//將group1和group2加入group3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);		
		
		//Case 1 修改存在群組成不存在的群組名稱
		assertTrue( authorizationCenter.modifyGroupName("group1", "group4") );
		
		//測試群組列表是否修改成功
		List<String> groupList_String = authorizationCenter.getStringGroupList();
		
		//看取得之列表數目是否正確
		assertTrue( groupList_String.size() == 3 );
		
		//看所加入之群組名稱是否正確
		assertFalse( groupList_String.contains("group1") );
		assertTrue( groupList_String.contains("group2") );
		assertTrue( groupList_String.contains("group3") );
		assertTrue( groupList_String.contains("group4") );
		
		//測試群組成員列表是否修改成功
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//測試加入group2修改之後的名稱, group1應不存在, group4應存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group1", 
				GroupMemberType.group) == null );
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group4", 
				GroupMemberType.group) != null );
		//測試加入group3修改之後的名稱, group1應不存在, group4應存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group1", 
				GroupMemberType.group) == null );
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group4", 
				GroupMemberType.group) != null );
	}
	
	/**
	 * 測試修改群組名稱, 包含群組列表, 在其它群組成員的名稱,
	 * 在讀取權限所設定之名稱
	 * 
	 * Case2 修改不存在之群組
	 */
	public void testModifyGroupName2(){
		//加入群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//加入群組成員
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//將group1和group3加入group2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//將group1和group2加入group3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case 2 修改不存在之群組
		assertFalse( authorizationCenter.modifyGroupName("group4", "group5") );
		
		List<String> groupList_String = authorizationCenter.getStringGroupList();
		
		//看取得之列表數目是否正確
		assertTrue( groupList_String.size() == 3 );
		
		//看所加入之群組名稱是否正確
		assertTrue( groupList_String.contains("group1") );
		assertTrue( groupList_String.contains("group2") );
		assertTrue( groupList_String.contains("group3") );
		assertFalse( groupList_String.contains("group4") );
		assertFalse( groupList_String.contains("group5") );
		
		//測試群組成員列表是否修改成功
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//測試加入group2修改之後的名稱, group1應存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group1", 
				GroupMemberType.group) != null );
		//測試加入group3修改之後的名稱, group1應不存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group1", 
				GroupMemberType.group) != null );
	}
	
	/**
	 * 測試修改群組名稱, 包含群組列表, 在其它群組成員的名稱,
	 * 在讀取權限所設定之名稱
     *
	 * Case3 修改存在群組成存在群組名稱
	 */
	public void testModifyGroupName3(){
		//加入群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//加入群組成員
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//將group1和group3加入group2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//將group1和group2加入group3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case 3 修改存在群組成存在群組名稱
		assertFalse( authorizationCenter.modifyGroupName("group2", "group3") );
		
		List<String> groupList_String = authorizationCenter.getStringGroupList();
		
		//看取得之列表數目是否正確
		assertTrue( groupList_String.size() == 3 );
		
		//看所加入之群組名稱是否正確
		assertTrue( groupList_String.contains("group2") );
		assertTrue( groupList_String.contains("group3") );
		assertTrue( groupList_String.contains("group1") );
		
		//測試群組成員列表是否修改成功
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//測試加入group2修改之後的名稱, group1應存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group1", 
				GroupMemberType.group) != null );
		//測試加入group3修改之後的名稱, group1應存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group1", 
				GroupMemberType.group) != null );
	}
	
	/**
	 * 測試刪除群組, 包含群組列表, 在其它群組成員的名稱,
	 * 在讀取權限所設定之名稱
	 * 
	 * Case1 刪除第一個群組
	 */
	public void testDeleteGroup1(){
		//加入群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//加入群組成員
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//將group1和group3加入group2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//將group1和group2加入group3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case1 刪除第一個群組
		authorizationCenter.deleteGroup("group1");
		
		//刪除之後大小應為2
		assertTrue( authorizationCenter.getGroupSize() == 2 );
		
		//所找到的group應還存在
		assertTrue( authorizationCenter.findGroup("group2") != null );
		assertTrue( authorizationCenter.findGroup("group3") != null );
		//所找到的group應為null
		assertTrue( authorizationCenter.findGroup("group1") == null );
		
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//在group2的群組成員中, group1應不存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group1", 
				GroupMemberType.group) == null );
		//在group3的群組成員中, group1應不存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group1", 
				GroupMemberType.group) == null );
	}
	
	/**
	 * 測試刪除群組, 包含群組列表, 在其它群組成員的名稱,
	 * 在讀取權限所設定之名稱
	 * 
	 * Case2 刪除最後一個群組
	 */
	public void testDeleteGroup2(){
		//加入群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//加入群組成員
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//將group1和group3加入group2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//將group1和group2加入group3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case2 刪除最後一個群組
		authorizationCenter.deleteGroup("group3");
		
		//刪除之後大小應為2
		assertTrue( authorizationCenter.getGroupSize() == 2 );
		
		//所找到的group應還存在
		assertTrue( authorizationCenter.findGroup("group1") != null );
		assertTrue( authorizationCenter.findGroup("group2") != null );
		//所找到的group應為null
		assertTrue( authorizationCenter.findGroup("group3") == null );
		
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//在group1的群組成員中, group3應不存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group1)).findGroupMember("group3", 
				GroupMemberType.group) == null );
		//在group2的群組成員中, group3應不存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group3", 
				GroupMemberType.group) == null );
	}
	
	/**
	 * 測試刪除群組, 包含群組列表, 在其它群組成員的名稱,
	 * 在讀取權限所設定之名稱
	 * 
	 * Case3 刪除中間的群組
	 */
	public void testDeleteGroup3(){
		//加入群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//加入群組成員
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//將group1和group3加入group2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//將group1和group2加入group3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case3 刪除中間的群組
		authorizationCenter.deleteGroup("group2");
		
		//刪除之後大小應為2
		assertTrue( authorizationCenter.getGroupSize() == 2 );
		
		//所找到的group應還存在
		assertTrue( authorizationCenter.findGroup("group1") != null );
		assertTrue( authorizationCenter.findGroup("group3") != null );
		//所找到的group應為null
		assertTrue( authorizationCenter.findGroup("group2") == null );
		
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//在group1的群組成員中, group2應不存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group1)).findGroupMember("group2", 
				GroupMemberType.group) == null );
		//在group3的群組成員中, group2應不存在
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group2", 
				GroupMemberType.group) == null );
		
	}
	
	/**
	 * 測試刪除群組, 包含群組列表, 在其它群組成員的名稱,
	 * 在讀取權限所設定之名稱
	 * 
	 * Case4 刪除不存在的群組
	 */
	public void testDeleteGroup4(){
		//加入群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//加入群組成員
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//將group1和group3加入group2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//將group1和group2加入group3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case4 刪除不存在的群組
		authorizationCenter.deleteGroup("group4");
		
		//刪除之後大小應為3
		assertTrue( authorizationCenter.getGroupSize() == 3 );
		
		//所找到的group應還存在
		assertTrue( authorizationCenter.findGroup("group1") != null );
		assertTrue( authorizationCenter.findGroup("group2") != null );
		assertTrue( authorizationCenter.findGroup("group3") != null );

	}
	
	/**
	 * 測試刪除群組, 包含群組列表, 在其它群組成員的名稱,
	 * 在讀取權限所設定之名稱
	 * 
	 * Case5 刪除所有群組
	 */
	public void testDeleteGroup5(){
		//加入群組
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//加入群組成員
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//將group1和group3加入group2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//將group1和group2加入group3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case5 刪除所有群組
		authorizationCenter.deleteGroup("group1");
		authorizationCenter.deleteGroup("group2");
		authorizationCenter.deleteGroup("group3");

		//刪除之後大小應為0
		assertTrue( authorizationCenter.getGroupSize() == 0 );
		
		//群組應都不存在
		assertTrue( authorizationCenter.findGroup("group1") == null );
		assertTrue( authorizationCenter.findGroup("group2") == null );
		assertTrue( authorizationCenter.findGroup("group3") == null );		
	}
	
	/**
	 * 測試讀取權限認證檔案之後的群組與成員名稱與
	 * 權限是否正確
	 * *群組名稱
	 * Case1 讀取不存在的檔案
	 */
	public void testLoad1(){
		List<IGroup> groupList = null;
		
		//Case1 讀取不存在的檔案
		this.authorizationCenter.load("WebContent/TestFile/Nofile");
		groupList = this.authorizationCenter.getGroupList();
		//應沒有群組資料
		assertTrue( groupList == null);
	}
	
	/**
	 * 測試讀取權限認證檔案之後的群組與成員名稱與
	 * 權限是否正確
	 * *群組名稱
	 * Case2 讀取沒有群組的檔案
	 */
	public void testLoad2(){
		List<IGroup> groupList = null;
		//Case2 讀取沒有群組標頭的檔案[groups]
		this.authorizationCenter.load("WebContent/TestFile/testLoad_noGroupHeader.txt");
		groupList = this.authorizationCenter.getGroupList();
		//應沒有群組資料
		assertTrue( groupList == null);
	}

	/**
	 * 測試讀取權限認證檔案之後的群組與成員名稱與
	 * 權限是否正確
	 * *群組名稱
	 * Case3 正常讀取
	 */
	public void testLoad3(){
		List<IGroup> groupList = null;
		//Case3 正常讀取
		this.authorizationCenter.load("WebContent/TestFile/testLoad.txt");
		groupList = this.authorizationCenter.getGroupList();
		//測試群組列表
		assertTrue( groupList != null);
		//測試個數
		assertTrue( groupList.size() == 4);
		
		IGroup group1 = groupList.get(0);
		IGroup group2 = groupList.get(1);
		IGroup group3 = groupList.get(2);
		IGroup group4 = groupList.get(3);
		//測試所讀取之群組名稱是否正確
		assertTrue( group1.getName().equals("ST_team3") );
		assertTrue( group2.getName().equals("admin") );
		assertTrue( group3.getName().equals("ST_team1") );
		assertTrue( group4.getName().equals("ST_team2") );
		
		//測試群組成員列表
		//測試個數
		assertTrue( group1.getGroupMemberSize() == 0 );
		assertTrue( group2.getGroupMemberSize() == 2 );
		assertTrue( group3.getGroupMemberSize() == 3 );
		assertTrue( group4.getGroupMemberSize() == 1 );
		//測試群組成員名稱是否正確
		assertTrue( group1.findGroupMember("ST_team3",GroupMemberType.group) == null );
		
		assertTrue( group2.findGroupMember("franklin",GroupMemberType.account) != null );
		assertTrue( group2.findGroupMember("ST_team1",GroupMemberType.group) != null );
		
		assertTrue( group3.findGroupMember("franklin",GroupMemberType.account) != null );
		assertTrue( group3.findGroupMember("lenwind",GroupMemberType.account) != null );
		assertTrue( group3.findGroupMember("frankcheng",GroupMemberType.account) != null );
		
		assertTrue( group4.findGroupMember("thenewid",GroupMemberType.account) != null );
	}
	
	/**
	 * 測試讀取權限認證檔案之後的群組與成員名稱與
	 * 權限是否正確
	 * *群組名稱
	 * Case4 讀取無群組資料檔案
	 */
	public void testLoad4(){
		List<IGroup> groupList = null;
		//Case4 讀取無群組資料檔案
		this.authorizationCenter.load("WebContent/TestFile/testLoad_noGroup.txt");
		groupList = this.authorizationCenter.getGroupList();
		//應沒有群組資料
		assertTrue( groupList == null);
	}
	
	/**
	 * 測試儲存權限認證檔案之後的群組與成員名稱是否正確
	 * 進行一些操作,儲存之後再重新讀取,
	 * 驗證內容是否正確
	 * 
	 * Case1 已存在內容之權限認證檔案
	 */
	public void testSave1(){

		File source = new File("WebContent/TestFile/AuthFileTEst/authz");
		File target = new File("WebContent/TestFile/AuthFileTEst/tmp2");
		FileCopy.copy(source, target);
		
		this.authorizationCenter.load(target.getPath());
		//進行一些操作
		
		//新增群組
		this.authorizationCenter.addGroup("newGroup");
		//新增成員
		this.authorizationCenter.addGroupMember("newGroup", "newUser", "account");
		this.authorizationCenter.addGroupMember("newGroup", "ST_team2", "group");
		//刪除群組
		this.authorizationCenter.deleteGroup("admin");
		//新增權限
		this.authorizationCenter.addPermission("/newpath", "newGroup", "group", "r");
		//修改權限
		this.authorizationCenter.modifyPermission("/OOAD/VSTS", "*", "group", "rw");
		//刪除權限
		this.authorizationCenter.deletePermission("/OOAD/CSRM", "thenewid", "account");
		
		this.authorizationCenter.save();
		this.authorizationCenter = null;
		
		this.authorizationCenter = new AuthorizationCenter();
		//重新讀取
		
		this.authorizationCenter.load(target.getPath());
		
		//測試群組是否正確
		List<String> groupList =  this.authorizationCenter.getStringGroupList();
		assertTrue( groupList != null );
		assertTrue( ""  + groupList.size() , groupList.size() == 4 );
		assertTrue( groupList.indexOf("admin") == -1 );
		assertTrue( groupList.indexOf("ST_team1") != -1 );
		assertTrue( groupList.indexOf("ST_team2") != -1 );
		assertTrue( groupList.indexOf("ST_team3") != -1 );
		assertTrue( groupList.indexOf("newGroup") != -1 );
		
		//測試群組成員是否正確
		List<String> memberList = this.authorizationCenter.getStringMemberList("admin");
		assertTrue( memberList == null  || memberList.size() == 0 );
		
		memberList = this.authorizationCenter.getStringMemberList("ST_team1");
		assertTrue( memberList.size() == 3 );
		assertTrue( memberList.indexOf("franklin") != -1 );
		assertTrue( memberList.indexOf("lenwind") != -1 );
		assertTrue( memberList.indexOf("frankcheng") != -1 );
		
		memberList = this.authorizationCenter.getStringMemberList("ST_team2");
		assertTrue( memberList.size() == 3 );
		
		memberList = this.authorizationCenter.getStringMemberList("ST_team3");
		assertTrue( memberList.size() == 2 );
		assertTrue( memberList.indexOf("@ST_team1") != -1 );
		
		//測試權限部分是否正確
		List<String> pathMemberList = this.authorizationCenter.getPathMemberList("/newpath");
		assertTrue( pathMemberList.size() == 1 );
		assertTrue( pathMemberList.indexOf("@newGroup") != -1 );
		String permission = this.authorizationCenter.getPermission("/newpath", "newGroup", "group");
		assertTrue( permission.equals("r") );
		
		pathMemberList = this.authorizationCenter.getPathMemberList("/OOAD/VSTS");
		assertTrue( pathMemberList.size() == 3 );
		permission = this.authorizationCenter.getPermission("/OOAD/VSTS", "*", "group");
		assertTrue( permission.equals("rw") );
		
		pathMemberList = this.authorizationCenter.getPathMemberList("/OOAD/CSRM");
		assertTrue( pathMemberList.size() == 1 );
		assertTrue( pathMemberList.indexOf("thenewid") == -1 );
		permission = this.authorizationCenter.getPermission("/OOAD/CSRM", "thenewid", "account");
		assertTrue( permission.equals("") );
	}
	
	/**
	 * 測試儲存權限認證檔案之後的群組與成員名稱是否正確
	 * 進行一些操作,儲存之後再重新讀取,
	 * 驗證內容是否正確
	 * 
	 * Case2 刪除所有群組並且讀取
	 */
	public void testSave2(){

		File source = new File("WebContent/TestFile/AuthFileTest/authz");
		File target = new File("WebContent/TestFile/AuthFileTest/tmp2");
		FileCopy.copy(source, target);
		
		this.authorizationCenter.load(target.getPath());
		
		//刪除所有群組
		this.authorizationCenter.deleteGroup("admin");
		this.authorizationCenter.deleteGroup("ST_team1");
		this.authorizationCenter.deleteGroup("ST_team2");
		this.authorizationCenter.deleteGroup("ST_team3");
		//測試路徑成員包含群組成員ㄉ應該被刪除
		List<String> pathMember = this.authorizationCenter.getPathMemberList("/");
		assertTrue( pathMember.size() == 1 );
		assertTrue( !pathMember.contains("@admin") );
		assertTrue( pathMember.contains("@*") );
		
		pathMember = this.authorizationCenter.getPathMemberList("/ST/Team1");
		assertTrue( pathMember.size() == 1 );
		assertTrue( !pathMember.contains("@ST_team1") );
		assertTrue( pathMember.contains("@*") );
		
		this.authorizationCenter.save();
		
		//重新讀取
		this.authorizationCenter = null;
		this.authorizationCenter = new AuthorizationCenter();
		this.authorizationCenter.load(target.getPath());
		
		List<String> groupList = this.authorizationCenter.getStringGroupList();
		//回傳的非空則size應為0, 因為全刪除了
		if( groupList != null )
			assertTrue( ""+ groupList.size() , groupList.size() == 0 );
		//測試路徑數量是否正確	
		List<String> pathList = this.authorizationCenter.getPermissionPathList();
		assertTrue( pathList != null );		
		assertTrue( ""+ pathList.size(), pathList.size() == 14 );
		//測試路徑成員包含群組成員ㄉ應該被刪除, ps在讀檔時就擋掉了讀取不存在的群組
		pathMember = this.authorizationCenter.getPathMemberList("/");
		assertTrue( pathMember.size() == 1 );
		assertTrue( !pathMember.contains("@admin") );
		assertTrue( pathMember.contains("@*") );
		
		pathMember = this.authorizationCenter.getPathMemberList("/ST/Team1");
		assertTrue( pathMember.size() == 1 );
		assertTrue( !pathMember.contains("@ST_team1") );
		assertTrue( pathMember.contains("@*") );
	}
	
	/**
	 * 測試儲存權限認證檔案之後的群組與成員名稱是否正確
	 * 進行一些操作,儲存之後再重新讀取,
	 * 驗證內容是否正確
	 * 
	 * Case3 刪除路徑中所有成員,沒成員之路徑應不存於檔案中
	 */
	public void testSave3(){

		File source = new File("WebContent/TestFile/AuthFileTest/authz");
		File target = new File("WebContent/TestFile/AuthFileTest/tmp2");
		FileCopy.copy(source, target);
		
		this.authorizationCenter.load(target.getPath());
		
		//刪除所有群組
		this.authorizationCenter.deletePermission("/OOAD/VSTS", "bluewind", "account");
		this.authorizationCenter.deletePermission("/OOAD/VSTS", "ccchang", "account");
		this.authorizationCenter.deletePermission("/OOAD/VSTS", "*", "group");
		
		//測試路徑成員應該被刪除
		List<String> pathMember = this.authorizationCenter.getPathMemberList("/OOAD/VSTS");
		assertTrue( pathMember.size() == 0 );
		assertTrue( !pathMember.contains("bluewind") );
		assertTrue( !pathMember.contains("ccchang") );
		assertTrue( !pathMember.contains("@*") );

		this.authorizationCenter.save();
		
		//重新讀取
		this.authorizationCenter = null;
		this.authorizationCenter = new AuthorizationCenter();
		this.authorizationCenter.load(target.getPath());
		
		//測試路徑數量是否正確, 路徑為空的應該不存在, 即刪除了/OOAD/VSTS
		List<String> pathList = this.authorizationCenter.getPermissionPathList();
		assertTrue( pathList != null );		
		assertTrue( ""+ pathList.size(), pathList.size() == 13 );
		
		//測試路徑成員應該被刪除
		pathMember = this.authorizationCenter.getPathMemberList("/OOAD/VSTS");
		assertTrue( pathMember == null );
	}
	
}


