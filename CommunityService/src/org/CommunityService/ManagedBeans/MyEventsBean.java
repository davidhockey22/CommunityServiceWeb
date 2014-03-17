package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;

@Join(path = "/events", to = "/Web/MyEvents.xhtml")
@ManagedBean
@RequestScoped
public class MyEventsBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	List<EventVolunteer> pendingEvents;
	List<EventVolunteer> acceptedEvents;
	List<EventVolunteer> pastEvents;

	@PostConstruct
	public void postConstructor() {
		pendingEvents = new ArrayList<EventVolunteer>();
		acceptedEvents = new ArrayList<EventVolunteer>();
		pastEvents = new ArrayList<EventVolunteer>();

		Volunteer volunteer = currentVolunteer.getVolunteer();
		List<EventVolunteer> eventVolunteers = VolunteerService.getEventVolunteersByVolunteer(volunteer);

		Date now = new Date();
		for (Iterator<EventVolunteer> iterator = eventVolunteers.iterator(); iterator.hasNext();) {
			EventVolunteer eventVolunteer = (EventVolunteer) iterator.next();
			if (eventVolunteer.getEvent().getEndTime().before(now)) {
				pastEvents.add(eventVolunteer);
			} else if (eventVolunteer.getApproved()) {
				acceptedEvents.add(eventVolunteer);
			} else {
				pendingEvents.add(eventVolunteer);
			}
		}
	}

	public List<EventVolunteer> getPendingEvents() {
		return pendingEvents;
	}

	public List<EventVolunteer> getAcceptedEvents() {
		return acceptedEvents;
	}

	public List<EventVolunteer> getPastEvents() {
		return pastEvents;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
