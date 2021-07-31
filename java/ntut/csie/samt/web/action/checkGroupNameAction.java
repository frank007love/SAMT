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
		
		aaManager = AAManager.getInstance();
		
		//設置Header與編碼
	    this.response.setContentType("text/xml");        
	    this.response.setHeader("Cache-Control", "no-cache");        
	    this.response.setCharacterEncoding("UTF-8");
	    
	    try{
	    //將判斷群組是否存在結果傳給View
	    PrintWriter out = this.response.getWriter();
	    out.print(validateGroup(this.request.getParameter("groupname")));
	    out.close();
	    
	    } catch( IOException e){
	    	return null;
	    }
	    
		return null;
	}

	/**
	 * 確認群組名稱是否已存在
	 * 
	 * @param name 所要確認之群組名稱
	 * @return 判斷結果
	 */
	private String validateGroup(String name){
		boolean checkFlag = aaManager.findGroup(name);

		//判斷群組是否符合只有英文+數字的格式
		Pattern p = Pattern.compile("[0-9a-zA-Z]*");
		Matcher m = p.matcher(name);
		boolean b = m.matches();
		//檢查通過回報給view 可新增之訊息
		if( b && !checkFlag ){
			return "true";
		}
		//無法新增
		return "false";
	}
}
