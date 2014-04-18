package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.hibernate.HibernateException;

public class EventService {

	public static List<Event> getEvents() throws HibernateException {
		String hql = "from Event";
		@SuppressWarnings("unchecked")
		List<Event> events = (List<Event>) DBConnection.query(hql, null);
		return events;
	}

	@SuppressWarnings("unchecked")
	public static List<Event> getEvents(Date lowBound, Date upperBound) throws HibernateException {
		String hql = "from Event as e where e.beginTime between ? and ? order by e.beginTime";
		List<Date> params = new ArrayList<Date>();
		params.add(lowBound);
		params.add(upperBound);
		List<Event> events = (List<Event>) DBConnection.queryLimit(hql, params, 10);
		return events;
	}

	public static void removeEventVolunteer(Integer eventId, Integer vId) {
		List<Integer> params = new ArrayList<Integer>();
		params.add(vId);
		params.add(eventId);
		DBConnection.queryDelete("delete EventVolunteer v where v.volunteer.volunteerId=? and v.event.eventId=?", params);
		System.out.println("EventService.removeEventVolunteer()");

	}

	public static List<Event> getEventsByOrg(Organization o) {
		String hql = "from Event as e where e.organization.orgId=? order by e.beginTime";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(o.getOrgId());
		try {
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) DBConnection.query(hql, params);
			System.out.println(events.size());
			return events;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Event> getEventsByGroup(Group g) {
		String hql = "Select g.events from Group as g where g.groupId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(g.getGroupId());
		try {
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) DBConnection.query(hql, params);
			System.out.println(events.size());
			return events;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Event getEventById(int eventId) throws HibernateException {

		String hql = "from Event as e where e.eventId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(eventId);
		try {
			@SuppressWarnings("unchecked")
			Event e = (Event) (((List<Event>) DBConnection.query(hql, params)).get(0));
			return e;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static Event getEventByIdForEdit(int eventId, int volunteerId) throws HibernateException {

		String hql = "from Event as e left join fetch e.volunteer left join fetch e.organization as o (from Volunteer as v where v.where e.eventId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(eventId);
		try {
			@SuppressWarnings("unchecked")
			Event e = (Event) (((List<Event>) DBConnection.query(hql, params)).get(0));
			return e;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static Event getEventByIdFetch(int eventId) throws HibernateException {

		String hql = "from Event as e left join fetch e.groups left join fetch e.organization left join fetch e.interests left join fetch e.eventSkills as es left join fetch es.skill where e.eventId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(eventId);
		try {
			@SuppressWarnings("unchecked")
			Event e = (Event) (((List<Event>) DBConnection.query(hql, params)).get(0));
			return e;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static List<Event> getEventsByVolunteer(Integer volunteerId) {
		String hql = "SELECT ev.event FROM EventVolunteer as ev where ev.volunteer.volunteerId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(volunteerId);
		try {
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) DBConnection.query(hql, params);
			return (events != null ? events : new ArrayList<Event>());
		} catch (HibernateException e) {
			e.printStackTrace();
			return new ArrayList<Event>();
		}
	}

	public static List<Event> getEventsByVolunteer(Volunteer volunteer) {
		return getEventsByVolunteer(volunteer.getVolunteerId());
	}

	public static List<Event> getEventsByDate() {

		Date date = new Date();

		String hql = "FROM Event as ev where ev.beginTime > ? order by ev.beginTime asc";
		ArrayList<Date> params = new ArrayList<Date>();
		params.add(date);
		try {
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) DBConnection.query(hql, params);
			return events;
		} catch (HibernateException e) {
			return null;
		}
	}

	public static List<Event> getEventsByInterest(Integer interestId) {

		String hql = "select i.events from Interest as i where i.interestId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(interestId);
		try {
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) DBConnection.query(hql, params);
			return events;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Event> getEventsBySkill(Integer skillID) {

		String hql = "SELECT ev.event FROM EventSkill as ev where ev.skill.skillId=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(skillID);
		try {
			@SuppressWarnings("unchecked")
			List<Event> events = (List<Event>) DBConnection.query(hql, params);
			return events;
		} catch (HibernateException e) {
			return null;
		}
	}

	public static EventVolunteer getEventVolunteerById(int eventId, int volunteerId) throws HibernateException {

		String hql = "from EventVolunteer as e where EventID=? and VolunteerID=?";
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(eventId);
		params.add(volunteerId);
		try {
			@SuppressWarnings("unchecked")
			EventVolunteer e = (EventVolunteer) (((List<EventVolunteer>) DBConnection.query(hql, params)).get(0));
			return e;
		} catch (HibernateException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static void signUp(Volunteer v, Event e) {
		// if (v != null) {
		EventVolunteer ev = new EventVolunteer(e, v, 0, false);
		DBConnection.update(ev);
	}

	public static void update(Event ev) throws HibernateException {

		DBConnection.update(ev);
	}

	public static void addEvent(Event event) throws HibernateException {
		DBConnection.save(event);
	}
}
