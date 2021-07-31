package ntut.csie.samt.svncontroll;

import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.SVNCommitInfo;
import java.util.*;

public class MockCommitClient extends SVNCommitClient {

	List<String> urlList;
	
	public MockCommitClient(){
		super((ISVNAuthenticationManager)null,null);
		urlList = new ArrayList<String>();
	}
	
	public SVNCommitInfo doMkDir(SVNURL []urllist,String msg){

		for( int i = 0 ; i < urllist.length ; i++ ){
			String url = urllist[i].getPath();
			this.urlList.add(url);
		}
		return null;
	}
	
	public List<String> getUrlList(){
		return urlList;
	}
	
}
