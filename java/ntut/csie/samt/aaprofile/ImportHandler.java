package ntut.csie.samt.aaprofile;

import java.io.File;
import java.util.List;

import ntut.csie.samt.aaprofile.importlog.AccountImportLogger;
import ntut.csie.samt.aaprofile.importlog.AccountLog;
import ntut.csie.samt.aaprofile.importlog.FolderImportLog;
import ntut.csie.samt.aaprofile.importlog.FolderLog;
import ntut.csie.samt.aaprofile.importlog.GroupImportLog;
import ntut.csie.samt.aaprofile.importlog.GroupLog;
import ntut.csie.samt.aaprofile.importlog.GroupMemberImportLog;
import ntut.csie.samt.aaprofile.importlog.GroupMemberLog;
import ntut.csie.samt.aaprofile.importlog.ImportLogger;
import ntut.csie.samt.aaprofile.importlog.Log;
import ntut.csie.samt.aaprofile.importlog.PermissionImportLog;
import ntut.csie.samt.aaprofile.importlog.PermissionLog;
import ntut.csie.samt.aaprofile.importlog.logStatus;
import ntut.csie.samt.manager.*;

public class ImportHandler {

	private AAProfileParser parser;
	private AAManager aaManager;
	
	/**
	 * ���AAManager�OSingleton��,
	 * �o�˼g���Touch my Heart
	 * @param aaManager
	 */
	public ImportHandler(AAManager aaManager){
		this.aaManager = aaManager;
	}
	
	/**
	 * �ھڶפJ�ɮ�,�إ�JAXB binding����
	 * @param file
	 */
	public void importProfile(File file){
		this.parser = new AAProfileParser(file);
	}
	
