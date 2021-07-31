package ntut.csie.samt.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import ntut.csie.samt.manager.AAManager;

public class deleteGroupAction implements ServletRequestAware{

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
				
		//從Client取得要刪除的群組名稱
		String GroupName = this.request.getParameter("name");
		
		aaManager = AAManager.getInstance();

		aaManager.deleteGroup(GroupName);
		
		return null;
	}
}
