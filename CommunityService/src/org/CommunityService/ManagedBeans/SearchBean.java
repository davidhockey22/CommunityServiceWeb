package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.Services.DBConnection;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.SkillService;

@ManagedBean
@SessionScoped
public class SearchBean {

	private String eventName;
	private List<Interest> selectedInterests = new ArrayList<Interest>();
	private List<Skill> selectedSkills = new ArrayList<Skill>();
	// TODO add implementation for organization and Group
	private List<Organization> selectedOrganizations = new ArrayList<Organization>();
	private List<Group> selectedGroups = new ArrayList<Group>();

	private List<Interest> interests;
	private List<Skill> skills;

	public String Search() {
		List<String> params;
		String query = "from Event as e ";
		String where = "where ";
		
		if(selectedInterests.size()>0 || selectedSkills.size()>0){
			query+=where;
			for(Interest i : selectedInterests){
				
			}
		}
		
		
		
		//DBConnection.query(query, params)
		
		
		
		
		
		return "SearchResults";
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public List<Interest> getSelectedInterests() {
		return selectedInterests;
	}

	public void setSelectedInterests(List<Interest> selectedInterests) {
		this.selectedInterests = selectedInterests;
	}

	public List<Skill> getSelectedSkills() {
		return selectedSkills;
	}

	public void setSelectedSkills(List<Skill> selectedSkills) {
		this.selectedSkills = selectedSkills;
	}

	public List<Organization> getSelectedOrganizations() {
		return selectedOrganizations;
	}

	public void setSelectedOrganizations(
			List<Organization> selectedOrganizations) {
		this.selectedOrganizations = selectedOrganizations;
	}

	public List<Group> getSelectedGroups() {
		return selectedGroups;
	}

	public void setSelectedGroups(List<Group> selectedGroups) {
		this.selectedGroups = selectedGroups;
	}

	public List<Interest> getInterests() throws Exception {
		if (interests == null) {
			interests = InterestService.getInterests();
		}
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public List<Skill> getSkills() throws Exception {
		skills = SkillService.getSkills();
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

}
