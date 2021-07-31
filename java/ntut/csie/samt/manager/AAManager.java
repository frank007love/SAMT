package ntut.csie.samt.manager;

import java.util.List;
import java.io.File;

import org.tmatesoft.svn.core.SVNException;

import ntut.csie.samt.aaprofile.ImportHandler;
import ntut.csie.samt.svncontroll.SVNRepositoryController;

public class AAManager {

	private AuthorizationCenter authorizationCenter = null;
	private AuthenticationCenter authenticationCenter = null;
	private static AAManager instance = null;
	private SAMTProperties properties;
	private ImportHandler importHandler;
	private SVNRepositoryController svnController;
	private boolean writable = true;
	
	/**
	 * �غc��
	 */
	protected AAManager(){
		//Ū���v��������T
		this.authorizationCenter  = new AuthorizationCenter();
		//Ū���պA�]�w��
		this.properties = new SAMTProperties();
		//Ū���{�Ҭ�����T
		this.authenticationCenter = new AuthenticationCenter();
		this.authenticationCenter.setProtocol(this.properties.getServerProtocol());
		
		//Ū����T�y�z��
		this.authorizationCenter.load(this.properties.getAuthorizationPath());
		this.authenticationCenter.load(this.properties.getAuthenticationPath());
		
		//�]�wSVN�s�u���
		this.svnController = new SVNRepositoryController();
		this.svnController.setAccount(this.properties.getManagerAccount());
		this.svnController.setPasswd(this.properties.getManagerPasswd());
		this.svnController.setSVNURL(this.properties.getURL());
		this.svnController.connection();
	}
	
    public static AAManager getInstance() {
        if (instance == null) {
            instance = new AAManager();
        }
        return instance;
    } 
    
    public static void Release() {
        instance = null;
    } 
	
    //�v���ɮ׻P�{���ɮ��x�s����
    
    /**
     * �N��T�x�s��AuthorizationFile
     */
    public void saveAuthorizationFile(){
    	if( writable ){
    		this.authorizationCenter.save();
    	}
    }
    
    /**
     * �N��T�x�s��AuthenticationFile
     */
    public void saveAuthenticationFile(){
    	if( writable ){
    		this.authenticationCenter.save();
    	}
    }
    
    /**
     * �x�s�ɮ׷|�ھڳo��Flag�M�w�O�_�n�x�s,
     * �D�n�Ω��קKimport�����פJ,
     * ��������Ҧ��B�J�������~�i��פJ
     * @param writable
     */
    public void setWritable(boolean writable){
    	this.writable = writable;
    }
    
    /**
     * ���o�O�_�{�b�i�H�i��g�J�ɮװT��
     * @return
     */
    public boolean isWritable(){
    	return this.writable;
    }
    
    //Normal Operation
    
