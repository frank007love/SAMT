package ntut.csie.samt.aaprofile.importlog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ImportLogger {
	
	protected File logFile = null;
	protected List<Log> logs = null;
	protected String WebRootPath = System.getProperty("ntut.csie.samt.ApplicationRoot");
	
	public ImportLogger(){
		this.logs = new ArrayList<Log>();

		//�Yreport folder���s�b�h�إ�
		File file = new File(WebRootPath+"report");
		if( !file.exists() ){
			file.mkdir();
		}
	}
	
	/**
	 * �x�sLog��
	 */
	abstract public void save();	
	
	/**
	 * �s�Wlog��T
	 * @param log
	 */
	public void addLog(Log log){
		this.logs.add(log);
	}
}
