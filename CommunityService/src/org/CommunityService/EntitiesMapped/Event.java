package org.CommunityService.EntitiesMapped;

// Generated Feb 22, 2014 8:45:42 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Event generated by hbm2java
 */
@Entity
@Table(name = "Event", catalog = "volunteerMeData")
public class Event implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -869530011310269144L;
	private Integer eventId;
	private Organization organization;

	private OccurrencePattern occurrencePattern;
	private Volunteer volunteer;

	private String eventName;
	private String description;
	private String location;
	private Date beginTime;
	private Date endTime;
	private Float hoursBonus;
	private Set<EventVolunteer> eventVolunteers = new HashSet<EventVolunteer>(0);
	private Set<Picture> pictures = new HashSet<Picture>(0);
	private Set<Interest> interests = new HashSet<Interest>(0);
	private Set<Group> groups = new HashSet<Group>(0);
	private Set<EventSkill> eventSkills = new HashSet<EventSkill>(0);

	public Event() {
	}

	public Event(String eventName, Date beginTime, Date endTime) {
		this.eventName = eventName;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public Event(OccurrencePattern occurrencePattern, String eventName, String description, String location, Date beginTime, Date endTime, Float hoursBonus,
			Set<EventVolunteer> eventVolunteers, Set<Picture> pictures, Set<Interest> interests, Set<EventSkill> eventSkills) {
		this.occurrencePattern = occurrencePattern;
		this.eventName = eventName;
		this.description = description;
		this.location = location;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.hoursBonus = hoursBonus;
		this.eventVolunteers = eventVolunteers;
		this.pictures = pictures;
		this.interests = interests;
		this.eventSkills = eventSkills;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EventID", unique = true, nullable = false)
	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	@Column(name = "EventName", nullable = false, length = 45)
	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Column(name = "Description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Location", length = 45)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EventHost")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "RecurID")
	public OccurrencePattern getOccurrencePattern() {
		return this.occurrencePattern;
	}

	public void setOccurrencePattern(OccurrencePattern occurrencePattern) {
		this.occurrencePattern = occurrencePattern;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EventAdmin")
	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BeginTime", nullable = false, length = 19)
	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EndTime", nullable = false, length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "HoursBonus", precision = 12, scale = 0, insertable=false)
	public Float getHoursBonus() {
		return this.hoursBonus;
	}

	public void setHoursBonus(Float hoursBonus) {
		this.hoursBonus = hoursBonus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<EventVolunteer> getEventVolunteers() {
		return this.eventVolunteers;
	}

	public void setEventVolunteers(Set<EventVolunteer> eventVolunteers) {
		this.eventVolunteers = eventVolunteers;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "events")
	public Set<Picture> getPictures() {
		return this.pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Event_Interest", catalog = "volunteerMeData", joinColumns = { @JoinColumn(name = "EventID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "InterestID", nullable = false, updatable = false) })
	public Set<Interest> getInterests() {
		return this.interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<EventSkill> getEventSkills() {
		return this.eventSkills;
	}

	public void setEventSkills(Set<EventSkill> eventSkills) {
		this.eventSkills = eventSkills;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Event_Group", catalog = "volunteerMeData", joinColumns = { @JoinColumn(name = "EventId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "GroupId", nullable = false, updatable = false) })
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

}
