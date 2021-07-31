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
	 * �Q�Τ��౱����o��HttpServletResponse
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}
	
	public String execute() {
		this.aaManager = AAManager.getInstance();
		//���o�Ҧ����s�զW��
		List<String> accountList = aaManager.listAllAccount();

		String xml = this.xmlContent(accountList);
		   
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
	 * �N�Ҧ��b���W�ټg��xml format�^�ǵ�Client
	 * @param accountList �b���W�٦C��
	 * @return �ഫ�᪺xml format
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
