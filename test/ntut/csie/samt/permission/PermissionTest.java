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
	 * ���ժ�l�غc��T�O�_���T,
	 * �v�����A�����S��
	 */
	public void testInitalData(){
		assertTrue( this.permission.getPathMember().getName().equals("group") );
		assertTrue( this.permission.getType().equals("") );
	}
	
	/**
	 * ���խק��v��,
	 * rw,r,w,�P�Ŧr��
	 * 
	 * Case1 �ק令rw
	 * Case2 �ק令r
	 * Case3 �ק令w
	 * Case4 �ק令�Ŧr��
	 * Case5 �ק令���b�|�Ӥ���
	 */
	public void testSetType(){
		//Case1 �ק令rw
		this.permission.setType("rw");
		assertTrue( this.permission.getType().equals("rw") );
		//Case2 �ק令r
		this.permission.setType("r");
		assertTrue( this.permission.getType().equals("r") );
		//Case3 �ק令w
		this.permission.setType("w");
		assertTrue( this.permission.getType().equals("w") );
		//Case4 �ק令�Ŧr��
		this.permission.setType("");
		assertTrue( this.permission.getType().equals("") );
		//Case5 �ק令���b�|�Ӥ���
		this.permission.setType("5566 is 87");
		assertTrue( this.permission.getType().equals("") );
	}
	
	/**
	 * ���զr���নPermission Type
	 * 
	 * Case1 ��rw
	 * Case2 ��r
	 * Case3 ��w
	 * Case4 ��Ŧr��
	 * Case5 �ण�b�H�W�|�Ӥ�
	 */
	public void testTypeTranslateToPermissionType(){
		//Case1 ��rw
		assertTrue( PermissionType.rw == this.permission.typeTranslateToPermissionType("rw") );
		//Case2 ��r
		assertTrue( PermissionType.read == this.permission.typeTranslateToPermissionType("r") );
		//Case3 ��w
		assertTrue( PermissionType.write == this.permission.typeTranslateToPermissionType("w") );
		//Case4 ��Ŧr��
		assertTrue( PermissionType.none == this.permission.typeTranslateToPermissionType("") );
		//Case5 �ण�b�H�W�|�Ӥ�
		assertTrue( PermissionType.none == this.permission.typeTranslateToPermissionType("K one is 87") );
	}
	
	/**
	 * ���զr���নPermission Type
	 * 
	 * Case1 ��PermissionType.rw
	 * Case2 ��PermissionType.read
	 * Case3 ��PermissionType.write
	 * Case4 ��PermissionType.none
	 */
	public void testTypeTranslateToString(){
		//Case1 ��PermissionType.rw
		assertTrue( this.permission.typeTranslateToString(PermissionType.rw).equals("rw") );
		//Case2 ��PermissionType.read
		assertTrue( this.permission.typeTranslateToString(PermissionType.read).equals("r") );
		//Case3 ��PermissionType.write
		assertTrue( this.permission.typeTranslateToString(PermissionType.write).equals("w") );
		//Case4 ��PermissionType.none
		assertTrue( this.permission.typeTranslateToString(PermissionType.none).equals("") );
	}
}
