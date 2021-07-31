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
	 * ����Ū���ɮ׫���o�s�զC��
	 * 
	 * Case1 Ū�����`�ɮ�
	 * Case2 Ū���S���s�ռ��Y���ɮ�[groups]
	 * Case3 Ū���L�s�ո���ɮ�
	 */
	public void testGetGroupList1(){
		//Case1 Ū�����`�ɮ�
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
		//Case2 Ū���S���s�ռ��Y���ɮ�[groups]
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_noGroupHeader.txt"));
		this.authorizationEntity.load();
		String []groupList = this.authorizationEntity.getGroupList();
		groupList = this.authorizationEntity.getGroupList();
		assertTrue( groupList.length == 0 );
	}
	
	public void testGetGroupList3(){
		//Case3 Ū���L����ɮ�
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_noGroup.txt"));
		this.authorizationEntity.load();
		String []groupList = this.authorizationEntity.getGroupList();
		groupList = this.authorizationEntity.getGroupList();
		assertTrue( groupList.length == 0 );
	}
	
	/**
	 * ����Ū���ɮ׫���o�s�զ����C��
	 * 
	 * Case1 Ū�����`�ɮ�
	 */
	public void testGetGroupMemberList(){
		//Case1 Ū�����`�ɮ�
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad.txt"));
		this.authorizationEntity.load();
		String []groupList = this.authorizationEntity.getGroupList();
		//���խӼ�
		assertTrue( groupList.length == 4 );
		//���ոs�զW��
		assertTrue( groupList[0].equals("admin") );
		assertTrue( groupList[1].equals("ST_team1") );
		assertTrue( groupList[2].equals("ST_team2") );
		assertTrue( groupList[3].equals("ST_team3") );
		//���ոs�զ����O�_���T
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
	 * ����Ū���ɮ׫���o���|�C��
	 * 
	 * Case1 Ū�����`�ɮ�
	 */
	public void testGetRepositoryPath(){
		//Case 1 Ū�����`�ɮ�
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_permission.txt"));
		//Ū���v�����
		this.authorizationEntity.loadPermissions();
		//���o���|�C��
		String[] rpList = this.authorizationEntity.getRepositoryPath();
		assertTrue( rpList != null );
		//���ո��|�ƬO�_���T
		assertTrue( rpList.length == 14 );
		//���ը��o���W�� ���ղĤ@��,�ĤK��,�P�̫�@��
		assertTrue( rpList[0].equals("/") ); //�Ĥ@��
		assertTrue( rpList[7].equals("/PSP/nickhades") ); //�ĤK��
		assertTrue( rpList[13].equals("/OOAD/VSTS") ); //�ĤQ�|��
	}
	
	/**
	 * ����Ū���ɮ׫���o�Y���|�U�������C��
	 * 
	 * Case1 Ū�����`�ɮץB�s�b�����|
	 * Case2 Ū�����`�ɮץB���s�b�����|
	 */
	public void testGetFileMemberList(){
		//Case1 Ū�����`�ɮץB�s�b�����|
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_permission.txt"));
		//Ū���v�����
		this.authorizationEntity.loadPermissions();
		String [] members;
		//���ո��| "/"
		members = authorizationEntity.getFileMemberList("/");
		assertTrue(members[0].equals("*"));
		assertTrue(members[1].equals("@admin"));
		//���ո��| "/OOAD/VMMS"
		members = authorizationEntity.getFileMemberList("/OOAD/VMMS");
		assertTrue(members[0].equals("*"));
		assertTrue(members[1].equals("frankcheng"));
		assertTrue(members[2].equals("lenwind"));
		//���ո��| "/OOAD/VSTS"
		members = authorizationEntity.getFileMemberList("/OOAD/VSTS");
		assertTrue(members[0].equals("*"));
		assertTrue(members[1].equals("bluewind"));
		assertTrue(members[2].equals("ccchang"));	
		
		//Case2 Ū�����`�ɮץB���s�b�����|
		members = authorizationEntity.getFileMemberList("/OOAD/VSTS/NO");
		assertTrue(members.length == 0);
	}

	/**
	 * ���ը��oPermission���v����� 
	 * 
	 * Case1 Ū�����`�ɮץBŪ���s�b�����|�P�ϥΪ�
	 * Case2 Ū�����`�ɮץB���s�b�����|�P�s�b�ϥΪ�
	 * Case3 Ū�����`�ɮץB�s�b�����|�P���s�b�ϥΪ�
	 */
	public void testGetPermissionsData(){
		//Case1 Ū�����`�ɮץB�s�b�����|
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/testLoad_permission.txt"));
		//Ū���v�����
		this.authorizationEntity.loadPermissions();
		//����Ū���X�Ӫ��v���O�_���T
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
		
		//Case2 Ū�����`�ɮץB���s�b�����|�P�s�b�ϥΪ�
		permission = this.authorizationEntity.getPermissionsData("/NO","@admin");
		assertTrue(!permission.equals("rw"));
		assertTrue(!permission.equals("r"));
		assertTrue(!permission.equals("w"));
		assertTrue(permission.equals(""));
		
		//Case3 Ū�����`�ɮץB�s�b�����|�P���s�b�ϥΪ�
		permission = this.authorizationEntity.getPermissionsData("/OOAD/VMMS","@admin");
		assertTrue(!permission.equals("rw"));
		assertTrue(!permission.equals("r"));
		assertTrue(!permission.equals("w"));
		assertTrue(permission.equals(""));
	}
	
	/**
	 * ���խn�s�J���s�զC��O�_���T
	 */
	public void testSetGroupList(){
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/AuthFileTest/authz_empty"));
		//�s���s�զ���
		List<String> setGroupList = new ArrayList<String>();
		setGroupList.add("group1");
		setGroupList.add("group2");
		setGroupList.add("group3");
		setGroupList.add("group4");
		//�]�m�s�զ���
		this.authorizationEntity.setGroupList(setGroupList);
		//���o�s�զ���
		String [] getGroupList = this.authorizationEntity.getGroupList();
		assertTrue( getGroupList.length == 4 );
		//���ոs�զW�٬O�_���T
		assertTrue( getGroupList[0] , getGroupList[0].equals("group1") );
		assertTrue( getGroupList[1] , getGroupList[1].equals("group2") );
		assertTrue( getGroupList[2] , getGroupList[2].equals("group3") );
		assertTrue( getGroupList[3] , getGroupList[3].equals("group4") );
	}
	
	/**
	 * ���ճ]�m�s�զ���
	 * 
	 * Case1 �L�s�զ������s��
	 * Case2 �s�զ����]�t�s��
	 * Case3 ���s�b���s�զ���
	 */
	public void testSetGroupMember(){
		this.authorizationEntity = new AuthorizationEntity(new File("WebContent/TestFile/AuthFileTest/authz_save"));
		//�s���s�զ���
		List<String> setGroupList = new ArrayList<String>();
		setGroupList.add("group1");
		setGroupList.add("group2");
		setGroupList.add("group3");
		//�]�m�s�զ���
		this.authorizationEntity.setGroupList(setGroupList);
		//���o�s�զ���
		String [] getGroupList = this.authorizationEntity.getGroupList();
		assertTrue( getGroupList.length == 3 );
		//���ոs�զW�٬O�_���T
		assertTrue( getGroupList[0] , getGroupList[0].equals("group1") );
		assertTrue( getGroupList[1] , getGroupList[1].equals("group2") );
		assertTrue( getGroupList[2] , getGroupList[2].equals("group3") );
		
		List<String> setMemberList = null;
		
		//Case1 �L�s�զ������s��
		//group1 �S���s�զ���
		String [] getMemberList = this.authorizationEntity.getGroupMemberList("group1");
		assertTrue( getMemberList == null );
		
		//Case2 �s�զ����]�t�s��
		//group2 �s�զ����]�t��group3
		setMemberList = new ArrayList<String>();
		setMemberList.add("@group3");
		setMemberList.add("user1");
		setMemberList.add("user2");
		this.authorizationEntity.setGroupMember("group2", setMemberList);
		
		getMemberList = this.authorizationEntity.getGroupMemberList("group2");
		assertTrue( getMemberList != null );
		assertTrue( getMemberList.length == 3 );
		//���ոs�զ����W�٬O�_���T
		assertTrue( getMemberList[0] , getMemberList[0].equals("@group3") );
		assertTrue( getMemberList[1] , getMemberList[1].equals("user1") );
		assertTrue( getMemberList[2] , getMemberList[2].equals("user2") );
		
		//Case3 ���s�b���s�զ���
		this.authorizationEntity.setGroupMember("group4", setMemberList);
		getMemberList = this.authorizationEntity.getGroupMemberList("group4");
		assertTrue( getMemberList == null );
	}
	
	/**
	 * ���ճ]�m�v����T
	 * 
	 * Case1 �]�m�s��(r)�P*(r)
	 * Case2 �]�m�@�ӨϥΪ�(r)�P�s��(r)
	 * Case3 �]�m�@�ӨϥΪ�()�P�s��(rw)
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
	 * �����x�s�ɮ�
	 * 
	 * Case1 ���է����S�]�m����x�s
	 */
	public void testSave1(){
		this.authorizationEntity = new AuthorizationEntity(this.tmpFile);
		//�]�m�Ÿ��
		this.authorizationEntity.setGroupList(null);
		this.authorizationEntity.setGroupMember(null, null);
		this.authorizationEntity.setPermissionData(null, null, null);
		this.authorizationEntity.save();
		
		//���ը��o���G���e
		this.authorizationEntity = null;
		this.authorizationEntity = new AuthorizationEntity(this.tmpFile);
		//�s�զC��������
		String []groupList = this.authorizationEntity.getGroupList();
		if( groupList == null ){
			assertTrue( true );
		} else {
			assertTrue(""+ groupList.length, groupList.length == 0 );
		}
		//���|�C��������
		String []pathList = this.authorizationEntity.getRepositoryPath();
		if( pathList == null ){
			assertTrue( true );
		} else {
			assertTrue(""+ pathList.length, pathList.length == 0 );
		}
	}
	
}

