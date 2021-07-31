package ntut.csie.samt.web;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.ServletContextSingleton;

public class InitialServerConfig {

	/**
	 * 初始化Server設定
	 */
	static public void init(){
		ServletContextSingleton context = ServletContextSingleton.getInstance();
		ServletContext app = context.getServletContext();
		app = ServletActionContext.getServletContext();
		
		//設置Server Root Path
		String WebRootPath = app.getRealPath("/");
		System.setProperty("ntut.csie.samt.ApplicationRoot", WebRootPath);

		//設置SAMT PropertiesPath
		String propertiesPath = "properties/samt.properties";
		System.setProperty("ntut.csie.samt.PropertiesPath", WebRootPath + propertiesPath);	
	}
	
}
