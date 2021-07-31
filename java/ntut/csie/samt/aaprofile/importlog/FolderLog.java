package ntut.csie.samt.aaprofile.importlog;

public class FolderLog extends Log{
	private String path;

	public FolderLog(String path,logStatus status){
		this.path = path;
		super.setStatus(status);
	}
	
	public String getPath(){
		return this.path;
	}
}
