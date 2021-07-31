package ntut.csie.samt.importlog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ntut.csie.samt.aaprofile.importlog.ImportLogger;
import ntut.csie.samt.aaprofile.importlog.Log;
import ntut.csie.samt.aaprofile.importlog.PermissionImportLog;
import ntut.csie.samt.aaprofile.importlog.PermissionLog;
import ntut.csie.samt.aaprofile.importlog.logStatus;

import junit.framework.TestCase;

public class PermissionImportLogTest extends TestCase {

	private ImportLogger importLog;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
		this.importLog = new PermissionImportLog();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * �����x�slog�ɮ׬O�_���T
	 */
	public void testSave(){
		//�إ�logs
		Log log1 = new PermissionLog("/JCIS/java","member1","account",logStatus.Success);
		Log log2 = new PermissionLog("/JCIS/java","member2","group",logStatus.Failure);
		Log log3 = new PermissionLog("/JCIS/test","member3","account",logStatus.Success);
		Log log4 = new PermissionLog("/JCIS/test","member4","account",logStatus.Success);
		//�s�Wlogs
		this.importLog.addLog(log1);
		this.importLog.addLog(log2);
		this.importLog.addLog(log3);
		this.importLog.addLog(log4);
		//�x�slogs
		this.importLog.save();
		
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader("WebContent/report/permissionReport.xml"));
			
			String line = "";
			String content = "";
			
			while((line = reader.readLine()) != null){
				content += line;
			}			
			//���ҵ��G                                                       
			assertTrue(content.contains("<path name=\"/JCIS/java\">"));
			assertTrue(content.contains("<pathmember name=\"member1\" type=\"account\" status=\"success\">"));
			assertTrue(content.contains("<pathmember name=\"member2\" type=\"group\" status=\"failure\">"));
			assertTrue(content.contains("<path name=\"/JCIS/test\">"));
			assertTrue(content.contains("<pathmember name=\"member3\" type=\"account\" status=\"success\">"));
			assertTrue(content.contains("<pathmember name=\"member4\" type=\"account\" status=\"success\">"));
			
			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
}
