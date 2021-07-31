package ntut.csie.samt.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import ntut.csie.samt.manager.AAManager;

public class modifyPermissionAction implements ServletRequestAware{
	
	private AAManager aaManager;
	private HttpServletRequest request;
	
	/*
	 * 利用反轉控制取得的setServletRequest
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public String execute() {
		//取得路徑、成員名稱與型態和要修改的權限
		String path = this.request.getParameter("path");
		String name = this.request.getParameter("name");		
		String type = this.request.getParameter("type");
		String permission = this.request.getParameter("permission");		
		//修改權限
		this.aaManager = AAManager.getInstance();
		this.aaManager.modifyPermission(path, name, type, permission);
		
		return null;
	}
}
