package ntut.csie.samt.aaprofile.importlog;

public class GroupLog extends Log {
	private String groupName;

	public GroupLog(String name,logStatus status){
		this.groupName = name;
		super.setStatus(status);
	}
	
	public String getName(){
		return this.groupName;
	}
}
