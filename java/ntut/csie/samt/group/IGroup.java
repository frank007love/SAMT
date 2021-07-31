package ntut.csie.samt.group;

import java.util.List;

public abstract class IGroup {

	protected String name;
	protected GroupMemberType type;

	public IGroup(){

	}
	
	/**
	 * �]�w�W��
	 * 
	 * @param name �ҭn�]�w���W��
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * ���o�W��
	 * 
	 * @return �W��
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * ���o�s�զ��������A, �s�թΨϥΪ�
	 * 
	 * @return GroupMemberType.group or 
	 * 	GroupMemberType.user
	 */
	public String getType(){
		return this.typeTanslateToString(this.type);
	}
	
	/**
	 * �s�W�s�զ���
	 * 
	 * @param igroup �ҭn�s�W���s�զ���
	 */
	public boolean addGroupMember(IGroup igroup){
		return false;
	}
	
	/**
	 * �ھڦW�ٻP���A,
	 * ������igroup�O�_�ۦP
	 * 
	 * @return �ۦP�^��true, �Ϥ��hfalse
	 */
	public boolean isEqual(IGroup igroup){
		if( igroup != null && igroup.name.equals(this.name) && igroup.type.equals(this.type) ){
			return true;
		}
		
		return false;
	}
	
	/**
	 * �R���s�զ���
	 * 
	 * @param igroup �ҭn�R�����s�զ���
	 */
	public void deleteGroupMember(IGroup igroup){
		
	}
	
	/**
	 * �R���s�զ���, �ǥѦW�ٻP���A
	 * 
	 * @param name �n�R���������W��
	 * @param type �n�R�����������A
	 */
	public boolean deleteGroupMember(String name, String type){
		return false;
	}
	
	/**
	 * ���o�Ҧ��s�զ���
	 * 
	 * @return null
	 */
	public List<IGroup> getGroupMembers(){
		return null;
	}
	
	/**
	 * ���o�s�զ������ƶq
	 * 
	 * @return
	 */
	abstract public int getGroupMemberSize();
	
	/**
	 * ��M�s�զ���
	 * 
	 * @param igroup �n��M���s�զ���
	 * @return �ҧ�쪺�s�զ���, �S���h�^��null
	 */
	abstract public IGroup findGroupMember(IGroup igroup);
	
	/**
	 * ��M�s�զ���, �ǥѦW�ٻP���A
	 * 
	 * @param name �n��M���W��
	 * @param type �n��M�����A
	 * @return �ҧ�쪺�s�զ���, �S���h�^��null
	 */
	abstract public IGroup findGroupMember(String name, GroupMemberType type);
	
	private String typeTanslateToString(GroupMemberType type){
		String retureType = "";
		
		if( type == GroupMemberType.account ){
			retureType = "account";
		} else if( type == GroupMemberType.group ){
			retureType = "group";
		}
		return retureType;
	}
	
}
