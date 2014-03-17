package org.CommunityService.ManagedBeans;

import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.Services.EventService;

@ManagedBean
@SessionScoped
public class EventPage {
	private Event event;
	private String eventId;

	@ManagedProperty(value = "#{currentVolunteerBean}")
	private CurrentVolunteerBean currentVolunteer;

	public String signUp() {
		EventService.signUp(currentVolunteer.getVolunteer(), event);
		return "?faces-redirect=true";
	}

	
	
	
	public void fetchEvent() {
		try {
			this.event = EventService.getEventById(Integer.parseInt(eventId));
		} catch (NumberFormatException e) {
			this.event = null;
		}
	}

	public boolean isSignedUp(){
		//Set<Event> userEvents = EventService.
		return false;
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

	public CurrentVolunteerBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(CurrentVolunteerBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
