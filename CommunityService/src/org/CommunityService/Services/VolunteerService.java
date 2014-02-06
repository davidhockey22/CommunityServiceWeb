package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.List;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.hibernate.HibernateException;

public class VolunteerService {

	public static Volunteer getVolunteerByName(String name) {
		String hql = "from Volunteer as v where v.volunteerName=?";
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

	public static List<Event> getEventsByVolunteer(Volunteer volunteer) {
		String hql = "SELECT ev.event FROM EventVolunteer as ev where ev.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volunteer.getVolunteerId());
		try {
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) DBConnection.query(hql, params);
			return events;
		} catch (HibernateException e) {
			return null;
		}
	}

	public static List<Volunteer> getVolunteers() throws HibernateException {
		String hql = "from Volunteer as v";
		@SuppressWarnings("unchecked")
		List<Volunteer> v = (List<Volunteer>) DBConnection.query(hql, null);
		return v;
	}

	public static void addVolunteer(Volunteer v) throws Exception {
		DBConnection.persist(v);
		return;
	}

	public static void updateVolunteer(Volunteer v) throws Exception {
		DBConnection.update(v);
		return;
	}
}
