package org.CommunityService.ManagedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.hibernate.HibernateException;

@ManagedBean
public class VolunteerPage {

	String volunteerEmail = "No email provided";
	List<Volunteer> allVolunteers;

	@ManagedProperty(value = "#{currentVolunteerBean}")
	private CurrentVolunteerBean currentVolunteer;

	private Volunteer volunteer;
	private Integer volunteerId;

	public String getVolunteerEmail() {
		try {
			Volunteer v = VolunteerService.getVolunteerByName("garfield");
			volunteerEmail = v.getEmailAddress();
		} catch (Exception e) {
			e.printStackTrace();
			volunteerEmail = "Exception thrown";
		}
		return volunteerEmail;
	}

	public List<Volunteer> getAllVolunteers() {
		this.allVolunteers = VolunteerService.getVolunteers();
		return allVolunteers;
	}

	public void fetchVolunteer() {
		if (this.volunteerId == null)
			this.volunteer = null;
		else if (currentVolunteer.getVolunteer().getVolunteerId() == this.volunteerId)
			this.volunteer = currentVolunteer.getVolunteer();
		else
			try {
				this.volunteer = VolunteerService.getVolunteerById(this.volunteerId);
			} catch (HibernateException e) {
				this.volunteer = null;
				e.printStackTrace();
			}
	}

	// Getters and Setters
	public void setAllVolunteers(List<Volunteer> allVolunteer) {
		this.allVolunteers = allVolunteer;
	}

	public void setVolunteerEmail(String volunteerEmail) {
		this.volunteerEmail = volunteerEmail;
	}

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteerId(String volunteerId) {
		this.volunteerId = Integer.parseInt(volunteerId);
	}
}