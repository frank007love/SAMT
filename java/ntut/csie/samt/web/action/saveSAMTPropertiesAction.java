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
	
	public String execute(){
		//���o�ϥΪ̿�J��Properties
		String authenticationPath = this.request.getParameter("authenticationPath");
		String authorizationPath = this.request.getParameter("authorizationPath");
		String svnURL = this.request.getParameter("svnURL");
		
		this.aaManager = AAManager.getInstance();
		
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
	
	    try{
	    	PrintWriter out = this.response.getWriter();
	    	//�x�sProperties���e
	    	try{
	    		//�]�mProperties
	    		this.aaManager.setAuthorizationPath(authorizationPath);
	    		this.aaManager.setAuthenticationPath(authenticationPath);
	    		this.aaManager.setURL(svnURL);
	    		//�NProperties�s�J�ɮ�
	    		this.aaManager.saveProperties();
	    		
	    		//�H�s�պA���sŪ��
	    		AAManager.Release();
	    		this.aaManager = AAManager.getInstance();
	    		//�^�����\�T��
	    		out.print("true");
	    	} catch (Exception e){
	    		//�^�����ѰT��
	    		out.print("false");
	    	}
		    out.close();
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	}
	
}
