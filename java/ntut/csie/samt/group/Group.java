package ntut.csie.samt.group;

import java.util.ArrayList;
import java.util.List;;

public class Group extends IGroup {

	private List<IGroup> groupMember;
	
	/**
	 * �غc��
	 * ��l�Ƹs�ժ�type��group
	 */
	public Group(){
		this.type = GroupMemberType.group;
		groupMember = new ArrayList<IGroup>();
	}
	
	/**
	 * �s�W�s�զ���
	 * 
	 * @param igroup �ҭn�s�W���s�զ���
	 */
	public boolean addGroupMember(IGroup igroup){
		if( this.findGroupMember(igroup) == null ){
			this.groupMember.add(igroup);
			return true;
		}
		return false;
	}
	
	/**
	 * �R���s�զ���, �ǥѶǤJIGroup���A, �M��
	 * ���o�W�ٻP���A, �A�ǥ�deleteGroupMember(String name,
	 *  GroupMemberType type)�h�R��
	 * 
	 * @param igroup 
	 */
	public void deleteGroupMember(IGroup igroup){
		String name = igroup.getName();
	
		deleteGroupMember(name, igroup.getType());	
	}
	
	/**
	 * �R���s�զ���, �ǥѦW�ٻP�������A
	 * 
	 * @param name �����W��
	 * @param type �������A
	 */
	public boolean deleteGroupMember(String name, String type){

		for( int i = 0 ; i < this.groupMember.size() ; i++ ){
			
			IGroup groupMember = this.groupMember.get(i);
			
			//�R���P�s�զ����W�ٻP���A�ۦP��
			if( groupMember.getName().equals(name) && groupMember.getType().equals(type) ){
				this.groupMember.remove(groupMember);
				
				return true;
			}
		}
		
		return false;
	}	
	
	/**
	 * ���o�Ҧ��s�զ���
	 */
	public List<IGroup> getGroupMembers(){
		return this.groupMember;
	}
	
	/**
	 * ���o�s�զ������j�p
	 */
	public int getGroupMemberSize(){
		return this.groupMember.size();
	}
	
	/**
	 * 
	 * �d�߸s�զ����ǥ�IGroup, �����o�W�ٻP���A,
	 * �A�Q��findGroupMember(String name,
	 * 	 GroupMemberType type)�h��M
	 * 
	 * @param igroup �n�d�ߪ��s�զ���
	 * 
	 * @return ����M��N�^�ǧ�쪺�s�զ���,
	 * 		�S���N�^��null
	 */
	public IGroup findGroupMember(IGroup igroup){
		//���o�n�d�ߤ��W�ٻP���A
		String name = igroup.getName();
		return findGroupMember(name, igroup.type);
	}
	
	/**
	 * �d�߸s�զ����ǥѦW�ٻP���A
	 * 
	 * @param name �n�d�ߪ��s�զ����W��
	 * @param type �n�d�ߪ��s�զ������A
	 * 
	 * @return ����M��N�^�ǧ�쪺�s�զ���,
	 * 		�S���N�^��null
	 */
	public IGroup findGroupMember(String name, GroupMemberType type){
		
		//�d�߸s�զ����O�_���P�n�d�ߪ��W�ٻP���A�ۦP
		for(int i = 0; i < this.groupMember.size() ; i++){
			
			IGroup groupMember = this.groupMember.get(i);
			
			//�^�ǲզ����W�ٻP���A�ۦP��
			if( groupMember.getName().equals(name) && groupMember.type == type ){
				return groupMember;
			}
			
		}
		//�S���
		return null;
	}
	
}
