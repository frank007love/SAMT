package ntut.csie.samt.aaprofile;

import junit.framework.TestCase;
import java.util.List;
import java.io.File;

public class AAProfileParserTest extends TestCase {

	private AAProfileParser parser;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		this.parser = new AAProfileParser(new File("WebContent/TestFile/AAProfileTest/AAProfileTest.xml"));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * ����Parser�����o�b���C��O�_���T
	 */
	public void testGetAccountIdList(){
		List<String> accountList = this.parser.getAccountIdList();
		//���ը��o�b���C��O�_�s�b
		assertTrue( accountList != null );
		//���ը��o�b�������K��
		assertTrue( accountList.size() == 8 );
		//���ը��o�b���W�٬O�_���T
		assertTrue( accountList.get(0).equals("oph") );
		assertTrue( accountList.get(1).equals("ztgao") );
		assertTrue( accountList.get(2).equals("tshsu") );
		assertTrue( accountList.get(3).equals("yc") );
		assertTrue( accountList.get(4).equals("ctchen") );
		assertTrue( accountList.get(5).equals("franklin") );
		assertTrue( accountList.get(6).equals("lenwind") );
		assertTrue( accountList.get(7).equals("frankcheng") );
	}
	
	/**
	 * ����Parser�����o�b���C��O�_���T 
	 */
	public void testGetGroupList(){
		List<String> groupList = this.parser.getGroupList();
		//���ը��o�s�զC��O�_�s�b
		assertTrue( groupList != null );
		//���ը��o�s�������|��
		assertTrue( groupList.size() == 4 );
		//���ը��o�s�զW�٬O�_���T
		assertTrue( groupList.get(0).equals("admin") );
		assertTrue( groupList.get(1).equals("96") );
		assertTrue( groupList.get(2).equals("95") );
		assertTrue( groupList.get(3).equals("old") );
	}
	
	/**
	 * ����Parser�����o�Y�s�ժ������C��O�_���T 
	 */
	public void testGetGroupMemberList(){
		List<String> groupList = this.parser.getGroupList();
		//���ը��o�s�զC��O�_�s�b
		assertTrue( groupList != null );
		//���ը��o�s�������|��
		assertTrue( groupList.size() == 4 );
		//���ը��o�s�զW�٬O�_���T
		assertTrue( groupList.get(0).equals("admin") );
		assertTrue( groupList.get(1).equals("96") );
		assertTrue( groupList.get(2).equals("95") );
		assertTrue( groupList.get(3).equals("old") );
		
		//���ոs�զ���
		List<String> groupMemberList = null;
		
		//���զW�٬�admin���s�զ���
		groupMemberList = this.parser.getGroupMemberList("admin");
		//���ը��o�s�զ����C��O�_�s�b
		assertTrue( groupMemberList != null );
		//���ը��o�s�զ��������T��
		assertTrue( groupMemberList.size() == 3 );
		//���ը��o�s�զ����O�_���T
		assertTrue( groupMemberList.get(0).equals("@95") );
		assertTrue( groupMemberList.get(1).equals("tshsu") );
		assertTrue( groupMemberList.get(2).equals("ctchen") );
		
		//���զW�٬�96���s�զ���
		groupMemberList = this.parser.getGroupMemberList("96");
		//���ը��o�s�զ����C��O�_�s�b
		assertTrue( groupMemberList != null );
		//���ը��o�s�զ��������T��
		assertTrue( groupMemberList.size() == 3 );
		//���ը��o�s�զ����O�_���T
		assertTrue( groupMemberList.get(0).equals("franklin") );
		assertTrue( groupMemberList.get(1).equals("lenwind") );
		assertTrue( groupMemberList.get(2).equals("frankcheng") );
		
		//���զW�٬�95���s�զ���
		groupMemberList = this.parser.getGroupMemberList("95");
		//���ը��o�s�զ����C��O�_�s�b
		assertTrue( groupMemberList != null );
		//���ը��o�s�զ��������G��
		assertTrue( groupMemberList.size() == 2 );
		//���ը��o�s�զ����O�_���T
		assertTrue( groupMemberList.get(0).equals("oph") );
		assertTrue( groupMemberList.get(1).equals("ztgao") );
		
		//���զW�٬�old���s�զ���
		groupMemberList = this.parser.getGroupMemberList("old");
		//���ը��o�s�զ����C��O�_�s�b
		assertTrue( groupMemberList != null );
		//���ը��o�s�զ��������T��
		assertTrue( groupMemberList.size() == 3 );
		//���ը��o�s�զ����O�_���T
		assertTrue( groupMemberList.get(0).equals("yc") );
		assertTrue( groupMemberList.get(1).equals("tshsu") );
		assertTrue( groupMemberList.get(2).equals("ctchen") );
	}
	
