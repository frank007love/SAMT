package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class saveSAMTPropertiesAction implements ServletRequestAware,ServletResponseAware {
	
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
	
	public String execute(){
		//取得使用者輸入的Properties
		String authenticationPath = this.request.getParameter("authenticationPath");
		String authorizationPath = this.request.getParameter("authorizationPath");
		String svnURL = this.request.getParameter("svnURL");
		
		this.aaManager = AAManager.getInstance();
		
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
	
	    try{
	    	PrintWriter out = this.response.getWriter();
	    	//儲存Properties內容
	    	try{
	    		//設置Properties
	    		this.aaManager.setAuthorizationPath(authorizationPath);
	    		this.aaManager.setAuthenticationPath(authenticationPath);
	    		this.aaManager.setURL(svnURL);
	    		//將Properties存入檔案
	    		this.aaManager.saveProperties();
	    		
	    		//以新組態重新讀取
	    		AAManager.Release();
	    		this.aaManager = AAManager.getInstance();
	    		//回報成功訊息
	    		out.print("true");
	    	} catch (Exception e){
	    		//回報失敗訊息
	    		out.print("false");
	    	}
		    out.close();
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	}
	
}
