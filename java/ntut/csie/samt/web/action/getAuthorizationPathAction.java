package ntut.csie.samt.web.action;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletResponseAware;

import java.io.PrintWriter;
import java.io.IOException;

import ntut.csie.samt.manager.*;

public class getAuthorizationPathAction implements ServletResponseAware{
	
	private AAManager aaManager;
	private HttpServletResponse response;
	
	/*
	 * 利用反轉控制取得的HttpServletResponse
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}
	
	public String execute() {
		this.aaManager = AAManager.getInstance();

	    this.response.setContentType("text/xml; charset=UTF-8");          
	    this.response.setHeader("Cache-Control", "no-cache");
		
	    try{
		    PrintWriter out = this.response.getWriter();
		    out.print(this.aaManager.getAuthorizationPath());
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	}
}
