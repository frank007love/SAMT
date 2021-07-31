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
		
		//���o���|�B�����W�ٻP���A�M�n�ק諸�v��
		String path = this.request.getParameter("path");
		String name = this.request.getParameter("name");		
		String type = this.request.getParameter("type");
		
		this.aaManager = AAManager.getInstance();

		//�]�mHeader
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
		
	    try{
		    PrintWriter out = this.response.getWriter();
		    //�R�������v�����,���\�hUI true���T��,�Ϥ��h��false�T��
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
