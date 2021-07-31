package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.csie.samt.manager.AAManager;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class addPermissionAction implements ServletRequestAware,ServletResponseAware{

	private AAManager aaManager;
	HttpServletRequest request;
	HttpServletResponse response;
	
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
		//�qClient���o���|�B�n�[�J�������W�ٻP���A
		String name = this.request.getParameter("name") ;
		String type = this.request.getParameter("type") ;
		String path = this.request.getParameter("path") ;
		
		this.aaManager = AAManager.getInstance();
		
		this.response.setContentType("text/xml");        
		this.response.setHeader("Cache-Control", "no-cache");        
		this.response.setCharacterEncoding("UTF-8");
	    
	    try{
	    //�s�W�@���v�����,�ñN�O�_���\�T���^����Client
	    PrintWriter out = this.response.getWriter();
	    if( this.aaManager.addPermission(path, name, type, "") ){
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
