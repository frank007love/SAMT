package ntut.csie.samt.account;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SVNAuthenticationEntity extends IAuthenticationEntity{

	public SVNAuthenticationEntity(File file){
		super(file);
	}
	
	/**
	 * 取得檔案中的帳號id列表
	 */
	public List<String> getAccountList(){
		List<String> accountList = new ArrayList<String>();
		//將從檔案讀取出來的字串處理成帳號id列表
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			
			if( line.contains("=") ){
				//取得=之前的字串,為帳號id
				line = line.substring(0, line.indexOf("="));
				//將帳號ID放到accountList
				accountList.add(line);
			}
		}
		//回傳帳號id列表
		return accountList;
	}
	
	/**
	 * 取得檔案中的密碼列表
	 */
	public List<String> getAccountPasswdList(){
		List<String> passwdList = new ArrayList<String>();
		//將從檔案讀取出來的字串處理成帳號id列表
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			
			if( line.contains("=") ){
				//取得=之前的字串,為帳號id
				line = line.substring(line.indexOf("=")+1);
				//將帳號ID放到accountList
				passwdList.add(line);
			}
		}
		//回傳帳號id列表
		return passwdList;
	}
	
	/**
	 * 建立帳號
	 */
	public void createAccount(String id, String passwd){
		if( id.isEmpty() || passwd.isEmpty() ){
			return;
		}
		this.accountLines.add(id + " = " + passwd);
	}
	
	/**
	 * 修改密碼
	 */
	public void modifyAccountPasswd(String id, String passwd){
		if( passwd.isEmpty() ){
			return;
		}
		//找尋要修改密碼的帳號, 進行修改
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			line = line.trim();
			//若為要修改的帳號,則進行修改
			if( line.startsWith(id) ){
				line = id + "=" + passwd;
				this.accountLines.set(i, line);
				break;
			}
		}
		
	}
	
}
