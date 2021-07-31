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
		
		//�R�����ռȦs��
		if( target.exists() ){ 
			target.delete();
		}
	}

	/**
	 * ���շs�W�s��, ���ը��o�r��s�զC��
	 * 
	 * Case1 �[�J���s�b���s��
	 */
	public void testAddGroup1(){
		//Case 1 �[�J���s�b���s��
		//���լO�_���[�J���\
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		List<String> groupList = authorizationCenter.getStringGroupList();
		
		//�ݨ��o���C��ƥجO�_���T
		assertTrue( groupList.size() == 3 );
		
		//�ݩҥ[�J���s�զW�٬O�_���T
		assertTrue( groupList.contains("group1") );
		assertTrue( groupList.contains("group2") );
		assertTrue( groupList.contains("group3") );
	}
	
	/**
	 * ���շs�W�s��, ���ը��o�r��s�զC��
	 * 
	 * Case2 �[�J�w�s�b���s��
	 */
	public void testAddGroup2(){
		//Case 2 �[�J�w�s�b���s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		//���s�[�Jgroup1
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		List<String> groupList = authorizationCenter.getStringGroupList();
		
		//�ݩҥ[�J���s�զW�٬O�_���T
		assertTrue( groupList.contains("group1") );
		assertTrue( groupList.contains("group2") );
		assertTrue( groupList.contains("group3") );
	}
	
	/**
	 * ���շs�W�s��, ���ը��o�r��s�զC��
	 * 
	 * Case3 �[�J�W�٬�""���s��
	 */
	public void testAddGroup3(){
		//Case 3 �[�J�W�٬�""���s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		//�[�Jgroup ""
		authorizationCenter.addGroup("");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		List<String> groupList = authorizationCenter.getStringGroupList();
		//�ݩҥ[�J���s�զW�٬O�_���T
		assertTrue( groupList.contains("group1") );
		assertTrue( groupList.contains("group2") );
		assertTrue( groupList.contains("group3") );
		assertTrue( !groupList.contains("") );
	}
	
	/**
	 * ���խק�s�զW��, �]�t�s�զC��, �b�䥦�s�զ������W��,
	 * �bŪ���v���ҳ]�w���W��
	 * 
	 * Case1 �ק�s�b�s�զ����s�b���s�զW��
	 */
	public void testModifyGroupName1(){
		//�[�J�s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//�[�J�s�զ���
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//�Ngroup1�Mgroup3�[�Jgroup2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//�Ngroup1�Mgroup2�[�Jgroup3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);		
		
		//Case 1 �ק�s�b�s�զ����s�b���s�զW��
		assertTrue( authorizationCenter.modifyGroupName("group1", "group4") );
		
		//���ոs�զC��O�_�ק令�\
		List<String> groupList_String = authorizationCenter.getStringGroupList();
		
		//�ݨ��o���C��ƥجO�_���T
		assertTrue( groupList_String.size() == 3 );
		
		//�ݩҥ[�J���s�զW�٬O�_���T
		assertFalse( groupList_String.contains("group1") );
		assertTrue( groupList_String.contains("group2") );
		assertTrue( groupList_String.contains("group3") );
		assertTrue( groupList_String.contains("group4") );
		
		//���ոs�զ����C��O�_�ק令�\
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//���ե[�Jgroup2�ק蠟�᪺�W��, group1�����s�b, group4���s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group1", 
				GroupMemberType.group) == null );
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group4", 
				GroupMemberType.group) != null );
		//���ե[�Jgroup3�ק蠟�᪺�W��, group1�����s�b, group4���s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group1", 
				GroupMemberType.group) == null );
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group4", 
				GroupMemberType.group) != null );
	}
	
	/**
	 * ���խק�s�զW��, �]�t�s�զC��, �b�䥦�s�զ������W��,
	 * �bŪ���v���ҳ]�w���W��
	 * 
	 * Case2 �ק藍�s�b���s��
	 */
	public void testModifyGroupName2(){
		//�[�J�s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//�[�J�s�զ���
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//�Ngroup1�Mgroup3�[�Jgroup2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//�Ngroup1�Mgroup2�[�Jgroup3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case 2 �ק藍�s�b���s��
		assertFalse( authorizationCenter.modifyGroupName("group4", "group5") );
		
		List<String> groupList_String = authorizationCenter.getStringGroupList();
		
		//�ݨ��o���C��ƥجO�_���T
		assertTrue( groupList_String.size() == 3 );
		
		//�ݩҥ[�J���s�զW�٬O�_���T
		assertTrue( groupList_String.contains("group1") );
		assertTrue( groupList_String.contains("group2") );
		assertTrue( groupList_String.contains("group3") );
		assertFalse( groupList_String.contains("group4") );
		assertFalse( groupList_String.contains("group5") );
		
		//���ոs�զ����C��O�_�ק令�\
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//���ե[�Jgroup2�ק蠟�᪺�W��, group1���s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group1", 
				GroupMemberType.group) != null );
		//���ե[�Jgroup3�ק蠟�᪺�W��, group1�����s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group1", 
				GroupMemberType.group) != null );
	}
	
	/**
	 * ���խק�s�զW��, �]�t�s�զC��, �b�䥦�s�զ������W��,
	 * �bŪ���v���ҳ]�w���W��
     *
	 * Case3 �ק�s�b�s�զ��s�b�s�զW��
	 */
	public void testModifyGroupName3(){
		//�[�J�s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//�[�J�s�զ���
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//�Ngroup1�Mgroup3�[�Jgroup2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//�Ngroup1�Mgroup2�[�Jgroup3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case 3 �ק�s�b�s�զ��s�b�s�զW��
		assertFalse( authorizationCenter.modifyGroupName("group2", "group3") );
		
		List<String> groupList_String = authorizationCenter.getStringGroupList();
		
		//�ݨ��o���C��ƥجO�_���T
		assertTrue( groupList_String.size() == 3 );
		
		//�ݩҥ[�J���s�զW�٬O�_���T
		assertTrue( groupList_String.contains("group2") );
		assertTrue( groupList_String.contains("group3") );
		assertTrue( groupList_String.contains("group1") );
		
		//���ոs�զ����C��O�_�ק令�\
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//���ե[�Jgroup2�ק蠟�᪺�W��, group1���s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group1", 
				GroupMemberType.group) != null );
		//���ե[�Jgroup3�ק蠟�᪺�W��, group1���s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group1", 
				GroupMemberType.group) != null );
	}
	
	/**
	 * ���էR���s��, �]�t�s�զC��, �b�䥦�s�զ������W��,
	 * �bŪ���v���ҳ]�w���W��
	 * 
	 * Case1 �R���Ĥ@�Ӹs��
	 */
	public void testDeleteGroup1(){
		//�[�J�s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//�[�J�s�զ���
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//�Ngroup1�Mgroup3�[�Jgroup2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//�Ngroup1�Mgroup2�[�Jgroup3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case1 �R���Ĥ@�Ӹs��
		authorizationCenter.deleteGroup("group1");
		
		//�R������j�p����2
		assertTrue( authorizationCenter.getGroupSize() == 2 );
		
		//�ҧ�쪺group���٦s�b
		assertTrue( authorizationCenter.findGroup("group2") != null );
		assertTrue( authorizationCenter.findGroup("group3") != null );
		//�ҧ�쪺group����null
		assertTrue( authorizationCenter.findGroup("group1") == null );
		
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//�bgroup2���s�զ�����, group1�����s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group1", 
				GroupMemberType.group) == null );
		//�bgroup3���s�զ�����, group1�����s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group1", 
				GroupMemberType.group) == null );
	}
	
	/**
	 * ���էR���s��, �]�t�s�զC��, �b�䥦�s�զ������W��,
	 * �bŪ���v���ҳ]�w���W��
	 * 
	 * Case2 �R���̫�@�Ӹs��
	 */
	public void testDeleteGroup2(){
		//�[�J�s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//�[�J�s�զ���
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//�Ngroup1�Mgroup3�[�Jgroup2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//�Ngroup1�Mgroup2�[�Jgroup3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case2 �R���̫�@�Ӹs��
		authorizationCenter.deleteGroup("group3");
		
		//�R������j�p����2
		assertTrue( authorizationCenter.getGroupSize() == 2 );
		
		//�ҧ�쪺group���٦s�b
		assertTrue( authorizationCenter.findGroup("group1") != null );
		assertTrue( authorizationCenter.findGroup("group2") != null );
		//�ҧ�쪺group����null
		assertTrue( authorizationCenter.findGroup("group3") == null );
		
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//�bgroup1���s�զ�����, group3�����s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group1)).findGroupMember("group3", 
				GroupMemberType.group) == null );
		//�bgroup2���s�զ�����, group3�����s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group2)).findGroupMember("group3", 
				GroupMemberType.group) == null );
	}
	
	/**
	 * ���էR���s��, �]�t�s�զC��, �b�䥦�s�զ������W��,
	 * �bŪ���v���ҳ]�w���W��
	 * 
	 * Case3 �R���������s��
	 */
	public void testDeleteGroup3(){
		//�[�J�s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//�[�J�s�զ���
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//�Ngroup1�Mgroup3�[�Jgroup2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//�Ngroup1�Mgroup2�[�Jgroup3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case3 �R���������s��
		authorizationCenter.deleteGroup("group2");
		
		//�R������j�p����2
		assertTrue( authorizationCenter.getGroupSize() == 2 );
		
		//�ҧ�쪺group���٦s�b
		assertTrue( authorizationCenter.findGroup("group1") != null );
		assertTrue( authorizationCenter.findGroup("group3") != null );
		//�ҧ�쪺group����null
		assertTrue( authorizationCenter.findGroup("group2") == null );
		
		List<IGroup> groupList_IGroup = authorizationCenter.getGroupList();
		
		//�bgroup1���s�զ�����, group2�����s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group1)).findGroupMember("group2", 
				GroupMemberType.group) == null );
		//�bgroup3���s�զ�����, group2�����s�b
		assertTrue(groupList_IGroup.get(groupList_IGroup.indexOf(group3)).findGroupMember("group2", 
				GroupMemberType.group) == null );
		
	}
	
	/**
	 * ���էR���s��, �]�t�s�զC��, �b�䥦�s�զ������W��,
	 * �bŪ���v���ҳ]�w���W��
	 * 
	 * Case4 �R�����s�b���s��
	 */
	public void testDeleteGroup4(){
		//�[�J�s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//�[�J�s�զ���
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//�Ngroup1�Mgroup3�[�Jgroup2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//�Ngroup1�Mgroup2�[�Jgroup3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case4 �R�����s�b���s��
		authorizationCenter.deleteGroup("group4");
		
		//�R������j�p����3
		assertTrue( authorizationCenter.getGroupSize() == 3 );
		
		//�ҧ�쪺group���٦s�b
		assertTrue( authorizationCenter.findGroup("group1") != null );
		assertTrue( authorizationCenter.findGroup("group2") != null );
		assertTrue( authorizationCenter.findGroup("group3") != null );

	}
	
	/**
	 * ���էR���s��, �]�t�s�զC��, �b�䥦�s�զ������W��,
	 * �bŪ���v���ҳ]�w���W��
	 * 
	 * Case5 �R���Ҧ��s��
	 */
	public void testDeleteGroup5(){
		//�[�J�s��
		authorizationCenter.addGroup("group1");
		assertTrue(authorizationCenter.getGroupSize() == 1);
		
		authorizationCenter.addGroup("group2");
		assertTrue(authorizationCenter.getGroupSize() == 2);
		
		authorizationCenter.addGroup("group3");
		assertTrue(authorizationCenter.getGroupSize() == 3);
		
		//�[�J�s�զ���
		IGroup group1 = authorizationCenter.findGroup("group1");
		IGroup group2 = authorizationCenter.findGroup("group2");
		IGroup group3 = authorizationCenter.findGroup("group3");
		
		//�Ngroup1�Mgroup3�[�Jgroup2
		group2.addGroupMember(group1);
		group2.addGroupMember(group3);
		//�Ngroup1�Mgroup2�[�Jgroup3
		group3.addGroupMember(group1);
		group3.addGroupMember(group2);	
		
		//Case5 �R���Ҧ��s��
		authorizationCenter.deleteGroup("group1");
		authorizationCenter.deleteGroup("group2");
		authorizationCenter.deleteGroup("group3");

		//�R������j�p����0
		assertTrue( authorizationCenter.getGroupSize() == 0 );
		
		//�s���������s�b
		assertTrue( authorizationCenter.findGroup("group1") == null );
		assertTrue( authorizationCenter.findGroup("group2") == null );
		assertTrue( authorizationCenter.findGroup("group3") == null );		
	}
	
	/**
	 * ����Ū���v���{���ɮפ��᪺�s�ջP�����W�ٻP
	 * �v���O�_���T
	 * *�s�զW��
	 * Case1 Ū�����s�b���ɮ�
	 */
	public void testLoad1(){
		List<IGroup> groupList = null;
		
		//Case1 Ū�����s�b���ɮ�
		this.authorizationCenter.load("WebContent/TestFile/Nofile");
		groupList = this.authorizationCenter.getGroupList();
		//���S���s�ո��
		assertTrue( groupList == null);
	}
	
	/**
	 * ����Ū���v���{���ɮפ��᪺�s�ջP�����W�ٻP
	 * �v���O�_���T
	 * *�s�զW��
	 * Case2 Ū���S���s�ժ��ɮ�
	 */
	public void testLoad2(){
		List<IGroup> groupList = null;
		//Case2 Ū���S���s�ռ��Y���ɮ�[groups]
		this.authorizationCenter.load("WebContent/TestFile/testLoad_noGroupHeader.txt");
		groupList = this.authorizationCenter.getGroupList();
		//���S���s�ո��
		assertTrue( groupList == null);
	}

	/**
	 * ����Ū���v���{���ɮפ��᪺�s�ջP�����W�ٻP
	 * �v���O�_���T
	 * *�s�զW��
	 * Case3 ���`Ū��
	 */
	public void testLoad3(){
		List<IGroup> groupList = null;
		//Case3 ���`Ū��
		this.authorizationCenter.load("WebContent/TestFile/testLoad.txt");
		groupList = this.authorizationCenter.getGroupList();
		//���ոs�զC��
		assertTrue( groupList != null);
		//���խӼ�
		assertTrue( groupList.size() == 4);
		
		IGroup group1 = groupList.get(0);
		IGroup group2 = groupList.get(1);
		IGroup group3 = groupList.get(2);
		IGroup group4 = groupList.get(3);
		//���թ�Ū�����s�զW�٬O�_���T
		assertTrue( group1.getName().equals("ST_team3") );
		assertTrue( group2.getName().equals("admin") );
		assertTrue( group3.getName().equals("ST_team1") );
		assertTrue( group4.getName().equals("ST_team2") );
		
		//���ոs�զ����C��
		//���խӼ�
		assertTrue( group1.getGroupMemberSize() == 0 );
		assertTrue( group2.getGroupMemberSize() == 2 );
		assertTrue( group3.getGroupMemberSize() == 3 );
		assertTrue( group4.getGroupMemberSize() == 1 );
		//���ոs�զ����W�٬O�_���T
		assertTrue( group1.findGroupMember("ST_team3",GroupMemberType.group) == null );
		
		assertTrue( group2.findGroupMember("franklin",GroupMemberType.account) != null );
		assertTrue( group2.findGroupMember("ST_team1",GroupMemberType.group) != null );
		
		assertTrue( group3.findGroupMember("franklin",GroupMemberType.account) != null );
		assertTrue( group3.findGroupMember("lenwind",GroupMemberType.account) != null );
		assertTrue( group3.findGroupMember("frankcheng",GroupMemberType.account) != null );
		
		assertTrue( group4.findGroupMember("thenewid",GroupMemberType.account) != null );
	}
	
	/**
	 * ����Ū���v���{���ɮפ��᪺�s�ջP�����W�ٻP
	 * �v���O�_���T
	 * *�s�զW��
	 * Case4 Ū���L�s�ո���ɮ�
	 */
	public void testLoad4(){
		List<IGroup> groupList = null;
		//Case4 Ū���L�s�ո���ɮ�
		this.authorizationCenter.load("WebContent/TestFile/testLoad_noGroup.txt");
		groupList = this.authorizationCenter.getGroupList();
		//���S���s�ո��
		assertTrue( groupList == null);
	}
	
	/**
	 * �����x�s�v���{���ɮפ��᪺�s�ջP�����W�٬O�_���T
	 * �i��@�Ǿާ@,�x�s����A���sŪ��,
	 * ���Ҥ��e�O�_���T
	 * 
	 * Case1 �w�s�b���e���v���{���ɮ�
	 */
	public void testSave1(){

		File source = new File("WebContent/TestFile/AuthFileTEst/authz");
		File target = new File("WebContent/TestFile/AuthFileTEst/tmp2");
		FileCopy.copy(source, target);
		
		this.authorizationCenter.load(target.getPath());
		//�i��@�Ǿާ@
		
		//�s�W�s��
		this.authorizationCenter.addGroup("newGroup");
		//�s�W����
		this.authorizationCenter.addGroupMember("newGroup", "newUser", "account");
		this.authorizationCenter.addGroupMember("newGroup", "ST_team2", "group");
		//�R���s��
		this.authorizationCenter.deleteGroup("admin");
		//�s�W�v��
		this.authorizationCenter.addPermission("/newpath", "newGroup", "group", "r");
		//�ק��v��
		this.authorizationCenter.modifyPermission("/OOAD/VSTS", "*", "group", "rw");
		//�R���v��
		this.authorizationCenter.deletePermission("/OOAD/CSRM", "thenewid", "account");
		
		this.authorizationCenter.save();
		this.authorizationCenter = null;
		
		this.authorizationCenter = new AuthorizationCenter();
		//���sŪ��
		
		this.authorizationCenter.load(target.getPath());
		
		//���ոs�լO�_���T
		List<String> groupList =  this.authorizationCenter.getStringGroupList();
		assertTrue( groupList != null );
		assertTrue( ""  + groupList.size() , groupList.size() == 4 );
		assertTrue( groupList.indexOf("admin") == -1 );
		assertTrue( groupList.indexOf("ST_team1") != -1 );
		assertTrue( groupList.indexOf("ST_team2") != -1 );
		assertTrue( groupList.indexOf("ST_team3") != -1 );
		assertTrue( groupList.indexOf("newGroup") != -1 );
		
		//���ոs�զ����O�_���T
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
		
		//�����v�������O�_���T
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
	 * �����x�s�v���{���ɮפ��᪺�s�ջP�����W�٬O�_���T
	 * �i��@�Ǿާ@,�x�s����A���sŪ��,
	 * ���Ҥ��e�O�_���T
	 * 
	 * Case2 �R���Ҧ��s�ըåBŪ��
	 */
	public void testSave2(){

		File source = new File("WebContent/TestFile/AuthFileTest/authz");
		File target = new File("WebContent/TestFile/AuthFileTest/tmp2");
		FileCopy.copy(source, target);
		
		this.authorizationCenter.load(target.getPath());
		
		//�R���Ҧ��s��
		this.authorizationCenter.deleteGroup("admin");
		this.authorizationCenter.deleteGroup("ST_team1");
		this.authorizationCenter.deleteGroup("ST_team2");
		this.authorizationCenter.deleteGroup("ST_team3");
		//���ո��|�����]�t�s�զ����x���ӳQ�R��
		List<String> pathMember = this.authorizationCenter.getPathMemberList("/");
		assertTrue( pathMember.size() == 1 );
		assertTrue( !pathMember.contains("@admin") );
		assertTrue( pathMember.contains("@*") );
		
		pathMember = this.authorizationCenter.getPathMemberList("/ST/Team1");
		assertTrue( pathMember.size() == 1 );
		assertTrue( !pathMember.contains("@ST_team1") );
		assertTrue( pathMember.contains("@*") );
		
		this.authorizationCenter.save();
		
		//���sŪ��
		this.authorizationCenter = null;
		this.authorizationCenter = new AuthorizationCenter();
		this.authorizationCenter.load(target.getPath());
		
		List<String> groupList = this.authorizationCenter.getStringGroupList();
		//�^�Ǫ��D�ūhsize����0, �]�����R���F
		if( groupList != null )
			assertTrue( ""+ groupList.size() , groupList.size() == 0 );
		//���ո��|�ƶq�O�_���T	
		List<String> pathList = this.authorizationCenter.getPermissionPathList();
		assertTrue( pathList != null );		
		assertTrue( ""+ pathList.size(), pathList.size() == 14 );
		//���ո��|�����]�t�s�զ����x���ӳQ�R��, ps�bŪ�ɮɴN�ױ��FŪ�����s�b���s��
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
	 * �����x�s�v���{���ɮפ��᪺�s�ջP�����W�٬O�_���T
	 * �i��@�Ǿާ@,�x�s����A���sŪ��,
	 * ���Ҥ��e�O�_���T
	 * 
	 * Case3 �R�����|���Ҧ�����,�S���������|�����s���ɮפ�
	 */
	public void testSave3(){

		File source = new File("WebContent/TestFile/AuthFileTest/authz");
		File target = new File("WebContent/TestFile/AuthFileTest/tmp2");
		FileCopy.copy(source, target);
		
		this.authorizationCenter.load(target.getPath());
		
		//�R���Ҧ��s��
		this.authorizationCenter.deletePermission("/OOAD/VSTS", "bluewind", "account");
		this.authorizationCenter.deletePermission("/OOAD/VSTS", "ccchang", "account");
		this.authorizationCenter.deletePermission("/OOAD/VSTS", "*", "group");
		
		//���ո��|�������ӳQ�R��
		List<String> pathMember = this.authorizationCenter.getPathMemberList("/OOAD/VSTS");
		assertTrue( pathMember.size() == 0 );
		assertTrue( !pathMember.contains("bluewind") );
		assertTrue( !pathMember.contains("ccchang") );
		assertTrue( !pathMember.contains("@*") );

		this.authorizationCenter.save();
		
		//���sŪ��
		this.authorizationCenter = null;
		this.authorizationCenter = new AuthorizationCenter();
		this.authorizationCenter.load(target.getPath());
		
		//���ո��|�ƶq�O�_���T, ���|���Ū����Ӥ��s�b, �Y�R���F/OOAD/VSTS
		List<String> pathList = this.authorizationCenter.getPermissionPathList();
		assertTrue( pathList != null );		
		assertTrue( ""+ pathList.size(), pathList.size() == 13 );
		
		//���ո��|�������ӳQ�R��
		pathMember = this.authorizationCenter.getPathMemberList("/OOAD/VSTS");
		assertTrue( pathMember == null );
	}
	
}


