package ntut.csie.samt.group;

public class User extends IGroup {

	/**
	 * 建構元
	 * 初始化使用者的type為user
	 */
	public User(){
		this.type = GroupMemberType.account;
	}
	
	/**
	 * 取得群組成員數量, 由於這是User
	 * 所以沒有群組成員
	 * 
	 * @return 0
	 */
	public int getGroupMemberSize(){
		return 0;
	}
	
	/**
	 * 找尋群組成員, 有找到就回傳自己, 
	 * 否則回傳null
	 * 
	 * @param igroup 要查詢的群組成員
	 */
	public IGroup findGroupMember(IGroup igroup){
		//取得要查詢的群組成員名稱與型態
		String name = igroup.getName();
		GroupMemberType type = igroup.type;
		
		return findGroupMember(name, type);
	}
	
	/**
	 * 找尋群組成員, 有找到就回傳自己, 
	 * 否則回傳null
	 * 
	 * @param name 要查詢的群組成員名稱
	 * @param type 要查詢的群組成員型態
	 */
	public IGroup findGroupMember(String name, GroupMemberType type){
		//看自己的名稱與型態是否與要查詢的相同, 是的話就回傳自己, 不然就會傳null
		if( this.name.equals(name) && this.type == type ){
			return this;
		}
		
		return null;
	}
	
}
