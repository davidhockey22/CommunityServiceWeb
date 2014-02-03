package org.CommunityService.EntitiesMapped;

// Generated Feb 3, 2014 2:50:59 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * OrganizationFollower generated by hbm2java
 */
@Entity
@Table(name = "Organization_Follower", catalog = "dbAppData", uniqueConstraints = @UniqueConstraint(columnNames = "OrgFollowerID"))
public class OrganizationFollower implements java.io.Serializable {

	private OrganizationFollowerId id;
	private Organization organization;
	private Volunteer volunteer;
	private int orgFollowerId;

	public OrganizationFollower() {
	}

	public OrganizationFollower(OrganizationFollowerId id,
			Organization organization, Volunteer volunteer, int orgFollowerId) {
		this.id = id;
		this.organization = organization;
		this.volunteer = volunteer;
		this.orgFollowerId = orgFollowerId;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "volunteerId", column = @Column(name = "VolunteerID", nullable = false)),
			@AttributeOverride(name = "orgId", column = @Column(name = "OrgID", nullable = false)) })
	public OrganizationFollowerId getId() {
		return this.id;
	}

	public void setId(OrganizationFollowerId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OrgID", nullable = false, insertable = false, updatable = false)
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VolunteerID", nullable = false, insertable = false, updatable = false)
	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	@Column(name = "OrgFollowerID", unique = true, nullable = false)
	public int getOrgFollowerId() {
		return this.orgFollowerId;
	}

	public void setOrgFollowerId(int orgFollowerId) {
		this.orgFollowerId = orgFollowerId;
	}

}
