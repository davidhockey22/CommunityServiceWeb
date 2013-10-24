package org.CommunityService.Entities.DEPRECATED;

// Generated Oct 12, 2013 4:02:28 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Event generated by hbm2java
 */
public class Event implements java.io.Serializable {

	private Integer eventId;
	private OccurrencePattern occurrencePattern;
	private String eventName;
	private String description;
	private String location;
	private Date beginTime;
	private Date endTime;
	private float hoursBonus;
	private Set pictureEvents = new HashSet(0);
	private Set eventInterestses = new HashSet(0);
	private Set eventSkills = new HashSet(0);
	private Set eventVolunteers = new HashSet(0);

	public Event() {
	}

	public Event(String eventName, Date beginTime, Date endTime,
			float hoursBonus) {
		this.eventName = eventName;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.hoursBonus = hoursBonus;
	}

	public Event(OccurrencePattern occurrencePattern, String eventName,
			String description, String location, Date beginTime, Date endTime,
			float hoursBonus, Set pictureEvents, Set eventInterestses,
			Set eventSkills, Set eventVolunteers) {
		this.occurrencePattern = occurrencePattern;
		this.eventName = eventName;
		this.description = description;
		this.location = location;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.hoursBonus = hoursBonus;
		this.pictureEvents = pictureEvents;
		this.eventInterestses = eventInterestses;
		this.eventSkills = eventSkills;
		this.eventVolunteers = eventVolunteers;
	}

	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public OccurrencePattern getOccurrencePattern() {
		return this.occurrencePattern;
	}

	public void setOccurrencePattern(OccurrencePattern occurrencePattern) {
		this.occurrencePattern = occurrencePattern;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public float getHoursBonus() {
		return this.hoursBonus;
	}

	public void setHoursBonus(float hoursBonus) {
		this.hoursBonus = hoursBonus;
	}

	public Set getPictureEvents() {
		return this.pictureEvents;
	}

	public void setPictureEvents(Set pictureEvents) {
		this.pictureEvents = pictureEvents;
	}

	public Set getEventInterestses() {
		return this.eventInterestses;
	}

	public void setEventInterestses(Set eventInterestses) {
		this.eventInterestses = eventInterestses;
	}

	public Set getEventSkills() {
		return this.eventSkills;
	}

	public void setEventSkills(Set eventSkills) {
		this.eventSkills = eventSkills;
	}

	public Set getEventVolunteers() {
		return this.eventVolunteers;
	}

	public void setEventVolunteers(Set eventVolunteers) {
		this.eventVolunteers = eventVolunteers;
	}

}