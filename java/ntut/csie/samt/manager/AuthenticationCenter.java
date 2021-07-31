package ntut.csie.samt.manager;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import ntut.csie.samt.account.*;

public class AuthenticationCenter {

	private List<Account> accounts;
	private IAuthenticationEntity authenticationEntity;
	private File authenticationFile;
	private String protocol;
	
	/**
	 * �غc��
	 * 
	 */
	public AuthenticationCenter(){
		this.accounts = new ArrayList<Account>();
		this.authenticationFile = null;
	}
	
	public void setProtocol(String protocol){
		this.protocol = protocol;
	}
	
	/**
	 * Ū���b���{�Ҵy�z�ɮ�
	 * @param name �ɮת���m
	 * @return
	 */
	public boolean load(String name){
		this.authenticationFile = new File(name);
		//�ɮצs�b
		if( this.authenticationFile.exists() ){
			//svn�ϥ�http protocol�h�ϥ�apache�{���ɮ׮榡 
			if( this.protocol.equals("http") ){
				this.authenticationEntity = new ApacheAuthenticationEntity(this.authenticationFile);
				
			//svn�ϥ�svn server protocol�h�ϥ�svn�{���ɮ׮榡
			} else if( this.protocol.equals("svn") ){
				this.authenticationEntity = new SVNAuthenticationEntity(this.authenticationFile);
			}
			
			//�q�ɮפ�Ū���Ҧ����b����T
			this.loadAccount();
			return true;
		}
		return false;
	}
	
	/**
	 * �N�v����T�s�J�ɮפ�
	 */
	public void save(){
		this.authenticationEntity.save();
	}
	
	/**
	 * �q�ɮפ�Ū���Ҧ����b����T
	 */
	private void loadAccount(){
		this.accounts.clear();
		//Ū���ɮפ��e
		this.authenticationEntity.load();
		//���o�ɮ״y�z���b����T
		List<String> accountList = this.authenticationEntity.getAccountList();

		//�إߩҦ��ɮפ��y�z���b����ccounts
		for( int i = 0 ; accountList != null && i < accountList.size() ; i++ ){
			/*
			 * �N�b����T���accounts
			 * ���ϥ�createAccount�O�ѩ�createAccount�|�W�[Entity���e
			 * �b�o�̥ثe���s��password���,�]��apache server�ϥ�md5�[�K
			 * �s��èS����ڷN�q, ����svn server���ݭn�B�z�K�X�A�g
			 */
			Account account = new Account();
			account.setName(accountList.get(i));
			account.setPassword("");
			this.accounts.add(account);
		}
	}
	
	/**
	 * �R���b��
	 * @param id �n�R�����b��ID
	 * @return
	 */
	public boolean deleteAccount(String id){
		//��M�b���O�_�s�b, �s�b�h�i��R��
		Account account = this.findAccount(id);
		if( account != null ){
			//�i��R��
			this.accounts.remove(account);
			//�ק�Entity Data����
			this.authenticationEntity.deleteAccount(id);
			return true;
		}
		return false;
	}
	
	/**
	 * �إ߷s���b��
	 * @param id �b���W��
	 * @param passwd �K�X
	 * @return ���\�h�^��true, �Ϥ��hfalse
	 */
	public boolean createAccount(String id, String passwd){
		passwd = passwd.trim();
		//��M�b���O�_�s�b, �s�b�h���[�J, �Y�S��J�K�X�]�O
		if( this.findAccount(id) != null || passwd == null || passwd.isEmpty() ){
			return false;
		}
		//�إ߱b��
		Account account = new Account();
		account.setName(id);
		account.setPassword(passwd);
		
		this.accounts.add(account);
		//�ק�Entity Data����
		this.authenticationEntity.createAccount(id, passwd);
		return true;
	}
	
	/**
	 * Apache Server���X�Ӫ��K�X�|�g�L�[�K
	 */
	
	/**
	 * �ק�b�����W�ٻP�K�X
	 * @param id
	 * @param newid
	 * @param newpasswd
	 * @return
	 */
	public boolean modifyAccount(String id,String newid, String newpasswd){
		//��M�n�ק諸�b��
		Account account =  this.findAccount(id);

		//�Y�n�ק蠟�b�����s�b�άO�ק蠟�s�b���W�٤w�s�b(���F�ۦP),�h���i��ק�
		if( account == null || ( this.findAccount(newid) != null && !id.equals(newid) ) ){
			return false;
		}
		//�i��ק�ʧ@
		
		//���n�ק�b���hid�Mnewid�|���P�N�i��ק�
		if( !id.equals(newid) ){
			account.setName(newid);
			//�ק��ɮ׳������
			this.authenticationEntity.modifyAccountId(id, newid);
		}
		//�D�ūh�N��n�ק�K�X
		if( newpasswd != null && !newpasswd.isEmpty() ){
			account.setPassword(newpasswd);
			this.authenticationEntity.modifyAccountPasswd(id, newpasswd);
		}

		//�^�ǭק令�\���T��
		return true;
	}
	
	/**
	 * �̷�id��M�b��
	 * @param id �n��M�b����id
	 * @return ��M�쪺�b��
	 */
	public Account findAccount(String id){
		Account account  = null;
		//��M�۹������b��
		for( int i = 0 ; i < this.accounts.size() ;i ++ ){
			//���o�b��
			account = this.accounts.get(i);
			//���hbreak�M��^��
			if( account.getId().equals(id) ){
				break;
			}
			//�S���account�]��null
			account = null;
		}
		
		//�^�ǧ�쪺�b��
		return account;
	}
	
	/**
	 * ���o�b��ID�C��
	 * @return
	 */
	public List<String> getAccountIdList(){
		List<String> accountList = new ArrayList<String>();
		//�qAccounts�����o�b��ID���accountList
		for( int i = 0 ; i < this.accounts.size() ; i++ ){
			Account account = this.accounts.get(i);
			accountList.add(account.getId());
		}
		//�Y�S���b���s�b�h�]��null
		if( accountList.size() == 0 ){
			accountList = null;
		}
		//�^�Ǳb��ID�C��
		return accountList;
	}
	
}
