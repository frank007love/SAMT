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
	 * �i��SVN�s�u
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
	 * ���o�Y�����|�U�Ҧ����ɮצW��(or���|)
	 * @param parentPath 
	 * @param action ��O�n���oDIR or FILE
	 * @return
	 * @throws SVNException �NException��UI�B�z
	 */
    public List<String> getRepositorySubFile(String parentPath,String action) throws SVNException{
    	//�^�Ǫ����|�C��
    	List<String> pathList = new ArrayList<String>();
    	
    	
		//���oSVN�����w��parentPath���U���Ҧ�entry
    	Collection<SVNDirEntry> entries = repository.getDir(parentPath, -1, null,(Collection) null);
    	Iterator<SVNDirEntry> iterator = entries.iterator();
        
        //�̾ڨϥΪ̩ҭn���o�����e�]�m�n���o����DIR or FILE
        SVNNodeKind actionkind = SVNNodeKind.NONE;
        if( action.equals("DIR") ){
        	actionkind = SVNNodeKind.DIR;
        } else if( action.equals("FILE") ){
        	actionkind = SVNNodeKind.FILE;
        }
        
        //���oentry���|���^�Ǫ����|�C��pathList
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            
            String entryName = entry.getName();
            
            //entry���@�Ӹ�Ƨ�,�[�Wdir�ΥH��Otype
            if( entry.getKind().equals(actionkind)){
            	pathList.add(entryName);
            }
        }
    
    	return pathList;
    }

	/**
	 * �إ��ɮ�, �Y�ǤJ��pathlist�����w�إߪ�, 
	 * �h�i��skip
	 * @param pathlist
	 */
	public void createDir(List<String> pathlist) throws SVNException{
		//pathlist���ūh���i��ʧ@
		if( pathlist == null ) return;
		
        SVNCommitClient commitClient = this.createCommitClient();
        
        //Skip���w�Q�إߪ��ɮ�
        for( int i = 0 ; i < pathlist.size() ; i++ ){

       		//���o�_�@���|��SVNNodeKind, �ΥH�P�_�O�_�s�b
    		SVNNodeKind kind = this.repository.checkPath( pathlist.get(i) ,-1);
    		//�w�s�b���ɮ׫h�����n�إߤ��C��
    		if( kind != SVNNodeKind.NONE ){
    			pathlist.remove(i);
    			i--; //�ѩ�size��1,�]��������1
    		}
        }
        SVNURL []urllist = new SVNURL[pathlist.size()];
        //�N�n�إߪ��C���ഫ��SVNURL
        for( int i = 0 ; pathlist != null && i < pathlist.size() ; i++ ){
        	String createdurl = this.url + pathlist.get(i);

        	urllist[i] = SVNURL.parseURIDecoded(createdurl);

        }

        //�i���ɮ׫إ�
        commitClient.doMkDir( urllist , "SAMT�ɮ׫إ�");
	}
	
	/**
	 * �إ�CommitClient
	 * @return
	 */
	protected SVNCommitClient createCommitClient(){
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		SVNClientManager clientManager = SVNClientManager.newInstance(options, this.name, this.password);
		return clientManager.getCommitClient();
	}
	
	/**
	 * �T�{path�O�_�w�s�b��SVN
	 * @param path
	 * @return
	 */
	public boolean isPathExist(String path){
		SVNNodeKind kind;
		try {
			//���o�_�@���|��SVNNodeKind, �ΥH�P�_�O�_�s�b
			kind = this.repository.checkPath( path ,-1);
		} catch( SVNException e ){
			e.printStackTrace();
			kind = SVNNodeKind.NONE; 
		}
		//���|�w�s�b
		if( kind != SVNNodeKind.NONE ){		
			return true;
		}
		//���|���s�b
		return false;
	}
	
	/***************************************
	 * SVN�򥻳]�m����
	 ***************************************/
	
    /**
     * �]�m�s��SVN Repository���b��
     * @param id
     */
    public void setAccount(String id){
    	this.name = id;
    }
    
    /**
     * �]�m�s��SVN Repository���K�X
     * @param passwd
     */
    public void setPasswd(String passwd){
    	this.password = passwd;
    }
    
    /**
     * �]�mSVN�����w��url
     * @param url
     */
    public void setSVNURL(String url){
    	this.url = url;
    }

    /**
     * �]�mSVN Repository, �D�n�Ω�Testing
     * @param repository
     */
    public void setRepository(SVNRepository repository){
    	this.repository = repository;
    }
}


