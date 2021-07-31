package ntut.csie.samt.manager;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import ntut.csie.samt.group.*;
import ntut.csie.samt.permission.*;
import ntut.csie.samt.svncontroll.AuthorizationEntity;

public class AuthorizationCenter {

	private Map<String,IGroup> groups;
	private Map<String,RepositoryPath> path;
	private AuthorizationEntity authorizationEntity;
	private File authorizationFile;
	
	/**
	 * 建構元
	 * 
	 */
	public AuthorizationCenter(){
		this.groups = new HashMap<String,IGroup>();
		this.path = new HashMap<String,RepositoryPath>();
	}
	
	/**
	 * 取得檔案內的群組相關內容, 包含群組與群組成員資料,
	 * 將內容放到groups, 採取先讀取群組之原因,
	 * 為了避免前面的群組包含後面的群組為成員
	 */
	private void loadGroupHeader(){
		this.authorizationEntity.load();
		//取得群組資料
		String []groupList = this.authorizationEntity.getGroupList();
		//將所有群組新增到groups
		for( int i = 0 ; i <groupList.length ; i++ ){
			this.addGroup(groupList[i]);
		}
		//將所有群組成員新增到群組
		for( int i = 0 ; i <groupList.length ; i++ ){
			//取得群組
			IGroup group = findGroup(groupList[i]);
			//讀取所有群組成員列表
			String []groupMembers = this.authorizationEntity.getGroupMemberList(group.getName());
			
			//有群組成員
			if( groupMembers != null ){
			
				//將讀取的群組成員列表加到群組中
				for( int j = 0 ; j < groupMembers.length ; j++ ){
					//群組成員為一個群組
					if( groupMembers[j].startsWith("@") ){
						this.addGroupMember(group.getName(), groupMembers[j].substring(1), "group");
					}//群組成員為一個使用者
					else { 
						this.addGroupMember(group.getName(), groupMembers[j], "account");
					}
				}
			}
		}
	}
	
	/**
	 * 取得檔案內的權限相關內容, 包含路徑與路徑成員,
	 * 及其權限
	 */
	private void loadPermission(){
		String []pathList = this.authorizationEntity.getRepositoryPath();
		for( int i = 0 ; i < pathList.length ; i++ ){		
			String []pathMemberList = this.authorizationEntity.getFileMemberList(pathList[i]);
			for( int j = 0 ; j < pathMemberList.length ; j++ ){
				String name = pathMemberList[j];
				String permission = this.authorizationEntity.getPermissionsData(pathList[i], name);
				String type = "account";
				
				//the member is a group
				if( name.startsWith("@") ){
					name = name.substring(1);
					type = "group";
				//在SVN中, *代表全部的人
				} else if( name.equals("*") ){
					type = "group";
				}
				
				this.addPermission(pathList[i], name,type , permission);
			}
		}
	}
	
	/**
	 * 將所有群組資訊設置到authorizationEntity
	 */
	private void saveGroupHeader(){
		//取得現有的群組列表
		List<String> groupList = this.getStringGroupList();
		//設置群組內容
		this.authorizationEntity.setGroupList(groupList);
		//設置群組成員內容
		for( int i = 0 ; groupList != null && i < groupList.size() ; i++ ){
			String groupName = groupList.get(i);
			//取得群組成員列表
			List<String> memberList = this.getStringMemberList(groupName);
			//設置群組成員內容
			this.authorizationEntity.setGroupMember(groupName, memberList);
		}
	}
	
	/**
	 * 將所有權限資訊, 包含路徑與路徑成員與權限,
	 * 設置到authorizationEntity
	 */
	private void savePermission(){
		Collection<RepositoryPath> pathList = this.path.values();
		Iterator<RepositoryPath> iter = pathList.iterator();
		
		//對各路徑取得其成員與權限並設置到authorizationEntity
		while( iter.hasNext() ){
			RepositoryPath rp = iter.next();
			//路徑名稱
			String pathName = rp.getPathName();
			//成員列表
			List<String> memberList = this.getPathMemberList(pathName);
			List<String> permissionList = new ArrayList<String>();
			//取得現有的所有群組成員及其權限,然後設置到authorizationEntity
			for( int i = 0 ; i < memberList.size() ; i++){
				String name = memberList.get(i);
				String type = "account";
				//成員為一個群組則只取名稱@之後的
				if( name.startsWith("@") ){
					name = name.substring(1);
					type = "group";
				}
				//取得成員的權限
				String permission = this.getPermission(pathName, name, type);
				permissionList.add(permission);
			}
			
			//設置路徑的權限資訊
			this.authorizationEntity.setPermissionData(pathName, memberList, permissionList);
		}
		
	}
	
