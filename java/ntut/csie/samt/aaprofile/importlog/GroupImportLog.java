package ntut.csie.samt.aaprofile.importlog;

import java.io.*;
import java.util.Iterator;

public class GroupImportLog extends ImportLogger {

	private String fileName = "report/groupReport.xml";
	
	public GroupImportLog(){
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
        		String name = ((GroupLog)log).getName();
        		String logStatus = ((GroupLog)log).getStatus();
        		
        		writer.write("<group name=\"" + name + "\" status=\"" + logStatus + "\"></group>");
        	}
            
        	writer.write("</report>");
        	
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
	}
	
}
