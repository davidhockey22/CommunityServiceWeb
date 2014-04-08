package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.Services.DBConnection;
import org.CommunityService.Services.InterestService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/searchorganization", to = "/Web/SearchOrganization.xhtml")
public class SearchOrganization {

	private String organizationName;
	private List<String> selectedInterests = null;
	// TODO add implementation for organization and Group
	private List<String> selectedOrganizations = null;

	private List<Interest> interests;
	private List<Organization> orgs = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String Search() throws Exception {
		if (orgs != null) {
			orgs.clear();
		}
		List params = new ArrayList();
		params.clear();
		String like = "%" + organizationName + "%";
		params.add(like);
		String query = "Select distinct o from Organization as o left join o.interests int where o.orgName like ? ";
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

		System.out.println(query);

		orgs = (List<Organization>) DBConnection.query(query, params);

		return "Search?faces-redirect=true";
	}

	public List<Interest> getInterests() {
		if (interests == null) {
			interests = InterestService.getInterests();
		}
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public List<String> getSelectedInterests() {
		return selectedInterests;
	}

	public void setSelectedInterests(List<String> selectedInterests) {
		this.selectedInterests = selectedInterests;
	}

	public List<String> getSelectedOrganizations() {
		return selectedOrganizations;
	}

	public void setSelectedOrganizations(List<String> selectedOrganizations) {
		this.selectedOrganizations = selectedOrganizations;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public List<Organization> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Organization> orgs) {
		this.orgs = orgs;
	}

}
