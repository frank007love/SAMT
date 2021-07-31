package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.csie.samt.manager.AAManager;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class addPermissionAction implements ServletRequestAware,ServletResponseAware{

	private AAManager aaManager;
	HttpServletRequest request;
	HttpServletResponse response;
	
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
		//從Client取得路徑、要加入之成員名稱與型態
		String name = this.request.getParameter("name") ;
		String type = this.request.getParameter("type") ;
		String path = this.request.getParameter("path") ;
		
		this.aaManager = AAManager.getInstance();
		
		this.response.setContentType("text/xml");        
		this.response.setHeader("Cache-Control", "no-cache");        
		this.response.setCharacterEncoding("UTF-8");
	    
	    try{
	    //新增一筆權限資料,並將是否成功訊息回報給Client
	    PrintWriter out = this.response.getWriter();
	    if( this.aaManager.addPermission(path, name, type, "") ){
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
