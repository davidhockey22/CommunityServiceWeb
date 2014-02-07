package org.CommunityService.EntitiesUnmapped;

// Generated Feb 5, 2014 10:51:42 PM by Hibernate Tools 3.4.0.CR1

/**
 * VolunteerInterest generated by hbm2java
 */
public class VolunteerInterest implements java.io.Serializable {

	private VolunteerInterestId id;
	private Interest interest;
	private Volunteer volunteer;
	private byte disinterest;

	public VolunteerInterest() {
	}

	public VolunteerInterest(VolunteerInterestId id, Interest interest, Volunteer volunteer, byte disinterest) {
		this.id = id;
		this.interest = interest;
		this.volunteer = volunteer;
		this.disinterest = disinterest;
	}

	public VolunteerInterestId getId() {
		return this.id;
	}

	public void setId(VolunteerInterestId id) {
		this.id = id;
	}

	public Interest getInterest() {
		return this.interest;
	}

	public void setInterest(Interest interest) {
		this.interest = interest;
	}

	public Volunteer getVolunteer() {
		return this.volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public byte getDisinterest() {
		return this.disinterest;
	}

	public void setDisinterest(byte disinterest) {
		this.disinterest = disinterest;
	}

}