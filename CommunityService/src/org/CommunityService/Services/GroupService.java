package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.hibernate.HibernateException;

public class GroupService {
	
	public static void addGroup(Group o) throws HibernateException {
		DBConnection.save(o);
		return;
	}
	
	public static void addGroupMember(GroupMember o) throws HibernateException {
		DBConnection.persist(o);
		return;
	}
	
	public static void removeGroupMember(int groupMemberId) {
		String hql = "delete GroupMember as g where g.groupMemberId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(groupMemberId);
		try {
			DBConnection.query(hql, params);
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}	
	public static void updateGroup(Group o) throws HibernateException {
		DBConnection.update(o);
		return;
	}
	
	public static Group getGroupByName(String groupName) throws HibernateException {
		String hql = "from Group as g where g.groupName=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(groupName);
		try {
			@SuppressWarnings("unchecked")
			Group o = (Group) (((List<Group>) DBConnection.query(hql, params)).get(0));
			return o;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static Group getGroupById(int groupId) throws HibernateException {
		String hql = "from Group as o JOIN FETCH o.groupMembers where o.groupId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(groupId);
		try {
			@SuppressWarnings("unchecked")
			Group o = (Group) (((List<Group>) DBConnection.query(hql, params)).get(0));
			return o;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static GroupMember getGroupMemberById(int groupMemberId) throws HibernateException {
		String hql = "from GroupMember as o JOIN FETCH o.volunteer where o.groupMemberId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(groupMemberId);
		try {
			@SuppressWarnings("unchecked")
			GroupMember o = (GroupMember) (((List<GroupMember>) DBConnection.query(hql, params)).get(0));
			return o;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}	
	
	public static Set<GroupMember> getVolunteerGroupsByVolunteerId(Integer vId) {
		if (vId == null)
			return null;
		String hql = "from GroupMember as VS join fetch VS.volunteer where VS.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(vId);
		try {
			@SuppressWarnings("unchecked")
			Set<GroupMember> v = new HashSet<GroupMember>((((List<GroupMember>) DBConnection.query(hql, params))));
			return v;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}	
}
