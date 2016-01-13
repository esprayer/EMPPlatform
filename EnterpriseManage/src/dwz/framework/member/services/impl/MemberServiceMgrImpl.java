/**
 * 
 */
package dwz.framework.member.services.impl;

import java.util.List;

import dwz.framework.exception.ObjectDuplicateException;
import dwz.framework.exception.ObjectHasSubObjectException;
import dwz.framework.member.Member;
import dwz.framework.member.persistence.UsrMemberMapper;
import dwz.framework.member.services.MemberServiceMgr;
import dwz.framework.member.services.object.ConditionVO;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public class MemberServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements MemberServiceMgr
{
	private UsrMemberMapper usrMemberMapper;
	
	public UsrMemberMapper getUsrMemberMapper()
	{
		return usrMemberMapper;
	}

	public void setUsrMemberMapper(UsrMemberMapper usrMemberMapper)
	{
		this.usrMemberMapper = usrMemberMapper;
	}
	
	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#addMember(dwz.framework.member.Member)
	 */
	@Override
	public Member addMember(Member member) throws ObjectDuplicateException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#confiredMember(dwz.framework.member.Member)
	 */
	@Override
	public Member confiredMember(Member member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#createMember(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Member createMember(String email, String username, String password)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#deleteMember(dwz.framework.member.Member)
	 */
	@Override
	public Member deleteMember(Member member)
			throws ObjectHasSubObjectException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#getMember(java.lang.Long)
	 */
	@Override
	public Member getMember(Long id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#getMemberByEmail(java.lang.String)
	 */
	@Override
	public Member getMemberByEmail(String email)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#getMemberByUsername(java.lang.String)
	 */
	@Override
	public Member getMemberByUsername(String username)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#getMembers(dwz.framework.member.services.object.ConditionVO, int, int)
	 */
	@Override
	public List<Member> getMembers(ConditionVO vo, int offset, int limit)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#getTotalMembers(dwz.framework.member.services.object.ConditionVO)
	 */
	@Override
	public int getTotalMembers(ConditionVO vo)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see dwz.framework.member.services.MemberSrvMgr#updateMember(dwz.framework.member.Member)
	 */
	@Override
	public Member updateMember(Member member)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
