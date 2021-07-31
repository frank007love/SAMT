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
	 * ���շs�WPermission
	 * 
	 * Case1 �s�W���s�b������
	 */
	public void testAddPermission1(){
		//�s�W�@�Ӧ���
		this.repositoryPath.addPermission(group1,"");
		Permission permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		//���ոs�զW�ٻP��l���A
		IGroup tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("") );
		
		//�s�W�@�Ӧ���
		this.repositoryPath.addPermission(group2,"");
		permission = this.repositoryPath.findPermission(group2);
		assertTrue( permission != null );
		//���ոs�զW�ٻP��l���A
		tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group2") );
		assertTrue( permission.getType().equals("") );
		
		//�s�W�@�Ӧ���
		this.repositoryPath.addPermission(user2,"");
		permission = this.repositoryPath.findPermission(user2);
		assertTrue( permission != null );
		//���ոs�զW�ٻP��l���A
		tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("user2") );
		assertTrue( permission.getType().equals("") );
		
		//�s�W�@�Ӧ���
		this.repositoryPath.addPermission(user1,"");
		permission = this.repositoryPath.findPermission(user1);
		assertTrue( permission != null );
		//���ոs�զW�ٻP��l���A
		tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("user1") );
		assertTrue( permission.getType().equals("") );
	}
	
	/**
	 * ���շs�WPermission
	 * 
	 * Case2 �s�W�w�s�b������
	 */
	public void testAddPermission2(){
		//�s�W�@�Ӧ���
		this.repositoryPath.addPermission(group1,"");
		Permission permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		//���ոs�զW�ٻP��l���A
		IGroup tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("") );
		//�s�W�ۦP������, ������[�J
		assertFalse( this.repositoryPath.addPermission(group1,"") );
		permission = this.repositoryPath.findPermission(group1);
	}
	
	/**
	 * ���խק�s���v��
	 * 
	 * Case1 �ק�s�b���s���v��
	 */
	public void testModifyPermission1(){
		//�s�W�@�Ӧ���
		this.repositoryPath.addPermission(group1,"");
		Permission permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		//���ոs�զW�ٻP��l���A
		IGroup tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("") );
		//���խק��v��
		this.repositoryPath.modifyPermission(group1,"rw");
		tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("rw") );
	}
	
	/**
	 * ���խק�s���v��
	 * 
	 * Case2 �ק藍�s�b���s���v��
	 */
	public void testModifyPermission2(){
		//�s�W�@�Ӧ���
		this.repositoryPath.addPermission(group1,"");
		Permission permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		//���ոs�զW�ٻP��l���A
		IGroup tgroup = permission.getPathMember();
		assertTrue( tgroup.getName().equals("group1") );
		assertTrue( permission.getType().equals("") );
		//���խק藍�s�b�s���v��
		assertFalse( this.repositoryPath.modifyPermission(group2,"rw") );
	}
	
	/**
	 * ���էR���s���v��
	 * 
	 * Case1 �R���s�b���s���v��
	 * Case2 �R�����s�b���s���v��
	 */
	public void testRemovePermission1(){
		//add Permissions
		this.repositoryPath.addPermission(group1,"");
		this.repositoryPath.addPermission(user1,"");
		this.repositoryPath.addPermission(user2,"");
		//Case1 �R���s�b���s���v��
		this.repositoryPath.removePermission(group1);
		assertTrue( this.repositoryPath.findPermission(group1) == null );
		this.repositoryPath.removePermission(user1);
		assertTrue( this.repositoryPath.findPermission(user1) == null );
		this.repositoryPath.removePermission(user2);
		assertTrue( this.repositoryPath.findPermission(user2) == null );
		//Case2 �R�����s�b���s���v��
		assertFalse(this.repositoryPath.removePermission(group1));
		assertFalse(this.repositoryPath.removePermission(user1));
		assertFalse(this.repositoryPath.removePermission(user2));
	}
	
	/**
	 * ���ծھکҳ]�w��������M�v��
	 * 
	 * Case1 ��M�Ĥ@��
	 * Case2 ��M������
	 * Case3 ��M�̫�@��
	 * Case4 ��M���s�b��
	 */
	public void testFindPermission(){
		//add Permissions
		this.repositoryPath.addPermission(group1,"");
		this.repositoryPath.addPermission(user1,"");
		this.repositoryPath.addPermission(user2,"");
		
		Permission permission = null;
		
		//Case1 ��M�Ĥ@��
		permission = this.repositoryPath.findPermission(group1);
		assertTrue( permission != null );
		assertTrue( permission.getPathMember().getName().equals("group1") );	
		//Case2 ��M������
		permission = this.repositoryPath.findPermission(user1);
		assertTrue( permission != null );
		assertTrue( permission.getPathMember().getName().equals("user1") );	
		//Case3 ��M�̫�@��
		permission = this.repositoryPath.findPermission(user2);
		assertTrue( permission != null );
		assertTrue( permission.getPathMember().getName().equals("user2") );	
		//Case4 ��M���s�b��
		permission = this.repositoryPath.findPermission(group2);
		assertTrue( permission == null );
	
	}

}
