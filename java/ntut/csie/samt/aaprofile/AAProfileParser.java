package ntut.csie.samt.aaprofile;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ntut.csie.samt.aaprofile.jaxb.*;

public class AAProfileParser {

	final private String packagePath = "ntut.csie.samt.aaprofile.jaxb";
	private ntut.csie.samt.aaprofile.jaxb.AAProfile profile; //jaxb object
	
	/**
	 * 建構元
	 * 建立Unmarshaller物件
	 */
	public AAProfileParser(File file){
		try
		{
			//建立Unmarshaller物件, binding XML file的Element
			JAXBContext jc = JAXBContext.newInstance(this.packagePath);
			Unmarshaller u = jc.createUnmarshaller();
			JAXBElement<?> profileElement = (JAXBElement<?>) u.unmarshal(file);
			
			//取得XML File Root Element
			this.profile = (ntut.csie.samt.aaprofile.jaxb.AAProfile) profileElement.getValue();
		
			
		} catch (JAXBException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 從AAProfile中取得帳號列表
	 * @return 帳號列表
	 */
	public List<String> getAccountIdList(){
		//從profile中取得帳號列表
		Accounts accounts = this.profile.getAccounts();
		List<Account> accountList = accounts.getAccount();
		//初始化要回傳的帳號列表
		List<String> accountStringList = new ArrayList<String>();
		
		//取得accountList所有的Account, 將Account's id放到accountStringList
		for( int i = 0 ; accountList != null && i < accountList.size() ; i++ ){
			Account account = accountList.get(i);
			accountStringList.add(account.getId());
		}
		
		//回傳accountStringList
		return accountStringList;
	}
	
	/**
	 * 從AAProfile中取得密碼列表
	 * @return 密碼列表
	 */
	public List<String> getAccountPasswdList(){
		//從profile中取得帳號列表
		Accounts accounts = this.profile.getAccounts();
		List<Account> accountList = accounts.getAccount();
		//初始化要回傳的密碼列表
		List<String> passwdStringList = new ArrayList<String>();
		
		//取得accountList所有的Account, 將Account's passwd放到passwdStringList
		for( int i = 0 ; accountList != null && i < accountList.size() ; i++ ){
			Account account = accountList.get(i);
			passwdStringList.add(account.getPasswd());
		}
		
		//回傳passwdStringList
		return passwdStringList;
	}
	
	/**
	 * 從AAProfile中取得群組列表
	 * @return 群組列表
	 */
	public List<String> getGroupList(){
		//從profile中取得群組列表
		List<Group> groupList = this.getGroupListFromProfile();
		//初始化要回傳的群組列表
		List<String> groupStringList = new ArrayList<String>();
		
		//取得groupList所有的group, 將group's id放到groupStringList
		for( int i = 0 ; groupStringList != null && i < groupList.size() ; i++ ){
			Group group = groupList.get(i);
			groupStringList.add(group.getId());
		}
		
		//回傳groupStringList
		return groupStringList;
	}
	
	/**
	 * 取得Profile中的所有群組
	 * @return
	 */
	private List<Group> getGroupListFromProfile(){
		Groups groups = this.profile.getGroups();
		List<Group> groupList = groups.getGroup();
		
		return groupList;
	}
	
	/**
	 * 從AAProfile中取得群組成員列表
	 * @param groupName 群組名稱
	 * @return
	 */
	public List<String> getGroupMemberList(String groupName){
		//找尋要取得群組成員的群組
		Group group = this.findGroup(groupName);
		
		//群組不存在則沒有成員
		if( group == null ){
			return null;
		}
		
		//取得群組成員列表
		List<GroupMember> groupMemberList = group.getGroupMebmer();
		//初始化要回傳的群組列表
		List<String> groupMemberStringList = new ArrayList<String>();
		
		/*
		 * 取得groupMemberList所有的groupMember, 
		 * 將groupMember's id放到groupMemberStringList
		 */
		for( int i = 0 ; groupMemberList != null && i < groupMemberList.size() ; i++ ){
			GroupMember groupMember = groupMemberList.get(i);
			String groupMemberName = groupMember.getId();
			//若成員是一個群組, 依照SVN檔案格式在名稱前加@
			if( groupMember.getType().equals("Group") ){
				groupMemberName = "@" + groupMemberName;
			}
			
			groupMemberStringList.add(groupMemberName);
		}
		
		return groupMemberStringList;
	}

	/**
	 * 找尋群組列表中名稱為groupName的群組
	 * @param groupName 要找尋的群組名稱
	 * @return 
	 */
	private Group findGroup(String groupName){
		//從Profile中取得群組列表
		List<Group> groupList = this.getGroupListFromProfile();
		Group group = null;
		//找尋群組, 找到就Break
		for( int i = 0 ; groupList != null && i < groupList.size() ; i++ ){
			group = groupList.get(i);
			if( group.getId().equals(groupName) ){
				break;
			}
			//非要找尋的設為null, 使得若全部沒找到就回傳null
			group = null;
		}
		//回傳找到的群組
		return group;
	}
	
	/**
	 * 從AAProfile中取得權限描述中的RepositoryPath列表
	 * @return RepositoryPath列表
	 */
	public List<String> getRepositoryPath(){
		//從Profile中取得RepositoryPath列表
		List<Path> pathList = this.getPathListFromProfile();
		List<String> pathStringList = new ArrayList<String>();

		//將pathList中所有的Path Name放到pathStringList
		for( int i = 0 ; pathList != null && i < pathList.size() ; i++ ){
			Path path = pathList.get(i);
			pathStringList.add(path.getName());
		}
		
		//回傳RepositoryPath列表
		return pathStringList;
	}
	
	/**
	 * 從Profile中取得所有路徑
	 * @return
	 */
	private List<Path> getPathListFromProfile(){
		Permissions permissions = this.profile.getPermissions();
		List<Path> pathList = permissions.getPath();
		
		return pathList;
	}
	
	/**
	 * 從AAProfile中取得path的路徑成員列表
	 * @param path 要取得路徑成員的路徑
	 * @return 路徑成員列表
	 */
	public List<String> getPathMember(String pathName){
		//取得要找尋成員的路徑
		Path path = this.findRepositoryPath(pathName);
		
		//路徑不存在則沒有成員
		if( path == null ){
			return null;
		}
		
		List<PathMember> pathMemberList = path.getPathMember();
		List<String> pathMemberStringList = new ArrayList<String>();
		
		/*
		 * 取得pathMemberList所有的pathMember, 
		 * 將pathMember's id放到pathMemberStringList
		 */
		for( int i = 0 ; pathMemberList != null && i < pathMemberList.size() ; i++ ){
			PathMember pathMember = pathMemberList.get(i);
			String memberName = pathMember.getId();
			//若成員是群組, 依照SVN檔案格式在名稱前加@
			if( pathMember.getType().equals("Group") ){
				memberName = "@" + memberName;
			} 
			
			pathMemberStringList.add(memberName);
		}
		//回傳路徑成員列表
		return pathMemberStringList;
	}
	
	/**
	 * 找尋路徑列表中名稱為path的路徑
	 * @param pathName
	 * @return
	 */
	private Path findRepositoryPath(String pathName){
		//從Profile中取得路徑列表
		List<Path> pathList = this.getPathListFromProfile();
		Path path = null;
		//找尋路徑, 找到就Break
		for( int i = 0 ; pathList != null && i < pathList.size() ; i++ ){
			path = pathList.get(i);
			if( path.getName().equals(pathName) ){
				break;
			}
			//非要找尋的設為null, 使得若全部沒找到就回傳null
			path = null;
		}
		//回傳找到的路徑
		return path;
	}
	
	/**
	 * 從AAProfile中取得path的路徑成員
	 * @param pathName 路徑名稱
	 * @param memberName 成員名稱
	 * @return
	 */
	private PathMember findPathMember(String pathName, String memberName){
		//取得要找尋成員的路徑
		Path path = this.findRepositoryPath(pathName);
		
		//路徑不存在則沒有成員
		if( path == null ){
			return null;
		}
		
		List<PathMember> pathMemberList = path.getPathMember();
		PathMember pathMember = null; //要找尋的路徑成員
		
		/*
		 * 找尋名稱為memberName的路徑成員
		 */
		for( int i = 0 ; pathMemberList != null && i < pathMemberList.size() ; i++ ){
			pathMember = pathMemberList.get(i);
			String name = pathMember.getId();
			//若成員是群組, 依照SVN檔案格式在名稱前加@
			if( pathMember.getType().equals("Group") ){
				name = "@" + name;
			}
			//找到要找的群組就Break
			if( name.equals(memberName) ){
				break;
			}
			//沒找到就設為null
			pathMember = null;
		}
		//回傳找到的路徑成員
		return pathMember;
	}
	
	/**
	 * 取得路徑成員的權限資料
	 * @param pathName
	 * @param memberName
	 * @return 可讀"r", 可寫"w", 可讀可寫"rw", 都不可""
	 */
	public String getPermission(String pathName, String memberName){
		//取得要找尋成員權限的路徑
		Path path = this.findRepositoryPath(pathName);
		
		//路徑不存在則無權限資料
		if( path == null ){
			return null;
		}
		//成員權限
		String permission = "";	
		//取得路徑成員
		PathMember pathMember = this.findPathMember(pathName, memberName);
		
		if( pathMember == null ){
			return permission;
		}
		
		//取得讀取權限
		boolean readable = pathMember.isRead();
		boolean writable = pathMember.isWrite();
		//設定回傳權限資訊
		if( readable && writable ){
			permission = "rw";
		} else if( readable ){
			permission = "r";
		} else if( writable ){
			permission = "w";
		} else {
			permission = "";
		}
		
		return permission;
	}
	
	/**
	 * 取得所有要建立的Folder路徑
	 * @return
	 */
	public List<String> getAllFolderList(){
		//取得Folder列表
		FolderList folderList = this.profile.getFolderList();
		List<Folder> folders = folderList.getFolder();
		List<String> stringFolders = new ArrayList<String>();
		
		/**
		 * 將從Profile取得要Create的檔案列表放到stringFolders
		 */
		for( int i = 0 ; folders != null && i < folders.size() ; i++ ){
			Folder folder = folders.get(i);
			stringFolders.add(folder.getPath());
		}
		//回傳Folder列表
		return stringFolders;
	}
	
}