	/**
	 * ����Parser�����oRepositoryPath�O�_���T 
	 */
	public void testGetRepositoryPath(){
		List<String> repositoryPathList = this.parser.getRepositoryPath();
		//���ը��orepositoryPathList�O�_�s�b
		assertTrue( repositoryPathList != null );
		//���ը��oRepositoryPath�����|��
		assertTrue( repositoryPathList.size() == 4 );
		//���ը��oPath Name�O�_���T
		assertTrue( repositoryPathList.get(0).equals("/") );
		assertTrue( repositoryPathList.get(1).equals("/JCIS/Java") );
		assertTrue( repositoryPathList.get(2).equals("/JCIS/Test") );
		assertTrue( repositoryPathList.get(3).equals("/TestingPath") );
	}
	
	public void testGetPathMember(){
		List<String> repositoryPathList = this.parser.getRepositoryPath();
		//���ը��orepositoryPathList�O�_�s�b
		assertTrue( repositoryPathList != null );
		//���ը��oRepositoryPath�����|��
		assertTrue( repositoryPathList.size() == 4 );
		//���ը��oPath Name�O�_���T
		assertTrue( repositoryPathList.get(0).equals("/") );
		assertTrue( repositoryPathList.get(1).equals("/JCIS/Java") );
		assertTrue( repositoryPathList.get(2).equals("/JCIS/Test") );
		assertTrue( repositoryPathList.get(3).equals("/TestingPath") );
		
		List<String> pathMemberList = null;
		//���ը��o���|"/"�����|����
		pathMemberList = this.parser.getPathMember("/");
		//���ը��o���|�����O�_�s�b
		assertTrue( pathMemberList != null );
		//���ը��o���|���������G��
		assertTrue( pathMemberList.size() == 2 );
		//���ը��o���|�����O�_���T
		assertTrue( pathMemberList.get(0).equals("@*") );
		assertTrue( pathMemberList.get(1).equals("@admin") );
		
		//���ը��o���|"/JCIS/Java"�����|����
		pathMemberList = this.parser.getPathMember("/JCIS/Java");
		//���ը��o���|�����O�_�s�b
		assertTrue( pathMemberList != null );
		//���ը��o���|���������T��
		assertTrue( pathMemberList.size() == 3 );
		//���ը��o���|�����O�_���T
		assertTrue( pathMemberList.get(0).equals("@95") );
		assertTrue( pathMemberList.get(1).equals("@96") );
		assertTrue( pathMemberList.get(2).equals("yc") );
		
		//���ը��o���|"/JCIS/Test"�����|����
		pathMemberList = this.parser.getPathMember("/JCIS/Test");
		//���ը��o���|�����O�_�s�b
		assertTrue( pathMemberList != null );
		//���ը��o���|���������G��
		assertTrue( pathMemberList.size() == 2 );
		//���ը��o���|�����O�_���T
		assertTrue( pathMemberList.get(0).equals("@95") );
		assertTrue( pathMemberList.get(1).equals("@96") );
	}
	
