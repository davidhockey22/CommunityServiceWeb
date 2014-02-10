package org.CommunityService.ManagedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.Gravatar;
import org.hibernate.HibernateException;

@ManagedBean
public class VolunteerPage {

	String volunteerEmail = "No email provided";
	List<Volunteer> allVolunteers;

	private Volunteer volunteer;
	private Integer volunteerId;

	public String getVolunteerEmail() {
		fetchVolunteer();
		if (volunteer != null)
			return volunteer.getEmailAddress();
		else
			return null;
	}

	public List<Volunteer> getAllVolunteers() {
		this.allVolunteers = VolunteerService.getVolunteers();
		return allVolunteers;
	}

	public void fetchVolunteer() {
		if (this.volunteerId == null) {
			this.volunteer = null;
		} else {
			try {
				this.volunteer = VolunteerService.getVolunteerById(this.volunteerId);
			} catch (HibernateException e) {
				this.volunteer = null;
				e.printStackTrace();
			}
		}
	}

	public String getGravatarURL () {
		String email = (this.volunteer != null ? this.volunteer.getEmailAddress() : "");
		return Gravatar.getGravatarImage(email);
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
		try {
			this.volunteerId = Integer.parseInt(volunteerId);
		} catch (NumberFormatException e) {
			this.volunteerId = null;
		}
	}

	public String getVolunteerId() {
		if (volunteerId != null)
			return volunteerId.toString();
		else
			return null;
	}
}