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
		//���oServer�պA
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
			//���osvnurl
			this.svnurl = defaultProps.getProperty("svn.url");
			//Ū���y�z���v���ɮ�
			this.authorizationPath = defaultProps.getProperty("authorization.file");
			//Ū���ϥΪ̸�T�ɮ�
			this.authenticationPath = defaultProps.getProperty("authentication.file");
			//�ˬdA&A�y�z�ɪ����|���۹�or����
			this.checkAAPath();
			//�O�_�����N�ק�g�J�ɮ�
			this.writable = defaultProps.getProperty("writable");
			//Ū���޲z�̱b���K�X
			this.managerAccount = defaultProps.getProperty("manageraccount");
			this.managerPasswd = defaultProps.getProperty("managerpasswd");
		} catch(IOException e){
			System.out.println(e);
		}
	}
	
	/**
	 * �ˬdA&A�y�z�ɪ����|���۹�or����
	 * �Y���۹�h�[�WServer Root
	 */
	private void checkAAPath(){
		String separator = System.getProperty("file.separator");
		//�D������|,�h��۹���|�ϥ�
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
	 * ���oServer Protocol Type
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
	 * �x�s�t�ΩҦ���Properties���ɮפ�
	 */
	public void saveProperties(){
		try{
			Properties defaultProps = new Properties();
			FileOutputStream out = new FileOutputStream(this.propertiesPath);
			
			//��ϥΪ̶i��ק��, �B�����Jaddress format
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
