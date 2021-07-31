package ntut.csie.samt.aaprofile.importlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class AccountImportLogger extends ImportLogger{
	private String fileName = "report/accountReport.xml";
	
	public AccountImportLogger(){
		super();
		this.fileName = this.WebRootPath +  this.fileName;
		this.logFile = new File( this.fileName);
		//�ɮפ��s�b�h�إ��ɮ�
		if( !this.logFile.exists() ){
			try{
				this.logFile.createNewFile();
			} catch( IOException e ){
				e.printStackTrace();
			}
		}
	}

	/**
	 * �Nlog��T�s�JXML�ɮפ�
	 */
	public void save(){
		Writer writer = null;
        try {
        	writer = new FileWriter(this.fileName);
            
        	Iterator<Log> iter = this.logs.iterator();
        	
        	writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><report>");
        	
        	//�NLog��T��X��xml file
        	while( iter.hasNext() ){
        		Log log = (Log)iter.next();
        		String accountName = ((AccountLog)log).getAccountName();
        		String logStatus = ((AccountLog)log).getStatus();
        		writer.write("<account name=\"" + accountName + "\" status=\"" + logStatus + "\"></account>");
        	}
            
        	writer.write("</report>");
        	
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
	}
}
