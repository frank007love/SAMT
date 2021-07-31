package ntut.csie.samt.web;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.ServletContextSingleton;

public class InitialServerConfig {

	/**
	 * ��l��Server�]�w
	 */
	static public void init(){
		ServletContextSingleton context = ServletContextSingleton.getInstance();
		ServletContext app = context.getServletContext();
		app = ServletActionContext.getServletContext();
		
		//�]�mServer Root Path
		String WebRootPath = app.getRealPath("/");
		System.setProperty("ntut.csie.samt.ApplicationRoot", WebRootPath);

		//�]�mSAMT PropertiesPath
		String propertiesPath = "properties/samt.properties";
		System.setProperty("ntut.csie.samt.PropertiesPath", WebRootPath + propertiesPath);	
	}
	
}
