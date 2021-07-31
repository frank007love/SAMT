package ntut.csie.samt.svncontroll;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

import org.tmatesoft.svn.core.internal.wc.SVNFileUtil;

//註解,錯誤的權限描述,不同的路徑描述法尚未做
public class AuthorizationEntity {

    private File myFile;
    private String[] permissionLines;
    private List<String> groupHeader;
    private List<String> pathList;
    private Map<String,List<String>> permissions;
    private long myLastModified;
	
    public AuthorizationEntity(File file){
    	this.myFile = file.getAbsoluteFile();
    	
    	this.groupHeader = new ArrayList<String>();
    	this.pathList = new ArrayList<String>();
    	this.permissions = new HashMap<String,List<String>>();
    }
    
    /**
     * 取得群組名稱
     */
    public String[] getGroupList(){
    	
    	String []groupList = new String[this.groupHeader.size()];
    	
    	for( int i = 0 ; i < this.groupHeader.size() ; i++ ){
    		String groupHeaderLine = this.groupHeader.get(i);
    		groupList[i] = groupHeaderLine.substring(0, groupHeaderLine.indexOf('=')).trim();
    		
    	}
    	
    	return groupList;
    }
    
    /**
     * 取得群組名稱為groupName的群組成員
     * 
     * @param groupName 群組名稱
     * @return 群組成員
     */
    public String[] getGroupMemberList(String groupName){
    	
    	String []groupMemberList = null;
    	
    	for( int i = 0 ; i < this.groupHeader.size() ; i++ ){
    		String groupHeaderLine = this.groupHeader.get(i);
    		if( groupHeaderLine.startsWith(groupName) ){
    			String buf = this.groupHeader.get(i).substring(groupHeaderLine.indexOf('=') + 1).trim();
    			
    			if( buf.equals("") ){
    				break;
    			}
    			
    			groupMemberList = buf.split(","); 
    			
    			for( int j = 0; j < groupMemberList.length ; j++ ){
    				groupMemberList[j] = groupMemberList[j].trim();
    			}
    			
    			break;
    		}
    	}
    	
    	return groupMemberList;
    }
    
    
    
	public void load(){
		//讀取群組與成員資訊
		this.loadGroupHeader();
		//讀取路徑與權限資訊
		this.loadPermissions();
	}
	
    public void save() {
        if (this.groupHeader == null && this.permissionLines == null) {
            return;
        }
        if (this.myFile.isDirectory()) {
            return;
        }
        if (this.myFile.getParentFile() != null) {
        	this.myFile.getParentFile().mkdirs();
        }
        Writer writer = null;
        String eol = System.getProperty("line.separator");
        eol = eol == null ? "\n" : eol;
        try {
            writer = new FileWriter(myFile);
            writer.write("[groups]");
            writer.write(eol);
            for (int i = 0; this.groupHeader != null && i < this.groupHeader.size(); i++) {
                String line = this.groupHeader.get(i);
                if (line == null) {
                    continue;
                }
                writer.write(line);
                writer.write(eol);
            }
            writer.write(eol);
            //將權限設定寫入權限設定檔
            writePermissionData(writer);
            
            writer.close();
        } catch (IOException e) {
            //
        } finally {
            SVNFileUtil.closeFile(writer);
        }
        myLastModified = this.myFile.lastModified();
    }
	
    /**
     * 將權限設定寫入權限設定檔
     * @param writer
     * @throws IOException 交給save()處理
     */
    private void writePermissionData(Writer writer) throws IOException{
    	//將所有路徑與其路徑成員及其權限寫入檔案
    	for( int i = 0 ; i < this.pathList.size() ; i++ ){
    		String path = this.pathList.get(i);
    		//寫入路徑
    		writer.write("[" + path + "]");
    		writer.write("\n");
    		//取得路徑對應之路徑成員
    		List<String> permissionLines = this.permissions.get(path);
    		//將路徑成員與權限資料寫入檔案
    		for( int j = 0 ; permissionLines != null && j < permissionLines.size() ; j++ ){
    			String permissionLine = permissionLines.get(j);
    			writer.write(permissionLine);
    			writer.write("\n");
    		}
    		writer.write("\n");
    	} 	
    }
	
