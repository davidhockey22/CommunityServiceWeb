package org.CommunityService.EntitiesMapped;

// Generated Oct 21, 2013 2:07:36 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Interest generated by hbm2java
 */
@Entity
@Table(name = "Interest", catalog = "dbAppData")
public class Interest implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4865286571098761986L;
    private Integer interestId;
    private String name;
    private String description;
    private Set<VolunteerInterest> volunteerInterests = new HashSet<VolunteerInterest>(0);
    private Set<EventInterests> eventInterestses = new HashSet<EventInterests>(0);

    public Interest() {
    }

    public Interest(String name, String description) {
	this.name = name;
	this.description = description;
    }

    public Interest(String name, String description, Set<VolunteerInterest> volunteerInterests, Set<EventInterests> eventInterestses) {
	this.name = name;
	this.description = description;
	this.volunteerInterests = volunteerInterests;
	this.eventInterestses = eventInterestses;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "InterestID", unique = true, nullable = false)
    public Integer getInterestId() {
	return this.interestId;
    }

    public void setInterestId(Integer interestId) {
	this.interestId = interestId;
    }

    @Column(name = "Name", nullable = false, length = 45)
    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Column(name = "Description", nullable = false, length = 16777215)
    public String getDescription() {
	return this.description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interest")
    public Set<VolunteerInterest> getVolunteerInterests() {
	return this.volunteerInterests;
    }

    public void setVolunteerInterests(Set<VolunteerInterest> volunteerInterests) {
	this.volunteerInterests = volunteerInterests;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interest")
    public Set<EventInterests> getEventInterestses() {
	return this.eventInterestses;
    }

    public void setEventInterestses(Set<EventInterests> eventInterestses) {
	this.eventInterestses = eventInterestses;
    }

}