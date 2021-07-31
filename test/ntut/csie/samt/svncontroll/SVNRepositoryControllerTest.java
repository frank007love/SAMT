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
	 * ���իإ߸�Ƨ�
	 * Case1 Repository����, or �L�����ɮ�
	 */
	public void testCreateDir1(){
		//�n�إߪ���Ƨ��C��
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
			
			//���շs�W�����|�O�_���T
			SVNCommitClient commitClient = ((MockSVNRepositoryController)this.svnController).getCommitClient();
			List<String> urlList = ((MockCommitClient)commitClient).getUrlList();
			//Repository���w�]��Root
			assertTrue(urlList.contains("/JCIS/java/dir1"));
			assertTrue(urlList.contains("/JCIS/java/dir2"));
			assertTrue(urlList.contains("/JCIS/java/dir3"));
			assertTrue(urlList.contains("/JCIS/test/dir1"));
			assertTrue(urlList.contains("/JCIS/test/dir2"));
			assertTrue(urlList.contains("/JCIS/test/dir3"));
			
		} catch(Exception e){
			//���`���p���|����Exception
			assertTrue(false);
		}
	}
	
	/**
	 * ���իإ߸�Ƨ�
	 * Case2 Repository���������ɮ�
	 */
	public void testCreateDir2(){
		//�n�إߪ���Ƨ��C��
		List<String> pathList = new ArrayList<String>();
		pathList.add("/JCIS/java/dir1");
		pathList.add("/JCIS/java/dir2");
		pathList.add("/JCIS/java/dir3");
		pathList.add("/JCIS/test/dir1");
		pathList.add("/JCIS/test/dir2");
		pathList.add("/JCIS/test/dir3");
		
		try{
			SVNRepository repository = new MockSVNRepository();
			//�]�mSVN Repository��w�s�b���ɮ�
			List<String> rpPathList = new ArrayList<String>();
			rpPathList.add("/JCIS/java/dir1");
			rpPathList.add("/JCIS/java/dir4");
			rpPathList.add("/JCIS/java/dir5");
			rpPathList.add("/JCIS/test/dir1");
			((MockSVNRepository)repository).setPath(rpPathList);
			this.svnController.setRepository(repository);
			this.svnController.createDir(pathList);
			
			//���շs�W�����|�O�_���T
			SVNCommitClient commitClient = ((MockSVNRepositoryController)this.svnController).getCommitClient();
			List<String> urlList = ((MockCommitClient)commitClient).getUrlList();
			//Repository���w�]��Root, dir1�����b�s�W�����|��, �]�쥻�w�s�b�G�QSkip
			assertTrue(!urlList.contains("/JCIS/java/dir1"));
			assertTrue(urlList.contains("/JCIS/java/dir2"));
			assertTrue(urlList.contains("/JCIS/java/dir3"));
			assertTrue(!urlList.contains("/JCIS/test/dir1"));
			assertTrue(urlList.contains("/JCIS/test/dir2"));
			assertTrue(urlList.contains("/JCIS/test/dir3"));
			
		} catch(Exception e){
			//���`���p���|����Exception
			assertTrue(false);
		}
	}
	
	/**
	 * ���իإ߸�Ƨ�
	 * Case3 �PRepository�s�u����,����Exception
	 */
	public void testCreateDir3(){
		//�n�إߪ���Ƨ��C��
		List<String> pathList = new ArrayList<String>();
		pathList.add("/JCIS/java/dir1");
		pathList.add("/JCIS/java/dir2");
		pathList.add("/JCIS/java/dir3");
		pathList.add("/JCIS/test/dir1");
		pathList.add("/JCIS/test/dir2");
		pathList.add("/JCIS/test/dir3");
		
		try{
			this.svnController.createDir(pathList);
			//������Exception, �Y�q�L�h�S��Exception
			assertTrue(false);
		} catch(Exception e){
			//����Exception
			assertTrue(true);
		}
	}
}
