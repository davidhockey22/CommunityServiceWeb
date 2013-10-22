package org.CommunityService.EntitiesMapped;

// Generated Oct 21, 2013 2:07:36 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * EventInterests generated by hbm2java
 */
@Entity
@Table(name = "EventInterests", catalog = "dbAppData")
public class EventInterests implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6349278541288343526L;
    private Integer eventInterestsId;
    private Event event;
    private Interest interest;

    public EventInterests() {
    }

    public EventInterests(Event event, Interest interest) {
	this.event = event;
	this.interest = interest;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "EventInterestsID", unique = true, nullable = false)
    public Integer getEventInterestsId() {
	return this.eventInterestsId;
    }

    public void setEventInterestsId(Integer eventInterestsId) {
	this.eventInterestsId = eventInterestsId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventID", nullable = false)
    public Event getEvent() {
	return this.event;
    }

    public void setEvent(Event event) {
	this.event = event;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InterestID", nullable = false)
    public Interest getInterest() {
	return this.interest;
    }

    public void setInterest(Interest interest) {
	this.interest = interest;
    }

}
