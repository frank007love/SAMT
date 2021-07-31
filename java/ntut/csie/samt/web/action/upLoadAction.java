package ntut.csie.samt.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import com.opensymphony.xwork2.ActionSupport;

public class upLoadAction extends ActionSupport {

	private static final long serialVersionUID = 572146812454l;

	private final String[] messageString = { "�ɮ������Dxml�ɡA�Э��s�פJ" , "�פJ���\" , "�פJ����" 
			,"�W����쬰��"};
	
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	private String imageFileName;
	private String message;
	
	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	private void copy(File source, File target) {
		try{
			BufferedReader reader = null;
			Writer writer = null;
			try{
				if( !target.exists() ){
					target.createNewFile();
				}
				
				reader = new BufferedReader(new FileReader(source));
				writer = new FileWriter(target);
				
				String line ="";
				
	            while ((line = reader.readLine()) != null) {
	            	line = line.trim() + "\n";
	            	writer.write(line);
	            }
			
	            writer.close();
	            reader.close();
	            
			} catch (IOException e){
				e.printStackTrace();
				writer.close();
		        reader.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
			
	}

	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	/**
	 * �T�{���ɦW�O�_��xml
	 * @return
	 */
	private boolean validateFileName(){
		if( this.uploadFileName == null ){
			this.message = this.messageString[3];
			return false;
		} else if( !this.uploadFileName.endsWith(".xml")){
			this.message = this.messageString[0];
			return false;
		}
		return true;
	}
	
	public String execute() {

		if( !this.validateFileName() ){
			return ERROR;
		}
		
		try{	
			//�ɮפ��s�b�N��Exception
			if( !this.upload.exists() )
				throw new Exception();
			
			
			File copy = new File(this.upload.getParent() + "copy");
			//�ƻs�@��
			this.copy(this.upload, copy);
			System.setProperty("uploadFile", copy.getPath());
			
		} catch( Exception e ){
			//�]�m���ѰT��
			this.setMessage(this.messageString[2]);
			return ERROR;
		}
		
		return SUCCESS;
	}
}
