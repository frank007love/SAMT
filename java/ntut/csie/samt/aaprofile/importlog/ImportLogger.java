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

		//若report folder不存在則建立
		File file = new File(WebRootPath+"report");
		if( !file.exists() ){
			file.mkdir();
		}
	}
	
	/**
	 * 儲存Log檔
	 */
	abstract public void save();	
	
	/**
	 * 新增log資訊
	 * @param log
	 */
	public void addLog(Log log){
		this.logs.add(log);
	}
}
