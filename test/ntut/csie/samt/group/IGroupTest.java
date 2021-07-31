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
	 * 測試新增群組成員
	 */
	public void testAddGroupMember(){
		
		//宣告群組成員
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//新增群組成員
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//取得群組所有成員
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//測試加入的群組成員名稱是否正確
		assertEquals("group1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("user1", ((IGroup)groupMember.get(1)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(2)).getName());
		assertEquals("user2", ((IGroup)groupMember.get(3)).getName());
	}
	
	/**
	 * 測試刪除群組成員
	 * 
	 * Case1 刪除群組成員藉由IGroup
	 */
	public void testDeleteGroupMember1(){
		//宣告群組成員
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//新增群組成員
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//Case1  刪除群組成員藉由IGroup
		
		//刪除群組成員 "group1" by IGroup
		igroup.deleteGroupMember(group1);
		
		//測試群組成員數量是否為3
		assertTrue(igroup.getGroupMemberSize() == 3 );
		
		//取得群組所有成員
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//測試第一個群組成員是否已被刪除
		assertFalse( ((IGroup)groupMember.get(0)).getName().equals("group1") );
		
		//測試所剩下之群組名稱是否正確
		assertEquals("user1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(1)).getName());
		assertEquals("user2", ((IGroup)groupMember.get(2)).getName());
	}

	/**
	 * 測試刪除群組成員
	 * 
	 * Case2 刪除群組成員藉由名稱與型態
	 */
	public void testDeleteGroupMember2(){
		//宣告群組成員
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//新增群組成員
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//Case2 刪除群組成員藉由名稱與型態
		
		//刪除群組成員 "user2" by name "user2" and type "user"
		igroup.deleteGroupMember("user2", "account");
		
		//測試群組成員數量是否為2
		assertTrue(igroup.getGroupMemberSize() == 3 );
		
		//取得群組所有成員
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//測試所剩下之群組名稱是否正確
		assertEquals("group1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("user1", ((IGroup)groupMember.get(1)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(2)).getName());
	}
	 
	/**
	 * 測試刪除群組成員
	 * 
	 * Case3 刪除名稱存在但是型態錯誤的群組成員,by name and type
	 */
	public void testDeleteGroupMember3(){
		//宣告群組成員
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//新增群組成員
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//Case3 刪除名稱存在但是型態錯誤的群組成員,by name and type
		
		//刪除群組成員 "user1" by name "user1" and type "group"
		igroup.deleteGroupMember("user1", "group");
		
		//測試群組成員數量是否為2
		assertTrue(igroup.getGroupMemberSize() == 4 );
		
		//取得群組所有成員
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//測試所剩下之群組名稱是否正確
		assertEquals("group1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("user1", ((IGroup)groupMember.get(1)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(2)).getName());
		assertEquals("user2", ((IGroup)groupMember.get(3)).getName());
	}
	
	/**
	 * 測試刪除群組成員
	 * 
	 * Case4  刪除名稱不存在但是型態正確的群組成員,by IGroup
	 */
	public void testDeleteGroupMember4(){
		//宣告群組成員
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//新增群組成員
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//Case4  刪除名稱不存在但是型態正確的群組成員,by IGroup
		IGroup group3 = new Group();
		group3.setName("group3");
		
		//刪除群組成員
		igroup.deleteGroupMember(group3);
		
		//測試群組成員數量是否為2
		assertTrue(igroup.getGroupMemberSize() == 4 );
		
		//取得群組所有成員
		List<IGroup> groupMember = igroup.getGroupMembers();
		
		//測試所剩下之群組名稱是否正確
		assertEquals("group1", ((IGroup)groupMember.get(0)).getName());
		assertEquals("user1", ((IGroup)groupMember.get(1)).getName());
		assertEquals("group2", ((IGroup)groupMember.get(2)).getName());
		assertEquals("user2", ((IGroup)groupMember.get(3)).getName());
	}
	
	/**
	 * 測試尋找群組成員
	 * 
	 * Case1 找尋第一個, 應回傳找到的物件
	 * Case2 找尋中間的, 應回傳找到的物件
	 * Case3 找尋最後一個, 應回傳找到的物件
	 * Case4 找尋不在裡面的, 應回傳null
	 */
	public void testFindGroupMember1(){
		//宣告群組成員
		IGroup group1 = new Group();
		group1.setName("group1");
		IGroup group2 = new Group();
		group2.setName("group2");
		IGroup user1 = new User();
		user1.setName("user1");
		IGroup user2 = new User();
		user2.setName("user2");
		
		//新增群組成員
		igroup.addGroupMember(group1);
		igroup.addGroupMember(user1);
		igroup.addGroupMember(group2);
		igroup.addGroupMember(user2);
		
		//測試群組成員大小是否為4
		assertTrue( igroup.getGroupMemberSize() == 4 );
		
		//Case1 找尋第一個, 應回傳找到的物件
		assertTrue( igroup.findGroupMember(group1) == group1 );
		assertTrue( igroup.findGroupMember("group1",GroupMemberType.group) == group1 );
		
		//Case2 找尋中間的, 應回傳找到的物件
		assertTrue( igroup.findGroupMember(group2) == group2 );
		assertTrue( igroup.findGroupMember("group2",GroupMemberType.group) == group2 );
		
		//Case3 找尋最後一個, 應回傳找到的物件
		assertTrue( igroup.findGroupMember(user2) == user2 );
		assertTrue( igroup.findGroupMember("user2",GroupMemberType.account) == user2 );
		
		//Case4 找尋不在裡面的, 應回傳null
		IGroup user3 = new User();
		user3.setName("user3");
		assertTrue( igroup.findGroupMember(user3) == null );		
		assertTrue( igroup.findGroupMember("user3",GroupMemberType.account) == null );	
		
	}
	
}
