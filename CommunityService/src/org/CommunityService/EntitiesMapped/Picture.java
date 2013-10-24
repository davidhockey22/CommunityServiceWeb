package org.CommunityService.EntitiesMapped;

// Generated Oct 21, 2013 2:07:36 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Picture generated by hbm2java
 */
@Entity
@Table(name = "Picture", catalog = "dbAppData", uniqueConstraints = @UniqueConstraint(columnNames = "PictureLink"))
public class Picture implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1986972143498680800L;
    private Integer pictureId;
    private String pictureLink;
    private Date uploadedOn;
    private String albumName;
    private String description;
    private Set<PictureEvent> pictureEvents = new HashSet<PictureEvent>(0);
    private Set<PictureGroup> pictureGroups = new HashSet<PictureGroup>(0);
    private Set<PictureVolunteer> pictureVolunteers = new HashSet<PictureVolunteer>(0);
    private Set<PictureOrg> pictureOrgs = new HashSet<PictureOrg>(0);

    public Picture() {
    }

    public Picture(String pictureLink, Date uploadedOn, String albumName) {
	this.pictureLink = pictureLink;
	this.uploadedOn = uploadedOn;
	this.albumName = albumName;
    }

    public Picture(String pictureLink, Date uploadedOn, String albumName, String description, Set<PictureEvent> pictureEvents, Set<PictureGroup> pictureGroups,
	    Set<PictureVolunteer> pictureVolunteers, Set<PictureOrg> pictureOrgs) {
	this.pictureLink = pictureLink;
	this.uploadedOn = uploadedOn;
	this.albumName = albumName;
	this.description = description;
	this.pictureEvents = pictureEvents;
	this.pictureGroups = pictureGroups;
	this.pictureVolunteers = pictureVolunteers;
	this.pictureOrgs = pictureOrgs;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PictureID", unique = true, nullable = false)
    public Integer getPictureId() {
	return this.pictureId;
    }

    public void setPictureId(Integer pictureId) {
	this.pictureId = pictureId;
    }

    @Column(name = "PictureLink", unique = true, nullable = false, length = 45)
    public String getPictureLink() {
	return this.pictureLink;
    }

    public void setPictureLink(String pictureLink) {
	this.pictureLink = pictureLink;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UploadedOn", nullable = false, length = 19)
    public Date getUploadedOn() {
	return this.uploadedOn;
    }

    public void setUploadedOn(Date uploadedOn) {
	this.uploadedOn = uploadedOn;
    }

    @Column(name = "AlbumName", nullable = false, length = 45)
    public String getAlbumName() {
	return this.albumName;
    }

    public void setAlbumName(String albumName) {
	this.albumName = albumName;
    }

    @Column(name = "Description", length = 45)
    public String getDescription() {
	return this.description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    public Set<PictureEvent> getPictureEvents() {
	return this.pictureEvents;
    }

    public void setPictureEvents(Set<PictureEvent> pictureEvents) {
	this.pictureEvents = pictureEvents;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    public Set<PictureGroup> getPictureGroups() {
	return this.pictureGroups;
    }

    public void setPictureGroups(Set<PictureGroup> pictureGroups) {
	this.pictureGroups = pictureGroups;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    public Set<PictureVolunteer> getPictureVolunteers() {
	return this.pictureVolunteers;
    }

    public void setPictureVolunteers(Set<PictureVolunteer> pictureVolunteers) {
	this.pictureVolunteers = pictureVolunteers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    public Set<PictureOrg> getPictureOrgs() {
	return this.pictureOrgs;
    }

    public void setPictureOrgs(Set<PictureOrg> pictureOrgs) {
	this.pictureOrgs = pictureOrgs;
    }

}