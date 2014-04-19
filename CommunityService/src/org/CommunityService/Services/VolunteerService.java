package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.EntitiesMapped.VolunteerDevice;
import org.CommunityService.Validators.PasswordHash;
import org.hibernate.HibernateException;

public class VolunteerService {

	public static void addVolunteer(Volunteer v) throws HibernateException {
		DBConnection.persist(v);
		return;
	}

	public static void updateVolunteer(Volunteer v) throws HibernateException {
		DBConnection.update(v);
		return;
	}

	public static void updateVolunteerPoints(Volunteer v, int hours, int rating, float bonus) {

		// Update volunteer
		v.setEventsWorked(v.getEventsWorked() + 1);
		v.setHoursWorked(v.getHoursWorked() + hours);
		v.setAvgRating(v.getAvgRating() + (rating / (float) v.getEventsWorked()));
		float points = v.getPoints() + hours * bonus;
		v.setPoints(points);
		DBConnection.update(v);

		// update group aggregation
		List<GroupMember> gms = VolunteerService.getGroupMembersByVolunteer(v);
		for (Iterator<GroupMember> iterator = gms.iterator(); iterator.hasNext();) {
			GroupMember groupMember = (GroupMember) iterator.next();
			GroupService.recalcAll(groupMember.getGroup());
		}
	}

	public static void registerVolunteer(String username, String password, String email, String phoneNumber, String firstName, String lastName)
			throws Exception {
		password = PasswordHash.getHash(password, email);
		Volunteer v = new Volunteer(username, password, phoneNumber, email, firstName, lastName);
		DBConnection.persist(v);
		return;
	}

	public static Volunteer getVolunteerByName(String name) throws HibernateException {
		String hql = "from Volunteer as v left join fetch v.volunteerDevices where v.volunteerName=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(name);
		@SuppressWarnings("unchecked")
		List<Volunteer> volunteers = (List<Volunteer>) DBConnection.query(hql, params);
		return volunteers.isEmpty() ? null : volunteers.get(0);
	}

	public static Volunteer getVolunteerById(Integer id) throws HibernateException {
		if (id == null)
			return null;
		String hql = "from Volunteer as v where v.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(id);
		@SuppressWarnings("unchecked")
		List<Volunteer> volunteers = (List<Volunteer>) DBConnection.query(hql, params);
		return volunteers.isEmpty() ? null : volunteers.get(0);
	}

	private static final Map<String, String> entitiesMap;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("GroupMembers", " left join fetch v.groupMembers as gm left join fetch gm.group ");
		map.put("VolunteerInterests", " left join fetch v.volunteerInterests as VI left join fetch VI.interest");
		map.put("OrganizationFollowers", " left join fetch v.organizationFollowers as orgf left join fetch orgf.organization ");
		map.put("VolunteerSkills", " left join fetch v.volunteerSkills as vs left join fetch vs.skill ");
		map.put("EventVolunteers", " left join fetch v.eventVolunteers as ev left join fetch ev.event ");
		map.put("Notifications", " left join fetch v.notifications as n");

