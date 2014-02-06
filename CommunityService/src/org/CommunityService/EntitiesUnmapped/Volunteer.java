package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Volunteer generated by hbm2java
 */
public class Volunteer implements java.io.Serializable {

	private Integer volunteerId;
	private Organization organization;
	private String volunteerName;
	private String volunteerPassword;
	private String phoneNumber;
	private String emailAddress;
	private Date lastLoginDate;
	private float points;
	private int hoursWorked;
	private Float avgRating;
	private Date creationDate;
	private Date activationDate;
	private String firstName;
	private String lastName;
	private String description;
	private Set facebookLogIns = new HashSet(0);
	private Set eventVolunteers = new HashSet(0);
	private Set pictures = new HashSet(0);
	private Set volunteerInterests = new HashSet(0);
	private Set organizations = new HashSet(0);
	private Set groupMods = new HashSet(0);
	private Set organizationFollowers = new HashSet(0);
	private Set googleLogIns = new HashSet(0);
	private Set groupMembers = new HashSet(0);
	private Set volunteerSkills = new HashSet(0);
	private Set socialLinkses = new HashSet(0);

	public Volunteer() {
	}

	public Volunteer(String volunteerName, String volunteerPassword, String phoneNumber, String emailAddress, Date lastLoginDate, float points,
			int hoursWorked, String firstName, String lastName) {
		this.volunteerName = volunteerName;
		this.volunteerPassword = volunteerPassword;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.lastLoginDate = lastLoginDate;
		this.points = points;
		this.hoursWorked = hoursWorked;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Volunteer(Organization organization, String volunteerName, String volunteerPassword, String phoneNumber, String emailAddress, Date lastLoginDate,
			float points, int hoursWorked, Float avgRating, Date creationDate, Date activationDate, String firstName, String lastName, String description,
			Set facebookLogIns, Set eventVolunteers, Set pictures, Set volunteerInterests, Set organizations, Set groupMods, Set organizationFollowers,
			Set googleLogIns, Set groupMembers, Set volunteerSkills, Set socialLinkses) {
		this.organization = organization;
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
		this.facebookLogIns = facebookLogIns;
		this.eventVolunteers = eventVolunteers;
		this.pictures = pictures;
		this.volunteerInterests = volunteerInterests;
		this.organizations = organizations;
		this.groupMods = groupMods;
		this.organizationFollowers = organizationFollowers;
		this.googleLogIns = googleLogIns;
		this.groupMembers = groupMembers;
		this.volunteerSkills = volunteerSkills;
		this.socialLinkses = socialLinkses;
	}

	public Integer getVolunteerId() {
		return this.volunteerId;
	}

	public void setVolunteerId(Integer volunteerId) {
		this.volunteerId = volunteerId;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
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

	public float getPoints() {
		return this.points;
	}

	public void setPoints(float points) {
		this.points = points;
	}

	public int getHoursWorked() {
		return this.hoursWorked;
	}

	public void setHoursWorked(int hoursWorked) {
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

	public Set getFacebookLogIns() {
		return this.facebookLogIns;
	}

	public void setFacebookLogIns(Set facebookLogIns) {
		this.facebookLogIns = facebookLogIns;
	}

	public Set getEventVolunteers() {
		return this.eventVolunteers;
	}

	public void setEventVolunteers(Set eventVolunteers) {
		this.eventVolunteers = eventVolunteers;
	}

	public Set getPictures() {
		return this.pictures;
	}

	public void setPictures(Set pictures) {
		this.pictures = pictures;
	}

	public Set getVolunteerInterests() {
		return this.volunteerInterests;
	}

	public void setVolunteerInterests(Set volunteerInterests) {
		this.volunteerInterests = volunteerInterests;
	}

	public Set getOrganizations() {
		return this.organizations;
	}

	public void setOrganizations(Set organizations) {
		this.organizations = organizations;
	}

	public Set getGroupMods() {
		return this.groupMods;
	}

	public void setGroupMods(Set groupMods) {
		this.groupMods = groupMods;
	}

	public Set getOrganizationFollowers() {
		return this.organizationFollowers;
	}

	public void setOrganizationFollowers(Set organizationFollowers) {
		this.organizationFollowers = organizationFollowers;
	}

	public Set getGoogleLogIns() {
		return this.googleLogIns;
	}

	public void setGoogleLogIns(Set googleLogIns) {
		this.googleLogIns = googleLogIns;
	}

	public Set getGroupMembers() {
		return this.groupMembers;
	}

	public void setGroupMembers(Set groupMembers) {
		this.groupMembers = groupMembers;
	}

	public Set getVolunteerSkills() {
		return this.volunteerSkills;
	}

	public void setVolunteerSkills(Set volunteerSkills) {
		this.volunteerSkills = volunteerSkills;
	}

	public Set getSocialLinkses() {
		return this.socialLinkses;
	}

	public void setSocialLinkses(Set socialLinkses) {
		this.socialLinkses = socialLinkses;
	}

}
