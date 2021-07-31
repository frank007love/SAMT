package ntut.csie.samt.group;

import java.util.List;

public abstract class IGroup {

	protected String name;
	protected GroupMemberType type;

	public IGroup(){

	}
	
	/**
	 * 設定名稱
	 * 
	 * @param name 所要設定的名稱
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 取得名稱
	 * 
	 * @return 名稱
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 取得群組成員的型態, 群組或使用者
	 * 
	 * @return GroupMemberType.group or 
	 * 	GroupMemberType.user
	 */
	public String getType(){
		return this.typeTanslateToString(this.type);
	}
	
	/**
	 * 新增群組成員
	 * 
	 * @param igroup 所要新增的群組成員
	 */
	public boolean addGroupMember(IGroup igroup){
		return false;
	}
	
	/**
	 * 根據名稱與型態,
	 * 比較兩個igroup是否相同
	 * 
	 * @return 相同回傳true, 反之則false
	 */
	public boolean isEqual(IGroup igroup){
		if( igroup != null && igroup.name.equals(this.name) && igroup.type.equals(this.type) ){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 刪除群組成員
	 * 
	 * @param igroup 所要刪除的群組成員
	 */
	public void deleteGroupMember(IGroup igroup){
		
	}
	
	/**
	 * 刪除群組成員, 藉由名稱與型態
	 * 
	 * @param name 要刪除成員之名稱
	 * @param type 要刪除成員之型態
	 */
	public boolean deleteGroupMember(String name, String type){
		return false;
	}
	
	/**
	 * 取得所有群組成員
	 * 
	 * @return null
	 */
	public List<IGroup> getGroupMembers(){
		return null;
	}
	
	/**
	 * 取得群組成員的數量
	 * 
	 * @return
	 */
	abstract public int getGroupMemberSize();
	
	/**
	 * 找尋群組成員
	 * 
	 * @param igroup 要找尋的群組成員
	 * @return 所找到的群組成員, 沒找到則回傳null
	 */
	abstract public IGroup findGroupMember(IGroup igroup);
	
	/**
	 * 找尋群組成員, 藉由名稱與型態
	 * 
	 * @param name 要找尋的名稱
	 * @param type 要找尋的型態
	 * @return 所找到的群組成員, 沒找到則回傳null
	 */
	abstract public IGroup findGroupMember(String name, GroupMemberType type);
	
	private String typeTanslateToString(GroupMemberType type){
		String retureType = "";
		
		if( type == GroupMemberType.account ){
			retureType = "account";
		} else if( type == GroupMemberType.group ){
			retureType = "group";
		}
		return retureType;
	}
	
}
