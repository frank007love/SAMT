package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ntut.csie.samt.manager.AAManager;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class createAccountAction implements ServletRequestAware,ServletResponseAware {

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
		//���o�ϥΪ̿�J���b���P�K�X
		String account = this.request.getParameter("account");
		String passwd = this.request.getParameter("passwd");
		
		aaManager = AAManager.getInstance();
		
		
	    try{
		    PrintWriter out = response.getWriter();
		    
		    //�i��s�W�b��, �åB�^����View���\�Υ��Ѥ��T��
		    if( aaManager.createAccount(account, passwd) ){
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
