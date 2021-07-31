package ntut.csie.samt.aaprofile.importlog;

public class PermissionLog extends Log {
	private String pathName;
	private String pathMemberName;
	private String pathMemberType;
	
	public PermissionLog(String pathName,String pathMemberName,String pathMemberType,logStatus status){
		this.pathName = pathName;
		this.pathMemberName = pathMemberName;
		this.pathMemberType = pathMemberType;
		super.setStatus(status);
	}
	
	public String getPathName(){
		return this.pathName;
	}
	
	public String getPathMemberName(){
		return this.pathMemberName;
	}
	
	public String getPathMemberType(){
		return this.pathMemberType;
	}
	
}
