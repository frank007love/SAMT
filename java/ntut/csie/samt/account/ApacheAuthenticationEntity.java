package ntut.csie.samt.account;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class ApacheAuthenticationEntity extends IAuthenticationEntity{

	/*
	 * �Ψ��x�s�q�ɮפ�Ū���X�Ӫ��b��, ���F��O�w���K�X�P�ϥΪ̭n�[�J���Ϲj,
	 * �]��MD5���X�����H�ѱK
	 */
	private List<String> accountList;
	
	public ApacheAuthenticationEntity(File file){
		super(file);
		accountList = new ArrayList<String>();
	}
	
	/**
	 * ���o�ɮפ����b��id�C��
	 */
	public List<String> getAccountList(){
		this.accountList.clear();
		//�N�q�ɮ�Ū���X�Ӫ��r��B�z���b��id�C��
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			
			if( line.contains(":") ){
				//���o:���e���r��,���b��id
				line = line.substring(0, line.indexOf(":"));
				//�N�b��ID���accountList
				this.accountList.add(line);
			}
		}
		//�^�Ǳb��id�C��
		return this.accountList;
	}
	
	/**
	 * ���o�ɮפ����K�X�C��
	 */
	public List<String> getAccountPasswdList(){
		List<String> passwdList = new ArrayList<String>();
		//�N�q�ɮ�Ū���X�Ӫ��r��B�z���b��id�C��
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			
			if( line.contains(":") ){
				//���o:���e���r��,���b��id
				line = line.substring(line.indexOf(":")+1);
				//�N�b��ID���accountList
				passwdList.add(line);
			}
		}
		//�^�Ǳb��id�C��
		return passwdList;
	}
	
	/**
	 * �ק�b��
	 * @param oldId
	 * @param newId
	 */
	public void modifyAccountId(String oldId, String newId){
		if( accountList.size() == 0 )
			this.getAccountList();
		
		int index = accountList.indexOf(oldId);
		//�n�ק諸ID���s�b�h���i��ק�
		if( index == -1 ) return;
		
		//�i��ID�ק�
		accountList.set(index , newId);
		super.modifyAccountId(oldId, newId);
	}
	
	/**
	 * �ק�K�X
	 * @param id �n�ק諸ID
	 * @param passwd �s�K�X
	 */
	public void modifyAccountPasswd(String id, String passwd){
		if( accountList.size() == 0 )
			this.getAccountList();
		//�R���b���A�s�W
		this.deleteAccount(id);
		this.createAccount(id, passwd);
	}
	
	/**
	 * �ϥ�Apache��htpasswd�إ߱b���K�X
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
			//�إߤ@�ӼȦs�ɥΨӵ�Apache�����ɫإ߱K�X
			tempfile.createNewFile();
			//���o�ɮ׸��|
			String path = tempfile.getPath();
			String line = "";
			
			//����إ߱b�����O
			if( passwd != "" ){
				String command = this.getCommand();
				Process p = rt.exec( command + path + " " + id + " " + passwd);
				//�i��Process����
				p.waitFor();
				
				//���o��إߪ���T���accountLines
				BufferedReader reader = new BufferedReader(new FileReader(tempfile));
				
				line = reader.readLine();
				
				//����Reader, �קK�L�k�R���Ȧs��
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
		//�R���Ȧs��
		tempfile.delete();
	}
	
	/**
	 * ���o�R�O
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
	 * �R���b���y�z��T�P�b���C��
	 * @param id �n�R�����b��ID
	 */
	public void deleteAccount(String id){
		//��M�n�R���b����index
		int index = this.accountList.indexOf(id);
		//�n�R�����b���s�b�h�i��R��
		if( index != -1 ){
			this.accountList.remove(id);
			super.deleteAccount(id);
		}
	}
}
