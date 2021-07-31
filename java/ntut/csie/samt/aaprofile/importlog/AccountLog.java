package ntut.csie.samt.aaprofile.importlog;

public class AccountLog extends Log {
	private String accountName;

	public AccountLog(String accountName,logStatus status){
		this.accountName = accountName;
		super.setStatus(status);
	}
	
	public String getAccountName(){
		return this.accountName;
	}
}
