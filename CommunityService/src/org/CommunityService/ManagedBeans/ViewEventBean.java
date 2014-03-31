package org.CommunityService.ManagedBeans;

import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.Services.EventService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean(name = "viewEventBean")
@ViewScoped
@Join(path = "/event/{eventId}", to = "/Web/ViewEvent.xhtml")
public class ViewEventBean {
	private Event event;
	@ManagedProperty(value="#{eventId}")
	private String eventId;
	private boolean signedUp = false;
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	public String signUp() {
		System.out.println("Signing up!");
		EventService.signUp(currentVolunteer.getVolunteer(), event);
		return "?faces-redirect=true";
	}

	public String removeEventFromVolunteer() {
		EventService.removeEventVolunteer(Integer.parseInt(eventId), currentVolunteer.getVolunteer().getVolunteerId());
		return "?faces-redirect=true";
	}

	public void fetchEvent() {
		try {
			if (event == null || event.getEventId() != Integer.parseInt(eventId)) {
				this.event = EventService.getEventById(Integer.parseInt(eventId));
				System.out.println("Sign up!");
				this.signedUp = this.signedUp();
			}
		} catch (NumberFormatException e) {
			this.event = null;
		}
	}

	public boolean signedUp() {
		if (currentVolunteer.getVolunteer() != null) {
			List<Event> userEvents = (List<Event>) EventService.getEventsByVolunteer(currentVolunteer.getVolunteer().getVolunteerId());
			for (Iterator iterator = userEvents.iterator(); iterator.hasNext();) {
				Event event = (Event) iterator.next();
				if (event.getEventId() == Integer.parseInt(this.eventId)) {
					System.out.println("ViewEventBean.signedUp()");
					return true;
				}
			}
		}
		return false;
	}

	public boolean isSignedUp() {

		return signedUp;
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
