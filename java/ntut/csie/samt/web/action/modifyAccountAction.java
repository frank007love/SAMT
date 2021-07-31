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
		//取得要修改帳號之資訊, 包含舊帳號,新帳號與新密碼
		String oldAccount = this.request.getParameter("oldaccount");
		String newAccount = this.request.getParameter("newaccount");
		String newPasswd = this.request.getParameter("newpasswd");
		
		this.aaManager = AAManager.getInstance();

	    try{
		    //將修改是否成功結果傳給View
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
