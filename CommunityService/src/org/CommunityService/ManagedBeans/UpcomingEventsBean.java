package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.Services.EventService;

public class UpcomingEventsBean {

	static List<Event> events = new ArrayList();
	static Date lastChecked;

	@SuppressWarnings("deprecation")
	public synchronized static List<Event> getEvents() {

		Date currentTime = new Date();
		Date later = new Date();
		later.setHours(later.getHours() + 24);
		if (lastChecked == null || lastChecked.before(currentTime)) {
			// don't check for a minute
			lastChecked = (Date) currentTime.clone();
			lastChecked.setMinutes(lastChecked.getMinutes() + 1);
			// Get events coming up
			events = EventService.getEvents(currentTime, later);
			System.out.println("New check running.");
		}
		// events.size();
		return events;

	}

	public void setEvents(List<Event> events) {
		UpcomingEventsBean.events = events;
	}

}
