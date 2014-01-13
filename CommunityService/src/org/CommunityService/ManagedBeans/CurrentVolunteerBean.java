package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Volunteer;

@ManagedBean
@SessionScoped
public class CurrentVolunteerBean {
	private Volunteer volunteer;

	public boolean isLoggedIn() {
		return volunteer != null;
	}
	
	// Getters and Setters
	// ---------------------------------------------------------------------------------------------------
	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
}
