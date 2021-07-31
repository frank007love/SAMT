package ntut.csie.samt.svncontroll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNRepositoryController {

	private SVNRepository repository = null;
	private String url = "http://100.0.0.127/";
	private String name = "";
	private String password = "";

	public SVNRepositoryController() {
	}

	/**
	 * 進行SVN連線
	 * @return
	 */
	public boolean connection(){
		try {
			this.setupLibrary();
			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			// System.out.println(repository.get.getPath());
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(this.name,
							this.password);
			repository.setAuthenticationManager(authManager);
			
			return true;
		} catch (SVNException e) {
			System.out.println("RepositoryFileList construct fail: " + e);
			
			return false;
		}
	}

	/*
	 * Initializes the library to work with a repository via different
	 * protocols.
	 */
	private void setupLibrary() {
		/*
		 * For using over http:// and https://
		 */
		DAVRepositoryFactory.setup();
		/*
		 * For using over svn:// and svn+xxx://
		 */
		SVNRepositoryFactoryImpl.setup();

		/*
		 * For using over file:///
		 */
		FSRepositoryFactory.setup();
	}

	/**
	 * 取得某條路徑下所有的檔案名稱(or路徑)
	 * @param parentPath 
	 * @param action 辨別要取得DIR or FILE
	 * @return
	 * @throws SVNException 將Exception給UI處理
	 */
    public List<String> getRepositorySubFile(String parentPath,String action) throws SVNException{
    	//回傳的路徑列表
    	List<String> pathList = new ArrayList<String>();
    	
    	
		//取得SVN版本庫中parentPath底下的所有entry
    	Collection<SVNDirEntry> entries = repository.getDir(parentPath, -1, null,(Collection) null);
    	Iterator<SVNDirEntry> iterator = entries.iterator();
        
        //依據使用者所要取得的內容設置要取得的為DIR or FILE
        SVNNodeKind actionkind = SVNNodeKind.NONE;
        if( action.equals("DIR") ){
        	actionkind = SVNNodeKind.DIR;
        } else if( action.equals("FILE") ){
        	actionkind = SVNNodeKind.FILE;
        }
        
        //取得entry路徑放到回傳的路徑列表pathList
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            
            String entryName = entry.getName();
            
            //entry為一個資料夾,加上dir用以辨別type
            if( entry.getKind().equals(actionkind)){
            	pathList.add(entryName);
            }
        }
    
    	return pathList;
    }

	/**
	 * 建立檔案, 若傳入的pathlist中有已建立的, 
	 * 則進行skip
	 * @param pathlist
	 */
	public void createDir(List<String> pathlist) throws SVNException{
		//pathlist為空則不進行動作
		if( pathlist == null ) return;
		
        SVNCommitClient commitClient = this.createCommitClient();
        
        //Skip掉已被建立的檔案
        for( int i = 0 ; i < pathlist.size() ; i++ ){

       		//取得否一路徑的SVNNodeKind, 用以判斷是否存在
    		SVNNodeKind kind = this.repository.checkPath( pathlist.get(i) ,-1);
    		//已存在的檔案則移除要建立之列表
    		if( kind != SVNNodeKind.NONE ){
    			pathlist.remove(i);
    			i--; //由於size少1,因此必須減1
    		}
        }
        SVNURL []urllist = new SVNURL[pathlist.size()];
        //將要建立的列表轉換成SVNURL
        for( int i = 0 ; pathlist != null && i < pathlist.size() ; i++ ){
        	String createdurl = this.url + pathlist.get(i);

        	urllist[i] = SVNURL.parseURIDecoded(createdurl);

        }

        //進行檔案建立
        commitClient.doMkDir( urllist , "SAMT檔案建立");
	}
	
	/**
	 * 建立CommitClient
	 * @return
	 */
	protected SVNCommitClient createCommitClient(){
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		SVNClientManager clientManager = SVNClientManager.newInstance(options, this.name, this.password);
		return clientManager.getCommitClient();
	}
	
	/**
	 * 確認path是否已存在於SVN
	 * @param path
	 * @return
	 */
	public boolean isPathExist(String path){
		SVNNodeKind kind;
		try {
			//取得否一路徑的SVNNodeKind, 用以判斷是否存在
			kind = this.repository.checkPath( path ,-1);
		} catch( SVNException e ){
			e.printStackTrace();
			kind = SVNNodeKind.NONE; 
		}
		//路徑已存在
		if( kind != SVNNodeKind.NONE ){		
			return true;
		}
		//路徑不存在
		return false;
	}
	
	/***************************************
	 * SVN基本設置相關
	 ***************************************/
	
    /**
     * 設置存取SVN Repository的帳號
     * @param id
     */
    public void setAccount(String id){
    	this.name = id;
    }
    
    /**
     * 設置存取SVN Repository的密碼
     * @param passwd
     */
    public void setPasswd(String passwd){
    	this.password = passwd;
    }
    
    /**
     * 設置SVN版本庫的url
     * @param url
     */
    public void setSVNURL(String url){
    	this.url = url;
    }

    /**
     * 設置SVN Repository, 主要用於Testing
     * @param repository
     */
    public void setRepository(SVNRepository repository){
    	this.repository = repository;
    }
}


