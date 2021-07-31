package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class deletePermissionAction implements ServletRequestAware,ServletResponseAware {
	
	private AAManager aaManager;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
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
	
	public String execute() {
		
		//取得路徑、成員名稱與型態和要修改的權限
		String path = this.request.getParameter("path");
		String name = this.request.getParameter("name");		
		String type = this.request.getParameter("type");
		
		this.aaManager = AAManager.getInstance();

		//設置Header
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
		
	    try{
		    PrintWriter out = this.response.getWriter();
		    //刪除成員權限資料,成功則UI true之訊息,反之則給false訊息
		    if( this.aaManager .deletePermission(path, name, type)){
		    	out.print("true");
		    } else {
		    	out.print("false"); 
		    }
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	}
}
