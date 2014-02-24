package org.CommunityService.Services;

import java.util.ArrayList;
import java.util.List;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.hibernate.HibernateException;

public class EventService {

	public static List<Event> getEvents() throws HibernateException {
		String hql = "from Event";
		@SuppressWarnings("unchecked")
		List<Event> events = (List<Event>) DBConnection.query(hql, null);
		return events;
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

	public static boolean signUp(Volunteer v, Event e) {
		//if (v != null) {
			EventVolunteer ev = new EventVolunteer(e, v);
			DBConnection.persist(ev);
			//return true;
		//}
		return false;
	}

	public static void addEvent(Event event) throws HibernateException {
		DBConnection.persist(event);
		return;
	}
}
