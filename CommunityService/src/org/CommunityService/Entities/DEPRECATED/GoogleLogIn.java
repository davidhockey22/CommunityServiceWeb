package org.CommunityService.Entities.DEPRECATED;

// Generated Oct 12, 2013 4:02:28 PM by Hibernate Tools 3.4.0.CR1

/**
 * GoogleLogIn generated by hbm2java
 */
public class GoogleLogIn implements java.io.Serializable {

	private Integer googleLogInId;
	private Volunteer volunteer;
	private String token;

	public GoogleLogIn() {
	}

	public GoogleLogIn(Volunteer volunteer, String token) {
		this.volunteer = volunteer;
		this.token = token;
	}

	public Integer getGoogleLogInId() {
		return this.googleLogInId;
	}

	public void setGoogleLogInId(Integer googleLogInId) {
		this.googleLogInId = googleLogInId;
	}

	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
