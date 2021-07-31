package ntut.csie.samt.permission;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import ntut.csie.samt.group.*;

public class RepositoryPath {

	private List<Permission> permissions;
	private String pathname;
	
	/**
	 * 建構元
	 * 初始化permission
	 * 設定路徑名稱
	 * 
	 * @param name 路徑名稱
	 */
	public RepositoryPath(String name){
		this.permissions = new ArrayList<Permission>();
		this.pathname = name;
	}
	
	/**
	 * 新增一筆Permission Data
	 * 
	 * @param igroup 此PermissionData所描述的使用者
	 * @return 新增成功則回傳true, 反之則false
	 */
	public boolean addPermission(IGroup igroup,String type){
		
		//找尋所要新增之Permission成員是否已存在,存在則不加入
		if( this.findPermission(igroup) != null || igroup == null  ){
			return false;
		}
		//新增一個Permission,並init權限為沒有
		Permission permission = new Permission(igroup);
		permission.setType(type);
		this.permissions.add(permission);
		return true;
	}
	
	/**
	 * 刪除存取權限
	 * 
	 * @param igroup 要刪除所設定之權限成員
	 * @return 刪除成功則回傳true, 反之則false
	 */
	public boolean removePermission(IGroup igroup){
		
		Permission permission = this.findPermission(igroup);
		//找尋所要新增之Permission成員是否已存在,存在才刪除
		if( permission == null ){
			return false;
		}
		//刪除權限資料
		this.permissions.remove(permission);
		return true;
	}
	
	/**
	 * 修改成員存取權限
	 * 
	 * @param igroup 要修改之成員
	 * @param type 存取權限型態
	 * @return 修改成功則回傳true, 反之則false
	 */
	public boolean modifyPermission(IGroup igroup, String type){
		
		Permission permission = this.findPermission(igroup);
		//找尋所要新增之Permission成員是否已存在,存在才修改權限
		if( permission == null ){
			return false;
		}
		//修改權限
		permission.setType(type);
		return true;
	}
	
	/**
	 * 依據成員尋找Permission
	 * 
	 * @param igroup 要找尋Permission之成員
	 * @return 找尋結果, 有找到則回傳permission
	 */
	public Permission findPermission(IGroup igroup){
		Iterator<Permission> iter = this.permissions.iterator();
		//find the pathMember match the igroup 
		while(iter.hasNext()){
			Permission permission = (Permission)iter.next();
			IGroup pathMember = permission.getPathMember();
			//若路徑成員存在且與要找尋的成員相同則回傳它的permission
			if( pathMember != null && pathMember.isEqual(igroup) ){
				return permission;		
			}
		}
		//No find
		return null;
	}
	
	/**
	 * 取得路徑下所設定之成員
	 * @return
	 */
	public List<String> getStringMemberList(){
		Iterator<Permission> iter = this.permissions.iterator();
		List<String> mebmerList = new ArrayList<String>();

		while(iter.hasNext()){
			Permission permission = (Permission)iter.next();
			String name = permission.getPathMember().getName();
			String type = permission.getPathMember().getType();
			//若成員是group則名稱要加@, 若是account則不需要
			if( type.equals("account") ){
				mebmerList.add(name);
			} else if( type.equals("group") ){
				mebmerList.add("@" + name);
			}
			
		}
		//回傳成員列表
		return mebmerList;
	}
	
	/**
	 * 取得成員權限
	 * @param igroup 成員
	 * @return 權限
	 */
	public String getPermission(IGroup igroup){
		//取得成員權限
		Permission p = this.findPermission(igroup);
		
		//若無此成員, 權限為無
		if( p == null ){
			return "";
		}
		//回傳成員權限
		return p.getType();
	}
	
	/**
	 * 取得路徑名稱
	 * @return
	 */
	public String getPathName(){
		return this.pathname;
	}
}
