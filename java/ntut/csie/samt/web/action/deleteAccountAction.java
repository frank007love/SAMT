package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.csie.samt.manager.AAManager;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class deleteAccountAction implements ServletRequestAware,ServletResponseAware {

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

		String account = this.request.getParameter("account");
		
		this.aaManager = AAManager.getInstance();
		
	    try{
		    //�N�R���b���O�_���\���G�ǵ�View
		    PrintWriter out = response.getWriter();
		    //�R�����\
		    if( this.aaManager.deleteAccount(account) ){
		    	out.print("true");
		    } else { //�R������
		    	out.print("false");
		    }
		    out.close();
		    
		} catch( IOException e){
		    return null;
		}
		    
		return null;
	}
	
}
