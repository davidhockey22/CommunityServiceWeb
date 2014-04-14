package org.CommunityService.util;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventVolunteer;

public class CalculatedEvent {
	private Integer approvedVolunteers = new Integer(0);
	private Integer unapprovedVolunteers = new Integer(0);
	private Event event;

	public CalculatedEvent(Event event) {
		this.event = event;
		if (event != null) {
			for (EventVolunteer eventVolunteer : event.getEventVolunteers()) {
				if (eventVolunteer.getApproved()) {
					approvedVolunteers++;
				} else {
					unapprovedVolunteers++;
				}
			}
		}
	}

	public Integer getApprovedVolunteers() {
		return approvedVolunteers;
	}

	public void setApprovedVolunteers(Integer approvedVolunteers) {
		this.approvedVolunteers = approvedVolunteers;
	}

	public Integer getUnapprovedVolunteers() {
		return unapprovedVolunteers;
	}

	public void setUnapprovedVolunteers(Integer unapprovedVolunteers) {
		this.unapprovedVolunteers = unapprovedVolunteers;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
}
