package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

/**
 * VolunteerDevice generated by hbm2java
 */
public class VolunteerDevice implements java.io.Serializable {

	private VolunteerDeviceId id;
	private String deviceTokenInternal;

	public VolunteerDevice() {
	}

	public VolunteerDevice(VolunteerDeviceId id, String deviceTokenInternal) {
		this.id = id;
		this.deviceTokenInternal = deviceTokenInternal;
	}

	public VolunteerDeviceId getId() {
		return this.id;
	}

	public void setId(VolunteerDeviceId id) {
		this.id = id;
	}

	public String getDeviceTokenInternal() {
		return this.deviceTokenInternal;
	}

	public void setDeviceTokenInternal(String deviceTokenInternal) {
		this.deviceTokenInternal = deviceTokenInternal;
	}

}