	/**
	 * �s�W�@�ӦW�٬�name���s��
	 * 
	 * @param name �s�s�զW��
	 * @return ���\�h�^��true, �Ϥ�false
	 */
	public boolean addGroup(String name){
		boolean result = this.authorizationCenter.addGroup(name);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * �R���@�ӦW�٬�name���s��
	 * 
	 * @param name �R���s�զW��
	 * @return ���\�h�^��true, �Ϥ�false
	 */
	public boolean deleteGroup(String name){
		//�R���̫�@�Ӹs�շ|����
		boolean result = this.authorizationCenter.deleteGroup(name);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * �ק�s�զW��
	 * 
	 * @param oldname �쥻���W��
	 * @param newName �s���W��
	 * @return ���\�h�^��true, �Ϥ�false
	 */
	public boolean modifyGroupName(String oldname,String newname){
		boolean result = authorizationCenter.modifyGroupName(oldname, newname);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * ���o�s�զC��
	 * 
	 * @return �s�զC��
	 */
	public List<String> listAllGroup(){
		return this.authorizationCenter.getStringGroupList();
	}
	
	/**
	 * �s�W�@�Ӹs�զ���
	 * 
	 * @param groupName �n�[�J���s��
	 * @param mebmerName �s�զ����W��
	 * @param type �s�զ������A, "group" or "user"
	 * @return �[�J���\�h�^��true, �_�h�Ofalse
	 */
	public boolean addGroupMember(String groupName, String memberName, String type){
		boolean result = false;
		//�T�{�ϥΪ̬O�_�s�b, ���s�b�h���[�J
		if( type.equals("account") ){
			//account���s�b�h���[�J
			if( this.authenticationCenter.findAccount(memberName) == null){
				return result;
			}
			result =  this.authorizationCenter.addGroupMember(groupName, memberName, type);
			this.saveAuthorizationFile();
			return result;
		}
		
		//�s�W�s�զ���, type���@�Ӹs��
		result =  this.authorizationCenter.addGroupMember(groupName, memberName, type);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * �R���s�զ���
	 * 
	 * @param groupName �s�զW��
	 * @param memberName �����W��
	 * @param type �s�զ������A, "group" or "user"
	 * @return �[�J���\�h�^��true, �_�h�Ofalse
	 */
	public boolean deleteGroupMember(String groupName, String memberName, String type){		
		boolean result = this.authorizationCenter.deleteGroupMember(groupName, memberName, type);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * �C�X�Y�s�ժ�����
	 * 
	 * @param groupName �n��M���s�զW��
	 * @return ���s�դ��s�զ���
	 */
	public List<String> listGroupMember(String groupName){
		return this.authorizationCenter.getStringMemberList(groupName);
	}
	
	/**
	 * ��M�s��
	 * 
	 * @param name �n��M���s�զW��
	 * @return �����h�^�Ǹs��, �_�h�Nnull
	 */
	public boolean findGroup(String name){
		return this.authorizationCenter.findGroup(name) != null;
	}
	
	/**
	 * �s�W�s�v��,���P�_�ϥΪ̬O�_�s�b
	 * 
	 * @param path �n�s�W���u�����|
	 * @param name �s�W�����W��
	 * @param type �������A
	 * @param permission �v��
	 * @return ���\�htrue,���ѫhfalse
	 */
	public boolean addPermission(String path, String membername,String membertype, String permission){
		boolean result = false;
		//�Ytype��account,�h�浹AuthenticationCenter�P�_�ϥΪ̬O�_�s�b,
		//�Ytype��group,�h��U�h��AuthorizationCenter�M�w�O�_�n�[�J
		if( membertype.equals("account") ){
			//account���s�b�h���[�J
			if( this.authenticationCenter.findAccount(membername) == null){
				return result;
			}
			result =  this.authorizationCenter.addPermission(path, membername, membertype, permission);
		} else if( membertype.equals("group") ){
			result = this.authorizationCenter.addPermission(path, membername, membertype, permission);
		}
		
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * �ק��v��
	 * 
	 * @param path �n�ק��v�������|
	 * @param membername �n�ק諸����
	 * @param membertype �������A
	 * @param permission �ק諸�v��
	 * @return ���\�htrue,���ѫhfalse
	 */
	public boolean modifyPermission(String path, String membername, String membertype, String permission){
		boolean result = this.authorizationCenter.modifyPermission(path, membername, membertype, permission);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * �R���v�����
	 * 
	 * @param path �n�R���v�������|
	 * @param membername �n�R��������
	 * @param membertype �������A
	 * @return ���\�htrue,���ѫhfalse
	 */
	public boolean deletePermission(String path, String membername, String membertype){
		boolean result = this.authorizationCenter.deletePermission(path, membername, membertype);
		this.saveAuthorizationFile();
		return result;
	}
	
	/**
	 * �ھڸ��|���o��U�������C��
	 * 
	 * @param path �n���o�C�����|
	 * @return �����C��
	 */
	public List<String> getPathMemberList(String path){
		return this.authorizationCenter.getPathMemberList(path);
	}
	
	/**
	 * �ھڸ��|���o��U�������v��
	 * 
	 * @param path ���|�W��
	 * @param name �����W��
	 * @param type �������A
	 * @return �����v��
	 */
	public String getPermission(String path,String name,String type){
		return this.authorizationCenter.getPermission(path, name, type);
	}
	
	//�b���{�Ҭ����禡
	
	/**
	 * �C�X�Ҧ��b����ID
	 */
	public List<String> listAllAccount(){
		return this.authenticationCenter.getAccountIdList();
	}
	
	/**
	 * �P�_�b���O�_�w�Q�إ�
	 * @param id �n�P�_���b��id
	 * @return
	 */
	public boolean isAccountExist(String id){
		//�n��M���b��id���s�b
		if( this.authenticationCenter.findAccount(id) == null )
			return false;
		//�n��M���b��id�s�b
		return true;
	}
	
	/**
	 * �إ߷s���b��
	 * @param id
	 * @param passwd
	 * @return
	 */
	public boolean createAccount(String id,String passwd){
		boolean result = this.authenticationCenter.createAccount(id, passwd);
		this.saveAuthenticationFile();
		return result;
	}
	
	/**
	 * �R���b��
	 * @param id
	 * @return
	 */
	public boolean deleteAccount(String id){
		boolean result = this.authenticationCenter.deleteAccount(id);
		this.saveAuthenticationFile();
		return result;
	}
	
	/**
	 * �ק�b����ID�P�K�X
	 * @param oldId �±b��ID
	 * @param newId �s�b��ID
	 * @param newPasswd �s�K�X
	 * @return
	 */
	public boolean modifyAccount(String oldId,String newId,String newPasswd){
		boolean result = this.authenticationCenter.modifyAccount(oldId, newId, newPasswd);
		//�h�ק��v���������b���W�ٱN�ª��令�s���W��
		this.updateAuthorizationAccountName(oldId, newId);
		//�g�J�ɮ�
		this.saveAuthorizationFile();
		this.saveAuthenticationFile();
		return result;
	}
	
	/**
	 * �h��s�s�զ�������Account�W��,
	 * �NoldId�令newId
	 * @param oldId �쥻�W��
	 * @param newId �s�W��
	 */
	private void updateAuthorizationAccountName(String oldId,String newId){
		List<String> groupList = this.authorizationCenter.getStringGroupList();
		//�i��ק�ʧ@
		for( int i = 0 ; groupList!= null && i < groupList.size() ; i++ ){
			String groupName = groupList.get(i);
			String type = "account";
			//���R���쥻���A�s�W�s���Y�����ק�ʧ@
			if( this.authorizationCenter.deleteGroupMember(groupName, oldId, type))
				this.authorizationCenter.addGroupMember(groupName, newId, type);
		}
	}
	
	//���o�պA�����]�w�����禡
	
	public String getAuthenticationPath(){
		return this.properties.getAuthenticationPath();
	}
	
	public String getAuthorizationPath(){
		return this.properties.getAuthorizationPath();
	}
	
	public String getURL(){
		return this.properties.getURL();
	}	
	
	//�]�w�պA�����]�w�����禡
	
	public void setAuthenticationPath(String path){
		this.properties.setAuthenticationPath(path);
	}
	
	public void setAuthorizationPath(String path){
		this.properties.setauthorizationPath(path);
	}
	
	public void setURL(String url){
		this.properties.setSVNUrl(url);
	}
	
	/**
	 * �x�s�]�w��Properties���e
	 */
	public void saveProperties(){
		this.properties.saveProperties();
	}
	
	//SVN�����禡
	
	/**
	 * ���o�Y���|�U�Ҧ����ɮשθ�Ƨ�
	 * 
	 * @param parentPath �n���o�����|
	 * @param action "DIR" ���o��Ƨ� , "FILE" ���o�ɮ�
	 */
	public List<String> getRepositorySubFile(String parentPath,String action)  throws SVNException{
		return this.svnController.getRepositorySubFile(parentPath, action);
	}
	
	/**
	 * �ھڸ��|, �إ�SVN�۹�������Ƨ�
	 * @param pathlist �n�إߪ���Ƨ��C��
	 */
	public void createDir(List<String> pathlist) throws SVNException{
		this.svnController.createDir(pathlist);
	}
	
	/**
	 * �P�_path�O�_�s�bSVN��
	 * @param path
	 * @return
	 */
	public boolean isPathExist(String path){
		return this.svnController.isPathExist(path);
	}
	
	//�פJ�ɮ� , �ץX�ɮ�
	
	/**
	 * �פJ�v���]�w��, �N�����v���B�b���P�n�إߪ���Ƨ�
	 * �]�w����
	 * ���}import�ʧ@�O���F���@�����ϥΪ̪��D�{�b���b
	 * ��import������ʧ@
	 * @param file �n�פJ���ɮ�
	 */
	public void importProfile(File file){
		if( this.importHandler == null ){
			this.importHandler = new ImportHandler(this);
			this.importHandler.importProfile(file);
		}
	}
	
	/**
	 * �N�qAAProfile���o���إ�SVN��ƦC��إߨ�SVN�W
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importSVNFolder(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importSVNFolder();
	}
	
	/**
	 * �N�qAAProfile���o���b���O����b���]�w��
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importAccounts(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importAccounts();
	}
	
	/**
	 * �N�qAAProfile���o���s�զC���T�O�����v���]�w
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importGroups(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importGroups();
	}
	
	/**
	 * �N�qAAProfile���o���s�զ����C���T�O�����v���]�w
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importGroupMember(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importGroupMember();
	}
	
	/**
	 * �N�qAAProfile���o���v����T�O�����v���]�w
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importPermissions(){
		if( importHandler == null ){
			return 0;
		}
		return this.importHandler.importPermissions();
	}
	
}
