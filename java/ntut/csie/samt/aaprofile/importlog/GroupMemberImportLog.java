package ntut.csie.samt.aaprofile.importlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class GroupMemberImportLog extends ImportLogger {

	private String fileName = "report/groupMemberReport.xml";
	
	public GroupMemberImportLog(){
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
        	String lastGroupName ="";
        	//�Ψ��קK�Ĥ@�ӦL����
        	boolean flag = false;
        	while( iter.hasNext() ){
        		Log log = (Log)iter.next();
        		String groupName = ((GroupMemberLog)log).getGroupName();
        		String memberName = ((GroupMemberLog)log).getMemberName();
        		String memberType = ((GroupMemberLog)log).getMemberType();
        		String logStatus = ((GroupMemberLog)log).getStatus();
        		
        		if(lastGroupName != groupName){
        			lastGroupName = groupName;
        			if(flag){
        				writer.write("</group>");
        			}
        			else
        				flag = true;
        			writer.write("<group name=\"" + groupName + "\">");
        		}
        		
        		writer.write("<groupMember name=\"" + memberName + "\" type=\"" + memberType + "\" status=\"" + logStatus + "\"></groupMember>");     		
        	
        		//�̫�@��
        		if( !iter.hasNext() ){
        			writer.write("</group>");
        		}
        	}
        	writer.write("</report>");
        	
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
	}
	
}
