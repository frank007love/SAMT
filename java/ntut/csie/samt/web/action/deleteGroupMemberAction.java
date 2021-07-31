package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class deleteGroupMemberAction implements ServletRequestAware,ServletResponseAware {
	
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
		//取得View的資訊
		String memberName = request.getParameter("membername");
		String memberType = request.getParameter("membertype");		
		String groupName = request.getParameter("groupname");
		aaManager = AAManager.getInstance();
		
		//user = account
    	if( memberType.equals("user") ){
    		memberType = "account";
    	}

	    try{
	    	PrintWriter out = response.getWriter();

		    //將刪除是否成功結果傳給View
	    	if( aaManager.deleteGroupMember(groupName, memberName, memberType) ){
	    		out.print("true");
	    	}
	    	else{
	    		out.print("flase");
	    	}
		    out.close();
		    
		} catch( IOException e){
		    return null;
		}
		
		return null;
	}
}
