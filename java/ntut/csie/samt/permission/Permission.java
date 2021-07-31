package ntut.csie.samt.permission;

import ntut.csie.samt.group.*;

public class Permission {

	private PermissionType type;
	private IGroup pathmember;

	/**
	 * �غc��
	 * �N������l�s���v���]���S��
	 * 
	 * @param pathmember ���|���U������
	 */
	public Permission(IGroup pathmember){
		this.type = PermissionType.none;
		this.pathmember = pathmember;
	}
	
	/**
	 * �]�w�����s���v��
	 * 
	 * @param type �ҭn�]�w���v��
	 */
	public void setType(String type){
		this.type = this.typeTranslateToPermissionType(type);
	}
	
	/**
	 * ���o�����s���v��
	 * 
	 * @return �������s���v��
	 */
	public String getType(){
		return this.typeTranslateToString(this.type);
	}

	/**
	 * �]�w���|����
	 * 
	 * @param member
	 */
	public void setPathMember(IGroup pathmember){
		this.pathmember = pathmember;
	}
	
	/**
	 * ���o���|����
	 * 
	 * @return
	 */
	public IGroup getPathMember(){
		return this.pathmember;
	}

	/**
	 * �NPermissionType�ഫ��String���A
	 * 
	 * @param type �ҭn�ഫ��Type
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
	 * �NString�ഫ��PermissionType���A
	 * 
	 * @param type �ҭn�ഫ��Type
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
