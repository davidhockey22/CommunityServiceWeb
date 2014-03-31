package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.EntitiesMapped.VolunteerDevice;
import org.CommunityService.Validators.PasswordHash;
import org.hibernate.HibernateException;

public class VolunteerService {

	public static Volunteer getVolunteerByName(String name) {
//		Used the following to check the error page.
//		if (name.matches("banana"))
//			throw new HibernateException("banana");
		String hql = "from Volunteer as v left join fetch v.volunteerDevices where v.volunteerName=?";
		ArrayList<String> params = new ArrayList<String>();
		params.add(name);
		try {
			@SuppressWarnings("unchecked")
			List<Volunteer> vList = (List<Volunteer>) DBConnection.query(hql, params);
			if (vList.size() > 0) {
				Volunteer v = vList.get(0);
				return v;
			} else {
				return null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Volunteer getVolunteerById(Integer id) {
		if (id == null)
			return null;
		String hql = "from Volunteer as v where v.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(id);
		try {
			@SuppressWarnings("unchecked")
			Volunteer v = (Volunteer) (((List<Volunteer>) DBConnection.query(hql, params)).get(0));
			return v;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final Map<String, String> entitiesMap;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("GroupMembers", " left join fetch v.groupMembers ");
		map.put("VolunteerInterests", " left join fetch v.volunteerInterests ");
		map.put("OrganizationFollowers", " left join fetch v.organizationFollowers as orgf left join fetch orgf.organization ");
		map.put("VolunteerSkills", " left join fetch v.volunteerSkills as vs left join fetch vs.skill ");
		map.put("EventVolunteers", " left join fetch v.eventVolunteers as ev left join fetch ev.event ");
		entitiesMap = Collections.unmodifiableMap(map);
	}

	public static Volunteer getVolunteerByIdWithAttachedEntities(Integer id, Set<String> attachedEntities) {
		String hql = "from Volunteer as v ";
		if (attachedEntities != null) {
			for (Iterator<String> iterator = attachedEntities.iterator(); iterator.hasNext();) {
				String entity = (String) iterator.next();
				hql += entitiesMap.get(entity);
			}
		}
		hql += " where v.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(id);
		try {
			@SuppressWarnings("unchecked")
			Volunteer v = (Volunteer) (((List<Volunteer>) DBConnection.query(hql, params)).get(0));
			return v;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
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

	public static List<EventVolunteer> getEventVolunteersByVolunteer(Volunteer volunteer) {
		String hql = "FROM EventVolunteer AS ev JOIN FETCH ev.event WHERE ev.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volunteer.getVolunteerId());
		try {
			@SuppressWarnings("unchecked")
			List<EventVolunteer> eventVolunteers = (List<EventVolunteer>) DBConnection.query(hql, params);
			return (eventVolunteers != null ? eventVolunteers : new ArrayList<EventVolunteer>());
		} catch (HibernateException e) {
			e.printStackTrace();
			return new ArrayList<EventVolunteer>();
		}
	}

	public static List<GroupMember> getGroupMembersByVolunteer(Volunteer volunteer) {
		String hql = "FROM GroupMember AS gm LEFT JOIN FETCH gm.group WHERE gm.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volunteer.getVolunteerId());
		try {
			@SuppressWarnings("unchecked")
			List<GroupMember> groupMembers = (List<GroupMember>) DBConnection.query(hql, params);
			return (groupMembers != null ? groupMembers : new ArrayList<GroupMember>());
		} catch (HibernateException e) {
			e.printStackTrace();
			return new ArrayList<GroupMember>();
		}
	}

	public static List<OrganizationFollower> getOrganizationFollowersByVolunteer(Volunteer volunteer) {
		String hql = "FROM OrganizationFollower AS orgf LEFT JOIN FETCH orgf.organization WHERE orgf.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volunteer.getVolunteerId());
		try {
			@SuppressWarnings("unchecked")
			List<OrganizationFollower> organizationFollowers = (List<OrganizationFollower>) DBConnection.query(hql, params);
			return (organizationFollowers != null ? organizationFollowers : new ArrayList<OrganizationFollower>());
		} catch (HibernateException e) {
			e.printStackTrace();
			return new ArrayList<OrganizationFollower>();
		}
	}

	public static List<Volunteer> getLeaderboardByPoints() throws HibernateException {
		String hql = "from Volunteer as v order by v.points desc";
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null, 25);
		return v;
	}

	public static List<Volunteer> getLeaderboardByHours() throws HibernateException {
		String hql = "from Volunteer as v order by v.hoursWorked desc";
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null, 25);
		return v;
	}

	public static List<Volunteer> getVolunteers() throws HibernateException {
		String hql = "from Volunteer as v";
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null);
		return v;
	}

	public static void addVolunteer(Volunteer v) throws HibernateException {
		DBConnection.persist(v);
		return;
	}

	public static void registerVolunteer(String username, String password, String email, String phoneNumber, String firstName,
			String lastName) throws Exception {
		password = PasswordHash.getHash(password, email);
		Volunteer v = new Volunteer(username, password, phoneNumber, email, firstName, lastName);
		DBConnection.persist(v);
		return;
	}

	public static void updateVolunteer(Volunteer v) throws HibernateException {
		DBConnection.update(v);
		return;
	}
}
