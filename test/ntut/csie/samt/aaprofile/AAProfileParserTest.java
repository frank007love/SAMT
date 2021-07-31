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
	 * 測試Parser完取得帳號列表是否正確
	 */
	public void testGetAccountIdList(){
		List<String> accountList = this.parser.getAccountIdList();
		//測試取得帳號列表是否存在
		assertTrue( accountList != null );
		//測試取得帳號應有八筆
		assertTrue( accountList.size() == 8 );
		//測試取得帳號名稱是否正確
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
	 * 測試Parser完取得帳號列表是否正確 
	 */
	public void testGetGroupList(){
		List<String> groupList = this.parser.getGroupList();
		//測試取得群組列表是否存在
		assertTrue( groupList != null );
		//測試取得群組應有四筆
		assertTrue( groupList.size() == 4 );
		//測試取得群組名稱是否正確
		assertTrue( groupList.get(0).equals("admin") );
		assertTrue( groupList.get(1).equals("96") );
		assertTrue( groupList.get(2).equals("95") );
		assertTrue( groupList.get(3).equals("old") );
	}
	
	/**
	 * 測試Parser完取得某群組的成員列表是否正確 
	 */
	public void testGetGroupMemberList(){
		List<String> groupList = this.parser.getGroupList();
		//測試取得群組列表是否存在
		assertTrue( groupList != null );
		//測試取得群組應有四筆
		assertTrue( groupList.size() == 4 );
		//測試取得群組名稱是否正確
		assertTrue( groupList.get(0).equals("admin") );
		assertTrue( groupList.get(1).equals("96") );
		assertTrue( groupList.get(2).equals("95") );
		assertTrue( groupList.get(3).equals("old") );
		
		//測試群組成員
		List<String> groupMemberList = null;
		
		//測試名稱為admin的群組成員
		groupMemberList = this.parser.getGroupMemberList("admin");
		//測試取得群組成員列表是否存在
		assertTrue( groupMemberList != null );
		//測試取得群組成員應有三筆
		assertTrue( groupMemberList.size() == 3 );
		//測試取得群組成員是否正確
		assertTrue( groupMemberList.get(0).equals("@95") );
		assertTrue( groupMemberList.get(1).equals("tshsu") );
		assertTrue( groupMemberList.get(2).equals("ctchen") );
		
		//測試名稱為96的群組成員
		groupMemberList = this.parser.getGroupMemberList("96");
		//測試取得群組成員列表是否存在
		assertTrue( groupMemberList != null );
		//測試取得群組成員應有三筆
		assertTrue( groupMemberList.size() == 3 );
		//測試取得群組成員是否正確
		assertTrue( groupMemberList.get(0).equals("franklin") );
		assertTrue( groupMemberList.get(1).equals("lenwind") );
		assertTrue( groupMemberList.get(2).equals("frankcheng") );
		
		//測試名稱為95的群組成員
		groupMemberList = this.parser.getGroupMemberList("95");
		//測試取得群組成員列表是否存在
		assertTrue( groupMemberList != null );
		//測試取得群組成員應有二筆
		assertTrue( groupMemberList.size() == 2 );
		//測試取得群組成員是否正確
		assertTrue( groupMemberList.get(0).equals("oph") );
		assertTrue( groupMemberList.get(1).equals("ztgao") );
		
		//測試名稱為old的群組成員
		groupMemberList = this.parser.getGroupMemberList("old");
		//測試取得群組成員列表是否存在
		assertTrue( groupMemberList != null );
		//測試取得群組成員應有三筆
		assertTrue( groupMemberList.size() == 3 );
		//測試取得群組成員是否正確
		assertTrue( groupMemberList.get(0).equals("yc") );
		assertTrue( groupMemberList.get(1).equals("tshsu") );
		assertTrue( groupMemberList.get(2).equals("ctchen") );
	}
	
	/**
	 * 測試Parser完取得RepositoryPath是否正確 
	 */
	public void testGetRepositoryPath(){
		List<String> repositoryPathList = this.parser.getRepositoryPath();
		//測試取得repositoryPathList是否存在
		assertTrue( repositoryPathList != null );
		//測試取得RepositoryPath應有四筆
		assertTrue( repositoryPathList.size() == 4 );
		//測試取得Path Name是否正確
		assertTrue( repositoryPathList.get(0).equals("/") );
		assertTrue( repositoryPathList.get(1).equals("/JCIS/Java") );
		assertTrue( repositoryPathList.get(2).equals("/JCIS/Test") );
		assertTrue( repositoryPathList.get(3).equals("/TestingPath") );
	}
	
	public void testGetPathMember(){
		List<String> repositoryPathList = this.parser.getRepositoryPath();
		//測試取得repositoryPathList是否存在
		assertTrue( repositoryPathList != null );
		//測試取得RepositoryPath應有四筆
		assertTrue( repositoryPathList.size() == 4 );
		//測試取得Path Name是否正確
		assertTrue( repositoryPathList.get(0).equals("/") );
		assertTrue( repositoryPathList.get(1).equals("/JCIS/Java") );
		assertTrue( repositoryPathList.get(2).equals("/JCIS/Test") );
		assertTrue( repositoryPathList.get(3).equals("/TestingPath") );
		
		List<String> pathMemberList = null;
		//測試取得路徑"/"的路徑成員
		pathMemberList = this.parser.getPathMember("/");
		//測試取得路徑成員是否存在
		assertTrue( pathMemberList != null );
		//測試取得路徑成員應有二筆
		assertTrue( pathMemberList.size() == 2 );
		//測試取得路徑成員是否正確
		assertTrue( pathMemberList.get(0).equals("@*") );
		assertTrue( pathMemberList.get(1).equals("@admin") );
		
		//測試取得路徑"/JCIS/Java"的路徑成員
		pathMemberList = this.parser.getPathMember("/JCIS/Java");
		//測試取得路徑成員是否存在
		assertTrue( pathMemberList != null );
		//測試取得路徑成員應有三筆
		assertTrue( pathMemberList.size() == 3 );
		//測試取得路徑成員是否正確
		assertTrue( pathMemberList.get(0).equals("@95") );
		assertTrue( pathMemberList.get(1).equals("@96") );
		assertTrue( pathMemberList.get(2).equals("yc") );
		
		//測試取得路徑"/JCIS/Test"的路徑成員
		pathMemberList = this.parser.getPathMember("/JCIS/Test");
		//測試取得路徑成員是否存在
		assertTrue( pathMemberList != null );
		//測試取得路徑成員應有二筆
		assertTrue( pathMemberList.size() == 2 );
		//測試取得路徑成員是否正確
		assertTrue( pathMemberList.get(0).equals("@95") );
		assertTrue( pathMemberList.get(1).equals("@96") );
	}
	
	/**
	 * 測試Parser完取得路徑下成員存取權限
	 */
	public void testGetPermission(){
		List<String> repositoryPathList = this.parser.getRepositoryPath();
		//測試取得repositoryPathList是否存在
		assertTrue( repositoryPathList != null );
		//測試取得RepositoryPath應有四筆
		assertTrue( repositoryPathList.size() == 4 );
		//測試取得Path Name是否正確
		assertTrue( repositoryPathList.get(0).equals("/") );
		assertTrue( repositoryPathList.get(1).equals("/JCIS/Java") );
		assertTrue( repositoryPathList.get(2).equals("/JCIS/Test") );
		
		List<String> pathMemberList = null;
		//測試取得路徑"/"的路徑成員
		pathMemberList = this.parser.getPathMember("/");
		//測試取得路徑成員是否存在
		assertTrue( pathMemberList != null );
		//測試取得路徑成員應有二筆
		assertTrue( pathMemberList.size() == 2 );
		//測試取得路徑成員是否正確
		assertTrue( pathMemberList.get(0).equals("@*") );
		assertTrue( pathMemberList.get(1).equals("@admin") );
		
		//測試取得路徑"/JCIS/Java"的路徑成員
		pathMemberList = this.parser.getPathMember("/JCIS/Java");
		//測試取得路徑成員是否存在
		assertTrue( pathMemberList != null );
		//測試取得路徑成員應有三筆
		assertTrue( pathMemberList.size() == 3 );
		//測試取得路徑成員是否正確
		assertTrue( pathMemberList.get(0).equals("@95") );
		assertTrue( pathMemberList.get(1).equals("@96") );
		assertTrue( pathMemberList.get(2).equals("yc") );
		
		//測試取得路徑"/JCIS/Test"的路徑成員
		pathMemberList = this.parser.getPathMember("/JCIS/Test");
		//測試取得路徑成員是否存在
		assertTrue( pathMemberList != null );
		//測試取得路徑成員應有二筆
		assertTrue( pathMemberList.size() == 2 );
		//測試取得路徑成員是否正確
		assertTrue( pathMemberList.get(0).equals("@95") );
		assertTrue( pathMemberList.get(1).equals("@96") );
		
		//測試成員權限
		//要Cover "r" , "rw" , "w" , "" 四種情況 
		
		//測試取得路徑"/"的路徑成員之權限
		assertTrue( this.parser.getPermission("/", "@*").equals("r") );
		assertTrue( this.parser.getPermission("/", "@admin").equals("rw") );
		//測試取得路徑"/JCIS/Java"的路徑成員之權限
		assertTrue( this.parser.getPermission("/JCIS/Java", "@95").equals("rw") );
		assertTrue( this.parser.getPermission("/JCIS/Java", "@96").equals("rw") );
		assertTrue( this.parser.getPermission("/JCIS/Java", "yc").equals("r") );
		//測試取得路徑"/JCIS/Test"的路徑成員之權限
		assertTrue( this.parser.getPermission("/JCIS/Test", "@95").equals("rw") );
		assertTrue( this.parser.getPermission("/JCIS/Test", "@96").equals("rw") );
		//測試取得路徑"/TestingPath"的路徑成員之權限
		assertTrue( this.parser.getPermission("/TestingPath", "@95").equals("") );
		assertTrue( this.parser.getPermission("/TestingPath", "@96").equals("w") );
	}
	
	/**
	 * 測試Parser完取得所有的路徑列表(要在SVN Repository Create的) 
	 */
	public void testGetAllFolderList(){
		List<String> folderList = this.parser.getAllFolderList();
		//取得之路徑應存在
		assertTrue( folderList != null );
		//取得路徑數量應為4
		assertTrue( "" + folderList.size() , folderList.size() == 4 );
		//測試取得路徑名稱是否正確
		assertTrue( folderList.get(0).equals("/JCIS") );
		assertTrue( folderList.get(1).equals("/TestingPath") );
		assertTrue( folderList.get(2).equals("/JCIS/Java") );
		assertTrue( folderList.get(3).equals("/JCIS/Test") );
	}
	
	
}
