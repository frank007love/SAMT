package ntut.csie.samt.web.action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;

import ntut.csie.samt.manager.AAManager;



public class groupMemberListAction implements ServletRequestAware,ServletResponseAware {

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
		//���o�n��M�s�զ������s��
		String groupname = this.request.getParameter("groupname");
		
		aaManager = AAManager.getInstance();
		
		String xml = this.xmlContent(aaManager.listGroupMember(groupname));
		
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");

	    try{
		    //�N��M�s�յ��G�ǵ�View
		    PrintWriter out = this.response.getWriter();
		    out.print(new String(xml.getBytes("UTF-8"),"UTF-8"));
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
	    
		
		return null;
	}
	
	/**
	 * �N���o���s�զ��� �নxml format
	 * 
	 * @param memberlist �s�զ���
	 * @return �নxml format�����G
	 */
	private String xmlContent(List<String> memberlist){
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
		
		xml += "<root>";
		
		//�S���s�զs�b
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
