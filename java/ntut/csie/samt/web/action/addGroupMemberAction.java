package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class addGroupMemberAction implements ServletRequestAware,ServletResponseAware{

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
		
		//取得要加入之群組名稱與要加入的成員名稱與型態
		String groupName = this.request.getParameter("groupName");
		String memberName = this.request.getParameter("memberName");
		String type = this.request.getParameter("type");
		
		aaManager = AAManager.getInstance();
		
	    try{
	    	PrintWriter out = this.response.getWriter();
		    //將加入成員是否成功結果傳給View
	    	if( aaManager.addGroupMember(groupName, memberName, type) ){
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
