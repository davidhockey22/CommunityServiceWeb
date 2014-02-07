package org.CommunityService.EntitiesMapped;

// Generated Feb 3, 2014 2:50:59 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OrganizationFollowerId generated by hbm2java
 */
@Embeddable
public class OrganizationFollowerId implements java.io.Serializable {

	private int volunteerId;
	private int orgId;

	public OrganizationFollowerId() {
	}

	public OrganizationFollowerId(int volunteerId, int orgId) {
		this.volunteerId = volunteerId;
		this.orgId = orgId;
	}

	@Column(name = "VolunteerID", nullable = false)
	public int getVolunteerId() {
		return this.volunteerId;
	}

	public void setVolunteerId(int volunteerId) {
		this.volunteerId = volunteerId;
	}

	@Column(name = "OrgID", nullable = false)
	public int getOrgId() {
		return this.orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OrganizationFollowerId))
			return false;
		OrganizationFollowerId castOther = (OrganizationFollowerId) other;

		return (this.getVolunteerId() == castOther.getVolunteerId())
				&& (this.getOrgId() == castOther.getOrgId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getVolunteerId();
		result = 37 * result + this.getOrgId();
		return result;
	}

}