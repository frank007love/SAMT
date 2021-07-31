package ntut.csie.samt.account;

import ntut.csie.samt.*;
import junit.framework.TestCase;
import java.io.File;
import java.util.List;

public class ApacheAuthenticationEntityTest extends TestCase {

	private IAuthenticationEntity apacheAuthenticationEntity;
	final private File loadFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd");
	final private File emptyFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd_empty");
	private File copyFile = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		this.apacheAuthenticationEntity = null;
		//�R�����եΪ��ƻs�ɮ�
		if( this.copyFile != null && this.copyFile.exists() ){
			this.copyFile.delete();
		}
	}

	/**
	 * ���ձq�b���ɮפ����o�b��ID�C��O�_���T
	 * 
	 * Case1 ���ձq���`�ɮפ����o�b��ID�O�_���T
	 * Case2 ���ձq���ɮפ����o�b��ID�O�_���T
	 */
	public void testGetAccountList(){
		//Case1 ���ձq���`�ɮפ����o�b��ID�O�_���T
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//Ū���ɮ�
		this.apacheAuthenticationEntity.load();
		//���o�b��id�C��
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		
		//Case2 ���ձq���ɮפ����o�b��ID�O�_���T
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.emptyFile);
		//Ū���ɮ�
		this.apacheAuthenticationEntity.load();
		//���o�b��id�C��
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList.size() == 0 );
	}
	
	/**
	 * ���խק�b��ID�O�_���\
	 * 
	 * Case1 �ק�s�b���b��
	 * Case2 �ק藍�s�b���b��
	 * Case3 �ק令�s�b���b��
	 */
	public void testModifyAccountId(){
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//Ū���ɮ�
		this.apacheAuthenticationEntity.load();
		
		//Case1 �ק�s�b���b��
		//�ק�b���W��
		this.apacheAuthenticationEntity.modifyAccountId("franklin", "franklin2");
		
		//���o�b��id�C��
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//���խק�b��ID�O�_���\
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		
		//Case2 �ק藍�s�b���b��
		//�ק藍�s�b�b���W��
		this.apacheAuthenticationEntity.modifyAccountId("franklin", "franklin3");
		
		//���o�b��id�C��
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//���խק�b��ID�O�_���\
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		assertTrue( accountList.indexOf("franklin3") == -1 );
		
		//Case3 �ק令�s�b���b��
		this.apacheAuthenticationEntity.modifyAccountId("bluewind", "nickhades");
		
		//���o�b��id�C��
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//���լO�_�Q�קﱼ�F
		assertTrue( accountList.indexOf("bluewind") != -1 );
	}

	/**
	 * ���խק�K�X
	 */
	public void testModifyAccountPasswd(){
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//Ū���ɮ�
		this.apacheAuthenticationEntity.load();
		
		//�i��T�{�O�_��Ū�X
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		assertTrue( accountList != null );
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		
		List<String> passwdList = this.apacheAuthenticationEntity.getAccountPasswdList();
		assertTrue( passwdList != null );
		assertTrue( "" + passwdList.size(), accountList.size() == passwdList.size() );
		
		int index = accountList.indexOf("franklin");
		String oldpasswd = passwdList.get(index);
		String newpasswd = "";
		
		//�ק�b���W��
		this.apacheAuthenticationEntity.modifyAccountPasswd("franklin", "test");
		
		//���o�b��id�C��
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		//���o�K�X�C��
		passwdList = this.apacheAuthenticationEntity.getAccountPasswdList();
		assertTrue( passwdList != null );
		assertTrue( "" + passwdList.size(), accountList.size() == passwdList.size() );
		//���o�ק諸�b�����s�K�X
		index = accountList.indexOf("franklin");
		newpasswd = passwdList.get(index);
		//�s�K�X�P�±K�X�����P
		assertFalse( oldpasswd.equals(newpasswd) );
	}
	
	/**
	 * ���շs�W�b��
	 * 
	 * Case1 �[�J�@�ӷs�b��
	 * Case2 �[�J�@�ӷs�b��,�S���K�X
	 * Case3 �[�J���ƪ��b��
	 */
	public void testCreateAccount(){
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//Ū���ɮ�
		this.apacheAuthenticationEntity.load();
		
		//Case1 �[�J�@�ӷs�b��
		this.apacheAuthenticationEntity.createAccount("user1", "password");
		//���o�b��id�C��
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 10 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		assertTrue( accountList.indexOf("user1") != -1 );
		
		//Case2 �[�J�@�ӷs�b��,�S���K�X
		this.apacheAuthenticationEntity.createAccount("user2", "");
		//���o�b��id�C��
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 11 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("user1") != -1 );
		assertTrue( accountList.indexOf("user2") != -1 );
		
		//Case3 �[�J���ƪ��b��
		this.apacheAuthenticationEntity.createAccount("franklin", "password");
		//���o�b��id�C��
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 11 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("user1") != -1 );
		assertTrue( accountList.indexOf("user2") != -1 );
	}
	
	/**
	 * ���էR���b����T
	 * 
	 * Case1 �R���Ĥ@�ӱb��Id
	 * Case2 �R���������b��Id
	 * Case3 �R���̫�@�Ӫ��b��Id
	 * Case4 �R�����s�b���b��Id
	 */
	public void testDeleteAccount(){
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.loadFile);
		//Ū���ɮ�
		this.apacheAuthenticationEntity.load();
		//���o�b��id�C��
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		
		//�i��b���R��
		
		//Case1 �R���Ĥ@��
		this.apacheAuthenticationEntity.deleteAccount("franklin");
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����K���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 8 );
		//�n�R�����b�������s�b
		assertTrue( accountList.indexOf("franklin") == -1 );
		
		//Case2 �R��������
		this.apacheAuthenticationEntity.deleteAccount("cyndi");
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����C���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 7 );
		//�n�R�����b�������s�b
		assertTrue( accountList.indexOf("cyndi") == -1 );
		
		//Case3 �R���̫�@�Ӫ�
		this.apacheAuthenticationEntity.deleteAccount("lenwind");
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//���������b����T
		assertTrue( "" + accountList.size(), accountList.size() == 6 );
		//�n�R�����b�������s�b
		assertTrue( accountList.indexOf("lenwind") == -1 );
		
		//Case4 �R�����s�b��Id
		this.apacheAuthenticationEntity.deleteAccount("No Exist");
		accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//���������b����T
		assertTrue( "" + accountList.size(), accountList.size() == 6 );
		//�n�R�����b�������s�b
		assertTrue( accountList.indexOf("No Exist") == -1 );
	}
	
	/**
	 * ���ձN�ɮ׸�T�g�^�ɮ׫�O�_���T
	 * 
	 * Case1 ����Ū���g�J���b�����e���ɮ�
	 */
	public void testSave1(){
		//�ƻs�@���n���ժ��ɮ�
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);
		
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.copyFile);
		//Ū���ɮ�
		this.apacheAuthenticationEntity.load();
		
		//�g�L�@�Ǿާ@ �ק� �إ� �R��
		this.apacheAuthenticationEntity.modifyAccountId("franklin", "franklin2");
		this.apacheAuthenticationEntity.createAccount("addtest", "haha");
		this.apacheAuthenticationEntity.deleteAccount("frankcheng");
		
		//�x�s�ɮ�
		this.apacheAuthenticationEntity.save();
		
		//���sŪ��������
		this.apacheAuthenticationEntity = null;
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.copyFile);
		this.apacheAuthenticationEntity.load();
		//���o�b��id�C��
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//�����E���b����T
		assertTrue( "" + accountList.size(), accountList.size() == 9 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") == -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		assertTrue( accountList.indexOf("addtest") != -1 );
	}
	
	
	/**
	 * ���ձN�ɮ׸�T�g�^�ɮ׫�O�_���T
	 * 
	 * Case2 ����Ū���g�J�ťժ��ɮ�
	 */
	public void testSave2(){
		//�ƻs�@���n���ժ��ɮ�
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp");
		FileCopy.copy(this.emptyFile, this.copyFile);
		
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.copyFile);
		//Ū���ɮ�
		this.apacheAuthenticationEntity.load();
		
		//�إ߱b��
		this.apacheAuthenticationEntity.createAccount("franklin1", "haha");
		this.apacheAuthenticationEntity.createAccount("franklin2", "haha");
		this.apacheAuthenticationEntity.createAccount("franklin3", "haha");
		this.apacheAuthenticationEntity.createAccount("franklin4", "haha");
		this.apacheAuthenticationEntity.createAccount("franklin5", "haha");
		
		//�x�s�ɮ�
		this.apacheAuthenticationEntity.save();
		
		//���sŪ��������
		this.apacheAuthenticationEntity = null;
		this.apacheAuthenticationEntity = new ApacheAuthenticationEntity(this.copyFile);
		this.apacheAuthenticationEntity.load();
		//���o�b��id�C��
		List<String> accountList =  this.apacheAuthenticationEntity.getAccountList();
		//���������b����T
		assertTrue( "" + accountList.size(), accountList.size() == 5 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin1") != -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		assertTrue( accountList.indexOf("franklin3") != -1 );
		assertTrue( accountList.indexOf("franklin4") != -1 );
		assertTrue( accountList.indexOf("franklin5") != -1 );
	}
	

	
}
