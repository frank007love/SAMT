package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class pathMemberListAction implements ServletRequestAware,ServletResponseAware {
	
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
		//取得要找尋成員的路徑
		String svnpath = this.request.getParameter("svnpath");
		
		aaManager = AAManager.getInstance();
		//取得成員列表所轉換之後的內容
		String xml = this.xmlContent(aaManager.getPathMemberList(svnpath));

		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
	
	    try{
		    PrintWriter out = this.response.getWriter();
		    out.print(xml);
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	} 

	/**
	 * 將取得之路徑成員 轉成xml format
	 * 
	 * @param memberlist 路徑成員
	 * @return 轉成xml format的結果
	 */
	private String xmlContent(List<String> memberlist){
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
		
		xml += "<root>";
		
		//沒有成員存在
		if( memberlist != null ){
			
			Iterator<String> iter = memberlist.iterator();
			
			while(iter.hasNext()){
				String member = iter.next();
				
				//成員為一個群組
				if( member.startsWith("@") ){
					member = member.substring(1);
					xml += "<member type=\"group\">" + member + "</member>";
				} else { //成員為一個使用者
					xml += "<member type=\"account\">" + member + "</member>";
				}
			}
		}
		
		xml += "</root>";
		
		return xml;
	}
	
}
