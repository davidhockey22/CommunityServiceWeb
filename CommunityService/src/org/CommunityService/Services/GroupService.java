package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.hibernate.HibernateException;

public class GroupService {
	
	public static void addGroup(Group o) throws Exception {
		//DBConnection.persist(o);
		DBConnection.persistRelationalEntity(o);
		return;
	}
	
	public static void updateGroup(Group o) throws Exception {
		DBConnection.update(o);
		return;
	}
	
	public static Set<GroupMember> getVolunteerGroupsByVolunteerId(Integer vId) {
		if (vId == null)
			return null;
		String hql = "from GroupMember as VS join fetch VS.volunteer where VS.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(vId);
		try {
			@SuppressWarnings("unchecked")
			Set<GroupMember> v = new HashSet((((List<GroupMember>) DBConnection.query(hql, params))));
			return v;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}	
}
