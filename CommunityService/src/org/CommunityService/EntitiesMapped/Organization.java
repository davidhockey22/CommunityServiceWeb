package org.CommunityService.EntitiesMapped;

// Generated Feb 22, 2014 8:45:42 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Organization generated by hbm2java
 */
@Entity
@Table(name = "Organization", catalog = "volunteerMeData", uniqueConstraints = { @UniqueConstraint(columnNames = "OrgName"),
		@UniqueConstraint(columnNames = "Address") })
public class Organization implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3343055981959181030L;
	private Integer orgId;
	private String orgName;
	private String address;
	private String phoneNumber;
	private String emailAddress;
	private String description;
	private Date createdOn;
	private Set<Group> groups = new HashSet<Group>(0);
	private Set<OrganizationFollower> organizationFollowers = new HashSet<OrganizationFollower>(0);
	private Set<Picture> pictures = new HashSet<Picture>(0);

	public Organization() {
	}

	public Organization(String orgName, String address, String phoneNumber, String emailAddress) {
		this.orgName = orgName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public Organization(String orgName, String address, String phoneNumber, String emailAddress, String description, Date createdOn, Set<Group> groups,
			Set<OrganizationFollower> organizationFollowers, Set<Picture> pictures) {
		this.orgName = orgName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.description = description;
		this.createdOn = createdOn;
		this.groups = groups;
		this.organizationFollowers = organizationFollowers;
		this.pictures = pictures;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "OrgID", unique = true, nullable = false)
	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "OrgName", unique = true, nullable = false, length = 60)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "Address", unique = true, nullable = false, length = 45)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PhoneNumber", nullable = false, length = 45)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "EmailAddress", nullable = false, length = 45)
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "Description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedOn", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "organizations", cascade = CascadeType.ALL)
	public Set<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<OrganizationFollower> getOrganizationFollowers() {
		return this.organizationFollowers;
	}

	public void setOrganizationFollowers(Set<OrganizationFollower> organizationFollowers) {
		this.organizationFollowers = organizationFollowers;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "organizations", cascade = CascadeType.ALL)
	public Set<Picture> getPictures() {
		return this.pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

}
