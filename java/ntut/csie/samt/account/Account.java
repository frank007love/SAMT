package ntut.csie.samt.account;

public class Account {

	private String id;
	private String password;
	
	/**
	 * �]�w�b��
	 * @param id
	 */
	public void setName(String id){
		this.id = id;
	}
	
	/**
	 * �]�w�K�X
	 * @param password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * ���o�b��
	 * @return
	 */
	public String getId(){
		return this.id;
	}
	
	/**
	 * ���o�K�X
	 * @return 
	 */
	public String getPassword(){
		return this.password;
	}
	
}
