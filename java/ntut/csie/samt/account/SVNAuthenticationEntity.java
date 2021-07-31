package ntut.csie.samt.account;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SVNAuthenticationEntity extends IAuthenticationEntity{

	public SVNAuthenticationEntity(File file){
		super(file);
	}
	
	/**
	 * ���o�ɮפ����b��id�C��
	 */
	public List<String> getAccountList(){
		List<String> accountList = new ArrayList<String>();
		//�N�q�ɮ�Ū���X�Ӫ��r��B�z���b��id�C��
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			
			if( line.contains("=") ){
				//���o=���e���r��,���b��id
				line = line.substring(0, line.indexOf("="));
				//�N�b��ID���accountList
				accountList.add(line);
			}
		}
		//�^�Ǳb��id�C��
		return accountList;
	}
	
	/**
	 * ���o�ɮפ����K�X�C��
	 */
	public List<String> getAccountPasswdList(){
		List<String> passwdList = new ArrayList<String>();
		//�N�q�ɮ�Ū���X�Ӫ��r��B�z���b��id�C��
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			
			if( line.contains("=") ){
				//���o=���e���r��,���b��id
				line = line.substring(line.indexOf("=")+1);
				//�N�b��ID���accountList
				passwdList.add(line);
			}
		}
		//�^�Ǳb��id�C��
		return passwdList;
	}
	
	/**
	 * �إ߱b��
	 */
	public void createAccount(String id, String passwd){
		if( id.isEmpty() || passwd.isEmpty() ){
			return;
		}
		this.accountLines.add(id + " = " + passwd);
	}
	
	/**
	 * �ק�K�X
	 */
	public void modifyAccountPasswd(String id, String passwd){
		if( passwd.isEmpty() ){
			return;
		}
		//��M�n�ק�K�X���b��, �i��ק�
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			line = line.trim();
			//�Y���n�ק諸�b��,�h�i��ק�
			if( line.startsWith(id) ){
				line = id + "=" + passwd;
				this.accountLines.set(i, line);
				break;
			}
		}
		
	}
	
}
