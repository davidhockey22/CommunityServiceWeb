package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.hibernate.HibernateException;

public class GroupService {

	public static void addGroup(Group o) throws HibernateException {
		DBConnection.save(o);
		return;
	}

	public static void addGroupMember(GroupMember o) throws HibernateException {
		DBConnection.save(o);
		return;
	}

	public static void updateGroup(Group o) throws HibernateException {
		DBConnection.update(o);
		return;
	}
	public static void updateGroupMember(GroupMember o) throws HibernateException {
		DBConnection.update(o);
		return;
	}
	public static Group getGroupByName(String groupName) throws HibernateException {
		String hql = "from Group as g where g.groupName=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(groupName);
		@SuppressWarnings("unchecked")
		List<Group> groups = ((List<Group>) DBConnection.query(hql, params));
		return groups.isEmpty() ? null : groups.get(0);
	}

	public static List<Group> getGroupsLikeName(String groupName) throws HibernateException {
		String hql = "from Group as g where g.groupName like ?";
		ArrayList<String> params = new ArrayList<String>();
		params.add("%" + groupName + "%");
		@SuppressWarnings("unchecked")
		List<Group> groups = ((List<Group>) DBConnection.query(hql, params));
		return groups;
	}

	public static Group getGroupById(int groupId) throws HibernateException {
		return getGroupByIdWithAttachedEntities(groupId, "");
	}

	private static final Map<String, String> entitiesMap;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("GroupMembers", " left join fetch g.groupMembers as gm left join fetch gm.volunteer ");
		map.put("Organizations", " left join fetch g.organiztions ");
		map.put("Pictures", " left join fetch g.pictures ");
		entitiesMap = Collections.unmodifiableMap(map);
	}

	public static Group getGroupByIdWithAttachedEntities(int groupId, String... attachedEntities) throws HibernateException {
		String hql = "from Group as g ";
		if (attachedEntities != null) {
			for (int i = 0; i < attachedEntities.length; i++) {
				hql += entitiesMap.get(attachedEntities[i]);
			}
		}
		hql += " where g.groupId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(groupId);
		@SuppressWarnings("unchecked")
		List<Group> groups = ((List<Group>) DBConnection.query(hql, params));
		return groups.isEmpty() ? null : groups.get(0);
	}
	public static void removeGroup(Integer groupId) {
		List<Integer> params = new ArrayList<Integer>();
		params.add(groupId);
		DBConnection.queryDelete("delete Group g where g.groupId=?", params);
		System.out.println("GroupService.removeGroup()");
	}	
}
