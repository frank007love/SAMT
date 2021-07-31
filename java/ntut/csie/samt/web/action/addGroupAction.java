package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import ntut.csie.samt.manager.AAManager;
import org.apache.struts2.interceptor.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class addGroupAction implements ServletRequestAware,ServletResponseAware {

	private AAManager aaManager;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	/*
	 * 利用反轉控制取得的setServletRequest
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	/*
	 * 利用反轉控制取得的HttpServletResponse
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}
	
	public String execute() {

		String newGroupName = this.request.getParameter("name");
		
		aaManager = AAManager.getInstance();
		aaManager.addGroup(newGroupName);
		
	    try{
		    //將判斷群組是否存在結果傳給View
		    PrintWriter out = response.getWriter();
		    out.print(newGroupName);
		    out.close();
		    
		} catch( IOException e){
		    return null;
		}
		    
		return null;
	}
	
}
