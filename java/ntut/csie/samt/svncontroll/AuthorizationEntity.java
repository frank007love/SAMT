package ntut.csie.samt.svncontroll;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

import org.tmatesoft.svn.core.internal.wc.SVNFileUtil;

//����,���~���v���y�z,���P�����|�y�z�k�|����
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
     * ���o�s�զW��
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
     * ���o�s�զW�٬�groupName���s�զ���
     * 
     * @param groupName �s�զW��
     * @return �s�զ���
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
		//Ū���s�ջP������T
		this.loadGroupHeader();
		//Ū�����|�P�v����T
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
            //�N�v���]�w�g�J�v���]�w��
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
     * �N�v���]�w�g�J�v���]�w��
     * @param writer
     * @throws IOException �浹save()�B�z
     */
    private void writePermissionData(Writer writer) throws IOException{
    	//�N�Ҧ����|�P����|�����Ψ��v���g�J�ɮ�
    	for( int i = 0 ; i < this.pathList.size() ; i++ ){
    		String path = this.pathList.get(i);
    		//�g�J���|
    		writer.write("[" + path + "]");
    		writer.write("\n");
    		//���o���|���������|����
    		List<String> permissionLines = this.permissions.get(path);
    		//�N���|�����P�v����Ƽg�J�ɮ�
    		for( int j = 0 ; permissionLines != null && j < permissionLines.size() ; j++ ){
    			String permissionLine = permissionLines.get(j);
    			writer.write(permissionLine);
    			writer.write("\n");
    		}
    		writer.write("\n");
    	} 	
    }
	
	/**
	 * Ū���s�ո�T��groupHeader��,
	 * �YgroupHeader�٦���ƥB�S���ק�, 
	 * �h��Ū��
	 */
	public void loadGroupHeader(){
		//�YgroupHeader�٦���ƥB�S���ק� �h��Ū��
		if( this.groupHeader != null && this.myFile.lastModified() == this.myLastModified ){
			return;
		}
		this.myLastModified = this.myFile.lastModified();
		this.groupHeader = (List<String>)this.doGroupHeaderLoad(this.myFile);
		this.myLastModified = this.myFile.lastModified();
	}
	
	/**
	 * Ū���s�ո�T
	 * 
	 * @param file �nŪ�����ɮ�
	 * @return �^��Ū�����s�ո�T
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
            	//�O�s�ո�T�h�U�@���}�l�N���e��ilines
            	if( line.equals("[groups]")  ){
            		groupStart = true;
            		continue;
            	//Ū������ѴN���L
            	}else if( line.startsWith("#")){
            		continue;
            	}//Ū����D�s�ո�T���e(���|�P�s���v��)
            	else if( line.contains("[") && line.contains("]") && !line.equals("[groups]") ){
            		break;
            	}  
            	//�N�s�ո�T���lines
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

		//�YgroupHeader�٦���ƥB�S���ק� �h��Ū��
		if( this.permissionLines != null && this.myFile.lastModified() == this.myLastModified ){
			return;
		}
		this.myLastModified = this.myFile.lastModified();
		this.permissionLines = this.doPermissionsLoad(this.myFile);
		this.myLastModified = this.myFile.lastModified();
	}
	
	/**
	 * Ū��Permssion�����
	 * 
	 * @param file �ҭnŪ�����ɮ�
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
            	//�O�s�ո�T�h�U�@���}�l�N���e��ilines
            	if( line.equals("[groups]")  ){
            		continue;
                //Ū������ѴN���L
            	}else if( line.startsWith("#")){
            		continue;
            	}//Ū����D�s�ո�T���e(���|�P�s���v��)
            	else if( line.contains("[") && line.contains("]") && !line.equals("[groups]") ){
            		permissionStart = true;
            	}
        		//System.out.println("start");
            	//�N�s�ո�T���lines
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
	 * ���o�ɮפ��Ҧ���RepositoryPath
	 * @return
	 */
	public String[] getRepositoryPath(){
		
    	List<String> pathlist = new ArrayList<String>();
    	
    	for( int i = 0 ; this.permissionLines != null && i < this.permissionLines.length ; i++ ){
    		String line = permissionLines[i];
    		//�D�s�մy�z
    		if( line.contains("[") && line.contains("]") && !line.equals("[groups]")){
    			pathlist.add(line.substring(1, line.length()-1));
    		}
    	}

		return (String[]) pathlist.toArray(new String[pathlist.size()]);
	}
	
	/**
	 * ���o�Y���|�U�Ҧ���Authorization Description
	 * @param path �n��M�����|
	 * @return Authorization Description
	 */
	private String [] getPermissionLines(String path){
		
		List<String> permissionList = new ArrayList<String>();
		
		boolean permissionLinesStart = false;
		//Parse all permission description lines
		for(int i = 0; i < this.permissionLines.length ; i++){
			String line = this.permissionLines[i];
			
			//�ҭnŪ����Path�w����
			if( permissionLinesStart && line.contains("[") && line.contains("]")  ){
				break;
			}
			
			//�n���o��Path�}�l
			if( permissionLinesStart ){
				permissionList.add(line);
			}
			
			//�U�@��}�l�O�v�����y�z
			if( line.equals("[" + path + "]" ) ){
				permissionLinesStart = true;
			}			
		}
		
		return (String[]) permissionList.toArray(new String[permissionList.size()]);
	}
	
	/**
	 * ���o�Y���|�U�ҳ]�w������
	 * @param path �ҭn�䪺���|
	 * @return �ҳ]�w������
	 */
	public String[] getFileMemberList(String path){
		
		List<String> fileMemberList = new ArrayList<String>();
		//���o���|�U�Ҧ���Authorization Description
		String [] permissionList = getPermissionLines(path);
		//���o��������
		for(int i = 0; i < permissionList.length ; i++){
			String buf = permissionList[i].substring(0,permissionList[i].indexOf('=')).trim();
			fileMemberList.add(buf);
		}
		
		return (String[]) fileMemberList.toArray(new String[fileMemberList.size()]);
	}
	
	public String getPermissionsData(String path,String memberName){

		String permission = "";
		//���o���|�U�Ҧ���Authorization Description
		String [] permissionList = getPermissionLines(path);
		
		//���o�Y�������v��
		for(int i = 0; i < permissionList.length ; i++){
			 //���o�y�z�����W��
			String membername = permissionList[i].substring(0,permissionList[i].indexOf('=')).trim();
			//�ݬO�_���n���o������
			if( membername.equals(memberName) ){
				permission = permissionList[i].substring(permissionList[i].indexOf('=')+1).trim();
				break;
			}
		}
		
		return permission;
	}
	
	/**
	 * �]�m�s�զC��
	 */
	public void setGroupList(List<String> groupList){
		this.groupHeader.clear();
		
		if( groupList == null )
			return;
		
		//�]�m�s��
		for( int i = 0 ; i < groupList.size() ; i ++ ){
			String groupLine = groupList.get(i) + " =";
			this.groupHeader.add(groupLine);
		}
	}
	
	/**
	 * �]�m�s�զ����C��
	 * @param groupName �s�զW��
	 * @param memberList �s�զ���
	 */
	public void setGroupMember(String groupName, List<String> memberList){
		//�S���s�զ�����T�P�s��Header��T, �h���]�m
		if( this.groupHeader == null || memberList == null ){
			return ;
		}
		//�]�m�s�զ�����s�զW�٬�groupName
		for( int i = 0 ; i < this.groupHeader.size() ; i++ ){
			String headerLine = this.groupHeader.get(i);
			//���n�[�J�������s�մy�z, �|�H�ۦP���s�զW�ٶ}�Y
			if( headerLine.startsWith(groupName) ){
				//�]�m�s�զ���
				for( int j = 0 ; j < memberList.size() ; j++){
					String memberName = memberList.get(j);
					headerLine += " " + memberName;
					//�̫�@�Ӧ������ݭn�[�r�I���j
					if( j+1 != memberList.size() ){
						headerLine += ",";
					}
				}
				//�N�]�m����headerLine��s
				groupHeader.set(i, headerLine);
				break;
			}
		}
	}
	
	/**
	 * �]�m�v����T,
	 * pathmember�|������permissions
	 * @param path ���|
	 * @param pathmember ���|����
	 * @param permissions�@�v����T
	 */
	public void setPermissionData(String path,List<String> pathmember,List<String> permissions){
		//���|�|���[�J, �h�[�J��pathList
		if( path != null && this.pathList.indexOf(path) == -1 ){
			this.pathList.add(path);
		}
		//���|�������v�����s�b�h���[�J���v����T
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
		//�N�v����Ʃ��۹�����path
		this.permissions.put(path, permissionList);
	}
	
}

