package ntut.csie.samt.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import java.io.PrintWriter;
import java.io.IOException;

import ntut.csie.samt.manager.*;

public class modifyGroupNameAction implements ServletRequestAware,ServletResponseAware {
	
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
	
	public String execute() {
		
		this.aaManager = AAManager.getInstance();
		//���o�Ȥ�ݨϥΪ̿�J���s�շs�W��
		String oldname = this.request.getParameter("oldname");
		String newname = this.request.getParameter("newname");
		
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
		
	    try{
		    PrintWriter out = this.response.getWriter();
		    //�ק�s�զW�٦��\�h�^�Ǧ��\�T��, �Ϥ��h�^�ǥ��ѰT��
		    if( this.aaManager.modifyGroupName(oldname, newname) ){
		    	out.print(new String("true"));
		    } else {
		    	out.print(new String("false"));
		    }
		 
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	}
}
