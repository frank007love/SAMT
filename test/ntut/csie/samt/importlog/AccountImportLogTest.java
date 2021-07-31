package ntut.csie.samt.importlog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ntut.csie.samt.aaprofile.importlog.AccountImportLogger;
import ntut.csie.samt.aaprofile.importlog.AccountLog;
import ntut.csie.samt.aaprofile.importlog.ImportLogger;
import ntut.csie.samt.aaprofile.importlog.Log;
import ntut.csie.samt.aaprofile.importlog.logStatus;

import junit.framework.TestCase;

public class AccountImportLogTest extends TestCase {

	private ImportLogger importLog;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
		this.importLog = new AccountImportLogger();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * �����x�slog�ɮ׬O�_���T
	 */
	public void testSave(){
		//�إ�logs
		Log log1 = new AccountLog("account1",logStatus.Success);
		Log log2 = new AccountLog("account2",logStatus.Failure);
		//�s�Wlogs
		this.importLog.addLog(log1);
		this.importLog.addLog(log2);
		//�x�slogs
		this.importLog.save();
		
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader("WebContent/report/accountReport.xml"));
			
			String line = "";
			String content = "";
			
			while((line = reader.readLine()) != null){
				content += line;
			}
			//���ҵ��G
			assertTrue(content.contains("<account name=\"account1\" status=\"success\">"));
			assertTrue(content.contains("<account name=\"account2\" status=\"failure\">"));
			
			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
}
