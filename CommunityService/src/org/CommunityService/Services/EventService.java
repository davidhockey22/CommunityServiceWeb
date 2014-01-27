package org.CommunityService.Services;

import java.util.List;

import org.CommunityService.EntitiesMapped.Event;

public class EventService {

	@SuppressWarnings("unchecked")
	public static List<Event> getEvents() throws Exception {
		String hql = "from Event";
		List<Event> events = (List<Event>) DBConnection.query(hql, null);
		return events;
	}

	public static void addEvent(Event event) throws Exception {
		DBConnection.persist(event);
		return;
	}
	
	
	
	

}
