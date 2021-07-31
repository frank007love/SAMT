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
	 * �غc��
	 * 
	 */
	public AuthorizationCenter(){
		this.groups = new HashMap<String,IGroup>();
		this.path = new HashMap<String,RepositoryPath>();
	}
	
	/**
	 * ���o�ɮפ����s�լ������e, �]�t�s�ջP�s�զ������,
	 * �N���e���groups, �Ĩ���Ū���s�դ���],
	 * ���F�קK�e�����s�ե]�t�᭱���s�լ�����
	 */
	private void loadGroupHeader(){
		this.authorizationEntity.load();
		//���o�s�ո��
		String []groupList = this.authorizationEntity.getGroupList();
		//�N�Ҧ��s�շs�W��groups
		for( int i = 0 ; i <groupList.length ; i++ ){
			this.addGroup(groupList[i]);
		}
		//�N�Ҧ��s�զ����s�W��s��
		for( int i = 0 ; i <groupList.length ; i++ ){
			//���o�s��
			IGroup group = findGroup(groupList[i]);
			//Ū���Ҧ��s�զ����C��
			String []groupMembers = this.authorizationEntity.getGroupMemberList(group.getName());
			
			//���s�զ���
			if( groupMembers != null ){
			
				//�NŪ�����s�զ����C��[��s�դ�
				for( int j = 0 ; j < groupMembers.length ; j++ ){
					//�s�զ������@�Ӹs��
					if( groupMembers[j].startsWith("@") ){
						this.addGroupMember(group.getName(), groupMembers[j].substring(1), "group");
					}//�s�զ������@�ӨϥΪ�
					else { 
						this.addGroupMember(group.getName(), groupMembers[j], "account");
					}
				}
			}
		}
	}
	
	/**
	 * ���o�ɮפ����v���������e, �]�t���|�P���|����,
	 * �Ψ��v��
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
				//�bSVN��, *�N��������H
				} else if( name.equals("*") ){
					type = "group";
				}
				
				this.addPermission(pathList[i], name,type , permission);
			}
		}
	}
	
	/**
	 * �N�Ҧ��s�ո�T�]�m��authorizationEntity
	 */
	private void saveGroupHeader(){
		//���o�{�����s�զC��
		List<String> groupList = this.getStringGroupList();
		//�]�m�s�դ��e
		this.authorizationEntity.setGroupList(groupList);
		//�]�m�s�զ������e
		for( int i = 0 ; groupList != null && i < groupList.size() ; i++ ){
			String groupName = groupList.get(i);
			//���o�s�զ����C��
			List<String> memberList = this.getStringMemberList(groupName);
			//�]�m�s�զ������e
			this.authorizationEntity.setGroupMember(groupName, memberList);
		}
	}
	
	/**
	 * �N�Ҧ��v����T, �]�t���|�P���|�����P�v��,
	 * �]�m��authorizationEntity
	 */
	private void savePermission(){
		Collection<RepositoryPath> pathList = this.path.values();
		Iterator<RepositoryPath> iter = pathList.iterator();
		
		//��U���|���o�䦨���P�v���ó]�m��authorizationEntity
		while( iter.hasNext() ){
			RepositoryPath rp = iter.next();
			//���|�W��
			String pathName = rp.getPathName();
			//�����C��
			List<String> memberList = this.getPathMemberList(pathName);
			List<String> permissionList = new ArrayList<String>();
			//���o�{�����Ҧ��s�զ����Ψ��v��,�M��]�m��authorizationEntity
			for( int i = 0 ; i < memberList.size() ; i++){
				String name = memberList.get(i);
				String type = "account";
				//�������@�Ӹs�իh�u���W��@���᪺
				if( name.startsWith("@") ){
					name = name.substring(1);
					type = "group";
				}
				//���o�������v��
				String permission = this.getPermission(pathName, name, type);
				permissionList.add(permission);
			}
			
			//�]�m���|���v����T
			this.authorizationEntity.setPermissionData(pathName, memberList, permissionList);
		}
		
	}
	
	/**
	 * Ū���v���ɮת����
	 * 
	 * @param name
	 * @return
	 */
	public boolean load(String name){
		this.authorizationFile = new File(name);

		if( this.authorizationFile.exists() ){

			this.authorizationEntity = new AuthorizationEntity(authorizationFile);
			//Ū���s�ո�T
			this.loadGroupHeader();
			//Ū���v����T
			this.loadPermission();
			return true;
		}
		return false;
	}
	
	/**
	 * �N�Ҧ��v���]�w�ɸ�Ʀs�J
	 * @return
	 */
	public boolean save(){
		if( this.authorizationFile.exists() && this.authorizationEntity != null ){
			//�]�m�s�ո�T
			this.saveGroupHeader();
			//�]�m�v����T
			this.savePermission();
			//�i���x�s
			this.authorizationEntity.save();
			return true;
		}
		return false;
	}
	
	/**
	 * �s�W�s��, ���ƪ��L�k�[�J, �B�^��false
	 * 
	 * @param name �ҭn�s�W���s�զW�� 
	 * 
	 * @return �Y�O�s�W���\�h�^��true, �Ϥ��hfalse
	 */
	public boolean addGroup(String name){
		
		//�ťզr��
		name.trim();
		if( name.equals("") )
			return false;
		
		//���o�W�٬�name���s��
		IGroup igroup = this.findGroup(name);
		//�Y���s�դ��s�b, �h�s�W�@�Ө�groups
		if( igroup == null ){
			
			//�s�W�@�ӦW�٬�name���s��
			igroup = new Group();
			igroup.setName(name);
			//�[�J��igroup
			this.groups.put(name,igroup);
			
			return true;
		}
		//�s�դw�s�b
		return false;
	}
	
	/**
	 * �ק�s�զW��, �L�k�ק令�w�s�b��
	 * 
	 * @param oldname �n�ק諸�s�զW��
	 * @param newname �ק蠟�᪺�s�զW��
	 * @return
	 */
	public boolean modifyGroupName(String oldname, String newname){
		
		//�ťզr��
		newname.trim();
		if( newname.equals("") )
			return false;
		
		//���o�¸s��
		IGroup oldGroup = this.groups.get(oldname);
		//�ҭn�ק蠟�s�զW�٤w�s�b or �n�ק蠟�s�դ��s�b
		if( this.groups.containsKey(newname) || oldGroup == null ){
			return false;
		}
		
		//�ק�s�զW��
		//�ѩ�key����, �ҭn�����쥻���s��, �M��A�[�J
		this.groups.remove(oldname);
		oldGroup.setName(newname);
		this.groups.put(newname, oldGroup);
		
		
		//�Y���s�լ��䥦�s�զ���, ��n�ק�W��
		//���o�Ҧ��s��
		Collection<IGroup> allgroups = this.groups.values();
		
		//�ק�Ҧ��s�ժ��W�٬�oldname���s�զ����W�٬�newname
		Iterator<IGroup> iter = allgroups.iterator();
		
		while( iter.hasNext() ){
			//���o�n�ק蠟�s�զ���
			IGroup modifyGroup = iter.next().findGroupMember(oldname, GroupMemberType.group);
			//�ק�s�զ������W��
			if( modifyGroup != null ){ //�O�n�ק諸�s��
				modifyGroup.setName(newname);
			}
		}
		
		//�ק惡�s�զb�v���]�w���W��
		
		//�g�J�ɮ�
		
		return true;
	}
	
	/**
	 * �R���s��
	 * 
	 * @param name �n�R���s�դ��W��
	 * @return �R�����\�h�^��true, �Ϥ��h��false
	 */
	public boolean deleteGroup(String name){
		//�n�R�����s�դ��s�b
		if(  !this.groups.containsKey(name) ){
			return false;
		}
		
		//�R���W�٬�name���s��
		this.groups.remove(name);
		
		//�Y���s�լ��䥦�s�զ���, ��n�R��
		//���o�Ҧ��s��
		Collection<IGroup> allgroups = this.groups.values();

		//�R���W�٬�name���s�զ���
		Iterator<IGroup> iter = allgroups.iterator();

		while( iter.hasNext() ){
			//�R���W�٬�name,�B�O�s�ժ��s�զ���
			iter.next().deleteGroupMember(name, "group");
		}
		
		//�R���b�v���]�w�����s��
		List<String> pathList = this.getPermissionPathList();
		Iterator<String> iter2 = pathList.iterator();
		while( iter2.hasNext() ){
			//�R���n�R���s�ժ��v�����
			String path = iter2.next();
			this.deletePermission(path, name, "group");
		}
		
		return true;
	}
	
	/**
	 * ���o�Ҧ��s�զW��
	 * 
	 * @return �s�զC��
	 */
	public List<String> getStringGroupList(){
		
		if( this.groups.size() == 0 ){
			return null;
		}
		
		List<String> groupList = new ArrayList<String>();
		
		//���o�Ҧ��s��
		Collection<IGroup> allgroups = this.groups.values();
		
		Iterator<IGroup> iter = allgroups.iterator();
		//�N�s�զW�٩�JgroupList
		while( iter.hasNext() ){
			groupList.add(iter.next().getName());
		}
		
		return groupList;
	}
	
	/**
	 * ���o�Ҧ��s��
	 * 
	 * @return �s�զC��
	 */
	public List<IGroup> getGroupList(){
		
		if( this.groups.size() == 0 ){
			return null;
		}
		
		List<IGroup> groupList = new ArrayList<IGroup>();
		
		//���o�Ҧ��s��
		Collection<IGroup> allgroups = this.groups.values();
		
		Iterator<IGroup> iter = allgroups.iterator();
		//�N�s�զW�٩�JgroupList
		while( iter.hasNext() ){
			groupList.add(iter.next());
		}
		
		return groupList;
	}

	/**
	 * ��M�s��
	 * 
	 * @param name �n��M���s�զW��
	 * @return �����h�^�Ǹs��, �_�h�Nnull
	 */
	public IGroup findGroup(String name){
		return this.groups.get(name);
	}
	
	/**
	 * �s�W�s�զ���
	 * 
	 * @param groupName �n�[�J���s��
	 * @param memberName �ҷs�W�����W��
	 * @param type �������A
	 * @return ���\�h�^��true, ���ѫh�^��false
	 */
	public boolean addGroupMember(String groupName, String memberName, String type){
		//�n�[�J���s��
		IGroup group = findGroup(groupName);
		//�s�դ��s�b
		if( group == null ){
			return false; 
		}

		//�ҭn�[�J������
		IGroup member = null;
		
		//�ҥ[�J���������ϥΪ�
		if( type.equals("account") ){
			member = new User();
			member.setName(memberName);
		//�ҥ[�J���������s��
		} else if( type.equals("group") ){
			member = findGroup(memberName);
		}
		
		//�ҭn�[�J���������s�b
		if( member == null){
			return false;
		}
		
		//�s�W����
		return group.addGroupMember(member);
	}
	
	/**
	 * �R���s�զ���
	 * 
	 * @param groupName
	 * @param memberName
	 * @param type
	 * @return
	 */
	public boolean deleteGroupMember(String groupName, String memberName, String type){
		//�n�R�����s��
		IGroup group = findGroup(groupName);
		//�s�դ��s�b
		if( group == null ){
			return false; 
		}

		return group.deleteGroupMember(memberName, type);
	}
	
	/**
	 * ���o�s�զ���
	 * 
	 * @param groupName �n���o���s��
	 * @return �s�դ�����
	 */
	public List<String> getStringMemberList(String groupName){
		IGroup group = this.findGroup(groupName);
		//�s�դ��s�b
		if( group == null ){
			return null;
		}
		
		List<IGroup> members = group.getGroupMembers();
		List<String> membersString = new ArrayList<String>();
		
		Iterator<IGroup> iter = members.iterator();
		//�Y�O�ϥΪ̪����^�ǦW��,�Y�O�s�ռW�[@�ΥH��O
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
	 * �s�W�@�Ӧs���v��
	 * @param path �ҭn�W�[�����|
	 * @param name �]�w�������W��
	 * @param type �������A
	 * @param permission �v��
	 * @return ���\�h�^��True, �Ϥ��hfalse
	 */
	public boolean addPermission(String path, String name, String type,String permission){
		
		RepositoryPath rp = this.findRepositoryPath(path);
		//RepositoryPath���s�b, �h�s�ؤ@��
		if( rp == null ){
			rp = new RepositoryPath(path);
		}
		
		IGroup filemember = null;
		
		//�bSVN�y�z�ɤ�'*'���������H, �G��@Group
		if( name.equals("*") && type.equals("group") ){
			filemember = new Group();
			filemember.setName("*");
		//�ҭn�]�w���������ϥΪ�
		} else if( type.equals("account") ){
			filemember = new User();
			filemember.setName(name);
		//�ҭn�]�w���������s��
		} else if( type.equals("group") ){
			filemember = this.findGroup(name);
		}
		//�s�W�@�Ӧs���v��
		if( rp.addPermission(filemember,permission) ){
			this.path.put(path, rp);
			return true;
		} else
			return false;
	}
	
	/**
	 * �R���Y���|�U�������s���v��
	 * @param path �ҭn�R�����������|
	 * @param name �R�������W��
	 * @param type �R���������A
	 * @return ���\�h�^��True, �Ϥ��hfalse
	 */
	public boolean deletePermission(String path, String name, String type){
		RepositoryPath rp = this.findRepositoryPath(path);
		//RepositoryPath���s�b, �h�L�k�R��
		if( rp == null ){
			return false;
		}
		
		IGroup filemember = null;
		//�ҭn�R�����������ϥΪ�
		if( type.equals("account") ){
			filemember = new User();
			filemember.setName(name);
		//�ҭn�R�����������s��
		} else if( type.equals("group") ){
			filemember = new Group();
			filemember.setName(name);
		}
		
		return rp.removePermission(filemember);
	}
	
	/**
	 * �ק���|�U�s�դ��v��
	 * @param path �n�ק��v�������|
	 * @param name �n�ק諸�����W��
	 * @param type ���������A
	 * @param permission �n�ק諸�v��
	 * @return
	 */
	public boolean modifyPermission(String path, String name, String type,String permission){
		RepositoryPath rp = this.findRepositoryPath(path);
		//RepositoryPath���s�b�h�L�k�ק�
		if( rp == null ){
			return false;
		}

		IGroup filemember = null;
		//�ҭn�]�w���������ϥΪ�
		if( type.equals("account") ){
			filemember = new User();
			filemember.setName(name);
		//�ҭn�]�w���������s��
		} else if( type.equals("group") ){
			//�Y�W�٬�*�N��Ҧ����s�թάO�H, �N����s�ըϥ�
			if( name.equals("*") ){
				filemember = new Group();
				filemember.setName("*");
			} else {
				filemember = this.findGroup(name);
			}
		}
		//�ק��v���v��
		return rp.modifyPermission(filemember, permission);
	}
	
	/**
	 * ��MRepositoryPath
	 * 
	 * @param path �ҭn��M�����|
	 * @return ��M�쪺�F��
	 */
	public RepositoryPath findRepositoryPath(String path){
		return this.path.get(path);
	}
	
	/**
	 * �ھڸ��|���o��U�������C��
	 * @param path �n���o�C�����|
	 * @return �����C��
	 */
	public List<String> getPathMemberList(String path){
		RepositoryPath rp = findRepositoryPath(path);	
		if( rp == null ){
			return null;
		}
		return rp.getStringMemberList();
	}
	
	/**
	 * ���o���|path�U�W�٬�name�������v��
	 * @param path ���|�W��
	 * @param name �����W��
	 * @param type �������A,��account or group
	 * @return �����v��, ��"r","rw","w",""
	 */
	public String getPermission(String path,String name,String type){
		//���oPath
		RepositoryPath rp = findRepositoryPath(path);	
		if( rp == null ){
			return null;
		}
		//�]�m������rp��M�v��
		IGroup member = null;
		if( type.equals("group") ){
			member = new Group();
		} else if( type.equals("account") ){
			member = new User();
		}
		member.setName(name);
		//���o�v��
		return rp.getPermission(member);
	}
	
	/**
	 * ���o�Ҧ��v���y�z�ɤ������|�C��
	 * @return
	 */
	public List<String> getPermissionPathList(){
		List<String> pathlist = new ArrayList<String>();
		pathlist.addAll(this.path.keySet());
		return pathlist;
	}
	
	/**
	 * ���o�s�ժ��ƶq
	 * 
	 * @return �s�ռƶq
	 */
	public int getGroupSize(){
		return this.groups.size();
	}
	
}