	/**
	 * 讀取群組資訊到groupHeader內,
	 * 若groupHeader還有資料且沒做修改, 
	 * 則不讀取
	 */
	public void loadGroupHeader(){
		//若groupHeader還有資料且沒做修改 則不讀取
		if( this.groupHeader != null && this.myFile.lastModified() == this.myLastModified ){
			return;
		}
		this.myLastModified = this.myFile.lastModified();
		this.groupHeader = (List<String>)this.doGroupHeaderLoad(this.myFile);
		this.myLastModified = this.myFile.lastModified();
	}
	
	/**
	 * 讀取群組資訊
	 * 
	 * @param file 要讀取的檔案
	 * @return 回傳讀取的群組資訊
	 */
	private Collection<String> doGroupHeaderLoad(File file){
		BufferedReader reader = null;
		
		Collection<String> lines = new ArrayList<String>();
		
		try{
			reader = new BufferedReader(new FileReader(this.myFile));
            String line = "";
            boolean groupStart = false;
            while ((line = reader.readLine()) != null) {
            	line.trim();
            	//是群組資訊則下一次開始將內容放進lines
            	if( line.equals("[groups]")  ){
            		groupStart = true;
            		continue;
            	//讀取到註解就略過
            	}else if( line.startsWith("#")){
            		continue;
            	}//讀取到非群組資訊內容(路徑與存取權限)
            	else if( line.contains("[") && line.contains("]") && !line.equals("[groups]") ){
            		break;
            	}  
            	//將群組資訊放到lines
            	if( groupStart && !line.isEmpty() ){
            		lines.add(line);
            	}
            }
            reader.close();
		} catch( IOException e ){
			lines.clear();
			System.out.println(e);
		}

		return lines;
	}
	
	public void loadPermissions(){

		//若groupHeader還有資料且沒做修改 則不讀取
		if( this.permissionLines != null && this.myFile.lastModified() == this.myLastModified ){
			return;
		}
		this.myLastModified = this.myFile.lastModified();
		this.permissionLines = this.doPermissionsLoad(this.myFile);
		this.myLastModified = this.myFile.lastModified();
	}
	
	/**
	 * 讀取Permssion的資料
	 * 
	 * @param file 所要讀取的檔案
	 * @return 
	 */
	private String[] doPermissionsLoad(File file){
		BufferedReader reader = null;
		
		Collection<String> lines = new ArrayList<String>();

		try{
			reader = new BufferedReader(new FileReader(this.myFile));
            String line = "";
            boolean permissionStart = false;
            while ((line = reader.readLine()) != null) {
            	line.trim();
            	//是群組資訊則下一次開始將內容放進lines
            	if( line.equals("[groups]")  ){
            		continue;
                //讀取到註解就略過
            	}else if( line.startsWith("#")){
            		continue;
            	}//讀取到非群組資訊內容(路徑與存取權限)
            	else if( line.contains("[") && line.contains("]") && !line.equals("[groups]") ){
            		permissionStart = true;
            	}
        		//System.out.println("start");
            	//將群組資訊放到lines
            	if( permissionStart && !line.isEmpty() ){
            		lines.add(line);
            	}
            }
            reader.close();
		} catch( IOException e ){
			lines.clear();
			System.out.println(e);
		}

		return (String[]) lines.toArray(new String[lines.size()]);
	}
	
	/**
	 * 取得檔案中所有的RepositoryPath
	 * @return
	 */
	public String[] getRepositoryPath(){
		
    	List<String> pathlist = new ArrayList<String>();
    	
    	for( int i = 0 ; this.permissionLines != null && i < this.permissionLines.length ; i++ ){
    		String line = permissionLines[i];
    		//非群組描述
    		if( line.contains("[") && line.contains("]") && !line.equals("[groups]")){
    			pathlist.add(line.substring(1, line.length()-1));
    		}
    	}

		return (String[]) pathlist.toArray(new String[pathlist.size()]);
	}
	
