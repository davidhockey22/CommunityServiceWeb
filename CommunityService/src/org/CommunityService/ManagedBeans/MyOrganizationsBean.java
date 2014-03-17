package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;

@Join(path = "/organizations", to = "/Web/MyOrganizations.xhtml")
@ManagedBean
@RequestScoped
public class MyOrganizationsBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	List<OrganizationFollower> adminOrgs;
	List<OrganizationFollower> memberOrgs;
	List<OrganizationFollower> followerOrgs;

	@PostConstruct
	public void postConstructor() {
		adminOrgs = new ArrayList<OrganizationFollower>();
		memberOrgs = new ArrayList<OrganizationFollower>();
		followerOrgs = new ArrayList<OrganizationFollower>();

		Volunteer volunteer = currentVolunteer.getVolunteer();
		List<OrganizationFollower> organizationFollowers = VolunteerService.getOrganizationFollowersByVolunteer(volunteer);

		for (Iterator<OrganizationFollower> iterator = organizationFollowers.iterator(); iterator.hasNext();) {
			OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
			if (organizationFollower.getAdmin()) {
				adminOrgs.add(organizationFollower);
			} else if (organizationFollower.getMod()) {
				memberOrgs.add(organizationFollower);
			} else {
				followerOrgs.add(organizationFollower);
			}
		}
	}

	public List<OrganizationFollower> getAdminOrgs() {
		return adminOrgs;
	}

	public List<OrganizationFollower> getMemberOrgs() {
		return memberOrgs;
	}

	public List<OrganizationFollower> getFollowerOrgs() {
		return followerOrgs;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
