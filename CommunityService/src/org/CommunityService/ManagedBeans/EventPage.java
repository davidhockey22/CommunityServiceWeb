package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.Services.EventService;

@ManagedBean
@RequestScoped
public class EventPage {
	private Event event;
	private String eventId;
	
	public void fetchEvent() {
		try {
			this.event = EventService.getEventById(Integer.parseInt(eventId));
		} catch (NumberFormatException e) {
			this.event = null;
		}
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Event getEvent() {
		return event;
	}
}
