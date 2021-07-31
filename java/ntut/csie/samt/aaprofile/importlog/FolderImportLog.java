package ntut.csie.samt.aaprofile.importlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class FolderImportLog extends ImportLogger {
	private String fileName = "report/svnfolderReport.xml";

	public FolderImportLog(){
		super();
		
		this.fileName = this.WebRootPath +  this.fileName;
		this.logFile = new File( this.fileName);
	
		//檔案不存在則建立檔案
		if( !this.logFile.exists() ){
			try{
				this.logFile.createNewFile();
			} catch( IOException e ){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 將log資訊存入XML檔案中
	 */
	public void save(){
		Writer writer = null;
        try {
        	writer = new FileWriter(this.fileName);
            
        	Iterator<Log> iter = this.logs.iterator();
        	
        	writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><report>");
        	
        	//將Log資訊輸出到xml file
        	while( iter.hasNext() ){
        		Log log = (Log)iter.next();
        		String path = ((FolderLog)log).getPath();
        		String logStatus = ((FolderLog)log).getStatus();
        		
        		writer.write("<path name=\"" + path + "\" status=\"" + logStatus + "\"></path>");
        	}
            
        	writer.write("</report>");
        	
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
	}
}
