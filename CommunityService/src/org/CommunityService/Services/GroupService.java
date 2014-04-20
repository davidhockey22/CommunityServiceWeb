package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

	public static void recalcAll(Group g) {

		g = getGroupByIdWithAttachedEntities(g.getGroupId(), "GroupMembers");

		float points = 0;
		float rating = 0;
		int hours = 0;

		for (Iterator<GroupMember> iterator = g.getGroupMembers().iterator(); iterator.hasNext();) {
			GroupMember gm = (GroupMember) iterator.next();
			rating += gm.getVolunteer().getAvgRating();
			points += gm.getVolunteer().getPoints();
			hours += gm.getVolunteer().getHoursWorked();
		}
		rating /= g.getGroupMembers().size();

		g.setAvgRatingOfVolunteers(rating);
		g.setHoursWorked(hours);
		g.setPoints(points);

		DBConnection.update(g);
	}

	public static Group getGroupByName(String groupName) throws HibernateException {
		String hql = "from Group as g where g.groupName=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(groupName);
		@SuppressWarnings("unchecked")
		List<Group> groups = ((List<Group>) DBConnection.query(hql, params));
		return groups.isEmpty() ? null : groups.get(0);
	}

	public static List<Group> getGroupLeaderBoardByPoints() throws HibernateException {
		String hql = "from Group as g order by g.points desc";
		@SuppressWarnings("unchecked")
		List<Group> groups = ((List<Group>) DBConnection.query(hql, null));
		return groups;
	}

	public static List<Group> getGroupLeaderBoardByHours() throws HibernateException {
		String hql = "from Group as g order by g.hoursWorked";
		@SuppressWarnings("unchecked")
		List<Group> groups = ((List<Group>) DBConnection.query(hql, null));
		return groups;
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
		map.put("GroupMembersList", " left join fetch g.groupMembers");
		map.put("Organizations", " left join fetch g.organizations ");
		map.put("Pictures", " left join fetch g.pictures ");
		entitiesMap = Collections.unmodifiableMap(map);
	}

	public static Group getGroupByIdWithAttachedEntities(int groupId, String... attachedEntities) throws HibernateException {
		String hql = "from Group as g ";
		if (attachedEntities != null) {
			for (int i = 0; i < attachedEntities.length; i++) {
				String next = entitiesMap.get(attachedEntities[i]);
				hql += next == null ? "" : next;
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
