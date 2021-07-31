package ntut.csie.samt;

import ntut.csie.samt.aaprofile.*;
import ntut.csie.samt.aaprofile.importlog.*;
import ntut.csie.samt.account.*;
import ntut.csie.samt.manager.*;
import ntut.csie.samt.svncontroll.*;
import ntut.csie.samt.permission.*;
import ntut.csie.samt.importlog.AccountImportLogTest;
import ntut.csie.samt.importlog.GroupImortLogTest;
import ntut.csie.samt.importlog.GroupMemberImportLogTest;
import ntut.csie.samt.importlog.PermissionImportLogTest;
import ntut.csie.samt.group.*;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ntut.csie.samt");
		//$JUnit-BEGIN$
		suite.addTestSuite(AAProfileParserTest.class);
		suite.addTestSuite(IGroupTest.class);
		suite.addTestSuite(AAManagerTest.class);
		suite.addTestSuite(AuthorizationCenterTest.class);
		suite.addTestSuite(PermissionTest.class);
		suite.addTestSuite(RepositoryPathTest.class);
		suite.addTestSuite(AuthorizationEntityTest.class);
		suite.addTestSuite(ApacheAuthenticationEntityTest.class);
		suite.addTestSuite(AuthenticationCenterTest.class);
		suite.addTestSuite(GroupImortLogTest.class);
		suite.addTestSuite(GroupMemberImportLogTest.class);
		suite.addTestSuite(PermissionImportLogTest.class);
		suite.addTestSuite(AccountImportLogTest.class);
		suite.addTestSuite(SVNRepositoryControllerTest.class);
		suite.addTestSuite(SVNAuthenticationEntityTest.class);
		suite.addTestSuite(ImportHandlerTest.class);
		//$JUnit-END$
		return suite;
	}

}
