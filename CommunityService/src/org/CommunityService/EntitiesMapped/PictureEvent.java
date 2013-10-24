package org.CommunityService.EntitiesMapped;

// Generated Oct 21, 2013 2:07:36 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PictureEvent generated by hbm2java
 */
@Entity
@Table(name = "PictureEvent", catalog = "dbAppData")
public class PictureEvent implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1097262363022303888L;
    private int pictureEventId;
    private Event event;
    private Picture picture;

    public PictureEvent() {
    }

    public PictureEvent(int pictureEventId) {
	this.pictureEventId = pictureEventId;
    }

    public PictureEvent(int pictureEventId, Event event, Picture picture) {
	this.pictureEventId = pictureEventId;
	this.event = event;
	this.picture = picture;
    }

    @Id
    @Column(name = "PictureEventID", unique = true, nullable = false)
    public int getPictureEventId() {
	return this.pictureEventId;
    }

    public void setPictureEventId(int pictureEventId) {
	this.pictureEventId = pictureEventId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventID")
    public Event getEvent() {
	return this.event;
    }

    public void setEvent(Event event) {
	this.event = event;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PictureID")
    public Picture getPicture() {
	return this.picture;
    }

    public void setPicture(Picture picture) {
	this.picture = picture;
    }

}