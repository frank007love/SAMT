package ntut.csie.samt.svncontroll;

import org.tmatesoft.svn.core.wc.SVNCommitClient;

public class MockSVNRepositoryController extends SVNRepositoryController {

	private SVNCommitClient commitClient;
	
	/**
	 * �إ�SVN��CommitClient, 
	 * �o�����ϥ�MockObject���N,
	 * �ϱo�i���o�ާ@�۹������e
	 */
	protected SVNCommitClient createCommitClient(){
		if( this.commitClient == null ){
			this.commitClient = new MockCommitClient();
		}
		return this.commitClient;
	}

	/**
	 * ���oCommitClient,
	 * �Ω���o�ާ@��������T
	 * @return
	 */
	public SVNCommitClient getCommitClient(){
		return this.commitClient;
	}
}