		entitiesMap = Collections.unmodifiableMap(map);
	}

	public static Volunteer getVolunteerByIdWithAttachedEntities(Integer id, String... attachedEntities) throws HibernateException {
		String hql = "from Volunteer as v ";
		if (attachedEntities != null) {
			for (int i = 0; i < attachedEntities.length; i++) {
				String next = entitiesMap.get(attachedEntities[i]);
				hql += next == null ? "" : next;
			}
		}
		hql += " where v.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(id);
		@SuppressWarnings("unchecked")
		List<Volunteer> volunteers = (List<Volunteer>) DBConnection.query(hql, params);
		return volunteers.isEmpty() ? null : volunteers.get(0);
	}

	public static Volunteer getVolunteerByToken(String token) {

		String hql = "SELECT v.volunteer FROM VolunteerDevice as v join fetch v.volunteer.volunteerDevices where v.deviceTokenInternal=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(token);
		try {
			@SuppressWarnings("unchecked")
			List<Volunteer> list = (List<Volunteer>) DBConnection.query(hql, params);
			Volunteer v = null;
			if (list != null && list.isEmpty() == false)
				v = list.get(0);
			return v;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void resetLoginToken(Volunteer v) {

		// new token
		Long token = UUID.randomUUID().getMostSignificantBits();

		VolunteerDevice device = null;
		if (v.getVolunteerDevices().isEmpty()) {

			// new device
			device = new VolunteerDevice(v, token.toString());
			v.getVolunteerDevices().add(device);
		} else {
			Iterator<VolunteerDevice> iter = v.getVolunteerDevices().iterator();
			device = iter.next();
		}
		device.setDeviceTokenInternal(token.toString());

		// persist
		try {
			VolunteerService.updateVolunteer(v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// move token to "salt" field because HibernateUtil.clean() deletes the
		// volunteerDevice set
		Iterator<VolunteerDevice> i = v.getVolunteerDevices().iterator();
		VolunteerDevice dev = i.next();
		v.setSalt(dev.getDeviceTokenInternal());
	}

	public static List<EventVolunteer> getEventVolunteersByVolunteer(Volunteer volunteer) throws HibernateException {
		String hql = "FROM EventVolunteer AS ev JOIN FETCH ev.event left join fetch ev.event.volunteer WHERE ev.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volunteer.getVolunteerId());
		@SuppressWarnings("unchecked")
		List<EventVolunteer> eventVolunteers = (List<EventVolunteer>) DBConnection.query(hql, params);
		return (eventVolunteers != null ? eventVolunteers : new ArrayList<EventVolunteer>());
	}

	public static List<EventVolunteer> getEventVolunteersByVolunteerBetweenDates(Volunteer volunteer, Date date1, Date date2) {
		Date before, after;
		if (date1.after(date2)) {
			after = date1;
			before = date2;
		} else {
			after = date2;
			before = date1;
		}
		String hql = "from EventVolunteer as ev left join fetch ev.event as e left join fetch ev.volunteer as v where v.volunteerId = ? and e.endTime > ? and e.beginTime < ?";
		List<Object> params = new ArrayList<Object>();
		params.add(volunteer.getVolunteerId());
		params.add(before);
		params.add(after);
		@SuppressWarnings("unchecked")
		List<EventVolunteer> eventVolunteers = (List<EventVolunteer>) DBConnection.query(hql, params);
		return eventVolunteers;
	}

	public static List<GroupMember> getGroupMembersByVolunteer(Volunteer volunteer) throws HibernateException {
		String hql = "FROM GroupMember AS gm LEFT JOIN FETCH gm.group WHERE gm.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volunteer.getVolunteerId());
		@SuppressWarnings("unchecked")
		List<GroupMember> groupMembers = (List<GroupMember>) DBConnection.query(hql, params);
		return (groupMembers != null ? groupMembers : new ArrayList<GroupMember>());
	}

	public static List<OrganizationFollower> getOrganizationFollowersByVolunteer(Volunteer volunteer) throws HibernateException {
		String hql = "FROM OrganizationFollower AS orgf LEFT JOIN FETCH orgf.organization WHERE orgf.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volunteer.getVolunteerId());
		@SuppressWarnings("unchecked")
		List<OrganizationFollower> organizationFollowers = (List<OrganizationFollower>) DBConnection.query(hql, params);
		return (organizationFollowers != null ? organizationFollowers : new ArrayList<OrganizationFollower>());
	}

	public static List<Volunteer> getLeaderboardByPoints(int limit) throws HibernateException {
		String hql = "from Volunteer as v order by v.points desc";
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null, limit);
		return v;
	}

	public static List<Volunteer> getLeaderboardByPoints() throws HibernateException {
		String hql = "from Volunteer as v order by v.points desc";
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null);
		return v;
	}

	public static List<Volunteer> getLeaderboardByHours() throws HibernateException {
		String hql = "from Volunteer as v order by v.hoursWorked desc";
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null, 100);
		return v;
	}

	public static List<Volunteer> getVolunteers() throws HibernateException {
		String hql = "from Volunteer as v";
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null);
		return v;
	}

	public static List<Volunteer> getVolunteersLikeName(String userName) throws HibernateException {
		String hql = "from Volunteer as v where v.volunteerName LIKE ?";
		userName = "%" + userName + "%";
		List<String> params = new ArrayList<String>();
		params.add(userName);
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, params);
		return v;
	}

}