	/**
	 * 讀取權限檔案的資料
	 * 
	 * @param name
	 * @return
	 */
	public boolean load(String name){
		this.authorizationFile = new File(name);

		if( this.authorizationFile.exists() ){

			this.authorizationEntity = new AuthorizationEntity(authorizationFile);
			//讀取群組資訊
			this.loadGroupHeader();
			//讀取權限資訊
			this.loadPermission();
			return true;
		}
		return false;
	}
	
	/**
	 * 將所有權限設定檔資料存入
	 * @return
	 */
	public boolean save(){
		if( this.authorizationFile.exists() && this.authorizationEntity != null ){
			//設置群組資訊
			this.saveGroupHeader();
			//設置權限資訊
			this.savePermission();
			//進行儲存
			this.authorizationEntity.save();
			return true;
		}
		return false;
	}
	
	/**
	 * 新增群組, 重複的無法加入, 且回傳false
	 * 
	 * @param name 所要新增的群組名稱 
	 * 
	 * @return 若是新增成功則回傳true, 反之則false
	 */
	public boolean addGroup(String name){
		
		//空白字串
		name.trim();
		if( name.equals("") )
			return false;
		
		//取得名稱為name的群組
		IGroup igroup = this.findGroup(name);
		//若此群組不存在, 則新增一個到groups
		if( igroup == null ){
			
			//新增一個名稱為name的群組
			igroup = new Group();
			igroup.setName(name);
			//加入到igroup
			this.groups.put(name,igroup);
			
			return true;
		}
		//群組已存在
		return false;
	}
	
	/**
	 * 修改群組名稱, 無法修改成已存在的
	 * 
	 * @param oldname 要修改的群組名稱
	 * @param newname 修改之後的群組名稱
	 * @return
	 */
	public boolean modifyGroupName(String oldname, String newname){
		
		//空白字串
		newname.trim();
		if( newname.equals("") )
			return false;
		
		//取得舊群組
		IGroup oldGroup = this.groups.get(oldname);
		//所要修改之群組名稱已存在 or 要修改之群組不存在
		if( this.groups.containsKey(newname) || oldGroup == null ){
			return false;
		}
		
		//修改群組名稱
		//由於key改變, 所要移除原本的群組, 然後再加入
		this.groups.remove(oldname);
		oldGroup.setName(newname);
		this.groups.put(newname, oldGroup);
		
		
		//若此群組為其它群組成員, 亦要修改名稱
		//取得所有群組
		Collection<IGroup> allgroups = this.groups.values();
		
		//修改所有群組的名稱為oldname的群組成員名稱為newname
		Iterator<IGroup> iter = allgroups.iterator();
		
		while( iter.hasNext() ){
			//取得要修改之群組成員
			IGroup modifyGroup = iter.next().findGroupMember(oldname, GroupMemberType.group);
			//修改群組成員之名稱
			if( modifyGroup != null ){ //是要修改的群組
				modifyGroup.setName(newname);
			}
		}
		
		//修改此群組在權限設定的名稱
		
		//寫入檔案
		
		return true;
	}
	
	/**
	 * 刪除群組
	 * 
	 * @param name 要刪除群組之名稱
	 * @return 刪除成功則回傳true, 反之則為false
	 */
	public boolean deleteGroup(String name){
		//要刪除之群組不存在
		if(  !this.groups.containsKey(name) ){
			return false;
		}
		
		//刪除名稱為name的群組
		this.groups.remove(name);
		
		//若此群組為其它群組成員, 亦要刪除
		//取得所有群組
		Collection<IGroup> allgroups = this.groups.values();

		//刪除名稱為name的群組成員
		Iterator<IGroup> iter = allgroups.iterator();

		while( iter.hasNext() ){
			//刪除名稱為name,且是群組的群組成員
			iter.next().deleteGroupMember(name, "group");
		}
		
		//刪除在權限設定的此群組
		List<String> pathList = this.getPermissionPathList();
		Iterator<String> iter2 = pathList.iterator();
		while( iter2.hasNext() ){
			//刪除要刪除群組的權限資料
			String path = iter2.next();
			this.deletePermission(path, name, "group");
		}
		
		return true;
	}
	
