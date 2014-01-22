package org.CommunityService.ManagedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;

@ManagedBean
@RequestScoped
public class FeedPage {
	@ManagedProperty(value = "#{currentVolunteerBean}")
	private CurrentVolunteerBean currentVolunteer;
	
	List<Event> volunteerEvents;
	
	public List<Event> getVolunteerEvents() {
		Volunteer volunteer = currentVolunteer.getVolunteer();
		List<Event> events = VolunteerService.getEventsByVolunteer(volunteer);
		return events;
	}

	public void setVolunteerEvents(List<Event> volunteerEvents) {
		this.volunteerEvents = volunteerEvents;
	}

	public CurrentVolunteerBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(CurrentVolunteerBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}

