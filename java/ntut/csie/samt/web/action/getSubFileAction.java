package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.tmatesoft.svn.core.SVNException;

import ntut.csie.samt.manager.AAManager;
import ntut.csie.samt.svncontroll.SVNRepositoryController;

public class getSubFileAction implements ServletRequestAware,ServletResponseAware {

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
		String parent = this.request.getParameter("parent");
		
		this.aaManager = AAManager.getInstance();
		
		//設定XML Header
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
		
		//xml file format
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><root>\n";
		
		//取得parent下所有的dir與file
		try{
			List<String> dirList = this.aaManager.getRepositorySubFile(parent,"DIR");
			List<String> fileList = this.aaManager.getRepositorySubFile(parent,"FILE");
			
			//將取得的資訊轉換成XML檔案格式
			xml += this.dirToXmlContent(dirList);
			xml += this.fileToXmlContent(fileList);
			xml += "</root>";
		
		} catch(SVNException e){
			e.printStackTrace();
			xml = "";
		}

	    try{
	    	//將XML內容傳給VIEW
		    PrintWriter out = this.response.getWriter();
		    out.print(new String(xml.getBytes("UTF-8"),"UTF-8"));
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	}
	
	/**
	 * 將取得的資料夾列表轉換成XML格式
	 * @param dirList
	 * @return
	 */
	private String dirToXmlContent(List<String> dirList){
		String content = "";
		
		if( dirList != null ){
			
			Iterator<String> iter  = dirList.iterator();
			//轉換資料
			while( iter.hasNext() ){
				String name = iter.next();
		
				content += "<subpath type='dir'>";
				content += name;
				content += "</subpath>\n";
			}
			
		}	
		return content;
	}
	
	/**
	 * 將取得的檔案列表轉換成XML格式
	 * @param fileList
	 * @return
	 */
	private String fileToXmlContent(List<String> fileList){
		String content = "";
		
		if( fileList != null ){
			
			Iterator<String> iter  = fileList.iterator();
			//轉換資料
			while( iter.hasNext() ){
				String name = iter.next();
		
				content += "<subpath type='file'>";
				content += name;
				content += "</subpath>\n";
			}
			
		}	
		return content;
	}
	
}