	/**
	 * 取得某路徑下所有的Authorization Description
	 * @param path 要找尋的路徑
	 * @return Authorization Description
	 */
	private String [] getPermissionLines(String path){
		
		List<String> permissionList = new ArrayList<String>();
		
		boolean permissionLinesStart = false;
		//Parse all permission description lines
		for(int i = 0; i < this.permissionLines.length ; i++){
			String line = this.permissionLines[i];
			
			//所要讀取的Path已結束
			if( permissionLinesStart && line.contains("[") && line.contains("]")  ){
				break;
			}
			
			//要取得的Path開始
			if( permissionLinesStart ){
				permissionList.add(line);
			}
			
			//下一行開始是權限的描述
			if( line.equals("[" + path + "]" ) ){
				permissionLinesStart = true;
			}			
		}
		
		return (String[]) permissionList.toArray(new String[permissionList.size()]);
	}
	
	/**
	 * 取得某路徑下所設定之成員
	 * @param path 所要找的路徑
	 * @return 所設定之成員
	 */
	public String[] getFileMemberList(String path){
		
		List<String> fileMemberList = new ArrayList<String>();
		//取得路徑下所有的Authorization Description
		String [] permissionList = getPermissionLines(path);
		//取得成員部分
		for(int i = 0; i < permissionList.length ; i++){
			String buf = permissionList[i].substring(0,permissionList[i].indexOf('=')).trim();
			fileMemberList.add(buf);
		}
		
		return (String[]) fileMemberList.toArray(new String[fileMemberList.size()]);
	}
	
	public String getPermissionsData(String path,String memberName){

		String permission = "";
		//取得路徑下所有的Authorization Description
		String [] permissionList = getPermissionLines(path);
		
		//取得某成員的權限
		for(int i = 0; i < permissionList.length ; i++){
			 //取得描述的成名稱
			String membername = permissionList[i].substring(0,permissionList[i].indexOf('=')).trim();
			//看是否為要取得之成員
			if( membername.equals(memberName) ){
				permission = permissionList[i].substring(permissionList[i].indexOf('=')+1).trim();
				break;
			}
		}
		
		return permission;
	}
	
	/**
	 * 設置群組列表
	 */
	public void setGroupList(List<String> groupList){
		this.groupHeader.clear();
		
		if( groupList == null )
			return;
		
		//設置群組
		for( int i = 0 ; i < groupList.size() ; i ++ ){
			String groupLine = groupList.get(i) + " =";
			this.groupHeader.add(groupLine);
		}
	}
	
	/**
	 * 設置群組成員列表
	 * @param groupName 群組名稱
	 * @param memberList 群組成員
	 */
	public void setGroupMember(String groupName, List<String> memberList){
		//沒有群組成員資訊與群組Header資訊, 則不設置
		if( this.groupHeader == null || memberList == null ){
			return ;
		}
		//設置群組成員到群組名稱為groupName
		for( int i = 0 ; i < this.groupHeader.size() ; i++ ){
			String headerLine = this.groupHeader.get(i);
			//為要加入成員的群組描述, 會以相同的群組名稱開頭
			if( headerLine.startsWith(groupName) ){
				//設置群組成員
				for( int j = 0 ; j < memberList.size() ; j++){
					String memberName = memberList.get(j);
					headerLine += " " + memberName;
					//最後一個成員不需要加逗點分隔
					if( j+1 != memberList.size() ){
						headerLine += ",";
					}
				}
				//將設置完的headerLine更新
				groupHeader.set(i, headerLine);
				break;
			}
		}
	}
	
	/**
	 * 設置權限資訊,
	 * pathmember會對應到permissions
	 * @param path 路徑
	 * @param pathmember 路徑成員
	 * @param permissions　權限資訊
	 */
	public void setPermissionData(String path,List<String> pathmember,List<String> permissions){
		//路徑尚未加入, 則加入到pathList
		if( path != null && this.pathList.indexOf(path) == -1 ){
			this.pathList.add(path);
		}
		//路徑成員或權限不存在則不加入到權限資訊
		if( pathmember == null || permissions == null 
				|| path == null || path.isEmpty()  )
			return;
		
		List<String> permissionList = new ArrayList<String>();
		
		for( int i = 0 ; i < pathmember.size() ; i++ ){
			String memberName = pathmember.get(i);
			if( memberName.equals("@*") ){
				memberName = "*";
			}
			String permissionLine = memberName + " = " + permissions.get(i);
			permissionList.add(permissionLine);
		}
		//將權限資料放到相對應的path
		this.permissions.put(path, permissionList);
	}
	
}

