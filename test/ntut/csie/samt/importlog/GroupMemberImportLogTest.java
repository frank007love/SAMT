package ntut.csie.samt.importlog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ntut.csie.samt.aaprofile.importlog.GroupMemberImportLog;
import ntut.csie.samt.aaprofile.importlog.GroupMemberLog;
import ntut.csie.samt.aaprofile.importlog.ImportLogger;
import ntut.csie.samt.aaprofile.importlog.Log;
import ntut.csie.samt.aaprofile.importlog.logStatus;

import junit.framework.TestCase;

public class GroupMemberImportLogTest extends TestCase {

	private ImportLogger importLog;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot", "WebContent/");
		this.importLog = new GroupMemberImportLog();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 測試儲存log檔案是否正確
	 */
	public void testSave(){
		//建立logs
		Log log1 = new GroupMemberLog("group1","member1","account",logStatus.Success);
		Log log2 = new GroupMemberLog("group1","member2","group",logStatus.Failure);
		Log log3 = new GroupMemberLog("group2","member3","account",logStatus.Success);
		Log log4 = new GroupMemberLog("group2","member4","account",logStatus.Success);
		//新增logs
		this.importLog.addLog(log1);
		this.importLog.addLog(log2);
		this.importLog.addLog(log3);
		this.importLog.addLog(log4);
		//儲存logs
		this.importLog.save();
		
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader("WebContent/report/groupMemberReport.xml"));
			
			String line = "";
			String content = "";
			
			while((line = reader.readLine()) != null){
				content += line;
			}			
			//驗證結果                                                       
			assertTrue(content.contains("<group name=\"group1\">"));
			assertTrue(content.contains("<groupMember name=\"member1\" type=\"account\" status=\"success\">"));
			assertTrue(content.contains("<groupMember name=\"member2\" type=\"group\" status=\"failure\">"));
			assertTrue(content.contains("<group name=\"group2\">"));
			assertTrue(content.contains("<groupMember name=\"member3\" type=\"account\" status=\"success\">"));
			assertTrue(content.contains("<groupMember name=\"member4\" type=\"account\" status=\"success\">"));
			
			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
}
