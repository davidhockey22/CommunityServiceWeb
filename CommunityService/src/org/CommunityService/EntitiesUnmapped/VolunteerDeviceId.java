package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

/**
 * VolunteerDeviceId generated by hbm2java
 */
public class VolunteerDeviceId implements java.io.Serializable {

	private String deviceId;
	private int volunteerId;

	public VolunteerDeviceId() {
	}

	public VolunteerDeviceId(String deviceId, int volunteerId) {
		this.deviceId = deviceId;
		this.volunteerId = volunteerId;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
		if (!(other instanceof VolunteerDeviceId))
			return false;
		VolunteerDeviceId castOther = (VolunteerDeviceId) other;

		return ((this.getDeviceId() == castOther.getDeviceId()) || (this.getDeviceId() != null && castOther.getDeviceId() != null && this.getDeviceId().equals(
				castOther.getDeviceId())))
				&& (this.getVolunteerId() == castOther.getVolunteerId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getDeviceId() == null ? 0 : this.getDeviceId().hashCode());
		result = 37 * result + this.getVolunteerId();
		return result;
	}

}