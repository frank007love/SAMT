package ntut.csie.samt.aaprofile.importlog;

public abstract class Log {

	protected logStatus status;
	private String [] typeArray = { "success", "failure", "warning" };
	
	public void setStatus(logStatus status){
		this.status = status;
	}
	
	public String getStatus(){
		return this.typeArray[this.status.ordinal()];
	}
	
}
