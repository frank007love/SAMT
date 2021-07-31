package ntut.csie.samt.group;

import java.util.ArrayList;
import java.util.List;;

public class Group extends IGroup {

	private List<IGroup> groupMember;
	
	/**
	 * 建構元
	 * 初始化群組的type為group
	 */
	public Group(){
		this.type = GroupMemberType.group;
		groupMember = new ArrayList<IGroup>();
	}
	
	/**
	 * 新增群組成員
	 * 
	 * @param igroup 所要新增的群組成員
	 */
	public boolean addGroupMember(IGroup igroup){
		if( this.findGroupMember(igroup) == null ){
			this.groupMember.add(igroup);
			return true;
		}
		return false;
	}
	
	/**
	 * 刪除群組成員, 藉由傳入IGroup型態, 然後
	 * 取得名稱與型態, 再藉由deleteGroupMember(String name,
	 *  GroupMemberType type)去刪除
	 * 
	 * @param igroup 
	 */
	public void deleteGroupMember(IGroup igroup){
		String name = igroup.getName();
	
		deleteGroupMember(name, igroup.getType());	
	}
	
	/**
	 * 刪除群組成員, 藉由名稱與成員型態
	 * 
	 * @param name 成員名稱
	 * @param type 成員型態
	 */
	public boolean deleteGroupMember(String name, String type){

		for( int i = 0 ; i < this.groupMember.size() ; i++ ){
			
			IGroup groupMember = this.groupMember.get(i);
			
			//刪除與群組成員名稱與型態相同的
			if( groupMember.getName().equals(name) && groupMember.getType().equals(type) ){
				this.groupMember.remove(groupMember);
				
				return true;
			}
		}
		
		return false;
	}	
	
	/**
	 * 取得所有群組成員
	 */
	public List<IGroup> getGroupMembers(){
		return this.groupMember;
	}
	
	/**
	 * 取得群組成員的大小
	 */
	public int getGroupMemberSize(){
		return this.groupMember.size();
	}
	
	/**
	 * 
	 * 查詢群組成員藉由IGroup, 先取得名稱與型態,
	 * 再利用findGroupMember(String name,
	 * 	 GroupMemberType type)去找尋
	 * 
	 * @param igroup 要查詢的群組成員
	 * 
	 * @return 有找尋到就回傳找到的群組成員,
	 * 		沒找到就回傳null
	 */
	public IGroup findGroupMember(IGroup igroup){
		//取得要查詢之名稱與型態
		String name = igroup.getName();
		return findGroupMember(name, igroup.type);
	}
	
	/**
	 * 查詢群組成員藉由名稱與型態
	 * 
	 * @param name 要查詢的群組成員名稱
	 * @param type 要查詢的群組成員型態
	 * 
	 * @return 有找尋到就回傳找到的群組成員,
	 * 		沒找到就回傳null
	 */
	public IGroup findGroupMember(String name, GroupMemberType type){
		
		//查詢群組成員是否有與要查詢的名稱與型態相同
		for(int i = 0; i < this.groupMember.size() ; i++){
			
			IGroup groupMember = this.groupMember.get(i);
			
			//回傳組成員名稱與型態相同的
			if( groupMember.getName().equals(name) && groupMember.type == type ){
				return groupMember;
			}
			
		}
		//沒找到
		return null;
	}
	
}
