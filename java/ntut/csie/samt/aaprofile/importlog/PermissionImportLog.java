package ntut.csie.samt.aaprofile.importlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class PermissionImportLog extends ImportLogger {
	
	private String fileName = "report/permissionReport.xml";
	
	public PermissionImportLog(){
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
	
	
	public void save(){
		Writer writer = null;
        try {
        	writer = new FileWriter(this.fileName);
            
        	Iterator<Log> iter = this.logs.iterator();
        	writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><report>");
        	
        	//�NLog��T��X��xml file
        	String lastPathName ="";
        	int x=0;
        	while( iter.hasNext() ){
        		Log log = (Log)iter.next();
        		String pathName = ((PermissionLog)log).getPathName();
        		String pathMemberName = ((PermissionLog)log).getPathMemberName();
        		String pathMemberType = ((PermissionLog)log).getPathMemberType();
        		String logStatus = ((PermissionLog)log).getStatus();
        		
        		if(lastPathName != pathName){
        			lastPathName = pathName;
        			if(x>0){
        				writer.write("</path>");
        			}
        			else
        				x++;
        			writer.write("<path name=\"" + pathName + "\">");
        		}
        		
        		writer.write("<pathmember name=\"" + pathMemberName + "\" type=\"" + pathMemberType + "\" status=\"" + logStatus + "\"></pathmember>");  
        		//�̫�@��
        		if( !iter.hasNext() ){
        			writer.write("</path>");
        		}
        	}
            
        	writer.write("</report>");
        	
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
