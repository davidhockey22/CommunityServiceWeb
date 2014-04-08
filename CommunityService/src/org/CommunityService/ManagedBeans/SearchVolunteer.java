package org.CommunityService.ManagedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/searchvolunteer", to = "/Web/SearchVolunteer.xhtml")
public class SearchVolunteer {
	private Volunteer volunteer;
	private String volunteerName;
	private List<Volunteer> volunteers;

	public String Search() {
		volunteers = VolunteerService.getVolunteersLikeName(volunteerName);

		return "Search?faces-redirect=true";
	}

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public String getVolunteerName() {
		return volunteerName;
	}

	public void setVolunteerName(String volunteerName) {
		this.volunteerName = volunteerName;
	}

	public List<Volunteer> getVolunteers() {
		return volunteers;
	}

	public void setVolunteers(List<Volunteer> volunteers) {
		this.volunteers = volunteers;
	}
}
