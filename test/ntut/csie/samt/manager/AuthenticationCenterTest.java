package ntut.csie.samt.manager;

import java.io.File;
import java.util.List;

import ntut.csie.samt.FileCopy;
import junit.framework.TestCase;

public class AuthenticationCenterTest extends TestCase {

	private AuthenticationCenter authenticationCenter;
	final private File loadFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd");
	final private File emptyFile = new File("WebContent/TestFile/AuthFileTest/dav_svn.passwd_empty");
	private File copyFile = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		this.authenticationCenter = new AuthenticationCenter();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		//�R�����եΪ��ƻs�ɮ�
		if( this.copyFile != null && this.copyFile.exists() ){
			this.copyFile.delete();
		}
	}

	/**
	 * Ū��http protocol���{�Ҥ覡�ɮ�,
	 * ����Ū���ɮ׫�, ���o���b��ID�C��O�_���T
	 * 
	 * Case1 Ū�����`���ɮ�
	 * Case2 Ū���Ū��ɮ�
	 */
	public void testGetAccountIdList_http(){
		List<String> accountList = null;
		
		//Case1 Ū�����`���ɮ�
		//�ƻs�@���n���ժ��ɮ�
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);

		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.copyFile.getPath()));
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����E���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 9 );
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
		
		//Case2 Ū���Ū��ɮ�
		this.authenticationCenter.setProtocol("http");
		this.authenticationCenter.load(this.emptyFile.getPath());
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList == null );
	}
	
	/**
	 * ���շs�W�b����C��O�_���T,�s�W��w�s�b���e���ɮ�
	 * 
	 * Case1 �s�W���s�b���b��
	 * Case2 �s�W�w�s�b���b��
	 * Case3 �s�W�L�K�X���b��
	 */
	public void testCreateAccount_http_existfile(){
		List<String> accountList = null;
		//�ƻs�@���n���ժ��ɮ�
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);
		//�]�m�n�ϥΪ�protocol�PŪ���ɮ�
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.copyFile.getPath()));
		
		//Case1 �s�W���s�b���b��
		assertTrue( this.authenticationCenter.createAccount("newAccount", "lisi87"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����Q���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 10 );
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
		assertTrue( accountList.indexOf("newAccount") != -1 );
		
		//Case2 �s�W�w�s�b���b��
		assertFalse( this.authenticationCenter.createAccount("newAccount", "lisi87"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����Q���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 10 );
		
		//Case3 �s�W�L�K�X���b��
		assertFalse( this.authenticationCenter.createAccount("newAccount2", ""));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����Q���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 10 );
	}
	
	/**
	 * ���շs�W�b����C��O�_���T,�s�W��Ū��ɮ�
	 * 
	 * Case1 �s�W���s�b�b��
	 * Case2 �s�W�w�s�b���b��
	 * �s�W�L�K�X���b��
	 */
	public void testCreateAccount_http_emptyfile(){
		List<String> accountList = null;
		//�]�m�n�ϥΪ�protocol�PŪ���ɮ�
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.emptyFile.getPath()));
		
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList == null );
		
		//Case1 �s�W���s�b���b��
		assertTrue( this.authenticationCenter.createAccount("newAccount", "lisi87"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����@���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 1 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("newAccount") != -1 );
		
		//Case2 �s�W�w�s�b���b��
		assertFalse( this.authenticationCenter.createAccount("newAccount", "lisi87"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����@���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 1 );
		
		//Case3 �s�W�L�K�X���b��
		assertFalse( this.authenticationCenter.createAccount("newAccount2", ""));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����@���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 1 );
	}
	
	/**
	 * ���էR���b����C��O�_���T,�R����w�s�b���e���ɮ�
	 * 
	 * Case1 �R���s�b���b��-�Ĥ@��
	 * Case2 �R���s�b���b��-������
	 * Case3 �R���s�b���b��-�̫�@��
	 * Case4 �R�����s�b���b��
	 */
	public void testDeleteAccount_http_existfile(){
		List<String> accountList = null;
		//�ƻs�@���n���ժ��ɮ�
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);
		//�]�m�n�ϥΪ�protocol�PŪ���ɮ�
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.copyFile.getPath()));
		
		//Case1 �R���s�b���b��-�Ĥ@��
		assertTrue( this.authenticationCenter.deleteAccount("franklin"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����K���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 8 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") != -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		
		//Case2 �R���s�b���b��-������
		assertTrue( this.authenticationCenter.deleteAccount("cyndi"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//�����C���b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 7 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") == -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") != -1 );
		
		//Case3 �R���s�b���b��-�̫�@��
		assertTrue( this.authenticationCenter.deleteAccount("lenwind"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//���������b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 6 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("bluewind") != -1 );
		assertTrue( accountList.indexOf("ccchange") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );
		assertTrue( accountList.indexOf("cyndi") == -1 );
		assertTrue( accountList.indexOf("thenewid") != -1 );
		assertTrue( accountList.indexOf("chihcheng") != -1 );
		assertTrue( accountList.indexOf("nickhades") != -1 );
		assertTrue( accountList.indexOf("lenwind") == -1 );
		
		//Case4 �R�����s�b���b��
		assertFalse( this.authenticationCenter.deleteAccount("lenwind"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//���������b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 6 );
	}
	
	/**
	 * ���էR���b����C��O�_���T,�R����Ū��ɮ�
	 * 
	 * Case1 �R���b��
	 */
	public void testDeleteAccount_http_emptyfile(){
		List<String> accountList = null;
		//�]�m�n�ϥΪ�protocol�PŪ���ɮ�
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.emptyFile.getPath()));
		
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList == null );
		
		//Case1 �R���b��
		//�b�����s�b
		assertFalse( this.authenticationCenter.deleteAccount("franklin"));
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList == null );
	}
	
	/**
	 * ���խק�b����T
	 * 
	 * Case1 �ק�s�b���b�������s�b���b���W��,���ק�K�X
	 * Case2 �ק�b���P�K�X, �b�������s�b���W��
	 * Case3 �ק藍�s�b���b��
	 */
	public void testModifyAccount_http(){
		List<String> accountList = null;
		//�ƻs�@���n���ժ��ɮ�
		this.copyFile = new File("WebContent/TestFile/AuthFileTest/tmp.txt");
		FileCopy.copy(this.loadFile, this.copyFile);
		//�]�m�n�ϥΪ�protocol�PŪ���ɮ�
		this.authenticationCenter.setProtocol("http");
		assertTrue( this.authenticationCenter.load(this.copyFile.getPath()));
		
		//Case1 �ק�s�b���b�������s�b���b���W��,���ק�K�X
		assertTrue ( this.authenticationCenter.modifyAccount("franklin", "franklin2", ""));
		
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//���������b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 9 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") == -1 );
		assertTrue( accountList.indexOf("franklin2") != -1 );
		
		//Case2 �ק�b���P�K�X, �n�ק諸�b�������s�b���W��
		assertTrue( this.authenticationCenter.modifyAccount("franklin2", "franklin", "hehey"));
		
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//���������b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 9 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("franklin2") == -1 );
		
		//Case3 �ק藍�s�b���b��
		assertFalse( this.authenticationCenter.modifyAccount("franklin2", "franklin", "hehey"));
		
		//���o�b���C��
		accountList = this.authenticationCenter.getAccountIdList();
		//���ը��o���b����T�O�_���T
		assertTrue( accountList != null );
		//���������b����T
		assertTrue( "" + accountList.size() ,  accountList.size() == 9 );
		//���ձb��ID�O�_���T
		assertTrue( accountList.indexOf("franklin") != -1 );
		assertTrue( accountList.indexOf("franklin2") == -1 );
	}
	
}
