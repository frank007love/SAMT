package ntut.csie.samt.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ntut.csie.samt.manager.*;


public class checkGroupNameAction implements ServletRequestAware,ServletResponseAware {

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
		
		aaManager = AAManager.getInstance();
		
		//�]�mHeader�P�s�X
	    this.response.setContentType("text/xml");        
	    this.response.setHeader("Cache-Control", "no-cache");        
	    this.response.setCharacterEncoding("UTF-8");
	    
	    try{
	    //�N�P�_�s�լO�_�s�b���G�ǵ�View
	    PrintWriter out = this.response.getWriter();
	    out.print(validateGroup(this.request.getParameter("groupname")));
	    out.close();
	    
	    } catch( IOException e){
	    	return null;
	    }
	    
		return null;
	}

	/**
	 * �T�{�s�զW�٬O�_�w�s�b
	 * 
	 * @param name �ҭn�T�{���s�զW��
	 * @return �P�_���G
	 */
	private String validateGroup(String name){
		boolean checkFlag = aaManager.findGroup(name);

		//�P�_�s�լO�_�ŦX�u���^��+�Ʀr���榡
		Pattern p = Pattern.compile("[0-9a-zA-Z]*");
		Matcher m = p.matcher(name);
		boolean b = m.matches();
		//�ˬd�q�L�^����view �i�s�W���T��
		if( b && !checkFlag ){
			return "true";
		}
		//�L�k�s�W
		return "false";
	}
}
