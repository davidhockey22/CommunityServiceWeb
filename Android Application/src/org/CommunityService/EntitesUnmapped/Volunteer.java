package org.CommunityService.EntitesUnmapped;

// Generated Feb 23, 2014 5:44:45 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Volunteer generated by hbm2java
 */
public class Volunteer implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3598548236823381966L;
	private Integer volunteerId;
	private String volunteerName;
	private String volunteerPassword;
	private String phoneNumber;
	private String emailAddress;
	private Date lastLoginDate;
	private Float points;
	private Integer hoursWorked;
	private Float avgRating;
	private Date creationDate;
	private Date activationDate;
	private String firstName;
	private String lastName;
	private String description;
	private Set<GroupMember> groupMembers = new HashSet<GroupMember>(0);
	private Set<FacebookLogIn> facebookLogIns = new HashSet<FacebookLogIn>(0);
	private Set<EventVolunteer> eventVolunteers = new HashSet<EventVolunteer>(0);
	private Set<Picture> pictures = new HashSet<Picture>(0);
	private Set<VolunteerInterest> volunteerInterests = new HashSet<VolunteerInterest>(0);
	private Set<OrganizationFollower> organizationFollowers = new HashSet<OrganizationFollower>(0);
	private Set<VolunteerDevice> volunteerDevices = new HashSet<VolunteerDevice>(0);
	private Set<GoogleLogIn> googleLogIns = new HashSet<GoogleLogIn>(0);
	private Set<VolunteerSkill> volunteerSkills = new HashSet<VolunteerSkill>(0);
	private Set<SocialLinks> socialLinkses = new HashSet<SocialLinks>(0);
	private Set<Notification> notifications = new HashSet<Notification>(0);

	public Volunteer() {
	}

	public Volunteer(String volunteerName, String volunteerPassword, String phoneNumber, String emailAddress, String firstName, String lastName) {
		this.volunteerName = volunteerName;
		this.volunteerPassword = volunteerPassword;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Volunteer(String volunteerName, String volunteerPassword, String phoneNumber, String emailAddress, Date lastLoginDate, Float points,
			Integer hoursWorked, Float avgRating, Date creationDate, Date activationDate, String firstName, String lastName, String description,
			Set<GroupMember> groupMembers, Set<FacebookLogIn> facebookLogIns, Set<EventVolunteer> eventVolunteers, Set<Picture> pictures,
			Set<VolunteerInterest> volunteerInterests, Set<OrganizationFollower> organizationFollowers, Set<VolunteerDevice> volunteerDevices,
			Set<GoogleLogIn> googleLogIns, Set<VolunteerSkill> volunteerSkills, Set<SocialLinks> socialLinkses, Set<Notification> notifications) {
		this.volunteerName = volunteerName;
		this.volunteerPassword = volunteerPassword;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.lastLoginDate = lastLoginDate;
		this.points = points;
		this.hoursWorked = hoursWorked;
		this.avgRating = avgRating;
		this.creationDate = creationDate;
		this.activationDate = activationDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.groupMembers = groupMembers;
		this.facebookLogIns = facebookLogIns;
		this.eventVolunteers = eventVolunteers;
		this.pictures = pictures;
		this.volunteerInterests = volunteerInterests;
		this.organizationFollowers = organizationFollowers;
		this.volunteerDevices = volunteerDevices;
		this.googleLogIns = googleLogIns;
		this.volunteerSkills = volunteerSkills;
		this.socialLinkses = socialLinkses;
		this.notifications = notifications;
	}

	public Integer getVolunteerId() {
		return this.volunteerId;
	}

	public void setVolunteerId(Integer volunteerId) {
		this.volunteerId = volunteerId;
	}

	public String getVolunteerName() {
		return this.volunteerName;
	}

	public void setVolunteerName(String volunteerName) {
		this.volunteerName = volunteerName;
	}

	public String getVolunteerPassword() {
		return this.volunteerPassword;
	}

	public void setVolunteerPassword(String volunteerPassword) {
		this.volunteerPassword = volunteerPassword;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Float getPoints() {
		return this.points;
	}

	public void setPoints(Float points) {
		this.points = points;
	}

	public Integer getHoursWorked() {
		return this.hoursWorked;
	}

	public void setHoursWorked(Integer hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public Float getAvgRating() {
		return this.avgRating;
	}

	public void setAvgRating(Float avgRating) {
		this.avgRating = avgRating;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getActivationDate() {
		return this.activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<GroupMember> getGroupMembers() {
		return this.groupMembers;
	}

	public void setGroupMembers(Set<GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public Set<FacebookLogIn> getFacebookLogIns() {
		return this.facebookLogIns;
	}

	public void setFacebookLogIns(Set<FacebookLogIn> facebookLogIns) {
		this.facebookLogIns = facebookLogIns;
	}

	public Set<EventVolunteer> getEventVolunteers() {
		return this.eventVolunteers;
	}

	public void setEventVolunteers(Set<EventVolunteer> eventVolunteers) {
		this.eventVolunteers = eventVolunteers;
	}

	public Set<Picture> getPictures() {
		return this.pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public Set<VolunteerInterest> getVolunteerInterests() {
		return this.volunteerInterests;
	}

	public void setVolunteerInterests(Set<VolunteerInterest> volunteerInterests) {
		this.volunteerInterests = volunteerInterests;
	}

	public Set<OrganizationFollower> getOrganizationFollowers() {
		return this.organizationFollowers;
	}

	public void setOrganizationFollowers(Set<OrganizationFollower> organizationFollowers) {
		this.organizationFollowers = organizationFollowers;
	}

	public Set<VolunteerDevice> getVolunteerDevices() {
		return this.volunteerDevices;
	}

	public void setVolunteerDevices(Set<VolunteerDevice> volunteerDevices) {
		this.volunteerDevices = volunteerDevices;
	}

	public Set<GoogleLogIn> getGoogleLogIns() {
		return this.googleLogIns;
	}

	public void setGoogleLogIns(Set<GoogleLogIn> googleLogIns) {
		this.googleLogIns = googleLogIns;
	}

	public Set<VolunteerSkill> getVolunteerSkills() {
		return this.volunteerSkills;
	}

	public void setVolunteerSkills(Set<VolunteerSkill> volunteerSkills) {
		this.volunteerSkills = volunteerSkills;
	}

	public Set<SocialLinks> getSocialLinkses() {
		return this.socialLinkses;
	}

	public void setSocialLinkses(Set<SocialLinks> socialLinkses) {
		this.socialLinkses = socialLinkses;
	}

	public Set<Notification> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

}
