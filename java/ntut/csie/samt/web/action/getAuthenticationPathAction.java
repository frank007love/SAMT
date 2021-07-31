package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class getAuthenticationPathAction implements ServletResponseAware {
	
	private AAManager aaManager;
	private HttpServletResponse response;
	
	/*
	 * �Q�Τ��౱����o��HttpServletResponse
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}
	
	public String execute() {
		
		this.aaManager = AAManager.getInstance();

		//�]�mHeader
	    this.response.setContentType("text/xml; charset=UTF-8");          
	    this.response.setHeader("Cache-Control", "no-cache");
		
	    try{
		    PrintWriter out =  this.response.getWriter();
		    out.print(this.aaManager.getAuthenticationPath());
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	}
}