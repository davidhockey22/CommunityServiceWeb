package org.CommunityService.ManagedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;

@ManagedBean
@SessionScoped
public class LeaderboardBean {

	@ManagedProperty(value = "#{currentVolunteerBean}")
	private CurrentVolunteerBean currentVolunteer;
	
	public List<Volunteer> getResults() {
		return results;
	}

	public void setResults(List<Volunteer> results) {
		this.results = results;
	}

	private List<Volunteer> results = null;
	
	public void preRenderHours() {
		
		results = VolunteerService.getLeaderboardByHours();
	}	
	public void preRenderPoints() {
		
		results = VolunteerService.getLeaderboardByPoints();
	}	
	public CurrentVolunteerBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(CurrentVolunteerBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
