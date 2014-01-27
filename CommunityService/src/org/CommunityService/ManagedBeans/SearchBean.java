package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.Services.InterestService;

@ManagedBean
public class SearchBean {

	private String eventName;
	private List<Interest> selectedInterests;
	private List<Skill> selectedSkills;
	// TODO add implementation for organization and Group
	private List<Organization> selectedOrganizations;
	private List<Group> selectedGroups;

	private List<Interest> interests;
	private List<Interest> skills;
	private List<Interest> organizations;
	private List<Interest> groups;

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

	public void setSelectedOrganizations(List<Organization> selectedOrganizations) {
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

	public List<Interest> getSkills() {
		return skills;
	}

	public void setSkills(List<Interest> skills) {
		this.skills = skills;
	}

	public List<Interest> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Interest> organizations) {
		this.organizations = organizations;
	}

	public List<Interest> getGroups() {
		return groups;
	}

	public void setGroups(List<Interest> groups) {
		this.groups = groups;
	}

}
