package ntut.csie.samt.svncontroll;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLock;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.ISVNFileRevisionHandler;
import org.tmatesoft.svn.core.io.ISVNLocationEntryHandler;
import org.tmatesoft.svn.core.io.ISVNLockHandler;
import org.tmatesoft.svn.core.io.ISVNReporterBaton;
import org.tmatesoft.svn.core.io.ISVNWorkspaceMediator;
import org.tmatesoft.svn.core.io.SVNRepository;

import java.util.*;

public class MockSVNRepository extends SVNRepository {

	List<String> path;
	
	public MockSVNRepository(){
		super(null, null);
		path = new ArrayList<String>();
	}
	
	/**
	 * 設置要測試的檔案路徑
	 * @param path
	 */
	public void setPath(List<String> path){
		this.path = path;
	}
	
	/**
	 * 檢查路徑是否存在,
	 * 比對假的檔案路徑path
	 * @return 存在則回傳SVNNodeKind.DIR,不存在則SVNNodeKind.NONE
	 */
	@Override
	public SVNNodeKind checkPath(String arg0, long arg1) throws SVNException {
		// TODO Auto-generated method stub
		if( path.contains(arg0) ){
			return SVNNodeKind.DIR;
		}
		else {
			return SVNNodeKind.NONE;
		}
	}

	@Override
	public void closeSession() {
		// TODO Auto-generated method stub

	}

	@Override
	public void diff(SVNURL arg0, long arg1, String arg2, boolean arg3,
			boolean arg4, ISVNReporterBaton arg5, ISVNEditor arg6)
			throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void diff(SVNURL arg0, long arg1, long arg2, String arg3,
			boolean arg4, boolean arg5, ISVNReporterBaton arg6, ISVNEditor arg7)
			throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void diff(SVNURL arg0, long arg1, long arg2, String arg3,
			boolean arg4, boolean arg5, boolean arg6, ISVNReporterBaton arg7,
			ISVNEditor arg8) throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public ISVNEditor getCommitEditor(String arg0, Map arg1, boolean arg2,
			ISVNWorkspaceMediator arg3) throws SVNException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDatedRevision(Date arg0) throws SVNException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getDir(String arg0, long arg1, Map arg2,
			ISVNDirEntryHandler arg3) throws SVNException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SVNDirEntry getDir(String arg0, long arg1, boolean arg2,
			Collection arg3) throws SVNException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getFile(String arg0, long arg1, Map arg2, OutputStream arg3)
			throws SVNException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFileRevisions(String arg0, long arg1, long arg2,
			ISVNFileRevisionHandler arg3) throws SVNException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLatestRevision() throws SVNException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLocations(String arg0, long arg1, long[] arg2,
			ISVNLocationEntryHandler arg3) throws SVNException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SVNLock getLock(String arg0) throws SVNException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVNLock[] getLocks(String arg0) throws SVNException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getRevisionProperties(long arg0, Map arg1) throws SVNException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRevisionPropertyValue(long arg0, String arg1)
			throws SVNException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVNDirEntry info(String arg0, long arg1) throws SVNException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lock(Map arg0, String arg1, boolean arg2, ISVNLockHandler arg3)
			throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public long log(String[] arg0, long arg1, long arg2, boolean arg3,
			boolean arg4, long arg5, ISVNLogEntryHandler arg6)
			throws SVNException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void replay(long arg0, long arg1, boolean arg2, ISVNEditor arg3)
			throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRevisionPropertyValue(long arg0, String arg1, String arg2)
			throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void status(long arg0, String arg1, boolean arg2,
			ISVNReporterBaton arg3, ISVNEditor arg4) throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void testConnection() throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlock(Map arg0, boolean arg1, ISVNLockHandler arg2)
			throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long arg0, String arg1, boolean arg2,
			ISVNReporterBaton arg3, ISVNEditor arg4) throws SVNException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(SVNURL arg0, long arg1, String arg2, boolean arg3,
			ISVNReporterBaton arg4, ISVNEditor arg5) throws SVNException {
		// TODO Auto-generated method stub

	}

}
