package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.EventService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@Join(path = "/", to = "/Web/Home.xhtml")
public class FeedBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	List<Event> volunteerEvents;
	List<Event> feed;

	public List<Event> getVolunteerEvents() {
		Volunteer volunteer = currentVolunteer.getVolunteer();
		List<Event> events = EventService.getEventsByVolunteer(volunteer);
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		Date tomorrow = (Date) today.clone();
		tomorrow.setDate(tomorrow.getDate() + 1);

		for (Iterator iterator = events.iterator(); iterator.hasNext();) {
			Event event = (Event) iterator.next();
			if (event.getBeginTime().after(today) && event.getEndTime().before(tomorrow)) {

			} else {
				iterator.remove();
			}
		}
		return events;
	}

	public void setVolunteerEvents(List<Event> volunteerEvents) {
		this.volunteerEvents = volunteerEvents;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public List<Event> getFeed() {
		if (feed == null) {
			currentVolunteer.attachOrganizations();
			Set<OrganizationFollower> of = currentVolunteer.getVolunteer().getOrganizationFollowers();
			List<Organization> orgs = new ArrayList();
			for (Iterator iterator = of.iterator(); iterator.hasNext();) {
				OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
				orgs.add(organizationFollower.getOrganization());
			}
			EventService.get
		}
		return feed;
	}

	public void setFeed(List<Event> feed) {
		this.feed = feed;
	}

}
