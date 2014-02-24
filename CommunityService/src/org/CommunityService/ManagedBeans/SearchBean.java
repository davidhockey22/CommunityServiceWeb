package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.Services.DBConnection;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.SkillService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@SessionScoped
@Join(path="/search", to="/Web/Search.xhtml")
public class SearchBean {

	private String eventName;
	private List<String> selectedInterests = null;
	private List<String> selectedSkills = null;
	// TODO add implementation for organization and Group
	private List<String> selectedOrganizations = null;
	private List<String> selectedGroups = null;
	private List<String> selectedEvents = null;

	private List<Interest> interests;
	private List<Skill> skills;
	private List<Event> events = null;

	@SuppressWarnings("unchecked")
	public String Search() throws Exception {
		if (events != null) {
			events.clear();
		}
		List<String> params = new ArrayList<String>();
		params.clear();
		String like = "%" + eventName + "%";
		params.add(like);
		String query = "Select distinct e from Event as e left join e.eventSkills sk left join e.interests int where e.eventName like ? ";
		boolean first = true;

		if (selectedInterests.size() > 0) {
			query += "AND (";
			for (int i = 0; i < selectedInterests.size(); i++) {
				if (first != true) {
					query += "OR ";
				} else {
					first = false;
				}
				query += "int.name=? ";
				params.add(selectedInterests.get(i));
			}
			query += ")";
			first = true;
		}
		if (selectedSkills.size() > 0) {
			query += "AND (";
			for (int i = 0; i < selectedSkills.size(); i++) {
				if (first != true) {
					query += "OR ";
				} else {
					first = false;
				}
				query += "sk.skill.skillName=? ";
				params.add(selectedSkills.get(i));
			}
			query += ")";
		}

		System.out.println(query);
		// System.out.println(Arrays.toString(params.toArray()));

		setEvents((List<Event>) DBConnection.query(query, params));

		return "Search?faces-redirect=true";
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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
		if (skills == null) {
			skills = SkillService.getSkills();
		}
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<String> getSelectedInterests() {
		return selectedInterests;
	}

	public void setSelectedInterests(List<String> selectedInterests) {
		this.selectedInterests = selectedInterests;
	}

	public List<String> getSelectedSkills() {
		return selectedSkills;
	}

	public void setSelectedSkills(List<String> selectedSkills) {
		this.selectedSkills = selectedSkills;
	}

	public List<String> getSelectedOrganizations() {
		return selectedOrganizations;
	}

	public void setSelectedOrganizations(List<String> selectedOrganizations) {
		this.selectedOrganizations = selectedOrganizations;
	}

	public List<String> getSelectedGroups() {
		return selectedGroups;
	}

	public void setSelectedGroups(List<String> selectedGroups) {
		this.selectedGroups = selectedGroups;
	}

	public List<String> getSelectedEvents() {
		return selectedEvents;
	}

	public void setSelectedEvents(List<String> selectedEvents) {
		this.selectedEvents = selectedEvents;
	}

}
