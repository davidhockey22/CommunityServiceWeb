package Test;

// Generated Feb 3, 2014 2:14:25 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * GoogleLogInId generated by hbm2java
 */
@Embeddable
public class GoogleLogInId implements java.io.Serializable {

	private int googleLogInId;
	private int volunteerId;

	public GoogleLogInId() {
	}

	public GoogleLogInId(int googleLogInId, int volunteerId) {
		this.googleLogInId = googleLogInId;
		this.volunteerId = volunteerId;
	}

	@Column(name = "GoogleLogInID", unique = true, nullable = false)
	public int getGoogleLogInId() {
		return this.googleLogInId;
	}

	public void setGoogleLogInId(int googleLogInId) {
		this.googleLogInId = googleLogInId;
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
		if (!(other instanceof GoogleLogInId))
			return false;
		GoogleLogInId castOther = (GoogleLogInId) other;

		return (this.getGoogleLogInId() == castOther.getGoogleLogInId())
				&& (this.getVolunteerId() == castOther.getVolunteerId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getGoogleLogInId();
		result = 37 * result + this.getVolunteerId();
		return result;
	}

}
