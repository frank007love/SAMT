package ntut.csie.samt.account;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class ApacheAuthenticationEntity extends IAuthenticationEntity{

	/*
	 * 用來儲存從檔案中讀取出來的帳號, 為了辨別已有密碼與使用者要加入的區隔,
	 * 因為MD5取出來難以解密
	 */
	private List<String> accountList;
	
	public ApacheAuthenticationEntity(File file){
		super(file);
		accountList = new ArrayList<String>();
	}
	
	/**
	 * 取得檔案中的帳號id列表
	 */
	public List<String> getAccountList(){
		this.accountList.clear();
		//將從檔案讀取出來的字串處理成帳號id列表
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			
			if( line.contains(":") ){
				//取得:之前的字串,為帳號id
				line = line.substring(0, line.indexOf(":"));
				//將帳號ID放到accountList
				this.accountList.add(line);
			}
		}
		//回傳帳號id列表
		return this.accountList;
	}
	
	/**
	 * 取得檔案中的密碼列表
	 */
	public List<String> getAccountPasswdList(){
		List<String> passwdList = new ArrayList<String>();
		//將從檔案讀取出來的字串處理成帳號id列表
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			
			if( line.contains(":") ){
				//取得:之前的字串,為帳號id
				line = line.substring(line.indexOf(":")+1);
				//將帳號ID放到accountList
				passwdList.add(line);
			}
		}
		//回傳帳號id列表
		return passwdList;
	}
	
	/**
	 * 修改帳號
	 * @param oldId
	 * @param newId
	 */
	public void modifyAccountId(String oldId, String newId){
		if( accountList.size() == 0 )
			this.getAccountList();
		
		int index = accountList.indexOf(oldId);
		//要修改的ID不存在則不進行修改
		if( index == -1 ) return;
		
		//進行ID修改
		accountList.set(index , newId);
		super.modifyAccountId(oldId, newId);
	}
	
	/**
	 * 修改密碼
	 * @param id 要修改的ID
	 * @param passwd 新密碼
	 */
	public void modifyAccountPasswd(String id, String passwd){
		if( accountList.size() == 0 )
			this.getAccountList();
		//刪除帳號再新增
		this.deleteAccount(id);
		this.createAccount(id, passwd);
	}
	
	/**
	 * 使用Apache的htpasswd建立帳號密碼
	 */
	public void createAccount(String id, String passwd){
		getAccountList();
		if( this.accountList.indexOf(id) != -1 )
			return;
		
		Runtime rt = Runtime.getRuntime();
	
		File tempfile = new File("/apahcesvn_temp");
		
		try{
			if( tempfile.exists() )
				tempfile.delete();
			//建立一個暫存檔用來給Apache執行檔建立密碼
			tempfile.createNewFile();
			//取得檔案路徑
			String path = tempfile.getPath();
			String line = "";
			
			//執行建立帳號指令
			if( passwd != "" ){
				String command = this.getCommand();
				Process p = rt.exec( command + path + " " + id + " " + passwd);
				//進行Process等待
				p.waitFor();
				
				//取得剛建立的資訊放到accountLines
				BufferedReader reader = new BufferedReader(new FileReader(tempfile));
				
				line = reader.readLine();
				
				//關閉Reader, 避免無法刪除暫存檔
				reader.close();
				
			}else{
				line = id + ":";
			}
			this.accountLines.add(line);
		} catch(IOException e){
			e.printStackTrace();
		} catch (Throwable e){ //Process Exception
			e.printStackTrace(); 
		}
		//刪除暫存檔
		tempfile.delete();
	}
	
	/**
	 * 取得命令
	 * @return
	 */
	private String getCommand(){
		String osName = System.getProperty("os.name");
		String command = "htpasswd -b ";
		
		if( osName.contains("Windows") ){
			String WebRootPath = System.getProperty("ntut.csie.samt.ApplicationRoot");
			command = WebRootPath + "properties/htpasswd.exe -b ";
		}
		
		return command;
	}
	
	/**
	 * 刪除帳號描述資訊與帳號列表
	 * @param id 要刪除的帳號ID
	 */
	public void deleteAccount(String id){
		//找尋要刪除帳號的index
		int index = this.accountList.indexOf(id);
		//要刪除的帳號存在則進行刪除
		if( index != -1 ){
			this.accountList.remove(id);
			super.deleteAccount(id);
		}
	}
}
