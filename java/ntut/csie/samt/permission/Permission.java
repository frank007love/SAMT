package ntut.csie.samt.permission;

import ntut.csie.samt.group.*;

public class Permission {

	private PermissionType type;
	private IGroup pathmember;

	/**
	 * 建構元
	 * 將成員初始存取權限設為沒有
	 * 
	 * @param pathmember 路徑底下的成員
	 */
	public Permission(IGroup pathmember){
		this.type = PermissionType.none;
		this.pathmember = pathmember;
	}
	
	/**
	 * 設定成員存取權限
	 * 
	 * @param type 所要設定的權限
	 */
	public void setType(String type){
		this.type = this.typeTranslateToPermissionType(type);
	}
	
	/**
	 * 取得成員存取權限
	 * 
	 * @return 成員的存取權限
	 */
	public String getType(){
		return this.typeTranslateToString(this.type);
	}

	/**
	 * 設定路徑成員
	 * 
	 * @param member
	 */
	public void setPathMember(IGroup pathmember){
		this.pathmember = pathmember;
	}
	
	/**
	 * 取得路徑成員
	 * 
	 * @return
	 */
	public IGroup getPathMember(){
		return this.pathmember;
	}

	/**
	 * 將PermissionType轉換成String型態
	 * 
	 * @param type 所要轉換的Type
	 * @return
	 */
	public String typeTranslateToString(PermissionType type){
		
		String typeString = "";
		
		if( type == PermissionType.read ){
			typeString = "r";
		} else if( type == PermissionType.write ){
			typeString = "w";
		} else if( type == PermissionType.rw ){
			typeString = "rw";
		} else if( type == PermissionType.none ){
			typeString = "";
		}
		
		return typeString;
	}
	
	/**
	 * 將String轉換成PermissionType型態
	 * 
	 * @param type 所要轉換的Type
	 * @return
	 */
	public PermissionType typeTranslateToPermissionType(String type){
		
		PermissionType permissionType = null;
		
		if( type.equals("r") ){
			permissionType = PermissionType.read;
		} else if( type.equals("w") ){
			permissionType = PermissionType.write;
		} else if( type.equals("rw") ){
			permissionType = PermissionType.rw;
		} else {
			permissionType = PermissionType.none;
		}
		
		return permissionType;
	}
	
}
