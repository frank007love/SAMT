package ntut.csie.samt.web.action;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;


//extends FileUploadInterceptor 
public class importAAProfileAction  implements ServletRequestAware,ServletResponseAware {

	private AAManager aaManager;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private File upload;
	
	/*
	 * �Q�Τ��౱����o��setServletRequest
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	/*
	 * �Q�Τ��౱����o��HttpServletResponse
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}
	
	/**
	 * �ھ�action�B�z�פJ�{��
	 */
	private int importProcess(String action){
		if( action.equals("1") ){
			//�פJ�s��
			return this.aaManager.importGroups();
		} else if( action.equals("2") ){
			//�פJ�s�զ���
			return this.aaManager.importGroupMember();
		} else if( action.equals("3") ){
			//�פJ�v��
			return this.aaManager.importPermissions();
		} else if( action.equals("4") ){
			//�פJ�b��
			return this.aaManager.importAccounts();
		} else if( action.equals("5") ){
			//�פJ�s�W��Ƨ�
			int flag = this.aaManager.importSVNFolder();
			if( flag != 0 ){
				boolean writable_flag = this.aaManager.isWritable();
				this.aaManager.setWritable(true);
				this.aaManager.saveAuthenticationFile();
				this.aaManager.saveAuthorizationFile();
				this.aaManager.setWritable(writable_flag);
			} else {
				//�o�Ϳ��~�����sŪ��
				AAManager.Release();
				this.aaManager = AAManager.getInstance();
			}
			return flag;
		}
		//��Exception�o�ͫh,�פJ����
		return 0;
	}
	
	public String execute() {
		//���oView���ШD
		String operation = this.request.getParameter("operation");
		//���o�W�Ǥ��ɮ�
		String path = System.getProperty("uploadFile");
		this.upload = new File(path);
		
		this.aaManager = AAManager.getInstance();
		
		this.aaManager.importProfile(this.upload);
		
		
		//�i���ɮ׶פJ
	    try{
		    PrintWriter out = response.getWriter();
		    
			//�����o��l�O�_�i�H�g�J�ɮת�Flag
			//�b�פJ�ɳ]��false, �������\�~���\�g�J�ɮ�
			//�̫�A�Nflag�]�^�쥻��value
		    
			boolean flag = this.aaManager.isWritable();
			//�]�m���x�s�ɮ�
			this.aaManager.setWritable(false);
			//�B�z�פJ�ɮ�
		    out.print(this.importProcess(operation));
		    //�]�^�쥻��value
		    this.aaManager.setWritable(flag);
		    
		    
		    out.close();
		    
		} catch( IOException e){
		    return null;
		}
		
		return null;
	}
}
