package ntut.csie.samt.account;

import java.io.File;

import junit.framework.TestCase;
import java.util.*;

public class SVNAuthenticationEntityTest extends TestCase {

	private IAuthenticationEntity svnAuthenticationEntity;
	final private File loadFile = new File("WebContent/TestFile/AuthFileTest/svn.passwd");
	final private File emptyFile = new File("WebContent/TestFile/AuthFileTest/svn.passwd_empty");
	final private File errorFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd");
	private File copyFile = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * ���ձq�b���ɮפ����o�b��ID�C��O�_���T
	 * 
	 * Case1 ���ձq���`�ɮפ����o�b��ID�O�_���T
	 * Case2 ���ձq���ɮפ����o�b��ID�O�_���T
	 * Case3 ���ձq���P�ɮ׮榡���o�b��ID�O�_���T
	 */
	public void testGetAccountList(){
		//Case1 ���ձq���`�ɮפ����o�b��ID�O�_���T
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList.size() == 9 );
		//�T�{�b���O�_���T
		assert( accountList.contains("franklin") );
		assert( accountList.contains("bluewind") );
		assert( accountList.contains("ccchange") );
		assert( accountList.contains("frankcheng") );
		assert( accountList.contains("cyndi") );
		assert( accountList.contains("thenewid") );
		assert( accountList.contains("chihcheng") );
		assert( accountList.contains("nickhades") );
		assert( accountList.contains("lenwind") );
		
		//Case2 ���ձq���ɮפ����o�b��ID�O�_���T
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.emptyFile);
		this.svnAuthenticationEntity.load();
		accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList == null || accountList.size() == 0 );
		
		//Case3 ���ձq���P�ɮ׮榡���o�b��ID�O�_���T
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.errorFile);
		this.svnAuthenticationEntity.load();
		accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList == null || accountList.size() == 0 );
	}
	
	/**
	 * ���ձq�b���ɮפ����o�b���K�X�C��O�_���T
	 * 
	 * Case1 ���ձq���`�ɮפ����o�b���K�X�O�_���T
	 * Case2 ���ձq���ɮפ����o�b���K�X�O�_���T
	 * Case3 ���ձq���P�ɮ׮榡���o�b���K�X�O�_���T
	 */
	public void testGetAccountPasswdList(){
		//Case1 ���ձq���`�ɮפ����o�b��ID�O�_���T
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> passwdList = this.svnAuthenticationEntity.getAccountPasswdList();
		assert( passwdList.size() == 9 );
		//�T�{�K�X�O�_���T
		for( int i = 0 ; i < passwdList.size() ; i++ ){
			assert( passwdList.get(i).equals("1234") );
		}
		
		//Case2 ���ձq���ɮפ����o�b��ID�O�_���T
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.emptyFile);
		this.svnAuthenticationEntity.load();
		passwdList = this.svnAuthenticationEntity.getAccountList();
		assert( passwdList == null || passwdList.size() == 0 );
		
		//Case3 ���ձq���P�ɮ׮榡���o�b��ID�O�_���T
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.errorFile);
		this.svnAuthenticationEntity.load();
		passwdList = this.svnAuthenticationEntity.getAccountList();
		assert( passwdList == null || passwdList.size() == 0 );
	}	
	
	/**
	 * ���խק�b��ID�O�_���\
	 * 
	 * Case1 �ק�s�b���b��
	 * Case2 �ק藍�s�b���b��
	 * Case3 �ק令�s�b���b��
	 */
	public void testModifyAccountId(){
		//Case1 �ק�s�b���b��
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList.size() == 9 );		
		this.svnAuthenticationEntity.modifyAccountId("franklin", "frank007love");
		assert( accountList.size() == 9 );
		//���խק�O�_���\
		assert( accountList.contains("frank007love") );
		assert( !accountList.contains("franklin") );
		
		//Case2 �ק藍�s�b���b��
		this.svnAuthenticationEntity.modifyAccountId("franklin", "frank008love");
		assert( accountList.contains("frank007love") );
		assert( !accountList.contains("franklin") );
		assert( !accountList.contains("frank008love") );
		
		//Case3 �ק令�s�b���b��
		this.svnAuthenticationEntity.modifyAccountId("frank007love", "bluewind");
		assert( accountList.contains("frank007love") );
		assert( accountList.contains("bluewind") );
	}
	
	/**
	 * ���շs�W�b��
	 * 
	 * Case1 �[�J�@�ӷs�b��
	 * Case2 �[�J�@�ӷs�b��,�S���K�X
	 * Case3 �[�J���ƪ��b��
	 */
	public void testCreateAccount(){
		//Case1 �[�J�@�ӷs�b��
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList.size() == 9 );
		this.svnAuthenticationEntity.createAccount("testAdd", "4567");
		assert( accountList.size() == 10 );
		assert( accountList.contains("testAdd") );
		
		//Case2 �[�J�@�ӷs�b��,�S���K�X
		this.svnAuthenticationEntity.createAccount("testAdd2", "");
		assert( accountList.size() == 10 );
		assert( !accountList.contains("testAdd2") );
		
		//Case3 �[�J���ƪ��b��
		this.svnAuthenticationEntity.createAccount("testAdd", "");
		assert( accountList.size() == 10 );
		assert( accountList.contains("testAdd") );
	}
	
	/**
	 * ���էR���b����T
	 * 
	 * Case1 �R���Ĥ@�ӱb��Id
	 * Case2 �R���������b��Id
	 * Case3 �R���̫�@�Ӫ��b��Id
	 * Case4 �R�����s�b���b��Id
	 * Case5 �R������Id
	 */
	public void testDeleteAccount(){
		//Case1 �R���Ĥ@�ӱb��Id
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		assert( accountList.size() == 9 );
		this.svnAuthenticationEntity.deleteAccount("franklin");
		assert( accountList.size() == 8 );
		assert( !accountList.contains("franklin") );
		
		//Case2 �R���������b��Id
		this.svnAuthenticationEntity.deleteAccount("frankcheng");
		assert( accountList.size() == 7 );
		assert( !accountList.contains("frankcheng") );
		
		//Case3 �R���̫�@�Ӫ��b��Id
		this.svnAuthenticationEntity.deleteAccount("lenwind");
		assert( accountList.size() == 6 );
		assert( !accountList.contains("lenwind") );
		
		//Case4 �R�����s�b���b��Id
		this.svnAuthenticationEntity.deleteAccount("lenwind");
		assert( accountList.size() == 6 );
		assert( !accountList.contains("lenwind") );
		
		//Case5 �R������Id
		this.svnAuthenticationEntity.deleteAccount("bluewind");
		this.svnAuthenticationEntity.deleteAccount("ccchange");
		this.svnAuthenticationEntity.deleteAccount("cyndi");
		this.svnAuthenticationEntity.deleteAccount("thenewid");
		this.svnAuthenticationEntity.deleteAccount("chihcheng");
		this.svnAuthenticationEntity.deleteAccount("nickhades");
		assert( accountList.size() == 0 );
	}
	
	/**
	 * ���խק�K�X
	 * 
	 * Case1 �ק�s�b���b�����K�X
	 * Case2 �ק�K�X���O�K�X����
	 * 
	 */
	public void testModifyAccountPasswd(){
		//Case1 �ק�s�b���b�����K�X
		this.svnAuthenticationEntity = new SVNAuthenticationEntity(this.loadFile);
		this.svnAuthenticationEntity.load();
		List<String> accountList = this.svnAuthenticationEntity.getAccountList();
		List<String> passwdList = this.svnAuthenticationEntity.getAccountPasswdList();
		
		this.svnAuthenticationEntity.modifyAccountPasswd("franklin", "5678");
		int index = accountList.indexOf("franklin");
		assert( !passwdList.get(index).equals("5678") );
		assert( passwdList.get(index).equals("5678") );
		
		//Case2 �ק�K�X���O�K�X����
		this.svnAuthenticationEntity.modifyAccountPasswd("franklin", "");
		assert( passwdList.get(index).equals("5678") );
	}
}
