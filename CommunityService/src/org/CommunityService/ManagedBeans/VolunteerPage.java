package org.CommunityService.ManagedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.util.MD5Util;
import org.hibernate.HibernateException;

@ManagedBean
public class VolunteerPage {

	String volunteerEmail = "No email provided";
	List<Volunteer> allVolunteers;

	private Volunteer volunteer;
	private Integer volunteerId;
	private Integer imageSize;

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

	// see https://en.gravatar.com/site/implement/images/ for details
	public String getGravatarImage() {
		// this is the default avatar used by gravatar when a gravatar doesn't
		// exist for this email
		String defaultImage = "?d=identicon";

		// send null hash in the event there is no volunteer
		// TODO may want to change this to send back either null or empty string
		String emailHash = "00000000000000000000000000000000";
		if (getVolunteerEmail() != null)
			emailHash = MD5Util.md5Hex(getVolunteerEmail());

		StringBuffer avatar = new StringBuffer("http://www.gravatar.com/avatar/");
		avatar.append(emailHash);
		avatar.append(defaultImage);
		return avatar.toString();
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

	public Integer getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		try {
			this.imageSize = Integer.parseInt(imageSize);
		} catch (NumberFormatException e) {
			this.imageSize = null;
		}
	}

	public String getVolunteerId() {
		if (volunteerId != null)
			return volunteerId.toString();
		else
			return null;
	}
}