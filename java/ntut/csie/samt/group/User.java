package ntut.csie.samt.group;

public class User extends IGroup {

	/**
	 * �غc��
	 * ��l�ƨϥΪ̪�type��user
	 */
	public User(){
		this.type = GroupMemberType.account;
	}
	
	/**
	 * ���o�s�զ����ƶq, �ѩ�o�OUser
	 * �ҥH�S���s�զ���
	 * 
	 * @return 0
	 */
	public int getGroupMemberSize(){
		return 0;
	}
	
	/**
	 * ��M�s�զ���, �����N�^�Ǧۤv, 
	 * �_�h�^��null
	 * 
	 * @param igroup �n�d�ߪ��s�զ���
	 */
	public IGroup findGroupMember(IGroup igroup){
		//���o�n�d�ߪ��s�զ����W�ٻP���A
		String name = igroup.getName();
		GroupMemberType type = igroup.type;
		
		return findGroupMember(name, type);
	}
	
	/**
	 * ��M�s�զ���, �����N�^�Ǧۤv, 
	 * �_�h�^��null
	 * 
	 * @param name �n�d�ߪ��s�զ����W��
	 * @param type �n�d�ߪ��s�զ������A
	 */
	public IGroup findGroupMember(String name, GroupMemberType type){
		//�ݦۤv���W�ٻP���A�O�_�P�n�d�ߪ��ۦP, �O���ܴN�^�Ǧۤv, ���M�N�|��null
		if( this.name.equals(name) && this.type == type ){
			return this;
		}
		
		return null;
	}
	
}
