package ntut.csie.samt.aaprofile;

import java.io.File;
import java.util.List;

import ntut.csie.samt.aaprofile.importlog.AccountImportLogger;
import ntut.csie.samt.aaprofile.importlog.AccountLog;
import ntut.csie.samt.aaprofile.importlog.FolderImportLog;
import ntut.csie.samt.aaprofile.importlog.FolderLog;
import ntut.csie.samt.aaprofile.importlog.GroupImportLog;
import ntut.csie.samt.aaprofile.importlog.GroupLog;
import ntut.csie.samt.aaprofile.importlog.GroupMemberImportLog;
import ntut.csie.samt.aaprofile.importlog.GroupMemberLog;
import ntut.csie.samt.aaprofile.importlog.ImportLogger;
import ntut.csie.samt.aaprofile.importlog.Log;
import ntut.csie.samt.aaprofile.importlog.PermissionImportLog;
import ntut.csie.samt.aaprofile.importlog.PermissionLog;
import ntut.csie.samt.aaprofile.importlog.logStatus;
import ntut.csie.samt.manager.*;

public class ImportHandler {

	private AAProfileParser parser;
	private AAManager aaManager;
	
	/**
	 * 其實AAManager是Singleton的,
	 * 這樣寫比較Touch my Heart
	 * @param aaManager
	 */
	public ImportHandler(AAManager aaManager){
		this.aaManager = aaManager;
	}
	
	/**
	 * 根據匯入檔案,建立JAXB binding物件
	 * @param file
	 */
	public void importProfile(File file){
		this.parser = new AAProfileParser(file);
	}
	
