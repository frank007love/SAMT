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
	
	public String execute(){
		String account = this.request.getParameter("account");
		
		this.aaManager = AAManager.getInstance();
		
		
		//設置Header與編碼
	    this.response.setContentType("text/xml");        
	    this.response.setHeader("Cache-Control", "no-cache");        
	    this.response.setCharacterEncoding("UTF-8");
	    
	    try{
		    //將判斷帳號是否有效結果傳給View
		    PrintWriter out = this.response.getWriter();
		    out.print(this.validateAccountID(account));
		    out.close();

	    } catch( IOException e){
	    	return null;
	    }
	    
		return null;
	}
	
	/**
	 * 判斷帳號是否已被建立, 建立則傳給View無法新增帳號的訊息
	 * 判斷帳號是否為有效的字元, 數字與英文才為有效
	 * true則已存在,false則不存在
	 * @param name
	 * @return
	 */
	private String validateAccountID(String name){
		
		//判斷帳號是否符合只有英文+數字的格式
		Pattern p = Pattern.compile("[0-9a-zA-Z]*");
		Matcher m = p.matcher(name);
		boolean b = m.matches();
		
		//若帳號可建立且ID閣是正確　則回傳true
		if( b && !this.aaManager.isAccountExist(name) )
			return "true";
		
		return "false";
	}

}
