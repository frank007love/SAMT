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
		//���o�n��M���������|
		String svnpath = this.request.getParameter("svnpath");
		
		aaManager = AAManager.getInstance();
		//���o�����C����ഫ���᪺���e
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
	 * �N���o�����|���� �নxml format
	 * 
	 * @param memberlist ���|����
	 * @return �নxml format�����G
	 */
	private String xmlContent(List<String> memberlist){
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
		
		xml += "<root>";
		
		//�S�������s�b
		if( memberlist != null ){
			
			Iterator<String> iter = memberlist.iterator();
			
			while(iter.hasNext()){
				String member = iter.next();
				
				//�������@�Ӹs��
				if( member.startsWith("@") ){
					member = member.substring(1);
					xml += "<member type=\"group\">" + member + "</member>";
				} else { //�������@�ӨϥΪ�
					xml += "<member type=\"account\">" + member + "</member>";
				}
			}
		}
		
		xml += "</root>";
		
		return xml;
	}
	
}
