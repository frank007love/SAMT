package ntut.csie.samt.aaprofile.importlog;

public class GroupMemberLog extends Log{
	private String groupName;
	private String memberName;
	private String memberType;
	
	public GroupMemberLog(String groupName,String memberName,String memberType,logStatus status){
		this.groupName = groupName;
		this.memberName = memberName;
		this.memberType = memberType;
		super.setStatus(status);
	}
	
	public String getGroupName(){
		return this.groupName;
	}
	
	public String getMemberName(){
		return this.memberName;
	}
	
	public String getMemberType(){
		return this.memberType;
	}
	
}
