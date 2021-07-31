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
	 * 利用反轉控制取得的setServletRequest
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	/*
	 * 利用反轉控制取得的HttpServletResponse
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}
	
	/**
	 * 根據action處理匯入程序
	 */
	private int importProcess(String action){
		if( action.equals("1") ){
			//匯入群組
			return this.aaManager.importGroups();
		} else if( action.equals("2") ){
			//匯入群組成員
			return this.aaManager.importGroupMember();
		} else if( action.equals("3") ){
			//匯入權限
			return this.aaManager.importPermissions();
		} else if( action.equals("4") ){
			//匯入帳號
			return this.aaManager.importAccounts();
		} else if( action.equals("5") ){
			//匯入新增資料夾
			int flag = this.aaManager.importSVNFolder();
			if( flag != 0 ){
				boolean writable_flag = this.aaManager.isWritable();
				this.aaManager.setWritable(true);
				this.aaManager.saveAuthenticationFile();
				this.aaManager.saveAuthorizationFile();
				this.aaManager.setWritable(writable_flag);
			} else {
				//發生錯誤應重新讀取
				AAManager.Release();
				this.aaManager = AAManager.getInstance();
			}
			return flag;
		}
		//有Exception發生則,匯入失敗
		return 0;
	}
	
	public String execute() {
		//取得View的請求
		String operation = this.request.getParameter("operation");
		//取得上傳之檔案
		String path = System.getProperty("uploadFile");
		this.upload = new File(path);
		
		this.aaManager = AAManager.getInstance();
		
		this.aaManager.importProfile(this.upload);
		
		
		//進行檔案匯入
	    try{
		    PrintWriter out = response.getWriter();
		    
			//先取得原始是否可以寫入檔案的Flag
			//在匯入時設為false, 全部成功才允許寫入檔案
			//最後再將flag設回原本的value
		    
			boolean flag = this.aaManager.isWritable();
			//設置不儲存檔案
			this.aaManager.setWritable(false);
			//處理匯入檔案
		    out.print(this.importProcess(operation));
		    //設回原本的value
		    this.aaManager.setWritable(flag);
		    
		    
		    out.close();
		    
		} catch( IOException e){
		    return null;
		}
		
		return null;
	}
}