	/**
	 * �N�qAAProfile���o���b���O����b���]�w��
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importAccounts(){
		int flag = 2; //�ΨӦ^���פJ���G
		
		ImportLogger importLog = new AccountImportLogger();
		//�q�ɮפ����o�b���C��
		List<String> accountList = this.parser.getAccountIdList();
		List<String> passwdList = this.parser.getAccountPasswdList();

		//�N�qProfile�����o���b���C��s�W��authenticationFile
		for( int i = 0 ; accountList != null && i < accountList.size() ; i++){
			String id = accountList.get(i);
			String passwd = passwdList.get(i);
			
			//�ھڷs�W���G���ͤ��P��Log
			logStatus status = null;
			try{
				//�i��s�W�B�s�W���\
				if( this.aaManager.createAccount(id, passwd)){
					status = logStatus.Success;
				//Skip
				} else {
					if( flag != 0 ) flag = 1;
					status = logStatus.Warning;
				}
			//�����~�o��
			}catch(Exception e){
				status = logStatus.Failure;
				flag = 0;
			}
			//�s�Wlog��log�ɤ�
			Log log = new AccountLog(id,status);
			importLog.addLog(log);
		}
		importLog.save();
		return flag;
	}
	
	/**
	 * �N�qAAProfile���o���s�զC���T�O�����v���]�w
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importGroups(){
		int flag = 2;
		ImportLogger importLog = new GroupImportLog();
		//�q�ɮפ����o���s�զC��
		List<String> groupList = this.parser.getGroupList();
		
		//�N�qProfile�����o���s�զC���T�s�W��authorizationFile
		for( int i = 0 ; groupList != null && i < groupList.size() ; i++ ){
			String name =  groupList.get(i);
			//�ھڷs�W���G���ͤ��P��Log
			logStatus status = null;
			try{
				//�i��s�W�B�s�W���\
				if( this.aaManager.addGroup(name) ){
					status = logStatus.Success;
				//Skip
				} else {
					if( flag != 0 ) flag = 1;
					status = logStatus.Warning;
				}
			//�����~�o��
			}catch(Exception e){
				status = logStatus.Failure;
				flag = 0;
			}
			
			//�s�Wlog��log�ɤ�
			Log log = new GroupLog(name,status);
			importLog.addLog(log);
		}
		importLog.save();
		return flag;
	}
	
	/**
	 * �N�qAAProfile���o���s�զ����C���T�O�����v���]�w
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importGroupMember(){
		int flag = 2;
		ImportLogger importLog = new GroupMemberImportLog();
		List<String> groupList = this.parser.getGroupList();
		//�@�@���o�s�զW��, �M����o�U�s�զ����[�J���v���]�w
		for( int i = 0 ; groupList != null && i < groupList.size() ; i++ ){
			//�s�զW��
			String groupName = groupList.get(i);
			//���o�s�զ���
			List<String> memberList = this.parser.getGroupMemberList(groupName);
			//�N�s�զ����[�J���v���]�w
			for( int j = 0 ; memberList != null && j < memberList.size() ; j++ ){
				//�����W�ٻP���A
				String memberName = memberList.get(j);
				String type = "account";
				//�HSVN�v���]�w�ɮ榡,�Y�������s�շ|�H@�}�Y
				if( memberName.startsWith("@") ){
					memberName = memberName.substring(1);
					type = "group";
				}
				
				//�ھڷs�W���G���ͤ��P��Log
				logStatus status = null;
				try{
					//�i��s�W�s�զ������v���]�w�B�s�W���\
					if( this.aaManager.addGroupMember(groupName, memberName, type) ){
						status = logStatus.Success;
					//Skip
					} else {
						if( flag != 0 ) flag = 1;
						status = logStatus.Warning;
					}
				//�����~�o��
				}catch(Exception e){
					status = logStatus.Failure;
					flag = 0;
				}
				
				//�s�Wlog��log�ɤ�
				Log log = new GroupMemberLog(groupName, memberName, type, status);
				importLog.addLog(log);
				
			}
		}
		importLog.save();
		return flag;
	}
	
	/**
	 * �N�qAAProfile���o���v����T�O�����v���]�w
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importPermissions(){
		int flag = 2;
		ImportLogger importLog = new PermissionImportLog();
		
		List<String> pathList = this.parser.getRepositoryPath();
		
		for( int i = 0 ; pathList != null && i < pathList.size() ; i++ ){
			String path = pathList.get(i);
			List<String> pathMember = this.parser.getPathMember(path);
			for( int j = 0 ; pathMember != null && j < pathMember.size() ; j++ ){
				//�����W�١B���A�B�v��
				String name = pathMember.get(j);
				String type = "account";
				String permission = this.parser.getPermission(path, name);
				//�HSVN�v���]�w�ɮ榡,�Y�������s�շ|�H@�}�Y
				if( name.startsWith("@") ){
					name = name.substring(1);
					type = "group";
				}
				
				//�ھڷs�W���G���ͤ��P��Log
				logStatus status = null;
				try{
					//�i��s�W�v���B�s�W���\
					if( this.aaManager.addPermission(path, name, type, permission) ){
						status = logStatus.Success;
					//Skip
					} else {
						if( flag != 0 ) flag = 1;
						status = logStatus.Warning;
					}
				//�����~�o��
				}catch(Exception e){
					status = logStatus.Failure;
					flag = 0;
				}
				//�s�Wlog��log�ɤ�
				Log log = new PermissionLog(path, name, type, status);
				importLog.addLog(log);
			}
		}
		importLog.save();
		return flag;
	}
	
	/**
	 * �N�qAAProfile���o���إ�SVN��ƦC��إߨ�SVN�W
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importSVNFolder(){
		int flag = 2;
		ImportLogger importLog = new FolderImportLog();
		
		List<String> folderList =  this.parser.getAllFolderList();
		
		for( int i = 0 ; folderList != null && i < folderList.size() ; i++ ){
			String path = folderList.get(i);

			logStatus status = null;
			//�s�b�h�s�Wskip��log
			if( this.aaManager.isPathExist(path)){
				flag = 1;
				status = logStatus.Warning; 
			//���s�b�h�s�W�i�[�J��log
			} else {
				status = logStatus.Success;
			}
			
			Log log = new FolderLog(path,status);
			importLog.addLog(log);
		}
		
		try{
			//�إ�SVN��Ƨ�
			this.aaManager.createDir(folderList);
			importLog.save();
			return flag;
		//�o��Exception�h��פJ����
		} catch ( Exception e){
			e.printStackTrace();
			
			importLog = new FolderImportLog();
			//�Ҧ����|�s�W����
			for( int i = 0 ; folderList != null && i < folderList.size() ; i++ ){
				String path = folderList.get(i);
				Log log = new FolderLog(path,logStatus.Failure);
				importLog.addLog(log);
			}			
			importLog.save();
			return 0;
		}
	}
}
