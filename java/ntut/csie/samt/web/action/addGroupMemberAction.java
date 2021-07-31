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
		
		//���o�n�[�J���s�զW�ٻP�n�[�J�������W�ٻP���A
		String groupName = this.request.getParameter("groupName");
		String memberName = this.request.getParameter("memberName");
		String type = this.request.getParameter("type");
		
		aaManager = AAManager.getInstance();
		
	    try{
	    	PrintWriter out = this.response.getWriter();
		    //�N�[�J�����O�_���\���G�ǵ�View
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
