package ntut.csie.samt.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import ntut.csie.samt.manager.AAManager;

public class modifyPermissionAction implements ServletRequestAware{
	
	private AAManager aaManager;
	private HttpServletRequest request;
	
	/*
	 * �Q�Τ��౱����o��setServletRequest
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public String execute() {
		//���o���|�B�����W�ٻP���A�M�n�ק諸�v��
		String path = this.request.getParameter("path");
		String name = this.request.getParameter("name");		
		String type = this.request.getParameter("type");
		String permission = this.request.getParameter("permission");		
		//�ק��v��
		this.aaManager = AAManager.getInstance();
		this.aaManager.modifyPermission(path, name, type, permission);
		
		return null;
	}
}
