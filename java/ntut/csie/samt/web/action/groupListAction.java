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
	 * �Q�Τ��౱����o��HttpServletResponse
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}
	
	public String execute() {
		//��l�Ƥ@�Ǩt�γ]�p, �pServer Root, Config file path
		InitialServerConfig.init();
		
		aaManager = AAManager.getInstance();
		//���o�Ҧ����s�զW��
		List<String> groupList = aaManager.listAllGroup();

		String xml = this.xmlContent(groupList);
		   
		//�]�wXML Header
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
	 * �N�Ҧ�Group�W�ټg��xml format�^�ǵ�Client
	 * @param groupList �s�զW�٦C��
	 * @return �ഫ�᪺xml format
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
