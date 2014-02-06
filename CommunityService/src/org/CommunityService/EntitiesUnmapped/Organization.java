package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Organization generated by hbm2java
 */
public class Organization implements java.io.Serializable {

	private Integer orgId;
	private String orgName;
	private String address;
	private String phoneNumber;
	private String emailAddress;
	private String description;
	private Date createdOn;
	private Set groups = new HashSet(0);
	private Set organizationFollowers = new HashSet(0);
	private Set pictures = new HashSet(0);
	private Set volunteers = new HashSet(0);
	private Set volunteers_1 = new HashSet(0);

	public Organization() {
	}

	public Organization(String orgName, String address, String phoneNumber, String emailAddress) {
		this.orgName = orgName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public Organization(String orgName, String address, String phoneNumber, String emailAddress, String description, Date createdOn, Set groups,
			Set organizationFollowers, Set pictures, Set volunteers, Set volunteers_1) {
		this.orgName = orgName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.description = description;
		this.createdOn = createdOn;
		this.groups = groups;
		this.organizationFollowers = organizationFollowers;
		this.pictures = pictures;
		this.volunteers = volunteers;
		this.volunteers_1 = volunteers_1;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Set getGroups() {
		return this.groups;
	}

	public void setGroups(Set groups) {
		this.groups = groups;
	}

	public Set getOrganizationFollowers() {
		return this.organizationFollowers;
	}

	public void setOrganizationFollowers(Set organizationFollowers) {
		this.organizationFollowers = organizationFollowers;
	}

	public Set getPictures() {
		return this.pictures;
	}

	public void setPictures(Set pictures) {
		this.pictures = pictures;
	}

	public Set getVolunteers() {
		return this.volunteers;
	}

	public void setVolunteers(Set volunteers) {
		this.volunteers = volunteers;
	}

	public Set getVolunteers_1() {
		return this.volunteers_1;
	}

	public void setVolunteers_1(Set volunteers_1) {
		this.volunteers_1 = volunteers_1;
	}

}
