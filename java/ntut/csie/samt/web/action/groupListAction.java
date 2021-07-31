package ntut.csie.samt.web.action;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;
import org.apache.struts2.interceptor.ServletResponseAware;

import ntut.csie.samt.manager.*;
import ntut.csie.samt.web.*;

public class groupListAction implements ServletResponseAware{

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
		//初始化一些系統設計, 如Server Root, Config file path
		InitialServerConfig.init();
		
		aaManager = AAManager.getInstance();
		//取得所有的群組名稱
		List<String> groupList = aaManager.listAllGroup();

		String xml = this.xmlContent(groupList);
		   
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
	 * 將所有Group名稱寫成xml format回傳給Client
	 * @param groupList 群組名稱列表
	 * @return 轉換後的xml format
	 */
	private String xmlContent(List<String> groupList){
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
		
		xml += "<root>";
		
		if( groupList != null ){
		
			Iterator<String> iter = groupList.iterator();
			
			while(iter.hasNext()){
				xml += "<groupname>" + iter.next() + "</groupname>";
			}
			
			xml += "</root>";
		}
		return xml;
	}
}