	/**
	 * 取得所有群組名稱
	 * 
	 * @return 群組列表
	 */
	public List<String> getStringGroupList(){
		
		if( this.groups.size() == 0 ){
			return null;
		}
		
		List<String> groupList = new ArrayList<String>();
		
		//取得所有群組
		Collection<IGroup> allgroups = this.groups.values();
		
		Iterator<IGroup> iter = allgroups.iterator();
		//將群組名稱放入groupList
		while( iter.hasNext() ){
			groupList.add(iter.next().getName());
		}
		
		return groupList;
	}
	
	/**
	 * 取得所有群組
	 * 
	 * @return 群組列表
	 */
	public List<IGroup> getGroupList(){
		
		if( this.groups.size() == 0 ){
			return null;
		}
		
		List<IGroup> groupList = new ArrayList<IGroup>();
		
		//取得所有群組
		Collection<IGroup> allgroups = this.groups.values();
		
		Iterator<IGroup> iter = allgroups.iterator();
		//將群組名稱放入groupList
		while( iter.hasNext() ){
			groupList.add(iter.next());
		}
		
		return groupList;
	}

	/**
	 * 找尋群組
	 * 
	 * @param name 要找尋的群組名稱
	 * @return 有找到則回傳群組, 否則就null
	 */
	public IGroup findGroup(String name){
		return this.groups.get(name);
	}
	
	/**
	 * 新增群組成員
	 * 
	 * @param groupName 要加入之群組
	 * @param memberName 所新增成員名稱
	 * @param type 成員型態
	 * @return 成功則回傳true, 失敗則回傳false
	 */
	public boolean addGroupMember(String groupName, String memberName, String type){
		//要加入之群組
		IGroup group = findGroup(groupName);
		//群組不存在
		if( group == null ){
			return false; 
		}

		//所要加入的成員
		IGroup member = null;
		
		//所加入之成員為使用者
		if( type.equals("account") ){
			member = new User();
			member.setName(memberName);
		//所加入之成員為群組
		} else if( type.equals("group") ){
			member = findGroup(memberName);
		}
		
		//所要加入之成員不存在
		if( member == null){
			return false;
		}
		
		//新增成員
		return group.addGroupMember(member);
	}
	
	/**
	 * 刪除群組成員
	 * 
	 * @param groupName
	 * @param memberName
	 * @param type
	 * @return
	 */
	public boolean deleteGroupMember(String groupName, String memberName, String type){
		//要刪除之群組
		IGroup group = findGroup(groupName);
		//群組不存在
		if( group == null ){
			return false; 
		}

		return group.deleteGroupMember(memberName, type);
	}
	
	/**
	 * 取得群組成員
	 * 
	 * @param groupName 要取得之群組
	 * @return 群組之成員
	 */
	public List<String> getStringMemberList(String groupName){
		IGroup group = this.findGroup(groupName);
		//群組不存在
		if( group == null ){
			return null;
		}
		
		List<IGroup> members = group.getGroupMembers();
		List<String> membersString = new ArrayList<String>();
		
		Iterator<IGroup> iter = members.iterator();
		//若是使用者直接回傳名稱,若是群組增加@用以辨別
		while( iter.hasNext() ){
			
			IGroup member = iter.next();
			
			if( member.getType().equals("account") ){
				membersString.add(member.getName());
			} else if( member.getType().equals("group") ) {
				membersString.add("@"+member.getName());
			}
		}
		
		return membersString;	
	}
	
