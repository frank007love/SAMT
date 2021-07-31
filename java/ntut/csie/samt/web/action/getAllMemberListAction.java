package ntut.csie.samt.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import ntut.csie.samt.manager.AAManager;

public class getAllMemberListAction implements ServletRequestAware,ServletResponseAware {
	
	private AAManager aaManager;
	private String searchname = "";
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
		String op = request.getParameter("op");
		//���o�n�s�W���������|
		this.searchname = request.getParameter("searchname");
		this.aaManager = AAManager.getInstance();
		
		List<String> originMemberList = null;
		List<String> grouplist = this.aaManager.listAllGroup();
		System.out.println(grouplist.size());
		List<String> accountlist = this.aaManager.listAllAccount();
		if( op.equals("path")){
			//���o���|�U�Ҧ�����
			if( grouplist == null ){
				grouplist = new ArrayList<String>();
			}
			grouplist.add("*"); //�v���i�H�ϥ�*��Ҧ����b���P�Ҧ��s��
			
			originMemberList = this.aaManager.getPathMemberList(this.searchname);
		} else if( op.equals("group") ){
			//���o�s�դU�Ҧ�����
			originMemberList = this.aaManager.listGroupMember(this.searchname);
		}
		
		//xml translate
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><root>\n";
		//�Ngroup�ഫ��xml
		
		xml += this.groupToXmlContent(grouplist,originMemberList);
		//�Naccount�ഫ��xml
		xml += this.accountToXmlContent(accountlist, originMemberList);
		
		xml += "</root>";
		
	    response.setContentType("text/xml; charset=UTF-8");          
	    response.setHeader("Cache-Control", "no-cache");
		
	    try{
		    //�N�Ҧ������C��ǵ�View,�]�t�b���P�s��
		    PrintWriter out = response.getWriter();
		    out.print(new String(xml.getBytes("UTF-8"),"UTF-8"));
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	} 
	/**
	 * �N�i�[�J�s�զC���নxml���榡
	 * �����쥻�s�b��
	 * @param grouplist �Ҧ����s�զC��
	 * @param originMemberList ���Y�@���|�θs�դU�������C��
	 * @return ���grouplist��xml�榡
	 */
	private String groupToXmlContent(List<String>  grouplist,List<String> originMemberList){
		String content = "";
		//�]�t�@�ӥH�W���s�իh�N�C���নxml
		if( grouplist != null ){
			
			Iterator<String> iter  = grouplist.iterator();
			//�ഫ���
			while( iter.hasNext() ){
				String name = iter.next();

				//�ˬd�O�_�s�լO�w�s�b�󦨭�,�Y�O�h����ܵ��ϥΪ̿��
				if( originMemberList != null && originMemberList.indexOf("@"+name) >= 0 
						|| this.searchname.equals(name)){
					continue;
				}
				content += "<member type='group'>";
				content += name;
				content += "</member>\n";
			}
			
		}	
		return content;
	}
	
	/**
	 * �N�i�[�J���b���C���ഫ��xml�榡
	 * �����쥻�s�b��
	 * @param accountlist �Ҧ����b���C��
	 * @param originMemberList ���Y�@���|�θs�դU�������C��
	 * @return
	 */
	private String accountToXmlContent(List<String>  accountlist,List<String> originMemberList){
		String content = "";
		//�]�t�@�ӥH�W���s�իh�N�C���নxml
		if( accountlist != null ){
			
			Iterator<String> iter  = accountlist.iterator();
			//�ഫ���
			while( iter.hasNext() ){
				String name = iter.next();
				
				//�ˬd�O�_�s�լO�w�s�b�󦨭�,�Y�O�h����ܵ��ϥΪ̿��
				if( originMemberList != null && originMemberList.indexOf(name) >= 0 
						|| this.searchname.equals(name)){
					continue;
				}
				content += "<member type='account'>";
				content += name;
				content += "</member>\n";
			}
			
		}	
		return content;
	}
	
}
