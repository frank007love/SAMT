package ntut.csie.samt.account;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public abstract class IAuthenticationEntity {
	
	protected List<String> accountLines;
	protected File myFile;
	
	public IAuthenticationEntity(File file){
		this.myFile = file;
		this.accountLines = new ArrayList<String>();
	}
	
	/**
	 * 將讀取出來的帳號資訊一行一行放到accountLines
	 */
	public void load(){
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(this.myFile));
			String line ="";
			
            while ((line = reader.readLine()) != null) {
            	line.trim();
            	this.accountLines.add(line);
            }
			
            reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 將帳號資訊存入檔案中
	 */
	public void save(){
		Writer writer = null;
		try{
			writer = new FileWriter(this.myFile);
			
			Iterator<String> iter = this.accountLines.iterator();
			//將每一筆資訊寫入檔案中
			while(iter.hasNext()){
				String line = iter.next();
				writer.write(line.trim());
				if( iter.hasNext() )
					writer.write("\n");
			}
			writer.close();
		} catch( IOException e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得從檔案中讀取的描述帳號資訊
	 * @return
	 */
	public List<String> getAccountLines(){
		return this.accountLines;
	}
	
	/**
	 * 取得帳號ID列表
	 * @return
	 */
	abstract public List<String> getAccountList();
	
	/**
	 * 取得密碼列表
	 * @return
	 */
	abstract public List<String> getAccountPasswdList();
	
	/**
	 * 修改帳號
	 * @param oldId
	 * @param newId
	 */
	public void modifyAccountId(String oldId, String newId){
		//不給使用者修改成同樣帳號的
		if( this.findAccoutLineIndex(newId) != -1 ){
			return;
		}
		//找尋要修改帳號為oldId的accoutLine資訊index
		int index = this.findAccoutLineIndex(oldId);
		
		//有找到則進行修改帳號Id
		if( index != -1 ){
			//取得原本的描述資訊
			String line = this.accountLines.get(index);
			//進行帳號修改
			line = line.replace(oldId, newId);
			this.accountLines.set(index,line);
		}
	}
	
	/**
	 * 修改密碼
	 * @param id 要修改的ID
	 * @param passwd 新密碼
	 */
	abstract public void modifyAccountPasswd(String id, String passwd);
	
	/**
	 * 刪除帳號描述資訊
	 * @param id 要刪除的帳號ID
	 */
	public void deleteAccount(String id){
		//找尋要刪除帳號為id的accoutLine資訊index
		int index = this.findAccoutLineIndex(id);
		
		//有找到則進行刪除帳號描述資訊
		if( index != -1 ){
			this.accountLines.remove(index);
		}
	}
	
	/**
	 * 建立新的帳號
	 * @param id 新帳號ID
	 * @param passwd 新帳號密碼
	 */
	abstract public void createAccount(String id, String passwd);
	
	/**
	 * 找詢帳號為id的accountLine
	 * @param id 要找尋的帳號ID
	 * @return 所在的accountLines的index, 沒找到則為-1
	 */
	protected int findAccoutLineIndex(String id){
		//找尋是描述要找尋的帳號的資訊
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			//若是要找尋的則回傳index
			if( line.startsWith(id) ){
				return i;
			}
		}	
		//沒找到回傳-1
		return -1;
	}
}