	/**
	 * 將從AAProfile取得的帳號記錄到帳號設定檔
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importAccounts(){
		int flag = 2; //用來回報匯入結果
		
		ImportLogger importLog = new AccountImportLogger();
		//從檔案中取得帳號列表
		List<String> accountList = this.parser.getAccountIdList();
		List<String> passwdList = this.parser.getAccountPasswdList();

		//將從Profile中取得的帳號列表新增到authenticationFile
		for( int i = 0 ; accountList != null && i < accountList.size() ; i++){
			String id = accountList.get(i);
			String passwd = passwdList.get(i);
			
			//根據新增結果產生不同的Log
			logStatus status = null;
			try{
				//進行新增且新增成功
				if( this.aaManager.createAccount(id, passwd)){
					status = logStatus.Success;
				//Skip
				} else {
					if( flag != 0 ) flag = 1;
					status = logStatus.Warning;
				}
			//有錯誤發生
			}catch(Exception e){
				status = logStatus.Failure;
				flag = 0;
			}
			//新增log到log檔中
			Log log = new AccountLog(id,status);
			importLog.addLog(log);
		}
		importLog.save();
		return flag;
	}
	
	/**
	 * 將從AAProfile取得的群組列表資訊記錄到權限設定
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importGroups(){
		int flag = 2;
		ImportLogger importLog = new GroupImportLog();
		//從檔案中取得的群組列表
		List<String> groupList = this.parser.getGroupList();
		
		//將從Profile中取得的群組列表資訊新增到authorizationFile
		for( int i = 0 ; groupList != null && i < groupList.size() ; i++ ){
			String name =  groupList.get(i);
			//根據新增結果產生不同的Log
			logStatus status = null;
			try{
				//進行新增且新增成功
				if( this.aaManager.addGroup(name) ){
					status = logStatus.Success;
				//Skip
				} else {
					if( flag != 0 ) flag = 1;
					status = logStatus.Warning;
				}
			//有錯誤發生
			}catch(Exception e){
				status = logStatus.Failure;
				flag = 0;
			}
			
			//新增log到log檔中
			Log log = new GroupLog(name,status);
			importLog.addLog(log);
		}
		importLog.save();
		return flag;
	}
	
	/**
	 * 將從AAProfile取得的群組成員列表資訊記錄到權限設定
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importGroupMember(){
		int flag = 2;
		ImportLogger importLog = new GroupMemberImportLog();
		List<String> groupList = this.parser.getGroupList();
		//一一取得群組名稱, 然後取得各群組成員加入到權限設定
		for( int i = 0 ; groupList != null && i < groupList.size() ; i++ ){
			//群組名稱
			String groupName = groupList.get(i);
			//取得群組成員
			List<String> memberList = this.parser.getGroupMemberList(groupName);
			//將群組成員加入到權限設定
			for( int j = 0 ; memberList != null && j < memberList.size() ; j++ ){
				//成員名稱與型態
				String memberName = memberList.get(j);
				String type = "account";
				//以SVN權限設定檔格式,若成員為群組會以@開頭
				if( memberName.startsWith("@") ){
					memberName = memberName.substring(1);
					type = "group";
				}
				
				//根據新增結果產生不同的Log
				logStatus status = null;
				try{
					//進行新增群組成員到權限設定且新增成功
					if( this.aaManager.addGroupMember(groupName, memberName, type) ){
						status = logStatus.Success;
					//Skip
					} else {
						if( flag != 0 ) flag = 1;
						status = logStatus.Warning;
					}
				//有錯誤發生
				}catch(Exception e){
					status = logStatus.Failure;
					flag = 0;
				}
				
				//新增log到log檔中
				Log log = new GroupMemberLog(groupName, memberName, type, status);
				importLog.addLog(log);
				
			}
		}
		importLog.save();
		return flag;
	}
	
	/**
	 * 將從AAProfile取得的權限資訊記錄到權限設定
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importPermissions(){
		int flag = 2;
		ImportLogger importLog = new PermissionImportLog();
		
		List<String> pathList = this.parser.getRepositoryPath();
		
		for( int i = 0 ; pathList != null && i < pathList.size() ; i++ ){
			String path = pathList.get(i);
			List<String> pathMember = this.parser.getPathMember(path);
			for( int j = 0 ; pathMember != null && j < pathMember.size() ; j++ ){
				//成員名稱、型態、權限
				String name = pathMember.get(j);
				String type = "account";
				String permission = this.parser.getPermission(path, name);
				//以SVN權限設定檔格式,若成員為群組會以@開頭
				if( name.startsWith("@") ){
					name = name.substring(1);
					type = "group";
				}
				
				//根據新增結果產生不同的Log
				logStatus status = null;
				try{
					//進行新增權限且新增成功
					if( this.aaManager.addPermission(path, name, type, permission) ){
						status = logStatus.Success;
					//Skip
					} else {
						if( flag != 0 ) flag = 1;
						status = logStatus.Warning;
					}
				//有錯誤發生
				}catch(Exception e){
					status = logStatus.Failure;
					flag = 0;
				}
				//新增log到log檔中
				Log log = new PermissionLog(path, name, type, status);
				importLog.addLog(log);
			}
		}
		importLog.save();
		return flag;
	}
	
	/**
	 * 將從AAProfile取得的建立SVN資料列表建立到SVN上
	 * @return 0: failure, 1: warning, 2: complete
	 */
	public int importSVNFolder(){
		int flag = 2;
		ImportLogger importLog = new FolderImportLog();
		
		List<String> folderList =  this.parser.getAllFolderList();
		
		for( int i = 0 ; folderList != null && i < folderList.size() ; i++ ){
			String path = folderList.get(i);

			logStatus status = null;
			//存在則新增skip的log
			if( this.aaManager.isPathExist(path)){
				flag = 1;
				status = logStatus.Warning; 
			//不存在則新增可加入的log
			} else {
				status = logStatus.Success;
			}
			
			Log log = new FolderLog(path,status);
			importLog.addLog(log);
		}
		
		try{
			//建立SVN資料夾
			this.aaManager.createDir(folderList);
			importLog.save();
			return flag;
		//發生Exception則算匯入失敗
		} catch ( Exception e){
			e.printStackTrace();
			
			importLog = new FolderImportLog();
			//所有路徑新增失敗
			for( int i = 0 ; folderList != null && i < folderList.size() ; i++ ){
				String path = folderList.get(i);
				Log log = new FolderLog(path,logStatus.Failure);
				importLog.addLog(log);
			}			
			importLog.save();
			return 0;
		}
	}
}
