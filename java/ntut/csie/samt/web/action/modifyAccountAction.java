package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class modifyAccountAction implements ServletRequestAware,ServletResponseAware {

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
		//���o�n�ק�b������T, �]�t�±b��,�s�b���P�s�K�X
		String oldAccount = this.request.getParameter("oldaccount");
		String newAccount = this.request.getParameter("newaccount");
		String newPasswd = this.request.getParameter("newpasswd");
		
		this.aaManager = AAManager.getInstance();

	    try{
		    //�N�ק�O�_���\���G�ǵ�View
		    PrintWriter out = response.getWriter();
		    if( this.aaManager.modifyAccount(oldAccount, newAccount, newPasswd) ){
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
