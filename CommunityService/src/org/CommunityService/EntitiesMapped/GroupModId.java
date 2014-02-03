package org.CommunityService.EntitiesMapped;

// Generated Feb 3, 2014 2:50:59 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GroupModId generated by hbm2java
 */
@Embeddable
public class GroupModId implements java.io.Serializable {

	private int groupId;
	private int volunteerId;

	public GroupModId() {
	}

	public GroupModId(int groupId, int volunteerId) {
		this.groupId = groupId;
		this.volunteerId = volunteerId;
	}

	@Column(name = "GroupID", nullable = false)
	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Column(name = "VolunteerID", nullable = false)
	public int getVolunteerId() {
		return this.volunteerId;
	}

	public void setVolunteerId(int volunteerId) {
		this.volunteerId = volunteerId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GroupModId))
			return false;
		GroupModId castOther = (GroupModId) other;

		return (this.getGroupId() == castOther.getGroupId())
				&& (this.getVolunteerId() == castOther.getVolunteerId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getGroupId();
		result = 37 * result + this.getVolunteerId();
		return result;
	}

}
