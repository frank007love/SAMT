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
	 * �NŪ���X�Ӫ��b����T�@��@����accountLines
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
	 * �N�b����T�s�J�ɮפ�
	 */
	public void save(){
		Writer writer = null;
		try{
			writer = new FileWriter(this.myFile);
			
			Iterator<String> iter = this.accountLines.iterator();
			//�N�C�@����T�g�J�ɮפ�
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
	 * ���o�q�ɮפ�Ū�����y�z�b����T
	 * @return
	 */
	public List<String> getAccountLines(){
		return this.accountLines;
	}
	
	/**
	 * ���o�b��ID�C��
	 * @return
	 */
	abstract public List<String> getAccountList();
	
	/**
	 * ���o�K�X�C��
	 * @return
	 */
	abstract public List<String> getAccountPasswdList();
	
	/**
	 * �ק�b��
	 * @param oldId
	 * @param newId
	 */
	public void modifyAccountId(String oldId, String newId){
		//�����ϥΪ̭ק令�P�˱b����
		if( this.findAccoutLineIndex(newId) != -1 ){
			return;
		}
		//��M�n�ק�b����oldId��accoutLine��Tindex
		int index = this.findAccoutLineIndex(oldId);
		
		//�����h�i��ק�b��Id
		if( index != -1 ){
			//���o�쥻���y�z��T
			String line = this.accountLines.get(index);
			//�i��b���ק�
			line = line.replace(oldId, newId);
			this.accountLines.set(index,line);
		}
	}
	
	/**
	 * �ק�K�X
	 * @param id �n�ק諸ID
	 * @param passwd �s�K�X
	 */
	abstract public void modifyAccountPasswd(String id, String passwd);
	
	/**
	 * �R���b���y�z��T
	 * @param id �n�R�����b��ID
	 */
	public void deleteAccount(String id){
		//��M�n�R���b����id��accoutLine��Tindex
		int index = this.findAccoutLineIndex(id);
		
		//�����h�i��R���b���y�z��T
		if( index != -1 ){
			this.accountLines.remove(index);
		}
	}
	
	/**
	 * �إ߷s���b��
	 * @param id �s�b��ID
	 * @param passwd �s�b���K�X
	 */
	abstract public void createAccount(String id, String passwd);
	
	/**
	 * ��߱b����id��accountLine
	 * @param id �n��M���b��ID
	 * @return �Ҧb��accountLines��index, �S���h��-1
	 */
	protected int findAccoutLineIndex(String id){
		//��M�O�y�z�n��M���b������T
		for( int i = 0 ; i < this.accountLines.size() ; i++ ){
			String line = this.accountLines.get(i);
			//�Y�O�n��M���h�^��index
			if( line.startsWith(id) ){
				return i;
			}
		}	
		//�S���^��-1
		return -1;
	}
}
