package ntut.csie.samt.permission;

import junit.framework.TestCase;
import ntut.csie.samt.group.Group;
import ntut.csie.samt.group.IGroup;
import ntut.csie.samt.permission.*;

public class PermissionTest extends TestCase {

	private Permission permission;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		IGroup group = new Group();
		group.setName("group");
		this.permission = new Permission(group);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 測試初始建構資訊是否正確,
	 * 權限型態應為沒有
	 */
	public void testInitalData(){
		assertTrue( this.permission.getPathMember().getName().equals("group") );
		assertTrue( this.permission.getType().equals("") );
	}
	
	/**
	 * 測試修改權限,
	 * rw,r,w,與空字串
	 * 
	 * Case1 修改成rw
	 * Case2 修改成r
	 * Case3 修改成w
	 * Case4 修改成空字串
	 * Case5 修改成不在四個內的
	 */
	public void testSetType(){
		//Case1 修改成rw
		this.permission.setType("rw");
		assertTrue( this.permission.getType().equals("rw") );
		//Case2 修改成r
		this.permission.setType("r");
		assertTrue( this.permission.getType().equals("r") );
		//Case3 修改成w
		this.permission.setType("w");
		assertTrue( this.permission.getType().equals("w") );
		//Case4 修改成空字串
		this.permission.setType("");
		assertTrue( this.permission.getType().equals("") );
		//Case5 修改成不在四個內的
		this.permission.setType("5566 is 87");
		assertTrue( this.permission.getType().equals("") );
	}
	
	/**
	 * 測試字串轉成Permission Type
	 * 
	 * Case1 轉rw
	 * Case2 轉r
	 * Case3 轉w
	 * Case4 轉空字串
	 * Case5 轉不在以上四個內
	 */
	public void testTypeTranslateToPermissionType(){
		//Case1 轉rw
		assertTrue( PermissionType.rw == this.permission.typeTranslateToPermissionType("rw") );
		//Case2 轉r
		assertTrue( PermissionType.read == this.permission.typeTranslateToPermissionType("r") );
		//Case3 轉w
		assertTrue( PermissionType.write == this.permission.typeTranslateToPermissionType("w") );
		//Case4 轉空字串
		assertTrue( PermissionType.none == this.permission.typeTranslateToPermissionType("") );
		//Case5 轉不在以上四個內
		assertTrue( PermissionType.none == this.permission.typeTranslateToPermissionType("K one is 87") );
	}
	
	/**
	 * 測試字串轉成Permission Type
	 * 
	 * Case1 轉PermissionType.rw
	 * Case2 轉PermissionType.read
	 * Case3 轉PermissionType.write
	 * Case4 轉PermissionType.none
	 */
	public void testTypeTranslateToString(){
		//Case1 轉PermissionType.rw
		assertTrue( this.permission.typeTranslateToString(PermissionType.rw).equals("rw") );
		//Case2 轉PermissionType.read
		assertTrue( this.permission.typeTranslateToString(PermissionType.read).equals("r") );
		//Case3 轉PermissionType.write
		assertTrue( this.permission.typeTranslateToString(PermissionType.write).equals("w") );
		//Case4 轉PermissionType.none
		assertTrue( this.permission.typeTranslateToString(PermissionType.none).equals("") );
	}
}
