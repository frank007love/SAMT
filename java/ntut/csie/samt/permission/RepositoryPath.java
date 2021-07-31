package ntut.csie.samt.permission;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import ntut.csie.samt.group.*;

public class RepositoryPath {

	private List<Permission> permissions;
	private String pathname;
	
	/**
	 * �غc��
	 * ��l��permission
	 * �]�w���|�W��
	 * 
	 * @param name ���|�W��
	 */
	public RepositoryPath(String name){
		this.permissions = new ArrayList<Permission>();
		this.pathname = name;
	}
	
	/**
	 * �s�W�@��Permission Data
	 * 
	 * @param igroup ��PermissionData�Ҵy�z���ϥΪ�
	 * @return �s�W���\�h�^��true, �Ϥ��hfalse
	 */
	public boolean addPermission(IGroup igroup,String type){
		
		//��M�ҭn�s�W��Permission�����O�_�w�s�b,�s�b�h���[�J
		if( this.findPermission(igroup) != null || igroup == null  ){
			return false;
		}
		//�s�W�@��Permission,��init�v�����S��
		Permission permission = new Permission(igroup);
		permission.setType(type);
		this.permissions.add(permission);
		return true;
	}
	
	/**
	 * �R���s���v��
	 * 
	 * @param igroup �n�R���ҳ]�w���v������
	 * @return �R�����\�h�^��true, �Ϥ��hfalse
	 */
	public boolean removePermission(IGroup igroup){
		
		Permission permission = this.findPermission(igroup);
		//��M�ҭn�s�W��Permission�����O�_�w�s�b,�s�b�~�R��
		if( permission == null ){
			return false;
		}
		//�R���v�����
		this.permissions.remove(permission);
		return true;
	}
	
	/**
	 * �ק令���s���v��
	 * 
	 * @param igroup �n�ק蠟����
	 * @param type �s���v�����A
	 * @return �ק令�\�h�^��true, �Ϥ��hfalse
	 */
	public boolean modifyPermission(IGroup igroup, String type){
		
		Permission permission = this.findPermission(igroup);
		//��M�ҭn�s�W��Permission�����O�_�w�s�b,�s�b�~�ק��v��
		if( permission == null ){
			return false;
		}
		//�ק��v��
		permission.setType(type);
		return true;
	}
	
	/**
	 * �̾ڦ����M��Permission
	 * 
	 * @param igroup �n��MPermission������
	 * @return ��M���G, �����h�^��permission
	 */
	public Permission findPermission(IGroup igroup){
		Iterator<Permission> iter = this.permissions.iterator();
		//find the pathMember match the igroup 
		while(iter.hasNext()){
			Permission permission = (Permission)iter.next();
			IGroup pathMember = permission.getPathMember();
			//�Y���|�����s�b�B�P�n��M�������ۦP�h�^�ǥ���permission
			if( pathMember != null && pathMember.isEqual(igroup) ){
				return permission;		
			}
		}
		//No find
		return null;
	}
	
	/**
	 * ���o���|�U�ҳ]�w������
	 * @return
	 */
	public List<String> getStringMemberList(){
		Iterator<Permission> iter = this.permissions.iterator();
		List<String> mebmerList = new ArrayList<String>();

		while(iter.hasNext()){
			Permission permission = (Permission)iter.next();
			String name = permission.getPathMember().getName();
			String type = permission.getPathMember().getType();
			//�Y�����Ogroup�h�W�٭n�[@, �Y�Oaccount�h���ݭn
			if( type.equals("account") ){
				mebmerList.add(name);
			} else if( type.equals("group") ){
				mebmerList.add("@" + name);
			}
			
		}
		//�^�Ǧ����C��
		return mebmerList;
	}
	
	/**
	 * ���o�����v��
	 * @param igroup ����
	 * @return �v��
	 */
	public String getPermission(IGroup igroup){
		//���o�����v��
		Permission p = this.findPermission(igroup);
		
		//�Y�L������, �v�����L
		if( p == null ){
			return "";
		}
		//�^�Ǧ����v��
		return p.getType();
	}
	
	/**
	 * ���o���|�W��
	 * @return
	 */
	public String getPathName(){
		return this.pathname;
	}
}
