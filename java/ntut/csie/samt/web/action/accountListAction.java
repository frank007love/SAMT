package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.AAManager;

public class accountListAction implements ServletResponseAware{

	private AAManager aaManager;
	private HttpServletResponse response;
	
	/*
	 * 利用反轉控制取得的HttpServletResponse
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}
	
	public String execute() {
		this.aaManager = AAManager.getInstance();
		//取得所有的群組名稱
		List<String> accountList = aaManager.listAllAccount();

		String xml = this.xmlContent(accountList);
		   
		//設定XML Header
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
		
	    try{
		    PrintWriter out = this.response.getWriter();
		    out.print(new String(xml.getBytes("UTF-8"),"UTF-8"));
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	}
	
	/**
	 * 將所有帳號名稱寫成xml format回傳給Client
	 * @param accountList 帳號名稱列表
	 * @return 轉換後的xml format
	 */
	private String xmlContent(List<String> accountList){
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
		
		xml += "<root>";
		
		if( accountList != null ){
		
			Iterator<String> iter = accountList.iterator();
			
			while(iter.hasNext()){
				xml += "<account>" + iter.next() + "</account>";
			}
			
			xml += "</root>";
		}
		return xml;
	}
}
