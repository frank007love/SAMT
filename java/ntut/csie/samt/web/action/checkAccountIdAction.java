package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ntut.csie.samt.manager.AAManager;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class checkAccountIdAction implements ServletRequestAware,ServletResponseAware  {

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
	
	public String execute(){
		String account = this.request.getParameter("account");
		
		this.aaManager = AAManager.getInstance();
		
		
		//�]�mHeader�P�s�X
	    this.response.setContentType("text/xml");        
	    this.response.setHeader("Cache-Control", "no-cache");        
	    this.response.setCharacterEncoding("UTF-8");
	    
	    try{
		    //�N�P�_�b���O�_���ĵ��G�ǵ�View
		    PrintWriter out = this.response.getWriter();
		    out.print(this.validateAccountID(account));
		    out.close();

	    } catch( IOException e){
	    	return null;
	    }
	    
		return null;
	}
	
	/**
	 * �P�_�b���O�_�w�Q�إ�, �إ߫h�ǵ�View�L�k�s�W�b�����T��
	 * �P�_�b���O�_�����Ī��r��, �Ʀr�P�^��~������
	 * true�h�w�s�b,false�h���s�b
	 * @param name
	 * @return
	 */
	private String validateAccountID(String name){
		
		//�P�_�b���O�_�ŦX�u���^��+�Ʀr���榡
		Pattern p = Pattern.compile("[0-9a-zA-Z]*");
		Matcher m = p.matcher(name);
		boolean b = m.matches();
		
		//�Y�b���i�إߥBID�լO���T�@�h�^��true
		if( b && !this.aaManager.isAccountExist(name) )
			return "true";
		
		return "false";
	}

}
