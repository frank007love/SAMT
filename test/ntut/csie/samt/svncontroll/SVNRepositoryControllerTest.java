package ntut.csie.samt.svncontroll;

import junit.framework.TestCase;
import java.util.*;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNCommitClient;

public class SVNRepositoryControllerTest extends TestCase {

	private SVNRepositoryController svnController;
	
	protected void setUp() throws Exception {
		super.setUp();
		svnController = new MockSVNRepositoryController();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 測試建立資料夾
	 * Case1 Repository為空, or 無重複檔案
	 */
	public void testCreateDir1(){
		//要建立的資料夾列表
		List<String> pathList = new ArrayList<String>();
		pathList.add("/JCIS/java/dir1");
		pathList.add("/JCIS/java/dir2");
		pathList.add("/JCIS/java/dir3");
		pathList.add("/JCIS/test/dir1");
		pathList.add("/JCIS/test/dir2");
		pathList.add("/JCIS/test/dir3");
		
		try{
			SVNRepository repository = new MockSVNRepository();
			this.svnController.setRepository(repository);
			this.svnController.createDir(pathList);
			
			//測試新增之路徑是否正確
			SVNCommitClient commitClient = ((MockSVNRepositoryController)this.svnController).getCommitClient();
			List<String> urlList = ((MockCommitClient)commitClient).getUrlList();
			//Repository為預設的Root
			assertTrue(urlList.contains("/JCIS/java/dir1"));
			assertTrue(urlList.contains("/JCIS/java/dir2"));
			assertTrue(urlList.contains("/JCIS/java/dir3"));
			assertTrue(urlList.contains("/JCIS/test/dir1"));
			assertTrue(urlList.contains("/JCIS/test/dir2"));
			assertTrue(urlList.contains("/JCIS/test/dir3"));
			
		} catch(Exception e){
			//正常情況不會產生Exception
			assertTrue(false);
		}
	}
	
	/**
	 * 測試建立資料夾
	 * Case2 Repository中有重複檔案
	 */
	public void testCreateDir2(){
		//要建立的資料夾列表
		List<String> pathList = new ArrayList<String>();
		pathList.add("/JCIS/java/dir1");
		pathList.add("/JCIS/java/dir2");
		pathList.add("/JCIS/java/dir3");
		pathList.add("/JCIS/test/dir1");
		pathList.add("/JCIS/test/dir2");
		pathList.add("/JCIS/test/dir3");
		
		try{
			SVNRepository repository = new MockSVNRepository();
			//設置SVN Repository原已存在之檔案
			List<String> rpPathList = new ArrayList<String>();
			rpPathList.add("/JCIS/java/dir1");
			rpPathList.add("/JCIS/java/dir4");
			rpPathList.add("/JCIS/java/dir5");
			rpPathList.add("/JCIS/test/dir1");
			((MockSVNRepository)repository).setPath(rpPathList);
			this.svnController.setRepository(repository);
			this.svnController.createDir(pathList);
			
			//測試新增之路徑是否正確
			SVNCommitClient commitClient = ((MockSVNRepositoryController)this.svnController).getCommitClient();
			List<String> urlList = ((MockCommitClient)commitClient).getUrlList();
			//Repository為預設的Root, dir1應不在新增的路徑內, 因原本已存在故被Skip
			assertTrue(!urlList.contains("/JCIS/java/dir1"));
			assertTrue(urlList.contains("/JCIS/java/dir2"));
			assertTrue(urlList.contains("/JCIS/java/dir3"));
			assertTrue(!urlList.contains("/JCIS/test/dir1"));
			assertTrue(urlList.contains("/JCIS/test/dir2"));
			assertTrue(urlList.contains("/JCIS/test/dir3"));
			
		} catch(Exception e){
			//正常情況不會產生Exception
			assertTrue(false);
		}
	}
	
	/**
	 * 測試建立資料夾
	 * Case3 與Repository連線失敗,產生Exception
	 */
	public void testCreateDir3(){
		//要建立的資料夾列表
		List<String> pathList = new ArrayList<String>();
		pathList.add("/JCIS/java/dir1");
		pathList.add("/JCIS/java/dir2");
		pathList.add("/JCIS/java/dir3");
		pathList.add("/JCIS/test/dir1");
		pathList.add("/JCIS/test/dir2");
		pathList.add("/JCIS/test/dir3");
		
		try{
			this.svnController.createDir(pathList);
			//應產生Exception, 若通過則沒有Exception
			assertTrue(false);
		} catch(Exception e){
			//產生Exception
			assertTrue(true);
		}
	}
}
