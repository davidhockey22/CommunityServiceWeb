package org.CommunityService.ManagedBeans;

import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.Services.EventService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@RequestScoped
@Join(path = "/event/{eventId}", to = "/Web/ViewEvent.xhtml")
public class ViewEventBean {
	private Event event;
	private String eventId;

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	public String signUp() {
		System.out.println("Signing up!");
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

	public boolean isSignedUp() {
		if (currentVolunteer.getVolunteer() != null) {
			List<Event> userEvents = (List<Event>) EventService.getEventsByVolunteer(currentVolunteer.getVolunteer().getVolunteerId());
			for (Iterator<Event> iterator = userEvents.iterator(); iterator.hasNext();) {
				Event event = (Event) iterator.next();
				if (event.getEventId() == Integer.parseInt(this.eventId)) {
					return true;
				}
			}
		}
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

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
