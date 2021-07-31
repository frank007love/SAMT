package ntut.csie.samt.account;

public class Account {

	private String id;
	private String password;
	
	/**
	 * 設定帳號
	 * @param id
	 */
	public void setName(String id){
		this.id = id;
	}
	
	/**
	 * 設定密碼
	 * @param password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * 取得帳號
	 * @return
	 */
	public String getId(){
		return this.id;
	}
	
	/**
	 * 取得密碼
	 * @return 
	 */
	public String getPassword(){
		return this.password;
	}
	
}
