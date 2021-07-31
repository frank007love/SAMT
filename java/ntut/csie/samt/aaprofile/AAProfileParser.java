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
	 * �غc��
	 * �إ�Unmarshaller����
	 */
	public AAProfileParser(File file){
		try
		{
			//�إ�Unmarshaller����, binding XML file��Element
			JAXBContext jc = JAXBContext.newInstance(this.packagePath);
			Unmarshaller u = jc.createUnmarshaller();
			JAXBElement<?> profileElement = (JAXBElement<?>) u.unmarshal(file);
			
			//���oXML File Root Element
			this.profile = (ntut.csie.samt.aaprofile.jaxb.AAProfile) profileElement.getValue();
		
			
		} catch (JAXBException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �qAAProfile�����o�b���C��
	 * @return �b���C��
	 */
	public List<String> getAccountIdList(){
		//�qprofile�����o�b���C��
		Accounts accounts = this.profile.getAccounts();
		List<Account> accountList = accounts.getAccount();
		//��l�ƭn�^�Ǫ��b���C��
		List<String> accountStringList = new ArrayList<String>();
		
		//���oaccountList�Ҧ���Account, �NAccount's id���accountStringList
		for( int i = 0 ; accountList != null && i < accountList.size() ; i++ ){
			Account account = accountList.get(i);
			accountStringList.add(account.getId());
		}
		
		//�^��accountStringList
		return accountStringList;
	}
	
	/**
	 * �qAAProfile�����o�K�X�C��
	 * @return �K�X�C��
	 */
	public List<String> getAccountPasswdList(){
		//�qprofile�����o�b���C��
		Accounts accounts = this.profile.getAccounts();
		List<Account> accountList = accounts.getAccount();
		//��l�ƭn�^�Ǫ��K�X�C��
		List<String> passwdStringList = new ArrayList<String>();
		
		//���oaccountList�Ҧ���Account, �NAccount's passwd���passwdStringList
		for( int i = 0 ; accountList != null && i < accountList.size() ; i++ ){
			Account account = accountList.get(i);
			passwdStringList.add(account.getPasswd());
		}
		
		//�^��passwdStringList
		return passwdStringList;
	}
	
	/**
	 * �qAAProfile�����o�s�զC��
	 * @return �s�զC��
	 */
	public List<String> getGroupList(){
		//�qprofile�����o�s�զC��
		List<Group> groupList = this.getGroupListFromProfile();
		//��l�ƭn�^�Ǫ��s�զC��
		List<String> groupStringList = new ArrayList<String>();
		
		//���ogroupList�Ҧ���group, �Ngroup's id���groupStringList
		for( int i = 0 ; groupStringList != null && i < groupList.size() ; i++ ){
			Group group = groupList.get(i);
			groupStringList.add(group.getId());
		}
		
		//�^��groupStringList
		return groupStringList;
	}
	
	/**
	 * ���oProfile�����Ҧ��s��
	 * @return
	 */
	private List<Group> getGroupListFromProfile(){
		Groups groups = this.profile.getGroups();
		List<Group> groupList = groups.getGroup();
		
		return groupList;
	}
	
	/**
	 * �qAAProfile�����o�s�զ����C��
	 * @param groupName �s�զW��
	 * @return
	 */
	public List<String> getGroupMemberList(String groupName){
		//��M�n���o�s�զ������s��
		Group group = this.findGroup(groupName);
		
		//�s�դ��s�b�h�S������
		if( group == null ){
			return null;
		}
		
		//���o�s�զ����C��
		List<GroupMember> groupMemberList = group.getGroupMebmer();
		//��l�ƭn�^�Ǫ��s�զC��
		List<String> groupMemberStringList = new ArrayList<String>();
		
		/*
		 * ���ogroupMemberList�Ҧ���groupMember, 
		 * �NgroupMember's id���groupMemberStringList
		 */
		for( int i = 0 ; groupMemberList != null && i < groupMemberList.size() ; i++ ){
			GroupMember groupMember = groupMemberList.get(i);
			String groupMemberName = groupMember.getId();
			//�Y�����O�@�Ӹs��, �̷�SVN�ɮ׮榡�b�W�٫e�[@
			if( groupMember.getType().equals("Group") ){
				groupMemberName = "@" + groupMemberName;
			}
			
			groupMemberStringList.add(groupMemberName);
		}
		
		return groupMemberStringList;
	}

	/**
	 * ��M�s�զC���W�٬�groupName���s��
	 * @param groupName �n��M���s�զW��
	 * @return 
	 */
	private Group findGroup(String groupName){
		//�qProfile�����o�s�զC��
		List<Group> groupList = this.getGroupListFromProfile();
		Group group = null;
		//��M�s��, ���NBreak
		for( int i = 0 ; groupList != null && i < groupList.size() ; i++ ){
			group = groupList.get(i);
			if( group.getId().equals(groupName) ){
				break;
			}
			//�D�n��M���]��null, �ϱo�Y�����S���N�^��null
			group = null;
		}
		//�^�ǧ�쪺�s��
		return group;
	}
	
	/**
	 * �qAAProfile�����o�v���y�z����RepositoryPath�C��
	 * @return RepositoryPath�C��
	 */
	public List<String> getRepositoryPath(){
		//�qProfile�����oRepositoryPath�C��
		List<Path> pathList = this.getPathListFromProfile();
		List<String> pathStringList = new ArrayList<String>();

		//�NpathList���Ҧ���Path Name���pathStringList
		for( int i = 0 ; pathList != null && i < pathList.size() ; i++ ){
			Path path = pathList.get(i);
			pathStringList.add(path.getName());
		}
		
		//�^��RepositoryPath�C��
		return pathStringList;
	}
	
	/**
	 * �qProfile�����o�Ҧ����|
	 * @return
	 */
	private List<Path> getPathListFromProfile(){
		Permissions permissions = this.profile.getPermissions();
		List<Path> pathList = permissions.getPath();
		
		return pathList;
	}
	
	/**
	 * �qAAProfile�����opath�����|�����C��
	 * @param path �n���o���|���������|
	 * @return ���|�����C��
	 */
	public List<String> getPathMember(String pathName){
		//���o�n��M���������|
		Path path = this.findRepositoryPath(pathName);
		
		//���|���s�b�h�S������
		if( path == null ){
			return null;
		}
		
		List<PathMember> pathMemberList = path.getPathMember();
		List<String> pathMemberStringList = new ArrayList<String>();
		
		/*
		 * ���opathMemberList�Ҧ���pathMember, 
		 * �NpathMember's id���pathMemberStringList
		 */
		for( int i = 0 ; pathMemberList != null && i < pathMemberList.size() ; i++ ){
			PathMember pathMember = pathMemberList.get(i);
			String memberName = pathMember.getId();
			//�Y�����O�s��, �̷�SVN�ɮ׮榡�b�W�٫e�[@
			if( pathMember.getType().equals("Group") ){
				memberName = "@" + memberName;
			} 
			
			pathMemberStringList.add(memberName);
		}
		//�^�Ǹ��|�����C��
		return pathMemberStringList;
	}
	
	/**
	 * ��M���|�C���W�٬�path�����|
	 * @param pathName
	 * @return
	 */
	private Path findRepositoryPath(String pathName){
		//�qProfile�����o���|�C��
		List<Path> pathList = this.getPathListFromProfile();
		Path path = null;
		//��M���|, ���NBreak
		for( int i = 0 ; pathList != null && i < pathList.size() ; i++ ){
			path = pathList.get(i);
			if( path.getName().equals(pathName) ){
				break;
			}
			//�D�n��M���]��null, �ϱo�Y�����S���N�^��null
			path = null;
		}
		//�^�ǧ�쪺���|
		return path;
	}
	
	/**
	 * �qAAProfile�����opath�����|����
	 * @param pathName ���|�W��
	 * @param memberName �����W��
	 * @return
	 */
	private PathMember findPathMember(String pathName, String memberName){
		//���o�n��M���������|
		Path path = this.findRepositoryPath(pathName);
		
		//���|���s�b�h�S������
		if( path == null ){
			return null;
		}
		
		List<PathMember> pathMemberList = path.getPathMember();
		PathMember pathMember = null; //�n��M�����|����
		
		/*
		 * ��M�W�٬�memberName�����|����
		 */
		for( int i = 0 ; pathMemberList != null && i < pathMemberList.size() ; i++ ){
			pathMember = pathMemberList.get(i);
			String name = pathMember.getId();
			//�Y�����O�s��, �̷�SVN�ɮ׮榡�b�W�٫e�[@
			if( pathMember.getType().equals("Group") ){
				name = "@" + name;
			}
			//���n�䪺�s�մNBreak
			if( name.equals(memberName) ){
				break;
			}
			//�S���N�]��null
			pathMember = null;
		}
		//�^�ǧ�쪺���|����
		return pathMember;
	}
	
	/**
	 * ���o���|�������v�����
	 * @param pathName
	 * @param memberName
	 * @return �iŪ"r", �i�g"w", �iŪ�i�g"rw", �����i""
	 */
	public String getPermission(String pathName, String memberName){
		//���o�n��M�����v�������|
		Path path = this.findRepositoryPath(pathName);
		
		//���|���s�b�h�L�v�����
		if( path == null ){
			return null;
		}
		//�����v��
		String permission = "";	
		//���o���|����
		PathMember pathMember = this.findPathMember(pathName, memberName);
		
		if( pathMember == null ){
			return permission;
		}
		
		//���oŪ���v��
		boolean readable = pathMember.isRead();
		boolean writable = pathMember.isWrite();
		//�]�w�^���v����T
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
	 * ���o�Ҧ��n�إߪ�Folder���|
	 * @return
	 */
	public List<String> getAllFolderList(){
		//���oFolder�C��
		FolderList folderList = this.profile.getFolderList();
		List<Folder> folders = folderList.getFolder();
		List<String> stringFolders = new ArrayList<String>();
		
		/**
		 * �N�qProfile���o�nCreate���ɮצC����stringFolders
		 */
		for( int i = 0 ; folders != null && i < folders.size() ; i++ ){
			Folder folder = folders.get(i);
			stringFolders.add(folder.getPath());
		}
		//�^��Folder�C��
		return stringFolders;
	}
	
}
