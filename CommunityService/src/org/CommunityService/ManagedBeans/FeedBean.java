package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.EventService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@Join(path = "/", to = "/Web/Home.xhtml")
@ViewScoped
public class FeedBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	List<Event> volunteerEvents = null;
	List<Event> feed = null;
	List<Event> organizationEvents = null;
	List<Event> upcomingEvents = null;

	@PostConstruct
	public void preRender() {
		System.out.println("Pre render");
		if (feed == null) {
			// followed orgs events
			if (organizationEvents == null) {
				organizationEvents = new ArrayList<Event>();
				currentVolunteer.attachOrganizations();
				Set<OrganizationFollower> of = currentVolunteer.getVolunteer().getOrganizationFollowers();
				List<Organization> orgs = new ArrayList<Organization>();
				for (Iterator<OrganizationFollower> iterator = of.iterator(); iterator.hasNext();) {
					OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
					orgs.add(organizationFollower.getOrganization());
				}

				for (Iterator<Organization> iterator = orgs.iterator(); iterator.hasNext();) {
					Organization organization = (Organization) iterator.next();
					organizationEvents.addAll(EventService.getEventsByOrg(organization));
				}
			}
			// events coming up soon
			if (upcomingEvents == null) {
				upcomingEvents = new ArrayList<Event>();
				upcomingEvents.addAll(UpcomingEventsBean.getEvents());
			}
			// groups events

			Set<Event> all = new HashSet<Event>();
			all.addAll(upcomingEvents);
			all.addAll(organizationEvents);
			Event[] evs = all.toArray(new Event[0]);
			Set<Event> feedSet = new HashSet<Event>();
			for (int i = 0; feedSet.size() < 5 && feedSet.size() < (all.size()); i++) {
				int index = (int) (Math.random() * all.size());

				feedSet.add(evs[index]);
			}
			// select random events from the mix of them and add them to feed
			feed = new ArrayList<Event>(feedSet);

		}
		if (volunteerEvents == null) {
			Volunteer volunteer = currentVolunteer.getVolunteer();
			List<Event> events = EventService.getEventsByVolunteer(volunteer);
			Date today = new Date();
			today.setHours(0);
			today.setMinutes(0);
			today.setSeconds(0);
			Date tomorrow = (Date) today.clone();
			tomorrow.setDate(tomorrow.getDate() + 1);

			for (Iterator<Event> iterator = events.iterator(); iterator.hasNext();) {
				Event event = (Event) iterator.next();
				if (event.getBeginTime().after(today) && event.getEndTime().before(tomorrow)) {

				} else {
					iterator.remove();
				}
			}
		}
	}

	public List<Event> getVolunteerEvents() {
		return volunteerEvents;
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
		return feed;
	}

	public void setFeed(List<Event> feed) {
		this.feed = feed;
	}

	public List<Event> getOrganizationEvents() {
		return organizationEvents;
	}

	public void setOrganizationEvents(List<Event> organizationEvents) {
		this.organizationEvents = organizationEvents;
	}

	public List<Event> getUpcomingEvents() {
		return upcomingEvents;
	}

	public void setUpcomingEvents(List<Event> upcomingEvents) {
		this.upcomingEvents = upcomingEvents;
	}

}
