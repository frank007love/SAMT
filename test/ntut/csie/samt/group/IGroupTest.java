package ntut.csie.samt.group;

import junit.framework.TestCase;
import java.util.*;

import ntut.csie.samt.group.*;

public class IGroupTest extends TestCase {

	private IGroup igroup;
	
	protected void setUp() throws Exception {
		igroup = new Group();
		igroup.setName("root");
	}

	protected void tearDown() throws Exception {
	}
	
	/**
	 * ���շs�W�s�զ���
	 */
	public void testAddGroupMember(){
		
		//�ŧi�s�զ���
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//�s�W�s�զ���
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//���o�s�թҦ�����
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//���ե[�J���s�զ����W�٬O�_���T
		assertEquals("group1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("user1", ((IGroup)groupMember.get(1)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(2)).getName());
		assertEquals("user2", ((IGroup)groupMember.get(3)).getName());
	}
	
	/**
	 * ���էR���s�զ���
	 * 
	 * Case1 �R���s�զ����ǥ�IGroup
	 */
	public void testDeleteGroupMember1(){
		//�ŧi�s�զ���
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//�s�W�s�զ���
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//Case1  �R���s�զ����ǥ�IGroup
		
		//�R���s�զ��� "group1" by IGroup
		igroup.deleteGroupMember(group1);
		
		//���ոs�զ����ƶq�O�_��3
		assertTrue(igroup.getGroupMemberSize() == 3 );
		
		//���o�s�թҦ�����
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//���ղĤ@�Ӹs�զ����O�_�w�Q�R��
		assertFalse( ((IGroup)groupMember.get(0)).getName().equals("group1") );
		
		//���թҳѤU���s�զW�٬O�_���T
		assertEquals("user1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(1)).getName());
		assertEquals("user2", ((IGroup)groupMember.get(2)).getName());
	}

	/**
	 * ���էR���s�զ���
	 * 
	 * Case2 �R���s�զ����ǥѦW�ٻP���A
	 */
	public void testDeleteGroupMember2(){
		//�ŧi�s�զ���
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//�s�W�s�զ���
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//Case2 �R���s�զ����ǥѦW�ٻP���A
		
		//�R���s�զ��� "user2" by name "user2" and type "user"
		igroup.deleteGroupMember("user2", "account");
		
		//���ոs�զ����ƶq�O�_��2
		assertTrue(igroup.getGroupMemberSize() == 3 );
		
		//���o�s�թҦ�����
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//���թҳѤU���s�զW�٬O�_���T
		assertEquals("group1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("user1", ((IGroup)groupMember.get(1)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(2)).getName());
	}
	 
	/**
	 * ���էR���s�զ���
	 * 
	 * Case3 �R���W�٦s�b���O���A���~���s�զ���,by name and type
	 */
	public void testDeleteGroupMember3(){
		//�ŧi�s�զ���
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//�s�W�s�զ���
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//Case3 �R���W�٦s�b���O���A���~���s�զ���,by name and type
		
		//�R���s�զ��� "user1" by name "user1" and type "group"
		igroup.deleteGroupMember("user1", "group");
		
		//���ոs�զ����ƶq�O�_��2
		assertTrue(igroup.getGroupMemberSize() == 4 );
		
		//���o�s�թҦ�����
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//���թҳѤU���s�զW�٬O�_���T
		assertEquals("group1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("user1", ((IGroup)groupMember.get(1)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(2)).getName());
		assertEquals("user2", ((IGroup)groupMember.get(3)).getName());
	}
	
	/**
	 * ���էR���s�զ���
	 * 
	 * Case4  �R���W�٤��s�b���O���A���T���s�զ���,by IGroup
	 */
	public void testDeleteGroupMember4(){
		//�ŧi�s�զ���
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//�s�W�s�զ���
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//Case4  �R���W�٤��s�b���O���A���T���s�զ���,by IGroup
		IGroup group3 = new Group();
		group3.setName("group3");
		
		//�R���s�զ���
		igroup.deleteGroupMember(group3);
		
		//���ոs�զ����ƶq�O�_��2
		assertTrue(igroup.getGroupMemberSize() == 4 );
		
		//���o�s�թҦ�����
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//���թҳѤU���s�զW�٬O�_���T
		assertEquals("group1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("user1", ((IGroup)groupMember.get(1)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(2)).getName());
		assertEquals("user2", ((IGroup)groupMember.get(3)).getName());
	}
	
	/**
	 * ���մM��s�զ���
	 * 
	 * Case1 ��M�Ĥ@��, ���^�ǧ�쪺����
	 * Case2 ��M������, ���^�ǧ�쪺����
	 * Case3 ��M�̫�@��, ���^�ǧ�쪺����
	 * Case4 ��M���b�̭���, ���^��null
	 */
	public void testFindGroupMember1(){
		//�ŧi�s�զ���
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//�s�W�s�զ���
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//���ոs�զ����j�p�O�_��4
		assertTrue( igroup.getGroupMemberSize() == 4 );
		
		//Case1 ��M�Ĥ@��, ���^�ǧ�쪺����
		assertTrue( igroup.findGroupMember(group1) == group1 );
		assertTrue( igroup.findGroupMember("group1",GroupMemberType.group) == group1 );
		
		//Case2 ��M������, ���^�ǧ�쪺����
		assertTrue( igroup.findGroupMember(group2) == group2 );
		assertTrue( igroup.findGroupMember("group2",GroupMemberType.group) == group2 );
		
		//Case3 ��M�̫�@��, ���^�ǧ�쪺����
		assertTrue( igroup.findGroupMember(user2) == user2 );
		assertTrue( igroup.findGroupMember("user2",GroupMemberType.account) == user2 );
		
		//Case4 ��M���b�̭���, ���^��null
		IGroup user3 = new User();
		user3.setName("user3");
		assertTrue( igroup.findGroupMember(user3) == null );		
		assertTrue( igroup.findGroupMember("user3",GroupMemberType.account) == null );	
		
	}
	
}