	/**
	 * 新增一個存取權限
	 * @param path 所要增加的路徑
	 * @param name 設定之成員名稱
	 * @param type 成員型態
	 * @param permission 權限
	 * @return 成功則回傳True, 反之則false
	 */
	public boolean addPermission(String path, String name, String type,String permission){
		
		RepositoryPath rp = this.findRepositoryPath(path);
		//RepositoryPath不存在, 則新建一個
		if( rp == null ){
			rp = new RepositoryPath(path);
		}
		
		IGroup filemember = null;
		
		//在SVN描述檔中'*'為全部的人, 故當作Group
		if( name.equals("*") && type.equals("group") ){
			filemember = new Group();
			filemember.setName("*");
		//所要設定之成員為使用者
		} else if( type.equals("account") ){
			filemember = new User();
			filemember.setName(name);
		//所要設定之成員為群組
		} else if( type.equals("group") ){
			filemember = this.findGroup(name);
		}
		//新增一個存取權限
		if( rp.addPermission(filemember,permission) ){
			this.path.put(path, rp);
			return true;
		} else
			return false;
	}
	
	/**
	 * 刪除某路徑下的成員存取權限
	 * @param path 所要刪除成員之路徑
	 * @param name 刪除成員名稱
	 * @param type 刪除成員型態
	 * @return 成功則回傳True, 反之則false
	 */
	public boolean deletePermission(String path, String name, String type){
		RepositoryPath rp = this.findRepositoryPath(path);
		//RepositoryPath不存在, 則無法刪除
		if( rp == null ){
			return false;
		}
		
		IGroup filemember = null;
		//所要刪除之成員為使用者
		if( type.equals("account") ){
			filemember = new User();
			filemember.setName(name);
		//所要刪除之成員為群組
		} else if( type.equals("group") ){
			filemember = new Group();
			filemember.setName(name);
		}
		
		return rp.removePermission(filemember);
	}
	
	/**
	 * 修改路徑下群組之權限
	 * @param path 要修改權限的路徑
	 * @param name 要修改的成員名稱
	 * @param type 成員之型態
	 * @param permission 要修改的權限
	 * @return
	 */
	public boolean modifyPermission(String path, String name, String type,String permission){
		RepositoryPath rp = this.findRepositoryPath(path);
		//RepositoryPath不存在則無法修改
		if( rp == null ){
			return false;
		}

		IGroup filemember = null;
		//所要設定之成員為使用者
		if( type.equals("account") ){
			filemember = new User();
			filemember.setName(name);
		//所要設定之成員為群組
		} else if( type.equals("group") ){
			//若名稱為*代表所有的群組或是人, 將它當群組使用
			if( name.equals("*") ){
				filemember = new Group();
				filemember.setName("*");
			} else {
				filemember = this.findGroup(name);
			}
		}
		//修改權限權限
		return rp.modifyPermission(filemember, permission);
	}
	
	/**
	 * 找尋RepositoryPath
	 * 
	 * @param path 所要找尋的路徑
	 * @return 找尋到的東西
	 */
	public RepositoryPath findRepositoryPath(String path){
		return this.path.get(path);
	}
	
	/**
	 * 根據路徑取得其下的成員列表
	 * @param path 要取得列表的路徑
	 * @return 成員列表
	 */
	public List<String> getPathMemberList(String path){
		RepositoryPath rp = findRepositoryPath(path);	
		if( rp == null ){
			return null;
		}
		return rp.getStringMemberList();
	}
	
	/**
	 * 取得路徑path下名稱為name的成員權限
	 * @param path 路徑名稱
	 * @param name 成員名稱
	 * @param type 成員型態,為account or group
	 * @return 成員權限, 為"r","rw","w",""
	 */
	public String getPermission(String path,String name,String type){
		//取得Path
		RepositoryPath rp = findRepositoryPath(path);	
		if( rp == null ){
			return null;
		}
		//設置成員給rp找尋權限
		IGroup member = null;
		if( type.equals("group") ){
			member = new Group();
		} else if( type.equals("account") ){
			member = new User();
		}
		member.setName(name);
		//取得權限
		return rp.getPermission(member);
	}
	
	/**
	 * 取得所有權限描述檔內的路徑列表
	 * @return
	 */
	public List<String> getPermissionPathList(){
		List<String> pathlist = new ArrayList<String>();
		pathlist.addAll(this.path.keySet());
		return pathlist;
	}
	
	/**
	 * 取得群組的數量
	 * 
	 * @return 群組數量
	 */
	public int getGroupSize(){
		return this.groups.size();
	}
	
}
