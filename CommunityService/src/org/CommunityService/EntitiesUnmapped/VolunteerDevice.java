package org.CommunityService.EntitiesUnmapped;

// Generated Feb 23, 2014 5:44:45 PM by Hibernate Tools 3.4.0.CR1

/**
 * VolunteerDevice generated by hbm2java
 */
public class VolunteerDevice implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7960953074345901633L;
	private Integer deviceId;
	private Volunteer volunteer;
	private String deviceTokenInternal;

	public VolunteerDevice() {
	}

	public VolunteerDevice(Volunteer volunteer, String deviceTokenInternal) {
		this.volunteer = volunteer;
		this.deviceTokenInternal = deviceTokenInternal;
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public String getDeviceTokenInternal() {
		return this.deviceTokenInternal;
	}

	public void setDeviceTokenInternal(String deviceTokenInternal) {
		this.deviceTokenInternal = deviceTokenInternal;
	}

}
