package ntut.csie.samt.manager;

import junit.framework.TestCase;
import java.io.File;
import java.util.List;

import ntut.csie.samt.manager.AAManager;

public class AAManagerTest extends TestCase {

	private AAManager aaManager;
	
	protected void setUp() throws Exception {
		super.setUp();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		File file = new File("WebContent/TestFile/AuthFileTest/authz_empty");
		file.delete();
		file.createNewFile();
		
		file = new File("WebContent/TestFile/AAProfileTest/dav_svn.passwd_empty");
		file.delete();
		file.createNewFile();
	}

	/**
	 * Case1 ���նפJAAProfile�ɮר�Ū��s���]�w�P�b���]�w��, ��ƬO�_���T�إ�
	 * Case2 ���ƶפJ, �����o�^�Ǭ�1
	 */
	public void testImportProfile1(){
		//Case1
		
		//�]�w�n���ժ��ɮ׸��|
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
		System.setProperty("ntut.csie.samt.PropertiesPath", "WebContent/TestFile/samt.properties_import");

		this.aaManager = AAManager.getInstance();
		
		//�פJAAProfie�ɮ�
		this.aaManager.importProfile(new File("WebContent/TestFile/AAProfileTest/AAProfileTest.xml"));
		
		assertTrue( this.aaManager.importAccounts() == 2);
		
		List<String> accountList = this.aaManager.listAllAccount();
		assertTrue( ""+accountList.size(), accountList.size() == 8 );
		assertTrue( accountList.indexOf("oph") != -1 );
		assertTrue( accountList.indexOf("ztgao") != -1 );
		assertTrue( accountList.indexOf("tshsu") != -1 );
		assertTrue( accountList.indexOf("yc") != -1 );
		assertTrue( accountList.indexOf("ctchen") != -1 );
		assertTrue( accountList.indexOf("franklin") != -1 );	
		assertTrue( accountList.indexOf("lenwind") != -1 );
		assertTrue( accountList.indexOf("frankcheng") != -1 );

		assertTrue( this.aaManager.importGroups() == 2);
		assertTrue( this.aaManager.importGroupMember() == 2); //�b���|���s�b�~�[����
		assertTrue( this.aaManager.importPermissions() == 2);

		
		//���ոs�զC��O�_���T
		List<String> groupList =  this.aaManager.listAllGroup();
		assertTrue( groupList != null );
		assertTrue( groupList.size() == 4 );
		assertTrue( groupList.indexOf("admin") != -1 );
		assertTrue( groupList.indexOf("96") != -1 );
		assertTrue( groupList.indexOf("95") != -1 );
		assertTrue( groupList.indexOf("old") != -1 );
		
		//���ոs�զ����C��O�_���T
		List<String> memberList = null;
		//����admin�������O�_���T
		memberList = this.aaManager.listGroupMember("admin");
		assertTrue( memberList != null );
		assertTrue(""+memberList.size() ,memberList.size() == 3 );
		assertTrue( memberList.indexOf("@95") != -1 );
		assertTrue( memberList.indexOf("tshsu") != -1 );
		assertTrue( memberList.indexOf("ctchen") != -1 );
		//����96�������O�_���T
		memberList = this.aaManager.listGroupMember("96");
		assertTrue( memberList != null );
		assertTrue( memberList.size() == 3 );
		assertTrue( memberList.indexOf("franklin") != -1 );
		assertTrue( memberList.indexOf("lenwind") != -1 );
		assertTrue( memberList.indexOf("frankcheng") != -1 );
		//����95�������O�_���T
		memberList = this.aaManager.listGroupMember("95");
		assertTrue( memberList != null );
		assertTrue( memberList.size() == 2 );
		assertTrue( memberList.indexOf("oph") != -1 );
		assertTrue( memberList.indexOf("ztgao") != -1 );
		//����old�������O�_���T
		memberList = this.aaManager.listGroupMember("old");
		assertTrue( memberList != null );
		assertTrue( memberList.size() == 3 );
		assertTrue( memberList.indexOf("yc") != -1 );
		assertTrue( memberList.indexOf("tshsu") != -1 );
		assertTrue( memberList.indexOf("ctchen") != -1 );
		
		//���ո��|�����O�_���T, �]�t�v��
		List<String> pathMemberList = null;
		//���ո��|"/"������
		pathMemberList = this.aaManager.getPathMemberList("/");
		assertTrue( pathMemberList != null );
		assertTrue( pathMemberList.size() == 2 );
		assertTrue( pathMemberList.indexOf("@*") != -1 ); //* �[�J�D�s�ժ����~
		assertTrue( pathMemberList.indexOf("@admin") != -1 );
		//���ո��|"/"�����v��
		assertTrue( this.aaManager.getPermission("/", "*", "group").equals("r") );
		assertTrue( this.aaManager.getPermission("/", "admin", "group").equals("rw") );
		
		//���ո��|"/JCIS/Java"������
		pathMemberList = this.aaManager.getPathMemberList("/JCIS/Java");
		assertTrue( pathMemberList != null );
		assertTrue( pathMemberList.size() == 3 );
		assertTrue( pathMemberList.indexOf("@95") != -1 );
		assertTrue( pathMemberList.indexOf("@96") != -1 );
		assertTrue( pathMemberList.indexOf("yc") != -1 );
		//���ո��|"/JCIS/Java"�����v��
		assertTrue( this.aaManager.getPermission("/JCIS/Java", "95", "group").equals("rw") );
		assertTrue( this.aaManager.getPermission("/JCIS/Java", "96", "group").equals("rw") );
		assertTrue( this.aaManager.getPermission("/JCIS/Java", "yc", "account").equals("r") );
		
		//���ո��|"/JCIS/Test"������
		pathMemberList = this.aaManager.getPathMemberList("/JCIS/Test");
		assertTrue( pathMemberList != null );
		assertTrue( pathMemberList.size() == 2 );
		assertTrue( pathMemberList.indexOf("@95") != -1 );
		assertTrue( pathMemberList.indexOf("@96") != -1 );
		//���ո��|"/JCIS/Test"�����v��
		assertTrue( this.aaManager.getPermission("/JCIS/Test", "95", "group").equals("rw") );
		assertTrue( this.aaManager.getPermission("/JCIS/Test", "96", "group").equals("rw") );
		
		//���ո��|"/TestingPath"������
		pathMemberList = this.aaManager.getPathMemberList("/TestingPath");
		assertTrue( pathMemberList != null );
		assertTrue( pathMemberList.size() == 2 );
		assertTrue( pathMemberList.indexOf("@95") != -1 );
		assertTrue( pathMemberList.indexOf("@96") != -1 );
		//���ո��|"/TestingPath"�����v��
		assertTrue( this.aaManager.getPermission("/TestingPath", "95", "group").equals("") );
		assertTrue( this.aaManager.getPermission("/TestingPath", "96", "group").equals("w") );
		
		//Case2 
		assertTrue( this.aaManager.importAccounts() == 1);
		assertTrue( this.aaManager.importGroups() == 1);
		assertTrue( this.aaManager.importGroupMember() == 1);
		assertTrue( this.aaManager.importPermissions() == 1);
	}
	
}
