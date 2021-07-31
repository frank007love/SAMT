package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class getPermissionAction implements ServletRequestAware,ServletResponseAware {
	
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
		//���o���|�B�����W�ٻP���A
		String path = this.request.getParameter("path");
		String name = this.request.getParameter("name");		
		String type = this.request.getParameter("type");
		//���o�v��
		this.aaManager = AAManager.getInstance();
		String permission = this.aaManager.getPermission(path, name, type);
		
	    try{
	    	//�^���v����UI
	    	PrintWriter out = this.response.getWriter();
		    out.print(permission);
		    out.close();
		    
		} catch( IOException e){
		    return null;
		}
		
		return null;
	}
}
