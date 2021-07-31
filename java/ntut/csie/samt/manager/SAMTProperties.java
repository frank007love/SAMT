package ntut.csie.samt.manager;

import java.io.*;
import java.util.Properties;

public class SAMTProperties {

	private String propertiesPath = "WebContent/properties/samt.properties";
	private String writable;
	private String authorizationPath;
	private String authenticationPath;
	private String svnurl;
	private String WebRootPath = "";
	private String managerAccount;
	private String managerPasswd;
	
	public SAMTProperties(){
		//取得Server組態
		String systemProperties = System.getProperty("ntut.csie.samt.PropertiesPath");
		if( systemProperties != null ){
			this.propertiesPath = systemProperties;
		}
		this.WebRootPath = System.getProperty("ntut.csie.samt.ApplicationRoot");

		try{
			Properties defaultProps = new Properties();
			
			FileInputStream in = new FileInputStream(this.propertiesPath);
			defaultProps.load(in);
			in.close();
			//取得svnurl
			this.svnurl = defaultProps.getProperty("svn.url");
			//讀取描述的權限檔案
			this.authorizationPath = defaultProps.getProperty("authorization.file");
			//讀取使用者資訊檔案
			this.authenticationPath = defaultProps.getProperty("authentication.file");
			//檢查A&A描述檔的路徑為相對or絕對
			this.checkAAPath();
			//是否直接將修改寫入檔案
			this.writable = defaultProps.getProperty("writable");
			//讀取管理者帳號密碼
			this.managerAccount = defaultProps.getProperty("manageraccount");
			this.managerPasswd = defaultProps.getProperty("managerpasswd");
		} catch(IOException e){
			System.out.println(e);
		}
	}
	
	/**
	 * 檢查A&A描述檔的路徑為相對or絕對
	 * 若為相對則加上Server Root
	 */
	private void checkAAPath(){
		String separator = System.getProperty("file.separator");
		//非絕對路徑,則當相對路徑使用
		if( !this.authorizationPath.contains(":")  ){
			this.authorizationPath = WebRootPath + this.authorizationPath;
			this.authorizationPath = this.authorizationPath.replace('/', separator.toCharArray()[0]);
			this.authorizationPath = this.authorizationPath.replace('\\', separator.toCharArray()[0]);
		}
		if( !this.authenticationPath.contains(":") ){
			this.authenticationPath = WebRootPath + this.authenticationPath;
			this.authenticationPath = this.authenticationPath.replace('/', separator.toCharArray()[0]);
			this.authenticationPath= this.authenticationPath.replace('\\', separator.toCharArray()[0]);
		}
	}
	
	//Properties Setting
	public void setSVNUrl(String url){
		this.svnurl = url;
	}
	
	public void setauthorizationPath(String path){
		this.authorizationPath = path;
	}
	
	public void setAuthenticationPath(String path){
		this.authenticationPath = path;
	}
	
	public void setWritable(boolean writable){
		if( writable ){
			this.writable = "true";
		} else {
			this.writable = "false";
		}
	}
	
	public String getPropertiesPath(){
		return this.propertiesPath;
	}
	
	public void setPropertiesPath(String path){
		this.propertiesPath = path;
	}
	
	public void setManagerAccount(String account){
		this.managerAccount = account;
	}
	
	public void setManagerPasswd(String passwd){
		this.managerPasswd = passwd;
	}
	
	
	//Get properties
	public String getURL(){
		return this.svnurl;
	}
	
	/**
	 * 取得Server Protocol Type
	 * @return
	 */
	public String getServerProtocol(){
		String protocol = "http";
		
		if( this.svnurl.startsWith("svn:") ){
			protocol = "svn";
		} else if( this.svnurl.startsWith("http:") ){
			protocol = "http";
		}
		return protocol;
	}
	
	public String getAuthorizationPath(){
		return this.authorizationPath;
	}
	
	public String getAuthenticationPath(){
		return this.authenticationPath;
	}
	
	public boolean getWritable(){
		if( this.writable.equals("true") ){
			return true;
		}
		return false;
	}
	
	public String getManagerAccount(){
		 return this.managerAccount;
	}
	
	public String getManagerPasswd(){
		return this.managerPasswd;
	}
	
	/**
	 * 儲存系統所有的Properties到檔案內
	 */
	public void saveProperties(){
		try{
			Properties defaultProps = new Properties();
			FileOutputStream out = new FileOutputStream(this.propertiesPath);
			
			//當使用者進行修改後, 矯正其輸入address format
			checkAAPath();
			//Modify System properties
			defaultProps.setProperty("svn.url", this.svnurl);
			defaultProps.setProperty("authorization.file", this.authorizationPath);
			defaultProps.setProperty("authentication.file", this.authenticationPath);
			defaultProps.setProperty("writable", this.writable);
			defaultProps.setProperty("manageraccount", this.managerAccount);
			defaultProps.setProperty("managerpasswd", this.managerPasswd);
			
			defaultProps.store(out,"SAMT properties");
			out.close();
			
		} catch(IOException e){
			System.out.println(e);
		}
	}
}
