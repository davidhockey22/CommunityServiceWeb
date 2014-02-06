package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

/**
 * OrganizationFollowerId generated by hbm2java
 */
public class OrganizationFollowerId implements java.io.Serializable {

	private int volunteerId;
	private int orgId;

	public OrganizationFollowerId() {
	}

	public OrganizationFollowerId(int volunteerId, int orgId) {
		this.volunteerId = volunteerId;
		this.orgId = orgId;
	}

	public int getVolunteerId() {
		return this.volunteerId;
	}

	public void setVolunteerId(int volunteerId) {
		this.volunteerId = volunteerId;
	}

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

		return (this.getVolunteerId() == castOther.getVolunteerId()) && (this.getOrgId() == castOther.getOrgId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getVolunteerId();
		result = 37 * result + this.getOrgId();
		return result;
	}

}
