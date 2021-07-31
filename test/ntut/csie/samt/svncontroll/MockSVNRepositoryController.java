package ntut.csie.samt.svncontroll;

import org.tmatesoft.svn.core.wc.SVNCommitClient;

public class MockSVNRepositoryController extends SVNRepositoryController {

	private SVNCommitClient commitClient;
	
	/**
	 * 建立SVN的CommitClient, 
	 * 這部分使用MockObject替代,
	 * 使得可取得操作相對應內容
	 */
	protected SVNCommitClient createCommitClient(){
		if( this.commitClient == null ){
			this.commitClient = new MockCommitClient();
		}
		return this.commitClient;
	}

	/**
	 * 取得CommitClient,
	 * 用於取得操作的內部資訊
	 * @return
	 */
	public SVNCommitClient getCommitClient(){
		return this.commitClient;
	}
}
