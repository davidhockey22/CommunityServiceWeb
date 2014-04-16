package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
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
	List<Event> groupEvents = null;

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@PostConstruct
	public void preRender() {
		System.out.println("Pre render");
		List<Event> events = null;
		if (volunteerEvents == null) {
			Volunteer volunteer = currentVolunteer.getVolunteer();
			events = EventService.getEventsByVolunteer(volunteer);
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

		Date now = new Date();
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

				for (Iterator<Organization> iterator = orgs.iterator(); iterator.hasNext() && organizationEvents.size() < 20;) {
					Organization organization = (Organization) iterator.next();
					List<Event> temp = EventService.getEventsByOrg(organization);
					for (Event e : temp) {
						if (e.getBeginTime().after(now)) {
							organizationEvents.add(e);
						}
					}
				}
			}
			// events coming up soon
			if (upcomingEvents == null) {
				upcomingEvents = new ArrayList<Event>();
				upcomingEvents.addAll(UpcomingEventsBean.getEvents());
			}
			// groups events
			if (groupEvents == null) {
				groupEvents = new ArrayList<Event>();
				currentVolunteer.attachGroups();
				Set<GroupMember> gm = currentVolunteer.getVolunteer().getGroupMembers();
				List<Group> groups = new ArrayList<Group>();
				for (Iterator<GroupMember> iterator = gm.iterator(); iterator.hasNext();) {
					GroupMember GroupMember = (GroupMember) iterator.next();
					groups.add(GroupMember.getGroup());
				}

				for (Iterator<Group> iterator = groups.iterator(); iterator.hasNext() && groupEvents.size() < 20;) {
					Group group = (Group) iterator.next();
					List<Event> temp = EventService.getEventsByGroup(group);
					for (Event e : temp) {
						if (e.getBeginTime().after(now)) {
							groupEvents.add(e);
						}
					}
				}
			}

			Set<Event> all = new HashSet<Event>();
			all.addAll(upcomingEvents);
			all.addAll(organizationEvents);
			all.addAll(groupEvents);
			Event[] evs = all.toArray(new Event[0]);
			// select random events from the mix of them and add them to feed
			feed = new ArrayList<Event>(Arrays.asList(evs));
			HashMap<Integer, Boolean> seen = new HashMap<Integer, Boolean>();
			for (Iterator iterator = feed.iterator(); iterator.hasNext();) {
				Event event = (Event) iterator.next();
				if (seen.get(event.getEventId()) != null || (events != null && events.contains(event))) {
					iterator.remove();
				} else {
					seen.put(event.getEventId(), true);
				}
			}
			Collections.shuffle(feed);
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
