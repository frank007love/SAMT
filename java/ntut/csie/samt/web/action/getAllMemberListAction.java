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
		String op = request.getParameter("op");
		//取得要新增成員之路徑
		this.searchname = request.getParameter("searchname");
		this.aaManager = AAManager.getInstance();
		
		List<String> originMemberList = null;
		List<String> grouplist = this.aaManager.listAllGroup();
		System.out.println(grouplist.size());
		List<String> accountlist = this.aaManager.listAllAccount();
		if( op.equals("path")){
			//取得路徑下所有成員
			if( grouplist == null ){
				grouplist = new ArrayList<String>();
			}
			grouplist.add("*"); //權限可以使用*當所有的帳號與所有群組
			
			originMemberList = this.aaManager.getPathMemberList(this.searchname);
		} else if( op.equals("group") ){
			//取得群組下所有成員
			originMemberList = this.aaManager.listGroupMember(this.searchname);
		}
		
		//xml translate
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><root>\n";
		//將group轉換成xml
		
		xml += this.groupToXmlContent(grouplist,originMemberList);
		//將account轉換成xml
		xml += this.accountToXmlContent(accountlist, originMemberList);
		
		xml += "</root>";
		
	    response.setContentType("text/xml; charset=UTF-8");          
	    response.setHeader("Cache-Control", "no-cache");
		
	    try{
		    //將所有成員列表傳給View,包含帳號與群組
		    PrintWriter out = response.getWriter();
		    out.print(new String(xml.getBytes("UTF-8"),"UTF-8"));
		    out.close();
		    
		} catch( IOException e){
		    	return null;
		}
		
		return null;
	} 
	/**
	 * 將可加入群組列表轉成xml之格式
	 * 扣掉原本存在的
	 * @param grouplist 所有的群組列表
	 * @param originMemberList 為某一路徑或群組下的成員列表
	 * @return 表示grouplist的xml格式
	 */
	private String groupToXmlContent(List<String>  grouplist,List<String> originMemberList){
		String content = "";
		//包含一個以上的群組則將列表轉成xml
		if( grouplist != null ){
			
			Iterator<String> iter  = grouplist.iterator();
			//轉換資料
			while( iter.hasNext() ){
				String name = iter.next();

				//檢查是否群組是已存在於成員,若是則不顯示給使用者選取
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
	 * 將可加入的帳號列表轉換成xml格式
	 * 扣掉原本存在的
	 * @param accountlist 所有的帳號列表
	 * @param originMemberList 為某一路徑或群組下的成員列表
	 * @return
	 */
	private String accountToXmlContent(List<String>  accountlist,List<String> originMemberList){
		String content = "";
		//包含一個以上的群組則將列表轉成xml
		if( accountlist != null ){
			
			Iterator<String> iter  = accountlist.iterator();
			//轉換資料
			while( iter.hasNext() ){
				String name = iter.next();
				
				//檢查是否群組是已存在於成員,若是則不顯示給使用者選取
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