	/**
	 * ����Parser�����o���|�U�����s���v��
	 */
	public void testGetPermission(){
		List<String> repositoryPathList = this.parser.getRepositoryPath();
		//���ը��orepositoryPathList�O�_�s�b
		assertTrue( repositoryPathList != null );
		//���ը��oRepositoryPath�����|��
		assertTrue( repositoryPathList.size() == 4 );
		//���ը��oPath Name�O�_���T
		assertTrue( repositoryPathList.get(0).equals("/") );
		assertTrue( repositoryPathList.get(1).equals("/JCIS/Java") );
		assertTrue( repositoryPathList.get(2).equals("/JCIS/Test") );
		
		List<String> pathMemberList = null;
		//���ը��o���|"/"�����|����
		pathMemberList = this.parser.getPathMember("/");
		//���ը��o���|�����O�_�s�b
		assertTrue( pathMemberList != null );
		//���ը��o���|���������G��
		assertTrue( pathMemberList.size() == 2 );
		//���ը��o���|�����O�_���T
		assertTrue( pathMemberList.get(0).equals("@*") );
		assertTrue( pathMemberList.get(1).equals("@admin") );
		
		//���ը��o���|"/JCIS/Java"�����|����
		pathMemberList = this.parser.getPathMember("/JCIS/Java");
		//���ը��o���|�����O�_�s�b
		assertTrue( pathMemberList != null );
		//���ը��o���|���������T��
		assertTrue( pathMemberList.size() == 3 );
		//���ը��o���|�����O�_���T
		assertTrue( pathMemberList.get(0).equals("@95") );
		assertTrue( pathMemberList.get(1).equals("@96") );
		assertTrue( pathMemberList.get(2).equals("yc") );
		
		//���ը��o���|"/JCIS/Test"�����|����
		pathMemberList = this.parser.getPathMember("/JCIS/Test");
		//���ը��o���|�����O�_�s�b
		assertTrue( pathMemberList != null );
		//���ը��o���|���������G��
		assertTrue( pathMemberList.size() == 2 );
		//���ը��o���|�����O�_���T
		assertTrue( pathMemberList.get(0).equals("@95") );
		assertTrue( pathMemberList.get(1).equals("@96") );
		
		//���զ����v��
		//�nCover "r" , "rw" , "w" , "" �|�ر��p 
		
		//���ը��o���|"/"�����|�������v��
		assertTrue( this.parser.getPermission("/", "@*").equals("r") );
		assertTrue( this.parser.getPermission("/", "@admin").equals("rw") );
		//���ը��o���|"/JCIS/Java"�����|�������v��
		assertTrue( this.parser.getPermission("/JCIS/Java", "@95").equals("rw") );
		assertTrue( this.parser.getPermission("/JCIS/Java", "@96").equals("rw") );
		assertTrue( this.parser.getPermission("/JCIS/Java", "yc").equals("r") );
		//���ը��o���|"/JCIS/Test"�����|�������v��
		assertTrue( this.parser.getPermission("/JCIS/Test", "@95").equals("rw") );
		assertTrue( this.parser.getPermission("/JCIS/Test", "@96").equals("rw") );
		//���ը��o���|"/TestingPath"�����|�������v��
		assertTrue( this.parser.getPermission("/TestingPath", "@95").equals("") );
		assertTrue( this.parser.getPermission("/TestingPath", "@96").equals("w") );
	}
	
	/**
	 * ����Parser�����o�Ҧ������|�C��(�n�bSVN Repository Create��) 
	 */
	public void testGetAllFolderList(){
		List<String> folderList = this.parser.getAllFolderList();
		//���o�����|���s�b
		assertTrue( folderList != null );
		//���o���|�ƶq����4
		assertTrue( "" + folderList.size() , folderList.size() == 4 );
		//���ը��o���|�W�٬O�_���T
		assertTrue( folderList.get(0).equals("/JCIS") );
		assertTrue( folderList.get(1).equals("/TestingPath") );
		assertTrue( folderList.get(2).equals("/JCIS/Java") );
		assertTrue( folderList.get(3).equals("/JCIS/Test") );
	}
	
	
}
