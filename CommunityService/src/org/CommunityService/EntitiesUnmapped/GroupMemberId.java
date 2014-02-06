package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

/**
 * GroupMemberId generated by hbm2java
 */
public class GroupMemberId implements java.io.Serializable {

	private int groupId;
	private int volunteerId;

	public GroupMemberId() {
	}

	public GroupMemberId(int groupId, int volunteerId) {
		this.groupId = groupId;
		this.volunteerId = volunteerId;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

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
		if (!(other instanceof GroupMemberId))
			return false;
		GroupMemberId castOther = (GroupMemberId) other;

		return (this.getGroupId() == castOther.getGroupId()) && (this.getVolunteerId() == castOther.getVolunteerId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getGroupId();
		result = 37 * result + this.getVolunteerId();
		return result;
	}

}
