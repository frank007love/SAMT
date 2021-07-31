package ntut.csie.samt.permission;

import junit.framework.TestCase;

import ntut.csie.samt.permission.*;
import ntut.csie.samt.group.*;

public class RepositoryPathTest extends TestCase {

	private RepositoryPath repositoryPath = null;
	private IGroup group1,group2,user1,user2;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		//init repositortPath
		this.repositoryPath = new RepositoryPath("testpath");
		
		//init IGroup related
		this.group1 = new Group();
		this.group1.setName("group1");
		this.group2 = new Group();
		this.group2.setName("group2");
		this.user1 = new User();
		this.user1.setName("user1");
		this.user2 = new User();
		this.user2.setName("user2");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 測試新增Permission
	 * 
	 * Case1 新增不存在之成員
	 */
	public void testAddPermission1(){
		//新增一個成員
		this.repositoryPath.addPermission(group1,"");
		Permission permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		//測試群組名稱與初始型態
		IGroup tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("") );
		
		//新增一個成員
		this.repositoryPath.addPermission(group2,"");
		permission = this.repositoryPath.findPermission(group2);
		assertTrue( permission != null );
		//測試群組名稱與初始型態
		tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group2") );
		assertTrue( permission.getType().equals("") );
		
		//新增一個成員
		this.repositoryPath.addPermission(user2,"");
		permission = this.repositoryPath.findPermission(user2);
		assertTrue( permission != null );
		//測試群組名稱與初始型態
		tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("user2") );
		assertTrue( permission.getType().equals("") );
		
		//新增一個成員
		this.repositoryPath.addPermission(user1,"");
		permission = this.repositoryPath.findPermission(user1);
		assertTrue( permission != null );
		//測試群組名稱與初始型態
		tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("user1") );
		assertTrue( permission.getType().equals("") );
	}
	
	/**
	 * 測試新增Permission
	 * 
	 * Case2 新增已存在之成員
	 */
	public void testAddPermission2(){
		//新增一個成員
		this.repositoryPath.addPermission(group1,"");
		Permission permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		//測試群組名稱與初始型態
		IGroup tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("") );
		//新增相同之成員, 應不能加入
		assertFalse( this.repositoryPath.addPermission(group1,"") );
		permission = this.repositoryPath.findPermission(group1);
	}
	
	/**
	 * 測試修改群組權限
	 * 
	 * Case1 修改存在之群組權限
	 */
	public void testModifyPermission1(){
		//新增一個成員
		this.repositoryPath.addPermission(group1,"");
		Permission permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		//測試群組名稱與初始型態
		IGroup tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("") );
		//測試修改權限
		this.repositoryPath.modifyPermission(group1,"rw");
		tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("rw") );
	}
	
	/**
	 * 測試修改群組權限
	 * 
	 * Case2 修改不存在之群組權限
	 */
	public void testModifyPermission2(){
		//新增一個成員
		this.repositoryPath.addPermission(group1,"");
		Permission permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		//測試群組名稱與初始型態
		IGroup tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("") );
		//測試修改不存在群組權限
		assertFalse( this.repositoryPath.modifyPermission(group2,"rw") );
	}
	
	/**
	 * 測試刪除群組權限
	 * 
	 * Case1 刪除存在之群組權限
	 * Case2 刪除不存在之群組權限
	 */
	public void testRemovePermission1(){
		//add Permissions
		this.repositoryPath.addPermission(group1,"");
		this.repositoryPath.addPermission(user1,"");
		this.repositoryPath.addPermission(user2,"");
		//Case1 刪除存在之群組權限
		this.repositoryPath.removePermission(group1);
		assertTrue( this.repositoryPath.findPermission(group1) == null );
		this.repositoryPath.removePermission(user1);
		assertTrue( this.repositoryPath.findPermission(user1) == null );
		this.repositoryPath.removePermission(user2);
		assertTrue( this.repositoryPath.findPermission(user2) == null );
		//Case2 刪除不存在之群組權限
		assertFalse(this.repositoryPath.removePermission(group1));
		assertFalse(this.repositoryPath.removePermission(user1));
		assertFalse(this.repositoryPath.removePermission(user2));
	}
	
	/**
	 * 測試根據所設定的成員找尋權限
	 * 
	 * Case1 找尋第一個
	 * Case2 找尋中間的
	 * Case3 找尋最後一個
	 * Case4 找尋不存在的
	 */
	public void testFindPermission(){
		//add Permissions
		this.repositoryPath.addPermission(group1,"");
		this.repositoryPath.addPermission(user1,"");
		this.repositoryPath.addPermission(user2,"");
		
		Permission permission = null;
		
		//Case1 找尋第一個
		permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		assertTrue( permission.getPathMember().getName().equals("group1") );	
		//Case2 找尋中間的
		permission = this.repositoryPath.findPermission(user1);
		assertTrue( permission != null );
		assertTrue( permission.getPathMember().getName().equals("user1") );	
		//Case3 找尋最後一個
		permission = this.repositoryPath.findPermission(user2);
		assertTrue( permission != null );
		assertTrue( permission.getPathMember().getName().equals("user2") );	
		//Case4 找尋不存在的
		permission = this.repositoryPath.findPermission(group2);
		assertTrue( permission == null );
	
	}

}
