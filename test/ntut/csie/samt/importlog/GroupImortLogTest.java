package ntut.csie.samt.importlog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ntut.csie.samt.aaprofile.importlog.GroupImportLog;
import ntut.csie.samt.aaprofile.importlog.GroupLog;
import ntut.csie.samt.aaprofile.importlog.ImportLogger;
import ntut.csie.samt.aaprofile.importlog.Log;
import ntut.csie.samt.aaprofile.importlog.logStatus;

import junit.framework.TestCase;

public class GroupImortLogTest extends TestCase {

	private ImportLogger importLog;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
		this.importLog = new GroupImportLog();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 測試儲存log檔案是否正確
	 */
	public void testSave(){
		//建立logs
		Log log1 = new GroupLog("group1",logStatus.Success);
		Log log2 = new GroupLog("group2",logStatus.Success);
		Log log3 = new GroupLog("group3",logStatus.Warning);
		Log log4 = new GroupLog("group4",logStatus.Failure);
		//新增logs
		this.importLog.addLog(log1);
		this.importLog.addLog(log2);
		this.importLog.addLog(log3);
		this.importLog.addLog(log4);
		//儲存logs
		this.importLog.save();
		
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader("WebContent/report/groupReport.xml"));
			
			String line = "";
			String content = "";
			
			while((line = reader.readLine()) != null){
				content += line;
			}
			//驗證結果
			assertTrue(content.contains("<group name=\"group1\" status=\"success\">"));
			assertTrue(content.contains("<group name=\"group2\" status=\"success\">"));
			assertTrue(content.contains("<group name=\"group3\" status=\"warning\">"));
			assertTrue(content.contains("<group name=\"group4\" status=\"failure\">"));
			
			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
}
