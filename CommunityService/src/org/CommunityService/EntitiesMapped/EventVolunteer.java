package org.CommunityService.EntitiesMapped;

// Generated Feb 10, 2014 11:13:03 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * EventVolunteer generated by hbm2java
 */
@Entity
@Table(name = "Event_Volunteer", catalog = "dbAppData")
public class EventVolunteer implements java.io.Serializable {

	private EventVolunteerId id;
	private Event event;
	private Volunteer volunteer;
	private int attendedHours;
	private String approved;

	public EventVolunteer() {
	}

	public EventVolunteer(EventVolunteerId id, Event event, Volunteer volunteer, int attendedHours, String approved) {
		this.id = id;
		this.event = event;
		this.volunteer = volunteer;
		this.attendedHours = attendedHours;
		this.approved = approved;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "eventId", column = @Column(name = "EventID", nullable = false)),
			@AttributeOverride(name = "volunteerD", column = @Column(name = "VolunteerD", nullable = false)) })
	public EventVolunteerId getId() {
		return this.id;
	}

	public void setId(EventVolunteerId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EventID", nullable = false, insertable = false, updatable = false)
	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VolunteerD", nullable = false, insertable = false, updatable = false)
	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	@Column(name = "AttendedHours", nullable = false)
	public int getAttendedHours() {
		return this.attendedHours;
	}

	public void setAttendedHours(int attendedHours) {
		this.attendedHours = attendedHours;
	}

	@Column(name = "Approved", nullable = false, length = 6)
	public String getApproved() {
		return this.approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

}
