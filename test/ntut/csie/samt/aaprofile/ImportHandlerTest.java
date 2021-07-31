package ntut.csie.samt.aaprofile;

import junit.framework.TestCase;
import ntut.csie.samt.aaprofile.ImportHandler;

import java.io.*;

public class ImportHandlerTest extends TestCase {

	private ImportHandler ih;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("ntut.csie.samt.ApplicationRoot","WebContent/");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 測試匯入的Exception Handling, 使用AAManager的null point
	 */
	public void testImport(){
		
		ih = new ImportHandler(null);
		ih.importProfile(new File("WebContent/TestFile/AAProfileTest/AAProfileTest.xml"));
		
		//測試匯入帳號的Exception Handling
		assertTrue( ih.importAccounts() == 0);	
		File report = new File("WebContent/report/accountReport.xml");
		try{
			BufferedReader reader = new BufferedReader(new FileReader(report));
			String line = reader.readLine();
			
			//correct id
			String []names = { "oph","ztgao","tshsu","yc","ctchen","franklin",
					"lenwind","frankcheng"};
			String []buf = line.split("\"");
			//測試XML 內的檔案帳號與Log狀態是否正確
			//compare id
			for( int i = 0 ; i <names.length ; i++ ){
				String name = buf[(i+1)*4+1];
				String m = "Correct="+names[i] +","+"Buf="+name;
				assertTrue( m,name.equals(names[i]) );
			}
			//compare status
			for( int i = 7 ; i <=35 ; i+=4 ){
				assertTrue( buf[i].equals("failure") );
			}
			
		} catch ( IOException e){
			e.printStackTrace();
			assert(false);
		}
		
		//測試匯入群組的Exception Handling
		assertTrue( ih.importGroups() == 0);
		report = new File("WebContent/report/groupReport.xml");
		try{
			BufferedReader reader = new BufferedReader(new FileReader(report));
			String line = reader.readLine();
			
			//correct id
			String []names = { "admin","96","95","old"};
			String []buf = line.split("\"");
			//測試XML 內的檔案帳號與Log狀態是否正確
			//compare id
			for( int i = 0 ; i <names.length ; i++ ){
				String name = buf[(i+1)*4+1];
				String m = "Correct="+names[i] +","+"Buf="+name;
				assertTrue( m,name.equals(names[i]) );
			}
			//compare status
			for( int i = 7 ; i <=19 ; i+=4 ){
				assertTrue( buf[i].equals("failure") );
			}
			
		} catch ( IOException e){
			e.printStackTrace();
			assert(false);
		}
		
		//測試匯入群組成員的Exception Handling
		assertTrue( ih.importGroupMember() == 0);
		report = new File("WebContent/report/groupMemberReport.xml");
		try{
			BufferedReader reader = new BufferedReader(new FileReader(report));
			String line = reader.readLine();

			String []buf = line.split("\"");
			//測試XML 內的檔案帳號與Log狀態是否正確
			//Group admin
			assertTrue( buf[5].equals("admin") );
			//admin's Member
			assertTrue( buf[7].equals("95") );
			assertTrue( buf[9].equals("group") );
			assertTrue( buf[11].equals("failure") );
			assertTrue( buf[13].equals("tshsu") );
			assertTrue( buf[15].equals("account") );
			assertTrue( buf[17].equals("failure") );
			assertTrue( buf[19].equals("ctchen") );
			assertTrue( buf[21].equals("account") );
			assertTrue( buf[23].equals("failure") );
			//Group 96
			assertTrue( buf[25].equals("96") );
			//96's Member
			assertTrue( buf[27].equals("franklin") );
			assertTrue( buf[29].equals("account") );
			assertTrue( buf[31].equals("failure") );
			assertTrue( buf[33].equals("lenwind") );
			assertTrue( buf[35].equals("account") );
			assertTrue( buf[37].equals("failure") );
			assertTrue( buf[39].equals("frankcheng") );
			assertTrue( buf[41].equals("account") );
			assertTrue( buf[43].equals("failure") );
			//Group 95
			assertTrue( buf[45].equals("95") );
			//95's Member
			assertTrue( buf[47].equals("oph") );
			assertTrue( buf[49].equals("account") );
			assertTrue( buf[51].equals("failure") );
			assertTrue( buf[53].equals("ztgao") );
			assertTrue( buf[55].equals("account") );
			assertTrue( buf[57].equals("failure") );
			//Group old
			assertTrue( buf[59].equals("old") );
			//old's Member
			assertTrue( buf[61].equals("yc") );
			assertTrue( buf[63].equals("account") );
			assertTrue( buf[65].equals("failure") );
			assertTrue( buf[67].equals("tshsu") );
			assertTrue( buf[69].equals("account") );
			assertTrue( buf[71].equals("failure") );
			assertTrue( buf[73].equals("ctchen") );
			assertTrue( buf[75].equals("account") );
			assertTrue( buf[77].equals("failure") );
			
		} catch ( IOException e){
			e.printStackTrace();
			assert(false);
		}
		
		assertTrue( ih.importPermissions() == 0);
	}

}
